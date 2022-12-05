//
// Created by niko on 5/12/22.
//

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "utils.h"

char* int_to_string(int number) {
    char *result = malloc((int) ((ceil(log10(number)) + 1) * sizeof(char)));
    snprintf(result, (int) (ceil(log10(number)) + 1), "%d", number);
    return result;
}
