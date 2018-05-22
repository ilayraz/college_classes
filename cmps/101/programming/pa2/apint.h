#ifndef APINT_H
#define APINT_H
#include <inttypes.h>
typedef struct apintObj *apint;

/* Operations */
apint add(apint a,apint b);
apint subtract(apint a, apint b);
apint multiply(apint a, apint b);

/* Printing */
void print(apint a);

/* Constructors/Destructors */
apint newApint();
apint newApintStr(char *val);
apint newApintInt(int val);
void freeApint(apint *a);
#endif
