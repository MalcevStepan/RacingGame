package com.example.racinggame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.Objects;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, OnViewActionListener {

	private DrawThread thread;
	private Drawable direct, direct_90, turn_right_top, turn_right_bottom, turn_left_top, turn_left_bottom, finish, finish_90;
	private int w, h, marginStartPx, marginTopPx;
	private int[] computerXPos = new int[]{-1}, computerYPos = new int[]{-1}, userXPos = new int[]{-1}, userYPos = new int[]{-1}, finishXPos = new int[]{-1}, finishYPos = new int[]{-1};
	private Car computerCar, userCar;
	private RoadType[][] track;
	private OnGameActionListener listener;
	private boolean isPurpleCar, isFirstDraw = true;
	private Resources.Theme theme;
	private float referenceSpeed, userSpeed, pulseAmplitude, speedLevelCoef;
	private int bg_color;

	public GameView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		theme = getResources().newTheme();
	}

	private Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	private void initializeCars(Resources res) {
		final Drawable computerCarLeft = ResourcesCompat.getDrawable(res, R.drawable.computer_car_left, theme),
				computerCarRight = ResourcesCompat.getDrawable(res, R.drawable.computer_car_right, theme),
				computerCarUp = ResourcesCompat.getDrawable(res, R.drawable.computer_car_up, theme),
				computerCarDown = ResourcesCompat.getDrawable(res, R.drawable.computer_car_down, theme);
		final Bitmap computerCarLeftBitmap = drawableToBitmap(Objects.requireNonNull(computerCarLeft)),
				computerCarRightBitmap = drawableToBitmap(Objects.requireNonNull(computerCarRight)),
				computerCarUpBitmap = drawableToBitmap(Objects.requireNonNull(computerCarUp)),
				computerCarDownBitmap = drawableToBitmap(Objects.requireNonNull(computerCarDown));
		Drawable userCarLeft, userCarRight, userCarUp, userCarDown;
		if (isPurpleCar) {
			userCarLeft = ResourcesCompat.getDrawable(res, R.drawable.purple_car_left, theme);
			userCarRight = ResourcesCompat.getDrawable(res, R.drawable.purple_car_right, theme);
			userCarUp = ResourcesCompat.getDrawable(res, R.drawable.purple_car_up, theme);
			userCarDown = ResourcesCompat.getDrawable(res, R.drawable.purple_car_down, theme);
		} else {
			userCarLeft = ResourcesCompat.getDrawable(res, R.drawable.turquoise_car_left, theme);
			userCarRight = ResourcesCompat.getDrawable(res, R.drawable.turquoise_car_right, theme);
			userCarUp = ResourcesCompat.getDrawable(res, R.drawable.turquoise_car_up, theme);
			userCarDown = ResourcesCompat.getDrawable(res, R.drawable.turquoise_car_down, theme);
		}
		final Bitmap userCarLeftBitmap = drawableToBitmap(Objects.requireNonNull(userCarLeft)),
				userCarRightBitmap = drawableToBitmap(Objects.requireNonNull(userCarRight)),
				userCarUpBitmap = drawableToBitmap(Objects.requireNonNull(userCarUp)),
				userCarDownBitmap = drawableToBitmap(Objects.requireNonNull(userCarDown));
		userCar = new Car(userCarLeftBitmap, userCarRightBitmap, userCarUpBitmap, userCarDownBitmap, w, h, this, track, false);
		computerCar = new Car(computerCarLeftBitmap, computerCarRightBitmap, computerCarUpBitmap, computerCarDownBitmap, w, h, this, track, true);
		userCar.setCarDefaultSpeed(userSpeed);
		computerCar.setCarDefaultSpeed(referenceSpeed * speedLevelCoef);
	}

	private void initializeRoads(Resources res) {
		direct = ResourcesCompat.getDrawable(res, R.drawable.direct_road, theme);
		direct_90 = ResourcesCompat.getDrawable(res, R.drawable.direct_road_90, theme);
		turn_right_bottom = ResourcesCompat.getDrawable(res, R.drawable.turn_right_bottom, theme);
		turn_right_top = ResourcesCompat.getDrawable(res, R.drawable.turn_right_top, theme);
		turn_left_bottom = ResourcesCompat.getDrawable(res, R.drawable.turn_left_bottom, theme);
		turn_left_top = ResourcesCompat.getDrawable(res, R.drawable.turn_left_top, theme);
		finish = ResourcesCompat.getDrawable(res, R.drawable.finish, theme);
		finish_90 = ResourcesCompat.getDrawable(res, R.drawable.finish_90, theme);
	}

	public final void setCarColor(boolean isPurpleCar) {
		this.isPurpleCar = isPurpleCar;
	}

	public final void setRunning(boolean isRunning) {
		thread.setRunning(isRunning);
	}

	public final void stopRace() {
		thread.setRunning(false);
		userCar.setCarVelocity(0);
		computerCar.setCarVelocity(0);
		userCar.setCarRunning(false);
		computerCar.setCarRunning(false);
	}

	public final void setUIMode(boolean ui_mode) {
		bg_color = ui_mode ? Color.parseColor("#393939") : getResources().getColor(R.color.colorPrimaryDarkLight);
	}

	public final void setTrack(RoadType[][] track) {
		this.track = track;
	}

	@Override
	public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
		thread = new DrawThread(surfaceHolder);
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

	}

	@Override
	public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
		thread.setRunning(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public final void setReferenceSpeed(float refSpeed) {
		referenceSpeed = refSpeed;
	}

	public final void setSpeedLevelCoef(float speedLevelCoef) {
		this.speedLevelCoef = speedLevelCoef;
	}

	public final void setUserSpeed(float userSpeed) {
		this.userSpeed = userSpeed;
	}

	@Override
	public void onFinish() {
		listener.onFinish();
	}

	@Override
	public void onComputerFinish() {
		listener.onComputerFinish();
	}

	class DrawThread extends Thread {

		private boolean isRunning = false;
		private SurfaceHolder surfaceHolder;
		private Timer timer;

		public DrawThread(SurfaceHolder surfaceHolder) {
			this.surfaceHolder = surfaceHolder;
			timer = new Timer();
		}

		public final void setRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}


		// drawing game view
		@Override
		public void run() {
			Canvas canvas = null;
			while (isRunning)
				try {
					canvas = surfaceHolder.lockCanvas(null);
					if (canvas == null) continue;
					if (isFirstDraw) {
						int width = canvas.getWidth(),
								height = canvas.getHeight(),
								rowCount = track.length, columnCount = track[0].length;
						w = Math.min(width / columnCount, height / rowCount);
						h = w;
						marginStartPx = (width - w * columnCount) / 2;
						marginTopPx = (height - h * rowCount) / 2;
						final Resources res = getContext().getResources();
						initializeCars(res);
						initializeRoads(res);
						userCar.setImpulseAmplitude(pulseAmplitude);
						isFirstDraw = false;
					}
					// drawColor(bg_color) is equals to redraw canvas
					canvas.drawColor(bg_color);
					drawTrack(canvas, track);
					double deltaTime = timer.getDeltaTime();
					computerCar.move(canvas, computerYPos, computerXPos, track[computerYPos[0]][computerXPos[0]]);
					userCar.move(canvas, userYPos, userXPos, track[userYPos[0]][userXPos[0]]);
					userCar.setCarVelocity(deltaTime);
					computerCar.setCarVelocity(deltaTime);
				} finally {
					if (canvas != null)
						surfaceHolder.unlockCanvasAndPost(canvas);
				}
		}
	}

	public final void giveImpulse() {
		new Thread(() -> userCar.increaseCarSpeed()).start();
	}

	public final void setListener(OnGameActionListener listener) {
		this.listener = listener;
	}

	public final void setImpulseAmplitude(float amplitude) {
		pulseAmplitude = amplitude;
	}

	public final void startRace() {
		computerCar.setCarRunning(true);
		userCar.setCarRunning(true);
	}

	private void drawTrack(Canvas canvas, RoadType[][] track) {
		for (int i = 0; i < track.length; i++) {
			for (int j = 0; j < track[i].length; j++) {
				int left = j * w + marginStartPx,
						top = i * h + marginTopPx,
						right = j * w + w + marginStartPx,
						bottom = i * h + h + marginTopPx;
				switch (track[i][j].getValue()) {
					case 1:
					case 2:
						direct.setBounds(left, top, right, bottom);
						direct.draw(canvas);
						break;
					case 5:
					case 6:
						direct_90.setBounds(left, top, right, bottom);
						direct_90.draw(canvas);
						break;
					case 7:
					case 8:
						if (computerYPos[0] == -1 && computerXPos[0] == -1) {
							computerYPos[0] = i;
							computerXPos[0] = j;
							userXPos[0] = j;
							userYPos[0] = i;
							finishXPos[0] = j;
							finishYPos[0] = i;
							computerCar.setCarBounds(computerYPos, computerXPos, marginStartPx, marginTopPx);
							userCar.setCarBounds(userYPos, userXPos, marginStartPx, marginTopPx);
						}
						direct_90.setBounds(left, top, right, bottom);
						finish_90.setBounds(left, top, right, bottom);
						direct_90.draw(canvas);
						finish_90.draw(canvas);
						break;
					case 3:
					case 4:
						if (computerYPos[0] == -1 && computerXPos[0] == -1) {
							computerYPos[0] = i;
							computerXPos[0] = j;
							userXPos[0] = j;
							userYPos[0] = i;
							finishXPos[0] = j;
							finishYPos[0] = i;
							computerCar.setCarBounds(computerYPos, computerXPos, marginStartPx, marginTopPx);
							userCar.setCarBounds(userYPos, userXPos, marginStartPx, marginTopPx);
						}
						direct.setBounds(left, top, right, bottom);
						finish.setBounds(left, top, right, bottom);
						direct.draw(canvas);
						finish.draw(canvas);
						break;
					case 9:
					case 10:
						turn_left_bottom.setBounds(left, top, right, bottom);
						turn_left_bottom.draw(canvas);
						break;
					case 11:
					case 12:
						turn_right_bottom.setBounds(left, top, right, bottom);
						turn_right_bottom.draw(canvas);
						break;
					case 13:
					case 14:
						turn_left_top.setBounds(left, top, right, bottom);
						turn_left_top.draw(canvas);
						break;
					case 15:
					case 16:
						turn_right_top.setBounds(left, top, right, bottom);
						turn_right_top.draw(canvas);
						break;
					default:
						break;
				}
			}
		}
	}

}
