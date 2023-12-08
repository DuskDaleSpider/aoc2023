package day_8;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Day8Puzzle2 {

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

        //Find all nodes that end in A
        ArrayList<String> currentNodes = new ArrayList<>();
        for(String node : nodes.keySet()){
            if(node.endsWith("A"))
                currentNodes.add(node);
        }

        int numSteps = 0;
        boolean isFinished = false;
        while(!isFinished){
            for(int dir : directions){
                for(int i = 0; i < currentNodes.size(); i++){
                    String nextNode = nodes.get(currentNodes.get(i))[dir];
                    currentNodes.set(i, nextNode);
                }
                numSteps++;
            }
            boolean isAllZ = true;
            for(String node: currentNodes){
                isAllZ = node.endsWith("Z");
                if(!isAllZ){
                    break;
                }
            }
            isFinished = isAllZ;
            debug("Current Nodes: " + currentNodes);
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
