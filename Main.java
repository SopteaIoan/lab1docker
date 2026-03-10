import java.nio.file.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String text = Files.readString(Path.of("input.txt"));
        String rezultat = text.replaceAll("\\p{Punct}", "")
                .toUpperCase()
                .replaceAll("\\d", "");

        System.out.println(rezultat);
    }
}