//
// Created by niko on 2/12/22.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "day1.h"

int main(int argc, char **argv) {
    // 1 for the program name, plus 3 for the required params.
    if (argc != 4) {
        char *program_name = argv[0];
        printf("Usage: %s day part input\n", program_name);
        printf("  day:   number of day to run, e.g. 15\n");
        printf("  part:  part of day to run, e.g. 1\n");
        printf("  input: path to input file, e.g. input1.txt\n");
        return EXIT_FAILURE;
    }

    // For ease of use, we create on big executable.
    // TODO: this can be smarter with a macro

    char *result;
    if (strcmp(argv[1], "1") == 0) {
        if (strcmp(argv[2], "1") == 0) {
            result = day1_part1(argv[3]);
        } else if (strcmp(argv[2], "2") == 0) {
            result = day1_part2(argv[3]);
        } else {
            fprintf(stderr, "Unknown part %s, doing nothing.", argv[1]);
            return EXIT_FAILURE;
        }
    } else {
        fprintf(stderr, "Unknown day %s, doing nothing.", argv[1]);
        return EXIT_FAILURE;
    }

    printf("%s\n", result);

    free(result);

    return EXIT_SUCCESS;
}