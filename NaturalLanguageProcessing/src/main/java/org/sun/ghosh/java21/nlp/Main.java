package org.sun.ghosh.java21.nlp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Main {

    public static final String DIV_NAME_MW_CONTENT_TEXT = "#mw-content-text";
    public static final String OUTPUT_FILE_PATH_RAW_HTML = "./NaturalLanguageProcessing/src/main/java/org/sun/ghosh/java21/nlp/MySite.html";
    public static final String OUTPUT_FILE_PATH_TEXT = "./NaturalLanguageProcessing/src/main/java/org/sun/ghosh/java21/nlp/MySite.txt";

    public static void main(String[] args) {
        Instant startTime = Instant.now();
        String inputUrl = null;
        if (args == null || args.length == 0 || args[0] == null) {
            log("""
                    No arguments were passed.
                    Defaulting to the favourite URL:
                    https://en.wikipedia.org/wiki/Java_(programming_language)""");
            inputUrl = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        } else {
            inputUrl = args[0];
        }

        // Get URL from the user and validate
        URL url = null;
        try {
            url = new URI(inputUrl).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Failed during validating the URL: " + inputUrl, e);
        }

        // Read the web site content
        boolean readRawContent = false;
        String siteContent = null;

        if (readRawContent) {
            try (var in = url.openStream()) {
                siteContent = new String(in.readAllBytes(),
                        StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
                siteContent = String.format("The site %s was not fetched " +
                                "due to " +
                                "exception %s, details: %s", url,
                        e.getClass(), e.getMessage());
            }
        } else {
            try {
                Document doc = Jsoup.connect(inputUrl).get();
                // Select the main text content of the wiki
                Elements mainContent = doc.select(DIV_NAME_MW_CONTENT_TEXT);
                StringBuilder sb = new StringBuilder();
                // Select the main text content paragraphs of the wiki
                for (Element pElem : mainContent.select("p")) {
                    sb.append(pElem.text());
                }
                siteContent = sb.toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed during fetching and parsing via JSoup library", e);
            }
        }
        // Write the site content to a file
        try (FileWriter fw = new FileWriter(readRawContent ? OUTPUT_FILE_PATH_RAW_HTML : OUTPUT_FILE_PATH_TEXT)) {
            fw.write(siteContent);
        } catch (IOException e) {
            throw new RuntimeException("Failed during writing the site content to the local file:" +
                    (readRawContent ? OUTPUT_FILE_PATH_RAW_HTML : OUTPUT_FILE_PATH_TEXT), e);
        }

        Instant endTime = Instant.now();
        Duration between = Duration.between(startTime, endTime);
        System.out.println(String.format("Took %d seconds and %d nanoseconds",
                between.get(ChronoUnit.SECONDS),
                between.get(ChronoUnit.NANOS)));
    }

    static void log(String s) {
        System.out.println(s);
    }
}