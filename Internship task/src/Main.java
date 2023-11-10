import java.util.List;
import cards.*;
import game.Game;
import player.Player;
import player.Shuffler;
import utility.Utility;


public class Main {


public static void main(String[] args) {
        new Utility();
        // Create cards for player 1's deck
        List<Card> player1Deck = Utility.generateCards();
   
        // Create cards for player 2's deck
        List<Card> player2Deck = Utility.generateCards();

        Player player1 = new Player(20,player1Deck);
        player1.populateDeck(player1.getShuffler().shuffleDeck(player1.getDeck()));

        Player player2 = new Player(20,player2Deck);
        player2.populateDeck(player2.getShuffler().shuffleDeck(player2.getDeck()));

        // Create a game instance

        
        Game game = new Game(player1, player2);
        game.startGame();

    }
}