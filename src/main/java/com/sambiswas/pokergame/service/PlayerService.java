package com.sambiswas.pokergame.service;

import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.enums.InputType;

import java.util.List;

public interface PlayerService {
    void setPlayerList(List<Player> playerList);

    boolean checkIfStateCanBeChanged(int maxBet);
    void processPlayerInput(Player player, InputType inputType);
    void distributeCards();
    void getPlayerById();
    void resetPlayers();
    void setDealer();

    Player getDealer();
}
