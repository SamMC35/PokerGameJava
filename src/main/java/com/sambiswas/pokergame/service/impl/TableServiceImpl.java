package com.sambiswas.pokergame.service.impl;

import com.sambiswas.pokergame.dto.PlayerDTO;
import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.entity.Table;
import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.PlayerState;
import com.sambiswas.pokergame.enums.TableState;
import com.sambiswas.pokergame.exception.PokerGameException;
import com.sambiswas.pokergame.service.DeckService;
import com.sambiswas.pokergame.service.HandService;
import com.sambiswas.pokergame.service.PlayerService;
import com.sambiswas.pokergame.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class TableServiceImpl implements TableService {
    public static final int SMALL_BLIND = 5;
    public static final int BIG_BLIND = 10;
    private final Table table;
    private boolean isTableInitiated;

    @Autowired
    private DeckService deckService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private HandService handService;

    public TableServiceImpl(){
        table = new Table(0, new ArrayList<>(), 0,
                TableState.WAITING_FOR_PLAYERS, null, false, true, false);
        isTableInitiated = false;
    }

    @Override
    public void resetTable() {
        table.setTableCards(new ArrayList<>());
        table.setPot(0);
        table.setTableState(TableState.PRE_FLOP);
        table.setWinner(null);
        table.setShowDowned(false);

        PlayerDTO playerDTO = playerService.changeDealerForAnotherRound();

        playerDTO.getSmallBlind().setWallet(playerDTO.getSmallBlind().getWallet() - SMALL_BLIND);
        playerDTO.getSmallBlind().setBet(SMALL_BLIND);

        addPot(SMALL_BLIND);

        playerDTO.getBigBlind().setWallet(playerDTO.getBigBlind().getWallet() - BIG_BLIND);
        playerDTO.getBigBlind().setBet(BIG_BLIND);

        addPot(BIG_BLIND);

        isTableInitiated = true;
    }

    @Override
    public Table getTableInfo() {
        return table;
    }

    @Override
    public void addPot(int value) {
        int pot = table.getPot();
        table.setPot(pot + value);

        if(table.getMaxBet() < value){
            table.setMaxBet(value);
        }
    }

    @Override
    public int fetchMaxBet() {
        return table.getMaxBet();
    }

    @Override
    public boolean isTableInitiated() {
        return isTableInitiated;
    }

    @Override
    public void processTable() {
        //Check if state can be switched
        if (!table.isShowDowned()) {
            if(checkSwitchState()){
                switchStateForCardPlay();
            }
            if(!table.isTableProcessed()) {
                processState();
            }
        }
    }

    @Override
    public List<Player> getWinner() {
        return table.getWinner();
    }

    private void switchStateForCardPlay() {
        switch(table.getTableState()){
            case PRE_FLOP -> table.setTableState(TableState.FLOP);
            case FLOP -> table.setTableState(TableState.TURN);
            case TURN -> table.setTableState(TableState.RIVER);
            case RIVER -> table.setTableState(TableState.SHOWDOWN);
            default -> throw new PokerGameException("Invalid state for card play: " + table.getTableState());
        }

        table.setTableProcessed(false);
    }

    private void processState() {
        switch(table.getTableState()){
            case PRE_FLOP :
                break;
            case FLOP:
                IntStream.range(1, 4).forEach(i -> table.getTableCards().add(deckService.returnOneCard()));
                break;
            case TURN, RIVER:
                table.getTableCards().add(deckService.returnOneCard());
                break;
            case SHOWDOWN:
                determineWinner();
                table.getWinner().forEach(winner -> winner.setWallet(winner.getWallet() + (table.getPot()/table.getWinner().size())));
                table.setShowDowned(true);
                break;
            default:
                throw new PokerGameException("Invalid state: " + table.getTableState());
        }

        table.setTableProcessed(true);
    }

    private void determineWinner() {
        List<Player> playerList = playerService.returnPlayerList().stream()
                .filter(player -> player.getPlayerState() != PlayerState.FOLDED).toList();

        System.out.println("PlayerList before showdown: " + playerList);

        for(Player player : playerList){
            List<Card> playerCards = player.getCardList();

            List<List<Card>> communityCardsCombos = deckService.generateCombinations(table.getTableCards());

            List<Hand> playerHands = new ArrayList<>();

            for(List<Card> communityCards : communityCardsCombos){
                List<Card> tempCards = new ArrayList<>(playerCards);
                tempCards.addAll(communityCards);

                playerHands.add(handService.calculateHand(tempCards));
            }

            player.setHand(playerHands.stream().max(Comparator.comparingInt(Hand::getRank)).orElse(Hand.NONE));
        }

        Hand winningHand = playerList.stream()
                .map(Player::getHand)
                .max(Comparator.comparingInt(Hand::getRank))
                .orElse(null);
        table.setWinner(playerList.stream().filter(player -> player.getHand().equals(winningHand)).toList());
    }



    private boolean checkSwitchState() {
        //Check if all the players have folded or done some activity
        return playerService.checkIfStateCanBeChangedForCards(table.getMaxBet());
    }
}
