package io.thecreators.carworkshops.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "company_name",nullable = false)
    private String companyName;

    @CreatedDate
    @Column(name = "date_time",nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JsonBackReference
    @JoinColumn (name="user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "appointments_car_trademarks",
            joinColumns = {@JoinColumn(name = "appointment_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "car_trademark_id", referencedColumnName = "id")})
    private Set<CarTrademark> carTrademarks;

}
