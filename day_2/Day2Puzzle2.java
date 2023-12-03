package day_2;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

class Day2Puzzle2{

    static int gameId = 0;

    static final int RED_CUBES = 12;
    static final int GREEN_CUBES = 13;
    static final int BLUE_CUBES = 14;
    static final int TOTAL_CUBES = RED_CUBES + GREEN_CUBES + BLUE_CUBES;

    public static void main(String[] args){
        try{
            Scanner input = getInput("input.txt");
            ArrayList<Integer> cubePowers = new ArrayList<>();
            
            while(input.hasNextLine()){
                GameValues game = parseGame(input.nextLine());
                RoundValues minCubes = findMinCubes(game);
                debug("Min Red: " + minCubes.redCubes);
                debug("Min Green: " + minCubes.greenCubes);
                debug("Min Blue: " + minCubes.blueCubes);
                debug("Power: " + minCubes.cubePower);
                cubePowers.add(minCubes.cubePower);
            }

            System.out.println("Answer: " + cubePowers.stream().reduce(0, Integer::sum));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static RoundValues findMinCubes(GameValues game){
        int maxRed = 0;
        int maxBlue = 0;
        int maxGreen = 0;
        for(int i = 0; i < game.roundValues.size(); i++){
            RoundValues round = game.roundValues.get(i);
            printRound(round, i);
            if(round.redCubes > maxRed) maxRed = round.redCubes;
            if(round.blueCubes > maxBlue) maxBlue = round.blueCubes;
            if(round.greenCubes > maxGreen) maxGreen = round.greenCubes;
        }
        return new RoundValues(maxRed, maxGreen, maxBlue);   
    }

    public static GameValues parseGame(String game){
        gameId++;

        debug("Game: " + game);

        String[] rounds = game.split(":")[1].split(";");

        ArrayList<RoundValues> roundValues = new ArrayList<>();

        for(int i = 1; i <= rounds.length; i++){
            String[] colors = rounds[i-1].split(",");
            int redCubes = 0;
            int blueCubes = 0;
            int greenCubes = 0;

            for(String temp : colors){
                String[] split = temp.trim().split(" ");
                int numCubes = Integer.parseInt(split[0]);
                if("red".equals(split[1])) redCubes = numCubes;
                else if("blue".equals(split[1])) blueCubes = numCubes;
                else if("green".equals(split[1])) greenCubes = numCubes;
                else debug(split[1] + " is not a color");
            }   
            
            roundValues.add(new RoundValues(redCubes, greenCubes, blueCubes));
        }

        return new GameValues(gameId, roundValues);
    }

    public static void printRound(RoundValues round, int roundNum){
        debug("\tRound " + (roundNum + 1));
        debug("\t\tRed Cubes: " + round.redCubes);
        debug("\t\tGreen Cubes: " + round.greenCubes);
        debug("\t\tBlue Cubes: " + round.blueCubes);
        debug("\t\tCube Power: " + round.cubePower);;
    }

    public static Scanner getInput(String path) throws Exception{
        File inputFile = new File(Paths.get(path).toUri());
        return new Scanner(inputFile);
    }

    public static void debug(String line){
        System.out.println("Debug(Game " + gameId + "): " + line);
    }
}

class GameValues{
    public int id;
    public ArrayList<RoundValues> roundValues = new ArrayList<>();
    
    public GameValues(int id, ArrayList<RoundValues> roundValues){
        this.id = id;
        this.roundValues = roundValues;
    }
    
    
}

class RoundValues{
    public int redCubes, greenCubes, blueCubes, cubePower;

    public RoundValues(int redCubes, int greenCubes, int blueCubes){
        this.redCubes = redCubes;
        this.greenCubes = greenCubes;
        this.blueCubes = blueCubes;
        this.cubePower = redCubes * greenCubes * blueCubes;
    }
}