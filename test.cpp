#include <iostream>

int main() {
    int size = 10;
    int a = 1;
    int b = 9;
    int path[size];
    int pos[size];
    for (int i = 0; i < size; i++) {
        path[i] = i;
        pos[path[i]] = i;
    }

    int tmp1, tmp2;
    while (a < b) {
        // swap 
        tmp1 = path[a];
        tmp2 = path[b];

        path[a] = tmp2;
        path[b] = tmp1;
        // end swap

        // deras positioner i vår path byter också plats
        pos[tmp1] = b;
        pos[tmp2] = a;
        a++; b--;
    }

    a = 3;
    b = 6;

    while (a < b) {
        // swap 
        tmp1 = path[a];
        tmp2 = path[b];

        path[a] = tmp2;
        path[b] = tmp1;
        // end swap

        // deras positioner i vår path byter också plats
        pos[tmp1] = b;
        pos[tmp2] = a;
        a++; b--;
    }



    for (int i = 0; i < size; i++) {
        std::cout << i << " => " << path[i] << std::endl;
    }
    std::cout << "====== pos ======" << std::endl;
    for (int i = 0; i < size; i++) {
        std::cout << i << " => " << pos[i] << std::endl;
    }
}
