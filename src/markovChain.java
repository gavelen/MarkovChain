import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class markovChain {
    public static void main (String[] args) throws IOException {
        String fileName = "/Users/gavelen/Работа/MarkovChain/src/text";
        System.out.println(readUsingFiles(fileName));
    }
    private static String readUsingFiles(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

}

