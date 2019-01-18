package com.nikolov.utilslib.bytes;

import com.nikolov.utilslib.bytes.exceptions.BufferEmptyException;
import com.nikolov.utilslib.bytes.exceptions.UnexpectedArrayLengthException;
import com.nikolov.utilslib.primitives.NumberValue;
import com.nikolov.utilslib.primitives.PrimitiveType;
import com.nikolov.utilslib.primitives.PrimitiveValue;
import com.nikolov.utilslib.primitives.StringValue;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Objects;

import static com.nikolov.utilslib.primitives.PrimitiveType.*;

/**
 * Provides useful tools for parsing primitive values from bytes.
 */
public class FromBytesTranslator {

    private static final int EXPECTED_INT8_ARRAY_LENGTH = 1;
    private static final int EXPECTED_INT16_ARRAY_LENGTH = 2;
    private static final int EXPECTED_INT32_ARRAY_LENGTH = 4;
    private static final int EXPECTED_INT64_ARRAY_LENGTH = 8;
    private static final int EXPECTED_FLOAT_ARRAY_LENGTH = 4;
    private static final int EXPECTED_DOUBLE_ARRAY_LENGTH = 8;

    private ByteBuffer byteBuffer;
    private int positionIndex;

    /**
     * Translates a string from passed byte array, start and end indices
     *
     * @param array byte array containing the string
     * @param start start index
     * @param end   end index
     * @return translated string
     */
    public static String getString(byte[] array, int start, int end) {

        // Return default value
        if (array == null || array.length == 0) {
            return "";
        }

        return new String(Arrays.copyOfRange(array, start, end));
    }

