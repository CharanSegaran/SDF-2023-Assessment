import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java NextWordDistributionAnalyzer <directory>");
            System.exit(1);
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);

        if (!directory.isDirectory()) {
            System.err.println("Invalid directory: " + directoryPath);
            System.exit(1);
        }

        Map<String, Map<String, Integer>> nextWordDistribution = new HashMap<>();

        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                analyzeFile(file, nextWordDistribution);
            }
        }

        printNextWordDistribution(nextWordDistribution);
    }

    private static void analyzeFile(File file, Map<String, Map<String, Integer>> nextWordDistribution) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.replaceAll("[^a-zA-Z\\s]", "").toLowerCase().split("\\s+");
                for (int i = 0; i < words.length - 1; i++) {
                    String currentWord = words[i];
                    String nextWord = words[i + 1];
                    nextWordDistribution.computeIfAbsent(currentWord, k -> new HashMap<>())
                            .merge(nextWord, 1, Integer::sum);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printNextWordDistribution(Map<String, Map<String, Integer>> nextWordDistribution) {
        for (String word : nextWordDistribution.keySet()) {
            System.out.println("Word: " + word);
            Map<String, Integer> distribution = nextWordDistribution.get(word);
            for (String nextWord : distribution.keySet()) {
                int count = distribution.get(nextWord);
                System.out.println("  " + nextWord + ": " + count);
            }
        }
    }
}
