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

	public static GameVersion checkVersion(int value) {
		return Arrays.stream(GameVersion.values())
				.filter(gameVersion -> gameVersion.value == value)
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Game version " + value + "is not recognized"));
	}

}
