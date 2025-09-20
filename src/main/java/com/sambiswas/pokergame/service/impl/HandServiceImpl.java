package com.sambiswas.pokergame.service.impl;

import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.enums.Hand;
import com.sambiswas.pokergame.enums.Rank;
import com.sambiswas.pokergame.exception.PokerGameException;
import com.sambiswas.pokergame.service.HandService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HandServiceImpl implements HandService {
    @Override
    public Hand calculateHand(List<Card> cardList) {
        cardList = cardList.stream().sorted(Comparator.comparing(Card::getRank)).toList();

        if(cardList.size() != 5){
            throw new PokerGameException("Card List not 5");
        }

        //Check straight/royal flush
        if(checkFlush(cardList) && checkStraight(cardList)){
            if(cardList.getFirst().getRank() == Rank.TEN && cardList.getLast().getRank() == Rank.ACE){
                return Hand.ROYAL_FLUSH;
            } else {
                return Hand.STRAIGHT_FLUSH;
            }
        }

        //Check four of a kind
        if(checkFourOfAKind(cardList)){
            return Hand.FOUR_OF_A_KIND;
        }

        //Check full house
        if(checkFullHouse(cardList)){
            return Hand.FULL_HOUSE;
        }

        //Check flush
        if(checkFlush(cardList)){
            return Hand.FLUSH;
        }

        //Check straight
        if(checkStraight(cardList)){
            return Hand.STRAIGHT;
        }

        //Check three of a kind
        if(checkThreeOfAKind(cardList)){
            return Hand.THREE_OF_A_KIND;
        }

        //Check One Pair and Two pair
        long pairs = getPairs(cardList);

        if(pairs > 0){
            if(pairs == 2){
                return Hand.TWO_PAIR;
            } else if (pairs == 1) {
                return Hand.ONE_PAIR;
            }
        }
        //Check high Card
        return Hand.HIGH_CARD;
    }

    private boolean checkFullHouse(List<Card> cardList) {
        Map<Rank, Long> counts = cardList.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

        return counts.containsValue(3L) && counts.containsValue(2L);
    }

    private boolean checkFourOfAKind(List<Card> cardList) {
        return cardList.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()))
                .values().stream().anyMatch(count -> count == 4);
    }

    private boolean checkFlush(List<Card> cardList) {
        return cardList.stream().allMatch(card -> card.getSuit().equals(cardList.getFirst().getSuit()));
    }

    private boolean checkStraight(List<Card> cardList) {
        List<Integer> values = cardList.stream()
                .map(c -> c.getRank().getValue())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        if(values.contains(14) && values.contains(2)){
            values.remove(Integer.valueOf(14));
            values.addFirst(1);
        }

        if(values.size() != 5){
            return false;
        }

        return values.getLast() - values.getFirst() == 4;
    }

    private boolean checkThreeOfAKind(List<Card> cardList) {
        return cardList.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()))
                .values().stream().anyMatch(count -> count == 3);
    }

    private long getPairs(List<Card> cardList) {
        return cardList.stream()
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()))
                .values().stream()
                .filter(count -> count == 2)
                .count();
    }
}
