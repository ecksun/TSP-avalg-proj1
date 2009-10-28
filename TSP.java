import java.lang.Math;
import java.util.Arrays;
public class TSP {
    Kattio io;
    int numNodes;
    Node[] nodes;
    int[][] neighbors;

    double[][] distance;

    Tour tour;

    public final boolean DEBUG = true;

    private int neighborsToCheck = 11;

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

        printNeighbors();

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
        if (neighborsToCheck > numNodes)
            neighborsToCheck = numNodes-1;
        neighbors =  new int[numNodes][neighborsToCheck];

        for (int i = 0; i < numNodes; i++) {
            // Möjlig optimering, sätt n = i 
            Arrays.fill(neighbors[i], -1);
            for (int n = 0; n < numNodes; n++) {
                if (i != n) {
                    double dist = distance(i, n);

                    boolean push = false;
                    int prev = 0;
                    int tmp;
                    // Vi kollar om noden är en värdig granne
                    for (int j = 0; j < neighborsToCheck; j++) {
                        // om den är en värdig granne, sätt in den 

                        if (!push) {
                            if (neighbors[i][j] == -1 || (distance(i, neighbors[i][j]) > dist)) {
                                push = true;
                                prev = neighbors[i][j];
                                neighbors[i][j] = n;
                                continue;
                            }
                        }

                        // och flytta allt annat till höger
                        if (push) {
                            tmp = neighbors[i][j];
                            neighbors[i][j] = prev;
                            prev = tmp;
                        }
                    }
                }
            }
        }
    }

    /**
     * Vi kanske kan tjäna lite hastighet här genom att buffra outputen
     */
    void printTour() {
        System.out.println(tour);
    }

    /**
     * Print all neighbors in a matrix style kinda way
     */
    void printNeighbors() {
        System.err.println("=== Neighbors ===");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < neighborsToCheck; j++) {
                System.err.print(neighbors[i][j] + "\t");
            }
            System.err.println();
        }
    }

    /* eventuell optimering, ´cacha alla avstånt i en matris 
     * (sätt okalkylerade värden till -1)
     */
    double calcDistance(int a, int b) {
        return Math.sqrt((nodes[a].x-nodes[b].x) * (nodes[a].x-nodes[b].x) + (nodes[a].y-nodes[b].y) * (nodes[a].y-nodes[b].y));
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


    void twoOpt() {
        boolean improvement = true;
        while (improvement) {
            improvement = false;
improve:
            for (int i = 0; i < numNodes; i++) {
                int c1 = tour.getNode(i); // citi 1
                int nc1 = tour.getNode(i+1); // the next city after city 1

                for (int n = 0; n < neighborsToCheck; n++) {
                    // if (c1 == n)
                        // continue;
                    int c2 = neighbors[c1][n];
                    int nc2 = tour.getNextNode(tour.getPos(c2));
                    
                    // printTour();

                    if (distance(c1, nc1) + distance(c2, nc2) >
                            distance(c1, c2) + distance(nc1, nc2)) {
                        // if (distance(tour.getPos(i), tour.getPos(i)+1) + 
                        // distance(neighbors[pos[i]][n],pos[neighbors[pos[i]][n]]+1)
                        // >
                        // distance(pos[i], neighbors[pos[i]][n]) + 
                        // distance(pos[i]+1, pos[neighbors[pos[i]][n]]+1))
                        // {
                        System.err.printf("Working with c1(%d), nc1(%d), c2(%d), nc2(%d)\n", c1, nc1, c2, nc2);
                        System.err.printf("Före (%d, %d): %s\n", nc1, c2, tour.length(this));
                        improvement = true;

                        
                        // Det utkommenderande nedan är hesselbys kod
                        // int ai = tour.getPos(nc1);
                        // int bi = tour.getPos(c2);
                        // int amli = tour.getPos(tour.getPrevNode(nc1));
                        // int bpli = tour.getPos(tour.getNextNode(c2));
// 
// 
                        // if (Math.abs(bi - ai) < Math.abs(bpli - amli)) {
                            // reverse(ai, bi);
                        // }
                        // else {
                            // reverse(bpli, amli);
                        // }

                        reverse(nc1, c2);
                        System.err.printf("efter (%d, %d): %s\n", nc1, c2, tour.length(this));
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
        System.err.printf("Före: (%d, %d)\t \n%s", a, b, tour);
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
        System.err.printf("efter: (%d, %d)\t \n%s", a, b, tour);
        }
    }
    /*
     * hesselbys kod
     */
    // void reverse(int start, int end)
    // {
        // //System.err.println("reverse\t"+start+"\t"+end);
        // int len = end - start;
// 
        // if (len < 0) // vi har en wraparound
        // {
            // len += numNodes;
        // }
// 
        // len = (len+1)/2; // vi behöver bara swappa "hälften av längden" ggr, eftersom vi tar 2 element per swap (+1 för att inte udda antal element ska lämnas kvar)
// 
        // for (int k = 0; k < len; k++)
        // {
            // tour.swap(start, end);
// 
            // // wraparound för start & end
            // if (++start == numNodes)
                // start = 0;
// 
            // if (--end <= 0)
                // end = numNodes-1;
        // }
    // }

}
