//
// Created by niko on 3/12/22.
//

#ifndef ADVENTOFCODE_LIST_H
#define ADVENTOFCODE_LIST_H

#include <stddef.h>
#include <stdbool.h>

typedef struct List {
    // Number of elements currently in the list.
    size_t length;
    // Capacity, i.e. the number of elements we have space for.
    size_t capacity;
    // The size of one element.
    size_t element_size;
    // The actual data.
    char* data;
} List;

List list_create(size_t initial_capacity, size_t element_size);

List list_create_string(size_t initial_capacity);

List list_create_int_list(size_t initial_capacity);

void list_append(List* list, void* element);

void list_append_char(List* list, char element);

void list_append_int(List* list, int element);

// Delete a list (but not the elements of the list itself.
void list_destroy(List* list);

void list_destroy_and_free_contents(List* list);

// Remove content, but not the memory.
void list_clear(List* list);

void list_clear_and_free_contents(List* list);

char list_get_char(const List* list, size_t index);

int list_get_int(const List* list, size_t index);

void* list_get(const List* list, size_t index);

// Get a slice of the list.
// Note that this list should not be freed, as the data is the same as the original list.
// TODO: disallow editing this list...
List list_view(const List* list, size_t start, size_t end);

// ======================
// Some utility functions
// ======================

void list_int_print(const List* list);

// Get it as a null delimited list.
// You get ownership of this list.
char* as_null_delimited_string(const List* string);

// Get max value from a list with ints.
int list_int_max(const List* list);

int list_int_sum(const List* list);

// Sort the list with ints in place.
void list_int_sort(List* list, bool ascending);


#endif //ADVENTOFCODE_LIST_H
