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
        String[] sentences = text.split("\\.");

        System.out.println(text);

        Map<String, List<String >> dictionary = new HashMap<>();

        for (int i = 0; i < sentences.length; i++) {
            List<String> words = new ArrayList<>();
            for (String s : sentences[i].trim().split(" ")) {
                words.add(s.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "").toLowerCase());
            }
            createDictionary(dictionary, words);
        }


        //Map<String, Long> frequency = countWords(words);
        //System.out.println(frequency);
        System.out.println(dictionary);
        chain(dictionary);


    }

    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static Map<String, Long> countWords(List<String> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }

    public static Map<String, List<String>> createDictionary (Map<String, List<String>> dictionary, List<String> words) {

        //dictionary.put("*Start*", new ArrayList(Arrays.asList(words.get(0))));
        //dictionary.put(words.get(words.size()-1),new ArrayList( Arrays.asList("*Finish*")));

        List<String> valueWordsStart = dictionary.get(null);
        if (valueWordsStart == null) {
            valueWordsStart = new ArrayList<>();
            valueWordsStart.add(words.get(0));
            dictionary.put(null, valueWordsStart);
        } else {
            valueWordsStart.add(words.get(0));
        }

        List<String> valueWordsFinish = dictionary.get(words.get(words.size()-1));
        if (valueWordsFinish == null) {
            valueWordsFinish = new ArrayList<>(1);
            valueWordsFinish.add(null);
            dictionary.put(words.get(words.size()-1), valueWordsFinish);
        } else {
            valueWordsFinish.add(null);
        }

        for (int i = 0; i < (words.size() - 1); ++i) {
            StringBuilder key = new StringBuilder(words.get(i));
            for (int j = i + 1; j < i; ++j) {
                key.append(' ').append(words.get(j));
            }
            String value = (i + 1 < words.size()) ? words.get(i + 1) : "";
            List<String> valueWords = dictionary.get(words.get(i));
            if (valueWords == null) {
                valueWords = new ArrayList<>();
                valueWords.add(value);
                dictionary.put(key.toString(), valueWords);
            } else {
                valueWords.add(value);
            }
        }
        return dictionary;
    }


    public static void chain (Map<String, List<String>> dictionary) {
        Random random = new Random();
        int a = random.nextInt(dictionary.get(null).size());
        String firstWord = String.valueOf(dictionary.get(null).get(a));
        String nextWord = firstWord;
        String generatedText = "";

        do {
            //nextWord = getNextWord(dictionary, nextWord);
            if (nextWord == null){
                //generatedText += ".";
                break;
            }
            else {
                //nextWord = getNextWord(dictionary, nextWord);
                generatedText += nextWord + " ";
            }
        } while (true);
        System.out.println(generatedText);

        //System.out.println(dictionary);


    }
    public static String getNextWord(Map<String, List<String>> dictionary,String currentWord){
        Random random = new Random();
        if (currentWord == null)
            System.out.println(currentWord);

        List<String> list = dictionary.get(currentWord);
        if (list == null)
            System.out.println("null");
        int i = random.nextInt(list.size());

        String result = dictionary.get(currentWord).get(i);
        deleteWordFromDictionary(dictionary, currentWord, result);
        return result;
    }
    public static void deleteWordFromDictionary (Map<String, List<String>> dictionary, String key, String value){
        ((ArrayList<String>)dictionary.get(key)).remove(value);
    }



}




