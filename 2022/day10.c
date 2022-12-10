//
// Created by niko on 10/12/22.
//
#include <stdlib.h>
#include <string.h>

#include "day10.h"
#include "list.h"
#include "utils.h"


__attribute__((unused)) char* day10_part1(const char* input) {
    File file = read_lines(input);

    long register_x = 1;
    List saved_values = list_create(10, sizeof(long));
    long cycle_counter = 1;
    for (size_t ip = 0; ip < file.lines.length; ++ip) {
        // We could parse everything first, but why would I?
        // If, in a future day, this needs to be expanded, we'll do it then.
        char* line = as_null_delimited_string(list_get(&file.lines, ip));

        char* instruction = strtok(line, " ");

        // For both instructions, the first cycle does nothing.
        cycle_counter++;
        if (cycle_counter == 20 || cycle_counter == 60 || cycle_counter == 100 || cycle_counter == 140 || cycle_counter == 180 || cycle_counter == 220) {
            long value = register_x * cycle_counter;
            list_append(&saved_values, &value);
        }

        // Handle the second cycle of the addx instruction.
        if (strcmp(instruction, "addx") == 0) {
            char* argument = strtok(NULL, " ");
            long long_arg = strtol(argument, NULL, 10);
            register_x += long_arg;

            cycle_counter++;
            if (cycle_counter == 20 || cycle_counter == 60 || cycle_counter == 100 || cycle_counter == 140 || cycle_counter == 180 || cycle_counter == 220) {
                long value = register_x * cycle_counter;
                list_append(&saved_values, &value);
            }
        }
    }

    long sum = 0;
    for (size_t i = 0; i < saved_values.length; ++i) {
        long* saved_value = (long*) list_memory_of_index(&saved_values, i);
        sum += *saved_value;
    }

    return long_to_string(sum);
}

__attribute__((unused)) char* day10_part2(const char* input) {
    return NULL;
}