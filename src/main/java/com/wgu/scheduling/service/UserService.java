package com.wgu.scheduling.service;


import com.wgu.scheduling.model.Role;
import com.wgu.scheduling.model.User;
import com.wgu.scheduling.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getOne(Long id) {
        return userRepository.getById(id);
    }

    public Role getRoleById(Long id) {
        return userRepository.getRoleById(id);
    }

    public List<Role> getRoles() {
        return userRepository.getRoles();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    public void update(User updatedUser, Long id) {
        User user = userRepository.getById(id);

        user.setUserName(updatedUser.getUserName());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setRoleId(updatedUser.getRoleId());
        user.setActive(updatedUser.isActive());
        user.setMondayStartTime(updatedUser.getMondayStartTime());
        user.setMondayEndTime(updatedUser.getMondayEndTime());
        user.setMondayDayOff(updatedUser.isMondayDayOff());
        user.setTuesdayStartTime(updatedUser.getTuesdayStartTime());
        user.setTuesdayEndTime(updatedUser.getTuesdayEndTime());
        user.setTuesdayDayOff(updatedUser.isTuesdayDayOff());
        user.setWednesdayStartTime(updatedUser.getWednesdayStartTime());
        user.setWednesdayEndTime(updatedUser.getWednesdayEndTime());
        user.setWednesdayDayOff(updatedUser.isWednesdayDayOff());
        user.setThursdayStartTime(updatedUser.getThursdayStartTime());
        user.setThursdayEndTime(updatedUser.getThursdayEndTime());
        user.setThursdayDayOff(updatedUser.isThursdayDayOff());
        user.setFridayStartTime(updatedUser.getFridayStartTime());
        user.setFridayEndTime(updatedUser.getFridayEndTime());
        user.setFridayDayOff(updatedUser.isFridayDayOff());
        user.setSaturdayStartTime(updatedUser.getSaturdayStartTime());
        user.setSaturdayEndTime(updatedUser.getSaturdayEndTime());
        user.setSaturdayDayOff(updatedUser.isSaturdayDayOff());
        user.setSundayStartTime(updatedUser.getSundayStartTime());
        user.setSundayEndTime(updatedUser.getSundayEndTime());
        user.setSundayDayOff(updatedUser.isSundayDayOff());
        user.setModifiedAt(updatedUser.getModifiedAt());
        user.setModifiedBy(updatedUser.getModifiedBy());

        userRepository.update(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
