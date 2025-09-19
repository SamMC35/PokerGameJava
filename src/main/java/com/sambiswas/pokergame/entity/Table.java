package com.sambiswas.pokergame.entity;

import com.sambiswas.pokergame.enums.TableState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Table {
    private int pot;
    private List<Card> tableCards;
    private int maxBet;
    private TableState tableState;

    private boolean isStateChanged;
    private boolean isTableProcessed;

    public void setTableState(TableState tableState){
        isStateChanged = true;
        this.tableState = tableState;
        isTableProcessed = false;
    }
}
