package io.thecreators.carworkshops.mapper;

import io.thecreators.carworkshops.dto.AppointmentDto;
import io.thecreators.carworkshops.entity.Appointment;
import io.thecreators.carworkshops.entity.CarTrademark;
import io.thecreators.carworkshops.entity.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AppointmentMapper {

    public static Appointment dtoToEntity(AppointmentDto appointment){
        return new Appointment()
                .setUser(new User().setUserName(appointment.getUserName()))
                .setCarTrademarks(stringToCarTrademark(appointment.getCarTrademarks()))
                .setCompanyName(appointment.getCompanyName())
                .setDateTime(convertStringToLocalDateTime(appointment.getDateTime()));
    }

    public static AppointmentDto entityToDto(Appointment appointment){
        return new AppointmentDto()
                .setUserName(appointment.getUser().getUserName())
                .setCarTrademarks(carTrademarkToString(appointment.getCarTrademarks()))
                .setCompanyName(appointment.getCompanyName())
                .setDateTime(appointment.getDateTime().toString());
    }

    private static Set<CarTrademark> stringToCarTrademark(Set<String> carTrademarks) {
        return carTrademarks.stream()
                .filter(Objects::nonNull)
                .filter(name-> !name.isEmpty())
                .map(name-> new CarTrademark().setName(name))
                .collect(Collectors.toSet());
    }

    private static Set<String> carTrademarkToString(Set<CarTrademark> carTrademarks) {
        return carTrademarks.stream()
                .filter(Objects::nonNull)
                .map(CarTrademark::getName)
                .collect(Collectors.toSet());
    }

    private static LocalDateTime convertStringToLocalDateTime(String dateTime) {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTime,dateTimeFormatter);
    }


}
