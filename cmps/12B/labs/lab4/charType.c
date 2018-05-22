/* Ilay Raz */
/* ilraz */
/* CMPS12M-02 */
/* 2/12/18 */
/* charType.c */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>
#include <string.h>

#define MAXLENGTH 100

void extract_chars(char *s, char *a, char *d, char *p, char *w);

int main(int argc, char **argv) {
    FILE* in;
    FILE* out;
    char *s = malloc (sizeof *s * MAXLENGTH);
    char *a = malloc(sizeof *a * MAXLENGTH);
    char *d = malloc(sizeof *d * MAXLENGTH);
    char *p = malloc(sizeof *p * MAXLENGTH);
    char *w = malloc(sizeof *w * MAXLENGTH);
    int lineNumber = 1;

    if (argc != 3) {
        printf("Usage: %s <input file> <output file>\n", argv[0]);
        return EXIT_FAILURE;
    }

    assert((in = fopen(argv[1], "r")) != NULL);
    assert((out = fopen(argv[2], "w")) != NULL);

    while (fgets(s, MAXLENGTH, in) != NULL) {
        extract_chars(s, a, d, p, w);
        fprintf(out, "line %d contains:\n", lineNumber++);
        fprintf(out, "%lu alphabetic characters: %s\n", strlen(a), a);
        fprintf(out, "%lu numeric characters: %s\n", strlen(d), d);
        fprintf(out, "%lu punctuations characters: %s\n", strlen(p), p);
        fprintf(out, "%lu whitespace characters: %s\n", strlen(w), w);
    }

    fclose(in);
    fclose(out);

    free(s);
    free(a);
    free(d);
    free(p);
    free(w);

    return EXIT_SUCCESS;
}


void extract_chars(char *s, char *a, char *d, char *p, char *w) {
    char ch;

    while ((ch = *s++) != '\0') {
        if (isalpha((int)ch))
            *a++ = ch;
        else if (isdigit((int)ch))
            *d++ = ch;
        else if (ispunct((int)ch))
            *p++ = ch;
        else if (isspace((int)ch))
                 *w++ = ch;
    }

    *a = *d = *p = *w = '\0';
}
