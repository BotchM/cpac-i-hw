/*
 * Homework #7: Postfix Expressions
 * CPAC I, Evan Korth & Jared Wyatt
 * 03 Dec 12
 * Victor (Ben) Turner, vt520@nyu.edu, N15271750
 * 
 * This program takes an expression as input and then calculates
 * the result using the postfix conversion of the infix input.
 */

import java.util.Scanner;

public class Calculator {
	
	/**
	 * inputInfix(): Take in (assume it's correct) infix expression from user.
	 * @return infix expression from user. (String)
	 */
	public static String inputInfix() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter your infix expression: (e.g. 3+4*5/6)");
		return scanner.nextLine();
	}
	
	public static void main(String[] args) throws StackOverflowException {
		String infix, postfix;
		double result=0;
		
		infix = inputInfix();
		Converter converter = new Converter(infix);
		postfix = converter.toPostFix();
		System.out.println("Converted to postfix: " + postfix);
		result = evaluate(postfix);
		System.out.println("Answer: " + result);
	}
	
	/**
	 * evaluate(): Calculate result of infix expression by parsing postfix expression.
	 * @param postfix
	 * @return result (double)
	 * @throws StackOverflowException
	 * @throws StackUnderflowException
	 */
	public static double evaluate(String postfix) throws StackOverflowException, StackUnderflowException, IllegalOperatorException {
		BoundedStackInterface<Double> stack = new ArrayStack<Double>(50);
		Scanner tokenizer = new Scanner(postfix);
		double t, operand1, operand2;
		String operator;
		double result=0;

		for (int i=0; i<postfix.length(); i++) {
			while (tokenizer.hasNext()) {
				if (tokenizer.hasNextInt()) { // is operand
					t = tokenizer.nextInt();
					if (stack.isFull()) {
						throw new StackOverflowException("Too many operands.  Stack overflow!");
					}
					stack.push(t);
				}
				else { // is operator
					operator = tokenizer.next();
					
					// 2nd operand
					if (stack.isEmpty()) {
						throw new StackUnderflowException("Not enough operands.  Stack underflow!");
					}
					// Time to do an operation. Pop off the top 2 operands, making
					// sure top operand assigned to 2nd operand position!
					operand2 = stack.top();
					stack.pop();
					
					// 1st operand
					if (stack.isEmpty()) {
						throw new StackUnderflowException("Not enough operands.  Stack underflow!");
					}
					operand1 = stack.top();
					stack.pop();
					
					if (operator.equals("/"))
						result = operand1 / operand2;
					else if (operator.equals("*"))
						result = operand1 * operand2;
					else if (operator.equals("^"))
						result = Math.pow(operand1, operand2);
					else if (operator.equals("+"))
						result = operand1 + operand2;
					else if (operator.equals("-"))
						result = operand1 - operand2;
					else
							throw new IllegalOperatorException("Illegal symbol " + operator + " used as operator!");
					stack.push(result); // Need to put result of operation back onto stack.
				} // end if hasNextInt() & else
			} // end while
		} // end for
		
		// Added error checking from book.
		if (stack.isEmpty()) {
			throw new StackUnderflowException("Not enough operands.  Stack underflow!");
		}
		result = stack.top();
		stack.pop();
		
		if (!stack.isEmpty()) {
			throw new StackOverflowException("Too many operands.  Stack overflow!");
		}
		
		return result;
	}
}
