package com.nikhil.tokenApp.controller;


import com.nikhil.tokenApp.domain.TokenRequest;
import com.nikhil.tokenApp.domain.TokenResponse;
import com.nikhil.tokenApp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/metro/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;


    @GetMapping("/getAll")
    public List<TokenResponse> getAllTokens (){
        System.out.println("/token/getAll api called");
        List<TokenResponse> listAll = tokenService.getAllTokens();
        System.out.println("alltokens list" + listAll);
        return listAll;
    }

    @GetMapping("/getPrice")
    public TokenRequest getTokenPrice (@RequestParam Integer fromStationId, @RequestParam Integer toStationId){
        System.out.println("TokenRequest called, fromStationId: " + fromStationId + " , toStationId: " + toStationId);
        return tokenService.getTokenPrice(fromStationId, toStationId);
    }

    @PostMapping("/generate")
    public TokenResponse createNewToken (@RequestBody TokenRequest tokenRequest){
        TokenResponse tokenResponse = new TokenResponse();
        System.out.println("tokenRequest: " + tokenRequest);
        System.out.println("fromStationId "+ tokenRequest.getFromStationId());
        return tokenService.generateToken(tokenRequest);
    }
}
