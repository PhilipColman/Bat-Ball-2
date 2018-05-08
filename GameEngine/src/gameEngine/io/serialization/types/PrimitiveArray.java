package gameEngine.io.serialization.types;

import static gameEngine.io.serialization.SerializationHelper.*;
import static gameEngine.io.serialization.types.Type.*;

public class PrimitiveArray extends BaseType {

	public static final byte CONTAINER_TYPE = ContainerType.ARRAY;
	public byte type;
	public int count;
	public byte[] data;
	
	private short[] shortData;
	private char[] charData;
	private int[] intData;
	private long[] longData;
	private float[] floatData;
	private double[] doubleData;
	private boolean[] booleanData;
	
	private PrimitiveArray() {
		size += 1 + 1 + 4;
	}
	
	private void updateSize() {
		size += getDataSize();
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);
		pointer = writeBytes(dest, pointer, type);
		pointer = writeBytes(dest, pointer, count);
		
		switch(type) {
		case BYTE:
			pointer = writeBytes(dest, pointer, data);
			break;
		case SHORT:
			pointer = writeBytes(dest, pointer, shortData);
			break;
		case CHAR:
			pointer = writeBytes(dest, pointer, charData);
			break;
		case INTEGER:
			pointer = writeBytes(dest, pointer, intData);
			break;
		case LONG:
			pointer = writeBytes(dest, pointer, longData);
			break;
		case FLOAT:
			pointer = writeBytes(dest, pointer, floatData);
			break;
		case DOUBLE:
			pointer = writeBytes(dest, pointer, doubleData);
			break;
		case BOOLEAN:
			pointer = writeBytes(dest, pointer, booleanData);
			break;
		}
		return pointer;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getDataSize() {
		switch(type) {
		case BYTE:		return data.length * Type.getSize(BYTE);
		case SHORT:	return shortData.length * Type.getSize(SHORT);
		case CHAR:		return charData.length * Type.getSize(CHAR);
		case INTEGER:	return intData.length * Type.getSize(INTEGER);
		case LONG:		return longData.length * Type.getSize(LONG);
		case FLOAT:	return floatData.length * Type.getSize(FLOAT);
		case DOUBLE:	return doubleData.length * Type.getSize(DOUBLE);
		case BOOLEAN:	return booleanData.length * Type.getSize(BOOLEAN);
		}
		return 0;
	}

	public static PrimitiveArray Byte(String name, byte[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = BYTE;
		array.count = data.length;
		array.data = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Short(String name, short[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = SHORT;
		array.count = data.length;
		array.shortData = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Char(String name, char[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = CHAR;
		array.count = data.length;
		array.charData = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Integer(String name, int[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = INTEGER;
		array.count = data.length;
		array.intData = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Long(String name, long[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = LONG;
		array.count = data.length;
		array.longData = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Float(String name, float[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = FLOAT;
		array.count = data.length;
		array.floatData = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Double(String name, double[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = DOUBLE;
		array.count = data.length;
		array.doubleData = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Boolean(String name, boolean[] data) {
		PrimitiveArray array = new PrimitiveArray();
		array.setName(name);
		array.type = BOOLEAN;
		array.count = data.length;
		array.booleanData = data;
		array.updateSize();
		return array;
	}
	
	public static PrimitiveArray Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		PrimitiveArray result = new PrimitiveArray();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.size = readInt(data, pointer);
		pointer += 4;
		
		result.type = data[pointer++];
		
		result.count = readInt(data, pointer);
		pointer += 4;
		
		switch(result.type) {
		case BYTE:
			result.data = new byte[result.count];
			readBytes(data, pointer, result.data);
			break;
		case SHORT:
			result.shortData = new short[result.count];
			readShorts(data, pointer, result.shortData);
			break;
		case CHAR:
			result.charData = new char[result.count];
			readChars(data, pointer, result.charData);
			break;
		case INTEGER:
			result.intData = new int[result.count];
			readInts(data, pointer, result.intData);
			break;
		case LONG:
			result.longData = new long[result.count];
			readLongs(data, pointer, result.longData);
			break;
		case FLOAT:
			result.floatData = new float[result.count];
			readFloats(data, pointer, result.floatData);
			break;
		case DOUBLE:
			result.doubleData = new double[result.count];
			readDoubles(data, pointer, result.doubleData);
			break;
		case BOOLEAN:
			result.booleanData = new boolean[result.count];
			readBooleans(data, pointer, result.booleanData);
			break;
		}

		return result;
	}

	public byte[] getData() {
		return data;
	}

	public short[] getShortData() {
		return shortData;
	}

	public char[] getCharData() {
		return charData;
	}

	public int[] getIntData() {
		return intData;
	}

	public long[] getLongData() {
		return longData;
	}

	public float[] getFloatData() {
		return floatData;
	}

	public double[] getDoubleData() {
		return doubleData;
	}

	public boolean[] getBooleanData() {
		return booleanData;
	}
}
