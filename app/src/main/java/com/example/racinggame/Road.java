package com.example.racinggame;

public class Road {
	private Direction direction;
	private RoadType type;

	Road(Direction direction, RoadType type){
		this.direction = direction;
		this.type = type;
	}

	public Direction getDirection() {
		return direction;
	}

	public byte getTypeValue() {
		return type.getValue();
	}
}
