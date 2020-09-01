package com.example.racinggame;

import android.util.Log;

public class Timer {
	private double previousTime = -1;

	public void reset() {
		previousTime = -1L;
	}

	public double getDeltaTime() {
		if (previousTime == -1L) previousTime = System.nanoTime();
		long currentTime = System.nanoTime();
		double dt = (currentTime - previousTime) / 1000000;
		previousTime = currentTime;
		return dt;
	}
}
