/**
 * JEP 441: Pattern Matching for Switch
 *      https://openjdk.org/jeps/441
 * JDK-8300543 Compiler Implementation for Pattern Matching for switch
 *      https://git.openjdk.org/jdk/commit/eaa80ad08c949a05abcfa48897654ed52139145b
 */

import static java.lang.System.out;

// JEP 445: Unnamed Classes and Instance Main Methods (Preview)
void main(){
        print(formatValue("Java 21"));
        print(format21Value(Integer.MAX_VALUE));
        generateResponse(null);
        generate21Response("Hello");
        testInput("Yes");
        test21Input("No");
        print(validateInputAndRespond(null));
        exhaustiveSwitchWithoutEnumSupport(IndoorGame.BALL);
        exhaustiveSwitchWithBetterEnumSupport(IndoorGame.PUZZLE);
}

static void print(Object object){
        out.println(object);
}

/**
 * Prior to Java 21 -
 * Used chained if-else with
 * instanceof operator
 */
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

/**
 * Java 21 - Allows case labels with patterns (eg. Integer i)
 */
static String format21Value(Object obj) {
        return switch (obj) {
        case Integer i -> String.format("Integer %d", i);
        case Long l    -> String.format("Long %d", l);
        case Double d  -> String.format("Double %f", d);
        case String s  -> String.format("String %s", s);
        default        -> obj.toString();
        };
}

/**
 * Prior to Java 21 -
 * switch statements and expressions
 * throws NullPointerException if
 * the selector expression evaluates to null
 */
static void generateResponse(String s) {
        if (s == null) {
            print("Nothing to say!");
            return;
        }
        switch (s) {
        case "Hello", "Bye" -> print("See you!");
        default             -> print("Same to you!");
        }
}

/** Java 21
 * Allows a new null case label
 */
static void generate21Response(String s) {
        switch (s) {
        case null           -> print("Nothing to say!");
        case "Hello", "Bye" -> print("See you!");
        default             -> print("Same to you!");
        }
}


/** Java 21
 * Pattern case label can apply to many values
 * Leads to conditional code on the
 * right-hand side of a switch rule
 */
static void testInput(String response) {
        switch (response) {
        case null -> print("Nothing to say!");
        case String s -> {
            if (s.equalsIgnoreCase("YES"))
                print("Welcome!");
            else if (s.equalsIgnoreCase("NO"))
                print("Good Bye!");
            else
                print("Retry!");
        }
    }
}

/** Java 21
 * We can rewrite the above code using guards.
 * Allows when clauses in switch blocks to
 * specify guards to pattern case labels
 */
static void test21Input(String response) {
        switch (response) {
        case null -> print("Nothing to say!");
        case String s
        when s.equalsIgnoreCase("YES") -> {
                print("Welcome!");
        }
        case String s
        when s.equalsIgnoreCase("NO") -> {
                print("Good Bye!");
        }
        case String s -> {
                print("Retry!");
        }
}
}


/** Java 21
 * This leads to a more readable style of
 * switch programming where the complexity of
 * the test appears on the left of a switch rule, and
 * the logic that applies if that test is satisfied is
 * on the right of a switch rule
 */
static String validateInputAndRespond(String response) {
        return switch (response) {
        case null     -> "Nothing to say!";
        case "y", "Y" -> "Welcome!";
        case "n", "N" -> "Good Bye!";
        case String s when
         s.equalsIgnoreCase("YES") -> "Welcome!";
        case String s when
         s.equalsIgnoreCase("NO")  -> "Good Bye!";
        case String s -> "Retry!";
        };
}

/**
 * Given, the following interface and its implementations
 */
sealed interface GameClassification
        permits IndoorGame, OutdoorGame {}
public enum IndoorGame implements GameClassification
        { BOARD, BALL, VIDEO, PUZZLE }
final class OutdoorGame implements GameClassification {}

/** Java 21
 * Allows qualified names of enum constants to
 * appear as case constants (eg. IndoorGame.BOARD)
 *
 * Dropped the requirement that the selector expression be
 * of an enum type when the name of one of that enum's constants is
 * used as a case constant
 * @param c
 */

static void exhaustiveSwitchWithoutEnumSupport(GameClassification c) {
        switch (c) {
                case IndoorGame s when
                        s == IndoorGame.BOARD ->
                         print("It's BOARD");
                case IndoorGame s when
                        s == IndoorGame.BALL  ->
                         print("It's BALL");
                case IndoorGame s
                        when s == IndoorGame.VIDEO ->
                         print("It's VIDEO");
                case IndoorGame s  -> print("It's PUZZLE");
                case OutdoorGame t -> print("It's a OutdoorGame");
        }
}


// As of Java 21

/**
 * Relaxed the requirement that the
 * selector expression be of the enum type and
 * we allow case constants to use
 * qualified names of enum constants.
 * @param c
 */
static void exhaustiveSwitchWithBetterEnumSupport(GameClassification c) {
        switch (c) {
                case IndoorGame.BOARD ->
                        print("It's BOARD");
                case IndoorGame.BALL  ->
                        print("It's BALL");
                case IndoorGame.VIDEO ->
                        print("It's VIDEO");
                case IndoorGame.PUZZLE->
                        print("It's PUZZLE");
                case OutdoorGame t    ->
                        print("It's a OutdoorGame");
        }
}