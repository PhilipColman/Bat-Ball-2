package gameEngine.io.serialization.types;

import static gameEngine.io.serialization.SerializationHelper.*;

public class PrimitiveType extends BaseType {

	public static final byte CONTAINER_TYPE = ContainerType.FIELD;
	public byte type;
	public byte[] data;
	
	private PrimitiveType() {
	}
	
	public byte getByte() {
		return data[0];
	}
	
	public short getShort() {
		return readShort(data, 0);
	}
	
	public char getChar() {
		return readChar(data, 0);
	}
	
	public int getInt() {
		return readInt(data, 0);
	}
	
	public long getLong() {
		return readLong(data, 0);
	}
	
	public double getDouble() {
		return readDouble(data, 0);
	}
	
	public float getFloat() {
		return readFloat(data, 0);
	}
	
	public boolean getBoolean() {
		return readBoolean(data, 0);
	}
	
	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, type);
		pointer = writeBytes(dest, pointer, data);
		return pointer;
	}
	
	public int getSize() {
		assert(data.length == Type.getSize(type));
		return 1 + 2 + name.length + 1 + data.length;
	}

	public static PrimitiveType Byte(String name, byte value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.BYTE;
		field.data = new byte[Type.getSize(Type.BYTE)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	public static PrimitiveType Short(String name, short value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.SHORT;
		field.data = new byte[Type.getSize(Type.SHORT)];
		writeBytes(field.data, 0, value);
		return field;
	}

	public static PrimitiveType Char(String name, char value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.CHAR;
		field.data = new byte[Type.getSize(Type.CHAR)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	public static PrimitiveType Integer(String name, int value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.INTEGER;
		field.data = new byte[Type.getSize(Type.INTEGER)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	public static PrimitiveType Long(String name, long value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.LONG;
		field.data = new byte[Type.getSize(Type.LONG)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	public static PrimitiveType Float(String name, float value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.FLOAT;
		field.data = new byte[Type.getSize(Type.FLOAT)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	public static PrimitiveType Double(String name, double value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.DOUBLE;
		field.data = new byte[Type.getSize(Type.DOUBLE)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	public static PrimitiveType Boolean(String name, boolean value) {
		PrimitiveType field = new PrimitiveType();
		field.setName(name);
		field.type = Type.BOOLEAN;
		field.data = new byte[Type.getSize(Type.BOOLEAN)];
		writeBytes(field.data, 0, value);
		return field;
	}
	
	public static PrimitiveType Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];
		assert(containerType == CONTAINER_TYPE);
		
		PrimitiveType result = new PrimitiveType();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;
		
		result.type = data[pointer++];
		
		result.data = new byte[Type.getSize(result.type)];
		readBytes(data, pointer, result.data);
        return result;
	}
	
}
