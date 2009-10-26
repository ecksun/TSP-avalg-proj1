#include "tsp.h"
#include <iostream>
#include <math.h>
#define DEBUG 1

void TSP::init() {
    short int used[numNodes];
    // nodes[numNodes];
    // neighbors[numNodes];

    float x,y;
    for (int i = 0; i < numNodes; i++) {
        scanf("%f", &x);
        scanf("%f", &y);
        nodes.push_back(new node(x,y));
        used[i] = false;
        neighbors.push_back(new std::multimap<float, short int>);
    }
}

void TSP::createNeighbors() {
    // Vi letar reda på alla grannar vi har
    for (int i = 0; i < numNodes; i++) {
        for (int n = 0; n < numNodes; n++) {
            if (i != n) {
                neighbors[i]->insert(std::pair<float, int>(
                            distance(i, n),
                            n));
                // om vi har fler grannar än vi är intresserade av 
                // tar vi bort den sista
                if (neighbors[i]->size() > neighborsToCheck) {
                    neighbors[i]->erase(--neighbors[i]->end());
                }
            }
        }
    }
}

float TSP::distance(int a, int b) {
    return sqrt(pow((nodes[b]->x - nodes[a]->x), 2) + pow((nodes[b]->y - nodes[b]->y),2));
}

/*
 * Printar ut alla grannar
 *
 */
void TSP::printNeighbors() {
    std::multimap<float, short int>::iterator iter;

    for (int i = 0; i < numNodes; i++) {
        std::cerr << i << " { "<< std::endl;
        for (iter = neighbors[i]->begin(); iter != neighbors[i]->end(); iter++) {
            std::cerr << "\t" << iter->first << " => " << iter->second << std::endl;
        }
        std::cerr << i << " } " << std::endl;
    }
}

int main() {
    int nodes;
    scanf("%d", &nodes);
    if (DEBUG)
        std::cerr << "numNodes:\t" << nodes << std::endl;
    TSP tsp(nodes);

    tsp.init();
    std::cerr << "init done" << std::endl;
    tsp.createNeighbors();
    std::cerr << "createNeighbors() done" << std::endl;
    tsp.printNeighbors();
    std::cerr << "printNeighbors() done" << std::endl;
}
