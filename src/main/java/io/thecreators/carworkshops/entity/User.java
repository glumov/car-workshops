package io.thecreators.carworkshops.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

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

    @OneToMany(mappedBy = "user")
    private Set<Appointment> appointments;
}
