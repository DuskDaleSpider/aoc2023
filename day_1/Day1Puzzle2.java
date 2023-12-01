package day_1;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day1Puzzle2 {

    static int lineNumber = 0;
    public static void main(String[] args){
        try{
            Scanner scanner = getInput("./input.txt");
            Long answer = 0L;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                lineNumber++;
                int num = getNumberFromLine(line);
                debug("Number from line: "  + num);
                answer += num;
            }

            System.out.println("The answer is: " + answer);
            scanner.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Integer getNumberFromLine(String line){
        //Map: Index -> Numeric Value
        HashMap<Integer, Integer> digits = getDigitsFromLine(line);

        //sort by index
        Object[] indexes = digits.keySet().toArray();
        Arrays.sort(indexes);

        // Combine First and Last digits for correct number
        String first = digits.get(indexes[0]).toString();
        String last = digits.get(indexes[indexes.length - 1]).toString();
        debug("First: " + first + " | Last: " + last);
        Integer result = Integer.parseInt(first.concat(last));
        return result;
    }

    public static HashMap<Integer, Integer> getDigitsFromLine(String line){
        HashMap<Integer, Integer> map = new HashMap<>();
        String[] nums = {"zero","one","two","three","four","five","six","seven","eight","nine"};
        //Find any spelled out number
        for(String num : nums){
            map.putAll(findAllOccurences(num, line));
        }

        //Find any numeric digits
        char[] chars = line.toCharArray();
        for(int i = 0; i < chars.length; i++){
            if(Character.isDigit(chars[i]))
                map.put(i, Character.getNumericValue(chars[i]));
        }

        debug("Valid Digits Found (index=value): " + map.toString());
        return map;
    }

    public static HashMap<Integer, Integer> findAllOccurences(String num, String line){
        line = line.toLowerCase();
        num = num.toLowerCase();
        HashMap<Integer, Integer> map = new HashMap<>();
        int curIndex = 0;
        while(true){
            int index = line.indexOf(num, curIndex);
            if(index >= 0){
                //debug("Found " + num + " at " + index);
                map.put(index, parseInt(num));
                curIndex = index + 1;
            }else break;
        }

        return map;
    }

    public static Integer parseInt(String str){
        switch(str){
            case "one": return 1;
            case "two": return 2;
            case "three": return 3;
            case "four": return 4;
            case "five": return 5;
            case "six": return 6;
            case "seven": return 7;
            case "eight": return 8;
            case "nine": return 9;
            case "zero": return 0;
            default: return -1;
        }
    }

    public static Scanner getInput(String path) throws Exception{
        File inputFile = new File(Paths.get(path).toUri());
        return new Scanner(inputFile);
    }

    public static void debug(String line){
        System.out.println("Debug(Line " + lineNumber + "): " + line);
    }
}