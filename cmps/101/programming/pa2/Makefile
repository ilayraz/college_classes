FLAGS = -Wall -O3 -g
SOURCES = apint.c test.c
OBJECTS = apint.o test.o
HEADERS = apint.h
EXEBIN = apint

all: $(EXEBIN)

$(EXEBIN): $(OBJECTS) $(HEADERS)
	clang -o $(EXEBIN) $(OBJECTS)

$(OBJECTS): $(SOURCES) $(HEADERS)
	clang -c $(FLAGS) $(SOURCES)

clean :
	rm $(EXEBIN) $(OBJECTS)
