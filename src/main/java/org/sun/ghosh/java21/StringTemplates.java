package org.sun.ghosh.java21;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.File;
import java.io.StringReader;
import java.time.format.DateTimeFormatter;

import jakarta.json.JsonReader;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.lang.StringTemplate;

/**
 * JEP 430: String Templates (Preview)
 * https://openjdk.org/jeps/430
 * Code Changes
 * https://github.com/openjdk/jdk/commit/4aa65cbeefe44f29fbe6ea013809dcee579df9ff
 * JDK-8285932 Implementation of JEP 430 String Templates (Preview)
 * https://bugs.openjdk.org/browse/JDK-8285932
 * JDK-8273943 JEP 430: String Templates (Preview)
 * https://bugs.openjdk.org/browse/JDK-8273943
 * OpenJDK JDK 21 Early-Access Builds
 * https://jdk.java.net/21/ - Build 22 (2023/5/11)
 */
public class StringTemplates {

    public static void main(String[] args) {
        int x = 10;
        int y = 20;

        // String concatenation with the + operator produces
        // hard-to-read code
        String s = x + " plus " + y + " equals " + (x + y);
        System.out.println(s);

        // StringBuilder is verbose
        s = new StringBuilder()
                .append(x)
                .append(" plus ")
                .append(y)
                .append(" equals ")
                .append(x + y)
                .toString();
        System.out.println(s);

        /*
        String::format and String::formatted separate the
        format string from the parameters,
        inviting arity and type mismatches
         */
        s = String.format("%2$d plus %1$d equals %3$d", x, y, x + y);
        String t = "%2$d plus %1$d equals %3$d".formatted(x, y, x + y);
        System.out.println(s);
        System.out.println(t);


        // java.text.MessageFormat requires too much ceremony and
        // uses an unfamiliar syntax in the format string
        MessageFormat mf = new MessageFormat("{0} plus {1} equals {2}");
        s = mf.format("{0} plus {1} equals {2}", x, y, x + y);
        System.out.println(s);

        // The STR template processor
        /*
        STR is a template processor defined in the Java Platform.

        It performs string interpolation by replacing each
        embedded expression in the template with the
        (stringified) value of that expression.

        The result of evaluating a template expression
        which uses STR is a String

        STR is a public static final field that is automatically
        imported into every Java source file.

         */

        // Simple use case
        String name = "Sunit Ghosh";
        String programmingLanguage = "Java";
        String info = STR."My name is \{name}. I love programming in \{programmingLanguage}.";
        System.out.println(info);

        // Embedded expressions can be strings
        String firstName = "Sunit";
        String lastName = "Ghosh";
        String fullName = STR."\{firstName} \{lastName}";
        String sortName = STR."\{lastName}, \{firstName}";
        System.out.println(fullName);
        System.out.println(sortName);

        // Embedded expressions can perform arithmetic
        s = STR. "\{x} + \{y} = \{x + y}";

        // Embedded expressions can invoke methods
        s = STR. "You have a \{getMessageType()} waiting for you!";
        System.out.println(s);

        // Embedded expressions can invoke access fields
        AccessRequest request = new AccessRequest(
                LocalDate.now(),
                LocalTime.now(),
                "172.0.0.4");
        s = STR. "Access at \{request.date} \{request.time} from \{request.ipAddress}";
        System.out.println(s);

        // To aid refactoring, double-quote characters can be used
        // inside embedded expressions without escaping them as \"
        String filePath = "tmp.dat";
        File file = new File(filePath);
        String old = "The file " + filePath + " " +
                (file.exists() ? "does" : "does not") +
                " exist";
        String msg = STR.
        "The file \{filePath} \{file.exists() ? "does" : "does not"} exist";
        System.out.println(old);
        System.out.println(msg);

        // An embedded expression can be spread over multiple lines
        // in the source file without introducing newlines into the result
        s = STR."The time is \{
        // Adding a comment: using java.time.format package
                    DateTimeFormatter
                            .ofPattern("HH:mm:ss")
                            .format(LocalTime.now())
                    } right now";
        System.out.println(s);

        // Embedded expressions can be postfix increment expressions
        int index = 0;
        s = STR."\{index++}, \{index--}, \{index++}, \{index--}";
        System.out.println(s);

        // Embedded expression in a (nested) template expression
        String[] skills = { "Java", "Scala", "Groovy" };
        s = STR."\{skills[0]}, \{
                                STR."\{skills[1]}, \{skills[2]}"
                                }";
        System.out.println(s);

        // Multi-line template expressions
        String title = "My Java Blog";
        String text  = "Hello, Java 21";
        String html = STR."""
        <html>
          <head>
            <title>\{title}</title>
          </head>
          <body>
            <p>\{text}</p>
          </body>
        </html>
        """;
        System.out.println(html);

        String userName    = "Sunit Ghosh";
        String phone   = "555-123-4567";
        String address = "India, Earth";
        String json = STR."""
        {
            "name":    "\{userName}",
            "phone":   "\{phone}",
            "address": "\{address}"
        }
        """;

        System.out.println(json);


        // Formatting in a tabular format
        Rectangle[] zone = new Rectangle[] {
                new Rectangle("Alfa", 17.8, 31.4),
                new Rectangle("Bravo", 9.6, 12.4),
                new Rectangle("Charlie", 7.1, 11.23),
        };
        String table = STR."""
            Descrip     Width  Height  Area
            \{zone[0].name}     \{zone[0].width}  \{zone[0].height}  \{zone[0].area()}
            \{zone[1].name}     \{zone[1].width}  \{zone[1].height}  \{zone[1].area()}
            \{zone[2].name}     \{zone[2].width}  \{zone[2].height}  \{zone[2].area()}
            Total \{zone[0].area() + zone[1].area() + zone[2].area()}
            """;
        System.out.println(table);

        String jsonStringFormat = """
            {
                "userName": "%s",
                "phone"   : "%s",
                "address" : "%s"
            }
            """.formatted(userName, phone, address);

        JsonObject docFromStringFormatted = null;
        try (JsonReader jsonReader = Json.createReader(
                new StringReader(jsonStringFormat))) {
            docFromStringFormatted =  jsonReader.readObject();
        }
        System.out.println(docFromStringFormatted);


        var JSON = StringTemplate.Processor.of(
                    (StringTemplate st) -> {
            try (JsonReader jsonReader = Json.createReader(
                    new StringReader(st.interpolate()))) {
                return jsonReader.readObject();
            }
        }
        );

        userName = "Sunit Ghosh";
        phone   = "555-123-4567";
        address = "India, Earth";

        JsonObject doc = JSON."""
        {
            "name":    "\{userName}",
            "phone":   "\{phone}",
            "address": "\{address}"
        }
        """;

        System.out.println(doc.getClass());
        System.out.println("String Template to generate a JSON " +
                            "from a given String");
        System.out.println(doc);




    }

    private static String getMessageType() {
        return "proposal";
    }

    record AccessRequest(LocalDate date,
                         LocalTime time,
                         String ipAddress) {
    }

    record Rectangle(String name,
                     double width,
                     double height) {
        double area() {
            return width * height;
        }
    }
}