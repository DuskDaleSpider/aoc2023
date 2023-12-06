package day_6;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6Puzzle2 {
    
    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");
        Race[] races = parseInput(input);

        long totalPossiblites = 1;

        for(Race race: races){
            long possibleWins = 0;
            for(long i = 1; i < race.time; i++){
                long distance = calculateDistance(i, race.time);
                if(distance > race.distance){
                    possibleWins++;
                }
            }
            totalPossiblites *= possibleWins;
        }

        System.out.println("Answer: " + totalPossiblites);
    }

    public static long calculateDistance(long numSecondsHeld, long totalTime){
        return numSecondsHeld * (totalTime - numSecondsHeld);
    }

    public static Race[] parseInput(Scanner input){
        ArrayList<Race> races = new ArrayList<>();

        String tempTimes = input.nextLine().split(":")[1].trim().replaceAll(" ", "");
        String tempDistances = input.nextLine().split(": ")[1].trim().replaceAll(" ", "");

        races.add(new Race(Long.parseLong(tempTimes), Long.parseLong(tempDistances)));

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
    public long time;
    public long distance;
    
    public Race(long time, long distance){
        this.time = time;
        this.distance = distance;
    }

    public String toString(){
        return "{race: {time: " + time + ", distance: " + distance + "}}";
    }
}