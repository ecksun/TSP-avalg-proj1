#include "tsp.h"
#include "tour.h"
#include <iostream>
#include <math.h>
#include <assert.h>
#include <stdio.h>
#define DEBUG 1


/**
 * Constructs a new TSP instance
 */
TSP::TSP(int nodes) : numNodes(nodes) {
    tour = new Tour();
}

/**
 * Destructs this TSP instance.
 */
TSP::~TSP() {
    delete tour;
}


/*
 * Läser in alla noder och lägger dem i nodes[]
 */
void TSP::init() {
    float x,y;
    for (int i = 0; i < numNodes; i++) {
        scanf("%f", &x);
        scanf("%f", &y);
        nodes.push_back(new node(x,y));
        neighbors.push_back(new std::multimap<float, short int>);
    }
}

/*
 * Skapar alla grannlistor
 */
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

/*
 * Räknar ut avståndet mellan de två noderna a och b
 */
float TSP::distance(const int a, const int b) const {
    return sqrt(pow((nodes[b]->x - nodes[a]->x), 2) + pow((nodes[b]->y - nodes[a]->y),2));
}



/*
 * Håll koll på vart i en tur en nod är
 *
 * Vi behöver detta för att kunna slå upp saker i O(1) tid 
 * när vi ska titta på grannar, iom att vi inte vet vart i turen
 * en granne finns
 */
void TSP::createPos() {
    for (int i = 0; i < numNodes; i++) {
        (*pos)[(*tour)[i]] = i;
    }
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

void TSP::greedyPath() {
    bool used[numNodes];
    // initialize used to false
    // and tour to 0;
    for (int i = 0; i < numNodes; i++) {
        used[i] = false;
        tour->add(0); 
    }

    (*tour)[0] = 0;
    used[0] = true;
    int best;

    for (int i = 1; i < numNodes; i++) {
        best = -1;
        for (int j = 0; j < numNodes; j++) {
            if (!used[j]) {
                if (best == -1 || (distance((*tour)[i-1], j) < distance((*tour)[i-1], best))) {
                    best = j;
                }
            }
        }
        (*tour)[i] = best;
        used[best] = true;
    }
}

void TSP::oneToN() {
    for (int i = 0; i < numNodes; i++) {
        tour->add(i);
    }
}

/**
 * Performs the 2-opt local search.
 */
void TSP::twoOpt() {

}

void TSP::printTour() {
    std::cout << (*tour);
}

float TSP::tourLength() {
    return tour->length(*this);
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
    // tsp.printNeighbors();
    // std::cerr << "printNeighbors() done" << std::endl;

    // dont forget to init tour somewhere

    
//    tsp.oneToN();

    std::cerr << "oneToN() done" << std::endl;
    tsp.greedyPath();

    std::cerr << "greedyPath() done" << std::endl;

    // perform local search optimization
    tsp.twoOpt();
    std::cerr << "optimization done" << std::endl;

    tsp.printTour();

    std::cerr << "printTour() done" << std::endl;

    std::cerr << "tour length:\t" << tsp.tourLength() << std::endl;

}
