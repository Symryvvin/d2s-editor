package ru.aizen.d2.editor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SimpleD2SaveReader {

    private final ByteBuffer byteBuffer;

    public SimpleD2SaveReader(byte[] content) {
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


}
