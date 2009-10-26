#ifndef TSP_HEADER
#define TSP_HEADER
#include <map>
#include <vector>
#include "node.h"

struct TSP {
    int const numNodes;
    static const unsigned int neighborsToCheck = 100; // Perhaps pre-processing konstant (#define) ?


    std::vector<node *> nodes;

    std::vector<short int> tour;

    std::vector<std::multimap<float, short int> *> neighbors;

    TSP(int nodes) : numNodes(nodes) {}

    void init();
    
    void printTour();
    float tourLength();

    void createNeighbors();
    void printNeighbors();

    void greedyPath();
    void oneToN();

    float distance(int, int);
};
#endif
