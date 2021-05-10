import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class markovChain {
   static ArrayList<String> prepositions = new ArrayList<>();

static {
    prepositions.add("в");
    prepositions.add("к");
    prepositions.add("на");
    prepositions.add("с");
    prepositions.add("от");
    prepositions.add("над");
    prepositions.add("под");
    prepositions.add("по");
    prepositions.add("до");
    prepositions.add("из");
    prepositions.add("без");
    prepositions.add("у");
    prepositions.add("для");
    prepositions.add("о");
    prepositions.add("около");
    prepositions.add("за");
}
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
            if (prepositions.contains(value)){
                value =  value + " " + ((i + 2 < words.size()) ? words.get(i + 2) : "");
                i++;
            }
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
        String firstWord = null;
        String nextWord = firstWord;
        //int quantity = 10;
        int currentQuantity = 0;
        int quantityMin = 6;

        String generatedText = "";
        do{
            currentQuantity ++;
            nextWord = getNextWord(dictionary, nextWord, currentQuantity < quantityMin);
            if(nextWord == null)
                break;
            else
                generatedText+=nextWord + " ";
        }while(true);

//        for (int i = 0; i <= quantity; i++) {
//            nextWord = getNextWord(dictionary, nextWord);
//
//            if (i == quantity) {
//                if (nextWord != null) {
//                    String key = null;
//                    for (Map.Entry<String, List<String>> keyValue : dictionary.entrySet()) {
//                        for (String word : keyValue.getValue()) {
//                            if (word == null) {
//                                key = keyValue.getKey();
//                                break;
//                            }
//                        }
//                        if (key != null) {
//                            nextWord = key;
//                            break;
//                        }
//                    }
//                }
//            }
//
//            if (nextWord == null) {
//                if (i > quantityMin) {
//                    break;
//                }
//                nextWord = getNextWord(dictionary, nextWord);
//            }
//            else{
//                    generatedText += nextWord + " ";
//                }
//
//        }
        generatedText = generatedText.trim() + ".";
        System.out.println(generatedText);

    }
    public static String getNextWord(Map<String, List<String>> dictionary,String currentWord, boolean isNeedEnd){
        Random random = new Random();
        List<String> list = dictionary.get(currentWord);

        if (isNeedEnd) {
            for(String s : list) {
                if (s == null) {
                    deleteWordFromDictionary(dictionary, currentWord, null);
                    return null;
                }
            }
        }
        if (list == null)
            System.out.println("null");
        int i = random.nextInt(list.size());

        String result = dictionary.get(currentWord).get(i);



        deleteWordFromDictionary(dictionary, currentWord, result);
        return result;
    }

    public static void deleteWordFromDictionary (Map<String, List<String>> dictionary, String key, String value){
        int i = dictionary.get(key).indexOf(value);
        if(i < 0) return;
        dictionary.get(key).remove(i);
    }





}




