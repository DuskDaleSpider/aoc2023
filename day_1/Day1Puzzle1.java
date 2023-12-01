package day_1;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

class Day1Puzzle1{

    public static void main(String[] args){
        try{
            Scanner scanner = getInput("./input.txt");
            String line;
            Long answer = 0L;
            int lineCount = 0;
            ArrayList<Integer> values = new ArrayList<>();
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                lineCount++;
                ArrayList<Character> digits = new ArrayList<>(); 
                //Get all numeric characters in string
                for(int i = 0; i < line.length(); i++){
                    if(Character.isDigit(line.charAt(i))){
                        digits.add(line.charAt(i));
                    }
                }
                //Combine Digits
                values.add(Integer.parseInt("" + digits.get(0) + digits.get(digits.size() - 1)));
            }
            debug("Debug: Line Count: " + lineCount);
            debug("Debug: values length: " + values.size());
            debug("Debug: " + values.toString());

            //Sum up values for answer
            for(Integer x : values){
                answer += x;
            }

            System.out.println("The answer is: " + answer);
            scanner.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Scanner getInput(String path) throws Exception{
        File inputFile = new File(Paths.get(path).toUri());
        return new Scanner(inputFile);
    }

    public static void debug(String line){
        System.out.println("Debug: " + line);
    }

}