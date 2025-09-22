package com.sambiswas.pokergame;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.entity.Table;
import com.sambiswas.pokergame.enums.TableState;
import com.sambiswas.pokergame.service.DeckService;
import com.sambiswas.pokergame.service.PlayerService;
import com.sambiswas.pokergame.service.TableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class TableServiceTest {

    @Autowired
    private TableService tableService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private DeckService deckService;

    @Test
    void winnerTest(){
        //Init decks
        deckService.initDeck();
        deckService.shuffleDeck();

        List<String> names = List.of("Sam", "Arko", "Bittu");

        for(String name : names) {
            Player player = new Player(name);
            IntStream.range(1, 3).forEach(i -> player.getCardList().add(deckService.returnOneCard()));
            playerService.addPlayer(player);
        }

        List<Card> tableCards = new ArrayList<>();
        IntStream.range(1, 4).forEach(i -> tableCards.add(deckService.returnOneCard()));

        Table tableInfo = tableService.getTableInfo();
        tableInfo.setTableCards(tableCards);
        tableInfo.setTableProcessed(false);
        tableInfo.setTableState(TableState.SHOWDOWN);

        tableService.processTable();
        System.out.println("Winner:" + tableService.getWinner());
    }


}
