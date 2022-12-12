package com.wgu.scheduling;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalTime;
import java.util.HashMap;

@Log4j2
@SpringBootApplication
public class SchedulingApplication implements CommandLineRunner {
    private static final Logger logger = LogManager.getLogger(SchedulingApplication.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SchedulingApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        createTables();
        addData();
    }

    private void createCustomerTable(){
        logger.info("Creating customers table");
        jdbcTemplate.execute(
                "create table if not exists customers (\n" +
                        "    customer_id serial primary key,\n" +
                        "    first_name varchar(100) not null,\n" +
                        "    last_name varchar(100) not null,\n" +
                        "    email varchar(50) not null,\n" +
                        "    created_at timestamp not null,\n" +
                        "    created_by varchar(50) not null,\n" +
                        "    modified_at timestamp not null,\n" +
                        "    modified_by varchar(50) not null\n" +
                        ");"
        );
    }

    private void createStateTable(){
        logger.info("Creating states table");
        jdbcTemplate.execute(
                "create table if not exists states (\n" +
                        "    state_id serial primary key,\n" +
                        "    state_name varchar(100) not null unique,\n" +
                        "    state_abbreviation varchar(2) not null unique\n" +
                        ");"
        );
    }

    private void createPhoneTable(){
        logger.info("Creating phones table");
        jdbcTemplate.execute(
                "create table if not exists phones (\n" +
                        "    phone_id serial primary key,\n" +
                        "    phone_name varchar(50) not null,\n" +
                        "    phone_number varchar(50) not null,\n" +
                        "    customer_id int not null,\n" +
                        "    created_at timestamp not null,\n" +
                        "    created_by varchar(50) not null,\n" +
                        "    modified_at timestamp not null,\n" +
                        "    modified_by varchar(50) not null,\n" +
                        "    constraint fk_phone_customers foreign key(customer_id)\n" +
                        "        references customers(customer_id)\n" +
                        "        on delete cascade\n" +
                        ");"

        );
    }

    private void createAddressTable(){
        logger.info("Creating addresses table");
        jdbcTemplate.execute(
                "create table if not exists addresses (\n" +
                        "    address_id serial primary key,\n" +
                        "    address_1 varchar(50) not null,\n" +
                        "    address_2 varchar(50) not null,\n" +
                        "    city varchar(50) not null,\n" +
                        "    postal_code varchar(50) not null,\n" +
                        "    state_id int not null,\n" +
                        "    customer_id int not null, \n" +
                        "    created_at timestamp not null,\n" +
                        "    created_by varchar(50) not null,\n" +
                        "    modified_at timestamp not null,\n" +
                        "    modified_by varchar(50) not null,\n" +
                        "    constraint fk_state_addresses " +
                        "		foreign key(state_id)\n" +
                        "       references states(state_id),\n" +
                        "    constraint fk_customer_address " +
                        "		foreign key(customer_id)\n" +
                        "       references customers(customer_id)\n" +
                        "       on delete cascade\n" +
                        ");"
        );
    }

    private void createAppointmentTable(){
        logger.info("Creating appointments table");
        jdbcTemplate.execute(
                "create table if not exists appointments (\n" +
                        "    appointment_id serial primary key,\n" +
                        "    appointment_title varchar(150) not null,\n" +
                        "    appointment_description varchar(500) not null,\n" +
                        "    appointment_location varchar(100) not null,\n" +
                        "    preferred_contact varchar(50) not null,\n" +
                        "    appointment_type varchar(50) not null,\n" +
                        "    appointment_url varchar(50),\n" +
                        "    start_timestamp timestamp not null,\n" +
                        "    end_timestamp timestamp not null,\n" +
                        "    user_id int not null,\n" +
                        "    customer_id int not null,\n" +
                        "    created_at timestamp not null,\n" +
                        "    created_by varchar(50) not null,\n" +
                        "    modified_at timestamp not null,\n" +
                        "    modified_by varchar(50) not null,\n" +
                        "    constraint fk_appointment_customer foreign key(customer_id) " +
                        "        references customers (customer_id)\n" +
                        "        on delete cascade,\n" +
                        "    constraint fk_appointment_users foreign key(appointment_id)\n" +
                        "        references appointments(appointment_id)\n" +
                        ");"
        );
    }

    private void createUserTable(){
        logger.info("Creating users table");
        jdbcTemplate.execute(
                "create table if not exists users (\n" +
                        "    user_id serial primary key,\n" +
                        "    user_name varchar(50) not null unique,\n" +
                        "    user_password varchar(500) not null,\n" +
                        "    user_email varchar(500) not null,\n" +
                        "    is_active boolean not null,\n" +
                        "    user_business_hours_id int not null,\n" +
                        "    role_id int not null,\n" +
                        "    created_at timestamp not null,\n" +
                        "    created_by varchar(50) not null,\n" +
                        "    modified_at timestamp not null,\n" +
                        "    modified_by varchar(50) not null,\n" +
                        "    monday_day_off boolean not null,\n" +
                        "    monday_start_time time not null,\n" +
                        "    monday_end_time time not null,\n" +
                        "    tuesday_day_off boolean not null,\n" +
                        "    tuesday_start_time time not null,\n" +
                        "    tuesday_end_time time not null,\n" +
                        "    wednesday_day_off boolean not null,\n" +
                        "    wednesday_start_time time not null,\n" +
                        "    wednesday_end_time time not null,\n" +
                        "    thursday_day_off boolean not null,\n" +
                        "    thursday_start_time time not null,\n" +
                        "    thursday_end_time time not null,\n" +
                        "    friday_day_off boolean not null,\n" +
                        "    friday_start_time time not null,\n" +
                        "    friday_end_time time not null,\n" +
                        "    saturday_day_off boolean not null,\n" +
                        "    saturday_start_time time not null,\n" +
                        "    saturday_end_time time not null,\n" +
                        "    sunday_day_off boolean not null,\n" +
                        "    sunday_start_time time not null,\n" +
                        "    sunday_end_time time not null,\n" +
                        "    constraint fk_roles_users foreign key(role_id)\n" +
                        "        references roles(role_id)\n" +
                        "        on delete cascade\n" +
                        ");"
        );
    }

