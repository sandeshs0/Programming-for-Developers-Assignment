package Question5;
// Assume you were hired to create an application for an ISP, and there are n network devices, such as routers,
// that are linked together to provide internet access to users. You are given a 2D array that represents network
// connections between these network devices. write an algorithm to return impacted network devices, If there is
// a power outage on a certain device, these impacted device list assist you notify linked consumers that there is a
// power outage and it will take some time to rectify an issue.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q5b {

    public static List<Integer> getSingleParentNode(int[][] edges, int targetNode) {
        // Map to represent the graph structure
        Map<Integer, List<Integer>> graph = new HashMap<>();
        // Map to store the number of incoming edges for each node
        Map<Integer, Integer> noOfIncomingEdge = new HashMap<>(); 

        // calculating incoming edge count
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int sourceNode = edge[0];
            int destNode = edge[1];
            graph.putIfAbsent(sourceNode, new ArrayList<>());
            graph.get(sourceNode).add(destNode);
            noOfIncomingEdge.put(destNode, noOfIncomingEdge.getOrDefault(destNode, 0) + 1);
        }

        // DFS starting from the target node
        List<Integer> result = new ArrayList<>();
        dfs(graph, noOfIncomingEdge, targetNode, targetNode, result);

        return result;
    }

    //DFS to find impacted nodes
    private static void dfs(Map<Integer, List<Integer>> graph, Map<Integer, Integer> noOfIncomingEdge, int currentNode, int targetNode,
                            List<Integer> result) {
        // Checking if current node has only one incoming edge
        if (noOfIncomingEdge.getOrDefault(currentNode, 0) == 1 && graph.get(targetNode).contains(currentNode)) {
            result.add(currentNode); // Adding the current node to the result list
            addChildren(graph, currentNode, result); // adding children of the current node to the result list in recursion
        }

        // iterating through children of the current node
        List<Integer> children = graph.get(currentNode);
        if (graph.containsKey(currentNode)) {
            for (int i = 0; i < children.size(); i++) {
                int child = children.get(i);
                dfs(graph, noOfIncomingEdge, child, targetNode, result); // Recursive call for each child
            }
        }
    }

    //method to add children of a node to the result list recursively
    private static void addChildren(Map<Integer, List<Integer>> graph, int node, List<Integer> result) {
        if (graph.containsKey(node)) {
            List<Integer> descendants = graph.get(node);
            for (int i = 0; i < descendants.size(); i++) {
             int descendant = descendants.get(i);
                result.add(descendant); // Adding the child node to the result list
                addChildren(graph, descendant, result); // Recursively adding children of the child node
            }
        }
    }

    public static void main(String[] args) {
        int[][] edges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 1, 6 }, { 2, 4 }, { 4, 6 }, { 4, 5 }, { 5, 7 } };
        int targetNode = 4;

        List<Integer> impactedNodes = getSingleParentNode(edges, targetNode);

        System.out.println("Impacted nodes: " + impactedNodes);
    }
}
