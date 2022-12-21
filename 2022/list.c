//
// Created by niko on 3/12/22.
//
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <math.h>

#include "list.h"

// Create basic operations for a type on a list.
#define impl_list_of_type_and_name(type, name)               \
    list_create_header(name) {                               \
        return list_create(initial_capacity, sizeof(type));  \
    }                                                        \
                                                             \
    list_append_header(type, name) {                         \
        assert(list->element_size == sizeof(type));          \
        list_append(list, &item);                            \
    }                                                        \
                                                             \
    list_get_header(type, name) {                            \
        assert(list->element_size == sizeof(type));          \
        return *list_get_##name##_p(list, index);            \
    }                                                        \
                                                             \
    list_get_header_p(type, name) {                          \
        assert(list->element_size == sizeof(type));          \
        return (type*) list_get(list, index);                \
    }                                                        \
                                                             \
    list_pop_header(type, name) {                            \
        assert(list->element_size == sizeof(type));          \
        return *((type*) list_pop(list, index));             \
    }                                                        \
                                                             \
    list_contains_header(type, name) {                       \
        assert(list->element_size == sizeof(type));          \
        return list_contains(list, (char*) &element);        \
    }                                                        \

#define impl_list_sort(type, name)                           \
    int compare_##name##_asc(const void* a, const void* b) { \
        type arg1 = *(const type*) a;                        \
        type arg2 = *(const type*) b;                        \
                                                             \
        if (arg1 < arg2) return -1;                          \
        if (arg1 > arg2) return 1;                           \
        return 0;                                            \
    }                                                        \
                                                             \
    int compare_##name##_desc(const void* a, const void* b) {\
        type arg1 = *(const type*) a;                        \
        type arg2 = *(const type*) b;                        \
                                                             \
        if (arg1 < arg2) return 1;                           \
        if (arg1 > arg2) return -1;                          \
        return 0;                                            \
    }                                                        \
                                                             \
    list_sort_header(name) {                                 \
        qsort(list->data, list->length, list->element_size, ascending ? compare_##name##_asc : compare_##name##_desc); \
    }                                                        \

#define impl_max(type, name)                                 \
    list_max_header(type, name) {                            \
        type current_max = 0;                                \
        for (size_t i = 0; i < list->length; ++i) {          \
            type current = list_get_##name(list, i);        \
            if (current > current_max) {                     \
                current_max = current;                       \
            }                                                \
        }                                                    \
        return current_max;                                  \
    } \

#define impl_min(type, name)                                 \
    list_min_header(type, name) {                            \
        type current_min = 0;                                \
        for (size_t i = 0; i < list->length; ++i) {          \
            type current = list_get_##name(list, i);        \
            if (current < current_min) {                     \
                current_min = current;                       \
            }                                                \
        }                                                    \
        return current_min;                                  \
    } \

#define impl_list_sum(type, name)                            \
    list_sum_header(type, name) {                            \
        type sum = 0;                                        \
        for (size_t i = 0; i < list->length; ++i) {          \
            sum += list_get_##name(list, i);                \
        }                                                    \
        return sum;                                          \
    } \

#define impl_numeric(type)                 \
    impl_list_of_type_and_name(type, type) \
    impl_list_sort(type, type)             \
    impl_max(type, type)                   \
    impl_min(type, type)                   \
    impl_list_sum(type, type)              \

List list_create(size_t initial_capacity, size_t element_size) {
    void* data = malloc(initial_capacity * element_size);
    if (!data) {
        fprintf(stderr, "Could not allocate %zu elements for array...\n", initial_capacity);
        exit(EXIT_FAILURE);
    }

    List result = {
            .length = 0,
            .capacity = initial_capacity,
            .element_size = element_size,
            .data = data
    };

    return result;
}

void* list_get(const List* list, size_t index) {
    return list->data + (index * list->element_size);
}

void* list_pop(List* list, size_t index) {
    char* existing_address = list_get(list, index);
    char* raw_data = malloc(list->element_size);
    memcpy(raw_data, existing_address, list->element_size);
    // Remove the memory in the array, by moving it all forward.
    memcpy(existing_address, existing_address + list->element_size, (list->length - index - 1) * list->element_size);
    list->length--;
    return raw_data;
}

void list_append(List* list, void* raw_data_pointer) {
    assert(list->data != NULL);

    if (list->capacity == list->length) {
        // Increase capacity.
        void* increased_data = realloc(list->data, list->capacity * 2 * list->element_size);
        if (!increased_data) {
            fprintf(stderr, "Could not allocate %zu elements for array...\n", list->capacity * 2);
            exit(EXIT_FAILURE);
        }
        list->capacity = list->capacity * 2;
        list->data = increased_data;
    }

    // Insert the element in the array.
    // We copy the memory pointed to by "raw_data_pointer" into the slot we have for the raw_data_pointer in the list.
    char* destination = list_get(list, list->length);
    memcpy(destination, raw_data_pointer, list->element_size);
    list->length++;
}

void list_clear(List* list) {
    list->length = 0;
}

bool list_contains(const List* haystack, char* raw_needle_pointer) {
    for (size_t i = 0; i < haystack->length; ++i) {
        void* element = list_get(haystack, i);
        if (memcmp(element, raw_needle_pointer, haystack->element_size) == 0) {
            return true;
        }
    }
    return false;
}

List list_view(const List* list, size_t start, size_t length) {
    assert(0 <= start);
    assert(start + length <= list->length);
    assert(list->data != NULL);

    char* data = list_get(list, start);

    List result = {
            .length = length,
            .element_size = list->element_size,
            .capacity = length,
            .data = data
    };
    return result;
}

List list_intersection(const List* a, ...) {
    List result = list_create(a->length, a->element_size);

    va_list others;

    for (size_t i = 0; i < a->length; ++i) {
        char* a_element = list_get(a, i);
        bool is_in_all_others = true;

        const List* other;
        va_start(others, a);
        while ((other = va_arg(others, const List*)) && is_in_all_others) {
            is_in_all_others = list_contains(other, a_element) && is_in_all_others;
        }

        if (is_in_all_others) {
            list_append_char(&result, *a_element);
        }
    }

    return result;
}

List list_copy(const List* list) {
    List copy = list_create(list->length, list->element_size);
    copy.length = list->length;
    memcpy(copy.data, list->data, copy.length * copy.element_size);
    return copy;
}

void list_reverse(List* list) {
    // Move from both edges to the middle.

    // Temp storage for raw data.
    char* temp_storage = malloc(list->element_size);

    for (size_t i = 0; i < list->length / 2; i++) {
        // This is raw data...
        // Store the first element.
        char* first_element = list_get(list, i);
        char* second_element = list_get(list, list->length - 1 - i);
        memcpy(temp_storage, first_element, list->element_size);
        // Override the first element with the last element.
        memcpy(first_element, second_element, list->element_size);
        // Move the temp element back into the list.
        memcpy(second_element, temp_storage, list->element_size);
    }

    free(temp_storage);
}

// ======================
// Supported types
// ======================
impl_numeric(char)

impl_numeric(int)

impl_numeric(long)

impl_numeric(size_t)

impl_list_of_type_and_name(void*, pointer)

impl_list_of_type_and_name(List, list)

// ======================
// Some utility functions
// ======================


char* as_null_delimited_string(const List* string) {
    assert(string->data != NULL);
    assert(string->element_size == sizeof(char));
    char* result = malloc((string->length + 1) * sizeof(char));
    memcpy(result, string->data, string->length);
    result[string->length] = '\0';
    return result;
}

void list_merge_into(List* goal, const List* from) {
    assert(goal->element_size == from->element_size);
    // TODO: this could be done more efficiently.

    for (size_t i = 0; i < from->length; ++i) {
        list_append(goal, list_get(from, i));
    }
}
