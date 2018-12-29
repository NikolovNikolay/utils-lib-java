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

public class FromBytesTranslator {

    private static final int EXPECTED_INT8_ARRAY_LENGTH = 1;
    private static final int EXPECTED_INT16_ARRAY_LENGTH = 2;
    private static final int EXPECTED_INT32_ARRAY_LENGTH = 4;
    private static final int EXPECTED_INT64_ARRAY_LENGTH = 8;
    private static final int EXPECTED_FLOAT_ARRAY_LENGTH = 4;
    private static final int EXPECTED_DOUBLE_ARRAY_LENGTH = 8;

    private ByteBuffer bb;
    private int positionIndex;

    public byte[] getBufferArray() {
        return bb.array();
    }

    public boolean isBufferEmpty() {
        return bb == null || bb.array().length == 0;
    }

    public void wrap(byte[] array) {
        bb = ByteBuffer.wrap(array);
    }

    public void wrap(byte[] array, ByteOrder order) {
        this.wrap(array);
        bb.order(order);
    }

    public static String getString(byte[] array, int start, int end) {

        if (array == null || array.length == 0) {
            return "";
        }

        return new String(Arrays.copyOfRange(array, start, end));
    }

    public static PrimitiveValue<Byte> getInt8(byte[] array, ByteOrder order) {

        if (array.length != EXPECTED_INT8_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(INT8, wrapArray(array, order).get());
    }

    public static NumberValue<Short> getInt16(byte[] array, ByteOrder order) {

        if (array.length != EXPECTED_INT16_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(INT16, wrapArray(array, order).getShort());
    }

    public static NumberValue<Integer> getInt32(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_INT32_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(INT32, wrapArray(array, order).getInt());
    }

    public static NumberValue<Long> getInt64(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_INT64_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(INT64, wrapArray(array, order).getLong());
    }

    public static NumberValue<Float> getFloat(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_FLOAT_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(FLOAT, wrapArray(array, order).getFloat());
    }

    public static NumberValue<Double> getDouble(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_DOUBLE_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(DOUBLE, wrapArray(array, order).getDouble());
    }

    public static NumberValue<Short> getUInt8(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_INT8_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(UINT8, (short) Byte.toUnsignedInt(wrapArray(array, order).get()));
    }

    public static NumberValue<Integer> getUInt16(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_INT16_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(UINT16, Short.toUnsignedInt(wrapArray(array, order).getShort()));
    }

    public static NumberValue<Long> getUInt32(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_INT32_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(UINT32, Integer.toUnsignedLong(wrapArray(array, order).getInt()));
    }

    public static NumberValue<BigInteger> getUInt64(byte[] array, ByteOrder order) {
        if (array.length != EXPECTED_INT64_ARRAY_LENGTH) {
            throw new UnexpectedArrayLengthException();
        }

        return new NumberValue<>(UINT64, new BigInteger(Long.toUnsignedString(wrapArray(array, order).getLong())));
    }

    private static ByteBuffer wrapArray(byte[] array, ByteOrder order) {

        if (array == null) {
            throw new InvalidParameterException();
        }
        // ByteBuffer is initialized with BIG_ENDIAN by default, so
        // we care about order only if its not null
        if (order != null) {
            ByteBuffer bb = ByteBuffer.allocate(array.length).order(order).put(array);
            bb.position(0);
            return bb;
        } else {
            return ByteBuffer.wrap(array);
        }
    }

    public PrimitiveValue<String> getString(int length) {
        return getString(positionIndex, length);
    }

    public PrimitiveValue<String> getString(int start, int end) {
        positionIndex = start + end;
        byte[] temp = Arrays.copyOfRange(bb.array(), start, positionIndex);
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
        byte[] temp = Arrays.copyOfRange(bb.array(), bufferPosition, positionIndex);

        ByteOrder order = bb.order();
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
        bb.order(order);
    }

    public boolean hasMoreToRead() {
        return positionIndex < bb.array().length;
    }

    public boolean canReadValue(int bytesToRead) {
        return bb.array().length >= positionIndex + bytesToRead;
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
}
