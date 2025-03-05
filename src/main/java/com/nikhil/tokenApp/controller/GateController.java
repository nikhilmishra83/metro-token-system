package com.nikhil.tokenApp.controller;


import com.nikhil.tokenApp.domain.GateResponse;
import com.nikhil.tokenApp.service.GateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metro/gate")
public class GateController {

    @Autowired
    GateService gateService;

    @PostMapping("/getEntry")
    public GateResponse getEntry (@RequestParam Integer tokenId, @RequestParam Integer deviceId){
        GateResponse gateResponse = gateService.getEntry(tokenId, deviceId);
        return gateResponse;
    }
    @PostMapping("/getExit")
    public GateResponse getExit (@RequestParam Integer tokenId, @RequestParam Integer deviceId){
        GateResponse gateResponse = gateService.getExit(tokenId, deviceId);
        return gateResponse;
    }

}
