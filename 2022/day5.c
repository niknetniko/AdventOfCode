//
// Created by niko on 7/12/22.
//

#include <stdlib.h>
#include <assert.h>
#include <string.h>

#include "day5.h"
#include "list.h"
#include "utils.h"

typedef struct Move {
    size_t from;
    size_t to;
    size_t amount;
} Move;

typedef struct CratesAndRearrangement {
    List stack;
    List moves;
} CratesAndRearrangement;

CratesAndRearrangement parse_input(const char* input) {

    File input_file = read_lines(input);

    // We assume there are stacks as long as there is a [ in the line.
    // We also assume that there is at least one line for which this is the case.
    // We also assume every stack is the same width.
    int next_line_index = 0;
    List* current_line = list_get_list_p(&input_file.lines, next_line_index++);
    List cc = list_get_list(&input_file.lines, next_line_index);
    // There must be 3 characters remaining, as the last stack does not have a space at the end.
    size_t number_of_stacks = current_line->length / 4 + 1;
    List stacks = list_create_for_list(number_of_stacks);
    for (size_t stack = 0; stack < number_of_stacks; ++stack) {
        List st = list_create_for_char(10);
        list_append_list(&stacks, st);
    }

    while (list_contains_char(current_line, '[')) {
        assert(current_line->length % (4) == 3);
        assert(current_line->length / 4 + 1 == number_of_stacks);

        for (size_t stack = 0; stack < number_of_stacks; ++stack) {
            size_t stack_start = stack * 4;
            char stack_contents = list_get_char(current_line, stack_start + 1);
            if (stack_contents != ' ') {
                List* stack_list = list_get_list_p(&stacks, stack);
                list_append_char(stack_list, stack_contents);
            }
        }

        current_line = list_get_list_p(&input_file.lines, next_line_index++);
    }

    for (size_t i = 0; i < stacks.length; ++i) {
        List* stack = (List*) list_get_list_p(&stacks, i);
        list_reverse(stack);
    }

    // The next line is already the line with stack indices.
    // The next line is an empty line.
    next_line_index++;

    List moves = list_create(input_file.lines.length - next_line_index, sizeof(Move));

    for (size_t i = next_line_index; i < input_file.lines.length; ++i) {
        List* the_line = list_get_list_p(&input_file.lines, i);
        char* line = as_null_delimited_string(the_line);
        char* move = strtok(line, " ");
        assert(strcmp(move, "move") == 0);
        char* amount_str = strtok(NULL, " ");
        char* from = strtok(NULL, " ");
        assert(strcmp(from, "from") == 0);
        char* source_str = strtok(NULL, " ");
        char* to = strtok(NULL, " ");
        assert(strcmp(to, "to") == 0);
        char* dest_str = strtok(NULL, " ");

        Move the_move = {
                .from = strtol(source_str, NULL, 10),
                .to = strtol(dest_str, NULL, 10),
                .amount = strtol(amount_str, NULL, 10)
        };
        list_append(&moves, &the_move);
        free(line);
    }

    return (CratesAndRearrangement) {
            .stack = stacks,
            .moves = moves
    };
}

void apply_move_9000(List* stacks, Move* move) {
    for (size_t i = 0; i < move->amount; ++i) {
        List* src_stack = (List*) list_get(stacks, move->from - 1);
        List* dest_stack = (List*) list_get(stacks, move->to - 1);

        char crate = list_get_char(src_stack, src_stack->length - 1);

        // Remove it from the src.
        src_stack->length--;
        // Add it to the dest stack.
        list_append_char(dest_stack, crate);
    }
}

void apply_move_9001(List* stacks, Move* move) {
    List* src_stack = (List*) list_get(stacks, move->from - 1);
    List* dest_stack = (List*) list_get(stacks, move->to - 1);

    // We know the data is chars, so we can directly use the raw data.
    assert(src_stack->element_size == sizeof(char));
    assert(dest_stack->element_size == sizeof(char));

    // VLA :)
    char to_move[move->amount];
    memcpy(to_move, src_stack->data + (src_stack->length - move->amount), move->amount * sizeof(char));
    src_stack->length = src_stack->length - move->amount;

    // We could do this with one memcpy, but I am lazy.
    for (size_t i = 0; i < move->amount; ++i) {
        list_append_char(dest_stack, to_move[i]);
    }
}

char* get_results(const List* stacks) {
    char* result = malloc(sizeof(char) * stacks->length + 1);
    for (size_t i = 0; i < stacks->length; ++i) {
        List* stack = (List*) list_get(stacks, i);
        char top = list_get_char(stack, stack->length - 1);
        result[i] = top;
    }
    result[stacks->length] = '\0';
    return result;
}

__attribute__((unused)) char* day5_part1(const char* input) {
    CratesAndRearrangement parsed = parse_input(input);

    for (size_t i = 0; i < parsed.moves.length; ++i) {
        Move* move = (Move*) list_get(&parsed.moves, i);
        apply_move_9000(&parsed.stack, move);
    }

    char* result = get_results(&parsed.stack);
    return result;
}

__attribute__((unused)) char* day5_part2(const char* input) {
    CratesAndRearrangement parsed = parse_input(input);

    for (size_t i = 0; i < parsed.moves.length; ++i) {
        Move* move = (Move*) list_get(&parsed.moves, i);
        apply_move_9001(&parsed.stack, move);
    }

    char* result = get_results(&parsed.stack);
    return result;
}
