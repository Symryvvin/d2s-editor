package ru.aizen.d2.editor;

import java.util.Arrays;

public enum GameVersion {

    RESURRECTED(98), // 0x62000000
    LOD_1_14(97); // 0x61000000

    final int value;

    GameVersion(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void checkVersion(int value) {
        if (Arrays.stream(GameVersion.values())
                .noneMatch(gameVersion -> gameVersion.value == value)) {
            throw new RuntimeException("Game version " + value + "is not recognized");
        }
    }
}