    private void createRoleTable(){
        logger.info("Creating roles table");
        jdbcTemplate.execute(
                "create table if not exists roles (\n" +
                        "    role_id serial primary key,\n" +
                        "    role_name varchar(25) not null unique,\n" +
                        "    created_at timestamp not null,\n" +
                        "    created_by varchar(50) not null,\n" +
                        "    modified_at timestamp not null,\n" +
                        "    modified_by varchar(50) not null\n" +
                        ")"
        );
    }

    private HashMap<String, String> getStates() {
        HashMap<String, String> states = new HashMap<>();
        states.put("AK", "Alaska");
        states.put("AL", "Alabama");
        states.put("AR", "Arkansas");
        states.put("AZ", "Arizona");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("FL", "Florida");
        states.put("GA", "Georgia");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("IN", "Indiana");
        states.put("IA", "Iowa");
        states.put("KS", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("RI", "Rhode Island");
        states.put("SC", "South Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennessee");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VA", "Virginia");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");
        return states;
    }

    private void addStateData(){
        logger.info("Adding States");
        try{
            HashMap<String, String> states = getStates();
            states.forEach((k, v) -> jdbcTemplate.update("insert into states (state_name, state_abbreviation) values (?, ?)", v, k));
        } catch (Exception e) {
            logger.info(e);
        }
    }

    private void addRoleData(){
        logger.info("Adding Roles");
        try {
            jdbcTemplate.update("insert into roles (role_name, created_at, created_by, modified_at, modified_by) values (?, now(), ?, now(), ?)", "ADMIN", "system", "system");
            jdbcTemplate.update("insert into roles (role_name, created_at, created_by, modified_at, modified_by) values (?, now(), ?, now(), ?)", "USER", "system", "system");
        } catch (Exception e) {
            logger.info(e);
        }
    }

    private void addUserData(String username, boolean isAdmin){

        try{
            int role = 2;
            if(isAdmin) {
                role = 1;
            }
            jdbcTemplate.update("insert into users (user_name, " +
                            "user_password, " +
                            "user_email, " +
                            "is_active, " +
                            "user_business_hours_id, " +
                            "role_id, " +
                            "monday_day_off, " +
                            "monday_start_time, " +
                            "monday_end_time, " +
                            "tuesday_day_off, " +
                            "tuesday_start_time, " +
                            "tuesday_end_time, " +
                            "wednesday_day_off, " +
                            "wednesday_start_time, " +
                            "wednesday_end_time, " +
                            "thursday_day_off, " +
                            "thursday_start_time, " +
                            "thursday_end_time, " +
                            "friday_day_off, " +
                            "friday_start_time, " +
                            "friday_end_time, " +
                            "saturday_day_off, " +
                            "saturday_start_time, " +
                            "saturday_end_time, " +
                            "sunday_day_off, " +
                            "sunday_start_time, " +
                            "sunday_end_time, " +
                            "created_at, " +
                            "created_by, " +
                            "modified_at, " +
                            "modified_by) " +
                            "values (" +
                            "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?" +
                            ")",
                    username,
                    DigestUtils.sha256Hex("12Three45"),
                    "fake@fake.com",
                    true,
                    1,
                    role,
                    false, LocalTime.of(9, 30), LocalTime.of(12, 0),
                    false, LocalTime.of(9, 30), LocalTime.of(12, 0),
                    false, LocalTime.of(9, 30), LocalTime.of(12, 0),
                    false, LocalTime.of(9, 30), LocalTime.of(12, 0),
                    false, LocalTime.of(9, 30), LocalTime.of(12, 0),
                    true, LocalTime.of(12, 0), LocalTime.of(12, 1),
                    true, LocalTime.of(12, 0), LocalTime.of(12, 1),
                    "system",
                    "system"
            );
        } catch (Exception e) {
            logger.info(e);
        }

    }

    private void createTables(){
        logger.info("****Creating Tables****");
        createCustomerTable();
        createStateTable();
        createPhoneTable();
        createAddressTable();
        createAppointmentTable();
        createRoleTable();
        createUserTable();
    }

    private void addData(){
        logger.info("****Adding Data****");
        addStateData();
        addRoleData();
        logger.info("Adding Test User, ADMIN");
        logger.info("log in with test_admin");
        logger.info("log in password 12Three45");
        addUserData("test_admin", true);
        logger.info("Adding Test User, USER");
        logger.info("log in with test_user");
        logger.info("log in password 12Three45");
        addUserData("test_user", false);
    }
}
