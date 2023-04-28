package fifteenpuzzle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
	
	public static List<String> solvePuzzle(String inputFileName) throws IOException {
		fifteenpuzzle game = new fifteenpuzzle(inputFileName);
		PriorityQueue<Node> open = new PriorityQueue<>();
		HashMap<Node,Node> closed = new HashMap<>();
		
		Node start = new Node(game.getBoard(), null, "initial");
		byte[][] goalBoard = generateGoalBoard(game.getN());
		
		open.add(start);
		closed.put(start,start);
		
		
		while(!open.isEmpty()) {
			Node current = open.poll();
			
			if(Arrays.deepEquals(current.getState(), goalBoard)) {
				return constructSolutionPath(current);
			}
			
			List<Node> neighbors = generateNeighbors(current, goalBoard);
			for(Node element: neighbors) {		
				if (!closed.containsKey(element)) {
	                	open.add(element);
	                	closed.put(element,element);
	                }
				
			}
				
		}
		return null;
    }

    private static List<String> constructSolutionPath(Node finalNode) {
        List<String> path = new ArrayList<String>();
        Node current = finalNode;
        while (current.getParent() != null) {
            path.add(0, current.getMove()); //add in the beginning of the path bc we want to get the parent -> the descendant move
            current = current.getParent();
        }
        return path;
    }

	private static List<Node> generateNeighbors(Node node, byte[][] goalBoard) {
		// TODO Auto-generated method stub
		List<Node> neighbors = new ArrayList<Node>();
		int n = node.getState().length;
	    int blankRow = -1;
	    int blankCol = -1;
	    // Find the location of the blank tile (0)

	    boolean flag = false;
	    for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	            if (node.getState()[i][j] == 0) {
	                blankRow = i;
	                blankCol = j;
	                flag = true;
	                break;
	            }
	        }
	        if(flag == true)
	        	break;
	    }
	    
	    
	 // Generate neighbors by swapping the blank tile with adjacent tiles
	    int[][] directions = {{-1, 0}, 
	    					  {1, 0}, 
	    					  {0, -1}, 	
	    					  {0, 1}  };
	    for (int[] dir : directions) { 
	        int newBlankRow = blankRow + dir[0]; //for example, first iterate through directions[0], dir[0] = -1, dir[1]=0 => that means going up => finding the new position of number 0
	        int newBlankCol = blankCol + dir[1]; //use this position later on to swap 
	        if (newBlankRow >= 0 && newBlankRow < n && newBlankCol >= 0 && newBlankCol < n) {
	            byte[][] newState = new byte[n][n];
	            for (int i = 0; i < n; i++) {
	                for (int j = 0; j < n; j++) {
	                    newState[i][j] = node.getState()[i][j]; //copy the current state into newState(which later swap the position with the adjacent tile
	                }
	            }
	            // Swap the blank tile with the adjacent tile
	            newState[blankRow][blankCol] = newState[newBlankRow][newBlankCol];
	            newState[newBlankRow][newBlankCol] = 0;
	            // Create a new neighbor node
	            Node neighbor = new Node(newState, node, newState[blankRow][blankCol] + " "+getDirectionLetter(dir));
	            neighbors.add(neighbor);
	        }
	    }

	    return neighbors;
	    
	}
	
	
	private static String getDirectionLetter(int[] dir) {
		if (dir[0] == 1) {
	        return "U"; // swap with tile above, move blank tile down
	    } else if (dir[0] == -1) {
	        return "D"; // swap with tile below, move blank tile up
	    } else if (dir[1] == 1) {
	        return "L"; // swap with tile to the left, move blank tile right
	    } else {
	        return "R"; // swap with tile to the right, move blank tile left
	    }
	}

	
	public static void writeSolutionToFile(List<String> solution, String outputFileName) throws IOException {
        FileWriter writer = new FileWriter(outputFileName);
        for (String move : solution) {
            writer.write(move + "\n");
        }
        writer.close();
    }
    
    public static byte[][] generateGoalBoard(int n){
    	byte[][] goal = new byte[n][n];
    	int count = 1;
    	for(int i = 0;i<n;i++) {
    		for(int j =0;j<n;j++) {
    			goal[i][j] = (byte) count;
    			count = (count + 1) % (n * n);
    		}
    	}
    		
    	return goal;
 }
    
    
    
	public static void main(String[] args) throws IOException {
		System.out.println("number of arguments: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
		}

		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}
		
		
		// TODO
		File input = new File(args[0]);
		List<String> solution = solvePuzzle(input.toString());
		File output = new File(args[1]);
		// Write the solution path to file
	    writeSolutionToFile(solution, output.toString());
	
	}
}
