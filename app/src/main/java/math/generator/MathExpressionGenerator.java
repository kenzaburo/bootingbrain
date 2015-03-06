package math.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathExpressionGenerator {

	private static String[] OPERATIOR = { "+", "-", "*", "/", "^" };
	private static Random rand;

	public static Random getRand() {
		if (null == rand) {
			rand = new Random();
		}
		return rand;
	}

	public static List<String> gen(int max, int min, int count) {
		ArrayList<String> rs = new ArrayList<String>(count + 1);
		for (int i = 0; i < count; i++) {
			rs.add(mathExpression(max, min, 4));
		}
		return rs;
	}

	/**
	 * rand dom expression with
	 * 
	 * @param max
	 * @param min
	 * @param perator
	 * @return
	 */
	
	public static String mathExpression(int max, int min, int perator) {
		String rs = "";
		int a = getRand().nextInt(max - min + 1) + min - 1;
		int b = getRand().nextInt(max - min + 1) + min - 1;
		String operator = OPERATIOR[getRand().nextInt(perator)];
		if (operator.equals("-")) {
			if (a < b) {
				rs = b + operator + a;
			}
		} else if (operator.equals("*") || operator.equals("/")) {
			b = getRand().nextInt(9)+1;
			rs = b + operator + a;
		} else {
			rs = a + operator + b;
		}
		return rs;
	}
	
	public static String mathExpression(int min_1, int max_1, String operator, int min_2, int max_2){
		String rs = "0+0";
		int a = getRand().nextInt(max_1 - min_1 + 1) + min_1 - 1;
		int b = getRand().nextInt(max_2 - min_2 + 1) + min_2 - 1;
		return a+operator+b;
	}
	
	public static String mathExpression() {
		String rs = "0+0";
		int a = getRand().nextInt(99);
		int b;
		String operator = OPERATIOR[getRand().nextInt(4)];
		if (operator.equals("-")) 
		{
			b = getRand().nextInt(a);
			if (a < b) {
				rs = b + operator + a;
			} else {
				rs = a + operator + b;
			}
		} else if (operator.equals("*")) {
			b = getRand().nextInt(9)+1;
			rs = a + operator + b;
		} else if (operator.equals("/")){
			b = getRand().nextInt(9)+1;
			int r = a/b;
			a = b*r;
			rs = a + operator + b;
		} else{
			b = getRand().nextInt(99);
			rs = a + operator + b;
		}
		return rs;
	}
}
