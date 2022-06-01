package ru.aizen.d2.editor.attr;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class AttributeCodecTest {

	@Test
	void successfullyDecodeAttributesFromByteArray() {
		final byte[] data = Hex.decode("003C08A080000A066440A0800201060072C001801C80006001240058000A3093C002802EC040D020590000C08100008007030000FE03");
		final List<Attribute> expectedAttributes = new LinkedList<>();
		expectedAttributes.add(new Attribute("strength", 30));
		expectedAttributes.add(new Attribute("energy", 10));
		expectedAttributes.add(new Attribute("dexterity", 20));
		expectedAttributes.add(new Attribute("vitality", 25));
		expectedAttributes.add(new Attribute("remaining stat points", 5));
		expectedAttributes.add(new Attribute("remaining skill points", 1));
		expectedAttributes.add(new Attribute("current life", 57));
		expectedAttributes.add(new Attribute("life", 57));
		expectedAttributes.add(new Attribute("current mana", 11));
		expectedAttributes.add(new Attribute("mana", 11));
		expectedAttributes.add(new Attribute("current stamina", 73));
		expectedAttributes.add(new Attribute("stamina", 93));
		expectedAttributes.add(new Attribute("level", 2));
		expectedAttributes.add(new Attribute("experience", 713));
		expectedAttributes.add(new Attribute("gold", 2));
		expectedAttributes.add(new Attribute("stash gold", 3));


		List<Attribute> attributes = AttributeCodec.decode(data);


		assertThat(attributes, equalTo(expectedAttributes));
	}

	private static final class Hex {

		public static byte[] decode(String hex) {
			hex = hex.replace("\t", "");
			char[] data = hex.toCharArray();
			int len = data.length;

			if ((len & 1) != 0) {
				throw new RuntimeException("Odd number of characters.");
			}

			byte[] out = new byte[len >> 1];
			int i = 0;

			for (int j = 0; j < len; ++i) {
				int f = Character.digit(data[j], 16) << 4;
				++j;
				f |= Character.digit(data[j], 16);
				++j;
				out[i] = (byte) (f & 255);
			}

			return out;
		}

	}

}