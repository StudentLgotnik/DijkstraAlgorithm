import java.util.Random;
import java.util.StringJoiner;

public class Graph {

    /**
     * Presents a adjacency matrix where index of each array is node number
     * and numbers inside presents path weight to another node
     *
     * Path weight with node #:0 1 2 3 4
     *                         ↓ ↓ ↓ ↓ ↓
     *              Node 0 -> [0 0 0 7 0]
     *              Node 1 -> [0 0 7 0 0]
     *              Node 2 -> [0 7 0 6 5]
     *              Node 3 -> [7 0 6 0 9]
     *              Node 4 -> [0 0 5 9 0]
     */
    private final int[][] adjacencyMatrix;

    public Graph(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public Graph(int nodeQuantity) {
        Random rand = new Random();
        this.adjacencyMatrix = new int[nodeQuantity][nodeQuantity];
        for (int i = 0; i < nodeQuantity; i++) {
            boolean haveConnectedNode = false;
            for (int j = 0; j < nodeQuantity; j++) {
                if (j < i) {
                    //checking previous created links
                    if (this.adjacencyMatrix[i][j] > 0) {
                        haveConnectedNode = true;
                    }
                } else if (i == j) {
                    //link of the node with itself is always zero
                    this.adjacencyMatrix[i][j] = 0;
                } else if(j == adjacencyMatrix.length - 1 && !haveConnectedNode) {
                    //adding a link to another node, if no link was previous added
                    this.adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = rand.nextInt(10);
                } else {
                    //creating a link of random length
                    if (rand.nextInt(2) == 0) {
                        this.adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = 0;
                    } else {
                        this.adjacencyMatrix[i][j] = adjacencyMatrix[j][i] = rand.nextInt(10);
                        haveConnectedNode = true;
                    }
                }
            }
        }
    }

    public int getNdeQuantity() {
        return adjacencyMatrix.length;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int getPathLengthByNodeNumbers(int... nodeNumbers) {
        if (nodeNumbers.length < 2) {
            return 0;
        }
        int pathLength = 0;
        for (int i = 0; i < nodeNumbers.length - 1; i++) {
            pathLength += adjacencyMatrix[nodeNumbers[i]][nodeNumbers[i + 1]];
        }
        return pathLength;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] matrix : adjacencyMatrix) {
            sb.append("[");
            StringJoiner joiner = new StringJoiner(" ");
            for (int i : matrix) {
                joiner.add(String.valueOf(i));
            }
            sb.append(joiner.toString()).append("]\n");
        }
        return sb.toString();
    }
}
