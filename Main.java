import java.util.*;

class Solution {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        // Create an adjacency list to represent the graph
        List<List<double[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Populate the adjacency list with edges and their respective success probabilities
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            double prob = succProb[i];
            graph.get(u).add(new double[]{v, prob});
            graph.get(v).add(new double[]{u, prob});
        }

        // Max-heap priority queue to store nodes in order of their probability to reach them
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[1], a[1]));
        pq.add(new double[]{start, 1.0});

        // Array to track the maximum probability to reach each node
        double[] maxProb = new double[n];
        maxProb[start] = 1.0;

        while (!pq.isEmpty()) {
            double[] current = pq.poll();
            int node = (int) current[0];
            double currProb = current[1];

            // If we reached the end node, return the probability
            if (node == end) {
                return currProb;
            }

            // Explore neighbors
            for (double[] neighbor : graph.get(node)) {
                int nextNode = (int) neighbor[0];
                double edgeProb = neighbor[1];

                // Calculate the new probability to reach nextNode
                double newProb = currProb * edgeProb;

                // If the new probability is greater than the recorded one, update it
                if (newProb > maxProb[nextNode]) {
                    maxProb[nextNode] = newProb;
                    pq.add(new double[]{nextNode, newProb});
                }
            }
        }

        // If there's no path to the end node, return 0
        return 0.0;
    }
}