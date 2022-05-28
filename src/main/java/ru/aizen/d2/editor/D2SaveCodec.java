package ru.aizen.d2.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.BitSet;

public class D2SaveCodec {

    public static final Logger LOG = LoggerFactory.getLogger(D2SaveCodec.class);

    private static final byte[] SIGNATURE = new byte[]{85, -86, 85, -86}; // 0x55AA55AA
    private static final int CHARACTER_NAME_MAX_LENGTH = 15;

    D2Save decode(byte[] content) {
        System.out.println(Arrays.toString(content));
        LOG.info("Diablo II game save file size is {} by loaded content", content.length);
        ByteBuffer buffer = ByteBuffer.wrap(content)
                //all values larger than a byte are stored in x86 little-endian order
                .order(ByteOrder.LITTLE_ENDIAN);

        byte[] signature = new byte[4];
        buffer.get(signature);
        checkSignature(signature);

        int gameVersion = buffer.getInt();
        GameVersion.checkVersion(gameVersion);

        LOG.info("Skip 4 byte of actual file size {} bytes", buffer.getInt());
        LOG.info("Skip 4 byte of file checksum {}", buffer.getInt());
        LOG.info("Active hand value {}. Swap weapon", buffer.get()); // integer or byte?

        buffer.position(buffer.position() + 19); // skip 19 bytes
        BitSet status = BitSet.valueOf(new byte[]{buffer.get()});
        LOG.info("Bits of status value is {}", status);

        buffer.position(buffer.position() + 3); // skip 3 bytes
        byte characterClassId = buffer.get();
        LOG.info("Character class ID value is {}", characterClassId);

        buffer.position(buffer.position() + 7); // skip 7 bytes
        LocalDateTime gameSaveTime = LocalDateTime.ofEpochSecond(buffer.getInt(), 0, ZoneOffset.UTC);
        LOG.info("Last game saved at {}", gameSaveTime);

        buffer.position(buffer.position() + 212); // skip 220 bytes

        byte[] nameInBytes = new byte[CHARACTER_NAME_MAX_LENGTH];
        buffer.get(nameInBytes);
        String characterName = new String(nameInBytes).trim();
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
