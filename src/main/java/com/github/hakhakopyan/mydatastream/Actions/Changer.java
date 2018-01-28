package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.*;

import java.util.function.UnaryOperator;

/**
 * Релизация Модификатора для instance of {@link CompositeRecord}, which use UnaryOperator lympda
 */
public class Changer implements Actionable {
    /**
     * Contains Лямбда на основе интерфейса UnaryOperator
     */
    UnaryOperator<CompositeRecordable> myChanger;

    /**
     * Инициализирует {@link Changer#myChanger}
     * @param changer содержит лямбду, выполняющую модификацию над instance of {@link CompositeRecord}
     */
    public Changer(UnaryOperator<CompositeRecordable> changer) {
        this.myChanger = changer;
    }

    /**
     * Выполняет изменения над Composite Record  с помощью заданной UnaryOperator ляюбды
     * @param record instance of {@link CompositeRecord}
     * @return modified Composite Record
     */
    @Override
    public CompositeRecordable action(CompositeRecordable record) {

        return (CompositeRecordable) myChanger.apply(record);
    }
}
