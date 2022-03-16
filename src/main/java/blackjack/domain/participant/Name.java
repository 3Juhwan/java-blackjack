package blackjack.domain.participant;

public class Name {
	private static final String BLANK = "";
	private static final String ERROR_MESSAGE_EMPTY_NAME = "[ERROR] 이름은 공백일 수 없습니다.";
	private static final String ALLOWED_CHARACTERS = ".*[^0-9a-zA-Zㄱ-ㅎ가-힣_]+.*";
	private static final String ERROR_MESSAGE_UNAVAILABLE_CHARACTER = "[ERROR] 이름에 특수문자가 포함될 수 없습니다.";

	private final String name;

	private Name(String name) {
		this.name = name;
	}

	public static Name from(String name) {
		return new Name(validateName(name));
	}

	private static String validateName(String input) {
		String name = input.trim();
		checkBlankName(name);
		checkUnavailableName(name);
		return name;
	}

	private static void checkBlankName(String input) {
		if (input.equals(BLANK)) {
			throw new IllegalArgumentException(ERROR_MESSAGE_EMPTY_NAME);
		}
	}

	private static void checkUnavailableName(String input) {
		if (input.matches(ALLOWED_CHARACTERS)) {
			throw new IllegalArgumentException(ERROR_MESSAGE_UNAVAILABLE_CHARACTER);
		}
	}

	public String getName() {
		return name;
	}
}
