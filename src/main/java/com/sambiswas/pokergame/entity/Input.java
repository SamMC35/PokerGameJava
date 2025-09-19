package com.sambiswas.pokergame.entity;

import com.sambiswas.pokergame.enums.InputType;
import lombok.Data;

@Data
public class Input {
    private long id;
    private InputType inputType;
    private int raiseValue;
}
