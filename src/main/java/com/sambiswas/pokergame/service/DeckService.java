package com.sambiswas.pokergame.service;

import com.sambiswas.pokergame.entity.Card;

import java.util.List;

public interface DeckService {
    void initDeck();
    void shuffleDeck();
    Card returnOneCard();
    List<List<Card>> generateCombinations(List<Card> tableCards);
}
