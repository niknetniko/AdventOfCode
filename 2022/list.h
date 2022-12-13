//
// Created by niko on 3/12/22.
//

#ifndef ADVENTOFCODE_LIST_H
#define ADVENTOFCODE_LIST_H

#define list_create_header(name) List list_create_for_##name(size_t initial_capacity)
#define list_append_header(type, name) void list_append_##name(List* list, type item)
#define list_get_header(type, name) type list_get_##name(const List* list, size_t index)
#define list_get_header_p(type, name) type* list_get_##name##_p(const List* list, size_t index)
#define list_pop_header(type, name) type list_pop_##name(List* list, size_t index)
#define list_contains_header(type, name) bool list_contains_##name(const List* list, type element)

#define list_headers_for(type, name)   \
    list_create_header(name);          \
    list_append_header(type, name);    \
    list_get_header(type, name);       \
    list_get_header_p(type, name);     \
    list_pop_header(type, name);       \
    list_contains_header(type, name);  \

#define list_sort_header(name) void list_sort_##name(List* list, bool ascending)
#define list_max_header(type, name) type list_max_##name(const List* list)
#define list_min_header(type, name) type list_min_##name(const List* list)
#define list_sum_header(type, name) type list_sum_##name(const List* list)

#define numeric_list(type)       \
    list_headers_for(type, type) \
    list_sort_header(type);      \
    list_min_header(type, type); \
    list_max_header(type, type); \
    list_sum_header(type, type); \

#include <assert.h>
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
void list_append(List* list, void* raw_data_pointer);
void list_clear(List* list);

char* list_get(const List* list, size_t index);
char* list_pop(List* list, size_t index);

// TODO: disallow editing this list...
List list_view(const List* list, size_t start, size_t length);

List list_copy(const List* list);

void list_reverse(List* list);

List list_intersection(const List* a, ...);

bool list_contains(const List* haystack, char* raw_needle_pointer);

// ======================
// Supported types
// ======================
numeric_list(char)

numeric_list(int)

numeric_list(size_t)

numeric_list(long)

list_headers_for(void*, pointer)

list_headers_for(List, list)

// ======================
// Some utility functions
// ======================

// Get it as a null delimited list.
// You get ownership of this list.
char* as_null_delimited_string(const List* string);

#endif //ADVENTOFCODE_LIST_H
