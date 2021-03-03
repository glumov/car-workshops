package io.thecreators.carworkshops.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "car_trademarks")
public class CarTrademark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "carTrademarks")
    private Set<CarWorkshop> carWorkshops;

}
