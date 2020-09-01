package com.example.racinggame;

public enum RoadType {
	VOID((byte) 0),
	DIRECT_LEFT((byte) 1),
	DIRECT_RIGHT((byte) 2),
	DIRECT_FINISH_LEFT((byte) 3),
	DIRECT_FINISH_RIGHT((byte) 4),
	DIRECT_90_UP((byte) 5),
	DIRECT_90_DOWN((byte) 6),
	DIRECT_90_FINISH_UP((byte) 7),
	DIRECT_90_FINISH_DOWN((byte) 8),
	TURN_LEFT_BOTTOM_LEFT((byte) 9),
	TURN_LEFT_BOTTOM_DOWN((byte) 10),
	TURN_RIGHT_BOTTOM_RIGHT((byte) 11),
	TURN_RIGHT_BOTTOM_DOWN((byte) 12),
	TURN_LEFT_TOP_LEFT((byte) 13),
	TURN_LEFT_TOP_UP((byte) 14),
	TURN_RIGHT_TOP_RIGHT((byte) 15),
	TURN_RIGHT_TOP_UP((byte) 16);

	private byte value;

	RoadType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
}
