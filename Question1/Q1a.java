package Question1;
// You are a planner working on organizing a series of events in a row of n venues. Each venue can be decorated with
// one of the k available themes. However, adjacent venues should not have the same theme. The cost of decorating
// each venue with a certain theme varies.
// The costs of decorating each venue with a specific theme are represented by an n x k cost matrix. For example,
// costs [0][0] represents the cost of decorating venue 0 with theme 0, and costs[1][2] represents the cost of
// decorating venue 1 with theme 2. Your task is to find the minimum cost to decorate all the venues while adhering
// to the adjacency constraint.
// For example, given the input costs = [[1, 5, 3], [2, 9, 4]], the minimum cost to decorate all the venues is 5. One
// possible arrangement is decorating venue 0 with theme 0 and venue 1 with theme 2, resulting in a minimum cost of
// 1 + 4 = 5. Alternatively, decorating venue 0 with theme 2 and venue 1 with theme 0 also yields a minimum cost of
// 3 + 2 = 5.
// Write a function that takes the cost matrix as input and returns the minimum cost to decorate all the venues while
// satisfying the adjacency constraint.
// Please note that the costs are positive integers.
// Example: Input: [[1, 3, 2], [4, 6, 8], [3, 1, 5]] Output: 7
// Explanation: Decorate venue 0 with theme 0, venue 1 with theme 1, and venue 2 with theme 0. Minimum cost: 1 +
// 6 + 1 = 7.


public class Q1a {
    public static int minimumCostFinder(int[][] costs) {
       
        // Supposing that n be the number of venues and k be the number of themes as suggested by question.
        int n = costs.length; // The length input array is the number of venues
        int k = costs[0].length; // Similarly, the length of array inside array is the number of themes for each venue.

        // Declaring a Cost Matrix to store minimum costs
        int[][] temp = new int[n][k];

        
        // Outer loop Iterating through venues
        for (int i = 0; i < n; i++) {
            // Inner Loop Iterating through themes
            for (int j = 0; j < k; j++) { 
                // First Initializing the cost of current venue and current theme as the minimum cost
                temp[i][j] = costs[i][j];

                // Now to update the minimum cost,
                if (i > 0) { // If i is not the the first venue in array, checking the previous venue's cost
                    int minPrevCost = Integer.MAX_VALUE; // Initializing the previous venue's cost as Infinity
                    for (int x = 0; x < k; x++) { // Iterating the themes of the previous venue
                        if (x != j) { // Excluding the current theme
                            minPrevCost = Math.min(minPrevCost, temp[i - 1][x]); // Updating the current value of minPrevCost with the minimum among current and other themes.
                        }
                    }
                    temp[i][j] += minPrevCost; // Updating the cost matrix by adding the minimum cost of the previous venue
                }
            }
        }
        // After this loop ends, the temp[][ ] will have all the minimum costs between every pair of venues and themes.

        //Now, Finding Minimum Cost
        int minCost = Integer.MAX_VALUE; // First initializing the minimum cost as infinity
        for (int j = 0; j < k; j++) { // For every themes of the last venue
            minCost = Math.min(minCost, temp[n - 1][j]); // Updating the minCost with the minimum among  all themes of the last Venue
        }

        return minCost; // Finally returning the minimum cost
    }

    public static void main(String[] args) {
        int[][] costs = {{2, 3, 1}, {4, 6, 8}, {3, 2, 5}};
        System.out.println(minimumCostFinder(costs)); 
        // Output= 7
    }
}
