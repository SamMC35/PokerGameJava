package com.sambiswas.pokergame.service;

import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.entity.Table;

import java.util.List;

public interface TableService {
    void resetTable();
    Table getTableInfo();
    void addPot(int value);
    int fetchMaxBet();
    boolean isTableInitiated();
    void processTable();

    Player getWinner();
}
