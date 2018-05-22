#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#include <string.h>
#include <ctype.h>
#include <assert.h>
#include "apint.h"

#define FALSE 0
#define TRUE 1

/* Private definitions */

typedef struct apintObj {
    uint8_t *data;
    size_t size;
    uint8_t isNeg;
} apintObj;

/*
  Compares a to b
  Input: apint a, apint b
  Output: -1 if a < b, 0 if a == b, 1 if a > b
 */
int8_t compareTo(apint a, apint b) {
    if (a->isNeg != b->isNeg)
        return a->isNeg ? -1 : 1;
    if (a->size != b->size) {
        if (a->isNeg)
            return b->size > a->size ? 1 : -1;
        return a->size > b->size ? 1 : -1;
    }
    for (size_t i = a->size - 1; i != -1; i--)
        if (a->data[i] != b->data[i])
            return a->data[i] > b->data[i] ? 1 : -1;
    return 0;
}

/*
  Clones an existing apint
  Input: apint
  Output: apint
 */
apint newApintApint(apint a, size_t dataSize) {
    apint ret = malloc(sizeof *ret);
    assert(ret != NULL);

    ret->size = a->size;
    ret->isNeg = a->isNeg;

    ret->data = calloc(dataSize, sizeof *(ret->data));
    assert(ret->data != NULL);
    memcpy(ret->data, a->data, ret->size);

    return ret;
}

/* Public definitions */

/*
  Returns an apint set to 0 with the given size
  Input: size_t
  Output: apint
 */
apint newApint(size_t size) {
    apint ret = malloc(sizeof *ret);
    assert(ret != NULL);

    ret->size = 1;
    ret->isNeg = FALSE;

    ret->data = calloc(size, sizeof *(ret->data));
    assert(ret->data != NULL);

    return ret;
}

/*
  Creates a new apint from a strin gofmatted according to regex "[+-]?[0-9]+"
  Input: string
  Output: apint
 */
apint newApintStr(char *val) {
    size_t strsize = strlen(val);

    apint ret = malloc(sizeof *ret);
    assert(ret != NULL);

    ret->size = strsize - !isdigit(val[0]);

    ret->data = calloc(strsize, sizeof *(ret->data));
    assert(ret->data != NULL);

    ret->isNeg = (!isdigit(val[0]) && (val[0] == '-'));

    for (size_t i = strsize - 1; i != -1 && isdigit(val[i]); i--)
        ret->data[strsize - i - 1] = val[i] - '0';
    return ret;
}

/*
  Creates a new apint from an integer
  Input: int
  Output: apint
 */
apint newApintInt(int val) {
    int i;
    apint ret = calloc(1, sizeof *ret);
    assert(ret != NULL);

    if (val == 0) {
        ret->data = calloc(1, sizeof *(ret->data));
        assert(ret->data != NULL);
        ret->size = 1;
        ret->isNeg = FALSE;
        return ret;
    } else {
        ret->isNeg = (val < 0);
        if (ret->isNeg)
            val *= -1;
        for (i = val; i != 0; i /= 10, ret->size++);

        ret->data = calloc(ret->size, sizeof *ret->data);
        assert(ret->data != NULL);
        for(i = 0; i < ret->size; i++) {
            ret->data[i] = val % 10;
            val /= 10;
        }
    }

    return ret;
}

/*
  Frees all data allocated to an apint. Also sets that apint poter to NULL
  Input: apint
  Output: void
 */
void freeApint(apint *a) {
    if (a != NULL && *a != NULL) {
        if ((*a)->data != NULL)
            free((*a)->data);
        free(*a);
        *a = NULL;
    }
}

/*
  Adds 2 apints together and returns the result.
  Input: apint, apint
  Output: apint
 */
apint add(apint a, apint b) {
    apint ret, other;
    uint8_t aSign = a->isNeg;
    uint8_t bSign = b->isNeg;
    int8_t carry = 0;
    size_t i;

    // Alias a and b in order to not have to check sign and length. ret= bigger number (absolute size).
    a->isNeg = FALSE;
    b->isNeg = FALSE;
    if (compareTo(a, b) >= 0) {
        ret = newApintApint(a, a->size + 1);
        other = b;
        ret->isNeg = aSign;
        b->isNeg = bSign;
    } else {
        ret = newApintApint(b, b->size + 1);
        other = a;
        ret->isNeg = bSign;
        other->isNeg = aSign;
    }
    a->isNeg = aSign;
    b->isNeg = bSign;

    // If both have the same sign, add with carry
    if (ret->isNeg == other->isNeg) {
        for (i = 0; i < ret->size; i++) {
            if (i >= other->size)
                carry += ret->data[i];
            else
                carry += ret->data[i] + other->data[i];
            ret->data[i] = carry % 10;
            carry /= 10;
        }
        if (carry != 0) {
            ret->data[i+1] = carry;
            ret->size++;
        }
    } else {
        // If both have different signs, subtract with borrowing
        for (i = 0; i < ret->size; i++) {
            if (i >= other->size)
                carry += ret->data[i];
            else
                carry += ret->data[i] - other->data[i];
            if (carry < 0) {
                ret->data[i] = carry + 10;
                carry = -1;
            } else {
                ret->data[i] = carry;
                carry = 0;
            }
        }
        while (ret->size > 0 && ret->data[ret->size-- - 1] == 0);
        ret->size++;
    }

    return ret;
}

/* Subtracts b from a  */
apint subtract(apint a, apint b) {
    b->isNeg = !b->isNeg;
    apint ret = add(a, b);
    b->isNeg = !b->isNeg;
    return ret;
}

/*
  Multiply two apints together
  Input: apint
  Output apint
*/
apint multiply(apint a, apint b) {
    apint ret, temp, me, other, holder;
    uint8_t carry, currentDigit;
    size_t i, j, maxSize;

    // Set me to the apint that has the most digits
    if (a->size > b->size) {
        me = a;
        other = b;
    } else {
        me = b;
        other = a;
    }

    // Initialize variables
    maxSize = me->size + other->size + 1;
    ret = newApint(maxSize);
    temp = newApint(maxSize);

    for (i = 0; i < other->size; i++) {
        carry = 0;
        memset(temp->data, 0, maxSize);
        currentDigit = other->data[i];

        // Multiply
        for (j = 0; j < me->size; j++) {
            carry += currentDigit * me->data[j];
            temp->data[i+j] = carry % 10;
            carry /= 10;
        }
        temp->size = i+j+1;
        if (carry != 0)
            temp->data[i+j] = carry;

        // Add intermidiate results
        holder = ret;
        ret = add(ret, temp);
        freeApint(&holder);
    }

    // Set the sign of the new number. Ensure that 0 is always positive
    if (ret->size == 1 && ret->data[0] == 0)
        ret->isNeg = FALSE;
    else
        ret->isNeg = other->isNeg ^ me->isNeg;

    while (ret->size > 0 && ret->data[ret->size-- - 1] == 0);
    ret->size++;

    return ret;
}

void print(apint a) {
    if (a->isNeg)
        putchar('-');
    for (size_t i = a->size - 1; i != -1; i--)
        putchar(a->data[i] + '0');
}
