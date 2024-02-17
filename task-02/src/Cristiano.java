import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cristiano {
    public static void main(String args[]){
        //if user give more than one input on the same line
        if (args.length != 1){
            System.err.println("Usage: java NextWordDistributionAnalyzer <directory>");
            System.exit(1);
        }

        String directoryPath = args[0];
        File directory = new File(directoryPath);

        //if directory not found
        if(!directory.isDirectory()){
            System.err.println("Invalid directory: " + directoryPath);
            System.exit(1);
        }

        Map<String, Map<String, Integer>> nextWordDistribution = new HashMap<>();

        for (File file: directory.listFiles()){
            if (file.isFile()){
                analyzeFile(file, nextWordDistribution);
                printProbability(nextWordDistribution);
            }
        }
        

    }

    private static void analyzeFile(File file, Map<String, Map<String, Integer>> nextWordDistribution){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                String[] words = line.replaceAll("[^a-zA-Z\\s]", "").toLowerCase().split("\\s+");
                for (int i = 0; i < words.length - 1; i++){
                    String currentWord = words[i];
                    String nextWord = words[i+1];
                    Map<String, Integer> nextWordMap = nextWordDistribution.get(currentWord);

                    //if currentWord dosen't exist in nextWordDistribution
                    if (nextWordMap == null){
                        nextWordMap = new HashMap<>();
                        nextWordDistribution.put(currentWord, nextWordMap);
                    }
                    
                    //if internal map does not contain nextWord
                    if (!nextWordMap.containsKey(nextWord)){
                        nextWordMap.put(nextWord, 1);
                    }
                    //if internal map contains nextWord
                    else if (nextWordMap.containsKey(nextWord)){
                        int frequency = nextWordMap.get(nextWord);
                        nextWordMap.put(nextWord, frequency + 1);
                    }
                    
                }
            }
            }catch(IOException e){
                e.printStackTrace();
        }
    }

    private static void printProbability(Map<String, Map<String, Integer>> nextWordDistribution){
        for (String currentWord: nextWordDistribution.keySet()){
            System.out.println(currentWord);
            //for each currentWord, get the total int sum of all its nextWord occurences first
            int totalCount = 0;
            Map<String, Integer> nextWordMap = nextWordDistribution.get(currentWord);
            for (String nextWord: nextWordMap.keySet()){
                totalCount += nextWordMap.get(nextWord);
            }
            //We now have the total sum. We can calculate the probability by dividing each nextWord int value by the total sum.
            //SUIIIIIIIIIIIIIIII
            for (String nextWord : nextWordMap.keySet()){
                System.out.println("      " + nextWord + " : " + (double)nextWordMap.get(nextWord) / totalCount);
            }
        }
    }
}

