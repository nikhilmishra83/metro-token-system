package com.nikhil.tokenApp.mapper;

import com.nikhil.tokenApp.domain.TokenRequest;
import com.nikhil.tokenApp.entity.TokenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestTokenMapper {


    TokenEntity convertToTokenEntity(TokenRequest tokenRequest);
}
