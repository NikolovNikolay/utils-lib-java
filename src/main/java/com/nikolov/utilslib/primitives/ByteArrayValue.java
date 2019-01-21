package com.nikolov.utilslib.primitives;

public class ByteArrayValue implements PrimitiveValue<Byte[]> {

    private int bytesCount;
    private Byte[] value;

    public ByteArrayValue(Byte[] value) {
        this.value = value;
        this.bytesCount = value.length;
    }

    public ByteArrayValue(int bytesCount) {
        this.bytesCount = bytesCount;
    }

    @Override
    public int getBytesCount() {
        return bytesCount;
    }

    @Override
    public Byte[] getValue() {
        return value;
    }

    @Override
    public void setValue(Byte[] value) {
        this.value = value;
        this.bytesCount = value.length;
    }

    @Override
    public Class<?> getType() {
        return Byte[].class;
    }
}
