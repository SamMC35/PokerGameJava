package com.sambiswas.pokergame.entity;

import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.PlayerState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Player {
    public static long idCounter = 0;

    private long id;
    private String name;
    private int bet;
    private int wallet;
    private Hand hand = Hand.NONE;
    private PlayerState playerState = PlayerState.WAITING;
    private boolean isCurrentPlayer = false;
    private List<Card> cardList;

}
