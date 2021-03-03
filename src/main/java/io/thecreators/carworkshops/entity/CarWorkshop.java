package io.thecreators.carworkshops.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "car_workshops")
public class CarWorkshop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "company_name", unique = true, nullable = false)
    private String companyName;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToMany
    @JoinTable(name = "car_workshop_car_trademarks",
            joinColumns = {@JoinColumn(name = "car_workshop_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "car_trademark_id", referencedColumnName = "id")})
    private Set<CarTrademark> carTrademarks;
}
