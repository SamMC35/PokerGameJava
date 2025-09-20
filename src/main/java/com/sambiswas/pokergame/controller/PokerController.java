package com.sambiswas.pokergame.controller;

import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.entity.Table;
import com.sambiswas.pokergame.service.PlayerService;
import com.sambiswas.pokergame.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/getPlayers")
    public List<Player> returnPlayerList(){
        return playerService.returnPlayerList();
    }

    @PostMapping("/addPlayers")
    public Map<String, Long> addPlayer(@RequestBody Player player){
        return Map.of("id", playerService.addPlayer(player));
    }

    @GetMapping("getPlayer/{id}")
    public Player getPlayerById(@PathVariable long id){
        return playerService.getPlayerById(id);
    }

    @GetMapping("/hasGameStarted")
    public Map<String, Boolean> hasGameInitiated(){
        return Map.of("tableInitiated", tableService.isTableInitiated());
    }

    @PostMapping("/startGame")
    public void startGame(){
        tableService.resetTable();
        playerService.resetPlayers();
    }


}
