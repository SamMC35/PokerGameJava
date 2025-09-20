package com.sambiswas.pokergame;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.enums.Rank;
import com.sambiswas.pokergame.enums.Suit;
import com.sambiswas.pokergame.service.DeckService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DeckServiceTest {

    @Autowired
    private DeckService deckService;

    @Test
    void test(){
        System.out.println(deckService.generateCombinations(List.of(new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.KING),
                new Card(Suit.CLUBS, Rank.JACK),
                new Card(Suit.CLUBS, Rank.NINE),
                new Card(Suit.CLUBS, Rank.TEN))));
    }
}
