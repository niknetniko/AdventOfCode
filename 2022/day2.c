//
// Created by niko on 6/12/22.
//
#include <assert.h>
#include <stdbool.h>
#include <stdio.h>

#include "day2.h"
#include "list.h"
#include "utils.h"

typedef enum Play {
    ROCK = 'X', PAPER = 'Y', SCISSORS = 'Z'
} Play;

typedef enum Outcome {
    LOSS = 'X', DRAW = 'Y', WIN = 'Z'
} Outcome;

Play opponent_letter_to_play(char letter) {
    if (letter == 'A') {
        return ROCK;
    } else if (letter == 'B') {
        return PAPER;
    } else if (letter == 'C') {
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

typedef struct MatchOrExpectedOutcome {
    Play opponent;
    char me;
} MatchOrExpectedOutcome;

int get_match_score(const MatchOrExpectedOutcome* match) {
    switch (match->opponent) {
        case ROCK:
            switch ((Play) match->me) {
                case ROCK:
                    return 3;
                case PAPER:
                    return 6;
                case SCISSORS:
                    return 0;
            }
        case PAPER:
            switch ((Play) match->me) {
                case ROCK:
                    return 0;
                case PAPER:
                    return 3;
                case SCISSORS:
                    return 6;
            }
        case SCISSORS:
            switch ((Play) match->me) {
                case ROCK:
                    return 6;
                case PAPER:
                    return 0;
                case SCISSORS:
                    return 3;
            }
    }
}

List input_to_matches(const char* input) {
    // We just copy the struct into the list, since we are lazy :)
    List matches = list_create(1000, sizeof(MatchOrExpectedOutcome));

    FILE* input_file = fopen(input, "rw");
    char line[5]; // One for opponent, one for space, one for me, one for newline and one for null byte.
    while (fgets(line, 5, input_file) != NULL) {
        MatchOrExpectedOutcome match = {.opponent = opponent_letter_to_play(line[0]), .me = line[2]};
        assert(line[1] == ' ');
        assert(line[3] == '\n');
        assert(line[4] == '\0');
        list_append(&matches, &match);
    }

    return matches;
}

__attribute__((unused)) char* day2_part1(const char* input) {

    List matches = input_to_matches(input);
    int my_score = 0;
    for (size_t i = 0; i < matches.length; ++i) {
        MatchOrExpectedOutcome* match = (MatchOrExpectedOutcome*) list_memory_of_index(&matches, i);
        my_score += get_match_score(match);
        my_score += get_shape_score((Play) match->me);
    }
    list_destroy(&matches);
    return int_to_string(my_score);
}

Play expected_outcome_to_needed_play(MatchOrExpectedOutcome* match) {
    switch ((Outcome) match->me) {
        case LOSS:
            switch (match->opponent) {
                case ROCK:
                    return SCISSORS;
                case PAPER:
                    return ROCK;
                case SCISSORS:
                    return PAPER;
            }
        case DRAW:
            return match->opponent;
        case WIN:
            switch (match->opponent) {
                case ROCK:
                    return PAPER;
                case PAPER:
                    return SCISSORS;
                case SCISSORS:
                    return ROCK;
            }
    }
}

__attribute__((unused)) char* day2_part2(const char* input) {
    List matches = input_to_matches(input);
    int my_score = 0;
    for (size_t i = 0; i < matches.length; ++i) {
        MatchOrExpectedOutcome* expected_outcome = (MatchOrExpectedOutcome*) list_memory_of_index(&matches, i);
        MatchOrExpectedOutcome actual_match = {
                .opponent = expected_outcome->opponent,
                .me = expected_outcome_to_needed_play(expected_outcome)
        };
        my_score += get_match_score(&actual_match);
        my_score += get_shape_score(actual_match.me);
    }
    list_destroy(&matches);
    return int_to_string(my_score);
}
