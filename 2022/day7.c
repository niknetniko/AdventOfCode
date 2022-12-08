#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-pragmas"
#pragma ide diagnostic ignored "misc-no-recursion"
//
// Created by niko on 8/12/22.
//

#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "day7.h"
#include "list.h"
#include "utils.h"

typedef enum Type {
    DIRECTORY, DATA
} Type;

typedef struct FsNode {
    Type type;
    char* name;
    struct FsNode* parent;
    union {
        size_t size;
        List children;
    };
    size_t total_size;
} FsNode;


void calculate_sizes(FsNode* directory) {
    if (directory->type == DATA) {
        directory->total_size = directory->size;
        return;
    }

    directory->total_size = 0;
    for (size_t i = 0; i < directory->children.length; ++i) {
        // Fill in the child first.
        FsNode* child = list_get(&directory->children, i);
        calculate_sizes(child);
        directory->total_size += child->total_size;
    }
}

void free_fs_tree(FsNode* directory) {
    if (directory->type == DATA) {
        return;
    }

    for (size_t i = 0; i < directory->children.length; ++i) {
        // Fill in the child first.
        FsNode* child = list_get(&directory->children, i);
        free_fs_tree(child);
        free(child);
    }
    list_destroy(&directory->children);
}


FsNode* parse_filesystem(const char* input) {
    // First converting the whole file to lines, just to reconvert them to strings is very inefficient.
    // But who cares?
    File file = read_lines(input);

    FsNode* root = NULL;
    FsNode* cwd = NULL;

    for (size_t i = 0; i < file.lines.length; ++i) {
        // TODO: fix this memory leak, by copying the string...
        char* line = as_null_delimited_string(list_get(&file.lines, i));
        assert(cwd == NULL || cwd->type == DIRECTORY);

        // Handle a command
        if (line[0] == '$') {
            if (strcmp(line, "$ ls") == 0) {
                assert(cwd != NULL);
                // We do nothing here.
            } else {
                // We skip '$ cd '
                char* name = line + 5;
                if (strcmp(name, "/") == 0) {
                    if (root == NULL) {
                        assert(cwd == NULL);
                        root = malloc(sizeof(FsNode));
                        root->type = DIRECTORY;
                        root->name = name;
                        root->parent = NULL;
                        root->children = list_create(10, sizeof(FsNode*));
                    }
                    cwd = root;
                } else if (strcmp(name, "..") == 0) {
                    assert(cwd != NULL);
                    assert(cwd->parent != NULL);
                    cwd = cwd->parent;
                } else {
                    bool was_added = false;
                    for (size_t j = 0; j < cwd->children.length; ++j) {
                        FsNode* child = list_get(&cwd->children, j);
                        if (strcmp(child->name, name) == 0 && child->type == DIRECTORY) {
                            cwd = child;
                            was_added = true;
                            break;
                        }
                    }
                    assert(was_added);
                }
            }
        } else {
            // We are reading directories
            char* first = strtok(line, " ");
            char* second = strtok(NULL, " ");

            if (strcmp(first, "dir") == 0) {
                assert(cwd->type == DIRECTORY);
                // Check if the directory already has the child in question.
                bool was_added_already = false;
                for (size_t j = 0; j < cwd->children.length; ++j) {
                    FsNode* child = list_get(&cwd->children, j);
                    was_added_already = (strcmp(child->name, second) == 0 && child->type == DIRECTORY) || was_added_already;
                }
                if (!was_added_already) {
                    FsNode* new_directory = malloc(sizeof(FsNode));
                    new_directory->type = DIRECTORY;
                    new_directory->name = second;
                    new_directory->parent = cwd;
                    new_directory->children = list_create(10, sizeof(FsNode*));
                    list_append_pointer(&cwd->children, new_directory);
                }
            } else {
                // It is a file.
                bool was_added_already = false;
                for (size_t j = 0; j < cwd->children.length; ++j) {
                    FsNode* child = list_get(&cwd->children, j);
                    was_added_already = (strcmp(child->name, second) == 0 && child->type == DATA) || was_added_already;
                }
                if (!was_added_already) {
                    FsNode* new_directory = malloc(sizeof(FsNode));
                    new_directory->type = DATA;
                    new_directory->name = second;
                    new_directory->parent = cwd;
                    new_directory->size = (size_t) strtol(first, NULL, 10);
                    list_append_pointer(&cwd->children, new_directory);
                }
            }
        }
    }

    destroy_file(&file);
    assert(root != NULL);

    calculate_sizes(root);
    return root;
}

size_t sum_part1(FsNode* directory) {
    if (directory->type == DATA) {
        return 0;
    }

    // We count ourselves.
    size_t sum = 0;
    if (directory->total_size < 100000) {
        sum += directory->total_size;
    }

    // Allow children to be counted.
    for (size_t i = 0; i < directory->children.length; ++i) {
        // Fill in the child first.
        FsNode* child = list_get(&directory->children, i);
        size_t child_sum = sum_part1(child);
        sum += child_sum;
    }

    return sum;
}

__attribute__((unused)) char* day7_part1(const char* input) {
    FsNode* root = parse_filesystem(input);
    size_t result = sum_part1(root);
    free_fs_tree(root);
    free(root);
    return size_t_to_string(result);
}

FsNode* find_part2(FsNode* node, size_t at_least_size) {
    if (node->type == DATA) {
        return NULL;
    }
    // If we are too small, we can ignore any children.
    if (node->total_size < at_least_size) {
        return NULL;
    }

    FsNode* smallest_node = node;
    // If we are big enough, we could be the one, but ask the children just in case.
    for (size_t i = 0; i < node->children.length; ++i) {
        FsNode* child = list_get(&node->children, i);
        FsNode* to_delete = find_part2(child, at_least_size);
        if (to_delete != NULL && to_delete->total_size < smallest_node->total_size) {
            smallest_node = to_delete;
        }
    }

    return smallest_node;
}

__attribute__((unused)) char* day7_part2(const char* input) {
    FsNode* root = parse_filesystem(input);
    size_t free_memory = 70000000 - root->total_size;
    size_t to_delete_size = 30000000 - free_memory;

    FsNode* to_delete = find_part2(root, to_delete_size);
    size_t result = to_delete->total_size; // Rescue value from free
    free_fs_tree(root);
    free(root);
    return size_t_to_string(result);
}

#pragma clang diagnostic pop