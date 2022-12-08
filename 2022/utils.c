//
// Created by niko on 5/12/22.
//

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <errno.h>

#include "utils.h"
#include "list.h"

char* int_to_string(int original_number) {
    // Division is mostly faster for small values, which we have.
    int number_of_digits = original_number < 0 ? 2 : 1;
    int number = abs(original_number);
    while (number != 0) {
        number /= 10;
        number_of_digits++;
    }
    char* result = malloc(number_of_digits * sizeof(char));
    snprintf(result, number_of_digits, "%d", original_number);
    return result;
}

char* size_t_to_string(size_t original_number) {
    // Division is mostly faster for small values, which we have.
    size_t number_of_digits = 1;
    size_t number = original_number;
    while (number != 0) {
        number /= 10;
        number_of_digits++;
    }
    char* result = malloc(number_of_digits * sizeof(char));
    snprintf(result, number_of_digits, "%zu", original_number);
    return result;
}

FILE* open_file_for_reading(const char* location) {
    errno = 0;
    FILE* input_file = fopen(location, "r");
    if (!input_file) {
        fprintf(stderr, "Could not open file %s: ", location);
        perror("");
        exit(EXIT_FAILURE);
    }
    return input_file;
}

File read_lines(const char* location) {
    List lines = list_create(100, sizeof(List*));

    List current_line = list_create_string(20);
    FILE* file = open_file_for_reading(location);

    int character;
    while ((character = getc(file)) != EOF) {
        if (character == '\n') {
            List* copy = list_dynamic_copy(&current_line);
            list_append_pointer(&lines, copy);
            list_clear(&current_line);
        } else {
            list_append_char(&current_line, (char) character);
        }
    }

    // We might still have a line if the file does not end in a newline.
    if (current_line.length != 0) {
        List* copy = list_dynamic_copy(&current_line);
        list_append_pointer(&lines, copy);
    }

    list_destroy(&current_line);

    return (File) {
            .lines = lines,
            .file = file
    };
}

void destroy_file(File* file) {
    fclose(file->file);

    // We must clear the current group, meaning we should free all nested lists first.
    for (size_t i = 0; i < file->lines.length; ++i) {
        List* line = list_get(&file->lines, i);
        list_destroy(line);
        free(line);
    }
    list_destroy(&file->lines);
}