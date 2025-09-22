package com.sambiswas.pokergame.enums;

public enum InputType {
    CALLING("calling"),
    RAISING("raising"),
    FOLDING("folding"),
    ALL_IN("all_in");

    private final String action;

    InputType(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    // Optional: reverse lookup
    public static InputType fromAction(String action) {
        for (InputType it : InputType.values()) {
            if (it.action.equals(action)) {
                return it;
            }
        }
        throw new IllegalArgumentException("Invalid action: " + action);
    }
}
