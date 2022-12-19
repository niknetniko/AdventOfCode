//
// Created by niko on 19/12/22.
//
#include "day14.h"
#include "utils.h"

#include <stdlib.h>
#include <string.h>

typedef enum Content {
    AIR, ROCK, SAND,
} Content;

// Y = ROW, X = COL
typedef struct Point {
    int row;
    int col;
} Point;


void draw_rocks(int rows, int cols, Content grid[rows][cols], Point from, Point to) {
    int delta_row = to.row - from.row;
    int delta_col = to.col - from.col;

    // One of the two must be zero.
    assert(delta_row == 0 || delta_col == 0);
    // This allows use to use two loops and just do them.

    int delta_row_row = delta_row >= 0 ? 1 : -1;
    int delta_col_col = delta_col >= 0 ? 1 : -1;

    int col = from.col;
    int row = from.row;

    while (row != to.row) {
        grid[row][col] = ROCK;
        row += delta_row_row;
    }

    while (col != to.col) {
        grid[row][col] = ROCK;
        col += delta_col_col;
    }
    grid[row][col] = ROCK;
}

typedef enum SandState {
    SETTLED, OBLIVION, REACHED_TOP
} SandState;

typedef struct SandResult {
    SandState state;
    Point last_position;
} SandResult;

const Point sand_start = {.row = 0, .col = 500};

SandResult place_sand(int rows, int cols, Content grid[rows][cols], bool has_floor, int col_offset) {

    Point current_position = {.row = sand_start.row, .col = sand_start.col + col_offset};
    while (true) {
        // Check if we go out of bounds...
        if (!has_floor) {
            if (current_position.row + 1 >= rows || current_position.col + 1 >= cols || current_position.col - 1 < 0) {
                return (SandResult) {.state = OBLIVION, .last_position = current_position};
            }
        }
        // Try to go down.
        if (grid[current_position.row + 1][current_position.col] == AIR) {
            current_position = (Point) {.row = current_position.row + 1, .col = current_position.col};
            continue;
        }

        // Finally, we try to go left.
        if (grid[current_position.row + 1][current_position.col - 1] == AIR) {
            current_position = (Point) {.row = current_position.row + 1, .col = current_position.col - 1};
            continue;
        }

        // Try to go to the right.
        if (grid[current_position.row + 1][current_position.col + 1] == AIR) {
            current_position = (Point) {.row = current_position.row + 1, .col = current_position.col + 1};
            continue;
        }

        // If we get here, it means we are blocked, so the sand will settle.
        grid[current_position.row][current_position.col] = SAND;

        if (has_floor) {
            if (current_position.col == sand_start.col + col_offset && current_position.row == sand_start.row) {
                return (SandResult) {.state = REACHED_TOP, .last_position = current_position};
            }
        }

        return (SandResult) {.state = SETTLED, .last_position = current_position};
    }

    assert(false);
}

char* day14_part1(const char* input) {
    // Too lazy to move to common function...
    File file = read_lines(input);

    List list_of_points = list_create_for_list(file.lines.length);

    // Y = row
    // X = col
    int min_row, min_col, max_row, max_col;
    // First, parse all instructions into a list of points, while keeping the min, max for x and y.
    for (size_t i = 0; i < file.lines.length; ++i) {
        List* line = list_get_list_p(&file.lines, i);
        char* char_line = as_null_delimited_string(line);
        List point_parts = split_string(char_line, " -> ");
        List line_list = list_create(10, sizeof(Point));
        for (size_t j = 0; j < point_parts.length; ++j) {
            char* point = list_get_pointer(&point_parts, j);
            int col = (int) strtol(strtok(point, ","), NULL, 10);
            int row = (int) strtol(strtok(NULL, ","), NULL, 10);
            Point p = {.col = col, .row = row};
            list_append(&line_list, &p);
            min_row = int_min(min_row, row);
            min_col = int_min(min_col, col);
            max_row = int_max(max_row, row);
            max_col = int_max(max_col, col);
        }
        list_append_list(&list_of_points, line_list);
    }

    max_row += 1;
    max_col += 1;

    assert(min_row >= 0 && max_row > min_row);
    assert(min_col >= 0 && max_col > min_col);

    // We need one more at each side, so plus 2 for row and col.
    Content grid[max_row][max_col];
    for (int r = 0; r < max_row; ++r) {
        for (int c = 0; c < max_col - 1; ++c) {
            grid[r][c] = AIR;
        }
    }

    for (size_t i = 0; i < list_of_points.length; ++i) {
        List* line = list_get_list_p(&list_of_points, i);
        for (size_t j = 1; j < line->length; ++j) {
            Point from = *((Point*) list_get(line, j - 1));
            Point to = *((Point*) list_get(line, j));
            draw_rocks(max_row, max_col, grid, from, to);
        }
    }

    size_t sand_counter = 0;
    while (place_sand(max_row, max_col, grid, false, 0).state == SETTLED) {
        sand_counter++;
    }

    return size_t_to_string(sand_counter);
}

char* day14_part2(const char* input) {
    // Too lazy to move to common function...
    File file = read_lines(input);

    List list_of_points = list_create_for_list(file.lines.length);

    // Y = row
    // X = col
    int min_row, min_col, max_row, max_col;
    // First, parse all instructions into a list of points, while keeping the min, max for x and y.
    for (size_t i = 0; i < file.lines.length; ++i) {
        List* line = list_get_list_p(&file.lines, i);
        char* char_line = as_null_delimited_string(line);
        List point_parts = split_string(char_line, " -> ");
        List line_list = list_create(10, sizeof(Point));
        for (size_t j = 0; j < point_parts.length; ++j) {
            char* point = list_get_pointer(&point_parts, j);
            int col = (int) strtol(strtok(point, ","), NULL, 10);
            int row = (int) strtol(strtok(NULL, ","), NULL, 10);
            Point p = {.col = col, .row = row};
            list_append(&line_list, &p);
            min_row = int_min(min_row, row);
            min_col = int_min(min_col, col);
            max_row = int_max(max_row, row);
            max_col = int_max(max_col, col);
        }
        list_append_list(&list_of_points, line_list);
    }

    max_row += 1;
    max_col += 1;

    assert(min_row >= 0 && max_row > min_row);
    assert(min_col >= 0 && max_col > min_col);

    // We will need to add space for the infinite floor...
    // Just assume we need max 3* height at the end, otherwise it won't work.
    max_col = max_col + (3 * max_row);
    max_row = max_row + 2;

    // We need one more at each side, so plus 2 for row and col.
    Content grid[max_row][max_col];
    for (int r = 0; r < max_row; ++r) {
        for (int c = 0; c < max_col - 1; ++c) {
            grid[r][c] = AIR;
        }
    }
    for (int c = 0; c < max_col; ++c) {
        grid[max_row - 1][c] = ROCK;
    }

    for (size_t i = 0; i < list_of_points.length; ++i) {
        List* line = list_get_list_p(&list_of_points, i);
        for (size_t j = 1; j < line->length; ++j) {
            Point from = *((Point*) list_get(line, j - 1));
            Point to = *((Point*) list_get(line, j));
            Point offset_from = {.row = from.row, .col = from.col + max_row};
            Point offset_to = {.row = to.row, .col = to.col + max_row};
            draw_rocks(max_row, max_col, grid, offset_from, offset_to);
        }
    }

    size_t sand_counter = 0;
    while (place_sand(max_row, max_col, grid, true, max_row).state != REACHED_TOP) {
        sand_counter++;
    }

    return size_t_to_string(sand_counter + 1);
}
