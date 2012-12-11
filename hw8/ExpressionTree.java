/*
 * Homework #8: Expression Trees
 * CPAC I, Evan Korth & Jared Wyatt
 * 12 Dec 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750
 * 
 * This program takes an infix expression and converts it to
 * postfix, then turns it into a tree of nodes, then outputs
 * traversal results using preorder, inorder, and postorder,
 * and finally prints the numerical result of the expression.
 * 
 * TODO: Do I need a Tree class for this assignment?
 * 
 * REQUIRES: ArrayStack.java, BoundedStackInterface.java, Calculator.java,
 * Converter.java, ExpressionTree.java, IllegalOperatorException.java, 
 * StackInterface.java, StackOverflowException.java, StackUnderflowException.java
 */

import java.util.Scanner;

public class ExpressionTree {

	public static void main(String [] args) throws StackUnderflowException, StackOverflowException {
		String infix = "", postfix = "";
		boolean running = true;

		while (running == true) {
			infix = Calculator.inputInfix();

			if (infix.length() == 0) {
				running = false;
				System.out.println("Goodbye.");
			}
			else {
				Converter converter = new Converter(infix);
				postfix = converter.toPostFix();
				System.out.println("postfix: " + postfix);
				Node expressionTree = build(postfix);
				System.out.print("preorder traversal: ");
				System.out.println(preorder(expressionTree, ""));
				System.out.print("inorder traversal: ");
				System.out.println(inorder(expressionTree, ""));
				System.out.print("postorder traversal: ");
				System.out.println(postorder(expressionTree, ""));
				System.out.println ("result: " + Calculator.evaluate(postfix));
				System.out.println();
			} // end else infix.length() != 0
		} // end while is app running?
	} // end main()
	
	public static Node build(String postfix) throws StackOverflowException, StackUnderflowException {
		Scanner tokenizer = new Scanner(postfix);
		BoundedStackInterface<Node> stack = new ArrayStack<Node>(50);

		while (tokenizer.hasNext()) {
			if (tokenizer.hasNextInt()) { // is operand
				Node newNode = new Node(tokenizer.next());
				if (stack.isFull()) {
					throw new StackOverflowException("Too many operands.  Stack overflow!");
				}
				else {
					stack.push(newNode);
				}
			}
			else { // is operator
				Node newNode = new Node(tokenizer.next());

				// 2nd operand
				if (stack.isEmpty()) {
					throw new StackUnderflowException("Not enough operands.  Stack underflow!");
				}
				else {
					// Time to do an operation. Pop off the top 2 operands, making
					// sure top operand assigned to 2nd operand position!
					newNode.right = stack.top();
					stack.pop();
				}
				
				// 1st operand
				if (stack.isEmpty()) {
					throw new StackUnderflowException("Not enough operands.  Stack underflow!");
				}
				else {
					newNode.left = stack.top();
					stack.pop();
				}

				if (stack.isFull()) {
					throw new StackOverflowException("Too many operands.  Stack overflow!");
				}
				else {
					stack.push(newNode);
				}
				
			} // end else is operator
		} // end tokenizer
		return stack.top(); // returns the full expression in node tree
	} // end build()
	
	// left elements first, then right
	public static String preorder(Node curNode, String result) {
		if (result != "") {
			result += " ";
		}
		result += curNode.toString();
		
		if (curNode.left != null) {
			result = preorder(curNode.left, result);
		}
		
		if (curNode.right != null) {
			result = preorder(curNode.right, result);
		}
		
		return result;
	}
	
	// left, element, right
	public static String inorder(Node curNode, String result) {
		if (curNode.left != null) {
			result = inorder(curNode.left, result);
		}
		else {
			result += curNode.toString();
			return result;
		}
		
		result += " " + curNode.toString() + " ";
		
		if (curNode.right != null) {
			result = inorder(curNode.right, result);
		}
		else {
			result += curNode.toString();
		}

		return result;
	}
	
	// left, right, element
	public static String postorder(Node curNode, String result) {
		if (curNode.left != null) {
			result = postorder(curNode.left, result);
		}
		else {
			result += curNode.toString() + " ";
			return result;
		}
		
		if (curNode.right != null) {
			result = postorder(curNode.right, result);
		}
		else {
			result += curNode.toString() + " ";
			return result;
		}
		
		result += curNode.toString() + " ";

		return result;
	}
} // end ExpressionTree

class Node {
	Object element;
	Node left;
	Node right;

	public Node (Object o) {
		this (o, null, null);
	}

	public Node (Object o, Node l, Node r) {
		element = o;
		left = l;
		right = r;
	}

	public String toString() {
		return "" + element;
	}
}
