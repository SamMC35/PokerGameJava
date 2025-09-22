package com.sambiswas.pokergame;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.enums.*;
import com.sambiswas.pokergame.service.DeckService;
import com.sambiswas.pokergame.service.HandService;
import com.sambiswas.pokergame.service.PlayerService;
import com.sambiswas.pokergame.service.TableService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private DeckService deckService;

    @Autowired
    private HandService handService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TableService tableService;


    @Test
    public void test(){
        List<Card> playerCards = List.of(new Card(Suit.CLUBS, Rank.ACE), new Card(Suit.DIAMONDS, Rank.EIGHT));

        List<Card> tableCards = List.of(new Card(Suit.CLUBS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.KING),
                new Card(Suit.CLUBS, Rank.JACK),
                new Card(Suit.CLUBS, Rank.NINE),
                new Card(Suit.CLUBS, Rank.TEN));

        List<List<Card>> cardCombos = deckService.generateCombinations(tableCards);

        Map<List<Card>, Hand> handCardComboMap= cardCombos.stream()
                .map(cardCombo -> {
                    List<Card> fullCard = new ArrayList<>(cardCombo);
                    fullCard.addAll(playerCards);
                    return fullCard;
                })
                .collect(Collectors.toMap(combo -> combo, combo -> handService.calculateHand(combo)));

        System.out.println(handCardComboMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null));
    }

    @Test
    public void playerTest(){
        //Initiate poker game
        addPlayers();
        //Process table
    }

    private void addPlayers() {
        List<String> names = List.of("Sam", "Arko", "Bittu");

        for(String name : names) {
            playerService.addPlayer(new Player(name));
        }

        deckService.initDeck();
        deckService.shuffleDeck();
        tableService.resetTable();
        playerService.resetPlayersForNextGame();
        playerService.resetPlayersForNextBettingRound();
    }

    @Test
    void noRaiseTest(){
        addPlayers();

        List<Player> players = playerService.returnPlayerList();
        System.out.println("Players after init: " + players);
        System.out.println("Table after init: " + tableService.getTableInfo());

        int maxBet = tableService.fetchMaxBet();

        do{
            Player player = playerService.getCurrentPlayer();
            tableService.addPot(playerService.processPlayerInput(player, InputType.CALLING, maxBet, 0));
            tableService.processTable();
        }while(!tableService.getTableInfo().isShowDowned());

        System.out.println("Winner: " + tableService.getWinner() + " Table Cards: " + tableService.getTableInfo().getTableCards());

    }

    @Test
    void oneFoldTest(){
        addPlayers();

        int maxBet = tableService.fetchMaxBet();

        do{
            Player player = playerService.getCurrentPlayer();
            InputType inputType = player.getName().equals("Sam") ? InputType.FOLDING : InputType.CALLING;
            tableService.addPot(playerService.processPlayerInput(player, inputType, maxBet, 0));
            tableService.processTable();
        }while(!tableService.getTableInfo().isShowDowned());

        System.out.println("Winner: " + tableService.getWinner() + " Table Cards: " + tableService.getTableInfo().getTableCards());
    }

    @Test
    void raiseTest(){
        addPlayers();

        boolean hasRaised = false;

        do {
            Player player = playerService.getCurrentPlayer();

            var bet = 0;

            if(!hasRaised && player.getName().equals("Bittu")){
                bet = playerService.processPlayerInput(player, InputType.RAISING, tableService.fetchMaxBet(), 10);
                hasRaised = true;
            } else {
                bet = playerService.processPlayerInput(player, InputType.CALLING, tableService.fetchMaxBet(), 0);
            }
            tableService.addPot(bet);
            tableService.processTable();
        }while(!tableService.getTableInfo().isShowDowned());

        System.out.println("Winner: " + tableService.getWinner() + " Table cards: " + tableService.getTableInfo().getTableCards());
    }

    @Test
    void allInTest(){
        addPlayers();

        boolean hasRaised = false;

        do {
            Player player = playerService.getCurrentPlayer();

            var bet = 0;

            if(!hasRaised && player.getName().equals("Bittu")){
                bet = playerService.processPlayerInput(player, InputType.ALL_IN, tableService.fetchMaxBet(), 10);
                hasRaised = true;
            } else {
                bet = playerService.processPlayerInput(player, InputType.CALLING, tableService.fetchMaxBet(), 0);
            }
            tableService.addPot(bet);
            tableService.processTable();
        }while(!tableService.getTableInfo().isShowDowned());

        System.out.println("Winner: " + tableService.getWinner() + " Table cards: " + tableService.getTableInfo().getTableCards());
    }

}
