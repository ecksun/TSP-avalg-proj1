import java.lang.Math;
public class TSP {
    Kattio io;
    int numNodes;
    Node[] nodes;
    int[][] neighbors;

    double[][] distance;

    Tour tour;

    public final boolean DEBUG = true;

    private final int neighborsToCheck = 10;

    public static void main (String[] argv) {
        new TSP();
    }
    TSP() {
        io = new Kattio(System.in);
        this.numNodes = io.getInt();
        nodes = new Node[numNodes];
        tour = new Tour(numNodes);
        long time;
        if (DEBUG)
            time = System.currentTimeMillis();

        readNodes();

        if (DEBUG) {
            System.err.println("readNodes()         " + (System.currentTimeMillis() - time));	
            time = System.currentTimeMillis();
        }

        createDistance();
        if (DEBUG) {
            System.err.println("createDistance()	" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        // 
        // for (int i = 0; i < numNodes; i++) {
        // for (int j = 0; j < numNodes; j++) {
        // System.err.print(distance[i][j] + "\t");
        // }
        // System.out.println();
        // }

        createNeighbors();  
        if (DEBUG) {
            System.err.println("createNeighbors()	" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        NNPath();
        if (DEBUG) {
            System.err.println("NNPath()	        " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        twoOpt();
        if (DEBUG) {
            System.err.println("twoOpt()	        " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        printTour();
        if (DEBUG) {
            System.err.println("printTour()	        " + (System.currentTimeMillis() - time));
            System.err.println("tour length:  " + tour.length(this));
        }
    }

    void readNodes() {
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(io.getDouble(), io.getDouble());
        }
    }

    void createDistance() {
        distance = new double[numNodes][numNodes];
        for (int i = 0; i < numNodes; i++) {
            for (int j = i; j < numNodes; j++) {
                distance[i][j] = calcDistance(i,j);
            }
        }
    }

    /**
     * Försöker skapa grannar
     * TODO fungerar den?
     */
    void createNeighbors() {
        neighbors =  new int[numNodes][neighborsToCheck];

        for (int i = 0; i < numNodes; i++) {
            // Möjlig optimering, sätt n = i 
            for (int n = 0; n < numNodes; n++) {
                double dist = distance(i, n);

                // check if we at all want to use this node by comparing it to
                // the node furthest away from our node
                if (dist > distance(i, neighbors[i][neighborsToCheck-1])) {

                    boolean push = false;
                    int tmp = 0;
                    // Vi kollar om noden är en värdig granne
                    for (int j = 0; j < neighborsToCheck; j++) {
                        // om den är en värdig granne, sätt in den 
                        if (distance(i, neighbors[i][j]) < dist) {
                            push = true;
                            tmp = neighbors[i][j];
                            neighbors[i][j] = n;
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

    /* eventuell optimering, ´cacha alla avstånt i en matris 
     * (sätt okalkylerade värden till -1)
     */
    double calcDistance(int a, int b) {
        return Math.sqrt((nodes[a].x-nodes[b].x) * (nodes[a].x-nodes[b].x) + (nodes[a].y-nodes[b].y) * (nodes[a].y-nodes[b].y));
    }

    double calcDistance(Node a, Node b) {
        return Math.sqrt((a.x-b.x) * (a.x-b.x) + (a.y-b.y) * (a.y-b.y));
    }

    double distance(int a, int b) {
        if (b < a) {
            // System.err.printf("distance(%d, %d) = %f\n", a, b, distance[b][a]);
            return distance[b][a];
        }
        // System.err.printf("distance(%d, %d) = %f\n", a, b, distance[a][b]);
        return distance[a][b];
    }

    void NNPath() {
        boolean used[] = new boolean[numNodes];
        int best;

        tour.addNode(0);
        used[0] = true;

        for (int i = 1; i < numNodes; i++) {
            best = -1;
            for (int j = 0; j < numNodes; j++) {
                if (!used[j] && (best == -1 || 
                            distance(tour.getNode(i-1), j) < distance(tour.getNode(i-1), best))) {
                    best = j;
                            }
            }
            tour.addNode(best); // should be on pos i
            used[best] = true;
        }
    }

    // should be done i Tour
    // void createPos() {
    // for (int i = 0; i <  numNodes; i++) {
    // pos[tour.getNode(i)] = i;
    // }
    // }

    /**
     * Vi kanske kan tjäna lite hastighet här genom att buffra outputen
     */
    void printTour() {
        System.out.println(tour);
    }


    void twoOpt() {
        boolean improvement = true;
        while (improvement) {
            improvement = false;
improve:
            for (int i = 0; i < numNodes; i++) {
                for (int n = 0; n < neighborsToCheck; n++) {

                    int c1 = tour.getPos(i); // citi 1
                    int nc1 = tour.getNextNode(c1); // the next city after city 1

                    int c2 = neighbors[c1][n];
                    int nc2 = tour.getNextNode(c2);

                    if (distance(c1, nc1) + distance(c2, nc2) >
                            distance(c1, c2) + distance(nc1, nc2)) {
                        // if (distance(tour.getPos(i), tour.getPos(i)+1) + 
                        // distance(neighbors[pos[i]][n],pos[neighbors[pos[i]][n]]+1)
                        // >
                        // distance(pos[i], neighbors[pos[i]][n]) + 
                        // distance(pos[i]+1, pos[neighbors[pos[i]][n]]+1))
                        // {
                        improvement = true;
                        reverse(nc1, c2);
                        // reverse(pos[i]+1, neighbors[pos[i]][n]);
                        continue improve;
                        }

                            }
                }
            }
        }

        void reverse(int a, int b) {
            // fungerar detta=
            if (a > b)
                reverse(b, a);
            else {
                while (a < b) {
                    // wraparound
                    if (a == numNodes)
                        a = 0;
                    if (b == 0)
                        b = numNodes-1;

                    tour.swap(a,b);

                    a++;
                    b--;
                }
            }
        }
    }
