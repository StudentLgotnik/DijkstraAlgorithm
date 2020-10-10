import java.util.List;

public class Main {

    private static int[][] graphExample = {
            { 0, 10,  6,  8,  0,  0,  0,  0,  0},
            {10,  0,  0,  5, 13,  0, 11,  0,  0},
            { 6,  0,  0,  2,  3,  0,  0,  0,  0},
            { 8,  5,  2,  0,  5,  7, 12,  0,  0},
            { 0, 13,  3,  5,  0,  9,  0,  0, 12},
            { 0,  0,  0,  7,  9,  0,  4,  8, 10},
            { 0, 11,  0, 12,  0,  4,  0,  6, 16},
            { 0,  0,  0,  0,  0,  8,  6,  0, 15},
            { 0,  0,  0,  0, 12, 10, 16, 15,  0}};

    public static void main(String[] args) {
        Graph graph = new Graph(graphExample);
        List<Integer> path = DijkstraAlgorithm.findShortestWay(graph, 0, 8);
        System.out.println(path);
        System.out.println(graph.getPathLengthByNodeNumbers(path.stream().mapToInt(Integer::intValue).toArray()));
    }

}
