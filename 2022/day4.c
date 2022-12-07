//
// Created by niko on 7/12/22.
//
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "day4.h"
#include "list.h"
#include "utils.h"

typedef struct Interval {
    long start;
    long end;
} Interval;

typedef struct Pair {
    Interval first;
    Interval second;
} Pair;

List input_to_pairs(const char* input) {
    FILE* input_file = open_file_for_reading(input);

    List pairs = list_create(1000, sizeof(Pair));

    List current_line = list_create_string(20);
    int input_char;
    while ((input_char = fgetc(input_file)) != EOF) {
        if (input_char == '\n') {
            // We can parse this.
            char* line = as_null_delimited_string(&current_line);

            // We know what the format is, so we can hardcode it.
            char* first_start = strtok(line, "-");
            char* first_end = strtok(NULL, ",");
            char* second_start = strtok(NULL, "-");
            char* second_end = strtok(NULL, "-"); // Delimiter doesn't matter.

            Pair pair = {
                    .first = {
                            .start = strtol(first_start, NULL, 10),
                            .end = strtol(first_end, NULL, 10)
                    },
                    .second = {
                            .start = strtol(second_start, NULL, 10),
                            .end = strtol(second_end, NULL, 10)
                    }
            };

            list_append(&pairs, &pair);

            free(line);
            list_clear(&current_line);
        } else {
            list_append_char(&current_line, (char) input_char);
        }
    }

    list_destroy(&current_line);

    return pairs;
}

bool does_a_include_b(const Interval* a, const Interval* b) {
    return a->start <= b->start && b->end <= a->end;
}

__attribute__((unused)) char* day4_part1(const char* input) {
    List pairs = input_to_pairs(input);

    int overlapping = 0;
    for (size_t i = 0; i < pairs.length; ++i) {
        Pair* pair = (Pair*) list_memory_of_index(&pairs, i);
        if (does_a_include_b(&pair->first, &pair->second) || does_a_include_b(&pair->second, &pair->first)) {
            overlapping++;
        }
    }

    list_destroy(&pairs);

    return int_to_string(overlapping);
}

__attribute__((unused)) char* day4_part2(const char* input) {
    return NULL;
}
