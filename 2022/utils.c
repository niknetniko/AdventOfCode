//
// Created by niko on 5/12/22.
//

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <errno.h>

#include "utils.h"
#include "list.h"

char* int_to_string(int number) {
    size_t size = (size_t) (ceil(log10(number)) + 1);
    if (number < 0) {
        // For the "-"
        size += 1;
    }
    char* result = malloc(size * sizeof(char));
    snprintf(result, size, "%d", number);
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