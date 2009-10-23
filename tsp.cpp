#include <iostream>
#include <math.h>

struct node {
    float x;
    float y;
    node(float a, float b) : x(a), y(b) {}

};

std::ostream & operator<<(std::ostream & os, const node & d) {
    os << d.x << ',' << d.y;
    return os;
}       



float distance(float const x1, float const y1, float const x2, float const y2); 
float distance(const node & n1, const node & n2);

float pathLength(node * nodes[], int path[], int numNodes) {
    float sum = 0;
    for (int i = 1; i < numNodes; i++) {
        sum += distance(
                *nodes[path[i-1]], 
                *nodes[path[  i]]
                );
    }
    return sum;
}

float distance(int a, int b, node * nodes[], int path[]) {
    float sum = 0;
    for (;a < b; a++) {
        sum += distance(*nodes[path[a]], *nodes[path[a+1]]);
    }
    return sum;
}

void swap(int x, int y, int  path[]) {
    // path[x]^=(path[y]^=(path[x]^=path[y]));
    int tmp = path[x];
    path[x] = path[y];
    path[y] = tmp;
}

// using namespace std;

int main() {
    int numNodes;
    scanf("%d", &numNodes);
    // std::cerr << "numNodes:\t" << numNodes << std::endl;
    node * nodes[numNodes];

    float x,y;
    for (int i = 0; i < numNodes; i++) {
        scanf("%f", &x);
        scanf("%f", &y);
        nodes[i] = new node(x,y);
    }

    // testa något greedy


    int path[numNodes];

    // just some path
    for (int i = 0; i < numNodes; i++) {
        path[i] = i;
    }

    // 2-opt, i hope
    for (int i = 0; i < numNodes-3; i++) {
        for (int n = i; n < numNodes-3; i++) {

            // if (distance(*nodes[path[i+0]], *nodes[path[i+1]]) + distance(*nodes[path[i+2]], *nodes[path[i+3]]) >
                    // distance(*nodes[path[i+0]], *nodes[path[i+2]]) + distance(*nodes[path[i+1]], *nodes[path[i+3]]))

        }
    }
}

// output result
for (int i = 0; i < numNodes; i++) {
    std::cout << path[i] << std::endl;
}
std::cerr << "tour length:\t" << pathLength(nodes, path, numNodes) << std::endl;

}

float distance(float const x1, float const y1, float const x2, float const y2)  { 
    return sqrt(pow((x2-x1), 2) + pow((y2-y1),2));
}

float distance(const node & n1, const node & n2)  {
    return distance(n1.x, n1.y, n2.x, n2.y);
}
