package io.thecreators.carworkshops.mapper;

import io.thecreators.carworkshops.dto.CarWorkshopDto;
import io.thecreators.carworkshops.dto.UserDto;
import io.thecreators.carworkshops.entity.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CarWorkshopMapper {

    public static CarWorkshop dtoToEntity(CarWorkshopDto carWorkshop){
        return new CarWorkshop()
                .setCompanyName(carWorkshop.getCompanyName())
                .setCarTrademarks(stringToCarTrademark(carWorkshop.getCarTrademarks()))
                .setCity(new City().setName(carWorkshop.getCity()))
                .setPostalCode(carWorkshop.getPostalCode())
                .setCountry(new Country().setName(carWorkshop.getCountry()));
    }

    public static CarWorkshopDto entityToDto(CarWorkshop carWorkshop){
        return new CarWorkshopDto()
                .setCompanyName(carWorkshop.getCompanyName())
                .setCarTrademarks(carTrademarkToString(carWorkshop.getCarTrademarks()))
                .setCity(carWorkshop.getCity().getName())
                .setPostalCode(carWorkshop.getPostalCode())
                .setCountry(carWorkshop.getCountry().getName());
    }

    private static Set<CarTrademark> stringToCarTrademark(Set<String> carTrademarks) {
        return carTrademarks.stream()
                .filter(Objects::nonNull)
                .filter(name-> !name.isEmpty())
                .map(name-> new CarTrademark().setName(name))
                .collect(Collectors.toSet());
    }

    private static Set<String> carTrademarkToString(Set<CarTrademark> carTrademarks) {
        return carTrademarks.stream()
                .filter(Objects::nonNull)
                .map(CarTrademark::getName)
                .collect(Collectors.toSet());
    }

}
