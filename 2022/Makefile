CC=clang
CFLAGS=-std=c17 -lm

SOURCES = day1.c day2.c day3.c day4.c day5.c day6.c list.c utils.c
HEADERS = $(patsubst %.c,%.h, $(SOURCES))
OBJS = $(patsubst %.c,%.o, $(SOURCES))

%o: %.c
	$(CC) $(CFLAGS) -c $<

main: main.o $(OBJS)
	$(CC) $(CFLAGS) -g -O0 -Wall -Wextra $^ -o $@

main_fast: main.o $(OBJS)
	$(CC) $(CFLAGS) -Ofast $^ -o $@

all: main

clean:
	rm -f $(OBJS) main main.o