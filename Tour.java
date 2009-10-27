import java.lang.StringBuilder;

class Tour {

    /**
     * A vector holding node indices.
     */
    private int[] nodes;

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
        currNumAddedNodes = 0;
    }

    /**
     * Adds a node index to this tour.
     * @param index The index to add
     */
    void addNode(int index) {
        nodes[currNumAddedNodes++] = index;
        return;
    }

    /**
     * Returns the node index at the specified position index of the
     * tour.
     * @param posIndex The position index (0: first node)
     */
    int getNode(int posIndex) {
        try {
            return nodes[posIndex+1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }

        return nodes[posIndex];
    }

    /**
     * Returns the node index of the node that has the position index
     * right after the one specified.
     *
     * @param posIndex the position index after which to get the next
     * @return the node index if in bounds, otherwise -1
     */
    int getNextNode(int posIndex) {
        try {
            return nodes[posIndex+1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return -1;
        }
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
            sb.append(node);
            sb.append(System.getProperty("line.separator"));
        }

        return sb.toString();
    }

}
