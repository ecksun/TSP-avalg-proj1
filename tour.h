#ifndef TOUR_HEADER
#define TOUR_HEADER

#include "tsp.h"
#include <vector>

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


#endif /* TOUR_HEADER guard */
