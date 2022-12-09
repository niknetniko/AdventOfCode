//
// Created by niko on 9/12/22.
//
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "day9.h"
#include "utils.h"

typedef struct Pos {
    long row;
    long col;
} Pos;

void readjust_tail_if_need(const Pos* head_pos, Pos* tail_pos) {

    long diff_row = head_pos->row - tail_pos->row;
    long diff_col = head_pos->col - tail_pos->col;

    if (labs(diff_row) < 2 && labs(diff_col) < 2) {
        return;
    }

    // If moving in both axes, we must cap the movement to 1.
    // However, it should still be in the sign of the original.
    if (diff_row != 0 && diff_col != 0) {
        diff_row = diff_row < 0 ? -1 : 1;
        diff_col = diff_col < 0 ? -1 : 1;
    } else if (diff_row == 0) {
        diff_col = diff_col < 0 ? -1 : 1;
    } else {
        assert(diff_col == 0);
        diff_row = diff_row < 0 ? -1 : 1;
    }

    tail_pos->row = tail_pos->row + diff_row;
    tail_pos->col = tail_pos->col + diff_col;
}

void adjust_head(Pos* head_pos, char direction) {
    switch (direction) {
        case 'R':
            head_pos->col += 1;
            break;
        case 'L':
            head_pos->col -= 1;
            break;
        case 'U':
            head_pos->row += 1;
            break;
        case 'D':
            head_pos->row -= 1;
            break;
        default:
            assert(false);
    }
}

__attribute__((unused)) char* day9_part1(const char* input) {
    // Be lazy and read file to lines as always.
    File file = read_lines(input);

    Pos head_pos = {.row = 0, .col = 0};
    Pos tail_pos = {.row = 0, .col = 0};

    List visited_positions = list_create(20, sizeof(Pos));
    list_append(&visited_positions, &tail_pos);

    for (size_t i = 0; i < file.lines.length; ++i) {
        List* line = list_get(&file.lines, i);
        char* direction = strtok(as_null_delimited_string(line), " ");
        char* amount_string = strtok(NULL, " ");
        long amount = strtol(amount_string, NULL, 10);
        for (long j = 0; j < amount; ++j) {
            adjust_head(&head_pos, direction[0]);
            readjust_tail_if_need(&head_pos, &tail_pos);
            if (!list_contains(&visited_positions, (char*) &tail_pos)) {
                list_append(&visited_positions, &tail_pos);
            }
        }
    }

    return size_t_to_string(visited_positions.length);
}

__attribute__((unused)) char* day9_part2(const char* input) {
    // Be lazy and read file to lines as always.
    File file = read_lines(input);

    Pos head_pos = {.row = 0, .col = 0};
    Pos tail_pos[9] = {
            {.row = 0, .col = 0},
            {.row = 0, .col = 0},
            {.row = 0, .col = 0},
            {.row = 0, .col = 0},
            {.row = 0, .col = 0},
            {.row = 0, .col = 0},
            {.row = 0, .col = 0},
            {.row = 0, .col = 0},
            {.row = 0, .col = 0}
    };

    List visited_positions = list_create(20, sizeof(Pos));
    list_append(&visited_positions, &tail_pos);

    for (size_t i = 0; i < file.lines.length; ++i) {
        List* line = list_get(&file.lines, i);
        char* direction = strtok(as_null_delimited_string(line), " ");
        char* amount_string = strtok(NULL, " ");
        long amount = strtol(amount_string, NULL, 10);
        for (long j = 0; j < amount; ++j) {
            adjust_head(&head_pos, direction[0]);

            // Do it for tail 1.
            readjust_tail_if_need(&head_pos, &tail_pos[0]);

            // We know must move tails 2-9.
            for (int tail = 1; tail < 9; ++tail) {
                Pos* previous_tail = &tail_pos[tail - 1];
                Pos* tail_to_adjust = &tail_pos[tail];
                readjust_tail_if_need(previous_tail, tail_to_adjust);
            }

            if (!list_contains(&visited_positions, (char*) &tail_pos[8])) {
                list_append(&visited_positions, &tail_pos[8]);
            }
        }
    }

    return size_t_to_string(visited_positions.length);
}
