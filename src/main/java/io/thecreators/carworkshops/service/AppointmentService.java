package io.thecreators.carworkshops.service;

import io.thecreators.carworkshops.dto.AppointmentDto;
import io.thecreators.carworkshops.entity.Appointment;
import io.thecreators.carworkshops.entity.CarTrademark;
import io.thecreators.carworkshops.entity.User;
import io.thecreators.carworkshops.exception.NotFoundException;
import io.thecreators.carworkshops.mapper.AppointmentMapper;
import io.thecreators.carworkshops.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final UserRepository userRepository;

    private final AppointmentRepository appointmentRepository;

    private final CarTrademarkRepository carTrademarkRepository;

    public AppointmentService(UserRepository userRepository,
                              AppointmentRepository appointmentRepository,
                              CarTrademarkRepository carTrademarkRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.carTrademarkRepository = carTrademarkRepository;
    }

    public Appointment save(AppointmentDto appointmentDto) {
        Appointment appointment = AppointmentMapper.dtoToEntity(appointmentDto);
        User user = appointment.getUser();
        User persistedUser = userRepository.findByUserName(user.getUserName());
        if (Objects.isNull(persistedUser)) {
            throw new NotFoundException(String.format("Not found user with name %s", user.getUserName()));
        } else {
            Set<CarTrademark> carTrademarks = new HashSet<>();
            appointment.setUser(persistedUser);
            appointment.getCarTrademarks().stream()
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
            appointment.setCarTrademarks(carTrademarks);
        }
        return appointmentRepository.save(appointment);
    }

    public Appointment update(int id, AppointmentDto appointmentDto) {
        Appointment appointment = findById(id).orElseThrow(() -> new NotFoundException("No Appointment with ID : " + id));
        Appointment updateAppointment = AppointmentMapper.dtoToEntity(appointmentDto);
        updateAppointment.setId(appointment.getId());
        return appointmentRepository.save(updateAppointment);
    }

    public Appointment update(int id, String dateTime) {
        Appointment appointment = findById(id).orElseThrow(() -> new NotFoundException("No Appointment with ID : " + id));
        appointment.setDateTime(convertStringToLocalDateTime(dateTime));
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> findById(int id) {
        return appointmentRepository.findById(id);
    }

    public void delete(int id) {
        Appointment appointment = findById(id).orElseThrow(() -> new NotFoundException("No Appointment with ID : " + id));
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    private LocalDateTime convertStringToLocalDateTime(String dateTime) {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTime,dateTimeFormatter);
    }
}
