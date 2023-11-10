package game;

import java.util.Scanner;
import player.*;


public class Game {
    private Player player1;
    private Player player2;
    private Scanner scanner;
    private boolean gameEnded;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.scanner = new Scanner(System.in);
        gameEnded = false;
    }

    public boolean getGameEnded(){
        return gameEnded;
    }

    private boolean isPlayerWithoutOptionsToPlay(Player player){
        return isHandEmpty(player) && player.getDeck().isEmpty();
    }

    private boolean hasHealth(Player player){
        return player.getHealth() != 0;
    }

    private boolean isHandEmpty(Player player){
        return player.getHand().isEmpty();
    }

    public void startGame() {
        // Draw initial cards for both players
        player1.drawInitialCards();
        player2.drawInitialCards();

        // Game loop
        while (!getGameEnded()) {


            //HP check for player 1
            if (!hasHealth(player1)) {
                System.out.println("Player 2 wins!");
                gameEnded = true;
                break;
            }

            // Player 1's turn
            System.out.println("Player 1's Turn");
            if(isPlayerWithoutOptionsToPlay(player1)){
                System.out.println("You lost all your cards... \r\n Player 2 wins!");
                gameEnded = true;
                break;
            }

            playTurn(player1, player2);


            //HP check player 2

            if (!hasHealth(player2)) {
                System.out.println("Player 1 wins!");
                gameEnded = true;
                break;
            }

            // Player 2's turn
            System.out.println("Player 2's Turn");
            if(isPlayerWithoutOptionsToPlay(player2)){
                System.out.println("You lost all your cards... \r\n Player 1 wins!");
                gameEnded = true;
                break;
            }

            playTurn(player2, player1);

        }
    }

    private void playTurn(Player currentPlayer, Player opponentPlayer) {
        currentPlayer.drawCard();
        System.out.println("Health: " + currentPlayer.getHealth() + "\r\n");
        currentPlayer.resetAttackingStatus();
        currentPlayer.resetDamage();
 
        if(opponentPlayer.getAttackingStatus()){
            currentPlayer.printHand();
            currentPlayerUnderAttack(currentPlayer, opponentPlayer);
            if(isHandEmpty(currentPlayer) || !hasHealth(currentPlayer)){
                return;
            }
        }

        while (true) {
            currentPlayer.printHand();
            System.out.println("Enter the number of the card you want to play (or enter 'end' to end your turn):");
           
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("end")) {
                break;
            }
            try {
                    int cardNumber = Integer.parseInt(input);
                    currentPlayer.playCard(cardNumber);
                    //check if player can play anything else
                    if(isHandEmpty(currentPlayer)){
                        break;
                    }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid card number or 'end'.");
            }
        } 
    }

    public void currentPlayerUnderAttack(Player currentPlayer, Player opponentPlayer){

        System.out.println("WOW your opponent's attacking your health points with " + opponentPlayer.getDamage() + " damage!!! \r\n");

        if (currentPlayer.checkForProtectionPossibilitiesInHand(opponentPlayer.getLastPlayedCard())){
           tryToDefend(currentPlayer, opponentPlayer);
        }
        else {
            //player must take damage from attack
        currentPlayer.takeDamage(opponentPlayer.getDamage());
        System.out.println("Ohhh you've taken damage... Health: " + currentPlayer.getHealth() + "\r\n");
        }
    }

    public void tryToDefend(Player currentPlayer, Player opponentPlayer){

            //player has a way to deflect the attack
            System.out.println(String.format("Avoid the attack or take the damage... ('take'/1/%d)\r\n", opponentPlayer.getDamage()));
            String decision = getPlayersDistinctInput(opponentPlayer.getDamage(), currentPlayer);

            if (decision.equalsIgnoreCase("take")) {
                //player wants to take damage
                currentPlayer.takeDamage(opponentPlayer.getDamage());
                System.out.println("Ohhh you've taken damage... Health: " + currentPlayer.getHealth() + "\r\n");
            }
            if (decision.equalsIgnoreCase("1")){
                //player doesn't take damage this turn - uses protect card
                currentPlayer.playCard(Integer.parseInt(decision));
            }
            if (decision.equalsIgnoreCase(Integer.toString(opponentPlayer.getDamage()))) {
                //player doesn't take damage this turn - uses special ability of attacking card
                currentPlayer.playCardInDefense(Integer.parseInt(decision));
            }
    }

    public String getPlayersDistinctInput(int opponentDamage, Player currentPlayer) {
        
        String input;
    
        do {
            input = scanner.nextLine();
        } while (!input.equalsIgnoreCase("take") && !input.equals("1") && !(input.equals(Integer.toString(opponentDamage)) && currentPlayer.findNumberInHand(Integer.parseInt(input))));
    
        return input;
    }

    public void testCommandStartGameWithDeckAndHandSizeAtZero() {

        player1.getDeck().clear();
        player1.getHand().clear();

        while (!getGameEnded()) {


            //HP check for player 1
            if (!hasHealth(player1)) {
                System.out.println("Player 2 wins!");
                gameEnded = true;
                break;
            }

            // Player 1's turn
            System.out.println("Player 1's Turn");
            if (isPlayerWithoutOptionsToPlay(player1)) {
                System.out.println("You lost all your cards... \r\n Player 2 wins!");
                gameEnded = true;
                break;
            }

            playTurn(player1, player2);

//
//            //HP check player 2
//
//            if (!hasHealth(player2)) {
//                System.out.println("Player 1 wins!");
//                gameEnded = true;
//                break;
//            }
//
//            // Player 2's turn
//            System.out.println("Player 2's Turn");
//            if (isPlayerWithoutOptionsToPlay(player2)) {
//                System.out.println("You lost all your cards... \r\n Player 1 wins!");
//                gameEnded = true;
//                break;
//            }
//
//            playTurn(player2, player1);
        }
    }
    
}
