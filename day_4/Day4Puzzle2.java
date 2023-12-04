package day_4;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day4Puzzle2 {
    
    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");
        ArrayList<Game> cards = new ArrayList<>();

        while(input.hasNextLine()){
            cards.add(parseGame(input.nextLine()));
        }

        int[] numCopies = new int[cards.size()];
        Arrays.fill(numCopies, 0, numCopies.length, 1);
        
        for(int i = 0; i < cards.size(); i++){
            Game current = cards.get(i);
            int winningNumberCount = getWinningNumberCount(current);

            for(int k = i + 1; k <= i + winningNumberCount; k++){
                numCopies[k] += numCopies[i];
            }
        }

        System.out.println("Answer: " + Arrays.stream(numCopies).reduce(0, Integer::sum));
    }

    public static int getWinningNumberCount(Game game){
        int count = 0;
        for(int wNum : game.winningNumbers){
            if(game.actualNumbers.contains(wNum)){
                count++;
            }
        }
        return count;
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