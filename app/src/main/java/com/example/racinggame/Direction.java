package com.example.racinggame;

public enum Direction {
	LEFT((byte) 0), RIGHT((byte) 1), UP((byte) 2), DOWN((byte) 3);

	private byte value;

	Direction(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

}
