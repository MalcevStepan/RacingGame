package com.example.racinggame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.racinggame.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements OnGameActionListener, View.OnClickListener {

	private ActivityGameBinding binding;
	private boolean raceIsRunning = false, numerals;
	private final int[] seconds = new int[]{0};
	private Animation showQuestion, hideQuestion;
	private static final int QUESTION_ANIMATION_DURATION = 400;
	private int lapsCount = 4, computerLapsCount = 4;
	private Drawable[] lights;
	private TextView answerText;
	private float correctAnswer;
	private int correctAnswerCount, wrongAnswerCount;
	private NumeralConverter converter;
	private List<float[]> questionsList;
	private static final int MAX_DIFF_QUESTIONS = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final SharedPreferences sharedPreferences = getSharedPreferences(GameData.APP_PREFERENCES, Context.MODE_PRIVATE);
		final boolean ui_mode = sharedPreferences.getBoolean(GameData.APP_PREFERENCES_UI_MODE, false),
				user_color = sharedPreferences.getBoolean(GameData.APP_PREFERENCES_USER_COLOR, false);
		numerals = sharedPreferences.getBoolean(GameData.APP_PREFERENCES_NUMERALS, false);

		if (ui_mode)
			setTheme(R.style.AppThemeDark);
		else setTheme(R.style.AppThemeLight);

		binding = ActivityGameBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		final Resources res = getResources();
		final Resources.Theme theme = getTheme();

		int textColor = res.getColor(R.color.question_bar_text_color);
		if (!ui_mode) {
			int color = res.getColor(R.color.toolbar_light_color);
			binding.nextButton.setColorFilter(color);
			binding.updateButton.setColorFilter(color);
			binding.settingsButton.setColorFilter(color);
			binding.removeButton.setImageDrawable(ResourcesCompat.getDrawable(res, R.drawable.remove_light, theme));
			binding.appToolbar.setBackgroundColor(textColor);
		} else
			binding.appToolbar.setBackgroundColor(res.getColor(R.color.colorPrimary));

		binding.questionText.setTextColor(textColor);
		binding.answerText.setTextColor(textColor);

		questionsList = new ArrayList<>();

		final int operation = sharedPreferences.getInt(GameData.APP_PREFERENCES_OPERATION, 0),
				operation_level = sharedPreferences.getInt(GameData.APP_PREFERENCES_OPERATION_LEVEL, 1),
				speed_level = sharedPreferences.getInt(GameData.APP_PREFERENCES_SPEED_LEVEL, 1);

		answerText = binding.answerText;

		converter = new NumeralConverter();

		binding.nextButton.setEnabled(false);

		final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf"),
				light_font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		binding.countdownText.setTypeface(font);
		binding.timeText.setTypeface(font);
		binding.questionText.setTypeface(light_font);
		binding.answerText.setTypeface(light_font);
		binding.lapsText.setTypeface(font);
		setNumPadFont(light_font);

		GameView gameView = binding.gameView;
		gameView.setTrack(GameData.tracks[operation + (operation_level >= GameData.levelCounts[operation].getCount() / 2 ? 1 : 0)]);
		gameView.setListener(this);

		lights = new Drawable[]{
				ResourcesCompat.getDrawable(res, R.drawable.green_traffic_light, theme),
				ResourcesCompat.getDrawable(res, R.drawable.orange_traffic_light, theme),
		};

		binding.velocityScale.setScaleType(ImageView.ScaleType.CENTER_CROP);
		binding.velocityScale.getLayoutParams().width = 0;
		gameView.setCarVelocityScale(binding.velocityScale);

		gameView.setUIMode(ui_mode);

		if (numerals)
			setArabicNumerals();

		final String reference_speed = sharedPreferences.getString(GameData.APP_PREFERENCES_REFERENCE_SPEED, "5");
		final String[] speed_level_coefs = new String[]{
				sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_2, "1.1"),
				sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_3, "1.2"),
				sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_4, "1.3"),
				sharedPreferences.getString(GameData.APP_PREFERENCES_SPEED_LEVEL_COEF_5, "1.4")};
		final String user_constant_speed = sharedPreferences.getString(GameData.APP_PREFERENCES_USER_CONSTANT_SPEED, "0.7"),
				pulse_amplitude = sharedPreferences.getString(GameData.APP_PREFERENCES_PULSE_AMPLITUDE, "1.5");

		final Drawable wrongAnswerDrawable = ResourcesCompat.getDrawable(res, R.drawable.wrong_answer_image, theme);
		final Drawable correctAnswerDrawable = ResourcesCompat.getDrawable(res, R.drawable.check_image, theme);

		final Animation hideTrafficLight = AnimationUtils.loadAnimation(this, R.anim.hide_traffic_light);

		final float[] questionNumbers = GameData.getLevelNumbers(operation, operation_level);
		questionsList.add(questionNumbers);
		correctAnswer = countCorrectAnswer(questionNumbers, operation);
		final TimerThread timerThread = new TimerThread(numerals);
		final CountDownThread countDownThread = new CountDownThread(questionNumbers, operation, numerals, timerThread, hideTrafficLight);
		countDownThread.start();

		gameView.setCarColor(user_color);
		if (reference_speed != null && user_constant_speed != null && pulse_amplitude != null) {
			gameView.setReferenceSpeed(Float.parseFloat(reference_speed));
			gameView.setUserSpeed(Float.parseFloat(reference_speed) / Float.parseFloat(user_constant_speed));
			gameView.setImpulseAmplitude(Float.parseFloat(pulse_amplitude));
			gameView.setSpeedLevelCoef(speed_level == 1 ? 1f : Float.parseFloat(speed_level_coefs[speed_level - 2]));
		}

		showQuestion = AnimationUtils.loadAnimation(this, R.anim.show_question);
		hideQuestion = AnimationUtils.loadAnimation(this, R.anim.hide_question);

		binding.settingsButton.setOnClickListener(view -> {
			Intent intent = new Intent(this, SettingsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			raceIsRunning = false;
			gameView.setRunning(false);
			stopTimer(timerThread);
			finish();
		});

		binding.checkButton.setOnClickListener(view -> {
			String answer = binding.answerText.getText().toString();
			if (numerals) answer = converter.convertToArabic(answer);
			int len = answer.length();
			if (len > 1 || (len == 1 && !answer.equals("-"))) {
				if (Float.parseFloat(answer) == correctAnswer) {
					gameView.giveImpulse();
					binding.checkButton.setClickable(false);
					binding.answerText.setTextColor(res.getColor(R.color.correct_green_color));
					setNewQuestion(res, operation, operation_level, correctAnswerDrawable, numerals);
					correctAnswerCount++;
				} else {
					binding.answerText.setTextColor(res.getColor(R.color.wrong_red_color));
					binding.checkButton.setImageDrawable(wrongAnswerDrawable);
					binding.checkButton.setClickable(false);
					setNewQuestion(res, operation, operation_level, correctAnswerDrawable, numerals);
					wrongAnswerCount++;
				}
			}
		});

		binding.updateButton.setOnClickListener(view -> {
			gameView.stopRace();
			this.recreate();
		});

		binding.nextButton.setOnClickListener(view -> {
			Intent intent = new Intent(this, ResultActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.putExtra(GameData.CORRECT_ANSWER_COUNT, String.valueOf(correctAnswerCount));
			intent.putExtra(GameData.WRONG_ANSWER_COUNT, String.valueOf(wrongAnswerCount));
			intent.putExtra(GameData.APP_PREFERENCES_UI_MODE, ui_mode);
			intent.putExtra(GameData.APP_PREFERENCES_NUMERALS, numerals);
			startActivity(intent);
			raceIsRunning = false;
			gameView.setRunning(false);
			stopTimer(timerThread);
			finish();
		});
	}

	private float countCorrectAnswer(float[] numbers, int operation) {
		switch (operation) {
			case 0:
				return numbers[0] + numbers[1];
			case 1:
				return numbers[0] - numbers[1];
			case 2:
				return numbers[0] * numbers[1];
			default:
				return numbers[0] / numbers[1];
		}
	}

	@SuppressLint("SetTextI18n")
	private void setArabicNumerals() {
		binding.lapsCount.setText(String.valueOf(GameData.easternArabicNumerals[lapsCount]));
		binding.countdownText.setText(String.valueOf(GameData.easternArabicNumerals[3]));
		String doubleZero = String.valueOf(new char[]{GameData.easternArabicNumerals[0], GameData.easternArabicNumerals[0]});
		binding.timeText.setText(doubleZero + " : " + doubleZero);
		binding.zeroButton.setText(String.valueOf(GameData.easternArabicNumerals[0]));
		binding.oneButton.setText(String.valueOf(GameData.easternArabicNumerals[1]));
		binding.twoButton.setText(String.valueOf(GameData.easternArabicNumerals[2]));
		binding.threeButton.setText(String.valueOf(GameData.easternArabicNumerals[3]));
		binding.fourButton.setText(String.valueOf(GameData.easternArabicNumerals[4]));
		binding.fiveButton.setText(String.valueOf(GameData.easternArabicNumerals[5]));
		binding.sixButton.setText(String.valueOf(GameData.easternArabicNumerals[6]));
		binding.sevenButton.setText(String.valueOf(GameData.easternArabicNumerals[7]));
		binding.eightButton.setText(String.valueOf(GameData.easternArabicNumerals[8]));
		binding.nineButton.setText(String.valueOf(GameData.easternArabicNumerals[9]));
	}

	private void setNewQuestion(Resources res, int operation, int operation_level, Drawable checkImage, boolean isArabicNumerals) {
		new Thread(() -> {
			try {
				runOnUiThread(() -> {
					if (binding != null) {
						binding.question.startAnimation(hideQuestion);
						binding.questionText.startAnimation(hideQuestion);
					}
				});
				Thread.sleep(QUESTION_ANIMATION_DURATION);
				runOnUiThread(() -> {
					boolean isOtherNumbers = false;
					float[] numbers = null;
					int attempts = 15;
					while (!isOtherNumbers) {
						numbers = GameData.getLevelNumbers(operation, operation_level);
						boolean check = false;
						for (float[] prevNumbers : questionsList)
							if (prevNumbers[0] == numbers[0] && prevNumbers[1] == numbers[1]) {
								check = true;
								break;
							}
						if (!check || attempts-- <= 0) {
							isOtherNumbers = true;
							if (questionsList.size() == MAX_DIFF_QUESTIONS)
								questionsList.clear();
							questionsList.add(numbers);
						}
					}
					correctAnswer = countCorrectAnswer(numbers, operation);
					if (binding != null) {
						binding.questionText.setText(getQuestionByNumbers(numbers, operation, isArabicNumerals));
						binding.answerText.setText("");
						binding.answerText.setTextColor(res.getColor(R.color.default_question_bar_color));
					}
				});
				runOnUiThread(() -> {
					if (binding != null) {
						binding.checkButton.setImageDrawable(checkImage);
						binding.question.startAnimation(showQuestion);
						binding.questionText.startAnimation(showQuestion);
						binding.checkButton.setClickable(true);
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void setNumPadFont(Typeface font) {
		binding.oneButton.setTypeface(font);
		binding.twoButton.setTypeface(font);
		binding.threeButton.setTypeface(font);
		binding.fourButton.setTypeface(font);
		binding.fiveButton.setTypeface(font);
		binding.sixButton.setTypeface(font);
		binding.sevenButton.setTypeface(font);
		binding.eightButton.setTypeface(font);
		binding.nineButton.setTypeface(font);
		binding.zeroButton.setTypeface(font);
		binding.minusButton.setTypeface(font);
		binding.pointButton.setTypeface(font);
	}

	private String getQuestionByNumbers(float[] numbers, int operation, boolean isArabicNumerals) {
		String operationChar;
		switch (operation) {
			case 0:
				operationChar = " + ";
				break;
			case 1:
				operationChar = " - ";
				break;
			case 2:
				operationChar = " x ";
				break;
			default:
				operationChar = " ÷ ";
				break;
		}
		boolean isFraction1 = numbers[0] > (int) numbers[0],
				isFraction2 = numbers[1] > (int) numbers[1];
		String num1, num2;
		if (isFraction1) num1 = String.valueOf(numbers[0]);
		else num1 = String.valueOf((int) numbers[0]);
		if (isFraction2) num2 = String.valueOf(numbers[1]);
		else num2 = String.valueOf((int) numbers[1]);
		if (isArabicNumerals)
			return converter.convertToEstArabic(numbers[0]) + " " + operationChar + " " + converter.convertToEstArabic(numbers[1]);
		else
			return num1 + operationChar + num2;
	}

	private void stopTimer(TimerThread timerThread) {
		new Thread(() -> {
			try {
				timerThread.join();
				binding = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void setNumPadClickable(boolean isClickable) {
		binding.oneButton.setClickable(isClickable);
		binding.twoButton.setClickable(isClickable);
		binding.threeButton.setClickable(isClickable);
		binding.fourButton.setClickable(isClickable);
		binding.fiveButton.setClickable(isClickable);
		binding.sixButton.setClickable(isClickable);
		binding.sevenButton.setClickable(isClickable);
		binding.eightButton.setClickable(isClickable);
		binding.nineButton.setClickable(isClickable);
		binding.zeroButton.setClickable(isClickable);
		binding.minusButton.setClickable(isClickable);
		binding.pointButton.setClickable(isClickable);
	}

	@Override
	public void onFinish() {
		runOnUiThread(() -> {
			lapsCount--;
			if (lapsCount == 0)
				onRaceFinish();
			else {
				if (numerals)
					binding.lapsCount.setText(String.valueOf(GameData.easternArabicNumerals[lapsCount]));
				else
					binding.lapsCount.setText(String.valueOf(lapsCount));
			}
		});
	}

	@Override
	public void onRaceFinish() {
		runOnUiThread(() -> {
			int green = getResources().getColor(R.color.correct_green_color);
			binding.finishText.setVisibility(View.VISIBLE);
			if (computerLapsCount == 0) {
				binding.updateButton.setColorFilter(green);
				binding.finishText.setText(getString(R.string.lost_text));
				binding.finishText.setTextColor(getResources().getColor(R.color.wrong_red_color));
			} else {
				binding.finishText.setText(getString(R.string.win_text));
				binding.finishText.setTextColor(green);
				binding.nextButton.setEnabled(true);
				binding.nextButton.setColorFilter(green);
			}
		});
		binding.gameView.stopRace();
		raceIsRunning = false;
		setNumPadClickable(false);
		binding.checkButton.setClickable(false);
	}

	@Override
	public void onComputerFinish() {
		computerLapsCount--;
		if (computerLapsCount == 0)
			onRaceFinish();
	}

	@Override
	public void onClick(View view) {
		String text = answerText.getText().toString();
		int answerTextMaxLength = 5, textLength = text.length();
		if (view.equals(binding.pointButton)) {
			if (textLength > 0 && textLength != (answerTextMaxLength - 1) && !text.contains("."))
				answerText.append(".");
		} else if (view.equals(binding.minusButton)) {
			if (answerText.getText().length() == 0)
				answerText.append("-");
		} else if (view.equals(binding.zeroButton)) {
			if (!(textLength == 1 && text.equals(((Button) view).getText().toString())))
				answerText.append(((Button) view).getText());
		} else if (view.equals(binding.removeButton)) {
			CharSequence sequence = binding.answerText.getText();
			int length = sequence.length();
			if (length > 0) answerText.setText(sequence.subSequence(0, length - 1));
		} else if (!(textLength == 1 && text.equals(binding.zeroButton.getText().toString())))
			answerText.append(((Button) view).getText());
	}

	private class CountDownThread extends Thread {

		private int operation;
		private float[] numbers;
		private boolean isArabicNumerals;
		private TimerThread timerThread;
		private Animation trafficLightAnim;

		CountDownThread(float[] numbers, int operation, boolean isArabicNumerals, TimerThread timerThread, Animation trafficLightAnim) {
			this.operation = operation;
			this.numbers = numbers;
			this.isArabicNumerals = isArabicNumerals;
			this.timerThread = timerThread;
			this.trafficLightAnim = trafficLightAnim;
		}

		@Override
		public void run() {
			try {
				int countdownSec = 3;
				while (countdownSec > 1) {
					Thread.sleep(1000);
					countdownSec--;
					int finalCountdownSec = countdownSec;
					runOnUiThread(() -> {
						if (binding != null) {
							binding.trafficLight.setImageDrawable(lights[finalCountdownSec - 1]);
							if (isArabicNumerals)
								binding.countdownText.setText(converter.convertToEstArabic(finalCountdownSec));
							else binding.countdownText.setText(String.valueOf(finalCountdownSec));
						}
					});
				}
				Thread.sleep(1000);
				runOnUiThread(() -> {
					if (binding != null) {
						binding.questionText.setText(getQuestionByNumbers(numbers, operation, isArabicNumerals));
						binding.questionBar.setVisibility(View.VISIBLE);
						binding.questionBar.startAnimation(showQuestion);
						binding.countdownText.setVisibility(View.INVISIBLE);
						raceIsRunning = true;
						timerThread.start();
						binding.trafficLight.startAnimation(trafficLightAnim);
					}
				});
				if (binding != null)
					binding.gameView.startRace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private class TimerThread extends Thread {

		private boolean isArabicNumerals;

		TimerThread(boolean isArabicNumerals) {
			this.isArabicNumerals = isArabicNumerals;
		}

		@SuppressLint("SetTextI18n")
		@Override
		public void run() {
			while (raceIsRunning) {
				try {
					seconds[0]++;
					final int minutes = seconds[0] / 60;
					final int sec = seconds[0] - minutes * 60;
					if (binding != null) {
						runOnUiThread(() -> {
							if (isArabicNumerals)
								binding.timeText.setText((minutes < 10 ?
										GameData.easternArabicNumerals[0] + converter.convertToEstArabic(minutes) : converter.convertToEstArabic(minutes))
										+ ":" + (sec < 10 ? GameData.easternArabicNumerals[0] + converter.convertToEstArabic(sec) : converter.convertToEstArabic(sec)));
							else
								binding.timeText.setText((minutes < 10 ? "0" + minutes : minutes) + ":" + (sec < 10 ? "0" + sec : sec));
						});
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}