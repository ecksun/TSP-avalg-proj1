#!/bin/bash
# ./a.out < testfall/example.output 2>&1 | grep tour | awk '{ sum += $3 } END { print sum }'

./a.out < testfall/example.output 2>&1 | grep tour
./a.out < testfall/100 2>&1 | grep tour
./a.out < testfall/250 2>&1 | grep tour
./a.out < testfall/350 2>&1 | grep tour
./a.out < testfall/500 2>&1 | grep tour
./a.out < testfall/1000 2>&1 | grep tour
