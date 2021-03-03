package io.thecreators.carworkshops.repository;

import io.thecreators.carworkshops.entity.CarWorkshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarWorkshopRepository extends JpaRepository<CarWorkshop, Integer> {

    List<CarWorkshop> findByCity(String city);
}