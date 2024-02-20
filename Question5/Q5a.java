package Question5;
import java.util.Arrays;
import java.util.Random;

// Travelling Salesman problem using Ant Colony
class AntColony {
    private int[][] cityDisMatrix; // Matrix containing distances between cities
    private int numberOfAnts; // Number of ants in the colony
    private double[][] pheromoneMatrix; // Matrix containing pheromone levels on edges between cities
    private double[][] probabilityMatrix; // Matrix containing probabilityMatrix of choosing each city from a given city
    private int numberOfCities; // Number of cities
    private int[] optimumPath; // Best path found by the colony
    private int optimumPathLength; // Length of the best tour found
    private double evaporationRate; // Rate at which pheromones evaporate
    private double alpha; // Parameter controlling the influence of pheromones on path selection
    private double beta; // Parameter controlling the influence of distances on path selection

    // Constructor to initialize the AntColony
    public AntColony(int[][] cityDisMatrix, int numberOfAnts, double evaporationRate, double alpha, double beta) {
        this.cityDisMatrix = cityDisMatrix;
        this.numberOfAnts = numberOfAnts;
        this.evaporationRate = evaporationRate;
        this.alpha = alpha;
        this.beta = beta;
        this.numberOfCities = cityDisMatrix.length;
        this.pheromoneMatrix = new double[numberOfCities][numberOfCities];
        this.probabilityMatrix = new double[numberOfCities][numberOfCities];
        initializePheromones();
    }

    // Method to initialize pheromone levels on edges
    private void initializePheromones() {
        double initialPheromone = 1.0 / numberOfCities; //initially phermone is set to 1/number of cities
        //iterating through each possible pair of cities like city A to City 2.
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                if (i != j) { //To make sure we dont set Pheromone to same city pair
                    pheromoneMatrix[i][j] = initialPheromone; //// Assign the initial pheromone level to the edge between cities i and j
                }
            }
        }
    }

    // Method to solve the TSP
public void TSP(int maxIterations) {
    optimumPathLength = Integer.MAX_VALUE; // setting to infinty first
    optimumPath = new int[numberOfCities]; // array to store the optimum path
    Random random = new Random();

// Iterating through maxIterations
    for (int iteration = 0; iteration < maxIterations; iteration++) {
        // for over each ant in the colony
        for (int ant = 0; ant < numberOfAnts; ant++) {
            boolean[] visited = new boolean[numberOfCities]; //array to track visited cities
            int[] tour = new int[numberOfCities]; // Create an array to store the tour for the current ant

            // Randomly selecting the starting city for the ant
            int currentCity = random.nextInt(numberOfCities);
            tour[0] = currentCity; // Setting the starting city in the tour array
            visited[currentCity] = true; // Markig the starting city as visited

            // Iterating over the remaining cities to construct the tour
            for (int i = 1; i < numberOfCities; i++) {
                calculateprobabilityMatrix(currentCity, visited); // Calculating the probability matrix for selecting the next city
                int nextCity = selectNextCity(currentCity); // Selecting the next city based on the probability matrix
                tour[i] = nextCity; // Adding the next city to the tour
                visited[nextCity] = true; // Marking the next city as visited
                currentCity = nextCity; // Updating the current city
            }

            // Calculating the length of the tour
            int tourLength = calculateTourLength(tour);

            // Updating the optimum path if the current tour is shorter
            if (tourLength < optimumPathLength) {
                optimumPathLength = tourLength; // Updating the length of the optimum path
                optimumPath = tour; // Updating the optimum path
            }
        }

        updatePheromones(); // Update the pheromone levels after each iteration
    }
}

    // to calculate probability of moving to each city from the current city
    private void calculateprobabilityMatrix(int city, boolean[] visited) {
        double total = 0.0; // Initializing the total probability to 0
        
        // Loop through each city to calculate the probabilities
        for (int i = 0; i < numberOfCities; i++) {
            // if the city has not been visited yet
            if (!visited[i]) {
                // Calculating the probability for selecting the next city using pheromone
                probabilityMatrix[city][i] = Math.pow(pheromoneMatrix[city][i], alpha) * Math.pow(1.0 / cityDisMatrix[city][i], beta);
                total += probabilityMatrix[city][i]; // Adding the probability to the total sum
            } else {
                // If the city has been visited
                probabilityMatrix[city][i] = 0.0; // setting its probability to zero
            }
        }
    
        // Normalizing the probabilities
        for (int i = 0; i < numberOfCities; i++) {
            probabilityMatrix[city][i] /= total; // Dividing probability by the total sum
        }
    }

    // Method to select the next city based on probabilityMatrix
    private int selectNextCity(int city) {
        double[] probabilityMatrix = this.probabilityMatrix[city];
        double r = Math.random();
        double cumulativeProbability = 0.0;
        for (int i = 0; i < numberOfCities; i++) {
            cumulativeProbability += probabilityMatrix[i];
            if (r <= cumulativeProbability) {
                return i;
            }
        }
        return -1;
    }

    // Method to update pheromone levels
    private void updatePheromones() {
        // Evaporation
        for (int i = 0; i < numberOfCities; i++) {
            for (int j = 0; j < numberOfCities; j++) {
                pheromoneMatrix[i][j] *= (1.0 - evaporationRate);
            }
        }
        // Add new pheromones
        for (int ant = 0; ant < numberOfAnts; ant++) {
            for (int i = 0; i < numberOfCities - 1; i++) {
                int city1 = optimumPath[i];
                int city2 = optimumPath[i + 1];
                pheromoneMatrix[city1][city2] += (1.0 / optimumPathLength);
                pheromoneMatrix[city2][city1] += (1.0 / optimumPathLength);
            }
        }
    }

    // Method to calculate the total length of a tour
    private int calculateTourLength(int[] tour) {
        int length = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            length += cityDisMatrix[tour[i]][tour[i + 1]];
        }
        length += cityDisMatrix[tour[tour.length - 1]][tour[0]]; // Return to the starting city
        return length;
    }

    public int getoptimumPathLength() {
        return optimumPathLength;
    }

    public int[] getoptimumPath() {
        return optimumPath;
    }
}

public class Q5a {
    public static void main(String[] args) {
        int[][] cityDisMatrix = {
            {0, 12, 18, 25},
            {12, 0, 40, 30},
            {18, 40, 0, 35},
            {25, 30, 35, 0}
        };
        
        int numberOfAnts = 10;
        double evaporationRate = 0.3;
        double alpha = 0.5;
        double beta = 1.5;

        AntColony ACO = new AntColony(cityDisMatrix, numberOfAnts, evaporationRate, alpha, beta);
        ACO.TSP(100);
        System.out.println("Best tour: " + Arrays.toString(ACO.getoptimumPath()));
        System.out.println("Best tour length: " + ACO.getoptimumPathLength());
    }
}
