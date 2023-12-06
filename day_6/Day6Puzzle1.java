package day_6;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6Puzzle1 {
    
    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");
        Race[] races = parseInput(input);

        int totalPossiblites = 1;

        for(Race race: races){
            int possibleWins = 0;
            for(int i = 1; i < race.time; i++){
                int distance = calculateDistance(i, race.time);
                if(distance > race.distance){
                    possibleWins++;
                }
            }
            totalPossiblites *= possibleWins;
        }

        System.out.println("Answer: " + totalPossiblites);
    }

    public static int calculateDistance(int numSecondsHeld, int totalTime){
        return numSecondsHeld * (totalTime - numSecondsHeld);
    }

    public static Race[] parseInput(Scanner input){
        ArrayList<Race> races = new ArrayList<>();

        String[] tempTimes = input.nextLine().split(":")[1].trim().split(" ");
        String[] tempDistances = input.nextLine().split(": ")[1].trim().split(" ");

        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<Integer> distances = new ArrayList<>();
        
        for(int i = 0; i < tempTimes.length; i++){
            if(!tempTimes[i].equals("")){
                times.add(Integer.parseInt(tempTimes[i]));
            }
        }

        for(int i = 0; i < tempDistances.length; i++){
            if(!tempDistances[i].equals("")){
                distances.add(Integer.parseInt(tempDistances[i]));
            }
        }

        for(int i = 0; i < times.size(); i++){
            races.add(new Race(times.get(i), distances.get(i)));
        }

        return races.toArray(new Race[1]);
    }

    public static Scanner getInput(String path) throws Exception{
        File inputFile = new File(Paths.get(path).toUri());
        return new Scanner(inputFile);
    }

    public static void debug(String line){
        System.out.println("Debug: " + line);
    }

}

class Race{
    public int time;
    public int distance;
    
    public Race(int time, int distance){
        this.time = time;
        this.distance = distance;
    }

    public String toString(){
        return "{race: {time: " + time + ", distance: " + distance + "}}";
    }
}