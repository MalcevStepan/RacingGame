package com.example.racinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.racinggame.databinding.ActivitySettingsBinding;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

	private ActivitySettingsBinding binding;
	private SharedPreferences sharedPreferences;
	private int operation_level, speed_level;
	private boolean numerals;
	private NumeralConverter converter;
	private String reference_speed, speed_level_coef_2, speed_level_coef_3, speed_level_coef_4, speed_level_coef_5, user_const, pulse_amplitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		binding = ActivitySettingsBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
		setTextFont(font);

		converter = new NumeralConverter();

		sharedPreferences = getSharedPreferences(GameData.APP_PREFERENCES, Context.MODE_PRIVATE);

		final Resources res = getResources();

		final int operation = sharedPreferences.getInt(GameData.APP_PREFERENCES_OPERATION, 0);
		numerals = sharedPreferences.getBoolean(GameData.APP_PREFERENCES_NUMERALS, false);
		operation_level = sharedPreferences.getInt(GameData.APP_PREFERENCES_OPERATION_LEVEL, 1) - 1;
		speed_level = sharedPreferences.getInt(GameData.APP_PREFERENCES_SPEED_LEVEL, 1) - 1;

		binding.uiSwitch.setChecked(sharedPreferences.getBoolean(GameData.APP_PREFERENCES_UI_MODE, false));
		binding.userColorSwitch.setChecked(sharedPreferences.getBoolean(GameData.APP_PREFERENCES_USER_COLOR, false));
		binding.numeralsSwitch.setChecked(sharedPreferences.getBoolean(GameData.APP_PREFERENCES_NUMERALS, false));
		binding.operationSpinner.setSelection(operation);
		binding.operationLevelSpinner.setSelection(operation_level);
		binding.speedLevelSpinner.setSelection(speed_level);
		reference_speed = sharedPreferences.getString(GameData.APP_PREFERENCES_REFERENCE_SPEED, "5");
		speed_level_coef_2 = sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_2, "1.1");
		speed_level_coef_3 = sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_3, "1.2");
		speed_level_coef_4 = sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_4, "1.4");
		speed_level_coef_5 = sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_5, "1.6");
		user_const = sharedPreferences.getString(GameData.APP_PREFERENCES_USER_CONSTANT_SPEED, "0.7");
		pulse_amplitude = sharedPreferences.getString(GameData.APP_PREFERENCES_PULSE_AMPLITUDE, "1.5");

		setOperationLevels(operation);
		setSpeedLevels(res);
		setEditTextNumerals();

		binding.uiSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(GameData.APP_PREFERENCES_UI_MODE, b);
			editor.apply();
		});

		binding.numeralsSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(GameData.APP_PREFERENCES_NUMERALS, b);
			editor.apply();
			numerals = b;
			//setOperationLevels(operation);
			//setSpeedLevels(res);
			//setEditTextNumerals();
		});

		binding.userColorSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(GameData.APP_PREFERENCES_USER_COLOR, b);
			editor.apply();
		});

		binding.operationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				final SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putInt(GameData.APP_PREFERENCES_OPERATION, i);
				editor.apply();
				setOperationLevels(i);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		binding.operationLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				final SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putInt(GameData.APP_PREFERENCES_OPERATION_LEVEL, i + 1);
				editor.apply();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		binding.speedLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				final SharedPreferences.Editor editor = sharedPreferences.edit();
				editor.putInt(GameData.APP_PREFERENCES_SPEED_LEVEL, i + 1);
				editor.apply();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, GameActivity.class));
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(GameData.APP_PREFERENCES_REFERENCE_SPEED, binding.referenceSpeed.getText().toString());
		editor.putString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_2, binding.speedLevel2Coef.getText().toString());
		editor.putString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_3, binding.speedLevel3Coef.getText().toString());
		editor.putString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_4, binding.speedLevel4Coef.getText().toString());
		editor.putString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_5, binding.speedLevel5Coef.getText().toString());
		editor.putString(GameData.APP_PREFERENCES_USER_CONSTANT_SPEED, binding.userConstantSpeed.getText().toString());
		editor.putString(GameData.APP_PREFERENCES_PULSE_AMPLITUDE, binding.pulseAmplitude.getText().toString());
		editor.apply();
		binding = null;
		finish();
	}

	private void setTextFont(Typeface font) {
		binding.uiModeText.setTypeface(font);
		binding.numeralsText.setTypeface(font);
		binding.userColorText.setTypeface(font);
		binding.operationLevelText.setTypeface(font);
		binding.operationText.setTypeface(font);
		binding.speedLevelText.setTypeface(font);
		binding.speedLevelCoefText.setTypeface(font);
		binding.pulseAmplitudeText.setTypeface(font);
		binding.userConstantSpeedText.setTypeface(font);
		binding.referenceSpeedText.setTypeface(font);
		binding.dayText.setTypeface(font);
		binding.nightText.setTypeface(font);
		binding.level2Text.setTypeface(font);
		binding.level3Text.setTypeface(font);
		binding.level4Text.setTypeface(font);
		binding.level5Text.setTypeface(font);
	}

	private void setOperationLevels(int operation) {
		final List<String> arr = new ArrayList<>();
		for (int i = 0; i < GameData.levelCounts[operation].getCount(); i++)
			/*if (numerals)
				arr.add(converter.convertToEstArabic(i + 1));
			else*/
			arr.add(String.valueOf(i + 1));

		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.operation_level_item, arr);

		adapter.setDropDownViewResource(R.layout.operation_level_item);
		binding.operationLevelSpinner.setAdapter(adapter);
		binding.operationLevelSpinner.setSelection(operation_level);
	}

	private void setSpeedLevels(Resources res) {
		final String[] speedLevels = res.getStringArray(R.array.speed_levels);
		/*if (numerals)
			for (int i = 0; i < speedLevels.length; i++)
				speedLevels[i] = converter.convertToEstArabic(Integer.parseInt(speedLevels[i]));*/
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.operation_level_item, speedLevels);
		adapter.setDropDownViewResource(R.layout.operation_level_item);
		binding.speedLevelSpinner.setAdapter(adapter);
		binding.speedLevelSpinner.setSelection(speed_level);
	}

	private void setEditTextNumerals() {
		/*if (numerals) {
			binding.referenceSpeed.setText(converter.convertToEstArabic(reference_speed));
			binding.speedLevel2Coef.setText(converter.convertToEstArabic(speed_level_coef_2));
			binding.speedLevel3Coef.setText(converter.convertToEstArabic(speed_level_coef_3));
			binding.speedLevel4Coef.setText(converter.convertToEstArabic(speed_level_coef_4));
			binding.speedLevel5Coef.setText(converter.convertToEstArabic(speed_level_coef_5));
			binding.userConstantSpeed.setText(converter.convertToEstArabic(user_const));
			binding.pulseAmplitude.setText(converter.convertToEstArabic(pulse_amplitude));
		} else {*/
		binding.referenceSpeed.setText(reference_speed);
		binding.speedLevel2Coef.setText(speed_level_coef_2);
		binding.speedLevel3Coef.setText(speed_level_coef_3);
		binding.speedLevel4Coef.setText(speed_level_coef_4);
		binding.speedLevel5Coef.setText(speed_level_coef_5);
		binding.userConstantSpeed.setText(user_const);
		binding.pulseAmplitude.setText(pulse_amplitude);
	}
}