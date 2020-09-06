package com.example.racinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.Arrays;

public class Car {

	private Rect carBounds;
	private double angle = 0;
	private Matrix matrix;
	private int w, h;
	private double angleV, velocity, defaultVelocity, impulsePercent = 0, radius;
	private Bitmap carLeft, carRight, carUp, carDown;
	private boolean isRunning = false, isComputerCar;
	private int marginStartPx, marginTopPx, viewWidth;
	private static final float impulseAmplitudeDefValue = 0.5f;
	private final double[] lognormalDistr = Arrays.copyOf(GameData.lognormalDistr, GameData.lognormalDistr.length);
	private OnViewActionListener viewActionListener;
	private RoadType[][] track;

	Car(Bitmap carLeft, Bitmap carRight, Bitmap carUp, Bitmap carDown, int w, int h, OnViewActionListener viewActionListener, RoadType[][] track, boolean isComputerCar) {
		this.w = w;
		this.h = h;
		this.carLeft = carLeft;
		this.carRight = carRight;
		this.carUp = carUp;
		this.carDown = carDown;
		radius = (double) w / 2;
		this.viewActionListener = viewActionListener;
		this.track = track;
		this.isComputerCar = isComputerCar;
	}

	public void setCarBounds(int[] startY, int[] startX, int marginStartPx, int marginTopPx) {
		this.marginStartPx = marginStartPx;
		this.marginTopPx = marginTopPx;
		carBounds = new Rect();
		carBounds.left = startX[0] * w + marginStartPx;
		carBounds.right = (startX[0] + 1) * w + marginStartPx;
		carBounds.top = startY[0] * h + marginTopPx;
		carBounds.bottom = (startY[0] + 1) * h + marginTopPx;
		matrix = new Matrix();
		matrix.postTranslate(carBounds.left, carBounds.top);
		matrix.setScale(w, h);
	}

	public final void setCarDefaultSpeed(float defaultVelocity) {
		this.defaultVelocity = defaultVelocity;
	}

	public final void setViewWidth(int width) {
		viewWidth = width;
	}

	public final void setCarVelocity(double deltaTime) {
		if (isRunning) {
			double defVelocity = viewWidth / (defaultVelocity * (1000 / deltaTime));
			velocity = (defVelocity + defVelocity * impulsePercent) * deltaTime / 17;
			angleV = velocity / radius;
		} else velocity = 0;
	}

	public final void setImpulseAmplitude(float amplitude) {
		float times = amplitude / impulseAmplitudeDefValue;
		for (int i = 0; i < lognormalDistr.length; i++)
			lognormalDistr[i] *= times;
	}

	public final void setCarRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	private synchronized void increaseImpulsePercent(double percent) {
		impulsePercent += percent;
	}

	private synchronized void decreaseImpulsePercent(double percent) {
		impulsePercent -= percent;
	}

