//
// Created by niko on 3/12/22.
//
#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <math.h>

#include "list.h"


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

List list_create_string(size_t initial_capacity) {
    return list_create(initial_capacity, sizeof(char));
}

List list_create_int_list(size_t initial_capacity) {
    return list_create(initial_capacity, sizeof(int));
}

char* list_memory_of_index(const List* list, size_t index) {
    return list->data + (index * list->element_size);
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
    char* destination = list_memory_of_index(list, list->length);
    memcpy(destination, raw_data_pointer, list->element_size);
    list->length++;
}

void list_append_pointer(List* list, void* element) {
    assert(list->element_size == sizeof(void*));
    list_append(list, &element);
}

void list_append_char(List* list, char element) {
    assert(list->element_size == sizeof(char));
    list_append(list, &element);
}

void list_append_int(List* list, int element) {
    assert(list->element_size == sizeof(int));
    list_append(list, &element);
}

void list_destroy(List* list) {
    assert(list->data != NULL);
    free(list->data);
    list->capacity = 0;
    list->data = NULL;
    list->length = 0;
}

void list_clear(List* list) {
    list->length = 0;
}

void* list_get(const List* list, size_t index) {
    return *((void**) list_memory_of_index(list, index));
}

char list_get_char(const List* list, size_t index) {
    return *((char*) list_memory_of_index(list, index));
}

int list_get_int(const List* list, size_t index) {
    return *((int*) list_memory_of_index(list, index));
}

char* as_null_delimited_string(const List* string) {
    assert(string->data != NULL);
    assert(string->element_size == sizeof(char));
    char* result = malloc((string->length + 1) * sizeof(char));
    memcpy(result, string->data, string->length);
    result[string->length] = '\0';
    return result;
}

int list_int_max(const List* list) {
    int current_max = 0;
    for (size_t i = 0; i < list->length; ++i) {
        int current = list_get_int(list, i);
        if (current > current_max) {
            current_max = current;
        }
    }
    return current_max;
}

void list_destroy_and_free_contents(List* list) {
    assert(list->element_size == sizeof(void*));
    for (size_t i = 0; i < list->length; ++i) {
        void* element = list_get(list, i);
        free(element);
    }
    list_destroy(list);
}

void list_clear_and_free_contents(List* list) {
    assert(list->element_size == sizeof(void*));
    for (size_t i = 0; i < list->length; ++i) {
        void* element = list_get(list, i);
        free(element);
    }
    list_clear(list);
}

static int compare_ints_ascending(const void* a, const void* b) {
    int arg1 = *(const int*) a;
    int arg2 = *(const int*) b;

    if (arg1 < arg2) return -1;
    if (arg1 > arg2) return 1;
    return 0;
}

static int compare_ints_descending(const void* a, const void* b) {
    int arg1 = *(const int*) a;
    int arg2 = *(const int*) b;

    if (arg1 < arg2) return 1;
    if (arg1 > arg2) return -1;
    return 0;
}

void list_int_sort(List* list, bool ascending) {
    if (ascending) {
        qsort(list->data, list->length, list->element_size, compare_ints_ascending);
    } else {
        qsort(list->data, list->length, list->element_size, compare_ints_descending);
    }
}

List list_view(const List* list, size_t start, size_t length) {
    assert(0 <= start);
    assert(start + length <= list->length);
    assert(list->data != NULL);

    char* data = list_memory_of_index(list, start);

    List result = {
            .length = length,
            .element_size = list->element_size,
            .capacity = length,
            .data = data
    };
    return result;
}

void list_int_print(const List* list) {
    printf("Int list: {.length = %zu, .capacity = %zu, .element_size = %zu}\n", list->length, list->capacity,
           list->capacity);
    printf("[");
    for (size_t i = 0; i < list->length; ++i) {
        int value = list_get_int(list, i);
        printf("%zu: %d, ", i, value);
    }
    printf("]\n");
}

int list_int_sum(const List* list) {
    int sum = 0;
    for (size_t i = 0; i < list->length; ++i) {
        sum += list_get_int(list, i);
    }
    return sum;
}

bool list_char_contains(const List* haystack, char needle) {
    for (size_t i = 0; i < haystack->length; ++i) {
        char element = list_get_char(haystack, i);
        if (element == needle) {
            return true;
        }
    }
    return false;
}

bool list_contains(const List* haystack, char* raw_needle_pointer) {
    for (size_t i = 0; i < haystack->length; ++i) {
        void* element = list_memory_of_index(haystack, i);
        if (memcmp(element, raw_needle_pointer, haystack->element_size) == 0) {
            return true;
        }
    }
    return false;
}

List list_char_intersection(const List* a, ...) {
    List result = list_create_string(a->length);

    va_list others;

    for (size_t i = 0; i < a->length; ++i) {
        char a_element = list_get_char(a, i);
        bool is_in_all_others = true;

        const List* other;
        va_start(others, a);
        while ((other = va_arg(others, const List*)) && is_in_all_others) {
            is_in_all_others = list_char_contains(other, a_element) && is_in_all_others;
        }

        if (is_in_all_others) {
            list_append_char(&result, a_element);
        }
    }

    return result;
}

List* list_dynamic_copy(const List* list) {
    List* copy = malloc(sizeof(List));
    copy->length = list->length;
    copy->element_size = list->element_size;
    copy->capacity = list->element_size;
    copy->data = malloc(copy->length * copy->element_size);
    memcpy(copy->data, list->data, copy->length * copy->element_size);
    return copy;
}

void list_reverse(List* list) {
    // Move from both edges to the middle.

    // Temp storage for raw data.
    char* temp_storage = malloc(list->element_size);

    for (size_t i = 0; i < list->length / 2; i++) {
        // This is raw data...
        // Store the first element.
        char* first_element = list_memory_of_index(list, i);
        char* second_element = list_memory_of_index(list, list->length - 1 - i);
        memcpy(temp_storage, first_element, list->element_size);
        // Override the first element with the last element.
        memcpy(first_element, second_element, list->element_size);
        // Move the temp element back into the list.
        memcpy(second_element, temp_storage, list->element_size);
    }

    free(temp_storage);
}
