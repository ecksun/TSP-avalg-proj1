#include <iostream>
#include <math.h>

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



float distance(float x1, float y1, float x2, float y2); 

float distance(node n1, node n2);

using namespace std;
int main() {
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
    

    int path[numNodes];

    for (int i = 0; i < numNodes; i++) {
        path[i] = i;
    }


    for (int i = 0; i < numNodes; i++) {
        
    }

    // cout << distance(0.0f,0.0f,3.0f,4.0f) << endl;

    // for (int i = 0; i < numNodes; i++) {
        // cout << *nodes[i] << endl;
    // }


    for (int i = 0; i < numNodes; i++) {
        cout << path[i] << endl;
    }

}

float distance(float x1, float y1, float x2, float y2) {
    return sqrt(pow((x2-x1), 2) + pow((y2-y1),2));
}

float distance(node n1, node n2) {
    return distance(n1.x, n1.y, n2.x, n2.y);
}
