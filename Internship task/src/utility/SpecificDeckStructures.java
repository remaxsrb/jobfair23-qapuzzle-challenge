package utility;

import cards.AttackCard;
import cards.BoostAttackCard;
import cards.Card;
import cards.ProtectCard;

import java.util.ArrayList;
import java.util.List;

public class SpecificDeckStructures {

    public static List<Card> getAttackStructure()
    {
        List<Card> deck = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            deck.add(new ProtectCard());
        }
        for (int i = 0; i < 10; i++) {
            deck.add(new BoostAttackCard());
        }

        for (int i = 3; i <= 7; i++) {
            deck.add(new AttackCard(i));

        }
        for (int i = 3; i <= 7; i++) {
            deck.add(new AttackCard(i));

        }

        return deck;
    }

    public static List<Card> getBoostStructure() {

        List<Card> deck = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            deck.add(new ProtectCard());
        }

        for (int i = 3; i <= 7; i++) {
            deck.add(new AttackCard(i));
            deck.add(new AttackCard(i));

        }

        for (int i = 0; i < 10; i++) {
            deck.add(new BoostAttackCard());
        }

        return deck;

    }

    public static List<Card> getDefenceStructure() {
        List<Card> deck = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            deck.add(new BoostAttackCard());
        }

        for (int i = 3; i <= 7; i++) {
            deck.add(new AttackCard(i));
            deck.add(new AttackCard(i));
        }
        for (int i = 0; i < 5; i++) {
            deck.add(new ProtectCard());
        }
        return deck;
    }

    public static List<Card> getBalancedStructure() {
        List<Card> deck = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            deck.add(new BoostAttackCard());
        }

        for (int i = 0; i < 5; i++) {
            deck.add(new ProtectCard());
        }

        for (int i = 3; i <= 7; i++) {
            deck.add(new AttackCard(i));
            deck.add(new AttackCard(i));
        }

        deck.add(new BoostAttackCard());
        deck.add(new BoostAttackCard());

        return deck;
    }

}
