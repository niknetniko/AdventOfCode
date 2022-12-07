//
// Created by niko on 5/12/22.
//

#ifndef ADVENTOFCODE_UTILS_H
#define ADVENTOFCODE_UTILS_H

#include <stdio.h>
#include "list.h"

// Convert an int to a string. You get ownership.
char* int_to_string(int number);

// Open a file for reading, while dealing with errors.
// You gte ownership of the result.
FILE* open_file_for_reading(const char* location);

typedef struct File {
    List lines;
    FILE* file;
} File;

// Read a file to lines, excluding newlines.
// You should call destroy_file on the result.
File read_lines(const char* location);

void destroy_file(File* file);

#endif //ADVENTOFCODE_UTILS_H
