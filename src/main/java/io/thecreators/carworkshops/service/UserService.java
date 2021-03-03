package io.thecreators.carworkshops.service;

import io.thecreators.carworkshops.dto.UserDto;
import io.thecreators.carworkshops.entity.City;
import io.thecreators.carworkshops.entity.Country;
import io.thecreators.carworkshops.entity.User;
import io.thecreators.carworkshops.exception.NotFoundException;
import io.thecreators.carworkshops.mapper.UserMapper;
import io.thecreators.carworkshops.repository.CityRepository;
import io.thecreators.carworkshops.repository.CountryRepository;
import io.thecreators.carworkshops.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final CountryRepository countryRepository;

    public UserService(UserRepository userRepository,
                       CityRepository cityRepository,
                       CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
    }

    public User save(UserDto userDto) {
        User user = UserMapper.dtoToEntity(userDto);
        City persistedCity = cityRepository.findByName(user.getCity().getName());
        Country persistedCountry = countryRepository.findByName(user.getCountry().getName());
        if (Objects.isNull(persistedCity)) {
            user.setCity(cityRepository.save(user.getCity()));
        } else {
            user.setCity(persistedCity);
        }
        if (Objects.isNull(persistedCity)) {
            user.setCountry(countryRepository.save(user.getCountry()));
        } else {
            user.setCountry(persistedCountry);
        }
        return userRepository.save(user);
    }

    public void delete(int id) {
        User user = findById(id).orElseThrow(()->new NotFoundException("No User with ID : "+id));
        userRepository.delete(user);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
}
