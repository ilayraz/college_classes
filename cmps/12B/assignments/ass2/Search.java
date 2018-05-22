// Ilay Raz
// ilraz
// CMPS12B-02
// 1/28/18
// Search.java

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.IntStream;
import java.util.Arrays;

class Search {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: Search file target1 [target2 ..]");
            System.exit(1);
        }

        String[] word = Files.lines(Paths.get(args[0])).toArray(String[]::new);
        int[] lineNumber = IntStream.rangeClosed(1, word.length).toArray();

        mergeSort(word, lineNumber, 0, word.length - 1);
        for (int i = 1; i < args.length; i++) {
            int loc = binarySearch(word, args[i], 0, word.length);
            if (loc != -1)
                System.out.println(args[i] + " found on line " + lineNumber[loc]);
            else
                System.out.println(args[i] + " not found");
        }
    }

    static void mergeSort(String[] word, int[] lineNumber, int iStart, int iEnd) {
        int iMiddle;
        if(iStart < iEnd) {
            iMiddle = (iStart + iEnd) / 2;
            mergeSort(word, lineNumber, iStart, iMiddle);
            mergeSort(word, lineNumber, iMiddle + 1, iEnd);
            merge(word, lineNumber, iStart, iMiddle, iEnd);
        }
    }

    static void merge(String[] word, int[] lineNumber, int iStart, int iMiddle, int iEnd) {
        int n1 = iMiddle - iStart + 1;
        int n2 = iEnd - iMiddle;
        String[] Lw = Arrays.copyOfRange(word, iStart, iMiddle + 1);
        String[] Rw = Arrays.copyOfRange(word, iMiddle + 1, iEnd + 1);
        int[] Li = Arrays.copyOfRange(lineNumber, iStart, iMiddle + 1);
        int[] Ri = Arrays.copyOfRange(lineNumber, iMiddle + 1, iEnd + 1);

        int i = 0, j = 0;
        for(int k = iStart; k <= iEnd; k++){
            if(i < n1 && j < n2){
                if( Lw[i].compareTo(Rw[j]) < 0 ){
                    word[k] = Lw[i];
                    lineNumber[k] = Li[i];
                    i++;
                } else {
                    word[k] = Rw[j];
                    lineNumber[k] = Ri[j];
                    j++;
                }
            } else if(i < n1){
                word[k] = Lw[i];
                lineNumber[k] = Li[i];
                i++;
            }else{ // j<n2
                word[k] = Rw[j];
                lineNumber[k] = Ri[j];
                j++;
            }
        }
    }

    static int binarySearch(String[] word, String target, int iStart, int iEnd) {
        if (iStart > iEnd)
            return -1;
        int iMiddle = (iStart + iEnd) / 2;
        int compare = word[iMiddle].compareTo(target);

        if (compare < 0)
            return binarySearch(word, target, iMiddle + 1, iEnd);
        else if (compare > 0)
            return binarySearch(word, target, iStart, iMiddle - 1);
        else
            return iMiddle;
    }
}
