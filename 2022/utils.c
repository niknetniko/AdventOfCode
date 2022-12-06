//
// Created by niko on 5/12/22.
//

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <errno.h>

#include "utils.h"

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
