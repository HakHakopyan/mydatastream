package com.github.hakhakopyan.mydatastream.record.item;

public class Item {
    String value;
    ItemType type;

    public static Item getEmptyItem() {
        return new Item("", ItemType.NULL);
    }

    public Item(String value, ItemType type) {
        this.value = value;
        this.type = type;
    }

    public Item(Item item) {
        this(item.getValue(), item.getType());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
