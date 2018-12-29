package com.nikolov.utilslib.primitives;

public class StringValue implements PrimitiveValue<String> {

    private int bytesCount;
    private String value;

    public StringValue(int bytesCount) {
        this.bytesCount = bytesCount;
    }

    public StringValue(String value) {
        this.value = value;
        this.bytesCount = value.getBytes().length;
    }

    @Override
    public int getBytesCount() {
        return bytesCount;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Class<?> getType() {
        return String.class;
    }
}
