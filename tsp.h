#ifndef TSP_HEADER
#define TSP_HEADER
#include <map>
#include <vector>
#include "node.h"

struct TSP {
    int const numNodes;
    static const unsigned int neighborsToCheck = 100; // Perhaps pre-processing konstant (#define) ?


    std::vector<node *> nodes;


    std::vector<std::multimap<float, short int> *> neighbors;

    TSP(int nodes) : numNodes(nodes) {}

    void init();
    void printNode(const node &);
    void createNeighbors();
    void printNeighbors();

    float distance(int, int);
};
#endif
