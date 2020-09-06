package com.example.racinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.racinggame.databinding.ActivityResultBinding;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ActivityResultBinding binding = ActivityResultBinding.inflate(getLayoutInflater());

		final Intent data = getIntent();
		final boolean numerals = data.getBooleanExtra(GameData.APP_PREFERENCES_NUMERALS, false),
				ui_mode = data.getBooleanExtra(GameData.APP_PREFERENCES_UI_MODE, false);

		if (ui_mode)
			setTheme(R.style.AppThemeDark);
		else setTheme(R.style.AppThemeLight);

		final Resources res = getResources();

		if (!ui_mode) {
			int color = res.getColor(R.color.toolbar_light_color);
			binding.nextButton.setColorFilter(color);
			binding.updateButton.setColorFilter(color);
			binding.settingsButton.setColorFilter(color);
		} 

		setContentView(binding.getRoot());

		binding.nextButton.setColorFilter(getResources().getColor(R.color.correct_green_color));

		final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		binding.correctText.setTypeface(font);
		binding.wrongText.setTypeface(font);

		final NumeralConverter converter = new NumeralConverter();
		final int wrongCount = Integer.parseInt(Objects.requireNonNull(data.getStringExtra(GameData.WRONG_ANSWER_COUNT))),
				correctCount = Integer.parseInt(Objects.requireNonNull(data.getStringExtra(GameData.CORRECT_ANSWER_COUNT)));
		if (numerals) {
			binding.wrongText.setText(converter.convertToEstArabic(wrongCount));
			binding.correctText.setText(converter.convertToEstArabic(correctCount));
		} else {
			binding.wrongText.setText(data.getStringExtra(GameData.WRONG_ANSWER_COUNT));
			binding.correctText.setText(data.getStringExtra(GameData.CORRECT_ANSWER_COUNT));
		}

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
			final Intent intent = new Intent(this, SpeedActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		});
	}
}