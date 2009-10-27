#include "tour.h"
#include <iostream>

Tour::Tour() {
    nodes = new std::vector<int>();
}

Tour::~Tour() {
    delete nodes;
}

/**
 * Adds the specified node index at the end of this tour, after its
 * current last node.
 */
void Tour::add(int i) {
    nodes->push_back(i);
}

/**
 * Returns the node at index i of this tour.
 */
int Tour::get(int i) const {
    return (*nodes)[i];
}

/**
 * Calculates the length of this tour using a function pointer to the
 * distance function in TSP.
 */
float Tour::length(const TSP & tsp) const {
    float sum = 0;

    for (int i = 1; i < nodes->size(); i++) {
        sum += tsp.distance((*nodes)[i-1], (*nodes)[i]);
    }

    sum += tsp.distance((*nodes)[0], (*nodes)[nodes->size()-1]);
    return sum;
}

/**
 * Returns the number of nodes in this tour.
 */
int Tour::numNodes() const {
    return nodes->size();
}

/**
 * Overloads the operator[] with the containing vector operator[].
 *
 * TODO: is this working?
 */
int & Tour::operator[](int i) {
    return (*nodes)[i];
}

/**
 * Prints a string representation of this tour to the given ostream.
 */
std::ostream & operator<<(std::ostream & os, const Tour & t) {
    for (int i = 0; i < t.numNodes(); i++) {
         os<< t.get(i) << std::endl;
    }
    
    return os;
}

