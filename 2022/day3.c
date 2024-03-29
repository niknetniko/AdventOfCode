//
// Created by niko on 6/12/22.
//

#include <stdio.h>
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

__attribute__((unused)) char* day3_part1(const char* input) {
    FILE* input_file = open_file_for_reading(input);

    int sum = 0;

    List current_line = list_create_for_char(20);
    int input_char;
    while ((input_char = fgetc(input_file)) != EOF) {
        if (input_char == '\n') {
            // We have found a newline, meaning we can process a backpack.
            size_t half = current_line.length / 2;
            List first_compartment = list_view(&current_line, 0, half);
            List second_compartment = list_view(&current_line, half, half);

            List intersection = list_intersection(&first_compartment, &second_compartment, NULL);
            assert(intersection.length >= 1);
            char wrong_type = list_get_char(&intersection, 0);
            sum += score_from_char(wrong_type);
            list_clear(&current_line);
        } else {
            list_append_char(&current_line, (char) input_char);
        }
    }

    return int_to_string(sum);
}

__attribute__((unused)) char* day3_part2(const char* input) {
    FILE* input_file = open_file_for_reading(input);

    int sum = 0;

    List current_group = list_create_for_list(3);
    List current_line = list_create_for_char(20);
    int input_char;
    while ((input_char = fgetc(input_file)) != EOF) {
        if (input_char == '\n') {
            // We have found a newline, meaning we can process a backpack.
            // Since we store the current line in the backpack, we must create a new line.
            List copy_of_line = list_copy(&current_line);
            list_append_list(&current_group, copy_of_line);

            // We have found a new group
            if (current_group.length == 3) {
                // We now have a list of lists, where the nested lists are the backpacks of the elves in the group.
                // We need to find the intersection of all three backpacks.
                List intersection = list_intersection(list_get_list_p(&current_group, 0),
                                                      list_get_list_p(&current_group, 1),
                                                      list_get_list_p(&current_group, 2), NULL);
                assert(intersection.length >= 1);
                char wrong_type = list_get_char(&intersection, 0);
                sum += score_from_char(wrong_type);

                list_clear(&current_group);
            }
            list_clear(&current_line);
        } else {
            list_append_char(&current_line, (char) input_char);
        }
    }

    return int_to_string(sum);
}
