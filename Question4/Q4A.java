package Question4;
// You are given a 2D grid representing a maze in a virtual game world. The grid is of size m x n and consists of
// different types of cells:
// 'P' represents an empty path where you can move freely. 'W' represents a wall that you cannot pass through. 'S'
// represents the starting point. Lowercase letters represent hidden keys. Uppercase letters represent locked doors.
// You start at the starting point 'S' and can move in any of the four cardinal directions (up, down, left, right) to
// adjacent cells. However, you cannot walk through walls ('W').
// As you explore the maze, you may come across hidden keys represented by lowercase letters. To unlock a door
// represented by an uppercase letter, you need to collect the corresponding key first. Once you have a key, you can
// pass through the corresponding locked door.
// For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the English
// alphabet in the maze. This means that there is exactly one key for each door, and one door for each key. The letters
// used to represent the keys and doors follow the English alphabet order.
// Your task is to find the minimum number of moves required to collect all the keys and reach the exit point. The
// exit point is represented by 'E'. If it is impossible to collect all the keys and reach the exit, return -1.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Q4A {
    
    class CurCondition { //Class that represents the current condition in maze.
        int x, y; //Coordinates of the position 
        String keys; //Collected Keys

        // Constructor
        CurCondition(int x, int y, String keys) {
            this.x = x;
            this.y = y;
            this.keys = keys;
        }
    }
// method to find the minimum moves
   static int PathFinder(char[][] grid) {
        int m = grid.length; //rows
        int n = grid[0].length; //columns
        int initialPosX = -1; //Letting the initial position be (-1,-1)
        int initialPosY = -1;
        Set<Character> keys = new HashSet<>(); //to store keys collected
        Map<Character, int[]> doors = new HashMap<>(); // to store doors visited
        
      // Travelling  through the grid to find the Initial Position, find keys and doors
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char block = grid[i][j];
                if (block == 'S') {
                    initialPosX = i;
                    initialPosY = j;
                } else if ('a' <= block && block <= 'z') {
                    keys.add(block);
                } else if ('A' <= block && block<= 'Z') {
                    doors.put(block, new int[] { i, j });
                }
            }
        }
        // Storing the keys in a Arraylist
        List<Character> keysList = new ArrayList<>(keys);
        int[] minDistance = { Integer.MAX_VALUE }; //Initializing the minDistance as Infinity
       //Calling the DFS Search method
        dfs(grid, initialPosX, initialPosY, keysList, doors, new boolean[m][n], "", 0, minDistance);
        if (minDistance[0] != Integer.MAX_VALUE) {
            return minDistance[0];
        } else {
            return -1;
        }
    }

    // DFS Search
    private static void dfs(char[][] grid, int x, int y, List<Character> keys, Map<Character, int[]> doors,
            boolean[][] visited, String collectedKeys, int distance, int[] minDistance) {
       
        
        if (distance >= minDistance[0]) // IF the current distance is not lesser than minimum, return
            return;

        visited[x][y] = true; //Cuurent position is visited

        // For all four possible directions from the current position
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        for (int i = 0; i < directions.length; i++) {
            int[] dir = directions[i];
            // new positions
            int nx = x + dir[0];
            int ny = y + dir[1];

            // if new position are in the grid and havent been visited
            if (nx >= 0 && nx < grid.length && ny >= 0 && ny < grid[0].length && !visited[nx][ny]) {
                char cell = grid[nx][ny];

                //if the cell contains P or S (doors)
                if (cell == 'P' || cell == 'S') {
                    dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance);
                } else if ('a' <= cell && cell <= 'z') //if the cell contains lowercase letter, it means we have a key
                 {
                    String newCollectedKeys = collectedKeys + cell; //updting the collected keys
                    if (newCollectedKeys.length() == keys.size()) { //if collected keys = total keys
                        minDistance[0] = Math.min(minDistance[0], distance + 1); //updating the minimum distance
                    } else {
                        //if not, dfs again
                        dfs(grid, nx, ny, keys, doors, visited, newCollectedKeys, distance + 1, minDistance);
                    }
                } else if ('A' <= cell && cell <= 'Z') { // if cell contains uppercase, it is a DOOR
                    char key = Character.toLowerCase(cell); // getting the key for that door i.e lowercase
                   
                    if (collectedKeys.indexOf(key) != -1) { // if the key has been collected, visit the door
                        dfs(grid, nx, ny, keys, doors, visited, collectedKeys, distance + 1, minDistance);
                    }
                }
            }
            
        }
        //for backtracking, marking the current cell as not visited.
        visited[x][y] = false;
    }

    public static void main(String[] args) {
        char[][] grid1 = {
                { 'S', 'P', 'q', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'r', 'P', 'Q', 'P', 'R' }
        };
        System.out.println(PathFinder(grid1));
    }
}
