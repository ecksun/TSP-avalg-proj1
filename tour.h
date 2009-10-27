#ifndef TOUR_HEADER
#define TOUR_HEADER

#include "tsp.h"
#include <vector>
#include <iostream>
#include <sstream>
// TODO: rensa upp includes

struct TSP; // forward declaration -- fult?

class Tour {
    std::vector<short int> * nodes;

    public:

    Tour();
    ~Tour();

    void add(short int);
    short int get(int) const;
    float length(const TSP &) const;
    int numNodes() const;

    short int & operator[](short int);
};

std::ostream & operator<<(std::ostream &, const Tour &);

#endif /* TOUR_HEADER guard */
