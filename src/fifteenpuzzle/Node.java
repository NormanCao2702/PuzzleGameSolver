package fifteenpuzzle;

public class Node implements Comparable<Node> {
	private int[][] state;
    private int h; // heuristic cost from this node to goal state
    private int f; // estimated total cost (f = g + h)
    private Node parent;
    private String move;
    
    public Node(int[][] state, Node parent, String move) {
        this.state = state;
        this.h = calculateManhattanDistance();
        this.f = h;
        this.parent = parent;
        this.move = move;
    }

    
    public int[][] getState() {
        return state;
    }
    
    public void setF(int F) {
    	this.f = F;
    }


    public int getH() {
        return h;
    }

    public int getF() {
        return f;
    }

    public Node getParent() {
        return parent;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node other = (Node) o;
        // Compare the state of this node to the state of another node
        int n = state.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (state[i][j] != other.state[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public int hashCode() {
        // Generate a unique hash code for this node based on its state
    	int code = 0;
        int n = state.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                code = code * 31 + state[i][j];
            }
        }
        return code;
    }

    public int calculateManhattanDistance() {
        int distance = 0;
        int n = state.length; //board size
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = this.state[i][j];
                if (value != 0) {
                    int goalRow = (value - 1) / n; //the right row position
                    int goalCol = (value - 1) % n; //the right column position
                    distance += Math.abs(i - goalRow) + Math.abs(j - goalCol); //estimate how far that tile needs to go to the right position
                }
            }
        }
        return distance;
    }
    
    public String getMove() {
    	return this.move;
    }


	@Override
	public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }

	public void setParent(Node current) {
		// TODO Auto-generated method stub
		this.parent = current;
	}
}
