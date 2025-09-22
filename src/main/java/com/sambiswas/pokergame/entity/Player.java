package com.sambiswas.pokergame.entity;

import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.PlayerPerk;
import com.sambiswas.pokergame.enums.PlayerState;
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
    private List<Card> cardList = new ArrayList<>();

    private PlayerPerk playerPerk = PlayerPerk.NONE;

    private String password;

    public Player(String name){
        this.name = name;
    }
}
