#!/bin/bash
# ./a.out < testfall/example.output 2>&1 | grep tour | awk '{ sum += $3 } END { print sum }'

java TSP < testfall/example.output 2>&1 | grep tour
java TSP < testfall/100 2>&1 | grep tour
java TSP < testfall/250 2>&1 | grep tour
java TSP < testfall/350 2>&1 | grep tour
java TSP < testfall/500 2>&1 | grep tour
java TSP < testfall/1000 2>&1 | grep tour
