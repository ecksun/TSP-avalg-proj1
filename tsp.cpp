#include <iostream>

struct node {
    float x;
    float y;
    node(float a, float b) : x(a), y(b) {}
    node() : x(0), y(0) {}

};

std::ostream & operator<<(std::ostream & os, const node & d) {
    os << d.x << ',' << d.y;
    return os;
}       


int main() {
    using namespace std;
    int numNodes;
    scanf("%d", &numNodes);
    cerr << "numNodes:\t" << numNodes << endl;
    node * nodes[numNodes];

    float x,y;
    for (int i = 0; i < numNodes; i++) {
        scanf("%f", &x);
        scanf("%f", &y);
        nodes[i] = new node(x,y);
    }

    // testa något greedy
    

    // for (int i = 0; i < numNodes; i++) {
        // cout << *nodes[i] << endl;
    // }

    for (int i = 0; i < numNodes; i++) {
        cout << i << endl;
    }

}
