//
// Created by niko on 5/12/22.
//

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

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
    List lines = list_create_for_list(100);

    List current_line = list_create_for_char(20);
    FILE* file = open_file_for_reading(location);

    int character;
    while ((character = getc(file)) != EOF) {
        if (character == '\n') {
            List copy = list_copy(&current_line);
            list_append_list(&lines, copy);
            list_clear(&current_line);
        } else {
            list_append_char(&current_line, (char) character);
        }
    }

    // We might still have a line if the file does not end in a newline.
    if (current_line.length != 0) {
        List copy = list_copy(&current_line);
        list_append_list(&lines, copy);
    }

    return (File) {
            .lines = lines,
            .file = file
    };
}

char* long_to_string(long original_number) {
    // Division is mostly faster for small values, which we have.
    int number_of_digits = original_number < 0 ? 2 : 1;
    long number = labs(original_number);
    while (number != 0) {
        number /= 10;
        number_of_digits++;
    }
    char* result = malloc(number_of_digits * sizeof(char));
    snprintf(result, number_of_digits, "%ld", original_number);
    return result;
}

List split_string(const char* source, const char* delimiter) {
    List result = list_create_for_pointer(10);

    unsigned long skip = strlen(delimiter);
    unsigned long total_length = strlen(source);
    const char* prev_position = source;
    const char* next_position = strstr(source, delimiter);
    while (next_position) {
        // We want from the previous position until the new position as a new string.
        // To do so, copy the memory, as we don't want to modify the existing string.
        size_t number_of_bytes = next_position - prev_position;
        char* part = malloc(sizeof(char) * (number_of_bytes + 1));
        if (!part) {
            fprintf(stderr, "Not enough memory to split string!\n");
            exit(EXIT_FAILURE);
        }
        memcpy(part, prev_position, number_of_bytes);
        part[number_of_bytes] = '\0';
        list_append_pointer(&result, part);

        prev_position = next_position + skip;
        // We might not have the next position...
        if (prev_position > source + total_length) {
            next_position = NULL;
        } else {
            next_position = strstr(prev_position, delimiter);
        }
    }

    // Save the last part of the string.
    unsigned long remainder = source + total_length - prev_position;
    if (remainder > skip) {
        char* part = malloc(sizeof(char) * (remainder + 1));
        if (!part) {
            fprintf(stderr, "Not enough memory to split string!\n");
            exit(EXIT_FAILURE);
        }
        memcpy(part, prev_position, remainder);
        part[remainder] = '\0';
        list_append_pointer(&result, part);
    }

    return result;
}

int int_min(int a, int b) {
    if (a < b) {
        return a;
    } else {
        return b;
    }
}

int int_max(int a, int b) {
    if (a >= b) {
        return a;
    } else {
        return b;
    }
}
