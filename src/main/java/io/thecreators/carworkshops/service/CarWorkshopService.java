package io.thecreators.carworkshops.service;

import io.thecreators.carworkshops.dto.CarWorkshopDto;
import io.thecreators.carworkshops.entity.CarTrademark;
import io.thecreators.carworkshops.entity.CarWorkshop;
import io.thecreators.carworkshops.entity.City;
import io.thecreators.carworkshops.entity.Country;
import io.thecreators.carworkshops.exception.NotFoundException;
import io.thecreators.carworkshops.mapper.CarWorkshopMapper;
import io.thecreators.carworkshops.repository.CarTrademarkRepository;
import io.thecreators.carworkshops.repository.CarWorkshopRepository;
import io.thecreators.carworkshops.repository.CityRepository;
import io.thecreators.carworkshops.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarWorkshopService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final CarWorkshopRepository carWorkshopRepository;
    private final CarTrademarkRepository carTrademarkRepository;

    public CarWorkshopService(CityRepository cityRepository, CountryRepository countryRepository, CarWorkshopRepository carWorkshopRepository, CarTrademarkRepository carTrademarkRepository) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.carWorkshopRepository = carWorkshopRepository;
        this.carTrademarkRepository = carTrademarkRepository;
    }

    public CarWorkshop save(CarWorkshopDto carWorkshopDto) {
        CarWorkshop carWorkshop = CarWorkshopMapper.dtoToEntity(carWorkshopDto);
        Set<CarTrademark> carTrademarks = new HashSet<>();
        carWorkshop.getCarTrademarks().stream()
                .map(CarTrademark::getName)
                .collect(Collectors.toSet())
                .forEach(name -> {
                    CarTrademark persistedCarTrademark = carTrademarkRepository.findByName(name);
                    if (Objects.isNull(persistedCarTrademark)) {
                        carTrademarks.add(carTrademarkRepository.save(new CarTrademark().setName(name)));
                    } else {
                        carTrademarks.add(persistedCarTrademark);
                    }
                });
        carWorkshop.setCarTrademarks(carTrademarks);
        City persistedCity = cityRepository.findByName(carWorkshop.getCity().getName());
        Country persistedCountry = countryRepository.findByName(carWorkshop.getCountry().getName());
        if (Objects.isNull(persistedCity)) {
            carWorkshop.setCity(cityRepository.save(carWorkshop.getCity()));
        } else {
            carWorkshop.setCity(persistedCity);
        }
        if (Objects.isNull(persistedCity)) {
            carWorkshop.setCountry(countryRepository.save(carWorkshop.getCountry()));
        } else {
            carWorkshop.setCountry(persistedCountry);
        }
        return carWorkshopRepository.save(carWorkshop);
    }

    public void delete(int id){
        CarWorkshop carWorkshop = findById(id).orElseThrow(()->new NotFoundException("No CarWorkshop with ID : "+id));
        carWorkshopRepository.delete(carWorkshop);
    }

    public Optional<CarWorkshop> findById(int id) {
        return carWorkshopRepository.findById(id);
    }

    public List<CarWorkshop> findByCity(String city) {
        return carWorkshopRepository.findByCity(city);
    }

}
