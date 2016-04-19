package net.kopeph.ld35.util;

import java.util.Random;

public final class Lists {
	public static String[] solids = {
		"Banana Solid.svg",
		"Circle.svg",
		"Diamond.svg",
		"Soft Rect 2.svg",
		"Soft Rect.svg",
		"Spark.svg",
		"Wonk Rect.svg"
	};

	public static String[] dingbats = {
		"Dingbat Star Fullcent.svg",
		"Dingbat Star Fullcirc.svg",
		"Dingbat Star Halfcent.svg",
		"Dingbat Star Halfcirc.svg",
		"Dingbat Star.svg"
	};

	public static int[] colors = {
			0xFFFF725A,
			0xFFF37C47,
			0xFFE78533,
			0xFFF7923E,
			0xFFE49D23,
			0xFFCD9411,
			0xFFC6A017,
			0xFFBDAD1D,
			0xFFAEA008,
			0xFFADB21A,
			0xFFA9C428,
			0xFF91B030,
			0xFF83BB35,
			0xFF71AE40,
			0xFF62C054,
			0xFF4FB253,
			0xFF20C46F,
			0xFF3AA6E9,
			0xFF65B1F6,
			0xFF65A1E9,
	};

	public static boolean contains(String[] list, String value) {
		for (String string : list)
			if (string.equals(value))
				return true;
		return false;
	}

	private static final Random rand = new Random();

	public static int oneOf(int[] list) {
		return list[rand.nextInt(list.length)];
	}
}
