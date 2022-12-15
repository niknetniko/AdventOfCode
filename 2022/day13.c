#pragma clang diagnostic push
#pragma ide diagnostic ignored "misc-no-recursion"
//
// Created by niko on 15/12/22.
//

#include <stdlib.h>
#include "day13.h"
#include "list.h"
#include "utils.h"

typedef enum Type {
    LIST, INT
} Type;

typedef struct Element {
    Type type;
    union {
        int number;
        List children;
    };
} Element;


void print_element(const Element* element) {
    if (element->type == INT) {
        printf("%d, ", element->number);
    } else {
        printf("[");
        for (size_t i = 0; i < element->children.length; ++i) {
            Element* e = (Element*) list_get(&element->children, i);
            print_element(e);
        }
        printf("],");
    }
}

typedef enum Decision {
    ORDERED, UNORDERED, CONTINUE
} Decision;

Decision is_correct(const Element* a, const Element* b) {
    if (a->type == INT && b->type == INT) {
        if (a->number < b->number) {
            return ORDERED;
        } else if (a->number > b->number) {
            return UNORDERED;
        } else {
            assert(a->number == b->number);
            return CONTINUE;
        }
    }

    List a_children;
    List b_children;
    if (a->type == INT) {
        assert(b->type == LIST);
        a_children = list_create(1, sizeof(Element));
        Element a_copy = {
                .type = INT,
                .number = a->number
        };
        list_append(&a_children, &a_copy);
    } else {
        a_children = a->children;
    }
    if (b->type == INT) {
        assert(a->type == LIST);
        b_children = list_create(1, sizeof(Element));
        Element b_copy = {
                .type = INT,
                .number = b->number
        };
        list_append(&b_children, &b_copy);
    } else {
        b_children = b->children;
    }

    size_t max = a_children.length;
    if (a_children.length < b_children.length) {
        max = b_children.length;
    }

    for (size_t i = 0; i < max; ++i) {
        // If we have run out of items in one of the lists
        if (i == a_children.length || i == b_children.length) {
            if (a_children.length < b_children.length) {
                return ORDERED;
            } else if (a_children.length > b_children.length) {
                return UNORDERED;
            } else {
                assert(a_children.length == b_children.length);
                return CONTINUE;
            }
        }

        Element* c_a = list_get(&a_children, i);
        Element* c_b = list_get(&b_children, i);
        Decision el = is_correct(c_a, c_b);
        if (el != CONTINUE) {
            return el;
        }
    }

    return CONTINUE;
}

Element parse_line(const List* line) {

    // We are always working in the last list in the stack.
    List content_stack = list_create_for_list(10);

    // If we encounter numbers, they must be collected until the next comma.
    List current_number = list_create_for_char(2);
    for (size_t i = 0; i < line->length; ++i) {
        char c = list_get_char(line, i);

        if (c == '[') {
            // Start a new sublist.
            List current = list_create(10, sizeof(Element));
            list_append_list(&content_stack, current);
        } else if (c == ']') {
            List current = list_pop_list(&content_stack, content_stack.length - 1);
            // There can be an open number.
            if (current_number.length > 0) {
                int number = (int) strtol(as_null_delimited_string(&current_number), NULL, 10);
                Element e = {.type = INT, .number = number};
                list_append(&current, &e);
                list_clear(&current_number);
            }
            // Close the current stack, by adding it to the previous element.
            // If there is no previous element, we are done.
            Element e = { .type = LIST, .children = current };
            if (content_stack.length > 0) {
                List* parent = list_get_list_p(&content_stack, content_stack.length - 1);
                list_append(parent, &e);
            } else {
                return e;
            }
        } else if (c == ',') {
            // Either we closed a list previously, or we have a number that is completed.
            if (current_number.length > 0) {
                int number = (int) strtol(as_null_delimited_string(&current_number), NULL, 10);
                List* parent = list_get_list_p(&content_stack, content_stack.length - 1);
                Element e = {.type = INT, .number = number};
                list_append(parent, &e);
                list_clear(&current_number);
            } else {
                // There is nothing to do here...
                continue;
            }
        } else {
            assert('0' <= c && '0' <= '9');
            list_append_char(&current_number, c);
        }
    }

    // Impossible, this means the input is not valid.
    assert(false);
}

List read_input(const char* input) {
    File file = read_lines(input);
    List result = list_create(100, sizeof(Element));
    for (size_t i = 0; i < file.lines.length; ++i) {
        List line = list_get_list(&file.lines, i);

        if (line.length == 0) {
            // Newline, skip.
            continue;
        } else {
            Element e = parse_line(&line);
            list_append(&result, &e);
        }
    }

    return result;
}

char* day13_part1(const char* input) {
    List result = read_input(input);

    size_t index_sum = 0;

    for (size_t i = 0; i < result.length; i = i + 2) {
        Element* a = (Element*) list_get(&result, i);
        Element* b = (Element*) list_get(&result, i + 1);
//        printf("Comparing pair %zu...\n", (i / 2) + 1);
//        print_element(a);
//        printf("\n");
//        print_element(b);
//        printf("\n");

        if (is_correct(a, b) == ORDERED) {
            index_sum += (i / 2) + 1;
        }

    }

    return size_t_to_string(index_sum);
}

int sort_elements(const void* a, const void* b) {
    const Element* el_a = a;
    const Element* el_b = b;

    Decision decision = is_correct(el_a, el_b);
    if (decision == ORDERED) {
        return -1;
    } else {
        return 1;
    }
}

Element create_divider(int value) {
    Element divider = {
            .type = LIST,
            .children = list_create(1, sizeof(Element))
    };
    Element divider_ = {
            .type = LIST,
            .children = list_create(1, sizeof(Element))
    };
    Element divider__ = {
            .type = INT,
            .number = value
    };
    list_append(&divider_.children, &divider__);
    list_append(&divider.children, &divider_);
    return divider;
}

bool is_divider(const Element* el, int number) {
    if (el->type != LIST || el->children.length != 1) {
        return false;
    }
    Element* child_1 = list_get(&el->children, 0);
    if (child_1->type != LIST || child_1->children.length != 1) {
        return false;
    }

    Element* child_2 = list_get(&child_1->children, 0);
    return child_2->type == INT && child_2->number == number;
}

char* day13_part2(const char* input) {
    List result = read_input(input);

    Element divider_2 = create_divider(2);
    Element divider_6 = create_divider(6);

    list_append(&result, &divider_2);
    list_append(&result, &divider_6);

    qsort(result.data, result.length, result.element_size, &sort_elements);

    size_t product = 1;
    for (size_t i = 0; i < result.length; ++i) {
        Element* a = (Element*) list_get(&result, i);
        if (is_divider(a, 2) || is_divider(a, 6)) {
            product *= (i + 1);
        }

    }

    return size_t_to_string(product);
}

#pragma clang diagnostic pop