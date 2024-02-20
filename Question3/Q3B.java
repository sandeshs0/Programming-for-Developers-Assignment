package Question3;

import java.util.ArrayList;

class PriorityQueue{
    ArrayList<Integer> minHeap; //  Min heap to store the elements of priority queue
    
    PriorityQueue(){
        minHeap=new ArrayList<>();
    }
    void add(int data){

        minHeap.add(data);  //adding the new data
        upHeapify(minHeap.size()-1); //calling upheapify after adding new data to maintain the minimum heap

    }
    void upHeapify(int i){
        if(i==0){
            return;
        }
        int parentIndex=(i-1)/2; // Formula of Parent-child index in minheap
        if(minHeap.get(i)<minHeap.get(parentIndex)){ //swapping if the  element is lesser than the parent element
            swap(i,parentIndex);
            upHeapify(parentIndex); 
        }
    }

    // method to swap elements at two positions
    void  swap(int a, int b){
        int  tempA=minHeap.get(a);
        int tempB=minHeap.get(b);
        minHeap.set(a,tempB);
        minHeap.set(b,tempA);
     }

    
    int remove(){ //to  get remove the smallest element from priority queue.
        if(this.size()==0){
            return -1;
        }
        swap(0, minHeap.size()-1); //swapping the last element with first
        int val=minHeap.remove(minHeap.size()-1); //calling the remove function
        downHeapify(0);
        return val;
    }
    void downHeapify(int parent){
        int min=parent; //firstly assuming the minimum to be parent
        // Comparing with left child
        int leftIndex=2*parent+1; //from heap formula 
        // if leftchild is least, min is leftchild
        if(leftIndex< minHeap.size() && minHeap.get(leftIndex)<minHeap.get(min)){
            min=leftIndex;
        }
        // Comparing with right child
        int rightIndex=2*parent+2; //from heap formula
        // if rightchild is least, min is rightchild
        if(rightIndex< minHeap.size() && minHeap.get(rightIndex)<minHeap.get(min)){
            min=rightIndex;
        }

        if(min !=parent){ //if min is not parent we'll again swap the min and parent and call downheapify on min
            swap(parent, min);
            downHeapify(min); 
        }
    }

    // Method to check the smallest element i.e the parent element  in min heap
    int peek(){
        if(this.size()==0){
            return -1;
        }
        else{
            return minHeap.get(0);
        }
    }

    // Just returns the size of min heap
    int size(){
        return minHeap.size();
    }
}

public class Q3B {

    public static void main(String[] args) {
        PriorityQueue priorityQueue = new PriorityQueue();

        // Adding elements to priority queue
        priorityQueue.add(5);
        priorityQueue.add(3);
        priorityQueue.add(8);
        priorityQueue.add(1);
        priorityQueue.add(10);

        // printing the elements of the priority queue
        System.out.println("Elements of the priority queue:");
        while (priorityQueue.size() > 0) {
            System.out.println(priorityQueue.remove());
        }
    }
}