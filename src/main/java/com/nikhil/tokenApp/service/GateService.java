package com.nikhil.tokenApp.service;


import com.nikhil.tokenApp.domain.GateResponse;
import com.nikhil.tokenApp.entity.DeviceEntity;
import com.nikhil.tokenApp.entity.RatesEntity;
import com.nikhil.tokenApp.entity.StationEntity;
import com.nikhil.tokenApp.entity.TokenEntity;
import com.nikhil.tokenApp.repository.DeviceRepository;
import com.nikhil.tokenApp.repository.RatesRepository;
import com.nikhil.tokenApp.repository.StationRepository;
import com.nikhil.tokenApp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GateService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    StationRepository stationRepository;

    @Autowired
    RatesRepository ratesRepository;


    public GateResponse getEntry(Integer tokenId, Integer deviceId) {
        GateResponse gateResponse = new GateResponse();
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId).orElse(null);
        if (isInvalidDevice(deviceEntity, 2)) {
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage(getDeviceErrorMessage(deviceEntity));
            return gateResponse;
        }
        Optional<TokenEntity> tokenOptional = tokenRepository.findById(tokenId);
        if(tokenOptional.isEmpty()){
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage("Token details not found for the tokenId "+ tokenId);
            return gateResponse;
        }
        TokenEntity tokenEntity = tokenOptional.get();
        if(tokenEntity.getTokenStatus() == 1 || tokenEntity.getTokenStatus() == 2){
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage("Token has already bean used for entry ");
            return gateResponse;
        }
        if(tokenEntity.getGeneratedAt().plusHours(3).isBefore(LocalDateTime.now())){
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage("Your token is expired, you can use your token only within 3 hours of buying it.");
            return gateResponse;
        }
        tokenEntity.setEntryStationId(deviceEntity.getStationId());
        tokenEntity.setEntryTime(LocalDateTime.now());
        tokenEntity.setTokenStatus(1);

        gateResponse.setAccessAllowed(true);
        gateResponse.setMessage("Your entry is registered");
        tokenRepository.save(tokenEntity);

        return gateResponse;
    }

    public GateResponse getExit(Integer tokenId, Integer deviceId){
        GateResponse gateResponse = new GateResponse();
        DeviceEntity deviceEntity = deviceRepository.findById(deviceId).orElse(null);
        if (isInvalidDevice(deviceEntity, 3)) {
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage(getDeviceErrorMessage(deviceEntity));
            return gateResponse;
        }
        Optional<TokenEntity> tokenOptional = tokenRepository.findById(tokenId);
        if(tokenOptional.isEmpty()){
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage("Token details not found for the tokenId "+ tokenId);
            return gateResponse;
        }
        TokenEntity tokenEntity = tokenOptional.get();
        if (tokenEntity.getTokenStatus() == 2) {
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage("Token has already been used for exit.");
            return gateResponse;
        } else if (tokenEntity.getTokenStatus() == 0) {
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage("No entry registered for this token.");
            return gateResponse;
        }

        StationEntity stationFrom = stationRepository.findById(tokenEntity.getFromStationId()).orElse(null);
        StationEntity stationTo = stationRepository.findById(tokenEntity.getToStationId()).orElse(null);
        float distance = Math.abs(stationFrom.getRootDistance() - stationTo.getRootDistance());
        List<RatesEntity> rates = ratesRepository.findAll();
        for(RatesEntity rate: rates){
            if(rate.getMinKm() <= distance && rate.getMaxKm() >=distance ){
                tokenEntity.setTravellingPrice(rate.getPrice());
                break;
            }
        }
        float exitPrice = tokenEntity.getTravellingPrice() - tokenEntity.getPrice();
        if(exitPrice > 0){
            tokenEntity.setExitPrice(exitPrice);
            tokenEntity.setExitPricePaid(1);
            gateResponse.setAccessAllowed(false);
            gateResponse.setMessage("You have travelled more distance than allowed. Please contact customer helpdesk.");
            return gateResponse;
        }

        tokenEntity.setTravelledKm(distance);
        tokenEntity.setExitStationId(deviceEntity.getStationId());
        tokenEntity.setExitTime(LocalDateTime.now());
        tokenEntity.setTokenStatus(2);

        gateResponse.setAccessAllowed(true);
        gateResponse.setMessage("Your journey is completed. Please visit again.");
        tokenRepository.save(tokenEntity);

        return gateResponse;
    }

    private boolean isInvalidDevice(DeviceEntity deviceEntity, Integer deviceTypeId) {
        return !Objects.equals(deviceEntity.getDeviceTypeId(), deviceTypeId)
                || deviceEntity.getStationId() == null
                || deviceEntity.getStationId() == 0;
    }

    private String getDeviceErrorMessage(DeviceEntity deviceEntity) {
        if (!Objects.equals(deviceEntity.getDeviceTypeId(), 2)) {
            return "The device is not for entry, please use another device.";
        } else if (!Objects.equals(deviceEntity.getDeviceTypeId(), 3)) {
            return "The device is not for exit, please use another device.";
        }
        return "The device is not associated with a station.";
    }
}
