package gameEngine.io.serialization.types;

import java.util.ArrayList;
import java.util.List;

import static gameEngine.io.serialization.SerializationHelper.*;
import static gameEngine.io.serialization.types.ContainerType.OBJECT;

public class TypeObject extends BaseType {

	public static final byte CONTAINER_TYPE = OBJECT;
	private short fieldCount;
	public final List<PrimitiveType> fields = new ArrayList<>();
	private short stringCount;
	public final List<TypeString> strings = new ArrayList<>();
	private short arrayCount;
	public final List<PrimitiveArray> arrays = new ArrayList<>();

	private TypeObject() {
	}

	public TypeObject(String name) {
		size += 1 + 2 + 2 + 2;
		setName(name);
	}

	public void addField(PrimitiveType field) {
		fields.add(field);
		size += field.getSize();

		fieldCount = (short) fields.size();
	}

	public void addString(TypeString string) {
		strings.add(string);
		size += string.getSize();

		stringCount = (short) strings.size();
	}

	public void addArray(PrimitiveArray array) {
		arrays.add(array);
		size += array.getSize();

		arrayCount = (short) arrays.size();
	}

	public int getSize() {
		return size + name.length + 100;
	}

	public PrimitiveType findField(String name) {
		for (PrimitiveType field : fields) {
			if (field.getName().equals(name))
				return field;
		}
		return null;
	}

	public TypeString findString(String name) {
		for (TypeString string : strings) {
			if (string.getName().equals(name))
				return string;
		}
		return null;
	}

	public PrimitiveArray findArray(String name) {
		for (PrimitiveArray array : arrays) {
			if (array.getName().equals(name))
				return array;
		}
		return null;
	}

	public int getBytes(byte[] dest, int pointer) {
		pointer = writeBytes(dest, pointer, CONTAINER_TYPE);
		pointer = writeBytes(dest, pointer, nameLength);
		pointer = writeBytes(dest, pointer, name);
		pointer = writeBytes(dest, pointer, size);

		pointer = writeBytes(dest, pointer, fieldCount);
		for (PrimitiveType field : fields)
			pointer = field.getBytes(dest, pointer);

		pointer = writeBytes(dest, pointer, stringCount);
		for (TypeString string : strings)
			pointer = string.getBytes(dest, pointer);

		pointer = writeBytes(dest, pointer, arrayCount);
		for (PrimitiveArray array : arrays)
			pointer = array.getBytes(dest, pointer);

		return pointer;
	}

	public static TypeObject Deserialize(byte[] data, int pointer) {
		byte containerType = data[pointer++];

		TypeObject result = new TypeObject();
		result.nameLength = readShort(data, pointer);
		pointer += 2;
		result.name = readString(data, pointer, result.nameLength).getBytes();
		pointer += result.nameLength;

		result.size = readInt(data, pointer);
		pointer += 4;

		result.fieldCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.fieldCount; i++) {
			PrimitiveType field = PrimitiveType.Deserialize(data, pointer);
			result.fields.add(field);
			pointer += field.getSize();
		}

		result.stringCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.stringCount; i++) {
			TypeString string = TypeString.Deserialize(data, pointer);
			result.strings.add(string);
			pointer += string.getSize();
		}

		result.arrayCount = readShort(data, pointer);
		pointer += 2;

		for (int i = 0; i < result.arrayCount; i++) {
			PrimitiveArray array = PrimitiveArray.Deserialize(data, pointer);
			result.arrays.add(array);
			pointer += array.getSize();
		}
		return result;
	}
}
