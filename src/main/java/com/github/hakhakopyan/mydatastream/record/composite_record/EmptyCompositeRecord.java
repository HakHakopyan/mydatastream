package com.github.hakhakopyan.mydatastream.record.composite_record;

public class EmptyCompositeRecord extends CompositeRecord{

    public EmptyCompositeRecord() {
        super(EMPTY_RECORD_NAME);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String getName() {
        return EMPTY_RECORD_NAME;
    }

    /**
     * Nothing, because our EmptyRecord already have unchangeable name - {@link EmptyCompositeRecord#EMPTY_RECORD_NAME}
     * @param recordName never mind
     */
    @Override
    public void setName(String recordName) {
        // Nothing? because our EmptyRecord already have unchangeable
    }
}
