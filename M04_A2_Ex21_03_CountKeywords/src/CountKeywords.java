/**
* Assignment: SDEV200_M04_Assignment2_Ex21_03
* File: CountKeywords.java
* Version: 1.0
* Date: 2/5/2024
* Author: Tomomi Hobara
* Description: This program is executed from the command line and takes a Java file name as an argument. 
               It counts the number of Java keywords in the file while ignoring keywords in comments.
               The count of keywords is displayed in the console. 
* Variables: 
    - file: a File object, the name of a .java file to be checked
    - keywordString: a String array, holds a list of Java keywords
    - keywordSet: a Set, holds the list of Java keywords
    - count: an int variable, holds the count of keywords
* Steps:
    1. Receive a file name from the command line.
    2. Read the input file line by line.
    3. Ignore the line if:
      a. It is an empty line
      b. It starts with '/*'. Keep reading next line until a closing symbol is found.
      c. It starts with '//'
      d. It contains '//' (end of line comment). Update the line by removing the end of line comment.
      e. It contains '"' (text). Remove texts between two double quotation marks.
    4. Read each word in the line and check if the word is a keyword. Increase the count.
    5. Print the total count in the console.

* Note:
    Command line example:
    java -cp "C:\Users\...\bin" CountKeywords "C:\Users\...\src\App.java" 
*/

import java.util.*;
import java.io.*;

public class CountKeywords {    
    public static void main(String[] args) throws Exception {

        // Check if there is an input in command line. Exit the program if there is no input.
        if (args.length != 1) {
            System.out.println("In correct entry. Please enter: java -cp <Path of MatchGroupingSymbols location> fileNameToTest.java");
            System.exit(1);
        }

        // Create a file object based on the command line argument
        File file = new File(args[0]);


        // Process the file input with try-catch
        try {
            System.out.println("The number of keywords in " + file.getName() + " is " + countKeywords(file));
        }

        catch (Exception ex) {
            System.out.println("Wrong expression: " + args[0]);
        }
    }


  public static int countKeywords(File file) throws Exception {  
    // Array of all Java keywords + true, false and null
    String[] keywordString = {"abstract", "assert", "boolean", 
        "break", "byte", "case", "catch", "char", "class", "const",
        "continue", "default", "do", "double", "else", "enum",
        "extends", "for", "final", "finally", "float", "goto",
        "if", "implements", "import", "instanceof", "int", 
        "interface", "long", "native", "new", "package", "private",
        "protected", "public", "return", "short", "static", 
        "strictfp", "super", "switch", "synchronized", "this",
        "throw", "throws", "transient", "try", "void", "volatile",
        "while", "true", "false", "null"};

    // Convert the array to a set
    Set<String> keywordSet = 
      new HashSet<>(Arrays.asList(keywordString));     
    
    int count = 0;    

    try (Scanner input = new Scanner(file)) { 

      while (input.hasNextLine()) {
        
        String line = input.nextLine().trim();   // Remove white spaces before and after a line

        // Ignore if line is empty and go back to the while loop.
        if (line.isEmpty()) {
          continue;
        }

        // Ignore paragraph comments
        if (line.startsWith("/*")) {
          while (!line.contains("*/")) {         
              line = input.nextLine();           // Keep reading until finding '*/'
          }
          continue;                              // Go back to the top of the while loop
        }

        // Ignore line comments
        if (line.startsWith("//")) {
          continue;
        }

        // Ignore end of line comments. Trim from '//' to the end of line
        if (line.contains("//")) {
          int position = line.indexOf("//");             // Look for the position of '//'
          line = line.substring(0, position).trim();     // Extract anything before the '//' and trim the trailing space
        }

        // Ignore texts in a line
        if (line.contains("\"")) {
          int startQuotePosition = line.indexOf("\"");
          line = line.substring(0,startQuotePosition);                  // Extract anything before the opening quotation mark
          int endQuotePosition = line.indexOf("\"", startQuotePosition + 1);   // Look for the next '"' and extract rest of the line after the closing quotation mark
          line = line + line.substring(endQuotePosition + 1 );                      // Convine non-text parts in one line
        }


        // Process the line by splitting it into words and counting keywords
        String[] words = line.split("[\\s+\\p{P}]");      // Separate words and put them in an array
        
        for (int i =0; i < words.length; i++) {
          String word = words[i].toLowerCase();
          if (keywordSet.contains(word)) 
          count++;
        }

      }
      return count;
    }
  }
}
