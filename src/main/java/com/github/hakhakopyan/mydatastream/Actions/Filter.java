package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.Formatable;

import java.util.function.Predicate;

/**
 * реализует проверку условия, заданного с помощью интерфейса Predicate, над instance of {@link CompositeRecord}
 * @param <C> {@link CompositeRecord}
 */
public class Filter<C extends CompositeRecordable&Formatable> implements Actionable<C> {
    /**
     * Contains Лямбда на основе интерфейса Predicate для проверки Composite Record
     */
    Predicate<C> MyFormater;

    /**
     * Инициализируем лямбдой {@link Filter#MyFormater}
     * @param formater содержит лямбду для проверки записи
     */
    public Filter(Predicate<C> formater) {
        this.MyFormater = formater;
    }

    /**
     *
     * @param record instance of {@link CompositeRecord}
     * @return Переданную в параметре запись, если она прошла проверки, или instance of {@link EmptyCompositeRecord}
     */
    @Override
    public CompositeRecordable action(C record) {
        if (MyFormater.test(record))
            return record;
        return new EmptyCompositeRecord();
    }
}
