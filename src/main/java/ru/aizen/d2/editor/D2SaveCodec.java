package ru.aizen.d2.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.BitSet;

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

        LOG.info("Skip 4 byte of actual file size {} bytes", reader.readInt());
        LOG.info("Skip 4 byte of file checksum {}", reader.readInt());
        LOG.info("Active hand value {}. Swap weapon", reader.readByte());

        reader.skip(19);
        BitSet status = BitSet.valueOf(new byte[]{reader.readByte()});
        LOG.info("Bits of status value is {}", status);

        reader.skip(3);
        byte characterClassId = reader.readByte();
        LOG.info("Character class ID value is {}", characterClassId);

        reader.skip(7);
        LOG.info("Last game saved at {}", reader.readLocalDateTime());

        reader.skip(212);

        String characterName = reader.readString(CHARACTER_NAME_MAX_LENGTH);
        LOG.info("Character name is '{}'", characterName);

        return new D2Save.Builder(characterName, characterClassId, status)
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
