import java.lang.StringBuilder;

class Tour {

    public static final boolean DEBUG = false;
    
    /**
     * A vector holding node indices.
     */
    private int[] nodes;

    /**
     * A vector keeping track of in which position a given node
     * occurs, where both are indices starting at zero.
     */
    private int[] positions;

    /**
     * An integer that knows where to insert next. 
     * This equals current number of inserted nodes.
     */
    private int currNumAddedNodes;

    /**
     * Constructs a new tour with the specified capacity.
     * @param capacity The capacity of this tour
     */
    Tour(int capacity) {
        nodes = new int[capacity];
        positions = new int[capacity];
        currNumAddedNodes = 0;
    }

    /**
     * Adds a node index to this tour.
     *
     * @param nodeIndex The node index to add
     */
    void addNode(int nodeIndex) {
        nodes[currNumAddedNodes] = nodeIndex;
        positions[nodeIndex] = currNumAddedNodes;

        currNumAddedNodes++;
        return;
    }

    /**
     * Sets the node index at the specified position index to the
     * given node index. Does not care about nodes disappearing.
     *
     * @param posIndex position index
     * @param nodeIndex node index
     */
    private void setNode(int posIndex, int nodeIndex) {
        nodes[posIndex] = nodeIndex;
        positions[nodeIndex] = posIndex;
    }

    /**
     * Moves the given node index in this tour to the specified
     * position.
     *
     * @param x The node index to insert
     * @param a The node index after which x should be placed
     * @param a The node index before which x should be placed
     */
    boolean moveBetween(int x, int a, int b) {
        if (x == a || x == b || a == b) {
            return false;
        }

        int posX = getPos(x);
        int posA = getPos(a);
        int posB = getPos(b);

        if (DEBUG) {
            System.err.print(String.format("moveBetween(x=%d, a=%d, b=%d)", x, a, b));
            System.err.println(String.format(" = moveBetween(posX=%d, posA=%d, posB=%d)", posX, posA, posB));
        }

        if (posX < posA) {
            for (int i = posX; i < posA; i++) {
                setNode(i, getNode(i+1));
            }

            setNode(posA, x);

            if (DEBUG) nodesPosInvariant();
        } else { // posX > posA
            for (int i = posX; i > posB; i--) {
                setNode(i, getNode(i-1));
            }

            setNode(posB, x);
            if (DEBUG) nodesPosInvariant();
        }

        return true;
    }

    /**
     * Returns the node index at the specified position index of the
     * tour, both starting with zero.
     *
     * @param posIndex The position index (0: first node)
     * @return -1 if index out of bounds, otherwise the node index
     */
    int getNode(int posIndex) {
        if (posIndex == currNumAddedNodes)
            return nodes[0];
        return nodes[posIndex];
    }

    /**
     * Returns the node index of the node that has the position index
     * right after the one specified.
     *
     * @param posIndex the position index after which to get the next node from,
     * if posIndex is equal to the size of the tour it equalizes with it being 0
     * @return the node index if  
     */
    int getNextNode(int posIndex) {
        if (posIndex+1 == currNumAddedNodes)
            return nodes[0];
        return nodes[(posIndex+1)];
    }

    int getPrevNode(int posIndex) {
        if (posIndex == 0)
            return nodes[currNumAddedNodes-1];
        else
            return nodes[posIndex];
    }

    /**
     * Returns the node (index) that immediately follows the specified
     * node (index) in this tour.
     *
     * @param node the node (index) before the wanted node index
     * @return the node (index) following the specified node (index)
     */
    int getNodeAfter(int node) {
        int nodePos = getPos(node);

        return getNextNode(nodePos);
    }

    /**
     * Returns the position (order in this tour) index, starting with
     * zero of the specified node index.
     *
     * @param nodeIndex the node index for the returned position index
     * @return the position index, or -1 if not found
     */
    int getPos(int nodeIndex) {
        return positions[nodeIndex];
    }

    /**
     * Swap places on two node indices in the tour
     * @param a One of the node indices to change place 
     * @param b The other node index to change place
     * TODO: review this after changing to correct variable names.
     * I fear that the code might be borken, because of mixing 
     * of node indices and position indices.
     */
    void swap (int a, int b) {
        if (a == b) 
            return;
        int tmpNode = nodes[a];
        nodes[a] = nodes[b];
        nodes[b] = tmpNode;
        positions[nodes[a]] = a;
        positions[nodes[b]] = b;

    }

    /**
     * Returns the total length of this tour.
     *
     * @param tsp the TSP instance with distance method
     * @return total length
     */
    float length(TSP tsp) {
        float sum = 0;

        for (int i = 1; i < nodes.length; i++) {
            sum += tsp.distance(nodes[i-1], nodes[i]);
        }

        sum += tsp.distance(nodes[0], nodes[nodes.length-1]);
        return sum;
    }

    /**
     * Returns the current number of nodes in this tour.
     *
     * @return current number of nodes
     */
    int numNodes() {
        return currNumAddedNodes;
    }

    /**
     * Returns string representation of this tour, ending each node
     * index with a newline, including the last one.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int node : nodes) {
            sb.append(node + "\n");
        }

        return sb.toString();
    }

    /**
     * Print this tour to the specified stream.
     *
     * @param out The output stream
     */
    void print(Kattio out) {
        for (int i = 0; i < currNumAddedNodes; i++) {
            out.println(nodes[i]);
        }
        return;
    }

    /**
     * Tests the invariant that nodes vector and positions vector
     * should always be in sync.
     */
    private void nodesPosInvariant() {
        for (int node = 0; node < currNumAddedNodes; node++) {
            String fail = String.format("node=%d == nodes[positions[node=%d]=%d]=%d", node, node, positions[node], nodes[positions[node]]);
            assert node == nodes[positions[node]] : fail;
        }
    }
}
