package test;

import game.Game;
import org.junit.Test;
import player.Player;
import player.Shuffler;
import utility.Utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGame {

    private Player player1;
    private Player player2;

    private void instantiatePlayers() {
        new Utility();
        player1 = new Player(20, Utility.generateCards(), new Shuffler());
        player1.populateDeck(player1.getShuffler().shuffleDeck(player1.getDeck()));

        player2 = new Player(20, Utility.generateCards(), new Shuffler());
        player2.populateDeck(player2.getShuffler().shuffleDeck(player2.getDeck()));
    }



    @Test
    public void testGameEndsWhenPlayerHealthDropsToZero() {

        instantiatePlayers();
        Game game = new Game(player1, player2);



    }

}
