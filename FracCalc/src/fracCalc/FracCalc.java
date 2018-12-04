/*This class contains a method that accepts two fractions and an operator and returns the solution
 * @author Sammi Yeung
 * @version November 19, 2018
 */

package fracCalc;
import static org.junit.Assert.assertArrayEquals;
import java.util.*;

public class FracCalc {
    public static void main(String[] args){
    	Scanner userInput = new Scanner(System.in);
    	System.out.println("Please input the fraction expression you would like to evaluate:");
     	String input = userInput.nextLine();
    	
    	while (!input.equals("quit")) {
    		System.out.println(produceAnswer(input));
    		System.out.println("Do you want to keep going? Type \"quit\" to end.");
			input = userInput.nextLine();
    	}
    	userInput.close();
    }
  
    public static String produceAnswer(String input){ 
    	String[] expression = input.split(" "); //separating the operands and operators
    	String operator = expression[1];
    	int[] op1 = parseOperands(expression[0]);
    	int[] op2 = parseOperands(expression[2]);
    	int[] answerArray = parseOperands(doMath(op1, op2, operator));
    	int gcf = gcf(answerArray[1], answerArray[2]);
    	String answer = "";
    	if (gcf == 0) {
    		answer += toMixedNum(answerArray[1], answerArray[2]);
    	}
    	else {
    		answer += toMixedNum(answerArray[1]/gcf, answerArray[2]/gcf);
    	}
    	
		return answer;
    }
 
    public static int[] parseOperands(String operand) { //array with (whole, numerator, denominator)
    	String whole;
    	String numerator;
    	String denominator;
    	String[] splitOperand = operand.split("_");
    	if (splitOperand.length == 1) {
        	String[] splitFrac = splitOperand[0].split("/");
        	if (splitFrac.length == 1) { //no fraction
        		whole = splitFrac[0];
        		numerator = "0";
        		denominator = "1";
        	}
        	else { //no whole, no fraction
        		whole = "0";
        		numerator = splitFrac[0];
            	denominator = splitFrac[1];
        	}
    	}
    	else { //normal mixed number, with both a whole and a fraction
        	whole = splitOperand[0];
        	String fraction = splitOperand[1];
        	String [] splitFrac = fraction.split("/");
        		numerator = splitFrac[0];
        		denominator = splitFrac[1];
    	}
    	return intArray(whole, numerator, denominator); 
    }

    public static int[] intArray(String whole, String numerator, String denominator) {
    	int parseWhole = Integer.parseInt(whole);
    	int parseNum = Integer.parseInt(numerator);	
    	int parseDenom = Integer.parseInt(denominator);	
    	int[] fracParts = {parseWhole, parseNum, parseDenom};
		return fracParts;
    }
    
    public static String doMath(int[] op1, int[] op2, String operator) {
    	int[] imprOperand1 = toImproperFrac(op1[0], op1[1], op1[2]); //arrays breaking apart parts of each operand as an improper fraction
    	int[] imprOperand2 = toImproperFrac(op2[0], op2[1], op2[2]); 
        String answer = "";
        
        if(operator.equals("+")) {
        	int numAnswer = (imprOperand1[0]*imprOperand2[1])+(imprOperand2[0]*imprOperand1[1]); 
        	int denomAnswer = commonDenominator(imprOperand1[1], imprOperand2[1]); //common denominator found
        	answer = numAnswer + "/" + denomAnswer;
        }
        
        else  if(operator.equals("-")) {
        	int numAnswer = (imprOperand1[0]*imprOperand2[1])-(imprOperand2[0]*imprOperand1[1]);
        	int denomAnswer = commonDenominator(imprOperand1[1], imprOperand2[1]); //common denominator found
        	answer = numAnswer + "/" + denomAnswer;
        }
        
        else if(operator.equals("*")) {
        	int numAnswer = imprOperand1[0] * imprOperand2[0];
        	int denomAnswer = imprOperand1[1] * imprOperand2[1];
        	answer = numAnswer + "/" + denomAnswer;
        }
        
        else if(operator.equals("/")) {
        	int numAnswer = imprOperand1[0] * imprOperand2[1];
        	int denomAnswer = imprOperand1[1] * imprOperand2[0];
        	answer = numAnswer + "/" + denomAnswer;
        }
		return answer;
    }
    
    public static int commonDenominator(int denom1, int denom2) {
    	int common = denom1 * denom2;
    	return common;
    }
    
	public static int[] toImproperFrac(int wholenum, int numerator, int denominator) { //converts mixed number to improper fraction
		 if (wholenum < 0 && numerator > 0) {//negative whole numbers, stays negative when converted to improper fractions
			  numerator = numerator * -1;
		   }
		int[] answer = {(wholenum*denominator)+numerator, denominator};
		return answer; 
	}
	
	public static String toMixedNum(int num, int denom) { //converts improper to mixed fraction
		int wholenum = (num/denom);
		int newnumer = (num%denom);
		String answer = wholenum + "_" + newnumer + "/" + denom;
		if (newnumer > 0 && denom < 0) {
    		newnumer *= -1;
    		denom = Math.abs(denom);
    	}
		if (newnumer == 0) { //simplifies numbers like 5_0/1
    		answer = wholenum + "";
    	}
		else if (wholenum < 0) { //simplifies numbers like -5_-0/1
			newnumer = Math.abs(newnumer);
			denom = Math.abs(denom);
    		answer = wholenum + "_" + newnumer + "/" + denom;	
    	}
		if (wholenum == 0) { //simplifies numbers like 0_1/2
    		answer = newnumer + "/" + denom;	
    	}
    	if (wholenum == 0 && newnumer == 0) { //simplifies numbers like 0_0/1
    		answer = "0";
    	}
    	else if (newnumer < 0 && denom < 0) { //simplies numbers like -5/-8
    		newnumer = Math.abs(newnumer);
    		denom = Math.abs(denom);
			answer = wholenum + "_" + newnumer + "/" + denom;
    	}
		return answer; 
	}
	public static boolean isDivisibleBy(int dividend, int divisor) { //returns true if a is divisible by b
		if (divisor==0) throw new IllegalArgumentException("numbers cannot be divided by zero as it is undefined");
		if (dividend%divisor == 0) {
	    return true;
		}
		else
		return false;
	}
	
	public static int gcf(int a, int b){  //returns the greatest common denominator of the two inputed numbers
	    int answer = a;
	    a = Math.abs(a);
	    b = Math.abs(b);
	    if (a == 0) {
	    	answer = 0;
	    }
	    else {
	    	for(int i = a; i>=1; i--) {
	    		if(isDivisibleBy(a, i) && isDivisibleBy(b, i)) {
	    			answer = i; 
	    			i = 0;
	    		}
	    	}
	    }
		return answer;
	}
}