public class Converter {
	private String infix;
	
	// BEGIN constructors
	Converter(String infix) {
		this.infix = infix;
	}
	
	Converter() {
		this.infix = "";
	}
	// END constructors
	
	/**
	 * toPostFix(): Converts given infix to a postfix and returns postfix.
	 * @param infix: expression returned from user input. e.g. 3+4*5/6
	 * @return postfix: in postfix notation. e.g. 3 4 5 * 6 / +
	 * @throws StackOverflowException
	 * @throws StackUnderflowException
	 */
	public String toPostFix() throws StackOverflowException, StackUnderflowException {
		BoundedStackInterface<Character> stack = new ArrayStack<Character>(50);
		String postfix="";
		
		for (int i=0; i<infix.length(); i++) {
			char t = infix.charAt(i);
			if (isOperand(t)) {
				postfix += infix.charAt(i);
			}
			else if (isOperator(t)) {
				if (t != '(') {
					postfix += " "; // Proper spacing needed for Scanner tokenization.
				}
				if (!stack.isEmpty()) {
					if (t == '(') {
						stack.push(t);
					}
					else if (t == ')') {
						// Close (x) by popping all operations off stack back to delimiter '('.
						while (!stack.isEmpty() && stack.top() != '(') {
							postfix += stack.top();
							stack.pop();
						}
						// Once done popping, get rid of '(' on stack to complete (x).
						if (!stack.isEmpty() && stack.top() == '(') {
							stack.pop();
						}
					}
					else if (stack.top() == '+' || stack.top() == '-' || stack.top() == '(') {
						stack.push(t);
					}
					else { // '^', '*', '/', etc.
						postfix += stack.top() + " ";
						stack.pop();
						stack.push(t);
					}
				} // end if stack not empty
				else { // Always push the first operator onto an empty stack.
					stack.push(t);
				}
			} // if operand or operator
		} // end for
		while (!stack.isEmpty()) { // At end of expression, pop the rest of the operators off.
			postfix += " " + stack.top();
			stack.pop();
		}
		
		return postfix;
	}
	
	public static boolean isOperator(char token) {
		return ((token == '^') || (token == '*') || (token == '/') || (token == '+') || (token == '-') || (token == '(') || (token == ')'));
	}
	
	public static boolean isOperand(char token) {
		return ((token >= '0') && (token <= '9'));
	}

}
