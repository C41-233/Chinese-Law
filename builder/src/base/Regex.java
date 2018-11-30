package base;

public final class Regex {

    private static final String NumberBase_2 = "二|三|四|五|六|七|八|九";
    private static final String NumberBase = "一|" + NumberBase_2;

    public static final String ChineseNumber = String.format(
        "(%s)|十(%s)?|(%s)十(%s)?",
        NumberBase,
        NumberBase,
        NumberBase_2, NumberBase
    );

    public static final String Number = "[1-9]+[0-9]*";

    public static boolean isMatch(String value, String[] regexes){
        for(String regex : regexes){
            if(value.matches("^" + regex + "$")){
                return true;
            }
        }
        return false;
    }

}
