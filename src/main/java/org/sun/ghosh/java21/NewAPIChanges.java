import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Arrays;

void main(){

        // JDK-8302686 Add repeat methods to StringBuilder/StringBuffer
        // https://bugs.openjdk.org/browse/JDK-8302686
       /*
            Baby Shark, doo-doo, doo-doo
            Baby Shark, doo-doo, doo-doo
            Baby Shark, doo-doo, doo-doo
            Baby Shark
            Mommy Shark, doo-doo, doo-doo
            Mommy Shark, doo-doo, doo-doo
            Mommy Shark, doo-doo, doo-doo
            Mommy Shark
            Daddy Shark, doo-doo, doo-doo
            Daddy Shark, doo-doo, doo-doo
            Daddy Shark, doo-doo, doo-doo
            Daddy Shark
        */
        var builder=new StringBuilder();
        builder.append("My new peom:\n");
        builder.repeat("Baby Shark, doo-doo, doo-doo\n",3);
        builder.append("Baby Shark\n");
        builder.repeat("Mommy Shark, doo-doo, doo-doo\n",3);
        builder.append("Mommy Shark\n");
        builder.repeat("Daddy Shark, doo-doo, doo-doo\n",3);
        builder.append("Daddy Shark\n");
        print(builder.toString());


        // Character isEmoji
        // JDK-8303018 Unicode Emoji Properties
            // https://bugs.openjdk.org/browse/JDK-8303018
            // https://git.openjdk.org/jdk/commit/f593a6b52ee7161f7d63bfaf04062551c1281e61
            // https://www.unicode.org/Public/15.0.0/ucd/emoji/emoji-data.txt
        // JDK-8305107 Emoji related binary properties in RegEx
            // https://bugs.openjdk.org/browse/JDK-8305107
            // https://git.openjdk.org/jdk/commit/ee3023359caed3be4fe4cd829f04ede99d17ae86

        print(Character.isLetter('A')); // true
        String java = "I ❤️ ☕ Code";
        print(java);
        var emojiCodePoint = Character.codePointAt(
                                java , 5); // Coffee emoji
        print(emojiCodePoint); // 9749
        print(
                Character.isEmoji(
                        emojiCodePoint)); // true

        var ecp = emojiCodePoint; // for brevity in String Templates
        String emojiJSON = STR."""
        {
            "Presentation": "\{Character.isEmojiPresentation(ecp)}"
            "Modifier": "\{Character.isEmojiModifier(ecp)}"
            "ModifierBase": "\{Character.isEmojiModifierBase(ecp)}"
            "Component": "\{Character.isEmojiComponent(ecp)}"
            "ExtendedPictographic": "\{Character.isExtendedPictographic(ecp)}"
        }
        """;
        print(emojiJSON);

        // Regex for emoji
        /**
         * Matcher emojiP   = Pattern.compile("\\p{IsEmoji}").matcher("");
         * Matcher emojiPP  = Pattern.compile("\\p{IsEmoji_Presentation}").matcher("");
         * Matcher emojiMP  = Pattern.compile("\\p{IsEmoji_Modifier}").matcher("");
         * Matcher emojiMBP = Pattern.compile("\\p{IsEmoji_Modifier_Base}").matcher("");
         * Matcher emojiCP  = Pattern.compile("\\p{IsEmoji_Component}").matcher("");
         */
        Pattern emojiPattern = Pattern
                                .compile("\\p{IsEmoji}");

        Matcher emojiMatcher = emojiPattern
                                .matcher("☕");
        print(emojiMatcher.matches());//true

        String comment = "I ❤️ ☕ Code";
        boolean containsEmoji = comment
                                .codePoints()
                                .mapToObj(c -> (char) c)
                                .anyMatch(c -> emojiPattern
                                    .matcher(String.valueOf(c))
                                    .matches());
        print(containsEmoji);//true


        /**
         * JDK-8305488
         * Add split() variants that keep the delimiters to
         * String and j.u.r.Pattern
         * Commit: https://git.openjdk.org/jdk/commit/93ee19f58aa8c436c2960d171ba4646a374aa2e3
         * Methods Added to String class -
         * - splitWithDelimiters(String regex, int limit)
         * Methods Added to Pattern class -
         * - splitWithDelimiters(String regex, int limit)         *
         */

        var telegramMessage = """
                              GOT JAVA 21 CERTIFICATION TODAY STOP
                              POSTED TO LINKEDIN STOP
                              HIGH PAYING JOB FOLLOWS STOP
                              """;
        var splitMsg = telegramMessage.split("STOP", 0);
        print(Arrays.toString(splitMsg));

        splitMsg = telegramMessage.
                        splitWithDelimiters("STOP", 0);
        print(Arrays.toString(splitMsg));

        /**
         * JDK-8303650
         * Add String.indexOf(String str, int beginIndex, int endIndex)
         * Add an indexOf(String,int,int) variant allowing to specify both
         * a lower and an upper bound on the search.
         * Methods Added to String class -
         *     indexOf(String str, int beginIndex, int endIndex)
         * Commit: https://github.com/openjdk/jdk/commit/4bf1fbb06d63b4c52bfd3922beb2adf069e25b09
         */
        print(telegramMessage.indexOf("21", 0, 10));//-1
        print(telegramMessage.indexOf("21", 0, 15));//9
        }

static void print(Object object){
        System.out.println(object);
        }
