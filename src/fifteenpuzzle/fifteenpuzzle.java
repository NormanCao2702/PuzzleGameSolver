package fifteenpuzzle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class fifteenpuzzle {
	int n;
	byte board[][];


	/**
	 * @param fileName
	 * @throws FileNotFoundException if file not found
	 * @throws BadBoardException     if the board is incorrectly formatted Reads a
	 *                               board from file and creates the board
	 */
	//modify
	public fifteenpuzzle(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));

		n = Integer.parseInt(br.readLine());
		
		board = new byte[n][n];
		int c1, c2, s;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c1 = br.read();
				c2 = br.read();
				s = br.read(); // skip the space
				if (s != ' ' && s != '\n') {
					br.close();
				}
				if (c1 == ' ')
					c1 = '0';
				if (c2 == ' ')
					c2 = '0';
				board[i][j] = (byte) (10 * (c1 - '0') + (c2 - '0'));
			}
		}

		br.close();
	}

	public class Pair {
		int i, j;

		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
	
	
	public int getN() {
		return n;
	}
	
	public byte[][] getBoard(){
		return board;
	}
}
