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
        if (cycle_counter == 20 || cycle_counter == 60 || cycle_counter == 100 || cycle_counter == 140 ||
            cycle_counter == 180 || cycle_counter == 220) {
            long value = register_x * cycle_counter;
            list_append(&saved_values, &value);
        }

        // Handle the second cycle of the addx instruction.
        if (strcmp(instruction, "addx") == 0) {
            char* argument = strtok(NULL, " ");
            long long_arg = strtol(argument, NULL, 10);
            register_x += long_arg;

            cycle_counter++;
            if (cycle_counter == 20 || cycle_counter == 60 || cycle_counter == 100 || cycle_counter == 140 ||
                cycle_counter == 180 || cycle_counter == 220) {
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

void draw_sprite(long middle, long drawing_pixel, char* screen_row) {
    drawing_pixel = drawing_pixel - 1;
    long min = middle - 1;
    if (min < 0) {
        min = 0;
    }
    long max = middle + 1;
    if (max > 39) {
        max = 39;
    }
    if (middle < 0) {
        middle = 0;
    }
    if (middle > 39) {
        middle = 39;
    }

    if (min == drawing_pixel) {
        screen_row[min] = '#';
    }
    if (middle == drawing_pixel) {
        screen_row[middle] = '#';
    }
    if (max == drawing_pixel) {
        screen_row[max] = '#';
    }
}

__attribute__((unused)) char* day10_part2(const char* input) {
    char screen[6][41];
    File file = read_lines(input);

    for (int i = 0; i < 6; ++i) {
        for (int j = 0; j < 40; ++j) {
            screen[i][j] = '.';
        }
        screen[i][40] = '\0';
    }

    long register_x = 1;
    long cycle_counter = 1;
    for (size_t ip = 0; ip < file.lines.length; ++ip) {
        // We could parse everything first, but why would I?
        // If, in a future day, this needs to be expanded, we'll do it then.
        char* line = as_null_delimited_string(list_get(&file.lines, ip));

        char* instruction = strtok(line, " ");

        // For both instructions, the first cycle does nothing.
        long row = cycle_counter / 40;
        long drawing_pixel = cycle_counter - (row * 40);
        // Draw pixel for first cycle...
        draw_sprite(register_x, drawing_pixel, screen[row]);
        cycle_counter++;

        // Handle the second cycle of the addx instruction.
        if (strcmp(instruction, "addx") == 0) {
            row = cycle_counter / 40;
            drawing_pixel = cycle_counter - (row * 40);
            draw_sprite(register_x, drawing_pixel, screen[row]);

            char* argument = strtok(NULL, " ");
            long long_arg = strtol(argument, NULL, 10);
            register_x += long_arg;

            cycle_counter++;
        }
    }

    char* big_result = malloc(6 * 41 * sizeof(char));
    for (int i = 0; i < 6; ++i) {
        sprintf(big_result + (i * 41), "%s\n", screen[i]);
    }

    return big_result;
}