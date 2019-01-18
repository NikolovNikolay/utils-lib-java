package com.nikolov.utilslib.bytes;

import com.nikolov.utilslib.bytes.exceptions.BufferEmptyException;
import com.nikolov.utilslib.bytes.exceptions.UnexpectedArrayLengthException;
import com.nikolov.utilslib.primitives.NumberValue;
import com.nikolov.utilslib.primitives.PrimitiveType;
import com.nikolov.utilslib.primitives.PrimitiveValue;
import com.nikolov.utilslib.primitives.StringValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;

import static com.nikolov.utilslib.bytes.FromBytesTranslator.*;
import static com.nikolov.utilslib.primitives.PrimitiveType.*;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class FromBytesTranslatorTest {

    @Test
    public void shouldWrapAByteArray() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        byte[] array = {12, 43, 123, 0, 4, 0, 0, 123, 127, -5, -23};
        fbt.wrap(array);
        assertEquals(array.length, fbt.getBufferArray().length);
        assertEquals(array, fbt.getBufferArray());
    }

    @Test
    public void whenCallGetBufferArrayShouldReturnNullIfNotWrappedArray() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        assertNull(fbt.getBufferArray());
    }

    @Test
    public void whenNoArrayWrappedBufferShouldBeEmpty() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        assertTrue(fbt.isBufferEmpty());
    }

    @Test
    public void whenArrayWrappedBufferShouldNotBeEmpty() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.wrap(new byte[]{1, -89, 3, 0, 45});
        assertFalse(fbt.isBufferEmpty());
    }

    @Test
    public void whenCallGetByteShouldTakeAByteArrayWithAByteAndTranslateItToValue() {
        ByteBuffer bb = ByteBuffer.allocate(1).put((byte) 56);

        byte res = getInt8(bb.array(), null).getValue();
        assertEquals((byte) 56, res);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetByteShouldTakeAByteArrayWithAnIntegerAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(5555555);
        getInt16(bb.array(), null);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetByteShouldTakeAByteArrayWithAByteAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(1).put((byte) -65);
        getInt16(bb.array(), null);
    }

    @Test
    public void whenCallGetShortShouldTakeAByteArrayWithAShortAndTranslateItToValue() {
        ByteBuffer bb = ByteBuffer.allocate(2).putShort((short) 14567);

        short res = getInt16(bb.array(), null).getWrap();
        assertEquals(14567, res);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetShortShouldTakeAByteArrayWithAnIntegerAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(5555555);
        getInt16(bb.array(), null);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetShortShouldTakeAByteArrayWithAByteAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(1).put((byte) -65);
        getInt16(bb.array(), null);
    }

    @Test
    public void whenCallGetIntShouldTakeAByteArrayWithAnIntAndTranslateItToValue() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(14567456);

        int res = getInt32(bb.array(), null).getWrap();
        assertEquals(14567456, res);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetIntShouldTakeAByteArrayWithALongAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(8).putLong(555555598);
        getInt32(bb.array(), null);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetIntShouldTakeAByteArrayWithAByteAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(2).putShort((short) 2455);
        getInt32(bb.array(), null);
    }

    @Test
    public void whenCallGetLongShouldTakeAByteArrayWithALongAndTranslateItToValue() {
        ByteBuffer bb = ByteBuffer.allocate(8).putLong(1456745643);

        long res = getInt64(bb.array(), null).getWrap();
        assertEquals(1456745643, res);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetLongShouldTakeAByteArrayWithNotValidArrayAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(10).putLong(555555598);
        getInt64(bb.array(), null);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetLongShouldTakeAByteArrayWithAByteAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(245587346);
        getInt64(bb.array(), null);
    }

    @Test
    public void whenCallGetFloatShouldTakeAByteArrayWithALongAndTranslateItToValue() {
        ByteBuffer bb = ByteBuffer.allocate(4).putFloat(3.14f);
        float res = getFloat(bb.array(), null).getWrap();
        assertEquals(3.14f, res, 0.001);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetFloatShouldTakeAByteArrayWithNotValidArrayAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(2).putShort((short) 5598);
        getFloat(bb.array(), null);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetFloatShouldTakeAByteArrayWithAByteAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(8).putDouble(245587346d);
        getFloat(bb.array(), null);
    }

    @Test
    public void whenCallGetDoubleShouldTakeAByteArrayWithALongAndTranslateItToValue() {
        ByteBuffer bb = ByteBuffer.allocate(8).putDouble(33456.1455656566d);
        double res = getDouble(bb.array(), null).getWrap();
        assertEquals(33456.1455656566d, res, 0.001);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetDoubleShouldTakeAByteArrayWithNotValidArrayAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(10).putDouble(33456.1455656566d);
        getDouble(bb.array(), null);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetDoubleShouldTakeAByteArrayWithAByteAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(2455873);
        getDouble(bb.array(), null);
    }

    @Test
    public void whenCallGetUInt8ShouldTakeAByteArrayWithAByteAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(1).put((byte) 123);
        short res = getUInt8(bb.array(), null).getWrap();
        assertEquals(123, res);
    }

    @Test
    public void whenCallGetUInt8ShouldTakeAByteArrayWithANegativeByteVaueAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(1).put((byte) -123);
        bb.position(0);
        short int8Res = (short) Byte.toUnsignedInt(bb.get());
        short res = getUInt8(bb.array(), null).getWrap();
        assertEquals(int8Res, res);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetUInt8ShouldTakeAByteArrayWithNotValidArrayAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(10).putDouble(33456.1455656566d);
        getUInt8(bb.array(), null);
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetUInt8ShouldTakeAByteArrayWithAByteAndThrowException() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(2455873);
        getDouble(bb.array(), null);
    }

    @Test
    public void whenCallGetUInt16ShouldTakeAByteArrayWithAShortAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(2).putShort((short) 25900);
        int res = getUInt16(bb.array(), null).getWrap();
        assertEquals(25900, res);
    }

    @Test
    public void whenCallGetUInt16ShouldTakeAByteArrayWithANegativeShortAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(2).putShort((short) -25900);
        int res = getUInt16(bb.array(), null).getWrap();
        assertEquals(39636, res);
    }

    @Test
    public void whenCallGetUInt32ShouldTakeAByteArrayWithAnIntegerAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(654654645);
        long res = getUInt32(bb.array(), null).getWrap();
        assertEquals(654654645, res);
    }

    @Test
    public void whenCallGetUInt32ShouldTakeAByteArrayWithANegativeIntegerAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(4).putInt(-56484412);
        bb.position(0);
        long uint32Val = Integer.toUnsignedLong(bb.getInt());

        long res = getUInt32(bb.array(), null).getWrap();
        assertEquals(uint32Val, res);
    }

    @Test
    public void whenCallGetUInt64ShouldTakeAByteArrayWithALongAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(8).putLong(19587415636985L);
        BigInteger res = getUInt64(bb.array(), null).getWrap();
        assertEquals(new BigInteger("19587415636985"), res);
    }

    @Test
    public void whenCallGetUInt64ShouldTakeAByteArrayWithANegativeLongAndTranslateItToUnsignedValue() {
        ByteBuffer bb = ByteBuffer.allocate(8).putLong(-5649872156484412L);
        bb.position(0);
        BigInteger uint64Val = new BigInteger(Long.toUnsignedString(bb.getLong()));

        BigInteger res = getUInt64(bb.array(), null).getWrap();
        assertEquals(uint64Val, res);
    }

    @Test(expected = InvalidParameterException.class)
    public void whenCallGetNumberWithNullTypeItShouldThrowEsception() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.getNumber(null, 0);
    }

    @Test(expected = BufferEmptyException.class)
    public void whenCallGetNumberWithNoWrappedArrayShouldThrowEsception() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.getNumber(PrimitiveType.DOUBLE, 0);
    }

    @Test(expected = BufferEmptyException.class)
    public void whenCallGetNumberWithWrappedEmptyArrayShouldThrowEsception() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.wrap(new byte[]{});
        fbt.getNumber(PrimitiveType.DOUBLE, 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void whenWrappingArrayShouldBeAbleToReturnNumber() {
        ByteBuffer bb = ByteBuffer.allocate(30)
                .putLong(907856) // from 0
                .putInt(8734) // from 8
                .put((byte) 123) // from 12
                .putShort((short) 8739) // from 13
                .putDouble(87234.987d) // from 15
                .putFloat(786.29183f) // from 23
                .put((byte) -123) // from 27
                .putShort((short) -25900); // from 28


        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.wrap(bb.array());

        PrimitiveValue<Number> nv = fbt.getNumber(PrimitiveType.DOUBLE, 15);
        assertEquals(87234.987d, nv.getValue().doubleValue(), 0.001);
        nv = fbt.getNumber(PrimitiveType.FLOAT, 23);
        assertEquals(786.29183f, nv.getValue().floatValue(), 0.001);
        nv = fbt.getNumber(PrimitiveType.INT64, 0);
        assertEquals(907856, nv.getValue().longValue());
        nv = fbt.getNumber(INT32, 8);
        assertEquals(8734, nv.getValue().intValue());
        nv = fbt.getNumber(PrimitiveType.INT8, 12);
        assertEquals(123, nv.getValue().byteValue());
        nv = fbt.getNumber(PrimitiveType.INT16, 13);
        assertEquals(8739, nv.getValue().shortValue());
        nv = fbt.getNumber(PrimitiveType.UINT8, 27);
        assertEquals(133, nv.getValue().shortValue());
        nv = fbt.getNumber(PrimitiveType.UINT16, 28);
        assertEquals(39636, nv.getValue().intValue());
    }

    @Test
    public void whenWrappingArrayShouldBeAbleToReturnNumbersWithoutPassedPositionIndices() {
        ByteBuffer bb = ByteBuffer.allocate(30)
                .putLong(907856) // from 0
                .putInt(8734) // from 8
                .put((byte) 123) // from 12
                .putShort((short) 8739) // from 13
                .putDouble(87234.987d) // from 15
                .putFloat(786.29183f) // from 23
                .put((byte) -123) // from 27
                .putShort((short) -25900); // from 28


        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.wrap(bb.array());

        PrimitiveValue<Number> nv = fbt.getNumber(PrimitiveType.INT64);
        assertEquals(907856, nv.getValue().longValue());
        nv = fbt.getNumber(INT32);
        assertEquals(8734, nv.getValue().intValue());
        nv = fbt.getNumber(PrimitiveType.INT8);
        assertEquals(123, nv.getValue().byteValue());
        nv = fbt.getNumber(PrimitiveType.INT16);
        assertEquals(8739, nv.getValue().shortValue());
        nv = fbt.getNumber(PrimitiveType.DOUBLE);
        assertEquals(87234.987d, nv.getValue().doubleValue(), 0.001);
        nv = fbt.getNumber(PrimitiveType.FLOAT);
        assertEquals(786.29183f, nv.getValue().floatValue(), 0.001);
        nv = fbt.getNumber(PrimitiveType.UINT8);
        assertEquals(133, nv.getValue().shortValue());
        assertTrue(fbt.hasMoreToRead());
        nv = fbt.getNumber(PrimitiveType.UINT16);
        assertFalse(fbt.hasMoreToRead());
        assertEquals(39636, nv.getValue().intValue());
    }

    @Test
    public void whenCallSetBufferPositionIndexItShouldSetTheValue() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.setBufferPositionIndex(14);
        assertEquals(14, fbt.getBufferPositionIndex());
    }

    @Test
    public void whenCallResetBufferPositionIndexItShouldSetItToZero() {
        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.setBufferPositionIndex(14);
        assertEquals(14, fbt.getBufferPositionIndex());
        fbt.resetBufferPositionIndex();
        assertEquals(0, fbt.getBufferPositionIndex());
    }

    @Test
    public void whenCallGetStringItShouldReturnStringWithLength() {
        String strSample = " The quick brown fox jumped... over the doggy;  ";
        byte[] charSemBytes = strSample.getBytes();

        ByteBuffer bb = ByteBuffer.allocate(charSemBytes.length + 8);
        bb.putInt(8742).put(charSemBytes).putInt(923874);

        String result = FromBytesTranslator.getString(bb.array(), 4, 4 + charSemBytes.length);
        assertEquals(strSample, result);
    }

    @Test
    public void whenCallGetStringWIthoutStartIndexItShouldReturnStringWithLength() {
        String strSample = "My name is John Doe, nice to meet you!  ";
        byte[] charSemBytes = strSample.getBytes();

        ByteBuffer bb = ByteBuffer.allocate(charSemBytes.length + 8);
        bb.putInt(8742).put(charSemBytes).putInt(923874);

        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.wrap(bb.array());

        fbt.getNumber(INT32);
        String result = fbt.getString(charSemBytes.length).getValue();
        assertEquals(strSample, result);
    }

    @Test
    public void whenPassedEmptyOrNullDataArrayItShouldReturnBlank() {
        String result = FromBytesTranslator.getString(null, 4, 5);
        assertEquals("", result);
        result = FromBytesTranslator.getString(new byte[]{}, 4, 5);
        assertEquals("", result);
    }

    @Test
    public void whenCallGetTemplatedValuesWithCorrectTemplateItShouldFillValuesInTemplate() {
        PrimitiveValue[] template = new PrimitiveValue[]{
                new NumberValue(INT32),
                new NumberValue(INT64),
                new NumberValue(DOUBLE),
                new NumberValue(INT16),
                new StringValue(12),
                new NumberValue(UINT16)
        };

        ByteBuffer bb = ByteBuffer.allocate(36)
                .putInt(2478490)
                .putLong(-98090880L)
                .putDouble(89.0983)
                .putShort((short) 2837)
                .put("How are you?".getBytes())
                .putShort((short) -25900);
        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.wrap(bb.array());

        fbt.processTemplatedValues(template);
        assertEquals(2478490, template[0].getValue());
        assertEquals(-98090880L, template[1].getValue());
        assertEquals(89.0983, template[2].getValue());
        assertEquals((short) 2837, template[3].getValue());
        assertEquals("How are you?", template[4].getValue());
        assertEquals(39636, template[5].getValue());
    }

    @Test(expected = UnexpectedArrayLengthException.class)
    public void whenCallGetTemplatedValuesWithIncorrectTemplateItShouldThrowException() {
        PrimitiveValue[] template = new PrimitiveValue[]{
                new NumberValue(INT32),
                new NumberValue(INT64),
                new NumberValue(DOUBLE),
                new NumberValue(INT16),
                new StringValue(12),
                new NumberValue(UINT16)
        };

        ByteBuffer bb = ByteBuffer.allocate(22)
                .putInt(2478490)
                .putLong(-98090880L)
                .putDouble(89.0983)
                .putShort((short) 2837);

        FromBytesTranslator fbt = new FromBytesTranslator();
        fbt.wrap(bb.array());

        fbt.processTemplatedValues(template);
    }
}