package day_3;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3Puzzle1 {

    public static void main(String[] args){
        try{
            Scanner input = getInput("input.txt");
            ArrayList<String> lines = new ArrayList<>();
            ArrayList<Integer> partNumbers;
            while(input.hasNextLine())
                lines.add(input.nextLine());
            
            int width = lines.get(0).length();
            int height = lines.size();
            char[][] schematic = new char[height][width];

            for(int x = 0; x < height; x++){
                schematic[x] = lines.get(x).toCharArray();
            }

            printSchematic(schematic);
            partNumbers = findPartNumbers(schematic);

            System.out.println("Answer: " + partNumbers.stream().reduce(0, Integer::sum));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> findPartNumbers(char[][] schematic){
        ArrayList<Integer> validPartNums = new ArrayList<>();

        for(int x = 0; x < schematic.length; x++){
            int startPos = 0;
            int endPos = 0;
            Boolean numPossibleStarted = false;
            for(int y = 0; y < schematic[x].length; y++){
                char current = schematic[x][y];
                if(Character.isDigit(current)){
                    //if start of number
                    if(!numPossibleStarted){
                        startPos = y;
                        numPossibleStarted = true;
                    }else if(numPossibleStarted && y == schematic[x].length - 1){
                        endPos = y;
                        numPossibleStarted = false;
                        StringBuilder builder = new StringBuilder();
                        for(int digitY = startPos; digitY <= endPos; digitY++){
                            builder.append(schematic[x][digitY]);
                        }
                        int possibleNumber = Integer.parseInt(builder.toString());
                        if(isPossiblePartNumber(schematic, x, startPos, endPos)){
                            validPartNums.add(possibleNumber);
                        }else{
                            debug("------ Was NOT Valid -------");
                            printPossiblePart(schematic, x, startPos, endPos);
                        }
                    }

                }else{
                    if(numPossibleStarted){
                        endPos = y - 1;
                        numPossibleStarted = false;
                        StringBuilder builder = new StringBuilder();
                        for(int digitY = startPos; digitY <= endPos; digitY++){
                            builder.append(schematic[x][digitY]);
                        }
                        int possibleNumber = Integer.parseInt(builder.toString());
                        if(isPossiblePartNumber(schematic, x, startPos, endPos)){
                            validPartNums.add(possibleNumber);
                        }else{
                            debug("------ Was NOT Valid -------");
                            printPossiblePart(schematic, x, startPos, endPos);
                        }
                    }
                }
            }
        }

        return validPartNums;
    }

    public static Boolean isPossiblePartNumber(char[][] schematic, int x, int startY, int endY){
        // Check row above
        if(x != 0){
            char[] aboveRow = schematic[x-1];
            int loopStart = startY > 0 ? startY - 1 : startY;
            int loopEnd = endY < aboveRow.length - 1 ? endY + 1 : endY;
            for(int y = loopStart ; y <= loopEnd; y++){
                if(isSpecial(aboveRow[y])){
                    return true;
                }
            }
        }
        //Check to the sides of current row
        if(startY > 0){
            if(isSpecial(schematic[x][startY - 1])){
                return true;
            }
        }
        if(endY < schematic[x].length - 1){
            if(isSpecial(schematic[x][endY + 1])){
                return true;
            }
        }

        //check row below
        if(x != schematic.length - 1){
            char[] belowRow = schematic[x+1];
            int loopStart = startY > 0 ? startY - 1 : startY;
            int loopEnd = endY < belowRow.length  - 1? endY + 1 : endY;
            for(int y = loopStart ; y <= loopEnd; y++){
                if(isSpecial(belowRow[y])){
                    return true;
                }
            }
        }

        return false;
    }

    public static Boolean isSpecial(char x){
        return x != '.' && !Character.isDigit(x);
    }

    public static void printSchematic(char[][] schematic){
        for(int x = 0; x < schematic.length; x++){
            StringBuilder builder = new StringBuilder();
            for(int y = 0; y < schematic[x].length; y++){
                builder.append(schematic[x][y]);
            }
            debug(builder.toString());
        }
    }

    public static void printPossiblePart(char[][]schematic, int x, int startY, int endY){
        //print above
        System.out.println("==== Possible Part Number Found ====");
        int rowStart = startY > 0 ? startY - 1 : startY;
        int rowEnd = endY < schematic[x].length - 1 ? endY + 1: endY;
        if(x > 0){
            printRowSlice(schematic, x - 1, rowStart, rowEnd);
        }
        //print current row
        printRowSlice(schematic, x, rowStart, rowEnd);
        //print row below
        if(x < schematic.length - 1){
            printRowSlice(schematic, x + 1, rowStart, rowEnd);
        }
    }

    public static void printRowSlice(char[][] schematic, int x, int start, int end){
        for(int y = start; y <= end; y++){
            System.out.print(schematic[x][y]);
        }
        System.out.print("\n");
    }
    
    public static Scanner getInput(String path) throws Exception{
        File inputFile = new File(Paths.get(path).toUri());
        return new Scanner(inputFile);
    }

    public static void debug(String line){
        System.out.println("Debug: " + line);
    }
}
