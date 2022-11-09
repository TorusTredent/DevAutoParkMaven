package by.incubator.parser;

public class StringParser {

    private static final String REGEX_FOR_STRING_NUMBERS = "(\")(\\d+)(,)(\\d+)(\")";

    public static String[] convertStringTextToStringFields(String text) {
        return text.replaceAll(REGEX_FOR_STRING_NUMBERS, "$2" + "." + "$4").split(",");
    }
}
