CC=clang
CFLAGS=-std=c17 -lm -g -O0 -Wall -Wextra

SOURCES = day1.c day2.c day3.c day4.c day5.c list.c utils.c
HEADERS = $(patsubst %.c,%.h, $(SOURCES))
OBJS = $(patsubst %.c,%.o, $(SOURCES))

%o: %.c
	$(CC) $(CFLAGS) -c $<

main: main.o $(OBJS)
	$(CC) $(CFLAGS) $^ -o $@

all: main

clean:
	rm -f $(OBJS) main main.o