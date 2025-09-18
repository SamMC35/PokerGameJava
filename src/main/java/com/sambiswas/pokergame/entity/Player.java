package com.sambiswas.pokergame.entity;

import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.PlayerState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Player {
    private long id;
    private String name;
    private int bet;
    private int wallet;
    private Hand hand;
    private PlayerState playerState;
    private boolean isCurrentPlayer;
    private List<Card> cardList;
}
