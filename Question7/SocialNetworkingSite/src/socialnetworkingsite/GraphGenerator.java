/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socialnetworkingsite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author hello
 */
public class GraphGenerator {
    public static void initiateGraph( UserGraphStructure socialGraph){
//            UserGraphStructure socialGraph = new UserGraphStructure();
        // Add users
       // Fetching users from the Databas
Connection conn = dbConnection.dbconnect();


try {

    String query = "SELECT UserID FROM Users";

    PreparedStatement pstmt = conn.prepareStatement(query);
    ResultSet rs = pstmt.executeQuery();

    while (rs.next()) {
        socialGraph.addUser(rs.getInt("UserID"));
    }
    conn.close();
} catch (Exception ex) {
    ex.printStackTrace();
}       

//Retrieving the Connection from Database and 
Connection conn2= dbConnection.dbconnect();
try {
    String query = "SELECT follower_id, followee_id FROM Edge";

    PreparedStatement pstmt = conn2.prepareStatement(query);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
        int followerId = rs.getInt("follower_id");
        int followeeId = rs.getInt("followee_id");
        socialGraph.addConnection(followerId, followeeId);
        
    }
    conn2.close();
} catch (Exception ex) {
    ex.printStackTrace();
} }
}
