package player;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import cards.*;
import utility.Utility;

public class Player {

    private int health;
    private List<Card> hand;
    private List<Card> deck;

    public void setLastPlayedCard(Card lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
    } // setter for the means of testing is not necessary for the game

    private Card lastPlayedCard;
    private static int initialNumberOfCards = 6;
    private boolean attackingStatus;

    public void setDamage(int damage) {
        this.damage = damage;
    }

    private int damage;

    public Shuffler getShuffler() {
        return shuffler;
    }

    private Shuffler shuffler;

    /*In order to mock Card objects during their effect testing
    I need to have full control of what cards are on top of deck after
    shuffling. To achieve that I will write a wrapper class around a modified
    shuffleDeck method which will return a List<Card> collection instead of void.
    So shuffleDeck method will be removed from player class, in favor of shuffler
    member object.

    Furthermore, shuffling will not occur within a constructor but after a player is constructed.
    I'm not sure if I'm overusing my liberty to modify code which is being tested but as of now I can't
    come up with other solution to mocking issue.
    * */

    public Player(int health, List<Card> deck, Shuffler shuffler) {
        this.health = health;
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.shuffler = shuffler;
        lastPlayedCard = null;
        attackingStatus = false;
        damage = 0;


    }

    public void takeDamage(int amountOfDamage){

        if(health<amountOfDamage) health = 0;
        else health -= amountOfDamage;
    }

    public boolean getAttackingStatus(){
        return attackingStatus;
    }

    public void resetAttackingStatus(){
        attackingStatus = false;
    }

    public int getDamage(){
        return damage;
    }

    public void resetDamage(){
        damage = 0;
    }

    public int getHealth() {
        return health;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDeck() {
        return deck;
    }

      public Card getLastPlayedCard() {
        return lastPlayedCard;
    }

      public int getInitialNumberOfCards() {
        return initialNumberOfCards;
    }

//    public void shuffleDeck() {
//
//        List<Card> shuffledDeck = new ArrayList<>();
//
//        while(!this.deck.isEmpty()) {
//            int index = (int) (Math.random() * (this.deck.size()-1));
//            shuffledDeck.add(this.deck.remove(index));
//        }
//
//        populateDeck(shuffledDeck);
//        //custom solution just to use populateDeck method
//    }

    public void populateDeck(List<Card> cardList) {
        deck.addAll(cardList);
    }

    public int getNumberOfCardsInDeck(){
        return deck.size();
    }

    public int getNumberOfCardsInHand(){
        return hand.size();
    }

    public void drawCard() {
            Card drawnCard = deck.remove(deck.size() - 1);
            hand.add(drawnCard); 
    }

    public void drawInitialCards() {
        for (int i = 0; i < initialNumberOfCards; i++) {
            drawCard();
        }
    }

    public void playCard(int cardNumber) {
        Card cardToPlay = null;
        for (Card card : hand) {
            if (card.getNumber() == cardNumber) {
                cardToPlay = card;
                break;
            }
        }

        if(cardToPlay==null) {
            System.out.println("Card is not in hand");
            return;
        }

        hand.remove(cardToPlay);
        cardToPlay.effect();
         
        lastPlayedCard = cardToPlay;

        if(cardToPlay instanceof AttackCard){
            attackingStatus = true;
            damage += cardToPlay.getNumber();
        }
        if(cardToPlay instanceof BoostAttackCard){
            attackingStatus = true;
            damage += ((BoostAttackCard)cardToPlay).getBoost();
        }

    }

    public void playCardInDefense(int cardNumber){
         Card cardToPlay = null;
        for (Card card : hand) {
            if (card.getNumber() == cardNumber) {
                cardToPlay = card;
                break;
            }
        }

        if(cardToPlay != null){
            lastPlayedCard = cardToPlay;
            attackingStatus = true;

            System.out.println(String.format("You've defended yourself! You've been attacked for %d damage and you've used special ability of Attacking card %d to deflect the attack\r\n", cardNumber, cardNumber));
        }
        else {
            System.out.println("You don't have this card in your hand...\r\n");
        }
    }

    public boolean checkForProtectionPossibilitiesInHand(Card lastPlayedCard){
        for (Card card : hand) {
            if (card instanceof ProtectCard || card.getNumber() == lastPlayedCard.getNumber()) {
                return true;
            }
        }
        return false;
    }

    public boolean findNumberInHand(int number){
        for(Card card : hand){
            if(card.getNumber() == number){
                return true;
            }
        }
        System.out.println(String.format("There is no card in hand with number %d\r\n", number));
        return false;
    }

    public void printHand() {
        System.out.println("Player's Hand:");
        for (Card card : hand) {
            System.out.println(card.getNumber() + "(" + card.description()+ ")\r");
            // You can add additional details about the card if needed
        }
        System.out.println();
    }
}

