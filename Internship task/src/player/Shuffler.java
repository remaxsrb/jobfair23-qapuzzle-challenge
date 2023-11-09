package player;

import cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Shuffler {

    public List<Card> shuffleDeck(List<Card> deck)
    {
        List<Card> shuffledDeck = new ArrayList<>();

        while(!deck.isEmpty()) {
            int index = (int) (Math.random() * (deck.size()-1));
            shuffledDeck.add(deck.remove(index));
        }

        return shuffledDeck;
    }

}
