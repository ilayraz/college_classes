import java.io.*;
import java.util.Scanner;

class FindAnagrams {
    public static void main(String args[]) throws FileNotFoundException, IOException {
        // Start by calling the test program because I have no idea where else to put it
        AnagramTest.main(null);

        // Check for the correct number of arguements
        if (args.length != 1) {
            System.err.println("Wrong number of arguements");
            System.exit(0);
        }

        // Populate the map from file
        AnagramMap map = new AnagramMap();
        final BufferedReader br = new BufferedReader(new FileReader(args[0]));
        br.readLine(); // Ignore first line
        String line = br.readLine();
        while (line != null) {
            map.addAnagram(new Anagram(line));
            line = br.readLine();
        }

        // Take in an input and return all anagrams of it
        Scanner input = new Scanner(System.in);
        System.out.println("type a string of letters");
        map.printAnagrams(new Anagram(input.nextLine()));

        // Repeat this process until exit
        while (true) {
            System.out.println("Do another (y/n)?");
            if (input.nextLine().equals("n"))
                System.exit(0);
            map.printAnagrams(new Anagram(input.nextLine()));
        }
    }
}
