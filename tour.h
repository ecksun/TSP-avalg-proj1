#ifndef TOUR_HEADER
#define TOUR_HEADER

#include <vector>

class Tour {
    std::vector<short int> * nodes;

    public:

    Tour();
    ~Tour();

    void add(short int);
    short int get(int) const;
    float length(float (*distPtr)(int, int)) const;
    int numNodes() const;

    short int & operator[](short int);
};


#endif /* TOUR_HEADER guard */
