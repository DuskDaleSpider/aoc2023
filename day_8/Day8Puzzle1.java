package day_8;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day8Puzzle1 {

    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");

        ArrayList<Integer> directions = parseDirections(input.nextLine());
        //skip the blank
        input.nextLine();

        HashMap<String, String[]> nodes = parseNodes(input);

        debug("Directions: " + directions.toString());
        debug("Nodes: ");
        for(String key : nodes.keySet()){
            debug(key + " = " + Arrays.toString(nodes.get(key)));
        }

        String currentNode = "AAA";
        boolean isFinished = false;
        int numSteps = 0;
        while(!isFinished){
            for(int direction : directions){
                debug("Going from " + currentNode + " to " + nodes.get(currentNode)[direction]);
                currentNode = nodes.get(currentNode)[direction];
                numSteps++;
            }
            isFinished = "ZZZ".equals(currentNode);
        }
        
        System.out.println("Answer: " + numSteps);
    }

    public static HashMap<String, String[]> parseNodes(Scanner input){
        HashMap<String, String[]> nodes = new HashMap<>();
        while(input.hasNextLine()){
            String[] node = input.nextLine().split("=");
            String[] temp = node[1].trim().split(",");
            temp[0] = temp[0].substring(1).trim();
            temp[1] = temp[1].trim().substring(0, 3);
            nodes.put(node[0].trim(), temp);
        }   
        return nodes;
    }

    public static ArrayList<Integer> parseDirections(String directions){
        ArrayList<Integer> parsed = new ArrayList<>();
        for(char dir : directions.toCharArray()){
            switch(dir){
                case 'L':
                    parsed.add(0);
                    break;
                case 'R':
                    parsed.add(1);
            }
        }
        return parsed;
    }   
    
    
    public static Scanner getInput(String fiilename) throws Exception{
        return new Scanner(new File(Paths.get(fiilename).toUri()));
    }

    public static void debug(String line){
        System.out.println("[Debug] " + line);
    }
}
