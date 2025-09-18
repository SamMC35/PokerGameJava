package com.sambiswas.pokergame.enums;

public enum PlayerState {
    WAITING("waiting"),
    CALLED("calling"),
    FOLDED("folding"),
    RAISED("raised"),
    WAIT_FOR_RAISE("waitingForRaise");

    private final String state;

    PlayerState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    // Optional: reverse lookup
    public static PlayerState fromState(String state) {
        for (PlayerState ps : PlayerState.values()) {
            if (ps.state.equals(state)) {
                return ps;
            }
        }
        throw new IllegalArgumentException("Invalid state: " + state);
    }
}
