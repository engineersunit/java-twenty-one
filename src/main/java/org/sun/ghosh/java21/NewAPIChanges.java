import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        System.out.println(builder.toString());


        // Character isEmoji
        // JDK-8303018 Unicode Emoji Properties
            // https://bugs.openjdk.org/browse/JDK-8303018
            // https://git.openjdk.org/jdk/commit/f593a6b52ee7161f7d63bfaf04062551c1281e61
            // https://www.unicode.org/Public/15.0.0/ucd/emoji/emoji-data.txt
        // JDK-8305107 Emoji related binary properties in RegEx
            // https://bugs.openjdk.org/browse/JDK-8305107
            // https://git.openjdk.org/jdk/commit/ee3023359caed3be4fe4cd829f04ede99d17ae86

        System.out.println(Character.isLetter('A')); // true
        String java = "I ❤️ ☕ Code";
        System.out.println(java);
        var emojiCodePoint = Character.codePointAt(
                                java , 5); // Coffee emoji
        System.out.println(emojiCodePoint); // 9749
        System.out.println(
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
        System.out.println(emojiJSON);

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
        System.out.println(emojiMatcher.matches());//true

        String comment = "I ❤️ ☕ Code";
        boolean containsEmoji = comment
                                .codePoints()
                                .mapToObj(c -> (char) c)
                                .anyMatch(c -> emojiPattern
                                    .matcher(String.valueOf(c))
                                    .matches());
        System.out.println(containsEmoji);//true
        }
