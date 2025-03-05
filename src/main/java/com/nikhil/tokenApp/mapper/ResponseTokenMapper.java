package com.nikhil.tokenApp.mapper;

import com.nikhil.tokenApp.domain.TokenResponse;
import com.nikhil.tokenApp.entity.TokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResponseTokenMapper {


    TokenResponse convertToTokenResponse(TokenEntity tokenEntity);
}
