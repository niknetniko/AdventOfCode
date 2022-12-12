//
// Created by niko on 8/12/22.
//
#include <stdlib.h>
#include <assert.h>

#include "day8.h"
#include "utils.h"

typedef struct TreeGrid {
    size_t width;
    size_t height;
    unsigned char** grid;
} TreeGrid;

bool is_visible_from(TreeGrid grid, long x, long y, int x_delta, int y_delta) {
    assert(0 < x && (size_t) x < grid.width - 1 && 0 < y && (size_t) y < grid.height - 1);
    unsigned char tree_height = grid.grid[y][x];

    x += x_delta;
    y += y_delta;

    while (0 <= x && (size_t) x < grid.width && 0 <= y && (size_t) y < grid.height) {

        unsigned char new_height = grid.grid[y][x];
        if (new_height >= tree_height) {
            return false;
        }

        x += x_delta;
        y += y_delta;
    }

    return true;
}

size_t calculate_viewing_distance_for(TreeGrid grid, long x, long y, int x_delta, int y_delta) {
    unsigned char tree_height = grid.grid[y][x];
    size_t distance = 0;
    x += x_delta;
    y += y_delta;

    while (0 <= x && (size_t) x < grid.width && 0 <= y && (size_t) y < grid.height) {
        unsigned char new_height = grid.grid[y][x];
        distance++;
        if (new_height >= tree_height) {
            // Done.
            return distance;
        }

        x += x_delta;
        y += y_delta;
    }

    return distance;
}

bool is_visible(TreeGrid grid, size_t x, size_t y) {
    return is_visible_from(grid, (long) x, (long) y, 0, 1) ||
           is_visible_from(grid, (long) x, (long) y, 1, 0) ||
           is_visible_from(grid, (long) x, (long) y, 0, -1) ||
           is_visible_from(grid, (long) x, (long) y, -1, 0);
}

size_t calculate_viewing_distance(TreeGrid grid, size_t x, size_t y) {
    return calculate_viewing_distance_for(grid, (long) x, (long) y, 0, 1) *
           calculate_viewing_distance_for(grid, (long) x, (long) y, 1, 0) *
           calculate_viewing_distance_for(grid, (long) x, (long) y, 0, -1) *
           calculate_viewing_distance_for(grid, (long) x, (long) y, -1, 0);
}

TreeGrid parse_to_grid(const char* input) {
    File lines = read_lines(input);
    size_t height = lines.lines.length;
    size_t width = ((List*) list_get_pointer(&lines.lines, 0))->length;

    TreeGrid grid = {
            .width = width,
            .height = height,
            .grid = malloc(sizeof(unsigned char*) * height)
    };

    // Parse the input into a grid.
    for (size_t i = 0; i < height; ++i) {
        List* line = (List*) list_get_pointer(&lines.lines, i);
        assert(line->length == width);
        grid.grid[i] = malloc(sizeof(unsigned char) * width);
        for (size_t j = 0; j < width; ++j) {
            char number = list_get_char(line, j);
            // Since '0' - '0' == 0 and thus '1' - '0' == 1, we can use this.
            unsigned char as_numeric_value = number - '0';
            assert(0 <= as_numeric_value && as_numeric_value <= 9);
            grid.grid[i][j] = as_numeric_value;
        }
    }

    return grid;
}

__attribute__((unused)) char* day8_part1(const char* input) {

    TreeGrid grid = parse_to_grid(input);

    // To count the visible trees, don't count the outer edge.
    // Top and bottom rows = width * 2, left and right = height * 2, but minus 2 for the corners.
    size_t visible = grid.width * 2 + (grid.height - 2) * 2;

    for (size_t y = 1; y < grid.height - 1; ++y) {
        for (size_t x = 1; x < grid.width - 1; ++x) {
            if (is_visible(grid, x, y)) {
                visible++;
            }
        }
    }

    return size_t_to_string(visible);
}

__attribute__((unused)) char* day8_part2(const char* input) {
    TreeGrid grid = parse_to_grid(input);

    // To count the visible trees, don't count the outer edge.
    // Top and bottom rows = width * 2, left and right = height * 2, but minus 2 for the corners.
    size_t current_max = 0;

    for (size_t y = 0; y < grid.height; ++y) {
        for (size_t x = 0; x < grid.width; ++x) {
            size_t view_distance = calculate_viewing_distance(grid, x, y);
            if (view_distance > current_max) {
                current_max = view_distance;
            }
        }
    }

    return size_t_to_string(current_max);
}
