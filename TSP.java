public class TSP {
    Kattio io;
    int numNodes;
    Node[] nodes;
    double[][] neighbors;

    private final int neighborsToCheck = 30;

    public static void main (String[] argv) {
        new TSP();
    }
    TSP() {
        io = new Kattio(System.in);
        this.numNodes = io.getInt();
        nodes = new Node[numNodes];
        readNodes();
        createNeighbors();
    }

    void readNodes() {
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(i, io.getDouble(), io.getDouble());
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
}
