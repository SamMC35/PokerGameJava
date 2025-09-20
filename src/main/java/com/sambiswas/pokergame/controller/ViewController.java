package com.sambiswas.pokergame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/server")
    public String getServer(){
        return "server";
    }

    @GetMapping("/client")
    public String getClient(){
        return "client";
    }

    @GetMapping("/waiting")
    public String getWaiting(){
        return "waiting";
    }

    @GetMapping("/playerTerminal")
    public String getPlayerTerminal(){
        return "playerTerminal";
    }

    @GetMapping("/table")
    public String getTable(){
        return "table";
    }
}
