import java.util.*;

public class DijkstraAlgorithm {

    private static class Node {

        private final int nodeNumber;

        private int pathLength = Integer.MAX_VALUE;
        private Node previousNode;
        private boolean isConstantNode;

        private final List<Integer> adjacentNodeNumbers;

        public Node(int nodeNumber, List<Integer> adjacentNodeNumbers) {
            this.nodeNumber = nodeNumber;
            this.adjacentNodeNumbers = adjacentNodeNumbers;
        }

        public int getNodeNumber() {
            return nodeNumber;
        }

        public int getPathLength() {
            return pathLength;
        }

        public void setPathLength(int pathLength) {
            this.pathLength = pathLength;
        }

        public Node getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(Node previousNode) {
            this.previousNode = previousNode;
        }

        public boolean isConstantNode() {
            return isConstantNode;
        }

        public void setThisNodeAsConstant() {
            isConstantNode = true;
        }

        public List<Integer> getAdjacentNodeNumbers() {
            return adjacentNodeNumbers;
        }

    }

    public static List<Integer> findShortestWay(Graph graph, int startNode, int endNode) {

        if (startNode == endNode) {
            throw new IllegalArgumentException("End node is the same as start node");
        } else if (startNode > graph.getNdeQuantity() || endNode > graph.getNdeQuantity()) {
            throw new IllegalArgumentException("Start or end node not in the graph scope");
        }

        List<Node> graphNodes = graphToNodeList(graph);

        Node lastConstantNode = graphNodes.stream()
                .filter(n -> n.getNodeNumber() == startNode)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(String.format("Node with id %d doesn't exist", startNode)));
        lastConstantNode.setPathLength(0);
        lastConstantNode.setThisNodeAsConstant();

        while (lastConstantNode.getNodeNumber() != endNode) {

            for (Node node : graphNodes) {
                if (lastConstantNode.getAdjacentNodeNumbers().contains(node.getNodeNumber())) {
                    int pathLength = graph.getAdjacencyMatrix()[lastConstantNode.getNodeNumber()][node.getNodeNumber()];
                    if (lastConstantNode.getPathLength() + pathLength < node.getPathLength()) {
                        node.setPathLength(pathLength);
                        node.setPreviousNode(lastConstantNode);
                    }
                }
            }

            lastConstantNode = graphNodes.stream()
                    .filter(n -> !n.isConstantNode())
                    .min(Comparator.comparing(Node::getPathLength))
                    .orElseThrow(() -> new NoSuchElementException("Node with id %d doesn't exist"));

            lastConstantNode.setThisNodeAsConstant();
        }

        List<Integer> result = new ArrayList<>();

        while (lastConstantNode.getNodeNumber() != startNode) {
            result.add(lastConstantNode.getNodeNumber());
            lastConstantNode = lastConstantNode.getPreviousNode();
        }

        result.add(lastConstantNode.getNodeNumber());
        Collections.reverse(result);
        return result;
    }

    private static List<Node> graphToNodeList(Graph graph) {
        List<Node> result = new ArrayList<>();
        for (int i = 0; i < graph.getNdeQuantity(); i++) {

            List<Integer> adjacentNode = new ArrayList<>();

            for (int j = 0; j < graph.getAdjacencyMatrix()[i].length; j++) {
                if (graph.getAdjacencyMatrix()[i][j] > 0) {
                    adjacentNode.add(j);
                }
            }
            result.add(new Node(i, adjacentNode));
        }
        return result;
    }
}
