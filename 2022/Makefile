CC=clang
CFLAGS=-std=c17 -lm

SOURCES = day1.c day2.c day3.c day4.c day5.c day6.c day7.c day8.c day9.c day10.c day11.c day12.c day13.c day14.c day15.c day16.c list.c utils.c
HEADERS = $(patsubst %.c,%.h, $(SOURCES))
OBJS = $(patsubst %.c,%.o, $(SOURCES))

%o: %.c
	$(CC) $(CFLAGS) -c $<

main: CFLAGS += -g -O0 -Wall -Wextra
main: main.o $(OBJS)
	$(CC) $(CFLAGS) $^ -o $@

main_fast: CFLAGS += -Ofast
main_fast: main.o $(OBJS)
	$(CC)  $(CFLAGS) $^ -o $@

all: main

clean:
	rm -f $(OBJS) main main.o main_fast