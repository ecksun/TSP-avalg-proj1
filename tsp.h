#ifndef TSP_HEADER
#define TSP_HEADER
#include "node.h"
#include "tour.h"
#include <map>
#include <vector>

class Tour; // forward declaration -- fult?

struct TSP {
    int const numNodes;
    static const unsigned int neighborsToCheck = 100; // Perhaps pre-processing konstant (#define) ?

    short int * pos[];

    std::vector<node *> nodes;

    Tour * tour;

    std::vector<std::multimap<float, short int> *> neighbors;

    public:

    TSP(int nodes);
    ~TSP();

    void init();
    
    void printTour();
    float tourLength();

    void createNeighbors();
    void printNeighbors();

    void createPos();

    void greedyPath();
    void oneToN();

    void twoOpt();

    float distance(int, int) const;
};
#endif
