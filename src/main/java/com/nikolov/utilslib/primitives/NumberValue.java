package com.nikolov.utilslib.primitives;

/**
 * Wrapper over {@link java.lang.Number}. Can be used when dealing with the unsigned primitive types,
 * that are not specified in Java. In such case the unsigned value is return in some greater number type.
 * <p>
 * E.g.
 * UINT64 -> BigInteger
 * UINT32 -> Long
 * UINT16 -> Integer
 *
 * @param <T> Primitive wrapper that extends Number
 * @see PrimitiveType
 */
public class NumberValue<T extends Number> implements PrimitiveValue<T> {

    private PrimitiveType type;
    private T wrap;

    public NumberValue(PrimitiveType type) {
        this.type = type;
    }

    public NumberValue(PrimitiveType type, T value) {
        this.type = type;
        this.wrap = value;
    }

    public PrimitiveType getPrimitiveType() {
        return type;
    }

    public T getWrap() {
        return wrap;
    }

    public void setWrap(T wrap) {
        this.wrap = wrap;
    }

    @Override
    public int getBytesCount() {
        return type.getBytesCount();
    }

    @Override
    public T getValue() {
        return wrap;
    }

    @Override
    public void setValue(T value) {
        setWrap(value);
    }

    @Override
    public Class<?> getType() {
        return Number.class;
    }
}
