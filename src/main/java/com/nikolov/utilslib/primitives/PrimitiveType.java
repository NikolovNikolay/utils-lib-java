package com.nikolov.utilslib.primitives;

import java.math.BigInteger;

public final class PrimitiveType {

    public static final PrimitiveType INT8 = new PrimitiveType(1, 1, 0, -128, 127);
    public static final PrimitiveType INT16 = new PrimitiveType(2, 2, 0, -32768, 32767);
    public static final PrimitiveType INT32 = new PrimitiveType(3, 4, 0, -2147483648, 2147483647);
    public static final PrimitiveType INT64 = new PrimitiveType(4, 8, 0, -9223372036854775808L, 9223372036854775807L);

    public static final PrimitiveType UINT8 = new PrimitiveType(5, 1, 0, 0, 255);
    public static final PrimitiveType UINT16 = new PrimitiveType(6, 2, 0, 0, 65535);
    public static final PrimitiveType UINT32 = new PrimitiveType(7, 4, 0, 0, 4294967295L);
    public static final PrimitiveType UINT64 = new PrimitiveType(8, 8, 0, 0, new BigInteger("18446744073709551614"));

    public static final PrimitiveType FLOAT = new PrimitiveType(9, 4, 0.0F, Float.MIN_VALUE, Float.MAX_VALUE);
    public static final PrimitiveType DOUBLE = new PrimitiveType(10, 8, 0.0D, Double.MIN_VALUE, Double.MAX_VALUE);

    private int id;
    private int bytesCount;
    private Number defaultVal;
    private Number minVal;
    private Number maxVal;

    private PrimitiveType(int id, int bytesCount, Number defaultVal, Number minVal, Number maxVal) {
        this.id = id;
        this.bytesCount = bytesCount;
        this.defaultVal = defaultVal;
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    public int getId() {
        return id;
    }

    public int getBytesCount() {
        return bytesCount;
    }

    public Number getDefaultVal() {
        return defaultVal;
    }

    public Number getMinVal() {
        return minVal;
    }

    public Number getMaxVal() {
        return maxVal;
    }

    public boolean isOfType(PrimitiveType type) {
        return this.id == type.getId();
    }
}
