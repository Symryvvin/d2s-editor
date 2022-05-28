package ru.aizen.d2.editor;


import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class D2SaveCodecTest {

    @Test
    void successfullyDecodeDiablo2ResurrectedSave() {
        final String characterName = "Character";
        final int barbarianClassId = 4;
        final BitSet status = new BitSet(8);
        status.set(5);
        status.set(2);
        D2Save expectedD2Save =
                new D2Save.Builder(characterName, barbarianClassId, status)
                        .build();

        final String resourceName = "/Character.d2s";
        D2SaveCodec d2SaveCodec = new D2SaveCodec();


        D2Save d2Save = d2SaveCodec.decode(D2SaveLoader.load(resourceName));


        assertThat(d2Save, equalTo(expectedD2Save));
    }


}