//
// Created by niko on 2/12/22.
//

#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#include "day1.h"
#include "list.h"
#include "utils.h"

List calculate_elf_sums(const char* input) {
    // Keep an array with the sum for each elf.
    List elf_sums = list_create_for_int(10);

    // Keep an array of the lines for the current elf.
    List lines = list_create(5, sizeof(char*));

    // The current line we are reading.
    List current_line = list_create_for_char(5);

    FILE* input_file = open_file_for_reading(input);
    int previous_char;
    int input_char;
    while ((input_char = fgetc(input_file)) != EOF) {
        if (input_char == '\n') {
            // We have a new line...
            if (previous_char == '\n') {
                // We will get a new elf.
                // We must now sum all lines.
                int sum = 0;
                for (size_t i = 0; i < lines.length; ++i) {
                    char* line = list_get_pointer(&lines, i);
                    int number = atoi(line); // NOLINT(cert-err34-c)
                    sum += number;
                }
                list_append_int(&elf_sums, sum);
                list_clear(&lines);
            } else {
                // We have a new line within the same elf.
                list_append_pointer(&lines, as_null_delimited_string(&current_line));
                list_clear(&current_line);
            }
        } else {
            list_append_char(&current_line, (char) input_char);
        }
        previous_char = input_char;
    }

    return elf_sums;
}


__attribute__((unused)) char* day1_part1(const char* input) {
    List elf_sums = calculate_elf_sums(input);
    int max_calories = list_max_int(&elf_sums);
    return int_to_string(max_calories);
}

__attribute__((unused)) char* day1_part2(const char* input) {
    List elf_sums = calculate_elf_sums(input);
    list_sort_int(&elf_sums, false);
    List top_three = list_view(&elf_sums, 0, 3);
    int sum_calories = list_sum_int(&top_three);
    return int_to_string(sum_calories);
}