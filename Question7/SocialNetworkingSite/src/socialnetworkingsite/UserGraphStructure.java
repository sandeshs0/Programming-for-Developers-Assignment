/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socialnetworkingsite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

public class UserGraphStructure {
    private Map<Integer, List<Integer>> adjacencyList; // Maping user ID to their connections

    public UserGraphStructure() {
        adjacencyList = new HashMap<>(); 
    }

//        Adding a Node
    public void addUser(int userId) {
        // Adding a new user to the graph
        adjacencyList.put(userId, new ArrayList<>());
        System.out.println("User Added Successfully. Id: "+userId );
    }
//        Adding an Edge
    public void addConnection(int user1, int user2) {
        // Adding an an edge between user1 and user2
        adjacencyList.get(user1).add(user2);
        System.out.println(user1+"Follows"+user2);
//        adjacencyList.get(user2).add(user1); // For an undirected graph
    }
    
    public boolean isConnected(int user1, int user2) {
    List<Integer> connectionsOfUser1 = adjacencyList.get(user1);
    if (connectionsOfUser1 != null) {
        return connectionsOfUser1.contains(user2);
    } else {
        return false; // user1 doesn't exist in the graph
    }
}

    public List<Integer> getConnections(int userId) {
        return adjacencyList.getOrDefault(userId, new ArrayList<>());
    }
    public List<Integer> followingListOf(int userId) {
        return adjacencyList.getOrDefault(userId, new ArrayList<>());
    }
    // Getting connections for a given user
    public List<Integer> getFollowers(int userId) {
        List<Integer> followers = new ArrayList<>();
        for (List<Integer> connections : adjacencyList.values()){
            if (connections.contains(userId)) {
                for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
                    if (entry.getValue().equals(connections)) {
                        followers.add(entry.getKey());
                        break;
                    }
                }
            }
        }
        return followers;
    }

    public List<Integer> suggestUsersToFollow(int userId) {
        List<Integer> mutualFollowers = new ArrayList<>();

        // Getting the users followed by the given user
        List<Integer> following = followingListOf(userId);
        System.out.println(following);

        // Iterating through the users followed by the given user
        for (int followedUser : following) {
            // Getting the followers of the followed user
            List<Integer> followersOfFollowedUser = getFollowers(followedUser);
            System.out.println("followers of the follwed user , i.e 1 are:"+followersOfFollowedUser);

            // Checking if the followers of the followed user also follow the original user (userId)
            for (int follower : followersOfFollowedUser) {
                if (follower != userId && !following.contains(follower) && !mutualFollowers.contains(follower)) {
                    mutualFollowers.add(follower);
                }
            }
        }
        return mutualFollowers;
    }


    
 
    public static void main(String[] args) {
        
    }
}