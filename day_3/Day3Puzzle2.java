package day_3;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3Puzzle2 {

    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Integer> gearRatios = new ArrayList<>();
        while(input.hasNextLine())
            lines.add(input.nextLine());
        
        int width = lines.get(0).length();
        int height = lines.size();
        char[][] schematic = new char[height][width];

        for(int x = 0; x < height; x++){
            schematic[x] = lines.get(x).toCharArray();
        }

        printSchematic(schematic);
        gearRatios = findGearRatios(schematic);

        System.out.println("Answer: " + gearRatios.stream().reduce(0, Integer::sum));

    }

    public static ArrayList<Integer> findGearRatios(char[][] schematic){
        ArrayList<Integer> validGearRatios = new ArrayList<>();
        
        for(int x = 0; x < schematic.length; x++){
            for(int y = 0; y < schematic[x].length; y++){
                if(schematic[x][y] == '*' && isValidGear(schematic, x, y)){
                    debug("Found Valid Gear");
                    ArrayList<Integer> numbers = new ArrayList<>();
                    if(x > 0) numbers.addAll(findNumbers(schematic, x - 1, y - 1));
                    numbers.addAll(findNumbers(schematic, x, y-1));
                    if(x < schematic[x].length - 1) numbers.addAll(findNumbers(schematic, x + 1, y - 1));

                    validGearRatios.add(numbers.get(0) * numbers.get(1));
                }
            }
        }

        return validGearRatios;
    }

    private static boolean isValidGear(char[][] schematic, int x, int y) {
        debug("Possible gear at " + x + ", " + y);
        printPossiblePart(schematic, x, y, y);
        //check above row
        int possibleNumberCount = 0;
        
        if(x > 0){
            possibleNumberCount += findNumbers(schematic, x-1, y-1).size();
        }
        
        possibleNumberCount += findNumbers(schematic, x, y-1).size();

        if(x < schematic.length - 1){
            possibleNumberCount += findNumbers(schematic, x + 1, y-1).size();
        }

        return possibleNumberCount == 2;
    }

    public static ArrayList<Integer> findNumbers(char[][] schematic, int x, int starty){
        int areaStart = starty > 5 ? starty - 5 : 0;
        int areaEnd = starty + 5;
        areaEnd = areaEnd > schematic[x].length - 1 ? schematic[x].length - 1 : areaEnd;
        StringBuilder area = new StringBuilder();
        for(int i = areaStart; i <= areaEnd; i++) area.append(schematic[x][i]);
        debug("Fidning Numbers in this area: " + area);


        ArrayList<Integer> numbers = new ArrayList<>();
        int endPos = 0;
        int endy = starty + 2;
        for(int y = starty; y <= endy && y < schematic[x].length; y++){
            if(Character.isDigit(schematic[x][y])){
                ArrayList<Character> leftDigits = new ArrayList<>();
                ArrayList<Character> rightDigits = new ArrayList<>();
                int cury = y - 1;
                while(cury >= 0 && Character.isDigit(schematic[x][cury])){
                    leftDigits.add(schematic[x][cury]);
                    cury--;
                }
                cury = y + 1;
                while(cury <= schematic[x].length - 1  && Character.isDigit(schematic[x][cury])){
                    rightDigits.add(schematic[x][cury]);
                    cury++;
                }
                endPos = cury - 1;

                StringBuilder digits = new StringBuilder();
                for(int a = leftDigits.size() - 1; a >= 0; a--){
                    digits.append(leftDigits.get(a));
                }
                digits.append(schematic[x][y]);
                for(int a = 0; a < rightDigits.size(); a++){
                    digits.append(rightDigits.get(a));
                }
                numbers.add(Integer.parseInt(digits.toString()));

                y = endPos;
            }
        }
        debug("numbers found: " + numbers.toString());
        return numbers;
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
        System.out.println("==== Possible Gear Found ====");
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
