package ru.aizen.d2.editor;

import java.util.Arrays;

public enum CharacterClass {

    AMAZON(0),
    SORCERESS(1),
    NECROMANCER(2),
    PALADIN(3),
    BARBARIAN(4),
    DRUID(5),
    ASSASSIN(6);

    private final int id;

    CharacterClass(int id) {
        this.id = id;
    }

    public static CharacterClass byId(int classId) {
        return Arrays.stream(CharacterClass.values())
                .filter(characterClass -> characterClass.id == classId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Character class with id " + classId + " not found"));
    }
}
