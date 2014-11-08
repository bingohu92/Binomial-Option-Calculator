package pricer;

public class Node {
	int step = 0;             // Record the current step
    double stockPrice;    // The price of the stock
    double optionPrice;   // The price of the option (Call or Put)
    Node left;   // Pointer to the left subtree.
    Node right;  // Pointer to the right subtree.
}
