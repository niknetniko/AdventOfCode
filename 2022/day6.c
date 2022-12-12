//
// Created by niko on 7/12/22.
//
#include <stdlib.h>
#include <assert.h>

#include "day6.h"
#include "list.h"
#include "utils.h"

size_t count_unique_chars(const List* list) {
    size_t count = 0;
    bool encountered[26] = {false};
    for (size_t i = 0; i < list->length; ++i) {
        char c = list_get_char(list, i);
        assert('a' <= c && c <= 'z');
        if (!encountered[c - 'a']) {
            count++;
        }
        encountered[c - 'a'] = true;
    }

    return count;
}

int calculate(const List* line, size_t required_unique_characters) {
    for (size_t i = 0; i < line->length - required_unique_characters + 1; ++i) {
        List view = list_view(line, i, required_unique_characters);
        size_t count = count_unique_chars(&view);
        if (count == required_unique_characters) {
            return (int) (i + required_unique_characters);
        }
    }

    // No value was found...
    assert(false);
}

__attribute__((unused))  char* day6_part1(const char* input) {
    File file = read_lines(input);
    assert(file.lines.length == 1);

    List* line = list_get_pointer(&file.lines, 0);
    int result = calculate(line, 4);
    destroy_file(&file);

    return int_to_string(result);
}

__attribute__((unused)) char* day6_part2(const char* input) {
    File file = read_lines(input);
    assert(file.lines.length == 1);

    List* line = list_get_pointer(&file.lines, 0);
    int result = calculate(line, 14);
    destroy_file(&file);

    return int_to_string(result);
}
