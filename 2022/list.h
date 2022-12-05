//
// Created by niko on 3/12/22.
//

#ifndef ADVENTOFCODE_LIST_H
#define ADVENTOFCODE_LIST_H

#include <stddef.h>

typedef struct List {
    // Number of elements currently in the list.
    size_t length;
    // Capacity, i.e. the number of elements we have space for.
    size_t capacity;
    // The size of one element.
    size_t element_size;
    // The actual data.
    char *data;
} List;

List list_create(size_t initial_capacity, size_t element_size);

List list_create_string(size_t initial_capacity);

List list_create_int_list(size_t initial_capacity);

void list_append(List *list, void *element);

void list_append_char(List *list, char element);

void list_append_int(List *list, int element);

// Delete a list (but not the elements of the list itself.
void list_destroy(List *list);
void list_destroy_and_free_contents(List *list);

// Remove content, but not the memory.
void list_clear(List *list);
void list_clear_and_free_contents(List *list);

char list_get_char(const List *list, size_t index);

int list_get_int(const List *list, size_t index);

void *list_get(const List *list, size_t index);

// Get it as a null delimited list.
// You get ownership of this list.
char *as_null_delimited_string(const List *string);

// Get max value from int list.
int list_int_max(const List *list);


#endif //ADVENTOFCODE_LIST_H
