package Question4;

// You are provided with balanced binary tree with the target value k. return x number of values that are closest to the
// given target k. provide solution in O(n)
// Note: You have only one set of unique values x in binary search tree that are closest to the target.
// Input:
// K=3.8
// x=2
// Output: 4,3
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


// Class to for Tree Structure
class BinaryTree {
    int val;
    BinaryTree left, right; 
    public BinaryTree(int val) {
        this.val = val;
        this.left = this.right = null;
    }
}

public class Q4B {
    public static List<Integer> closestKValues(BinaryTree root, double target, int x) {
        List<Integer> result = new ArrayList<>();
        Stack<Integer> prevStack = new Stack<>();
        Stack<Integer> nextStack = new Stack<>();

        // Initializing both stacks during the in-order traversal
        inorderTraversal(root, target, false, prevStack);
        inorderTraversal(root, target, true, nextStack);

        // Merging the stacks to find the closest values
        while (x > 0) {
            x--; //
            if (prevStack.isEmpty()) {
                result.add(nextStack.pop());
            } else if (nextStack.isEmpty()) {
                result.add(prevStack.pop());
            } else if (Math.abs(prevStack.peek() - target) < Math.abs(nextStack.peek() - target)) {
                result.add(prevStack.pop());
            } else {
                result.add(nextStack.pop());
            }
        }

        return result;
    }

    // Implementing inorderTraversal in Binary Tree
    private static void inorderTraversal(BinaryTree root, double target, boolean reverse, Stack<Integer> stack) {
        if (root == null) {
            return;
        }
        Stack<BinaryTree> nodeStack = new Stack<>();
        BinaryTree current = root;
        while (current != null || !nodeStack.isEmpty()) {
            while (current != null) {
                nodeStack.push(current);
                current = (reverse) ? current.right : current.left;
            }
            current = nodeStack.pop();
            if (!reverse && current.val > target) {
                break;
            }
            if (reverse && current.val <= target) {
                break;
            }
            stack.push(current.val);
            current = (reverse) ? current.left : current.right;
        }
    }

    public static void main(String[] args) {
        BinaryTree root = new BinaryTree(4);
        root.left = new BinaryTree(2);
        root.right = new BinaryTree(5);
        root.left.left = new BinaryTree(1);
        root.left.right = new BinaryTree(3);

        double target = 3.8;
        int x = 2;

        List<Integer> closestValues = closestKValues(root, target, x);
        System.out.println(closestValues); 
    }
}
