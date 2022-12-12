package com.wgu.scheduling.service;

import com.wgu.scheduling.model.Appointment;
import com.wgu.scheduling.model.CurrentUser;
import com.wgu.scheduling.model.User;
import com.wgu.scheduling.repository.AppointmentRepository;
import com.wgu.scheduling.repository.UserRepository;
import com.wgu.scheduling.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.*;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Appointment getOne(Long id) {
        return appointmentRepository.getById(id);
    }

    public List<Appointment> getAll(long user_id) {
        return appointmentRepository.findAll(user_id);
    }

    public List<Appointment> all() {
        return appointmentRepository.all();
    }

    public List<Appointment> getByDay(long user_id) {
        List<Appointment> appointments = getAll(user_id);

        List<Appointment> dayOfAppointments = new ArrayList<>();
        LocalDate currentDay = LocalDate.now();

        for (Appointment appointment : appointments) {
            Timestamp appointmentTimeZone = Timestamp.valueOf(appointment.getStart());
            if (currentDay.equals(appointmentTimeZone.toLocalDateTime().toLocalDate())) {
                dayOfAppointments.add(appointment);
            }
        }

        return dayOfAppointments;
    }

    public List<List<Appointment>> getAllByWeekAndMonth(long user_id) {
        List<List<Appointment>> weeklyAndMonthlyAppointments = new ArrayList<>();
        List<Appointment> appointments = getAll(user_id);

        List<Appointment> week = new ArrayList<>();
        List<Appointment> month = new ArrayList<>();

        LocalDate startOfWeek = LocalDate.now();
        LocalDate endOfWeek = LocalDate.now();


        while (startOfWeek.getDayOfWeek() != DayOfWeek.MONDAY) {
            startOfWeek = startOfWeek.minusDays(1);
        }

        while (endOfWeek.getDayOfWeek() != DayOfWeek.SUNDAY) {
            endOfWeek = endOfWeek.plusDays(1);
        }

        for (Appointment appointment : appointments) {
            Timestamp appointmentTimeZone = Timestamp.valueOf(appointment.getStart());
            if (LocalDateTime.now().getMonth() == appointmentTimeZone.toLocalDateTime().getMonth()) {
                month.add(appointment);

            }
            if (startOfWeek.equals(appointmentTimeZone.toLocalDateTime().toLocalDate()) ||
                    (endOfWeek.equals(appointmentTimeZone.toLocalDateTime().toLocalDate()))) {
                week.add(appointment);
            }
            if (appointmentTimeZone.toLocalDateTime().toLocalDate().isAfter(startOfWeek) &&
                    appointmentTimeZone.toLocalDateTime().toLocalDate().isBefore(endOfWeek)) {
                week.add(appointment);
            }
        }

        weeklyAndMonthlyAppointments.add(week);
        weeklyAndMonthlyAppointments.add(month);

        return weeklyAndMonthlyAppointments;
    }

    public void update(Appointment updatedAppointment, Long id) {
        Appointment appointment = appointmentRepository.getById(id);

        appointment.setUserId(updatedAppointment.getUserId());
        appointment.setTitle(updatedAppointment.getTitle());
        appointment.setDescription(updatedAppointment.getDescription());
        appointment.setLocation(updatedAppointment.getLocation());
        appointment.setContact(updatedAppointment.getContact());
        appointment.setType(updatedAppointment.getType());
        appointment.setUrl(updatedAppointment.getUrl());
        appointment.setStart(updatedAppointment.getStart());
        appointment.setEnd(updatedAppointment.getEnd());
        appointment.setCustomerId(updatedAppointment.getCustomerId());
        appointment.setModifiedAt(updatedAppointment.getModifiedAt());
        appointment.setModifiedBy(updatedAppointment.getModifiedBy());

        appointmentRepository.update(appointment);
    }

    public void save(Appointment appointment) {
        User user = userRepository.getById(CurrentUser.id);
        Timestamp start = Tools.convertStringToTimestamp(appointment.getStart());
        Timestamp end = Tools.convertStringToTimestamp(appointment.getEnd());

        // Check if start date is less than end date
        if (start.compareTo(end) < 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            int startDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            cal.setTime(end);
            int endDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            // Check if appointment is on the same day
            if (startDayOfWeek == endDayOfWeek) {
                switch (startDayOfWeek) {
                    case 1: // Sunday
                        createAppointment(
                                start,
                                end,
                                user.isSundayDayOff(),
                                user.getSundayStartTime(),
                                user.getSundayEndTime(),
                                appointment
                        );
                        break;
                    case 2:
                        createAppointment(
                                start,
                                end,
                                user.isMondayDayOff(),
                                user.getMondayStartTime(),
                                user.getMondayEndTime(),
                                appointment
                        );
                        break;
                    case 3:
                        createAppointment(
                                start,
                                end,
                                user.isTuesdayDayOff(),
                                user.getTuesdayStartTime(),
                                user.getTuesdayEndTime(),
                                appointment
                        );
                        break;
                    case 4:
                        createAppointment(
                                start,
                                end,
                                user.isWednesdayDayOff(),
                                user.getWednesdayStartTime(),
                                user.getWednesdayEndTime(),
                                appointment
                        );
                        break;
                    case 5:
                        createAppointment(
                                start,
                                end,
                                user.isThursdayDayOff(),
                                user.getThursdayStartTime(),
                                user.getThursdayEndTime(),
                                appointment
                        );
                        break;
                    case 6:
                        createAppointment(
                                start,
                                end,
                                user.isFridayDayOff(),
                                user.getFridayStartTime(),
                                user.getFridayEndTime(),
                                appointment
                        );
                        break;
                    case 7:
                        createAppointment(
                                start,
                                end,
                                user.isSaturdayDayOff(),
                                user.getSaturdayStartTime(),
                                user.getSaturdayEndTime(),
                                appointment
                        );
                        break;
                }
            }
        }
    }

    private void createAppointment(Timestamp start,
                                   Timestamp end,
                                   boolean dayOff,
                                   LocalTime userStartTime,
                                   LocalTime userEndTime,
                                   Appointment appointment) {
        if (!dayOff) {
            if (userStartTime.compareTo(start.toLocalDateTime().toLocalTime()) <= 0 &&
                    userEndTime.compareTo(start.toLocalDateTime().toLocalTime()) > 0 &&
                    userStartTime.compareTo(end.toLocalDateTime().toLocalTime()) < 0 &&
                    userEndTime.compareTo(end.toLocalDateTime().toLocalTime()) >= 0
            ) {
                List<Appointment> conflictingAppointments = appointmentRepository.findAllByUserBetweenStartAndEndTime(CurrentUser.id, start, end);
                if (conflictingAppointments.isEmpty()) {
                    appointmentRepository.save(appointment);
                }
            }
        }
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

}
