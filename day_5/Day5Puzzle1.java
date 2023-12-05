package day_5;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day5Puzzle1 {
    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");

        ArrayList<Long> seeds = parseSeeds(input.nextLine());
        ArrayList<Mapping>[] mappings = new ArrayList[20];

        int currentMapping = 0;
        input.nextLine();

        while(input.hasNextLine()){
            input.nextLine();
            String line = input.nextLine().trim();
            while(!"".equals(line)){
                if(mappings[currentMapping] == null) mappings[currentMapping] = new ArrayList<>();
                mappings[currentMapping].add(parseMapping(line));
                line = input.hasNextLine() ? input.nextLine() : "";
            }
            currentMapping++;
        }

        long shortestDistance = Long.MAX_VALUE;

        for(long seed : seeds){
            long nextMapIndex = seed;
            for(int i = 0; i < mappings.length && mappings[i] != null; i++){
                long old = nextMapIndex;
                for(int k = 0; k < mappings[i].size(); k++){
                    Mapping current = mappings[i].get(k);
                    if(nextMapIndex >= current.sourceStart 
                    && nextMapIndex < current.sourceStart + current.range){
                        nextMapIndex = current.desinationStart + (nextMapIndex - current.sourceStart);
                        break;
                    }
                }
                debug("Mapped " + old + " to " + nextMapIndex);
            }
            debug("Location for seed: " + nextMapIndex);
            if(nextMapIndex < shortestDistance){
                shortestDistance = nextMapIndex;
            }
        }

        System.out.println("Answer: " + shortestDistance);

    }

    public static Mapping parseMapping(String mapping){
        String[] stringValues = mapping.split(" ");
        return new Mapping(Long.parseLong(stringValues[1]), Long.parseLong(stringValues[0]),  Long.parseLong(stringValues[2]));
    }

    public static ArrayList<Long> parseSeeds(String seeds){
        return new ArrayList<>(Arrays.stream(seeds.split(":")[1].trim().split(" ")).map((value) -> {
            return Long.parseLong(value);
        }).toList());
    }

    public static Scanner getInput(String path) throws Exception{
        File inputFile = new File(Paths.get(path).toUri());
        return new Scanner(inputFile);
    }

    public static void debug(String line){
        System.out.println("Debug: " + line);
    }
}

class Mapping{
    public long sourceStart, desinationStart, range;
    
    public Mapping(long sourceStart, long desinationStart, long range){
        this.sourceStart = sourceStart;
        this.desinationStart = desinationStart;
        this.range = range;
    }

    public String toString(){
        return "{Mapping: sourceStart: "+sourceStart+", destinationStart: " + desinationStart + ", range: " + range + "}";
    }
}