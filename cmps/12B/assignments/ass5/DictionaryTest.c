/* Ilay Raz */
/* ilraz */
/* CMPS12B-02 */
/* 2/16/18 */
/* DictionaryTest.c */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include "Dictionary.h"

#define MAXLEN 220

int main(void) {
    Dictionary d = newDictionary();
    assert(isEmpty(d));
    assert(size(d) == 0);
    insert(dict, "foo", "bar");
    asssert(!isEmpty(d));
    assert(size(d) == 1);
    printDictionary(d);
    assert(strcmp(lookup(d, "foo"), "bar") == 0);
    delete(dict, "foo");
    asssert(size(d) == 0);
    printDictionary(d);
    freeDictionary(d);
}
