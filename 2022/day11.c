//
// Created by niko on 11/12/22.
//
#include <stdlib.h>
#include <assert.h>
#include <string.h>

#include "day11.h"
#include "list.h"
#include "utils.h"

typedef struct Item {
    long worry_level;
} Item;


typedef struct Monkey {
    List items;
    char* new_worry_calculator;
    long test_divider;
    long if_worried_throw_to;
    long if_not_worried_throw_to;
    // Counters for the results.
    size_t inspected_objects;
} Monkey;

// 6 data lines and a blank line.
const int lines_per_monkey = 7;

// TODO: this is horribly slow!
long calculate_new_worry(const char* operation, long old_value) {
    size_t length = strlen(operation);
    char* copy = malloc(sizeof(char) * (length + 1));
    memcpy(copy, operation, length);
    copy[length] = '\0';

    char* slot_one_str = strtok(copy, " ");
    char* op_str = strtok(NULL, " ");
    char* slot_two_str = strtok(NULL, " ");

    long slot_one;
    long slot_two;

    if (strcmp(slot_one_str, "old") == 0) {
        slot_one = old_value;
    } else {
        slot_one = strtol(slot_one_str, NULL, 10);
    }

    if (strcmp(slot_two_str, "old") == 0) {
        slot_two = old_value;
    } else {
        slot_two = strtol(slot_two_str, NULL, 10);
    }

    if (op_str[0] == '*') {
        return slot_one * slot_two;
    } else {
        assert(op_str[0] == '+');
        return slot_one + slot_two;
    }
}

void do_monkey_in_round(const List* monkeys, size_t the_monkey_to_do, long divider, long modulus) {
    Monkey* monkey = (Monkey*) list_get(monkeys, the_monkey_to_do);

    while (monkey->items.length != 0) {
        Item item = *((Item*) list_pop(&monkey->items, 0));
        long new_worry = calculate_new_worry(monkey->new_worry_calculator, item.worry_level);
        item.worry_level = (new_worry / divider) % modulus;

        size_t receiver_index;
        if (item.worry_level % monkey->test_divider == 0) {
            receiver_index = monkey->if_worried_throw_to;
        } else {
            receiver_index = monkey->if_not_worried_throw_to;
        }
        Monkey* receiver = (Monkey*) list_get(monkeys, receiver_index);
        monkey->inspected_objects++;
        list_append(&receiver->items, &item);
    }
}

List parse_monkeys(const char* input) {
    File file = read_lines(input);
    List list_of_monkeys = list_create(10, sizeof(Monkey));

    // We assume the monkeys are in order...
    for (size_t i = 0; i < file.lines.length; i += lines_per_monkey) {

        List* monkey_number = list_get_list_p(&file.lines, i);
        List* starting_items = list_get_list_p(&file.lines, i + 1);
        List* operation = list_get_list_p(&file.lines, i + 2);
        List* test = list_get_list_p(&file.lines, i + 3);
        List* if_true = list_get_list_p(&file.lines, i + 4);
        List* if_false = list_get_list_p(&file.lines, i + 5);


        List actual_number = list_view(monkey_number, 7, (monkey_number->length - 7 - 1));
        long actual_number_value = strtol(as_null_delimited_string(&actual_number), NULL, 10);
        assert(actual_number_value == (long) i / lines_per_monkey);

        List actual_starting_list = list_view(starting_items, 17, starting_items->length - 17);
        char* actual_starting_list_str = as_null_delimited_string(&actual_starting_list);
        List item_list = list_create(5, sizeof(Item));
        char* part;
        part = strtok(actual_starting_list_str, ",");
        while (part != NULL) {
            assert(part[0] == ' ');
            Item item = {
                    .worry_level = strtol(part + 1, NULL, 10)
            };
            list_append(&item_list, &item);
            part = strtok(NULL, ",");
        }

        char* new_worry_operation = as_null_delimited_string(operation) + 19;
        char* divider_number = as_null_delimited_string(test) + 21;
        char* if_true_number = as_null_delimited_string(if_true) + 29;
        char* if_false_number = as_null_delimited_string(if_false) + 30;

        Monkey current_monkey = {
                .items = item_list,
                .inspected_objects = 0,
                .new_worry_calculator = new_worry_operation,
                .test_divider = strtol(divider_number, NULL, 10),
                .if_worried_throw_to = strtol(if_true_number, NULL, 10),
                .if_not_worried_throw_to = strtol(if_false_number, NULL, 10)
        };

        list_append(&list_of_monkeys, &current_monkey);
    }

    return list_of_monkeys;
}

char* get_number_of_inspections(const List* monkeys) {
    List number_of_inspections = list_create(monkeys->length, sizeof(size_t));

    for (size_t m = 0; m < monkeys->length; ++m) {
        Monkey* monkey = (Monkey*) list_get(monkeys, m);
        list_append(&number_of_inspections, &monkey->inspected_objects);
    }

    list_sort_size_t(&number_of_inspections, false);
    size_t result = list_get_size_t(&number_of_inspections, 0) * list_get_size_t(&number_of_inspections, 1);
    return size_t_to_string(result);
}

__attribute__((unused)) char* day11_part1(const char* input) {
    List list_of_monkeys = parse_monkeys(input);
    // Do the rounds 20 times
    for (int r = 0; r < 20; r++) {
        for (size_t m = 0; m < list_of_monkeys.length; ++m) {
            do_monkey_in_round(&list_of_monkeys, m, 3, 1);
        }
    }

    return get_number_of_inspections(&list_of_monkeys);
}

__attribute__((unused)) char* day11_part2(const char* input) {
    List list_of_monkeys = parse_monkeys(input);

    // To make the numbers smaller while not affecting the logic, we need to make sure that all
    // of the tests still pass, ie. the number can be modulus the lcm of all "divide by" tests.
    // Thanks to a hint on Reddit, it becomes obvious all "divide by" tests are prime,
    // so the lcm is just the product.
    long lcm = 1;
    for (size_t m = 0; m < list_of_monkeys.length; ++m) {
        Monkey* monkey = (Monkey*) list_get(&list_of_monkeys, m);
        lcm = lcm * monkey->test_divider;
    }

    for (int r = 0; r < 10000; r++) {
        for (size_t m = 0; m < list_of_monkeys.length; ++m) {
            do_monkey_in_round(&list_of_monkeys, m, 1, lcm);
        }
    }

    return get_number_of_inspections(&list_of_monkeys);
}
