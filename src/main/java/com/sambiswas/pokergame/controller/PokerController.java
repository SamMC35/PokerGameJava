package com.sambiswas.pokergame.controller;

import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.entity.Table;
import com.sambiswas.pokergame.service.PlayerService;
import com.sambiswas.pokergame.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PokerController {

    @Autowired
    private TableService tableService;

    @Autowired
    private PlayerService playerService;

    @GetMapping("/getTableInfo")
    public Table getTableInfo(){
        return tableService.getTableInfo();
    }

    @GetMapping("/returnPlayerList")
    public List<Player> returnPlayerList(){
        return playerService.returnPlayerList();
    }

    @PostMapping("/addPlayer")
    public void addPlayer(@RequestBody Player player){
        playerService.addPlayer(player);
    }
}
