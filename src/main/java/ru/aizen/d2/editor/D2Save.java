package ru.aizen.d2.editor;

import java.util.BitSet;
import java.util.Objects;

public class D2Save {

    private final String characterName;
    private final CharacterClass characterClass;
    private final Status status;

    public D2Save(Builder builder) {
        this.characterName = builder.characterName;
        this.characterClass = builder.characterClass;
        this.status = builder.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        D2Save d2Save = (D2Save) o;
        return characterClass == d2Save.characterClass
                && Objects.equals(characterName, d2Save.characterName)
                && Objects.equals(status, d2Save.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterName, characterClass, status);
    }

    @Override
    public String toString() {
        return "D2Save{" +
                "characterName='" + characterName + '\'' +
                ", characterClass=" + characterClass +
                ", status=" + status +
                '}';
    }

    public static class Builder {
        private final String characterName;
        private final CharacterClass characterClass;
        private final Status status;

        public Builder(String characterName, CharacterClass characterClass, Status status) {
            this.characterName = characterName;
            this.characterClass = characterClass;
            this.status = status;
        }

        public D2Save build() {
            return new D2Save(this);
        }

    }

}
