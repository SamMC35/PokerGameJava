package com.sambiswas.pokergame.enums;

public enum PlayerState {
    WAITING("waiting"),
    CALLED("calling"),
    FOLDED("folding"),
    RAISED("raised"),
    ALL_INNED("all_inned");

    PlayerState(String state) {
    }

}
