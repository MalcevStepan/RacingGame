package com.example.racinggame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.racinggame.databinding.ActivitySpeedBinding;

public class SpeedActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final ActivitySpeedBinding binding = ActivitySpeedBinding.inflate(getLayoutInflater());
		super.onCreate(savedInstanceState);

		final SharedPreferences prefs = getSharedPreferences(GameData.APP_PREFERENCES, MODE_PRIVATE);

		final boolean numerals = prefs.getBoolean(GameData.APP_PREFERENCES_NUMERALS, false),
				ui_mode = prefs.getBoolean(GameData.APP_PREFERENCES_UI_MODE, false);

		if (ui_mode)
			setTheme(R.style.AppThemeDark);
		else setTheme(R.style.AppThemeLight);

		final int speed_level = prefs.getInt(GameData.APP_PREFERENCES_SPEED_LEVEL, 1),
				operation = prefs.getInt(GameData.APP_PREFERENCES_OPERATION, 0),
				operation_level = prefs.getInt(GameData.APP_PREFERENCES_OPERATION_LEVEL, 1);
		final Resources res = getResources();

		if (!ui_mode) {
			int toolbarColor = res.getColor(R.color.question_bar_text_color);
			int color = res.getColor(R.color.toolbar_light_color);
			binding.nextButton.setColorFilter(color);
			binding.updateButton.setColorFilter(color);
			binding.settingsButton.setColorFilter(color);
			binding.appToolbar.setBackgroundColor(toolbarColor);
		} else binding.appToolbar.setBackgroundColor(res.getColor(R.color.colorPrimary));

		setContentView(binding.getRoot());

		binding.nextButton.setColorFilter(getResources().getColor(R.color.correct_green_color));

		final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		binding.speedLevelText.setTypeface(font);

		final int next_speed_level = speed_level == 5 ? 5 : speed_level + 1;

		setSpeedCircleImage(getResources(),
				getTheme(),
				next_speed_level,
				numerals,
				binding.speedLevelCircle,
				binding.speedLevelText);

		binding.updateButton.setOnClickListener(view -> {
			final Intent intent = new Intent(this, GameActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		});

		binding.settingsButton.setOnClickListener(view -> {
			final Intent intent = new Intent(this, SettingsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		});

		binding.nextButton.setOnClickListener(view -> {
			final SharedPreferences.Editor editor = prefs.edit();
			editor.putInt(GameData.APP_PREFERENCES_SPEED_LEVEL, next_speed_level);
			editor.putInt(GameData.APP_PREFERENCES_OPERATION_LEVEL, operation_level == GameData.levelCounts[operation].getCount() ? operation_level : operation_level + 1);
			editor.apply();
			final Intent intent = new Intent(this, GameActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		});
	}

	private void setSpeedCircleImage(Resources res,
									 Resources.Theme theme,
									 int speed_level,
									 boolean numerals,
									 ImageView speedLevelCircle,
									 TextView speedLevelText) {
		switch (speed_level) {
			case 1:
				speedLevelCircle.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.speed_level_one_circle, theme));
				speedLevelText.setTextColor(res.getColor(R.color.speed_level_one_color));
				if (numerals)
					speedLevelText.setText(GameData.easternArabicNumerals[1]);
				else
					speedLevelText.setText("1");
				break;
			case 2:
				speedLevelCircle.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.speed_level_two_circle, theme));
				speedLevelText.setTextColor(res.getColor(R.color.speed_level_two_color));
				if (numerals)
					speedLevelText.setText(GameData.easternArabicNumerals[2]);
				else
					speedLevelText.setText("2");
				break;
			case 3:
				speedLevelCircle.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.speed_level_three_circle, theme));
				speedLevelText.setTextColor(res.getColor(R.color.speed_level_three_color));
				if (numerals)
					speedLevelText.setText(GameData.easternArabicNumerals[3]);
				else
					speedLevelText.setText("3");
				break;
			case 4:
				speedLevelCircle.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.speed_level_four_circle, theme));
				speedLevelText.setTextColor(res.getColor(R.color.speed_level_four_color));
				if (numerals)
					speedLevelText.setText(GameData.easternArabicNumerals[4]);
				else
					speedLevelText.setText("4");
				break;
			case 5:
				speedLevelCircle.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.speed_level_five_circle, theme));
				speedLevelText.setTextColor(res.getColor(R.color.speed_level_five_color));
				if (numerals)
					speedLevelText.setText(GameData.easternArabicNumerals[5]);
				else
					speedLevelText.setText("5");
				break;
		}
	}
}