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
        // String[] words = text.split(" ");

        System.out.println(text);

        List<String> words = new ArrayList<>();
        for (String s : text.split(" ")) {
            words.add(s.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "").toLowerCase());
        }

        Map<String, Long> frequency = countWords(words);
        System.out.println(frequency);

        System.out.println(dictionary(words));


    }

    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static Map<String, Long> countWords(List<String> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }

    public static Map<String, List<String>> dictionary (List<String> words) {
        Map<String, List<String>> dictionary = new HashMap<>();
        dictionary.put("*Start*", Collections.singletonList(words.get(0)));
        dictionary.put("цап", Collections.singletonList("*Finish*"));

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

}



//        pairs.put("*НАЧАЛО*", Collections.singletonList(words.get(0)));
//        for (int i = 0; i < words.size() - 1; i++) {
//
//            pairs.put(words.get(i),words.get(i + 1));
//        }
//
//        pairs.put(words.get(words.size()-1), Collections.singletonList("*КОНЕЦ*"));
//
//        return pairs;

//    public static List<String> pairs (List<String> words) {
//        List<String> pairs = new ArrayList<>();
//        pairs.add("НАЧАЛО" + " " + words.get(0));
//        for (int i = 0; i < words.size() - 1; ++i) {
//            pairs.add(words.get(i) + " " + words.get(i + 1));
//
//        }
//        pairs.add(words.get(words.size() - 1) + " " + "КОНЕЦ");
//
//        return pairs;
//
//
//    }

