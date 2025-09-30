package com.sambiswas.pokergame.service;

import com.sambiswas.pokergame.dto.PlayerDTO;
import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.enums.InputType;

import java.util.List;

public interface PlayerService {

    long addPlayer(Player player);
    List<Player> returnPlayerList();

    boolean checkIfStateCanBeChangedForCards(int maxBet);
    int processPlayerInput(Player player, InputType inputType, int maxBet, int raiseValue);
    void distributeCards();
    Player getPlayerById(long id);
    void resetPlayersForNextBettingRound();

    void resetPlayersForNextGame();

    Player getDealer();
    Player getCurrentPlayer();

    PlayerDTO changeDealerForAnotherRound();

    boolean checkIfSolePlayerExists();

    Player getSoloWinner();
}
