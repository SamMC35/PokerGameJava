package com.sambiswas.pokergame.service;

import java.util.List;

public interface NotificationService {
    List<String> getLast5Messages();
    void addMessage(String message);
}
