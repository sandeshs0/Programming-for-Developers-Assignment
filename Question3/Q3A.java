package Question3;

import java.util.Collections;
import java.util.PriorityQueue;

class ScoreTracker{
    // Dividing the Score Stream by using Max Heap on left and Min Heap on right 
    PriorityQueue <Double> leftMaxHeap;
    PriorityQueue <Double> rightMinHeap;

// Constructer that initializes the ScoreTracker class.
    ScoreTracker(){
        leftMaxHeap= new PriorityQueue<>(Collections.reverseOrder());
        rightMinHeap=new PriorityQueue<>();
    }

    // Method to add a new score to stream
    void addScore(double score){
    
        if(leftMaxHeap.isEmpty() || leftMaxHeap.peek()>=score){ // if the leftMaxheap is empty or if score is less than the maximum score of left heap which means it will be in the left of that and hence adding to left heap
            leftMaxHeap.add(score);
            }
            else{
                rightMinHeap.add(score);
            }
            
            // To Balance the heaps
            // Logic: if there are even number of scores, both heaps will have equal
            // But if there are odd number of scores, the leftMaxHeap will have one more number of scores.
            if(leftMaxHeap.size()>rightMinHeap.size()+1){ //if left heap is has more than just one more score,
                   rightMinHeap.add(leftMaxHeap.poll());
            }
            else if(leftMaxHeap.size()<rightMinHeap.size()) { //If the left heap is smaller than right heap
                leftMaxHeap.add(rightMinHeap.poll());
            }
        }

        double getMedianScore(){
            double medianScore=0; //Initializing medianScore Variable 
            if(leftMaxHeap.size()==rightMinHeap.size()){ // When there are Even Number of Scores
                medianScore= (leftMaxHeap.peek() + rightMinHeap.peek()) /2.0; //Average of greatest of Left Max heap and smallest of right min Heap
            }
            else{ //When there are odd number of scores.
                medianScore=leftMaxHeap.peek(); // The greatest of left is the median since, left heap consists of more scores incase of odd numbers of scores
            }
            return medianScore;
        }
}




public class Q3A {
    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        System.out.println("Median1: "+scoreTracker.getMedianScore()); //Median1 : 87.8

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        System.out.println("Median2: "+scoreTracker.getMedianScore()); //Median2: 87.1
    }
}
