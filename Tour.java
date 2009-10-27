class Tour {

    /**
     * A vector holding node indices.
     */
    private int[] nodes;

    /**
     * An integer that knows where to insert next.
     */
    private int nextPosIndex;

    /**
     * Constructs a new tour with the specified capacity.
     * @param capacity The capacity of this tour
     */
    Tour(int capacity) {
        nodes = new int[capacity];
        nextPosIndex = 0;
    }

    /**
     * Adds a node index to this tour.
     * @param index The index to add
     */
    void addNode(int index) {
        nodes[nextPosIndex++] = index;
        return;
    }

    /**
     * Returns the node index at the specified position index of the
     * tour.
     * @param posIndex The position index (0: first node)
     */
    int getNode(int posIndex) {
        return nodes[posIndex];
    }

}
