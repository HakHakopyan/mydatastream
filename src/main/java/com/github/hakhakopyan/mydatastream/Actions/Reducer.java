package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;

import java.util.function.BinaryOperator;

public class Reducer implements Actionable{
    /**
     * Результат выполения операции над двумя записями
     */
    CompositeRecordable myResult = new EmptyCompositeRecord();
    /**
     * Возвращаемое значения для метода {@link Reducer#action(CompositeRecordable)}
     */
    CompositeRecordable myAnswer = new EmptyCompositeRecord();
    /**
     * Бинарная операция для выполнения действия над передаваем Записью в методе {@link Reducer#action(CompositeRecordable)}
     */
    BinaryOperator<CompositeRecordable> myBinaryOperator;

    public Reducer(BinaryOperator<CompositeRecordable> binaryOperator) {
        this.myBinaryOperator = binaryOperator;
    }

    /**
     * Выполняем заданную бинарную операция над переданной записью и результатом предыдущей бинарной операции
     * Для того чтобы прервать дальнейшее продвижение переданной записи в потоке выполения, возвращаем пустую запись
     * @param record Запись являющаяс вторым компонентом дл нашей ютинарной операции
     * @return instance of {@link EmptyCompositeRecord}
     */
    @Override
    public synchronized CompositeRecordable action(CompositeRecordable record) {
        this.myResult = this.myBinaryOperator.apply(this.myResult, record);

        return this.myAnswer;
    }

    public CompositeRecordable getResult() {
        return this.myResult;
    }
}
