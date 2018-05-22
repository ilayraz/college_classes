#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Dictionary.h"

int main(void) {
    Dictionary dict = newDictionary();
    assert(isEmpty(dict));
    assert(size(dict) == 0);
    insert(dict, "foo", "bar");
    assert(!isEmpty(dict));
    assert(size(dict) == 1);
    insert(dict, "cat", "dog");
    /* delete(dict, "meh"); */
    /* lookup(dict, "meh"); */
    assert(strcmp(lookup(dict, "foo"), "bar") == 0);
    assert(strcmp(lookup(dict, "cat"), "dog") == 0);
    printDictionary(stdout, dict);
    delete(dict, "foo");
    assert(size(dict) == 1);
    delete(dict, "cat");
    assert(isEmpty(dict));
    insert(dict, "foo", "bar");
    insert(dict, "cat", "dog");
    freeDictionary(dict);

    return EXIT_SUCCESS;
}
