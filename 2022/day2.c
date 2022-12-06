//
// Created by niko on 6/12/22.
//
#include <stdlib.h>
#include <assert.h>
#include <stdbool.h>
#include <stdio.h>

#include "day2.h"
#include "list.h"
#include "utils.h"

typedef enum Play {
    ROCK, PAPER, SCISSORS
} Play;

Play letter_to_play(char letter) {
    if (letter == 'A' || letter == 'X') {
        return ROCK;
    } else if (letter == 'B' || letter == 'Y') {
        return PAPER;
    } else if (letter == 'C' || letter == 'Z') {
        return SCISSORS;
    } else {
        // No.
        assert(false);
    }
}

int get_shape_score(Play play) {
    switch (play) {
        case ROCK:
            return 1;
        case PAPER:
            return 2;
        case SCISSORS:
            return 3;
    }
}

typedef struct Match {
    Play opponent;
    Play me;
} Match;

int get_match_score(const Match* match) {
    switch (match->opponent) {
        case ROCK:
            switch (match->me) {
                case ROCK:
                    return 3;
                case PAPER:
                    return 6;
                case SCISSORS:
                    return 0;
            }
        case PAPER:
            switch (match->me) {
                case ROCK:
                    return 0;
                case PAPER:
                    return 3;
                case SCISSORS:
                    return 6;
            }
        case SCISSORS:
            switch (match->me) {
                case ROCK:
                    return 6;
                case PAPER:
                    return 0;
                case SCISSORS:
                    return 3;
            }
    }
}

__attribute__((unused)) char* day2_part1(const char* input) {

    // First, parse the day into a list of matches.
    // We just copy the struct into the list, since we are lazy :)
    List matches = list_create(1000, sizeof(Match));

    FILE* input_file = fopen(input, "rw");
    char line[5]; // One for opponent, one for space, one for me, one for newline and one for null byte.
    while (fgets(line, 5, input_file) != NULL) {
        Match match = {.opponent = letter_to_play(line[0]), .me = letter_to_play(line[2])};
        assert(line[1] == ' ');
        assert(line[3] == '\n');
        assert(line[4] == '\0');
        list_append(&matches, &match);
    }

    int my_score = 0;
    for (size_t i = 0; i < matches.length; ++i) {
        Match* match = (Match*) list_memory_of_index(&matches, i);
        my_score += get_match_score(match);
        my_score += get_shape_score(match->me);
    }

    list_destroy(&matches);

    return int_to_string(my_score);
}

__attribute__((unused)) char* day2_part2(const char* input) {
    return NULL;
}
