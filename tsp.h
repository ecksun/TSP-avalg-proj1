#ifndef TSP_HEADER
#define TSP_HEADER
#include "node.h"
#include "tour.h"
#include <map>
#include <vector>

struct TSP {
    int const numNodes;
    static const unsigned int neighborsToCheck = 100; // Perhaps pre-processing konstant (#define) ?

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

    void greedyPath();
    void oneToN();

    void twoOpt();

    float distance(int, int);
};
#endif
