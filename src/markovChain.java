import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class markovChain {
    public static void main (String[] args) throws IOException {
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

        System.out.println(pairs(words));

    }

    private static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static Map<String, Long> countWords(List<String> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }
    public static List<String> pairs (List<String> words) {
        List<String> pairs = new ArrayList<>();
        pairs.add("НАЧАЛО" + " " + words.get(0));
        for (int i = 0; i < words.size() - 1; ++i) {
            pairs.add(words.get(i) + " " + words.get(i + 1));

        }
        pairs.add(words.get(words.size()-1) + " " + "КОНЕЦ");

        return pairs;
    }






}

