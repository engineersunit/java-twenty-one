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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    /**
     * If true, the program will fetch raw original content (like html) from the URL
     * If false, the program will fetch only the text content parsed from the URL
     */
    private static boolean readRawContent;
    private static final String DIV_NAME_MW_CONTENT_TEXT = "#mw-content-text";
    private static final String OUTPUT_FILE_PATH_RAW_HTML = "./NaturalLanguageProcessing/src/main/java/org/sun/ghosh/java21/nlp/MySite.html";
    private static final String OUTPUT_FILE_PATH_TEXT = "./NaturalLanguageProcessing/src/main/java/org/sun/ghosh/java21/nlp/MySite.txt";
    private static final String OUTPUT_FILE_PATH_NLP_STATS = "./NaturalLanguageProcessing/src/main/java/org/sun/ghosh/java21/nlp/NLPStatistics.txt";

    public static void main(String[] args) {
        Instant startTime = Instant.now();
        // Step 1 - Validate user input
        URL url = validateUserInputAsURL(args);

        // Step 2 - Configure to read only the text
        readRawContent = false;

        // Step 3 - Connect to the URL and get the content
        String textContent = getURLContentAsText(url);

        // Step 4 - Pre-process the text to remove characters other than alphabets
        textContent = textContent.toLowerCase()
                .replaceAll("[^a-z -]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        // Step 5 - Write the content to a local file
        writeToFile(textContent, readRawContent ? OUTPUT_FILE_PATH_RAW_HTML : OUTPUT_FILE_PATH_TEXT);

        // Step 6 - Compute NLP statistics on the content
        log("""
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                Natural Language Processing Statistics
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%""");

        StringBuilder sbNLPStats = new StringBuilder("""
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                Natural Language Processing Statistics
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%""");
        sbNLPStats.append("\n\nFor Source URL: ").append(url.toExternalForm());
        // Word token is an instance of a word in a text or a sequence of words
        String[] words = textContent.split(" ");
        sbNLPStats.append("\n\nWord Count (Word Tokens): ").append(words.length);

        // Bigrams are group of 2 consecutive words
        sbNLPStats.append("\n\nBigram Count: ").append(words.length - 1);
        List<String> bigramList = new ArrayList<>(words.length / 2);
        StringBuilder sbBigram = new StringBuilder();

        for (int i = 0; i < words.length - 2; i++) {
            sbBigram.append(words[i]).append(" ").append(words[i + 1]);
            bigramList.add(sbBigram.toString());
            sbBigram.setLength(0);
        }

        Set<String> uniqueBigrams = new HashSet<>(bigramList);
        sbNLPStats.append("\n\nUnique Bigrams: ").append(uniqueBigrams.size());

        /*
        Word types refers to the unique, distinct words or lexical items in a text or corpus
        Word types represent the different forms or expressions that convey distinct meanings
         */
        List<String> wordsList = Arrays.asList(words);
        Set<String> uniqueWords = new HashSet<>(wordsList);
        sbNLPStats.append("\n\nUnique Word Count (Word Types): ").append(uniqueWords.size());

        /*
        Type-token ratio (TTR) is a measure of lexical diversity in a text corpus
        It is calculated by dividing the number of unique words (types) in a corpus
        by the total number of words (tokens) in that corpus
         */
        sbNLPStats.append("\n\nTTR - Type-Token Ratio: ").append((float) uniqueWords.size() / (float) words.length);

        Map<String, Long> wordFrequencyMap = wordsList.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        LinkedHashMap<String, Long> countByWordSorted = wordFrequencyMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));

        List<String> top10MostUsedWords = countByWordSorted
                .entrySet()
                .stream()
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        sbNLPStats.append("\n\nTop 10 Most Used Words: ").append(top10MostUsedWords);

        LinkedHashMap<String, Long> top10MostUsedWordsWithFrequency = countByWordSorted
                .entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
        sbNLPStats.append("\n\nTop 10 Most Used Words (frequency): ").append(top10MostUsedWordsWithFrequency);

        List<String> top10LeastUsedWords = countByWordSorted
                .reversed()
                .entrySet()
                .stream()
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        sbNLPStats.append("\n\nTop 10 Least Used Words are: ").append(top10LeastUsedWords);

        LinkedHashMap<String, Long> top10LeastUsedWordsWithFrequency = countByWordSorted
                .reversed()
                .entrySet()
                .stream()
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
        sbNLPStats.append("\n\nTop 10 Least Used Words (frequency): ").append(top10LeastUsedWordsWithFrequency);

        List<String> wordsWithFrequencyAsOneSortedByLength = countByWordSorted
                .reversed()
                .entrySet()
                .stream()
                .takeWhile((wordWithFreq) -> wordWithFreq.getValue() == 1)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparingInt(String::length).reversed())
                .collect(Collectors.toList());

        sbNLPStats.append("\n\nTop 10 Most Interesting Words: \n\n");
        wordsWithFrequencyAsOneSortedByLength
                .stream()
                .limit(10)
                .forEach(word -> sbNLPStats.append(word).append("\n"));

        sbNLPStats.append("\n\nWords with Frequency as 1 (Sorted by word character length): \n\n");
        wordsWithFrequencyAsOneSortedByLength.forEach(word -> sbNLPStats.append(word).append("\n"));

        // More verbose outputs at the end
        sbNLPStats.append("\n\nBigrams: \n\n");
        bigramList.forEach(bigrm -> sbNLPStats.append(bigrm).append("\n"));

        sbNLPStats.append("\n\nWords by frequency (sorted - descending): \n\n");

        countByWordSorted.forEach((key, value) ->
                sbNLPStats.append(key).append(" ").append(value).append("\n")
        );

        writeToFile(sbNLPStats.toString(), OUTPUT_FILE_PATH_NLP_STATS);
        log("""
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                Natural Language Processing Statistics
                %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%""");


        Instant endTime = Instant.now();
        Duration between = Duration.between(startTime, endTime);
        log(String.format("Took %d seconds and %d nanoseconds",
                between.get(ChronoUnit.SECONDS),
                between.get(ChronoUnit.NANOS)));
    }

    private static URL validateUserInputAsURL(String[] userInput) {
        String inputUrl;
        if (userInput == null || userInput.length == 0 || userInput[0] == null) {
            log("""
                    No arguments were passed.
                    Defaulting to the favourite URL:
                    https://en.wikipedia.org/wiki/Java_(programming_language)""");
            inputUrl = "https://en.wikipedia.org/wiki/Java_(programming_language)";
        } else {
            inputUrl = userInput[0];
        }

        log("Validating user input: " + inputUrl);
        // Get URL from the user and validate
        URL url;
        try {
            url = new URI(inputUrl).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException("Failed during validating the URL: " + inputUrl, e);
        }
        log("Validated user input successfully: " + inputUrl);
        return url;
    }

    private static String getURLContentAsText(URL url) {
        String siteContent;
        log("Program is configured to fetch " + (readRawContent ? "raw " : "text ") + "content");
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
                Document doc = Jsoup.connect(url.toExternalForm()).get();
                // Select the main text content of the wiki
                Elements mainContent = doc.select(DIV_NAME_MW_CONTENT_TEXT);
                StringBuilder sb = new StringBuilder();
                // Select the main text content paragraphs of the wiki
                for (Element pElem : mainContent.select("p")) {
                    sb.append(pElem.text()).append(" ");
                }
                siteContent = sb.toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed during fetching and parsing via JSoup library", e);
            }
        }
        log("Received and parsed content of length: " + siteContent.length());
        return siteContent;
    }

    private static void writeToFile(String content, String filePath) {
        log("Writing content to path: " + filePath);
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed during writing the site content to the local file:" +
                    filePath, e);
        }
    }

    private static void log(String s) {
        System.out.println(s);
    }
}