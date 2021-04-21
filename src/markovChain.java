import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class markovChain {
    public static void main(String[] args) throws IOException {
        String fileName = "/Users/gavelen/Работа/MarkovChain/src/text";
        String text = readFile(fileName);

        System.out.println(text);

        List<String> words = new ArrayList<>();
        for (String s : text.split(" ")) {
            words.add(s.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "").toLowerCase());
        }

        Map<String, Long> frequency = countWords(words);
        //System.out.println(frequency);
        System.out.println(dictionary(words));
        chain(dictionary(words));


    }

    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static Map<String, Long> countWords(List<String> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }

    public static Map<String, List<String>> dictionary (List<String> words) {
        Map<String, List<String>> dictionary = new HashMap<>();
        dictionary.put("*Start*", new ArrayList(Arrays.asList(words.get(0))));
        dictionary.put(words.get(words.size()-1),new ArrayList( Arrays.asList("*Finish*")));

        for (int i = 0; i < (words.size() - 1); ++i) {
            StringBuilder key = new StringBuilder(words.get(i));
            for (int j = i + 1; j < i; ++j) {
                key.append(' ').append(words.get(j));
            }
            String value = (i + 1 < words.size()) ? words.get(i + 1) : "";
            if (!dictionary.containsKey(key.toString())) {
                ArrayList<String> list = new ArrayList<>();
                list.add(value);
                dictionary.put(key.toString(), list);
            } else {
                dictionary.get(key.toString()).add(value);
            }
        }
        return dictionary;
    }


    public static void chain (Map<String, List<String>> dictionary) {
        String firstWord = "*Start*";
        String nextWord = firstWord;
        String generatedText = "";

        do {
            nextWord = getNextWord(dictionary, nextWord);
            if (nextWord.equals("*Finish*"))
                break;
            generatedText += nextWord + " ";
        } while (true);
        System.out.println(generatedText);

        System.out.println(dictionary);


    }
    public static String getNextWord(Map<String, List<String>> dictionary,String currentWord){
        Random random = new Random();
        int i = random.nextInt(dictionary.get(currentWord).size());
        String result = dictionary.get(currentWord).get(i);
        deleteWordFromDictionary(dictionary, currentWord, result);
        return result;
    }
    public static void deleteWordFromDictionary (Map<String, List<String>> dictionary, String key, String value){
        ((ArrayList<String>)dictionary.get(key)).remove(value);
    }



}




