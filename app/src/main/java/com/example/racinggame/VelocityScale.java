package com.example.racinggame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class VelocityScale extends View {

	private Drawable velocityScaleDrawable;
	private Paint paint;
	private final Rect scale;
	private int startXPos;
	private boolean isDescending, isFirstDraw = true;
	private int velocity = 70;

	public VelocityScale(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		velocityScaleDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.velocity_scale, context.getTheme());
		paint = new Paint();
		scale = new Rect();
	}

	public final void animateScale() {
		new Thread(() -> {
			isDescending = false;
			while (scale.left < scale.right) {
				try {
					scale.left += velocity;
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			isDescending = true;
			while (scale.left > startXPos && isDescending) {
				try {
					synchronized (scale) {
						scale.left -= (velocity / 2);
					}
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void setBgColor(boolean ui_mode) {
		paint.setColor(getResources().getColor(ui_mode ? R.color.colorPrimaryDark : R.color.colorPrimaryLight));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isFirstDraw) {
			int width = getWidth(), height = getHeight();
			velocityScaleDrawable.setBounds(0, 0, width, height);
			startXPos = (int) (width * 0.2f);
			scale.left = startXPos;
			scale.top = 0;
			scale.right = width;
			scale.bottom = height;
			isFirstDraw = false;
			velocity = width / velocity;
		}
		velocityScaleDrawable.draw(canvas);
		canvas.drawRect(scale, paint);
		invalidate();
	}
}
