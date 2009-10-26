#ifndef TOUR_HEADER
#define TOUR_HEADER

#include <vector>

class Tour {
    std::vector<short int> nodes;

    public:

    Tour();
    ~Tour();

    void add(short int i);
    float length(float (*distPtr)(int, int)) const;

    Tour & operator[](short int i);
};

std::ostream & operator<<(std::ostream & os, const Tour &);

}

#endif /* TOUR_HEADER guard */
