package com.sambiswas.pokergame.dto;


import com.sambiswas.pokergame.entity.Card;
import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.enums.TableState;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TableDTO {
    private int pot;
    private TableState tableState;
    private List<Card> tableCards;

    private List<Player> playerList;
}
