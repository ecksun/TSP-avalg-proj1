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

    private int neighborsToCheck = 5;

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

        // printNeighbors();

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

        twoDotFiveOpt();
        if (DEBUG > 0) {
            System.err.println("twoDotFiveOpt()	        " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();
        }

        printTour();
        if (DEBUG > 0) {
            System.err.println("printTour()	        " + (System.currentTimeMillis() - time));
            System.err.println("tour length:  " + tour.length(this));
        }

        io.close();
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
                distance[j][i] = distance[i][j];
            }
        }
    }

    /**
     * Försöker skapa grannar
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
     * Såhär?
     */
    void printTour() {
        io.println(tour);
        return;
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
                int nc1 = tour.getNextNode(i); // the next city after city 1
                
                // select next edge from neighbor list
                for (int n = 0; n < neighborsToCheck; n++) {
                    int c2 = neighbors[c1][n];
                    int nc2 = tour.getNextNode(tour.getPos(c2));

                    if (c1 == c2) // doesn't happen if neighbors[][] isn't borken
                        continue;
                    
                    // printTour();

                    // TODO: bra?
                     if (distance(c1, c2) < distance(c1, nc1) ||
                        distance(c1, nc1) < distance(c2, nc2)) {
                        if (distance(c1, nc1) + distance(c2, nc2) >
                            distance(c1, c2) + distance(nc1, nc2)) {
                            improvement = true;
                            reverse(tour.getPos(nc1), tour.getPos(c2));
                            continue improve;
                        }
                    }
                }
            }
        }
    }

    void twoDotFiveOpt() {
        boolean improvement = true;
        while (improvement) {
            improvement = false;
improve: // restart 
            for (int i = 0; i < numNodes; i++) {
                int t1 = tour.getNode(i); // city 1
                int t2 = tour.getNextNode(i); // follows city 1 immediately 
                
                // find next city (t3) from neighbor list (and thereby t4)
                for (int n = 0; n < neighborsToCheck; n++) {
                    int t3 = neighbors[t1][n];
                    
                    // throw away seemingly useless moves
                    if (t1 == t3) continue;
                    if (t2 == t3) continue; 

                    int t4 = tour.getNodeAfter(t3);
                    int t5 = tour.getNodeAfter(t2); // rätt tänkt?

                    if (t1 == t4) continue;
                    if (t2 == t4) continue;
                    if (t3 == t5) continue;
                    
                    // 2.5-opt
                    double curr = distance(t1,t2) + distance(t3,t4) + distance(t2,t5);
                    double opt25 = distance(t4,t2) + distance(t3,t2) + distance(t1,t5);

                    if (opt25 < curr) {
                        tour.moveBetween(t2, t3, t4);
                        improvement = true;
                        continue improve;
                    } /*else if (distance(t1, t3) < distance(t1, t2) ||
                        distance(t1, t2) < distance(t3, t4)) {
                        if (distance(t1, t2) + distance(t3, t4) >
                            distance(t1, t3) + distance(t2, t4)) {
                            improvement = true;
                            reverse(tour.getPos(t2), tour.getPos(t3));
                            continue improve;
                        }
                    }
                    */
                    
                    /*else if ( distance(t1, t2) + distance(t3, t4) >
                                distance(t1, t3) + distance(t2, t4) )
                    {
                        reverse(tour.getPos(t2), tour.getPos(t3));
                        improvement = true;
                        continue improve;
                    }
                    blir sämre utan
                    */
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
