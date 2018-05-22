#include <stdio.h>
#include <assert.h>
#include <stdlib.h>
#include "apint.h"

int main() {
    /* Testing newApintStr */
    printf("Testing default apint constructor: ");
    apint zero = newApint(5);
    print(zero);
    putchar('\n');

    printf("Testing apint from string:\n" );
    apint foo = newApintStr("12345678901234567890");
    apint bar = newApintStr("-12345678900987654321");

    print(foo);
    putchar('\n');
    print(bar);
    putchar('\n');

    /* Testing newApintInt */
    printf("Testing apint from ints:\n" );
    apint fifteen = newApintInt(15);
    apint ten = newApintInt(10);

    print(fifteen);
    putchar('\n');
    print(ten);
    putchar('\n');
    putchar('\n');

    /* Testing addition */
    print(ten);
    putchar('+');
    print(fifteen);
    putchar('=');
    print(add(fifteen,ten));
    putchar('\n');

    print(foo);
    putchar('+');
    putchar('(');
    print(bar);
    putchar(')');
    putchar('=');
    print(add(foo, bar));
    putchar('\n');
    putchar('\n');

    /* Testins subtraction */
    print(ten);
    putchar('-');
    print(fifteen);
    putchar('=');
    print(subtract(fifteen,ten));
    putchar('\n');

    print(foo);
    putchar('-');
    putchar('(');
    print(bar);
    putchar(')');
    putchar('=');
    print(subtract(foo, bar));
    putchar('\n');
    putchar('\n');

    /* Testing Multiplication */
    print(ten);
    putchar('*');
    print(fifteen);
    putchar('=');
    print(multiply(fifteen,ten));
    putchar('\n');

    print(foo);
    putchar('*');
    putchar('(');
    print(bar);
    putchar(')');
    putchar('=');
    print(multiply(foo, bar));
    putchar('\n');

    return EXIT_SUCCESS;
}
