package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.Actions.functional_interfaces.VoidReturnable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

/**
 * Релизация Модификатора для instance of {@link CompositeRecord},
 * which use lympda based on {@link VoidReturnable} interface
 */
public class Modifier implements Actionable {
    /**
     * Contains Лямбда на основе интерфейса {@link VoidReturnable}
     */
    VoidReturnable myModifier;

    /**
     * Инициализирует {@link Modifier#myModifier}
     * @param modifier содержит лямбду, выполняющую модификацию над instance of {@link CompositeRecord}
     */
    public Modifier(VoidReturnable modifier) {
        this.myModifier = modifier;
    }

    /**
     * Выполняет изменения над Composite Record  с помощью заданной {@link VoidReturnable} ляюбды
     * @param record instance of {@link CompositeRecord}
     * @return modified Composite Record
     */
    @Override
    public synchronized CompositeRecordable action(CompositeRecordable record) {

        this.myModifier.execute(record);

        return record;
    }
}
