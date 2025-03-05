package com.nikhil.tokenApp.mapper;

import com.nikhil.tokenApp.domain.MasterStationResponse;
import com.nikhil.tokenApp.entity.StationEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MasterStationMapper {

    MasterStationResponse convertToMasterStationResponse(StationEntity stationEntity);

    List<MasterStationResponse> convertToMastertStationResponseList(List<StationEntity> stationEntityList);
}
