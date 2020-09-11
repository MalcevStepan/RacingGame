package com.example.racinggame;

import android.util.Log;

public final class GameData {

	public static final String APP_PREFERENCES = "settings",
			APP_PREFERENCES_UI_MODE = "ui_mode",
			APP_PREFERENCES_NUMERALS = "numerals",
			APP_PREFERENCES_USER_COLOR = "user_color",
			APP_PREFERENCES_OPERATION = "operation",
			APP_PREFERENCES_OPERATION_LEVEL = "operation_level",
			APP_PREFERENCES_SPEED_LEVEL = "speed_level",
			APP_PREFERENCES_REFERENCE_SPEED = "reference_speed",
			APP_PREFERENCES_SPEED_LEVEL_COEF_2 = "coef_2",
			APP_PREFERENCES_SPEED_LEVEL_COEF_3 = "coef_3",
			APP_PREFERENCES_SPEED_LEVEL_COEF_4 = "coef_4",
			APP_PREFERENCES_SPEED_LEVEL_COEF_5 = "coef_5",
			APP_PREFERENCES_USER_CONSTANT_SPEED = "user_constant_speed",
			APP_PREFERENCES_PULSE_AMPLITUDE = "pulse_amplitude",
			WRONG_ANSWER_COUNT = "wrong", CORRECT_ANSWER_COUNT = "correct";

	public static final char[] easternArabicNumerals = new char[]{
			'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'
	};


