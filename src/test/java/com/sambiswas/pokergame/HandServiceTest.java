package com.sambiswas.pokergame;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.enums.Rank;
import com.sambiswas.pokergame.enums.Suit;
import com.sambiswas.pokergame.service.HandService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class HandServiceTest {
    @Autowired
    private HandService handService;

    @Test
    void test(){
        System.out.println(handService.calculateHand(List.of(new Card(Suit.CLUBS, Rank.EIGHT),
                new Card(Suit.DIAMONDS, Rank.EIGHT),
                new Card(Suit.HEARTS, Rank.EIGHT),
                new Card(Suit.SPADES, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.ACE))));

        System.out.println(handService.calculateHand(List.of(new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.KING),
                new Card(Suit.CLUBS, Rank.JACK),
                new Card(Suit.CLUBS, Rank.QUEEN),
                new Card(Suit.CLUBS, Rank.TEN))));

        System.out.println(handService.calculateHand(List.of(new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.KING),
                new Card(Suit.CLUBS, Rank.JACK),
                new Card(Suit.CLUBS, Rank.NINE),
                new Card(Suit.CLUBS, Rank.TEN))));

    }
}
