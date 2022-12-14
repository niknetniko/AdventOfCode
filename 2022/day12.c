//
// Created by niko on 12/12/22.
//
#include <stdlib.h>
#include <stdint.h>

#include "day12.h"
#include "utils.h"

typedef struct Cell {
    unsigned height;
    size_t min_distance;
    bool visited;
    size_t row;
    size_t col;
} Cell;

int sort_cells(const void* a, const void* b) {
    const Cell* cell_a = *(const Cell**) a;
    const Cell* cell_b = *(const Cell**) b;

    if (cell_a->min_distance < cell_b->min_distance) {
        return 1;
    }
    if (cell_a->min_distance > cell_b->min_distance) {
        return -1;
    }
    assert(cell_a->min_distance == cell_b->min_distance);
    return 0;
}

bool allow_for_height_part1(const Cell* current, const Cell* neighbour) {
    // We can jump down as much as we want, but can only go up one.
    // This means the neighbour's height must be less than ours minus one.
    return neighbour->height <= current->height + 1;
}

bool allow_for_height_part2(const Cell* current, const Cell* neighbour) {
    // Here, we need to reverse, since we do the path in reverse.
    // This means we can jump as high as we want, but can only drop down one.
    // The neighbour's height should be at least ours plus one.
    return neighbour->height + 1 >= current->height == 1;
}

void do_pathfinding_to_top(long rows, long cols, Cell grid[rows][cols], Cell* start_position,
                           bool (allow_height)(const Cell*, const Cell*)) {
    // We implement Dijkstra's algorithm to do pathfinding.
    // To keep things simple, the current minimal distance to a node is kept in the node itself, as is the visitation state.

    // First, reset all state and add to the unvisited set.
    List unvisited = list_create_for_pointer(rows * cols);
    for (long row = 0; row < rows; ++row) {
        for (long col = 0; col < cols; ++col) {
            grid[row][col].min_distance = SIZE_MAX / 2; // /2 to prevent overflow
            grid[row][col].visited = false;
            list_append_pointer(&unvisited, &grid[row][col]);
        }
    }

    // The start position's distance is set to 0, meaning we will look at it first.
    start_position->min_distance = 0;

    // Do the actual algorithm.
    while (unvisited.length != 0) {
        // Get the next cell.
        // First, sort in reverse, meaning we can just pop the last item from the list.
        qsort(unvisited.data, unvisited.length, unvisited.element_size, &sort_cells);
        Cell* current_cell = list_get_pointer(&unvisited, unvisited.length - 1);
        unvisited.length--;

        // Iterate all the neighbours
        for (int d_row = -1; d_row <= 1; ++d_row) {
            for (int d_col = -1; d_col <= 1; ++d_col) {
                // We only support up, down, left and right, so no diagonal movement.
                if (abs(d_row) == abs(d_col)) {
                    continue;
                }

                long n_row = ((long) current_cell->row) + d_row;
                long n_col = ((long) current_cell->col) + d_col;

                // Ignore invalid positions.
                if (n_row < 0 || n_row >= rows || n_col < 0 || n_col >= cols) {
                    continue;
                }

                Cell* neighbour = &grid[n_row][n_col];

                // Ignore visited neighbours.
                if (neighbour->visited) {
                    continue;
                }

                // Ignore neighbours we cannot reach due to height differences.
                if (!allow_height(current_cell, neighbour)) {
                    continue;
                }

                size_t alternative_distance = current_cell->min_distance + 1;
                if (alternative_distance < neighbour->min_distance) {
                    neighbour->min_distance = alternative_distance;
                }
            }
        }
    }
}

char* day12_part1(const char* input) {
    File file = read_lines(input);
    List lines = file.lines;

    Cell* start_position;
    Cell* goal;

    long rows = (long) lines.length;
    long cols = (long) list_get_list_p(&lines, 0)->length;
    Cell grid[rows][cols];

    for (size_t row = 0; row < lines.length; ++row) {
        List line = list_get_list(&lines, row);
        for (size_t col = 0; col < line.length; ++col) {
            grid[row][col].row = row;
            grid[row][col].col = col;
            char square = list_get_char(&line, col);
            if (square == 'S') {
                start_position = &grid[row][col];
                square = 'a';
            } else if (square == 'E') {
                goal = &grid[row][col];
                square = 'z';
            }
            // Means a => 0, ...
            grid[row][col].height = square - 'a';
        }
    }

    do_pathfinding_to_top(rows, cols, grid, start_position, &allow_for_height_part1);
    return size_t_to_string(goal->min_distance);
}

char* day12_part2(const char* input) {
    File file = read_lines(input);
    List lines = file.lines;

    Cell* start_position;

    long rows = (long) lines.length;
    long cols = (long) list_get_list_p(&lines, 0)->length;
    Cell grid[rows][cols];

    List potential_start_positions = list_create_for_pointer(10);

    for (size_t row = 0; row < lines.length; ++row) {
        List line = list_get_list(&lines, row);
        for (size_t col = 0; col < line.length; ++col) {
            grid[row][col].row = row;
            grid[row][col].col = col;
            char square = list_get_char(&line, col);
            if (square == 'S') {
                square = 'a';
            } else if (square == 'E') {
                start_position = &grid[row][col];
                square = 'z';
            }

            if (square == 'a') {
                list_append_pointer(&potential_start_positions, &grid[row][col]);
            }

            // Means a => 1, ...
            grid[row][col].height = square - 'a';
        }
    }

    do_pathfinding_to_top(rows, cols, grid, start_position, &allow_for_height_part2);

    size_t current_smallest = SIZE_MAX;

    for (size_t i = 0; i < potential_start_positions.length; ++i) {
        Cell* c = list_get_pointer(&potential_start_positions, i);
        if (c->min_distance < current_smallest) {
            current_smallest = c->min_distance;
        }
    }

    return size_t_to_string(current_smallest);
}
