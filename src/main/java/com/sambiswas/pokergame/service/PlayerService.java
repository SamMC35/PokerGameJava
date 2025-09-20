package com.sambiswas.pokergame.service;

import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.enums.InputType;

import java.util.List;

public interface PlayerService {

    long addPlayer(Player player);
    List<Player> returnPlayerList();

    boolean checkIfStateCanBeChangedForCards(int maxBet);
    void processPlayerInput(Player player, InputType inputType, int maxBet, int raiseValue);
    void distributeCards();
    Player getPlayerById(long id);
    void resetPlayers();
    void setDealer();

    Player getDealer();
}
