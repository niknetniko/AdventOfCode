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

__attribute__((unused))  char* day6_part1(const char* input) {
    File file = read_lines(input);
    assert(file.lines.length == 1);

    List* line = list_get(&file.lines, 0);

    int result = -1;
    for (size_t i = 0; i < line->length - 3; ++i) {
        // This could be faster by hardcoding the stuff, but leave it.
        List view = list_view(line, i, 4);
        size_t count = count_unique_chars(&view);
        if (count == 4) {
            result = (int) (i + 4);
        }
    }

    destroy_file(&file);

    return int_to_string(result);
}

__attribute__((unused)) char* day6_part2(const char* input) {
    return NULL;
}
