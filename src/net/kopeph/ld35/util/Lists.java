package net.kopeph.ld35.util;

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

	public static boolean contains(String[] list, String value) {
		for (String string : list)
			if (string.equals(value))
				return true;
		return false;
	}
}
