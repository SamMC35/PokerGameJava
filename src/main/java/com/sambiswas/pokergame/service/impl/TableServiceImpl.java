package com.sambiswas.pokergame.service.impl;

import com.sambiswas.pokergame.entity.Table;
import com.sambiswas.pokergame.enums.TableState;
import com.sambiswas.pokergame.exception.PokerGameException;
import com.sambiswas.pokergame.service.TableService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TableServiceImpl implements TableService {
    private final Table table;
    private final boolean isTableInitiated;

    public TableServiceImpl(){
        table = new Table(0, new ArrayList<>(), 0, TableState.WAITING_FOR_PLAYERS, false, false);
        isTableInitiated = false;
    }

    @Override
    public void resetTable() {
        table.setTableCards(new ArrayList<>());
        table.setPot(0);
        table.setMaxBet(0);
        table.setTableState(TableState.PRE_FLOP);
    }

    @Override
    public Table getTableInfo() {
        return table;
    }

    @Override
    public void addPot(int value) {
        int pot = table.getPot();
        table.setPot(pot + value);

        if(table.getMaxBet() > value){
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
        if(checkSwitchState()){
            switchStateForCardPlay();
        }
        if(!table.isTableProcessed()) {
            processState();
        }
    }

    private void switchStateForCardPlay() {
        switch(table.getTableState()){
            case PRE_FLOP -> table.setTableState(TableState.FLOP);
            case FLOP -> table.setTableState(TableState.TURN);
            case TURN -> table.setTableState(TableState.RIVER);
            case RIVER -> table.setTableState(TableState.SHOWDOWN);
            default -> throw new PokerGameException("Invalid state for card play: " + table.getTableState());
        }
    }

    private void processState() {
        switch(table.getTableState()){
            case PRE_FLOP :
                break;
            case FLOP:
                //TODO: Add deck Service

                break;
            case TURN, RIVER:
                //TODO: Add adding one card
                break;
            case SHOWDOWN:
                break;
        }

        table.setTableProcessed(true);
    }

    private boolean checkSwitchState() {
        //Check if all the players have folded or done some activity
        return false;
    }
}
