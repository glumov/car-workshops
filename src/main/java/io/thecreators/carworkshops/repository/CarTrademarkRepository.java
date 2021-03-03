package io.thecreators.carworkshops.repository;

import io.thecreators.carworkshops.entity.CarTrademark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarTrademarkRepository extends JpaRepository<CarTrademark, Integer> {

    CarTrademark findByName(String name);

}
