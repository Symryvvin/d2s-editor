package ru.aizen.d2.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aizen.d2.editor.attr.AttributeCodec;

import java.util.Arrays;

public class D2SaveCodec {

    public static final Logger LOG = LoggerFactory.getLogger(D2SaveCodec.class);

    private static final byte[] SIGNATURE = new byte[]{85, -86, 85, -86}; // 0x55AA55AA
    private static final int CHARACTER_NAME_MAX_LENGTH = 15;

    D2Save decode(byte[] content) {
        var reader = new SimpleD2SaveReader(content);
        LOG.info("Diablo II game save file size is {} bytes of loaded content", content.length);

        byte[] signature = reader.readByteArray(SIGNATURE.length);
        checkSignature(signature);

        int gameVersion = reader.readInt();
        LOG.info("Game version value is {}", gameVersion);
        GameVersion.checkVersion(gameVersion);

        LOG.info("File size {} bytes, checksum = {}", reader.readInt(), reader.readByteArray(4));
        LOG.info("Active weapons '{}'", reader.readByte() == 0 ? "I" : "II");

        reader.skip(19);
        Status status = Status.from(reader.readByte());
        LOG.info("{}", status);
        LOG.info("Progression {}", reader.readByte());

        reader.skip(2);
        CharacterClass characterClass = CharacterClass.byId(reader.readByte());
        LOG.info("Character class is {}", characterClass.name());

        LOG.info("Unknown value in hex [0x10, 0x1E], in bytes {}", reader.readByteArray(2));
        LOG.info("Character level in game menu {}", reader.readByte());

        reader.skip(4);
        LOG.info("Last game saved at {}", reader.readLocalDateTime());

        reader.skip(Integer.BYTES);
        LOG.info("Skip [0xFF, 0xFF, 0xFF, 0xFF]");

        LOG.info("Hotkeys data {}", reader.readByteArray(16 * Integer.BYTES));
        LOG.info("Mouse hotkey I and II weapons{}", reader.readByteArray(4 * Integer.BYTES));

        reader.skip(80);
        LOG.info("Character looks like in game menu 4 * 12 byte array {}", reader.readByteArray(4 * 12));

        String characterName = reader.readString(CHARACTER_NAME_MAX_LENGTH);
        LOG.info("Character name is '{}'", characterName);

        LOG.info("Skip unknown data {}", reader.readByteArray(56));
        LOG.info("'Woo!' [0x57, 0x6F, 0x6F, 0x21] quest data identifier {}", reader.readByteArray(4));
        LOG.info("'Unknown always [0x06, 0x00, 0x00, 0x00, 0x2A, 0x01] data {}", reader.readByteArray(6));

        reader.skip(18 * 16); //quests

        LOG.info("'WS' [0x57, 0x53] Waypoints {}, and skip 6 bytes {}", reader.readByteArray(2), reader.readByteArray(6));
        LOG.info("Waypoint data {}", reader.readByteArray(3 * 24));

        LOG.info("' w4 ' NPC introductions identifier {} and data {}", reader.readByteArray(4), reader.readByteArray(8 * 3 * 2));

        int indexOfAttributeIdentifier = SimpleD2SaveReader.indexOf(content, new byte[]{103, 102});
        LOG.info("Attributes identifier [0x63, 0x66] {}, index is {}", reader.readByteArray(2), indexOfAttributeIdentifier);
        int indexOfSkillsIdentifier = SimpleD2SaveReader.indexOf(content, new byte[]{105, 102});
        LOG.info("Skills identifier [0x69, 0x66] index is {}", indexOfSkillsIdentifier);
        AttributeCodec.decode(reader.readByteArray(indexOfSkillsIdentifier - 2 - indexOfAttributeIdentifier));
        LOG.info("Skills identifier [0x69, 0x66] {} and data {}", reader.readByteArray(2), reader.readByteArray(30));

        LOG.info("Items identifier [0x4A,0x4D] {} and data {}", reader.readByteArray(2), reader.readToEnd());

        return new D2Save.Builder(characterName, characterClass, status)
                .build();
    }

    private void checkSignature(byte[] signature) {
        LOG.info("Signature of loaded file is {}", signature);
        if (!Arrays.equals(signature, SIGNATURE)) {
            throw new RuntimeException("Invalid Diablo II save game file signature");
        }
    }

    byte[] encode(D2Save save) {
        return null;
    }
}
