// Ilay Raz
// ilraz
// CMPS12M-02
// 2/27/18
// Dictionary.c

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Dictionary.h"


// Private types -----------------------------

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
    Node head;
    int size;
} DictionaryObj;

// public functions -----------------------------

Dictionary newDictionary(void) {
    Dictionary dict = malloc(sizeof *dict);
    assert(dict != NULL);
    dict->head = NULL;
    dict->size = 0;
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

char* lookup(Dictionary dict, char* key) {
    for (Node node = dict->head; node != NULL; node = node->next)
        if (strcmp(node->key, key) == 0)
            return node->value;
    return NULL;
}

void insert(Dictionary dict, char* key, char* value) {
    assert(lookup(dict, key) == NULL);
    if (dict->head == NULL)
        dict->head = newNode(key, value);
    else {
        Node node = dict->head;
        for (; node->next != NULL; node = node->next);
        node->next = newNode(key, value);
    }
    dict->size++;
}

void delete(Dictionary dict, char* key) {
    assert(dict->head != NULL);
    Node pNode, dNode;
    pNode = dict->head;

    if (strcmp(pNode->key, key) == 0) {
        dict->head = dict->head->next;
        freeNode(&pNode);
    } else {
        for (; pNode->next != NULL && strcmp(pNode->next->key, key) != 0; pNode = pNode->next);
        assert(pNode->next != NULL);
        dNode = pNode->next;
        if (dNode == NULL)
            pNode->next = NULL;
        else
            pNode->next = dNode->next;
        freeNode(&dNode);
    }
    dict->size--;
}

void makeEmpty(Dictionary dict) {
    Node temp;
    while (dict->head != NULL) {
        temp = dict->head;
        dict->head = dict->head->next;
        freeNode(&temp);
    }
    dict->size = 0;
}

void printDictionary(FILE* out, Dictionary dict) {
    for (Node node = dict->head; node != NULL; node = node->next)
        fprintf(out, "%s %s\n", node->key, node->value);
}
