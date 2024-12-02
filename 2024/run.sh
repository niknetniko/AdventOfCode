#!/usr/bin/env bash

set -e

if [ -z "$1" ]; then
  echo "Error: Please provide the Fortran file name as an argument."
  exit 1
fi

source_file="$1"

executable_name=$(basename "$source_file" .f90)

mkdir -p ".build"

gfortran -std=f2018 -Wall -J .build/ -c io.f90 -o .build/io.o
gfortran -std=f2018 -J .build/ -c strings.f90 -o .build/strings.o
gfortran -std=f2018 -Wall -Wno-uninitialized -J .build/ -o ".build/$executable_name.o" -c "$source_file"
gfortran .build/io.o .build/strings.o ".build/$executable_name.o" -o ".build/$executable_name"

cd .build || exit
./"$executable_name"
cd ..
