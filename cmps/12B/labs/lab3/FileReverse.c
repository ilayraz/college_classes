/*
  Ilay Raz
  ilraz
  CMPS12M-02
  2/02/18
  FileReverse.c
  It reverses a string...
*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void stringReverse(char* s);

int main(int argc, char** argv) {
    /* FILE* in, out; */
    FILE* in;
    FILE* out;
    char word[256];

    if (argc != 3) {
        printf("Usage: %s <input file> <output file>\n", argv[0]);
        return EXIT_FAILURE;
    }

    in = fopen(argv[1], "r");
    if (in == NULL) {
        printf("Unable to read from file %s\n", argv[1]);
        return EXIT_FAILURE;
    }

    out = fopen(argv[2], "w");
    if (out == NULL) {
        printf("Unable to write to file %s\n", argv[2]);
        return EXIT_FAILURE;
    }

    while (fscanf(in, " %s", word) != EOF) {
        stringReverse(word);
        fprintf(out, "%s\n", word);
    }

    return EXIT_SUCCESS;
}

void stringReverse(char* s) {
    int i, j;
    char temp;
    for(i = 0, j = strlen(s) - 1; i < j; i++, j--) {
        temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}