	public final void increaseCarSpeed() {
		for (double percent : lognormalDistr) {
			try {
				increaseImpulsePercent(percent);
				Thread.sleep(9000 / 270);
				decreaseImpulsePercent(percent);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public final void move(Canvas canvas, int[] y, int[] x, RoadType roadType) {
		switch (roadType.getValue()) {
			case 1:
			case 3:
				move_direct_left(canvas, y, x);
				break;
			case 2:
			case 4:
				move_direct_right(canvas, y, x);
				break;
			case 5:
			case 7:
				move_direct_90_up(canvas, y, x);
				break;
			case 6:
			case 8:
				move_direct_90_down(canvas, y, x);
				break;
			case 9:
				turn_left_bottom_left(canvas, y, x);
				break;
			case 10:
				turn_left_bottom_down(canvas, y, x);
				break;
			case 11:
				turn_right_bottom_right(canvas, y, x);
				break;
			case 12:
				turn_right_bottom_down(canvas, y, x);
				break;
			case 13:
				turn_left_top_left(canvas, y, x);
				break;
			case 14:
				turn_left_top_up(canvas, y, x);
				break;
			case 15:
				turn_right_top_right(canvas, y, x);
				break;
			case 16:
				turn_right_top_up(canvas, y, x);
				break;
		}
	}

	private void move_direct_right(Canvas canvas, int[] i, int[] j) {
		carBounds.left += velocity;
		carBounds.right += velocity;
		matrix.setTranslate(carBounds.left, carBounds.top);
		canvas.drawBitmap(carLeft, matrix, null);
		if (carBounds.left >= (j[0] + 1) * w + marginStartPx) {
			j[0]++;
			if (isFinish(i, j)) {
				if (isComputerCar) viewActionListener.onComputerFinish();
				else
					viewActionListener.onFinish();
			}
		}
	}

	private void move_direct_left(Canvas canvas, int[] i, int[] j) {
		carBounds.left -= velocity;
		carBounds.right -= velocity;
		matrix.setTranslate(carBounds.left, carBounds.top);
		canvas.drawBitmap(carRight, matrix, null);
		if (carBounds.right <= j[0] * w + marginStartPx) {
			j[0]--;
			if (isFinish(i, j)) {
				if (isComputerCar) viewActionListener.onComputerFinish();
				else
					viewActionListener.onFinish();
			}
		}
	}

	private void move_direct_90_up(Canvas canvas, int[] i, int[] j) {
		carBounds.top -= velocity;
		carBounds.bottom -= velocity;
		matrix.setTranslate(carBounds.left, carBounds.top);
		canvas.drawBitmap(carDown, matrix, null);
		if (carBounds.top <= (i[0] - 1) * h + marginTopPx) {
			i[0]--;
			if (isFinish(i, j)) {
				if (isComputerCar) viewActionListener.onComputerFinish();
				else
					viewActionListener.onFinish();
			}
		}
	}

	private void move_direct_90_down(Canvas canvas, int[] i, int[] j) {
		matrix.setTranslate(carBounds.left, carBounds.top);
		canvas.drawBitmap(carUp, matrix, null);
		carBounds.top += velocity;
		carBounds.bottom += velocity;
		if (carBounds.top >= (i[0] + 1) * h + marginTopPx) {
			i[0]++;
			if (isFinish(i, j)) {
				if (isComputerCar) viewActionListener.onComputerFinish();
				else
					viewActionListener.onFinish();
			}
		}
	}

	private void turn_left_bottom_left(Canvas canvas, int[] i, int[] j) {
		angle -= angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, j[0] * w + marginStartPx, (i[0] + 1) * h + marginTopPx);
		canvas.drawBitmap(carDown, matrix, null);
		if (degrees <= -90) {
			j[0]--;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void turn_left_bottom_down(Canvas canvas, int[] i, int[] j) {
		angle += angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, j[0] * w + marginStartPx, (i[0] + 1) * h + marginTopPx);
		canvas.drawBitmap(carLeft, matrix, null);
		if (degrees >= 90) {
			i[0]++;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void turn_right_bottom_right(Canvas canvas, int[] i, int[] j) {
		angle += angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, (j[0] + 1) * w + marginStartPx, (i[0] + 1) * h + marginTopPx);
		canvas.drawBitmap(carDown, matrix, null);
		if (degrees >= 90) {
			j[0]++;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void turn_right_bottom_down(Canvas canvas, int[] i, int[] j) {
		angle -= angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, (j[0] + 1) * w + marginStartPx, (i[0] + 1) * h + marginTopPx);
		canvas.drawBitmap(carRight, matrix, null);
		if (degrees <= -90) {
			i[0]++;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void turn_left_top_left(Canvas canvas, int[] i, int[] j) {
		angle += angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, j[0] * w + marginStartPx, i[0] * h + marginTopPx);
		canvas.drawBitmap(carUp, matrix, null);
		if (degrees >= 90) {
			j[0]--;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void turn_left_top_up(Canvas canvas, int[] i, int[] j) {
		angle -= angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, j[0] * w + marginStartPx, i[0] * h + marginTopPx);
		canvas.drawBitmap(carLeft, matrix, null);
		if (degrees <= -90) {
			i[0]--;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void turn_right_top_right(Canvas canvas, int[] i, int[] j) {
		angle -= angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, (j[0] + 1) * w + marginStartPx, i[0] * h + marginTopPx);
		canvas.drawBitmap(carUp, matrix, null);
		if (degrees <= -90) {
			j[0]++;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void turn_right_top_up(Canvas canvas, int[] i, int[] j) {
		angle += angleV;
		double degrees = angle * 180 / Math.PI;
		matrix.setTranslate(carBounds.left, carBounds.top);
		matrix.postRotate((float) degrees, (j[0] + 1) * w + marginStartPx, i[0] * h + marginTopPx);
		canvas.drawBitmap(carRight, matrix, null);
		if (degrees >= 90) {
			i[0]--;
			setCarBounds(i, j);
			angle = 0;
		}
	}

	private void setCarBounds(int[] i, int[] j) {
		carBounds.left = j[0] * w + marginStartPx;
		carBounds.right = (j[0] + 1) * w + marginStartPx;
		carBounds.top = i[0] * h + marginTopPx;
		carBounds.bottom = (i[0] + 1) * h + marginTopPx;
	}

	private boolean isFinish(int[] i, int[] j) {
		RoadType road = track[i[0]][j[0]];
		return road == RoadType.DIRECT_90_FINISH_DOWN
				|| road == RoadType.DIRECT_90_FINISH_UP
				|| road == RoadType.DIRECT_FINISH_LEFT
				|| road == RoadType.DIRECT_FINISH_RIGHT;
	}

}
