import java.lang.Math;
import java.util.Arrays;
public class TSP {
    Kattio io;
    int numNodes;
    Node[] nodes;
    int[][] neighbors;

    double[][] distance;

    Tour tour;

    public final int DEBUG = 0;

    private int neighborsToCheck = 14;

    private void dbg(Object o) {
        if (DEBUG > 2) {
            System.err.println(o);
        }
    }

    public static void main (String[] argv) {
        new TSP();
    }
    TSP() {
        io = new Kattio(System.in);
        this.numNodes = io.getInt();
        nodes = new Node[numNodes];
        tour = new Tour(numNodes);
        long time;
        if (DEBUG > 0)
            time = System.currentTimeMillis();

        readNodes();

        if (DEBUG > 0) {
            System.err.println("readNodes()         " + (System.currentTimeMillis() - time));	
            time = System.currentTimeMillis();
        }

        createDistance();
        if (DEBUG > 0) {
            System.err.println("createDistance()	" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        createNeighbors();  
        if (DEBUG > 0) {
            System.err.println("createNeighbors()	" + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        printNeighbors();

        NNPath();
        if (DEBUG > 0) {
            System.err.println("NNPath()	        " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        twoOpt();
        if (DEBUG > 0) {
            System.err.println("twoOpt()	        " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        printTour();
        if (DEBUG > 0) {
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
        if (neighborsToCheck >= numNodes)
            neighborsToCheck = numNodes-1;
        neighbors =  new int[numNodes][neighborsToCheck];

        // int tmp, prev = 0;

        double dist;
        for (int i = 0; i < numNodes; i++) {
            // Möjlig optimering, sätt n = i 
            Arrays.fill(neighbors[i], -1);
innerFor:
            for (int n = 0; n < numNodes; n++) {
                if (i != n) {
                    dist = distance(i, n);

                    // om den är en värdig granne, sätt in den 
                    if (neighbors[i][neighborsToCheck-1] != -1 && dist > distance(i, neighbors[i][neighborsToCheck-1]))
                        continue;

                    
                    int min=0, max, mid;
                    max = neighborsToCheck;

                    while(max-min <= 4) {
                        mid = (min + max)/2;
                        if (dist > distance(i, neighbors[i][mid])) 
                            min = mid +1;
                        else
                            max = mid -1;
                    }

                    // Vi kollar om noden är en värdig granne
                    for (int j = min; j < max; j++) {

                        // Är detta en bättre granne?
                        if (neighbors[i][j] == -1 || (distance(i, neighbors[i][j]) > dist)) {

                            int k = neighborsToCheck-1;
                            while (k > j) {
                                neighbors[i][k] = neighbors[i][k-1];
                                k--;
                            }
                            neighbors[i][j] = n;
                            continue innerFor;

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
        if (DEBUG <= 0) return;

        System.err.println("=== Neighbors ===");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < neighborsToCheck; j++) {
                // System.err.printf("%d \t", neighbors[i][j]);
                System.err.printf("%d (%f)\t", neighbors[i][j], distance(i, neighbors[i][j]));
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
            return distance[b][a];
        }
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
improve: // restart 
            for (int i = 0; i < numNodes; i++) {
                int c1 = tour.getNode(i); // citi 1
                int nc1 = tour.getNode(i+1); // the next city after city 1
                
                // select next edge from neighbor list
                for (int n = 0; n < neighborsToCheck; n++) {
                    int c2 = neighbors[c1][n];
                    int nc2 = tour.getNextNode(tour.getPos(c2));

                    if (c1 == c2) // doesn't happen if neighbors[][] isn't borken
                        continue;
                    
                    // printTour();

                     if (distance(c1, c2) < distance(c1, nc1) ||
                        distance(c1, nc1) < distance(c2, nc2)) {
                        if (distance(c1, nc1) + distance(c2, nc2) >
                            distance(c1, c2) + distance(nc1, nc2)) {
                            improvement = true;
                            reverse(tour.getPos(nc1), tour.getPos(c2));
                            continue improve;
                        }
                    }

//                    if (distance(c1, nc1) + distance(c2, nc2) >
//                            distance(c1, c2) + distance(nc1, nc2)) {
//                        dbg(String.format("Working with c1(%d), nc1(%d), c2(%d), nc2(%d)\n", c1, nc1, c2, nc2));
//                        dbg(String.format("Före (%d, %d): %s\n", nc1, c2, tour.length(this)));
//                        improvement = true;

//                        reverse(tour.getPos(nc1), tour.getPos(c2));
//                        dbg(String.format("efter (%d, %d): %s\n", nc1, c2, tour.length(this)));
                        
//                        continue improve;
//                    }

                }
            }
        }
    }

    public void reverse(int start, int end)
    {
        //System.err.println("reverse\t"+start+"\t"+end);
        int len = end - start;

        if (len < 0) // vi har en wraparound
        {
            len += numNodes;
        }

        len = (len+1)/2; // vi behöver bara swappa "hälften av längden" ggr, eftersom vi tar 2 element per swap (+1 för att inte udda antal element ska lämnas kvar)

        for (int k = 0; k < len; k++)
        {
            tour.swap(start, end);

            // wraparound för start & end
            if (++start == numNodes)
                start = 0;

            if (--end <= 0)
                end = numNodes-1;
        }
    }

}
