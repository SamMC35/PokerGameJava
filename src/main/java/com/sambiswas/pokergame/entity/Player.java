package com.sambiswas.pokergame.entity;

import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.PlayerState;
import com.sambiswas.pokergame.enums.Rank;
import com.sambiswas.pokergame.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private long id;
    private String name;
    private int bet = 0;
    private int wallet = 1500;
    private Hand hand = Hand.NONE;
    private PlayerState playerState = PlayerState.WAITING;
    private boolean isCurrentPlayer = false;
    private List<Card> cardList = List.of(new Card(Suit.CLUBS, Rank.EIGHT), new Card(Suit.DIAMONDS, Rank.FIVE));

    private String password;

}
