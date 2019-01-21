package com.nikolov.utilslib.arrays;

import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilsTest {

    @Test
    public void whenCallWrapByteArrayItShouldReturnWrappedByteArray() {
        byte[] nonWrapped = {1, 2, 3, 4, 5, 56, 67, 8};
        Byte[] wrapped = {1, 2, 3, 4, 5, 56, 67, 8};

        Byte[] result = ArrayUtils.wrapByteArray(nonWrapped);
        Assert.assertArrayEquals(result, wrapped);
    }
}