package com.sambiswas.pokergame.service.impl;

import com.sambiswas.pokergame.dto.PlayerDTO;
import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.InputType;
import com.sambiswas.pokergame.enums.PlayerPerk;
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

    List<Player> mutablePlayerList;
    List<Player> dealerPlayerList;

    List<Player> ogPlayerList;
    Player dealer;


    public PlayerServiceImpl(){
        mutablePlayerList = new ArrayList<>();
        dealerPlayerList = new ArrayList<>();

        ogPlayerList = new ArrayList<>();
    }


    @Override
    public long addPlayer(Player player) {
        player.setId(counter.incrementAndGet());
        mutablePlayerList.add(player);
        dealerPlayerList.add(player);
        ogPlayerList.add(player);
        return player.getId();
    }

    @Override
    public List<Player> returnPlayerList() {
        ogPlayerList.forEach(ogPlayer -> {
            ogPlayer.setCurrentPlayer(ogPlayer.equals(mutablePlayerList.getFirst()));
        });
        return ogPlayerList;
    }

    @Override
    public boolean checkIfStateCanBeChangedForCards(int maxBet) {
        return mutablePlayerList.stream()
                .filter(player -> player.getPlayerState() != PlayerState.FOLDED)
                .allMatch(player -> (player.getPlayerState() != PlayerState.WAITING && player.getBet() == maxBet) ||
                        player.getPlayerState() == PlayerState.ALL_INNED);
    }

    @Override
    public int processPlayerInput(Player player, InputType inputType, int maxBet, int raiseValue) {
        int bet = 0;

        System.out.println("Processing player Input: " + inputType + " Player:" + player);

        if(!player.equals(mutablePlayerList.getFirst())){
            throw new PokerGameException("Not current player");
        }

        switch(inputType){
            case InputType.CALLING:
                bet = maxBet - player.getBet();
                player.setWallet(player.getWallet() - bet);
                player.setBet(player.getBet() + bet);
                player.setPlayerState(PlayerState.CALLED);
                break;
            case InputType.FOLDING:
                player.setPlayerState(PlayerState.FOLDED);
                break;
            case InputType.RAISING:
                bet = (maxBet + raiseValue) - player.getBet();
                player.setBet(player.getBet() + bet);
                player.setWallet(player.getWallet() - bet);
                player.setPlayerState(PlayerState.RAISED);
                break;
            case InputType.ALL_IN:
                bet = player.getWallet();
                player.setBet(player.getBet() + bet);
                player.setWallet(0);
                player.setPlayerState(PlayerState.ALL_INNED);
                break;
            default:
                throw new PokerGameException("Invalid input type: " + inputType);
        }

        changeCurrentPlayer();

        return bet;
    }

    @Override
    public void distributeCards() {
        IntStream.range(1,2).forEach(i -> mutablePlayerList.forEach(player -> player.getCardList().add(deckService.returnOneCard())));
    }

    @Override
    public Player getPlayerById(long id) {
        return mutablePlayerList.stream().filter(player -> player.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void resetPlayersForNextBettingRound() {
        mutablePlayerList.stream()
                .filter(player -> player.getPlayerState() != PlayerState.FOLDED)
                .forEach(player -> player.setPlayerState(PlayerState.WAITING));
    }

    @Override
    public void resetPlayersForNextGame(){
        mutablePlayerList
                .forEach(player -> {
                    player.setCardList(new ArrayList<>());
                    IntStream.range(1, 3).forEach(i -> {
                        player.getCardList().add(deckService.returnOneCard());
                        player.setPlayerState(PlayerState.WAITING);
                        player.setBet(0);
                        player.setPlayerPerk(PlayerPerk.NONE);
                        player.setHand(Hand.NONE);
                    });
                });
    }

    @Override
    public Player getDealer() {
        return dealer;
    }

    @Override
    public Player getCurrentPlayer() {
        return mutablePlayerList.getFirst();
    }

    @Override
    public PlayerDTO changeDealerForAnotherRound() {
        dealerPlayerList.add(dealerPlayerList.removeFirst());

        dealer = dealerPlayerList.get(0);

        return new PlayerDTO(dealer, dealerPlayerList.get(1), dealerPlayerList.get(2));
    }

    @Override
    public boolean checkIfSolePlayerExists() {
        return mutablePlayerList.stream().filter(player -> player.getPlayerState() != PlayerState.FOLDED).toList().size() == 1;
    }

    @Override
    public Player getSoloWinner() {
        return mutablePlayerList.stream().filter(player -> player.getPlayerState() != PlayerState.FOLDED).toList().getFirst();
    }

    private void changeCurrentPlayer(){
        do {
            mutablePlayerList.add(mutablePlayerList.removeFirst());
        } while(mutablePlayerList.getFirst().getPlayerState() == PlayerState.FOLDED);
    }
}
