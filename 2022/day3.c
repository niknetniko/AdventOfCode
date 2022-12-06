//
// Created by niko on 6/12/22.
//

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <assert.h>

#include "day3.h"
#include "utils.h"
#include "list.h"

int score_from_char(char letter) {
    assert(('A' <= letter && letter <= 'Z') || ('a' <= letter && letter <= 'z'));
    // The lowercase ones are higher than uppercase ones.
    if (letter > 'Z') {
        return (int) letter - 'a' + 1;
    } else {
        return (int) letter - 'A' + 27;
    }


}

char* day3_part1(const char* input) {

    int sum = 0;

    FILE* input_file = open_file_for_reading(input);

    List current_line = list_create_string(20);
    int input_char;
    while ((input_char = fgetc(input_file)) != EOF) {
        if (input_char == '\n') {
            // We have found a newline, meaning we can process a backpack.
            size_t half = current_line.length / 2;
            List first_compartment = list_view(&current_line, 0, half);
            List second_compartment = list_view(&current_line, half, half);

            List intersection = list_char_intersection(&first_compartment, &second_compartment);
            assert(intersection.length >= 1);
            char wrong_type = list_get_char(&intersection, 0);
            sum += score_from_char(wrong_type);
            list_clear(&current_line);
            list_destroy(&intersection);
        } else {
            list_append_char(&current_line, (char) input_char);
        }
    }

    return int_to_string(sum);
}

char* day3_part2(const char* input) {
    return NULL;
}
