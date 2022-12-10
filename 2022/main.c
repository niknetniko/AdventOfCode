//
// Created by niko on 2/12/22.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <assert.h>
#include <stdbool.h>

#include "day1.h"
#include "day2.h"
#include "day3.h"
#include "day4.h"
#include "day5.h"
#include "day6.h"
#include "day7.h"
#include "day8.h"
#include "day9.h"
#include "day10.h"

// day1_part1

#define PART_CALL(day_param) ({                         \
    if (argv[2][0] == '1') {                            \
        result = day ## day_param ## _part1(argv[3]);   \
    } else {                                            \
        result = day ## day_param ## _part2(argv[3]);   \
    }                                                   \
    })

const int max_day = 10;

int main(int argc, char** argv) {
    // 1 for the program name, plus 3 for the required params.
    if (argc != 4) {
        char* program_name = argv[0];
        printf("Usage: %s day part input\n", program_name);
        printf("  day:   number of day to run, e.g. 15\n");
        printf("  part:  part of day to run, e.g. 1\n");
        printf("  input: path to input file, e.g. input1.txt\n");
        return EXIT_FAILURE;
    }

    // Start with a clean slate.
    errno = 0;
    char* end;
    long day = strtol(argv[1], &end, 10);

    // The day was not a number.
    if (*end != '\0' || errno != 0 || day > max_day) {
        fprintf(stderr, "Day %s is not a valid day, must be in range [1-%d]", argv[1], max_day);
        return EXIT_FAILURE;
    }

    // For ease of use, we create on big executable.
    if (argv[2][0] != '1' && argv[2][0] != '2') {
        fprintf(stderr, "Part %s is not a valid part, must be 1 or 2", argv[1]);
        return EXIT_FAILURE;
    }

    char* result;
    switch (day) {
        case 1:
            PART_CALL(1);
            break;
        case 2:
            PART_CALL(2);
            break;
        case 3:
            PART_CALL(3);
            break;
        case 4:
            PART_CALL(4);
            break;
        case 5:
            PART_CALL(5);
            break;
        case 6:
            PART_CALL(6);
            break;
        case 7:
            PART_CALL(7);
            break;
        case 8:
            PART_CALL(8);
            break;
        case 9:
            PART_CALL(9);
            break;
        case 10:
            PART_CALL(10);
            break;
        default:
            // No.
            assert(false);
    }

    printf("%s\n", result);

    return EXIT_SUCCESS;
}