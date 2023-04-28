package fifteenpuzzle;

public class Node implements Comparable<Node> {
	private byte[][] state;
    private int h; // heuristic cost from this node to goal state
    private Node parent;
    private String move;
    
    public Node(byte[][] state, Node parent, String move) {
        this.state = state;
        this.h = calculateManhattanDistance();
        this.parent = parent;
        this.move = move;
    }

    
    public byte[][] getState() {
        return state;
    }
    

    public int getH() {
        return h;
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
    
    @Override
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

    private int calculateManhattanDistance(){
        int distance = 0;
        int targetRow, targetCol;
        for (int row = 0; row < state.length; row++) {
            for (int col = 0; col < state.length; col++) {
                int value = state[row][col];
                if (value == 0) {
                    continue;
                }
                targetRow = (value - 1) / state.length;
                targetCol = (value - 1) % state.length;
                distance += Math.abs(row - targetRow) + Math.abs(col - targetCol);
                
                if (row == targetRow) {
                    for (int i = col + 1; i < state.length; i++) {
                        int otherValue = state[row][i];
                        if (otherValue != 0 && (otherValue - 1) / state.length == targetRow &&
                                otherValue < value) {
                            distance += 2;
                        }
                    }
                }
                if (col == targetCol) {
                    for (int i = row + 1; i < state.length; i++) {
                        int otherValue = state[i][col];
                        if (otherValue != 0 && (otherValue - 1) % state.length == targetCol &&
                                otherValue < value) {
                            distance += 2;
                        }
                    }
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
        return Integer.compare(this.h, other.h);
    }

	public void setParent(Node current) {
		// TODO Auto-generated method stub
		this.parent = current;
	}
}
