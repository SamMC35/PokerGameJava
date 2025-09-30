package com.sambiswas.pokergame.controller;

import com.sambiswas.pokergame.dto.TableDTO;
import com.sambiswas.pokergame.entity.Input;
import com.sambiswas.pokergame.entity.Player;
import com.sambiswas.pokergame.entity.Table;
import com.sambiswas.pokergame.service.PlayerService;
import com.sambiswas.pokergame.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class PokerController {

    @Autowired
    private TableService tableService;

    @Autowired
    private PlayerService playerService;

    @GetMapping("/getTableInfo")
    public TableDTO getTableInfo(){
        Table tableInfo = tableService.getTableInfo();

        return new TableDTO(tableInfo.getPot(), tableInfo.getTableState(), tableInfo.getTableCards(), playerService.returnPlayerList(), tableInfo.getWinner());
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

    @PostMapping("/processInput")
    public void processInput(@RequestBody Input input){
        int bet = playerService.processPlayerInput(getPlayerById(input.getId()), input.getInputType(), tableService.fetchMaxBet(), input.getRaiseValue());
        tableService.addPot(bet);
        tableService.processTable();
    }

    @PostMapping("/startGame")
    public void startGame(){
        if(playerService.returnPlayerList().size() < 3){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Need 3 or more players");
        }
        tableService.resetTable();
    }

    @GetMapping("/getWinner")
    public Player getWinner(){
        return tableService.getWinner();
    }

    @PostMapping("/resetGame")
    public void resetGame(){
        tableService.resetTable();
    }

}