	// look at res/drawable/turn_right_bottom.xml(direct.xml/direct_90.xml) to understand
	public final static RoadType[][][] tracks = new RoadType[][][]{
			new RoadType[][]{
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_TOP_UP
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT, RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_TOP_UP
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT, RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_FINISH_RIGHT, RoadType.TURN_LEFT_TOP_UP
					}
			},
			new RoadType[][]{
					{
							RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_FINISH_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN
					},
					{
							RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN
					},
					{
							RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN
					},
					{
							RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT
					}
			},
			new RoadType[][]{
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT, RoadType.DIRECT_90_UP
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_FINISH_RIGHT, RoadType.TURN_LEFT_TOP_UP
					}
			},
			new RoadType[][]{
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_TOP_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT, RoadType.DIRECT_90_UP
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_FINISH_RIGHT, RoadType.TURN_LEFT_TOP_UP
					}
			},
			new RoadType[][]{
					{
							RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_FINISH_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN
					},
					{
							RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN
					},
					{
							RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT, RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT
					}
			},
			new RoadType[][]{
					{
							RoadType.VOID, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.VOID, RoadType.VOID, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.DIRECT_90_FINISH_UP, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT
					},
					{
							RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_TOP_LEFT, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN
					},
					{
							RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.VOID, RoadType.VOID, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN
					},
					{
							RoadType.VOID, RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT
					}
			},
			new RoadType[][]{
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.DIRECT_90_DOWN, RoadType.DIRECT_90_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_TOP_UP
					},
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_TOP_LEFT, RoadType.TURN_RIGHT_TOP_UP, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_FINISH_RIGHT, RoadType.TURN_LEFT_TOP_UP
					}
			},
			new RoadType[][]{
					{
							RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.DIRECT_LEFT, RoadType.TURN_LEFT_BOTTOM_LEFT
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_TOP_LEFT, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_BOTTOM_RIGHT, RoadType.TURN_LEFT_TOP_UP, RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.TURN_LEFT_BOTTOM_DOWN, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_BOTTOM_LEFT, RoadType.TURN_RIGHT_BOTTOM_DOWN, RoadType.TURN_LEFT_TOP_LEFT, RoadType.DIRECT_90_UP
					},
					{
							RoadType.DIRECT_90_DOWN, RoadType.VOID, RoadType.TURN_RIGHT_TOP_UP, RoadType.TURN_LEFT_TOP_LEFT, RoadType.VOID, RoadType.DIRECT_90_UP
					},
					{
							RoadType.TURN_RIGHT_TOP_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_RIGHT, RoadType.DIRECT_FINISH_RIGHT, RoadType.TURN_LEFT_TOP_UP
					}
			}
	};

	public static final double[] lognormalDistr = new double[]{
			8.868474930353156e-54,
			1.3568095861016406e-08,
			6.25477651938829e-06,
			0.00012244760084088367,
			0.0007669057589079425,
			0.002717987462992772,
			0.00689831339453301,
			0.01410953606731335,
			0.02486444121814276,
			0.039336250556215833,
			0.05738929126795226,
			0.0786494938726992,
			0.10258484470132546,
			0.12857895831275995,
			0.15599046971635588,
			0.1841966822658022,
			0.21262284164662065,
			0.24075959311650752,
			0.26817139861093664,
			0.29449844047187157,
			0.31945410758108156,
			0.3428196999607455,
			0.36443757099599217,
			0.38420357900765356,
			0.40205944554222245,
			0.4179854094275416,
			0.4319934125000851,
			0.44412094364752397,
			0.4544255922498401,
			0.4629803117284597,
			0.4698693619233073,
			0.47518488014898597,
			0.4790240210584809,
			0.481486601923036,
			0.4826731904799955,
			0.4826835755766853,
			0.4816155653742449,
			0.47956406313287875,
			0.4766203760852396,
			0.47287171829814373,
			0.46840087352835785,
			0.4632859887851893,
			0.4576004735687371,
			0.4514129835442314,
			0.44478747075096264,
			0.4377832853549179,
			0.43045531647171,
			0.4228541617487334,
			0.4150263172414789,
			0.40701438068618334,
			0.3988572625947932,
			0.3905904007109673,
			0.38224597429676693,
			0.37385311549479494,
			0.3654381156527967,
			0.3570246250271567,
			0.3486338447157333,
			0.34028471002408234,
			0.33199406475522797,
			0.32377682614278835,
			0.31564614032984145,
			0.3076135284394171,
			0.29968902339366266,
			0.2918812977232743,
			0.2841977826714839,
			0.2766447789417908,
			0.269227559469088,
			0.2619504646126579,
			0.2548169901790557,
			0.24782986868509715,
			0.24099114426761287,
			0.2343022416386684,
			0.2277640294736419,
			0.2213768786058043,
			0.21514071538556337,
			0.20905507054590924,
			0.20311912389829886,
			0.19733174516560417,
			0.1916915312411291,
			0.18619684014528962,
			0.1808458219345359,
			0.17563644680058985,
			0.17056653058219004,
			0.16563375789632376,
			0.16083570308144662,
			0.15616984913144544,
			0.15163360478611929,
			0.1472243199317213,
			0.14293929945360997,
			0.1387758156722928,
			0.13473111948407077,
			0.1308024503180975,
			0.12698704501290572,
			0.12328214570730908,
			0.11968500683302175,
			0.11619290128931281,
			0.11280312587351055,
			0.10951300603514467,
			0.10631990001594731,
			0.10322120243278322,
			0.10021434735582907,
			0.09729681092993589,
			0.09446611358306763,
			0.091719821861982,
			0.08905554993189328,
			0.0864709607736976,
			0.08396376710944127,
			0.08153173208404146,
			0.07917266972881892,
			0.07688444523014794,
			0.07466497502446404,
			0.07251222673896966,
			0.07042421899564111,
			0.06839902109454228,
			0.06643475259098977,
			0.06452958277977379,
			0.06268173009841346,
			0.06088946146030128,
			0.05915109152756431,
			0.05746498193253152,
			0.05582954045583455,
			0.054243220168387236,
			0.05270451854377012,
			0.05121197654689174,
			0.04976417770420194,
			0.048359747160185686,
			0.04699735072437101,
			0.045675693912629704,
			0.044393520986139065,
			0.043149613990996635,
			0.041942791801138815,
			0.04077190916690369,
			0.03963585577129771,
			0.038533555295768976,
			0.037463964497058,
			0.03642607229648854,
			0.035418898882868075,
			0.03444149482999831,
			0.0334929402296388,
			0.032572343840627946,
			0.031678842254738895,
			0.030811599079734336,
			0.029969804139982233,
			0.02915267269490358,
			0.02835944467544048,
			0.027589383938660707,
			0.026841777540549287,
			0.026115935026979932,
			0.025411187742807862,
			0.02472688815898028,
			0.02406240921752098,
			0.023417143694210656,
			0.022790503578754104,
			0.022181919472198736,
			0.021590840001347304,
			0.021016731249886726,
			0.020459076205939945,
			0.019917374225733385,
			0.01939114051306164,
			0.018879905614221572,
			0.0183832149280818,
			0.017900628230947197,
			0.017431719215875236,
			0.01697607504609821,
			0.016533295922204306,
			0.016102994662731147,
			0.01568479629782574,
			0.015278337675627066,
			0.014883267081030722,
			0.014499243866497367,
			0.014125938094571784,
			0.013763030191783258,
			0.013410210613602987,
			0.013067179520139739,
			0.012733646462260387,
			0.012409330077827931,
			0.012093957797755869,
			0.011787265561583729,
			0.011488997542285221,
			0.011198905880027183,
			0.0109167504246035,
			0.010642298486275384,
			0.010375324594756014,
			0.010115610266083612,
			0.009862943777134469,
			0.009617119947533538,
			0.009377939928726833,
			0.009145210999986384,
			0.008918746371125144,
			0.008698364991705036,
			0.008483891366528063,
			0.008275155377206321,
			0.0080719921096126,
			0.007874241687019584,
			0.007681749108740977,
			0.007494364094093822,
			0.007311940931506787,
			0.007134338332604544,
			0.0069614192911035605,
			0.006793050946360111,
			0.006629104451415848,
			0.006469454845391672,
			0.006313980930085031,
			0.006162565150630596,
			0.0060150934800887925,
			0.005871455307830949,
			0.0057315433315941605,
			0.005595253453083184,
			0.005462484677000457,
			0.005333139013389551,
			0.005207121383180992,
			0.005084339526832958,
			0.0049647039159631924,
			0.004848127667871742,
			0.0047345264628574375,
			0.004623818464234461,
			0.004515924240958349,
			0.004410766692773827,
			0.0043082709777998935,
			0.004208364442470385,
			0.004110976553750902,
			0.004016038833555952,
			0.003923484795292338,
			0.003833249882457661,
			0.003745271409225042,
			0.00365948850294761,
			0.003575842048518353,
			0.0034942746345235387,
			0.0034147305011294747,
			0.003337155489644834,
			0.0032614969937026315,
			0.003187703912007727,
			0.0031157266025977483,
			0.0030455168385669375,
			0.0029770277652043037,
			0.002910213858499007,
			0.0028450308849675464,
			0.002781435862758862,
			0.0027193870239950266,
			0.0026588437783064845,
			0.0025997666775223814,
			0.0025421173814777526,
			0.002485858624900655,
			0.0024309541853436835,
			0.0023773688521252978,
			0.0023250683962478856,
			0.0022740195412602887,
			0.002224189935033864,
			0.0021755481224220215,
			0.002128063518774324,
			0.00208170638427722,
			0.0020364477990942324,
			0.001992259639279673,
			0.0019491145534405235,
			0.0019069859401221758,
			0.0018658479258944283,
			0.0018256753441150849,
			0.0017864437143490044,
			0.00174812922242154,
			0.0017107087010857242,
			0.001674159611283359,
			0.0016384600239809357,
			0.0016035886025617403,
			0.0015695245857563246,
			0.0015362477710939909,
			0.0015037384988586227,
			0.001471977636532602,
			0.0014409465637132885,
			0.0014106271574869079,
			0.001381001778245246,
			0
	};

	public static float[] getLevelNumbers(int operation, int operationLevel) {
		int sum, n1 = 0, n2 = 0;
		float num1, num2;
		switch (operation) {
			case 0:
				switch (operationLevel) {
					case 1:
						sum = rand(11, 19);
						n1 = rand(10, sum - 1);
						n2 = sum - n1;
						break;
					case 2:
						sum = rand(10, 12);
						n1 = rand(sum - 9, 9);
						n2 = sum - n1;
						break;
					case 3:
						sum = rand(13, 18);
						n1 = rand(sum - 9, 9);
						n2 = sum - n1;
						break;
					case 4:
						n1 = rand(1, 2) * 10 + rand(1, 8);
						n2 = rand(1, 2) * 10 + rand(1, 9 - n1 % 10);
						break;
					case 5:
						n1 = rand(3, 4) * 10 + rand(1, 8);
						n2 = rand(3, 4) * 10 + rand(1, 9 - n1 % 10);
						break;
					case 6:
						n1 = rand(1, 2) * 10 + rand(1, 8);
						n2 = rand(1, 2) * 10 + rand(10 - n1 % 10, 9);
						break;
					case 7:
						n1 = rand(3, 4) * 10 + rand(1, 8);
						n2 = rand(3, 4) * 10 + rand(10 - n1 % 10, 9);
						break;
					case 8:
						num1 = rand(4, 8) + rand(1, 8) * 0.1f;
						num2 = rand(1, 9 - n1 % 10) + rand(1, 9 - (n1 * 10) % 10) * 0.1f;
						num1 = ((int) (num1 * 10)) / 10f;
						num2 = ((int) (num2 * 10)) / 10f;
						Log.i("sum", ((num1 - (int) num1) + (num2 - (int) num2)) + "");
						if (((num1 - (int) num1) + (num2 - (int) num2)) >= 0.99f || ((int) num1 + (int) num2) >= 9.99f)
							return getLevelNumbers(operation, operationLevel);
						return new float[]{num1, num2};
					case 9:
						n1 = rand(5, 9) * 10 + rand(1, 8);
						n2 = rand(5, 9) * 10 + rand(1, 9 - n1 % 10);
						break;
					case 10:
						n1 = rand(1, 8) * 100 + rand(1, 8) * 10 + rand(1, 8);
						n2 = rand(1, 9 - (n1 / 100) % 10) * 100 + rand(1, 9 - (n1 / 10) % 10) * 10 + rand(1, 9 - n1 % 10);
						break;
					case 11:
						n1 = rand(1, 8) * 100 + rand(1, 8) * 10 + rand(1, 8);
						n2 = rand(1, 9 - (n1 / 100) % 10) * 100 + rand(1, 8 - (n1 / 10) % 10) * 10 + rand(10 - (n1 % 10), 9);
						break;
					case 12:
						n1 = rand(1, 8) * 100 + rand(1, 8) * 10 + rand(1, 8);
						n2 = rand(1, 9 - (n1 / 100) % 10) * 100 + rand(10 - (n1 / 10) % 10, 9) * 10 + rand(10 - (n1 % 10), 9);
						break;
					case 13:
						num1 = rand(1, 8) + rand(1, 9) * 0.1f;
						num2 = rand(1, 9 - (int) (num1 % 10)) + rand(10 - (int) ((num1 * 10) % 10), 9) * 0.1f;
						num1 = ((int) (num1 * 10)) / 10f;
						num2 = ((int) (num2 * 10)) / 10f;
						return new float[]{num1, num2};
					case 14:
						num1 = rand(5, 9) + rand(1, 9) * 0.1f;
						num2 = rand(5, 9) + rand(10 - (int) ((num1 * 10) % 10), 9) * 0.1f;
						num1 = ((int) (num1 * 10)) / 10f;
						num2 = ((int) (num2 * 10)) / 10f;
						return new float[]{num1, num2};
				}
				break;
			case 1:
				switch (operationLevel) {
					case 1:
						n1 = rand(3, 9);
						n2 = rand(1, 3);
						break;
					case 2:
						n1 = rand(7, 10);
						n2 = rand(4, 6);
						break;
					case 3:
						n1 = rand(16, 19);
						n2 = rand(5, n1 % 10);
						break;
					case 4:
						n1 = rand(4, 9) * 10 + rand(6, 9);
						n2 = rand(3, (n1 / 10) % 10) * 10 + rand(1, n1 % 10);
						break;
					case 5:
						n1 = rand(4, 9) * 100 + rand(6, 9) * 10 + rand(6, 9);
						n2 = rand(3, (n1 / 100) % 100) * 100 + rand(3, (n1 / 10) % 10) * 10 + rand(3, n1 % 10);
						break;
					case 6:
						n1 = 10 + rand(1, 5);
						n2 = rand(n1 % 10 + 1, 6);
						break;
					case 7:
						n1 = 10 + rand(1, 5);
						n2 = rand(7, 9);
						break;
					case 8:
						n1 = rand(2, 9) * 10 + rand(1, 5);
						n2 = rand(6, 9);
						break;
					case 9:
						n1 = rand(0, 5);
						n2 = rand(6, 9);
						break;
					case 10:
						n1 = rand(4, 9) * 10 + rand(1, 5);
						n2 = rand(2, n1 % 10 - 1) * 10 + rand(6, 9);
						break;
					case 11:
						num1 = rand(4, 9) + rand(1, 5) * 0.1f;
						num2 = rand(2, n1 % 10 - 1) + rand(6, 9) * 0.1f;
						num1 = ((int) (num1 * 10)) / 10f;
						num2 = ((int) (num2 * 10)) / 10f;
						return new float[]{num1, num2};
					case 12:
						n1 = rand(1, 4) * 10 + rand(1, 5);
						n2 = rand(6, 9) * 10 + rand(6, 9);
						break;
					case 13:
						num1 = rand(1, 4) + rand(1, 5) * 0.1f;
						num2 = rand(6, 9) + rand(6, 9) * 0.1f;
						num1 = ((int) (num1 * 10)) / 10f;
						num2 = ((int) (num2 * 10)) / 10f;
						return new float[]{num1, num2};
				}
				break;
			case 2:
				switch (operationLevel) {
					case 1:
						n1 = 2;
						n2 = rand(2, 9);
						break;
					case 2:
						n1 = 10;
						n2 = rand(2, 10);
						break;
					case 3:
						n1 = 5;
						n2 = rand(2, 9);
						break;
					case 4:
						n1 = rand(3, 4);
						n2 = rand(2, 5);
						break;
					case 5:
						n1 = rand(3, 4);
						n2 = rand(6, 9);
						break;
					case 6:
						n1 = rand(6, 7);
						n2 = rand(6, 9);
						break;
					case 7:
						n1 = rand(8, 9);
						n2 = rand(6, 9);
						break;
					case 8:
						n1 = rand(3, 9) * (((int) (Math.random() * 10) % 2) == 0 ? 1 : -1);
						n2 = rand(3, 9) * (((int) (Math.random() * 10) % 2) == 0 ? 1 : -1);
						break;
					case 9:
						n1 = rand(2, 4) * 10;
						n2 = rand(2, 9);
						break;
					case 10:
						n1 = 15;
						n2 = rand(2, 12);
						break;
					case 11:
						n1 = rand(6, 9) * 10;
						n2 = rand(2, 9);
						break;
					case 12:
						n1 = 25;
						n2 = rand(3, 12);
						break;
					case 13:
						num1 = rand(2, 9) * 0.1f;
						num2 = rand(2, 9) * (((int) (Math.random() * 10) % 2) == 0 ? 0.1f : 1);
						num1 = ((int) (num1 * 10)) / 10f;
						num2 = ((int) (num2 * 10)) / 10f;
						return ((int) (Math.random() * 10) % 2) == 0 ? new float[]{num1, num2} : new float[]{num2, num1};
					case 14:
						num1 = rand(2, 9) * (((int) (Math.random() * 10) % 2) == 0 ? 0.1f : 1) * (((int) (Math.random() * 10) % 2) == 0 ? 1 : -1);
						num2 = rand(2, 9) * (((int) (Math.random() * 10) % 2) == 0 ? 0.1f : 1) * (((int) (Math.random() * 10) % 2) == 0 ? 1 : -1);
						num1 = ((int) (num1 * 10)) / 10f;
						num2 = ((int) (num2 * 10)) / 10f;
						return ((int) (Math.random() * 10) % 2) == 0 ? new float[]{num1, num2} : new float[]{num2, num1};
				}
				return ((int) (Math.random() * 10) % 2) == 0 ? new float[]{n1, n2} : new float[]{n2, n1};
			default:
				switch (operationLevel) {
					case 1:
						n2 = 2;
						n1 = n2 * rand(2, 20);
						break;
					case 2:
						n2 = 2;
						n1 = rand(1, 4) * 2 * 100 + rand(1, 4) * 2 * 10 + rand(1, 4) * 2;
						break;
					case 3:
						n2 = 5;
						n1 = n2 * rand(4, 20);
						break;
					case 4:
						n2 = 4;
						n1 = n2 * rand(2, 20);
						break;
					case 5:
						n2 = 3;
						n1 = n2 * rand(2, 20);
						break;
					case 6:
						n2 = 6;
						n1 = n2 * rand(2, 12);
						break;
					case 7:
						n2 = 7;
						n1 = n2 * rand(2, 12);
						break;
					case 8:
						n2 = 8;
						n1 = n2 * rand(2, 12);
						break;
					case 9:
						n2 = 9;
						n1 = n2 * rand(2, 11);
						break;
					case 10:
						n1 = (rand(0, 4) * 2 + 1) * 100 + (rand(0, 4) * 2 + 1) * 10 + rand(1, 4) * 2;
						n2 = 2;
						break;
					case 11:
						n1 = rand(1, 3) * 3 * 100 + rand(1, 3) * 3 * 10 + rand(1, 3) * 3;
						n2 = 3;
						break;
					case 12:
						n1 = rand(3, 19) * 50;
						n2 = 50;
						break;
					case 13:
						num1 = rand(3, 19) * 0.5f;
						num2 = 0.5f;
						num1 = ((int) (num1 * 10)) / 10f;
						return new float[]{num1, num2};
					case 14:
						n1 = rand(5, 19) * 25;
						n2 = 25;
						break;
					case 15:
						num1 = rand(5, 19) * 0.25f;
						num2 = 0.25f;
						num1 = ((int) (num1 * 10)) / 10f;
						return new float[]{num1, num2};
				}
				break;
		}
		return new float[]{n1, n2};
	}

	private static int rand(int min, int max) {
		max -= min;
		return (int) ((Math.random() * ++max) + min);
	}

	public static final LevelCount[] levelCounts = new LevelCount[]{
			LevelCount.ADDITION, LevelCount.SUBTRACTION, LevelCount.MULTIPLICATION, LevelCount.DIVISION
	};

}

// constants of level counts
enum LevelCount {

	ADDITION(14), SUBTRACTION(13), MULTIPLICATION(14), DIVISION(15);

	private int count;

	LevelCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}
}
