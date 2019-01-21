package com.nikolov.utilslib.arrays;

public abstract class ArrayUtils {

    private ArrayUtils() {
    }

    public static Byte[] wrapByteArray(byte[] nonWrapped) {
        Byte[] result = new Byte[nonWrapped.length];
        for (int i = 0; i < nonWrapped.length; i++) {
            result[i] = nonWrapped[i];
        }
        return result;
    }
}
