package day_4;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Day4Puzzle1 {
    
    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");
        int totalScore = 0;
        while(input.hasNextLine()){
            Game current = parseGame(input.nextLine());
            int score = 0;
            for(Integer wNum : current.winningNumbers){
                if(current.actualNumbers.contains(wNum)){
                    if(score == 0) score++;
                    else score *= 2;
                }
            }
            totalScore += score;
        }

        System.out.println("Answer: " + totalScore);
    }

    public static Game parseGame(String line){
        //Replace pipe with a letter, becuase splitting on the pipe was not working
        String[] card = line.split(":")[1].replace('|', 'm').split("m");

        ArrayList<Integer> winningNumbers = new ArrayList<>();
        ArrayList<Integer> actualNumbers = new ArrayList<>();

        for(String wNum : card[0].trim().split(" ")){
            if(!"".equals(wNum.trim()))
                winningNumbers.add(Integer.parseInt(wNum));
        }

        for(String num : card[1].trim().split(" ")){
            if(!"".equals(num.trim()))
                actualNumbers.add(Integer.parseInt(num));
        }

        return new Game(winningNumbers, actualNumbers);
    }

    public static Scanner getInput(String path) throws Exception{
        File inputFile = new File(Paths.get(path).toUri());
        return new Scanner(inputFile);
    }

    public static void debug(String line){
        System.out.println("Debug: " + line);
    }

}

class Game{
    public ArrayList<Integer> winningNumbers;
    public ArrayList<Integer> actualNumbers;

    public Game(ArrayList<Integer> winning, ArrayList<Integer> actual){
        this.winningNumbers = winning;
        this.actualNumbers = actual;
    }

    public String toString(){
        return "{winningNumbers = " + winningNumbers.toString() + ", actualNumbers = " + actualNumbers.toString() + "}";
    }
}