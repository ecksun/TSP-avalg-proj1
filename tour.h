#ifndef TOUR_HEADER
#define TOUR_HEADER

#include "tsp.h"
#include <vector>
#include <iostream>
#include <sstream>
// TODO: rensa upp includes

struct TSP; // forward declaration -- fult?

class Tour {
    std::vector<int> * nodes;

    public:

    Tour();
    ~Tour();

    void add(int);
    int get(int) const;
    float length(const TSP &) const;
    int numNodes() const;

    int & operator[](int);
};

std::ostream & operator<<(std::ostream &, const Tour &);

#endif /* TOUR_HEADER guard */
