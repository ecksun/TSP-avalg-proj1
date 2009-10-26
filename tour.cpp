#include "tour.h"

Tour::Tour() {
    nodes = new std::vector<short int>();
}

Tour::~Tour() {
    delete nodes;
}

/**
 * Adds the specified node index at the end of this tour, after its
 * current last node.
 */
void add(short int i) {
    nodes.push_back(i);
}

/**
 * Calculates the length of this tour using a function pointer to the
 * distance function in TSP.
 */
float Tour::length(float (*distPtr)(int a, int b)) const {
    float sum = 0;
    std::vector<short int>::const_iterator it;

    for (it = nodes.begin(); it != nodes.end(); it++) {
        sum += (*distPtr)(tour[i-1], tour[i]);
    }

    sum += (*distPtr)(tour[0], tour[numNodes-1]);
    return sum;
}

/**
 * Overloads the operator[] with the containing vector operator[].
 *
 * TODO: is this working?
 */
short int & operator[](short int i) {
    return vector[i];
}

/**
 * Prints a string representation of this tour to the given ostream.
 */
std::ostream & operator<<(std::ostream & os, const Tour &) {
    std::vector<short int>::const_iterator tourIt;

    for (tourIt = nodes.begin(); tourIt != nodes.end(); tourIt++) {
        std::cout << *tourIt;
    }
    
    return os;
}

