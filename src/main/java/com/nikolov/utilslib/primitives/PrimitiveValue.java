package com.nikolov.utilslib.primitives;

import java.io.Serializable;

public interface PrimitiveValue<T extends Serializable> {

    int getBytesCount();

    T getValue();

    void setValue(T value);

    Class<?> getType();
}
