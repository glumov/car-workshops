package io.thecreators.carworkshops.mapper;

import io.thecreators.carworkshops.dto.UserDto;
import io.thecreators.carworkshops.entity.City;
import io.thecreators.carworkshops.entity.Country;
import io.thecreators.carworkshops.entity.User;

public class UserMapper {

    public static User dtoToEntity(UserDto user){
        return new User()
                .setUserName(user.getUserName())
                .setEmail(user.getEmail())
                .setCity(new City().setName(user.getCity()))
                .setPostalCode(user.getPostalCode())
                .setCountry(new Country().setName(user.getCountry()));
    }

    public static UserDto entityToDto(User user){
        return new UserDto()
                .setUserName(user.getUserName())
                .setEmail(user.getEmail())
                .setCity(user.getCity().getName())
                .setPostalCode(user.getPostalCode())
                .setCountry(user.getCountry().getName());
    }

}
