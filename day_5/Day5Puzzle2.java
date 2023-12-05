package day_5;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


//Probably a better way than brute force, but it works
public class Day5Puzzle2 {
    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");

        long[][] seeds = parseSeeds(input.nextLine());
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

        for(int x = 0; x < seeds.length; x++){
            for(long y = seeds[x][0]; y < seeds[x][0] + seeds[x][1]; y++){
                long nextMapIndex = y;
                for(int i = 0; i < mappings.length && mappings[i] != null; i++){
                    for(int k = 0; k < mappings[i].size(); k++){
                        Mapping current = mappings[i].get(k);
                        if(nextMapIndex >= current.sourceStart 
                        && nextMapIndex < current.sourceStart + current.range){
                            nextMapIndex = current.desinationStart + (nextMapIndex - current.sourceStart);
                            break;
                        }
                    }
                }
                if(nextMapIndex < shortestDistance){
                    shortestDistance = nextMapIndex;
                }
            }
        }

        System.out.println("Answer: " + shortestDistance);

    }

    public static Mapping parseMapping(String mapping){
        String[] stringValues = mapping.split(" ");
        return new Mapping(Long.parseLong(stringValues[1]), Long.parseLong(stringValues[0]),  Long.parseLong(stringValues[2]));
    }

    public static long[][] parseSeeds(String seeds){
        String[] split = seeds.split(":")[1].trim().split(" ");
        long[][] seedRanges = new long[split.length/2][2];
        for(int i = 0, k = 0; i < split.length; i += 2, k++){
            long seedStart = Long.parseLong(split[i]);
            long range = Long.parseLong(split[i+1]);
            long[] temp = {seedStart, range};
            seedRanges[k] = temp;
        }

        return seedRanges;
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