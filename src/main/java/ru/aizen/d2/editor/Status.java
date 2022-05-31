package ru.aizen.d2.editor;

import java.util.BitSet;
import java.util.Objects;

public class Status {

    private static final int IS_EXPANSION_BIT_POSITION = 5;
    private static final int IS_DIED_BIT_POSITION = 3;
    private static final int IS_HARDCODE_BIT_POSITION = 2;

    private boolean expansion;
    private boolean died;
    private boolean hardcore;
    private final boolean ladder;

    public Status() {
        this.ladder = false;
    }

    public static Status from(byte value) {
        BitSet bitSet = BitSet.valueOf(new byte[]{value});

        return new Status(
                bitSet.get(IS_EXPANSION_BIT_POSITION),
                bitSet.get(IS_DIED_BIT_POSITION),
                bitSet.get(IS_HARDCODE_BIT_POSITION)
        );
    }

    public byte byteValue() {
        byte[] array = new byte[]{0};
        BitSet bits = BitSet.valueOf(array);
        if (expansion) {
            bits.set(IS_EXPANSION_BIT_POSITION);
        }
        if (died) {
            bits.set(IS_DIED_BIT_POSITION);
        }
        if (hardcore) {
            bits.set(IS_HARDCODE_BIT_POSITION);
        }

        return bits.toByteArray()[0];
    }

    Status(boolean expansion, boolean died, boolean hardcore) {
        this.expansion = expansion;
        this.died = died;
        this.hardcore = hardcore;
        this.ladder = false;
    }

    public boolean isExpansion() {
        return expansion;
    }

    public void setExpansion() {
        this.expansion = true;
    }

    public boolean isDied() {
        return died;
    }

    public void setDied() {
        this.died = true;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore() {
        this.hardcore = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return expansion == status.expansion
                && died == status.died
                && hardcore == status.hardcore
                && ladder == status.ladder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expansion, died, hardcore, ladder);
    }

    @Override
    public String toString() {
        return "Status{" +
                "expansion=" + expansion +
                ", died=" + died +
                ", hardcore=" + hardcore +
                ", ladder=" + ladder +
                '}';
    }

}
