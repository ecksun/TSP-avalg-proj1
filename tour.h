#ifndef TOUR_HEADER
#define TOUR_HEADER

#include "tsp.h"
#include <vector>
#include <iostream>
#include <sstream>
// TODO: rensa upp includes

struct TSP; // forward declaration -- fult?

class Tour {
    std::vector<short unsigned int> * nodes;

    public:

    Tour();
    ~Tour();

    void add(short unsigned int);
    short unsigned int get(short unsigned int) const;
    float length(const TSP &) const;
    int numNodes() const;

    short unsigned int & operator[](short unsigned int);
};

std::ostream & operator<<(std::ostream &, const Tour &);

#endif /* TOUR_HEADER guard */
