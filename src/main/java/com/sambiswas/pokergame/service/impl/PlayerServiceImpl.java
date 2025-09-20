package com.sambiswas.pokergame.service.impl;

import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.enums.InputType;
import com.sambiswas.pokergame.enums.PlayerState;
import com.sambiswas.pokergame.exception.PokerGameException;
import com.sambiswas.pokergame.service.DeckService;
import com.sambiswas.pokergame.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;


@Component
public class PlayerServiceImpl implements PlayerService {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private DeckService deckService;

    List<Player> playerList;
    Player dealer;


    public PlayerServiceImpl(){
        playerList = new ArrayList<>();
    }


    @Override
    public long addPlayer(Player player) {
        player.setId(counter.incrementAndGet());
        playerList.add(player);
        return player.getId();
    }

    @Override
    public List<Player> returnPlayerList() {
        return playerList;
    }

    @Override
    public boolean checkIfStateCanBeChangedForCards(int maxBet) {
        return playerList.stream()
                .filter(player -> player.getPlayerState() != PlayerState.FOLDED)
                .allMatch(player -> player.getPlayerState() != PlayerState.WAITING && player.getBet() == maxBet);
    }

    @Override
    public void processPlayerInput(Player player, InputType inputType, int maxBet, int raiseValue) {
        int bet = 0;

        switch(inputType){
            case InputType.CALLING:
                bet = maxBet - player.getBet();
                player.setWallet(player.getWallet() - bet);
                player.setBet(bet);
                player.setPlayerState(PlayerState.CALLED);
                break;
            case InputType.FOLDING:
                player.setPlayerState(PlayerState.FOLDED);
                break;
            case InputType.RAISING:
                bet = (maxBet + raiseValue);
                player.setBet(bet);
                player.setWallet(player.getWallet() - bet);
                player.setPlayerState(PlayerState.RAISED);
                break;
            default:
                throw new PokerGameException("Invalid input type: " + inputType);
        }
    }

    @Override
    public void distributeCards() {
        IntStream.range(1,2).forEach(i -> playerList.forEach(player -> player.getCardList().add(deckService.returnOneCard())));
    }

    @Override
    public Player getPlayerById(long id) {
        return playerList.stream().filter(player -> player.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void resetPlayers() {
        playerList.stream().filter(player -> player.getPlayerState() != PlayerState.FOLDED).forEach(player -> player.setPlayerState(PlayerState.WAITING));
    }

    @Override
    public void setDealer() {
        playerList.add(playerList.removeFirst());
        dealer = playerList.getFirst();
    }

    @Override
    public Player getDealer() {
        return dealer;
    }
}
