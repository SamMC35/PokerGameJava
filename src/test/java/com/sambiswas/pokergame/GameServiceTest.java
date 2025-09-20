package com.sambiswas.pokergame;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.Rank;
import com.sambiswas.pokergame.enums.Suit;
import com.sambiswas.pokergame.service.DeckService;
import com.sambiswas.pokergame.service.HandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private DeckService deckService;

    @Autowired
    private HandService handService;


    @Test
    public void test(){
        List<Card> playerCards = List.of(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.DIAMONDS, Rank.EIGHT));

        List<Card> tableCards = List.of(new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.KING),
                new Card(Suit.CLUBS, Rank.JACK),
                new Card(Suit.CLUBS, Rank.NINE),
                new Card(Suit.CLUBS, Rank.TEN));

        List<List<Card>> cardCombos = deckService.generateCombinations(tableCards);

        Map<List<Card>, Hand> handCardComboMap= cardCombos.stream()
                .map(cardCombo -> {
                    List<Card> fullCard = new ArrayList<>(cardCombo);
                    fullCard.addAll(playerCards);
                    return fullCard;
                })
                .collect(Collectors.toMap(combo -> combo, combo -> handService.calculateHand(combo)));

        System.out.println(handCardComboMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null));
    }
}
