/* Ilay Raz */
/* ilraz */
/* CMPS12B-02 */
/* 2/16/18 */
/* Dictionary.c */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include "Dictionary.h"

#define TABLESIZE 101

/* Private definitions ----------------------------- */

typedef struct NodeObj {
    char* key;
    char* value;
    struct NodeObj* next;
} NodeObj;
typedef NodeObj* Node;

Node newNode(char* key, char* value) {
    Node node = malloc(sizeof *node);
    assert(node != NULL);
    node->key = key;
    node->value = value;
    node->next = NULL;
    return node;
}

void freeNode(Node* node) {
    if (node != NULL && *node != NULL) {
        free(*node);
        *node = NULL;
    }
}

typedef struct DictionaryObj {
    Node table[TABLESIZE];
    int size;
} DictionaryObj;

// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
    int sizeInBits = 8 * sizeof(unsigned int);
    shift = shift & (sizeInBits - 1);
    if (shift == 0)
        return value;
    return (value << shift) | (value >> (sizeInBits - shift));
}

// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) {
    unsigned int result = 0xBAE86554;
    while (*input) {
        result ^= *input++;
        result = rotate_left(result, 5);
    }
    return result;
}

// hash()
// turns a string into an int in the range 0 to TABLESIZE
int hash(char* key){
    return pre_hash(key) % TABLESIZE;
}
/* Public definitions ----------------------------- */

Dictionary newDictionary(void) {
    Dictionary dict = calloc(1, sizeof *dict);
    assert(dict != NULL);
    return dict;
}

void freeDictionary(Dictionary* dict) {
    if (dict != NULL && *dict != NULL) {
        makeEmpty(*dict);
        free(*dict);
        *dict = NULL;
    }
}

int isEmpty(Dictionary dict) {
    return dict->size == 0;
}

int size(Dictionary dict) {
    return dict->size;
}

char* lookup(Dictionary dict, char* k) {
    Node node;
    for (node = dict->table[hash(k)]; node != NULL && node->key != k; node = node->next);
    if (node == NULL)
        return NULL;
    return node->value;
}

void insert(Dictionary dict, char* k, char* v) {
    assert(lookup(dict, k) == NULL);
    Node node = newNode(k, v);
    int mhash = hash(k);
    node->next = dict->table[mhash];
    dict->table[mhash] = node;
    dict->size++;
}

void delete(Dictionary dict, char* k) {
    assert(lookup(dict, k) != NULL);
    int mhash = hash(k);
    Node toDel;
    for (toDel = dict->table[mhash]; strcmp(toDel->key, k) != 0; toDel = toDel->next);
    Node prev = dict->table[mhash];
    if (strcmp(prev->key, k) == 0)
        dict->table[mhash] = prev->next;
    else {
        for (; strcmp(prev->next->key, k) != 0; prev = prev->next);
        prev->next = prev->next->next;
    }
    dict->size--;
    freeNode(&toDel);
}

void makeEmpty(Dictionary dict) {
    dict->size = 0;
    Node temp;
    for (int i = 0; i < TABLESIZE; i++) {
        while(dict->table[i] != NULL) {
            temp = dict->table[i];
            dict->table[i] = dict->table[i]->next;
            freeNode(&temp);
        }
    }
}

void printDictionary(FILE* out, Dictionary dict) {
    Node node;
    for (int i = 0; i < TABLESIZE; i++)
        for (node = dict->table[i]; node != NULL; node = node->next)
            fprintf(out, "%s %s\n", node->key, node->value);
}
