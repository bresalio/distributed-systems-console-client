package model;

public enum Opinion {

	VERY_BAD,
	RATHER_BAD,
	NEUTRAL,
	RATHER_GOOD,
	VERY_GOOD,
	NOT_FOUND;
	
	public static String toStringCustom(Opinion opinion) {
		switch (opinion) {
		case VERY_BAD:
			return "Very bad!";
		case RATHER_BAD:
			return "Rather bad...";
		case NEUTRAL:
			return "Neutral";
		case RATHER_GOOD:
			return "Rather good...";
		case VERY_GOOD:
			return "Very good!";
		default:
			return "Not found!";
		}
	}
	
	public static Opinion convert(int number) {
		switch (number) {
		case 1:
			return VERY_BAD;
		case 2:
			return RATHER_BAD;
		case 3:
			return NEUTRAL;
		case 4:
			return RATHER_GOOD;
		case 5:
			return VERY_GOOD;
		default:
			return NOT_FOUND;
		}
	}
	
}