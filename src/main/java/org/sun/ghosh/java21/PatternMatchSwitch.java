/**
 * JEP 441: Pattern Matching for switch
 *      https://openjdk.org/jeps/441
 * JDK-8300543 Compiler Implementation for Pattern Matching for switch
 *      https://git.openjdk.org/jdk/commit/eaa80ad08c949a05abcfa48897654ed52139145b
 */

import static java.lang.System.out;
void main(){
        out.println(formatValue("Java 21"));
        out.println(format21Value(Integer.MAX_VALUE));
        generateResponse(null);
        generate21Response("Hello");
        testInput("Yes");
        test21Input("No");
        test21InputEnhanced(null);
        exhaustiveSwitchWithoutEnumSupport(IndoorGame.BALL);
        exhaustiveSwitchWithBetterEnumSupport(IndoorGame.PUZZLE);
}

// Prior to Java 21
static String formatValue(Object obj) {
        String formatted = "Unknown";
        if (obj instanceof Integer i) {
            formatted = String.format("Integer %d", i);
        } else if (obj instanceof Long l) {
            formatted = String.format("Long %d", l);
        } else if (obj instanceof Double d) {
            formatted = String.format("Double %f", d);
        } else if (obj instanceof String s) {
            formatted = String.format("String %s", s);
        }
        return formatted;
    }

// Java 21
static String format21Value(Object obj) {
        return switch (obj) {
        case Integer i -> String.format("Integer %d", i);
        case Long l    -> String.format("Long %d", l);
        case Double d  -> String.format("Double %f", d);
        case String s  -> String.format("String %s", s);
        default        -> obj.toString();
        };
}

// Prior to Java 21
static void generateResponse(String s) {
        if (s == null) {
            out.println("Nothing to say!");
            return;
        }
        switch (s) {
        case "Hello", "Bye" -> out.println("See you!");
        default             -> out.println("Same to you!");
        }
}

// Java 21
static void generate21Response(String s) {
        switch (s) {
        case null           -> out.println("Nothing to say!");
        case "Hello", "Bye" -> out.println("See you!");
        default             -> out.println("Same to you!");
        }
}


// Prior to Java 21
static void testInput(String response) {
        switch (response) {
        case null -> { out.println("Nothing to say!"); }
        case String s -> {
            if (s.equalsIgnoreCase("YES"))
                out.println("Welcome!");
            else if (s.equalsIgnoreCase("NO"))
                out.println("Good Bye!");
            else
                out.println("Retry!");
        }
    }
}

// Java 21
static void test21Input(String response) {
        switch (response) {
        case null -> { out.println("Nothing to say!"); }
        case String s
        when s.equalsIgnoreCase("YES") -> {
                out.println("Welcome!");
        }
        case String s
        when s.equalsIgnoreCase("NO") -> {
                out.println("Good Bye!");
        }
        case String s -> {
                out.println("Retry!");
        }
}
}


// Java 21
static void test21InputEnhanced(String response) {
        switch (response) {
        case null -> { out.println("Nothing to say!"); }
        case "y", "Y" -> {
                out.println("Welcome!");
        }
        case "n", "N" -> {
                out.println("Good Bye!");
        }
        case String s
        when s.equalsIgnoreCase("YES") -> {
                out.println("Welcome!");
        }
        case String s
        when s.equalsIgnoreCase("NO") -> {
                out.println("Good Bye!");
        }
        case String s -> {
                out.println("Retry!");
        }
        }
}


// As of Java 21
sealed interface GameClassification
        permits IndoorGame, OutdoorGame {}
public enum IndoorGame implements GameClassification
{ BOARD, BALL, VIDEO, PUZZLE }
final class OutdoorGame implements GameClassification {}

static void exhaustiveSwitchWithoutEnumSupport(GameClassification c) {
        switch (c) {
                case IndoorGame s when s == IndoorGame.BOARD -> {
                        out.println("It's BOARD");
                }
                case IndoorGame s when s == IndoorGame.BALL -> {
                        out.println("It's BALL");
                }
                case IndoorGame s when s == IndoorGame.VIDEO -> {
                        out.println("It's VIDEO");
                }
                case IndoorGame s -> {
                        out.println("It's PUZZLE");
                }
                case OutdoorGame t -> {
                        out.println("It's a OutdoorGame");
                }
        }
}


// As of Java 21

        /**
         * We therefore relax the requirement that the
         * selector expression be of the enum type and
         * we allow case constants to use
         * qualified names of enum constants.
         * @param c
         */
        static void exhaustiveSwitchWithBetterEnumSupport(GameClassification c) {
        switch (c) {
                case IndoorGame.BOARD -> {
                        out.println("It's BOARD");
                }
                case IndoorGame.BALL -> {
                        out.println("It's BALL");
                }
                case IndoorGame.VIDEO -> {
                        out.println("It's VIDEO");
                }
                case IndoorGame.PUZZLE -> {
                        out.println("It's PUZZLE");
                }
                case OutdoorGame t -> {
                        out.println("It's a OutdoorGame");
                }
        }
}

