package Question2;

import java.util.ArrayList;
import java.util.List;
// You are given an integer n representing the total number of individuals. Each individual is identified by a unique
// ID from 0 to n-1. The individuals have a unique secret that they can share with others.
// The secret-sharing process begins with person 0, who initially possesses the secret. Person 0 can share the secret
// with any number of individuals simultaneously during specific time intervals. Each time interval is represented by
// a tuple (start, end) where start and end are non-negative integers indicating the start and end times of the interval.
// You need to determine the set of individuals who will eventually know the secret after all the possible secretsharing intervals have occurred.

public class Q2b {

    public static List<Integer> peoplesecretKnower(int n, int[][] intervals, int person1) {
        // Array to store if any person knows the secret or not
        boolean[] secretKnower = new boolean[n];

        // Since person1 knows the secret
        secretKnower[person1] = true;

        // Iterating through each interval
        for (int k = 0; k < intervals.length; k++) {
            int[] interval = intervals[k];
            // Iterating through the range of the current interval
            for (int i = interval[0]; i <= interval[1]; i++) {
                //// If the current person already knows the secret
                if (secretKnower[i]) { // For all people in the current interval

                    for (int j = interval[0]; j <= interval[1]; j++) {
                        secretKnower[j] = true;
                    }
                    break;
                }
            }
        }

        // to store the index of people who know the secret
        List<Integer> answer = new ArrayList<>();

        // Iterating through all people
        for (int i = 0; i < n; i++) {
            //  add people who know secret to the answer list
            if (secretKnower[i]) {
                answer.add(i);
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        int totalPeople = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int person1 = 0;
        System.out.println(peoplesecretKnower(totalPeople, intervals, person1));
    }
}
