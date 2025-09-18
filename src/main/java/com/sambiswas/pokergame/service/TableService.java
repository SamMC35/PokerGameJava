package com.sambiswas.pokergame.service;

import com.sambiswas.pokergame.entity.Table;

public interface TableService {
    void resetTable();
    Table getTableInfo();
    void addPot(int value);
    int fetchMaxBet();
    boolean isTableInitiated();
    void processTable();
}
