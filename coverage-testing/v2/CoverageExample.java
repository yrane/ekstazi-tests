public class CoverageExample {
	/**
	 * A simple function that should return exactly the provided integer input.
	 * 
	 * The function below is expected to take in a value and return exactly the
	 * same value. The manner in which the return value is calculated is
	 * affected by three boolean values, but regardless of the boolean values
	 * input, the function should already return the originally provided integer
	 * value.
	 * 
	 * The implementation below has a bug, but the bug only manifests itself
	 * under a specific set of inputs.
	 * 
	 * @param x
	 *            the value to be returned.
	 * @param cond1
	 *            the boolean that causes x to be incremented when true.
	 * @param cond2
	 *            the boolean that causes x to be decremented when true.
	 * @param cond3
	 *            the boolean that causes x to be multiplied by 1 when true.
	 * @return the original value of x.
	 */
	public static int identity(int x, boolean cond1, boolean cond2, boolean cond3) {
		if (cond1) {      
			x++;      
		}

		if (cond2) {      
			x--;      
		}

		if (cond3) {      
			x *= 1;   
		}

		return x;         
	}
}