    /**
     * Parse int8 (byte) value from bytes
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Byte>} wrapped int8 value
     */
    public static PrimitiveValue<Byte> getInt8(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT8_ARRAY_LENGTH);
        return new NumberValue<>(INT8, wrapArray(array, order).get());
    }

    /**
     * Parse int16 (short) value from bytes
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Short>} wrapped int16 value
     */
    public static NumberValue<Short> getInt16(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT16_ARRAY_LENGTH);
        return new NumberValue<>(INT16, wrapArray(array, order).getShort());
    }

    /**
     * Parse int32 (int) value from bytes
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Integer>} wrapped int32 value
     */
    public static NumberValue<Integer> getInt32(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT32_ARRAY_LENGTH);
        return new NumberValue<>(INT32, wrapArray(array, order).getInt());
    }

    /**
     * Parse int64 (long) value from bytes
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Long>} wrapped int64 value
     */
    public static NumberValue<Long> getInt64(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT64_ARRAY_LENGTH);
        return new NumberValue<>(INT64, wrapArray(array, order).getLong());
    }

    /**
     * Parse float value from bytes
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Float>} wrapped float value
     */
    public static NumberValue<Float> getFloat(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_FLOAT_ARRAY_LENGTH);
        return new NumberValue<>(FLOAT, wrapArray(array, order).getFloat());
    }

    /**
     * Parse double value from bytes
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Double>} wrapped double value
     */
    public static NumberValue<Double> getDouble(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_DOUBLE_ARRAY_LENGTH);
        return new NumberValue<>(DOUBLE, wrapArray(array, order).getDouble());
    }

    /**
     * Parse uint8 value from bytes, placed in a Java Short
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Short>} wrapped short value
     */
    public static NumberValue<Short> getUInt8(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT8_ARRAY_LENGTH);
        return new NumberValue<>(UINT8, (short) Byte.toUnsignedInt(wrapArray(array, order).get()));
    }

    /**
     * Parse uint16 value from bytes, placed in a Java Integer
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Integer>} wrapped int value
     */
    public static NumberValue<Integer> getUInt16(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT16_ARRAY_LENGTH);
        return new NumberValue<>(UINT16, Short.toUnsignedInt(wrapArray(array, order).getShort()));
    }

    /**
     * Parse uint32 value from bytes, placed in a Java Long
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<Long>} wrapped long value
     */
    public static NumberValue<Long> getUInt32(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT32_ARRAY_LENGTH);
        return new NumberValue<>(UINT32, Integer.toUnsignedLong(wrapArray(array, order).getInt()));
    }

    /**
     * Parse uint64 value from bytes, placed in a Java BigInteger
     *
     * @param array array with bytes
     * @param order {@link ByteOrder} preferred order
     * @return {@link PrimitiveValue<BigInteger>} wrapped big integer value
     */
    public static NumberValue<BigInteger> getUInt64(byte[] array, ByteOrder order) {
        assertByteArrayLength(array, EXPECTED_INT64_ARRAY_LENGTH);
        return new NumberValue<>(UINT64, new BigInteger(Long.toUnsignedString(wrapArray(array, order).getLong())));
    }

    private static ByteBuffer wrapArray(byte[] array, ByteOrder order) {

        if (array == null) {
            throw new InvalidParameterException();
        }
        ByteBuffer bb;
        // ByteBuffer is initialized with BIG_ENDIAN by default, so
        // we care about order only if its not null
        if (order != null) {
            bb = ByteBuffer.allocate(array.length).order(order).put(array);
        } else {
            bb = ByteBuffer.wrap(array);
        }
        bb.position(0);
        return bb;
    }

    /**
     * Returns the wrapped array
     *
     * @return byte array or null if not wrapped a byte array
     */
    public byte[] getBufferArray() {
        if (byteBuffer == null)
            return null;

        return byteBuffer.array();
    }

    public boolean isBufferEmpty() {
        return byteBuffer == null || byteBuffer.array().length == 0;
    }

    public void wrap(byte[] array) {
        byteBuffer = ByteBuffer.wrap(array);
    }

    public void wrap(byte[] array, ByteOrder order) {
        this.wrap(array);
        byteBuffer.order(order);
    }

    public PrimitiveValue<String> getString(int length) {
        return getString(positionIndex, length);
    }

    public PrimitiveValue<String> getString(int start, int end) {
        positionIndex = start + end;
        byte[] temp = Arrays.copyOfRange(byteBuffer.array(), start, positionIndex);
        return new StringValue(new String(temp));
    }

    @SuppressWarnings("unchecked")
    public <T extends Number> PrimitiveValue<T> getNumber(PrimitiveType type) {
        return getNumber(type, positionIndex);
    }

    public <T extends Number> PrimitiveValue getNumber(PrimitiveType type, int bufferPosition) {

        if (type == null) {
            throw new InvalidParameterException();
        }
        if (isBufferEmpty()) {
            throw new BufferEmptyException();
        }

        positionIndex = bufferPosition + type.getBytesCount();
        byte[] temp = Arrays.copyOfRange(byteBuffer.array(), bufferPosition, positionIndex);

        ByteOrder order = byteBuffer.order();
        if (type.isOfType(DOUBLE)) {
            return getDouble(temp, order);
        } else if (type.isOfType(FLOAT)) {
            return getFloat(temp, order);
        } else if (type.isOfType(INT8)) {
            return getInt8(temp, order);
        } else if (type.isOfType(INT16)) {
            return getInt16(temp, order);
        } else if (type.isOfType(INT32)) {
            return getInt32(temp, order);
        } else if (type.isOfType(INT64)) {
            return getInt64(temp, order);
        } else if (type.isOfType(UINT8)) {
            return getUInt8(temp, order);
        } else if (type.isOfType(UINT16)) {
            return getUInt16(temp, order);
        } else if (type.isOfType(UINT32)) {
            return getUInt32(temp, order);
        } else if (type.isOfType(UINT64)) {
            return getUInt64(temp, order);
        } else {
            throw new InvalidParameterException("Primitive type id is not valid");
        }
    }

    public int getBufferPositionIndex() {
        return this.positionIndex;
    }

    public void resetBufferPositionIndex() {
        this.positionIndex = 0;
    }

    public void setBufferPositionIndex(int position) {
        this.positionIndex = position;
    }

    public void setOrder(ByteOrder order) {
        byteBuffer.order(order);
    }

    public boolean hasMoreToRead() {
        return positionIndex < byteBuffer.array().length;
    }

    public boolean canReadValue(int bytesToRead) {
        return byteBuffer.array().length >= positionIndex + bytesToRead;
    }

    @SuppressWarnings("unchecked")
    public void processTemplatedValues(PrimitiveValue[] template) {

        PrimitiveValue temp = null;
        for (PrimitiveValue pv : template) {
            Class<?> type = pv.getType();
            if (!canReadValue(pv.getBytesCount())) {
                throw new UnexpectedArrayLengthException();
            }

            if (Objects.equals(type, Number.class)) {
                temp = getNumber(((NumberValue) pv).getPrimitiveType());
            } else if (Objects.equals(type, String.class)) {
                temp = getString(pv.getBytesCount());
            }

            if (temp != null) {
                pv.setValue(temp.getValue());
            }
        }
    }

    private static void assertByteArrayLength(byte[] array, int expectedLength) {
        if (array.length != expectedLength) {
            throw new UnexpectedArrayLengthException();
        }
    }
}
