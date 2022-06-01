package ru.aizen.d2.editor;


import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class D2SaveCodecTest {

	@Test
	void successfullyDecodeDiablo2ResurrectedSave() {
		final String characterName = "Character";
		final Status status = new Status();
		status.setExpansion();
		status.setHardcore();
		D2Save expectedD2Save =
				new D2Save.Builder(characterName, CharacterClass.BARBARIAN, status)
						.build();

		final String resourceName = "/Character.d2s";
		D2SaveCodec d2SaveCodec = new D2SaveCodec();


		D2Save d2Save = d2SaveCodec.decode(D2SaveLoader.load(resourceName));


		assertThat(d2Save, equalTo(expectedD2Save));
	}


}