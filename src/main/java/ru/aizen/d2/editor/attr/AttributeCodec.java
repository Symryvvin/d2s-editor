package ru.aizen.d2.editor.attr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

public final class AttributeCodec {

	private static final Logger LOG = LoggerFactory.getLogger(AttributeCodec.class);

	private static final int ID_BINARY_SIZE = 9;
	private static final int STOP_CODE = 0x01FF;

	private static final List<AttributeSpec> attrSpecs;

	static {
		try (FileReader fileReader = new FileReader("spec/attributes.json")) {
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(AttributeSpec.class, new AttributeDeserializer())
					.create();
			attrSpecs = gson.fromJson(fileReader, new TypeToken<List<AttributeSpec>>() {
			}.getType());
		} catch (IOException e) {
			throw new RuntimeException("Attribute specification file not found");
		}
	}

	public static List<Attribute> decode(byte[] data) {
		List<Attribute> result = new LinkedList<>();
		BitSet bits = BitSet.valueOf(data);

		int currentPositionInBitSequence = 0;

		while (true) {
			int fromIndex = currentPositionInBitSequence;
			int toIndex = fromIndex + ID_BINARY_SIZE;

			int attributeId = bitsAsInt(bits.get(fromIndex, toIndex));
			if (attributeId == STOP_CODE) {
				break;
			}

			Optional<AttributeSpec> optionalAttr = attrSpecs.stream()
					.filter(attr -> attr.id() == attributeId)
					.findFirst();

			if (optionalAttr.isPresent()) {
				AttributeSpec attribute = optionalAttr.get();

				fromIndex = toIndex;
				toIndex = fromIndex + attribute.numberOfBits();
				long attributeValue = bitsAsLong(bits.get(fromIndex, toIndex)) / attribute.scaleCoefficient();

				result.add(new Attribute(attribute.name(), attributeValue));
				LOG.info("decoded attribute '{}' - {}", attribute.name(), attributeValue);

				currentPositionInBitSequence = toIndex;
			}
		}

		return result;
	}

	private static int bitsAsInt(BitSet bitSet) {
		return ByteBuffer.allocate(Integer.BYTES)
				.order(ByteOrder.LITTLE_ENDIAN)
				.put(bitSet.toByteArray())
				.rewind()
				.getInt();
	}

	private static long bitsAsLong(BitSet bitSet) {
		return ByteBuffer.allocate(Long.BYTES)
				.order(ByteOrder.LITTLE_ENDIAN)
				.put(bitSet.toByteArray())
				.rewind()
				.getInt();
	}

}
