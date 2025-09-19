package com.sambiswas.pokergame.service.impl;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.enums.Rank;
import com.sambiswas.pokergame.enums.Suit;
import com.sambiswas.pokergame.service.DeckService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DeckServiceImpl implements DeckService {
    private final List<Card> cardList = new ArrayList<>();

    @Override
    public void initDeck() {
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                cardList.add(new Card(suit, rank));
            }
        }
    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(cardList);
    }

    @Override
    public Card returnOneCard() {
        return cardList.removeFirst();
    }

    @Override
    public List<List<Card>> generateCombinations(List<Card> tableCards) {
        List<List<Card>> cardCombos = new ArrayList<>();
        gatherAllCombos(tableCards, new ArrayList<>(), cardCombos, 0);
        return cardCombos;
    }

    private void gatherAllCombos(List<Card> tableCards, List<Card> dummyList, List<List<Card>> cardCombos, int start) {
        if(dummyList.size() == 3){
            cardCombos.add(dummyList);
            return;
        }

        for(int i = start; i < cardCombos.size(); i++){
            dummyList.add(cardList.get(i));
            gatherAllCombos(tableCards, dummyList, cardCombos, i + 1);
            dummyList.removeLast();
        }
    }
}
