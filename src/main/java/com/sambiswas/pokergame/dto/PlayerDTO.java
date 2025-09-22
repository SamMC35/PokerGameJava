package com.sambiswas.pokergame.dto;

import com.sambiswas.pokergame.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDTO {
    private Player dealer;
    private Player smallBlind;
    private Player bigBlind;
}
