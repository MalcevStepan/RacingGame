package com.example.racinggame;

// converter to Eastern Arabic numbers

public class NumeralConverter {

	public String convertToArabic(String easternArabic) {
		char[] arr = easternArabic.toCharArray(), res = new char[arr.length];
		for (int i = 0; i < arr.length; i++)
			if (arr[i] != '.' || arr[i] != '-')
				res[i] = String.valueOf(indexOfEstArabic(arr[i])).toCharArray()[0];
		return String.valueOf(res);
	}

	public int indexOfEstArabic(char c) {
		for (int i = 0; i < GameData.easternArabicNumerals.length; i++)
			if (GameData.easternArabicNumerals[i] == c) return i;
		return -1;
	}

	public String convertToEstArabic(float num) {
		boolean isFractionNum = num > (int) num;
		char[] number;
		if (isFractionNum)
			number = String.valueOf(num).toCharArray();
		else number = String.valueOf((int) num).toCharArray();
		for (int i = 0; i < number.length; i++)
			if (number[i] != '.' && number[i] != '-')
				number[i] = GameData.easternArabicNumerals[Integer.parseInt(String.valueOf(number[i]))];
		return String.valueOf(number);
	}

	public String convertToEstArabic(String num) {
		return num.replaceAll("0", String.valueOf(GameData.easternArabicNumerals[0]))
				.replaceAll("1", String.valueOf(GameData.easternArabicNumerals[1]))
				.replaceAll("2", String.valueOf(GameData.easternArabicNumerals[2]))
				.replaceAll("3", String.valueOf(GameData.easternArabicNumerals[3]))
				.replaceAll("4", String.valueOf(GameData.easternArabicNumerals[4]))
				.replaceAll("5", String.valueOf(GameData.easternArabicNumerals[5]))
				.replaceAll("6", String.valueOf(GameData.easternArabicNumerals[6]))
				.replaceAll("7", String.valueOf(GameData.easternArabicNumerals[7]))
				.replaceAll("8", String.valueOf(GameData.easternArabicNumerals[8]))
				.replaceAll("9", String.valueOf(GameData.easternArabicNumerals[9]));
	}

}
