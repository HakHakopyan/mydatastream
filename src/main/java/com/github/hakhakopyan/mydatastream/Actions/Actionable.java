package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.Formatable;

/**
 * Интерфейс для определения объекта, хранящего лябда выражение, выполняющее какое то действие над экземпляром {@link CompositeRecord}
 * @param <C>
 */
public interface Actionable <C extends CompositeRecordable&Formatable> {
    /**
     * Выполняет зараннее заданное действие над экземпляром {@link CompositeRecord}
     * @param record instance of {@link CompositeRecord}
     * @return instance of {@link CompositeRecord}
     *         которое является результатом выполнения заранее определенных действий над входной записью
     */
    CompositeRecordable action(C record);
}
