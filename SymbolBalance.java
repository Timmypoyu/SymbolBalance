/*
Wu Po Yu 
pw2440
SymbolBalance.java
This file read in a file to check balanced symbol
*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class SymbolBalance{

    //main method    
    public static void main(String[] args){
        try{
	    //scan in file from commandline using a scan method
            Scanner scan = new Scanner(new File(args[0]));
            String fileStr = ""; 
            while (scan.hasNext()){
                fileStr = fileStr + scan.nextLine(); 
            }
	    //isBalanced method returns string 
            String error = isBalanced(fileStr);
            if (error != null) {
                System.out.println(error);
            }else{
		System.out.println("All is well");
	    }
	    
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //A method that reuturns a message when sth is mismatched
    public static String mismatch(String open, String close) {
        return "Mismatch: " + open + close;
    }
    //A method that returns a message when sth is unbalanced
    public static String unbalanced(String symbol) {
        return "Unbalanced: " + symbol;
    }
    //Check if a String isBalanced in its symbols
    public static String isBalanced(String checked){
        ArrayList<String> symbols = new ArrayList<String>();
        // fill an arraylist, symbols, with symbols from String checked
        for (int i = 0; i < checked.length(); i++) {
            String symbol = checked.substring(i, i+1);
            if (symbol.equals("{") || symbol.equals("}") ||
                symbol.equals("(") || symbol.equals(")") ||
                symbol.equals("[") || symbol.equals("]") ||
                symbol.equals("\"")) {
                symbols.add(symbol);
		//dealt expectional because there are two characters in comment markers
            } else if (symbol.equals("/") && checked.substring(i+1, i+2).equals("*")) {
                symbols.add("/*");
            } else if (symbol.equals("*") && checked.substring(i+1, i+2).equals("/")) {
                symbols.add("*/");
            }
        }
	//create a stack to push and pop symbols
        MyStack<String> filter = new MyStack<String>();

        
        String symbol = "";
        //create ignore's to deal with literal string and comments
	boolean comment_ignore = false;
        boolean string_ignore = false;
	//return arraylist size
        int size = symbols.size();
	//running through the arraylist
        for (int i = 0; i < symbols.size(); i++) {
            symbol = symbols.get(i);
	    //create swt so the program would not decrement the arrylist when seeing opening 
	    //quotation mark and comment markers
	    int swt = 0;
            // everything between comments are ignored
	    //if encounter opening comment marker or quotation, turn ignore mode on
	    //however this is under the condition that there are no other ignore mode 
	    //that are turned on
	    //also, turn ignore mode off only if the ignore mode was on.
            if (!comment_ignore && !string_ignore && symbol.equals("/*")) {
		swt = 1; 
		comment_ignore = true;
            } else if (!comment_ignore && !string_ignore && symbol.equals("\"")) {
		swt =1;
		string_ignore = true;
            } else if (comment_ignore && symbol.equals("*/")) {
                comment_ignore = false;
                continue;
            } else if (string_ignore && symbol.equals("\"")) {
                string_ignore = false;
                continue;
            }
	    //if either one of the ignore mode is turned on, then skip the current symbol
	    if ((comment_ignore || string_ignore) && swt == 1){
	    	swt ++;
	        continue;
	    	}
	    if ((comment_ignore || string_ignore) && swt !=1) {
		size--;
                continue;
            }

            //if see opening bracket, push it in the stack
            if (symbol.equals("{") ||
                symbol.equals("(") ||
                symbol.equals("[")) {
                filter.push(symbol);
            }

            //there is no opening symbol, see closing bracket from the start. 
            if (filter.isEmpty()) {
                return unbalanced(symbol);
            }
	    //if see closing bracket, pop from stack to check if they are corresponding.
	    //There is a mismatch if size is even
	    //if size is not even, then symbol is unbalanced.
	    //set Symbol back to "" for further use
            if (symbol.equals("}")) {
                String pop = filter.pop();
                if (!pop.equals("{") && size % 2 == 0) {
                    return mismatch(pop, symbol);
                } else if (!pop.equals("{") && size % 2 != 0) {
                    return unbalanced(symbol);
                } else {
                    symbol = "";
                }
            } else if (symbol.equals(")")) {
                String pop = filter.pop();
                if (!pop.equals("(") && size % 2 == 0) {
                    return mismatch(pop, symbol);
                } else if (!pop.equals("(") && size % 2 != 0) {
                    return unbalanced(symbol);
                } else {
                    symbol = "";
                }
            } else if (symbol.equals("]")) {
                String pop = filter.pop();
                if (!pop.equals("[") && size % 2 == 0) {
                    return mismatch(pop, symbol);
                } else if (!pop.equals("[") && size % 2 != 0) {
                    return unbalanced(symbol);
                } else {
                    symbol = "";
                }
            } else if (symbol.equals("*/")) {
                String pop = filter.pop();
                return unbalanced(symbol);
            }
        }

        // any other characters left, and are we in string_ignore mode
        if (string_ignore) {
            return unbalanced("\"");
        } else if (comment_ignore){
	    return unbalanced("*/");
	} else if (!filter.isEmpty()) {
            return unbalanced(filter.pop());
        } else {
            return null;
        }
    }
}