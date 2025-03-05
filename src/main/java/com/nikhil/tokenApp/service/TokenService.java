package com.nikhil.tokenApp.service;

import com.nikhil.tokenApp.domain.TokenRequest;
import com.nikhil.tokenApp.domain.TokenResponse;
import com.nikhil.tokenApp.entity.RatesEntity;
import com.nikhil.tokenApp.entity.StationEntity;
import com.nikhil.tokenApp.entity.TokenEntity;
import com.nikhil.tokenApp.mapper.RequestTokenMapper;
import com.nikhil.tokenApp.mapper.ResponseTokenMapper;
import com.nikhil.tokenApp.repository.RatesRepository;
import com.nikhil.tokenApp.repository.StationRepository;
import com.nikhil.tokenApp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.CDATASection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TokenService {


    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private RatesRepository ratesRepository;

    @Autowired
    private ResponseTokenMapper responseTokenMapper;

    @Autowired
    private RequestTokenMapper requestTokenMapper;

    @Autowired
    private StationRepository stationRepository;


    public List<TokenResponse> getAllTokens() {
        List<TokenEntity> allTokens = tokenRepository.findAll();
        List<TokenResponse> tokenResponses = allTokens.stream()
                .map(token -> {
                 TokenResponse response = responseTokenMapper.convertToTokenResponse(token);
                 System.out.println("TokenResponse after mapping: " + response);

                    if (response.getFromStationId() != null) {
                        StationEntity fromStationEntity = stationRepository.
                                                            findById(response.getFromStationId()).
                                                            orElse(null);
                        if (fromStationEntity != null) {
                            response.setFromStation(fromStationEntity.getStation());
                        } else {
                            response.setFromStation(null);
                        }
                    } else {
                        response.setFromStation(null);
                    }

                    if (response.getToStationId() != null) {
                        StationEntity toStationEntity = stationRepository.findById(response.getToStationId()).orElse(null);
                        if (toStationEntity != null) {
                            response.setToStation(toStationEntity.getStation());
                        } else {
                            response.setToStation(null);
                        }
                    } else {
                        response.setToStation(null);
                    }
                    System.out.println("TokenResponse after setting station names: " + response);
                    return response;

        })
                .collect(Collectors.toList());

        return tokenResponses;

    }


    public TokenRequest getTokenPrice(Integer fromStationId, Integer toStationId) {
        TokenRequest tokenPrice = new TokenRequest();
        tokenPrice.setFromStationId(fromStationId);
        tokenPrice.setToStationId(toStationId);
        if(fromStationId == toStationId){
            // has to return error
            tokenPrice.setPrice(0);
            return tokenPrice;
        }
        StationEntity stationFrom = stationRepository.findById(fromStationId).orElse(null);
        StationEntity stationTo = stationRepository.findById(toStationId).orElse(null);
        float distance = Math.abs(stationFrom.getRootDistance() - stationTo.getRootDistance());
        System.out.println("distance: "+ distance );
        List<RatesEntity> rates = ratesRepository.findAll();
        for(RatesEntity rate: rates){
            System.out.println("rate: " +rate);
            if(rate.getMinKm() <= distance && rate.getMaxKm() >=distance ){
                System.out.println("condition matched, price : " +rate.getPrice());
                tokenPrice.setPrice(rate.getPrice());
                break;
            }
        }
        return tokenPrice;
    }

    public TokenResponse generateToken(TokenRequest tokenRequest ){
        int fromStationId = tokenRequest.getFromStationId();
        int toStationId = tokenRequest.getToStationId();
        float price = tokenRequest.getPrice();
        TokenEntity saveToken = requestTokenMapper.convertToTokenEntity(tokenRequest);
        StationEntity stationFrom = stationRepository.findById(fromStationId).orElse(null);
        StationEntity stationTo = stationRepository.findById(toStationId).orElse(null);
        String stationFromName = stationFrom.getStation().replaceAll(" ", "").substring(0, 4);
        String stationToName = stationTo.getStation().replaceAll(" ", "").substring(0,4);
        LocalDateTime currentTime = LocalDateTime.now();
        Integer date = currentTime.getDayOfMonth();
        Integer month = currentTime.getMonthValue();
        Integer year = currentTime.getYear();
        Integer hour = currentTime.getHour();
        Integer minute = currentTime.getMinute();
        String dateTime = "" + (date < 10 ? "0" + date : date) +
                            (month < 10 ? "0" + month : month)  +
                            (hour < 10 ? "0" + hour : hour) +
                            (minute < 10 ? "0" + minute : minute);
        String tokenDate = "" + (date < 10 ? "0" + date : date) + "-" +
                                (month < 10 ? "0" + month : month) + "-" +
                                year;
        String tokenTime = "" + (hour < 10 ? "0" + hour : hour) + ":" +
                                (minute < 10 ? "0" + minute : minute);

        saveToken.setTokenNo(stationFromName + "To" + stationToName + dateTime);

        saveToken.setTokenStatus(0);
        saveToken.setExitPricePaid(0);
        saveToken.setGeneratedAt(LocalDateTime.now());
        TokenEntity savedToken = tokenRepository.save(saveToken);
        TokenResponse tokenResponse = responseTokenMapper.convertToTokenResponse(savedToken);
        tokenResponse.setTokenDate(tokenDate);
        tokenResponse.setTokenTime(tokenTime);
        tokenResponse.setFromStation(stationFrom.getStation());
        tokenResponse.setToStation(stationTo.getStation());
        tokenResponse.setTokenNote("Token will be valid only for three hours.");
        return tokenResponse;
    }
}
