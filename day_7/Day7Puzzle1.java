package day_7;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Day7Puzzle1 {
    
    public static void main(String[] args) throws Exception{
        Scanner input = getInput("input.txt");

        HashMap<HandType, ArrayList<Hand>> hands = new HashMap<>();
        

        while(input.hasNextLine()){
            Hand hand = parseHand(input.nextLine());
            if(hands.get(hand.handType) == null){
                ArrayList<Hand> list = new ArrayList<>();
                list.add(hand);
                hands.put(hand.handType, list);
            }else{
                hands.get(hand.handType).add(hand);
            }
        }

        int rank = 1;
        int totalScore = 0;
        for(HandType type : HandType.values()){
            ArrayList<Hand> currentHands = hands.get(type);
            if(hands.get(type) == null) continue;
            // Sort from lowest hand to highest
            currentHands.sort((a, b) -> {
                Character[] temp = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
                List<Character> CARDS = Arrays.asList(temp);
                char[] aChars = a.cards.toCharArray();
                char[] bChars = b.cards.toCharArray();
                for(int i = 0; i < aChars.length; i++){
                    if(CARDS.indexOf(aChars[i]) > CARDS.indexOf(bChars[i])){
                        return 1;
                    }else if(CARDS.indexOf(aChars[i]) < CARDS.indexOf(bChars[i])){
                        return -1;
                    }
                }
                return 0;
            });
            
            debug("Sorted: " + currentHands);

            for(Hand hand : currentHands){
                int score = hand.bid * rank;
                // debug("Score calculation: " + hand.bid + " * " + rank + " = " + score);
                totalScore += score;
                rank++;
            }

            System.out.println("Answer: " + totalScore);
        }

    }

    public static Hand parseHand(String hand){
        String[] data = hand.trim().split(" ");
        String cards = data[0];
        int bid = Integer.parseInt(data[1]);
        HandType type = determineHandType(cards);

        return new Hand(cards, bid, type);
    }

    public static HandType determineHandType(String cards){
        HashMap<Character, Integer> cardMap = new HashMap<>();
        
        for(char card : cards.toCharArray()){
            if(cardMap.containsKey(card))
                cardMap.put(card, cardMap.get(card) + 1);
            else
                cardMap.put(card, 1);
        }

        switch(cardMap.keySet().size()){
            case 5:
                return HandType.HIGH_CARD;
            case 4:
                return HandType.ONE_PAIR;
            case 3:
                for(char key : cardMap.keySet())
                    if(cardMap.get(key) == 3)
                        return HandType.THREE_OF_A_KIND;
                return HandType.TWO_PAIR;
            case 2: 
                int firstKeyCount = cardMap.get(cardMap.keySet().toArray()[0]);
                if(firstKeyCount == 2 || firstKeyCount == 3)
                    return HandType.FULL_HOUSE;
                return HandType.FOUR_OF_A_KIND;
            case 1:
                return HandType.FIVE_OF_A_KIND;
        }
        
        return HandType.ONE_PAIR;
    }

    public static Scanner getInput(String fiilename) throws Exception{
        return new Scanner(new File(Paths.get(fiilename).toUri()));
    }

    public static void debug(String line){
        System.out.println("[Debug] " + line);
    }

}

class Hand{
    public String cards;
    public int bid;
    public HandType handType;

    public Hand(String cards, int bid, HandType type){
        this.cards = cards;
        this.bid = bid;
        this.handType = type;
    }

    public String toString(){
        return "{Hand: {Cards: "+cards+", Bid: " + bid + ", HandType: " + handType.toString() + "}}";
    }
}

enum HandType{
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}