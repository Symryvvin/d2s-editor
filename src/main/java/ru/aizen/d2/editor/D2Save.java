package ru.aizen.d2.editor;

import java.util.BitSet;
import java.util.Objects;

public class D2Save {

    private final String characterName;
    private final int classId;
    private final BitSet status;

    public D2Save(Builder builder) {
        this.characterName = builder.characterName;
        this.classId = builder.classId;
        this.status = builder.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        D2Save d2Save = (D2Save) o;
        return classId == d2Save.classId
                && Objects.equals(characterName, d2Save.characterName)
                && Objects.equals(status, d2Save.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterName, classId, status);
    }

    @Override
    public String toString() {
        return "D2Save{" +
                "characterName='" + characterName + '\'' +
                ", classId=" + classId +
                ", status=" + status +
                '}';
    }

    public static class Builder {
        private final String characterName;
        private final int classId;
        private final BitSet status;

        public Builder(String characterName, int classId, BitSet status) {
            this.characterName = characterName;
            this.classId = classId;
            this.status = status;
        }

        public D2Save build() {
            return new D2Save(this);
        }

    }

}
