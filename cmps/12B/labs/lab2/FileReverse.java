/*
Ilay Raz
ilraz
CMPS12M-02
1/23/18
FileReverse.java

Reads in a file from args[0], reads each word, reverses it, and prints it to a new line`
*/

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.io.PrintWriter;

class FileReverse {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Did not recieve 2 args");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(args[1]);

        Arrays.stream(
                      Files.lines(Paths.get(args[0]), StandardCharsets.UTF_8)
                      .reduce(
                              (s1, s2) -> s1.concat(" " + s2.trim())
                              )
                      .get().trim().split(" ")
                      )
            .map(
                 text -> stringReverse(text, text.length())
                 )
            .forEachOrdered(out::println);
        out.close();
    }

    public static String stringReverse(String s, int n) {
        if (s.isEmpty())
            return "";
        if (s.length() == n)
            return stringReverse(s.substring(1, n), n-1) + s.charAt(0);

        return stringReverse(s.substring(1, n), n-1) + s.charAt(0) + s.substring(n+1);
    }
}
