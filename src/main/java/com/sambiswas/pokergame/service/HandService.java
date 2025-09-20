package com.sambiswas.pokergame.service;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.enums.Hand;

import java.util.List;

public interface HandService {
    Hand calculateHand(List<Card> cardList);
}
