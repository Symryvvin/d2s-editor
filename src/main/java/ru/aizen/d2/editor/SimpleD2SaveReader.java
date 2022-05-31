package ru.aizen.d2.editor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SimpleD2SaveReader {

    private final ByteBuffer byteBuffer;
    private final byte[] content;

    public SimpleD2SaveReader(byte[] content) {
        this.content = content;
        this.byteBuffer = ByteBuffer.wrap(content)
                //all values larger than a byte are stored in x86 little-endian order
                .order(ByteOrder.LITTLE_ENDIAN);
    }

    public void skip(int value) {
        byteBuffer.position(byteBuffer.position() + value);
    }

    public byte[] readByteArray(int length) {
        byte[] array = new byte[length];
        byteBuffer.get(array);
        return array;
    }

    public byte readByte() {
        return byteBuffer.get();
    }

    public short readShort() {
        return byteBuffer.getShort();
    }

    public int readInt() {
        return byteBuffer.getInt();
    }

    public String readString(int length) {
        byte[] nameInBytes = new byte[length];
        byteBuffer.get(nameInBytes);
        return new String(nameInBytes).trim();
    }

    public LocalDateTime readLocalDateTime() {
        return LocalDateTime.ofEpochSecond(byteBuffer.getInt(), 0, ZoneOffset.UTC);
    }

    public byte[] readToEnd() {
        byte[] array = new byte[content.length - byteBuffer.position()];
        byteBuffer.get(array);
        return array;
    }

    public static int indexOf(byte[] array, byte[] target) {
        if (target.length == 0) {
            return 0;
        }

        outer:
        for (int i = 0; i < array.length - target.length + 1; i++) {
            for (int j = 0; j < target.length; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }
}
