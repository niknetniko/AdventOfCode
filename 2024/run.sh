#!/usr/bin/env bash

set -e

if [ -z "$1" ]; then
  echo "Error: Please provide the Fortran file name as an argument."
  exit 1
fi

source_file="$1"

executable_name=$(basename "$source_file" .f90)

mkdir -p ".build"

gfortran -o .build/"$executable_name" "$source_file"

cd .build || exit
./"$executable_name"
cd ..
