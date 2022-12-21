//
// Created by niko on 19/12/22.
//

#include "day15.h"
#include "list.h"
#include "utils.h"

#include <stdlib.h>
#include <limits.h>
#include <string.h>

typedef struct Point {
    long x;
    long y;
} Point;

typedef struct Reading {
    Point sensor_position;
    Point beacon_position;
    long distance;
} Reading;

long distance(Point a, Point b) {
    return labs(a.x - b.x) + labs(a.y - b.y);
}

const int interesting_row = 2000000;

List parse_to_reading_list(const char* input) {
    File file = read_lines(input);
    List readings = list_create(file.lines.length, sizeof(Reading));

    for (size_t i = 0; i < file.lines.length; ++i) {
        List* line_list = list_get_list_p(&file.lines, i);
        // Poor man's regex.
        List parts = split_string(as_null_delimited_string(line_list), "=");
        long sensor_x = strtol(list_get_pointer(&parts, 1), NULL, 10);
        long sensor_y = strtol(list_get_pointer(&parts, 2), NULL, 10);
        long beacon_x = strtol(list_get_pointer(&parts, 3), NULL, 10);
        long beacon_y = strtol(list_get_pointer(&parts, 4), NULL, 10);
        Reading r = {
                .sensor_position = {.x = sensor_x, .y = sensor_y},
                .beacon_position = {.x = beacon_x, .y = beacon_y},
        };
        r.distance = distance(r.sensor_position, r.beacon_position);
        list_append(&readings, &r);
    }

    return readings;
}

char* day15_part1(const char* input) {

    List readings = parse_to_reading_list(input);
    List readings_within_range = list_create(50, sizeof(Reading));
    long min_x = LONG_MAX;
    long max_x = LONG_MIN;
    for (size_t i = 0; i < readings.length; ++i) {
        Reading* reading = list_get(&readings, i);
        if (interesting_row - reading->distance <= reading->sensor_position.y &&
            reading->sensor_position.y <= interesting_row + reading->distance) {
            list_append(&readings_within_range, reading);
            long local_min_x = reading->sensor_position.x - reading->distance;
            long local_max_x = reading->sensor_position.x + reading->distance;
            if (local_min_x < min_x) {
                min_x = local_min_x;
            }
            if (local_max_x > max_x) {
                max_x = local_max_x;
            }
        }
    }

    List list_of_interesting_points = list_create(1000, sizeof(Point));
    bool reserved[max_x - min_x + 1];
    for (int i = 0; i < max_x - min_x + 1; ++i) {
        reserved[i] = false;
    }
    for (size_t i = 0; i < readings_within_range.length; ++i) {
        Reading* reading = list_get(&readings_within_range, i);
        if (reading->sensor_position.y == interesting_row) {
            reserved[reading->sensor_position.x - min_x] = true;
        } else if (reading->beacon_position.y == interesting_row) {
            reserved[reading->beacon_position.x - min_x] = true;
        }
    }

    // For each point on the line, check the distance to the reader.
    for (long x = min_x; x <= max_x; ++x) {
        Point p = {.x = x, .y = interesting_row};
        for (size_t i = 0; i < readings_within_range.length; ++i) {
            Reading* reading = list_get(&readings_within_range, i);
            long d = distance(p, reading->sensor_position);
            if (d <= reading->distance && !reserved[x - min_x]) {
                list_append(&list_of_interesting_points, &p);
                break;
            }
        }
    }

    return size_t_to_string(list_of_interesting_points.length);
}

const long min_pos = 0;
const long max_pos = 4000000;


bool is_in_radius(const List* readings, Point point) {
    for (size_t i = 0; i < readings->length; ++i) {
        Reading* reading = list_get(readings, i);
        long d = distance(reading->sensor_position, point);
        if (d <= reading->distance || point.x < min_pos || point.x > max_pos || point.y < min_pos ||
            point.y > max_pos) {
            return true;
        }
    }
    return false;
}


char* day15_part2(const char* input) {
    List readings = parse_to_reading_list(input);
    List readings_within_range = list_create(50, sizeof(Reading));
    for (size_t i = 0; i < readings.length; ++i) {
        Reading* reading = list_get(&readings, i);
        // The y position must be within distance of the min.
        bool from_top = reading->sensor_position.y > min_pos - reading->distance;
        bool from_bottom = reading->sensor_position.y < max_pos + reading->distance;
        bool from_right = reading->sensor_position.x < max_pos + reading->distance;
        bool from_left = reading->sensor_position.x > min_pos - reading->distance;
        if (from_top || from_bottom || from_left || from_right) {
            list_append(&readings_within_range, reading);
        }
    }

    Point result;
    bool found = false;
    for (size_t i = 0; i < readings_within_range.length && !found; ++i) {
        Reading* reading = list_get(&readings_within_range, i);

        // Iterate the "border" of the "circle" with the manhattan distance.
        // This is actually a diamond shape and we switch directions when an axis becomes 0.
        // We start at the top and move clockwise.
        long delta_x = 1;
        long delta_y = 1;
        Point next_point_on_border = {.x = reading->sensor_position.x, .y = reading->sensor_position.y -
                                                                            reading->distance};

        bool is_full_circle = false;
        while (!is_full_circle && !found) {
            // Check if the squares surrounding this are in range.
            // If not, we found it.
            // We could be smarter and only check the outside squares, but I am lazy.
            int x_delta[] = {0, 1, 0, -1};
            int y_delta[] = {-1, 0, 1, 0};

            for (int j = 0; j < 4; ++j) {
                Point neighbour = {.x = next_point_on_border.x + x_delta[j], .y = next_point_on_border.y + y_delta[j]};
                if (!is_in_radius(&readings_within_range, neighbour)) {
                    result = neighbour;
                    found = true;
                    break;
                }
            }

            next_point_on_border = (Point) {.x = next_point_on_border.x + delta_x, next_point_on_border.y + delta_y};

            if (next_point_on_border.x == reading->sensor_position.x &&
                (next_point_on_border.y - reading->distance == reading->sensor_position.y ||
                 next_point_on_border.y + reading->distance == reading->sensor_position.y)) {
                delta_y = delta_y * -1;
            }
            if (next_point_on_border.y == reading->sensor_position.y &&
                (next_point_on_border.x - reading->distance == reading->sensor_position.x ||
                 next_point_on_border.x + reading->distance == reading->sensor_position.x)) {
                delta_x = delta_x * -1;
            }

            if (next_point_on_border.x == reading->sensor_position.x &&
                next_point_on_border.y == reading->sensor_position.y - reading->distance) {
                is_full_circle = true;
            }
        }
    }

    assert(found);

    long final = result.x * 4000000 + result.y;
    return long_to_string(final);
}
