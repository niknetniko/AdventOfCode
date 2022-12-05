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

static char* memory_of_index(const List* list, size_t index) {
    return list->data + (index * list->element_size);
}

// The data is a pointer to raw memory.
static void append(List* list, void* data) {
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
    // We copy the memory pointed to by "data" into the slot we have for the data in the list.
    char* destination = memory_of_index(list, list->length);
    memcpy(destination, data, list->element_size);
    list->length++;
}

void list_append(List* list, void* element) {
    assert(list->data != NULL);
    assert(list->element_size == sizeof(void*));
    append(list, &element);
}

void list_append_char(List* list, char element) {
    assert(list->data != NULL);
    assert(list->element_size == sizeof(char));
    append(list, &element);
}

void list_append_int(List* list, int element) {
    assert(list->data != NULL);
    assert(list->element_size == sizeof(int));
    append(list, &element);
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
    return *((void**) memory_of_index(list, index));
}

char list_get_char(const List* list, size_t index) {
    return *((char*) memory_of_index(list, index));
}

int list_get_int(const List* list, size_t index) {
    return *((int*) memory_of_index(list, index));
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
    assert(start + length < list->length);
    assert(list->data != NULL);

    char* data = memory_of_index(list, start);

    List result = {
            .length = length,
            .element_size = list->element_size,
            .capacity = length,
            .data = data
    };
    return result;
}

void list_int_print(const List* list) {
    printf("Int list: {.length = %zu, .capacity = %zu, .element_size = %zu}\n", list->length, list->capacity, list->capacity);
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

