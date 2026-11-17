import java.io.*;
import java.util.*;

public class WordAnalyzer {
    public static void main(String[] args) {
        String fileName = "sample_article.txt";
        Map<String, Integer> wordCount = new HashMap<>();
        int totalWords = 0;
        int sentences = 0;

        try {
            BufferedReader br = new BufferedReader(
                new FileReader(fileName));

            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");

                for (String word : words) {
                    word = word.replaceAll("[^a-zA-Z0-9]", "");

                    if (!word.isEmpty()) {
                        wordCount.put(word,
                            wordCount.getOrDefault(word, 0) + 1);
                        totalWords++;
                    }
                }

                sentences += line.split("[.!?]+").length - 1;
            }
            br.close();

            int uniqueWords = wordCount.size();
            double average = sentences > 0
                ? (double) totalWords / sentences : 0;

            System.out.println("Total Words: " + totalWords);
            System.out.println("Unique Words: " + uniqueWords);
            System.out.println("Total Sentences: " + sentences);
            System.out.printf("Average Sentence Length: %.2f%n",
                average);

            System.out.println("\nTop 10 Words:");

            wordCount.entrySet().stream()
                .sorted((a, b) ->
                    b.getValue().compareTo(a.getValue()))
                .limit(10)
                .forEach(e ->
                    System.out.println(e.getKey() + " : " + e.getValue()));

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}mudi
