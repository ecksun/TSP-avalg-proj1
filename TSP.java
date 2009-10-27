import java.lang.Math;
public class TSP {
    Kattio io;
    int numNodes;
    Node[] nodes;
    double[][] neighbors;

    int tour[];

    private final int neighborsToCheck = 30;

    public static void main (String[] argv) {
        new TSP();
    }
    TSP() {
        io = new Kattio(System.in);
        this.numNodes = io.getInt();
        nodes = new Node[numNodes];
        tour = new int[numNodes];
        readNodes();
        createNeighbors();
    }

    void readNodes() {
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(io.getDouble(), io.getDouble());
        }
    }

    /**
     * Försöker skapa grannar
     * TODO fungerar den?
     */
    void createNeighbors() {
        neighbors =  new double[numNodes][neighborsToCheck];

        for (int i = 0; i < numNodes; i++) {
            // Möjlig optimering, sätt n = i 
            for (int n = 0; n < numNodes; n++) {
                double dist = distance(nodes[i], nodes[n]);

                // check if we at all want to use this node
                if (dist > neighbors[i][neighborsToCheck-1]) {
                    
                    boolean push = false;
                    double tmp = 0;
                    // Vi kollar om noden är en värdig granne
                    for (int j = 0; j < neighborsToCheck; j++) {
                        // om den är en värdig granne, sätt in den 
                        if (neighbors[i][j] < dist) {
                            push = true;
                            tmp = neighbors[i][j];
                            neighbors[i][j] = dist;
                        }
                        // och flytta allt annat till höger
                        if (push) {
                            tmp = neighbors[i][j];
                            neighbors[i][j] = tmp;
                        }
                    }

                }
                else {
                    break;
                }
            }
        }
    }

    double distance(Node a, Node b) {
        return Math.sqrt((a.x-b.x) * (a.x-b.x) + (a.y-b.y) * (a.y-b.y));
    }

    void NNPath() {
        boolean used[numNodes];
        int best;

        tour.add(0);
        used[0] = true;

        for (int i = 1; i < numNodes; i++) {
            best = -1;
            for (int j = 0; j < numNodes; j++) {
                if (!used[j] && (best == -1 || 
                            distance(tour[i-1], j) < distance(tour[i-1], best)) {
                    best = j;
                }
            }
            tour[i] = best;
            used[best] = true;
        }
    }
}
