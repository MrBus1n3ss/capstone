create if not exists states (
    state_id serial primary key,
    state_name varchar(100) not null,
    state_abbreviation varchar(2) not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null
);

create if not exists phones (
    phone_id serial primary key,
    phone_name varchar(50) not null,
    phone_number varchar(50) not null,
    customer_id int not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null
)


create if not exists addresses (
    address_id serial primary key,
    address_1 varchar(50) not null,
    address_2 varchar(50) not null,
    city varchar(50) not null,
    state_id int not null,
    postal_code varchar(50) not null,
    state_id int not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null

    constraint fk_state_addresses foreign key(state_id)
        references states(state_id)

)


create if not exists customers (
    customer_id serial primary key,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    email varchar(50) not null,
    address_id int not null,
    phone_id int not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null

    constraint fk_address_customers foreign key(address_id)
        references addresses(address_id)
        on delete cascade
    constraint fk_phone_customers foreign key(phone_id)
        references addresses(phone_id)
        on delete cascade
);

create if not exists appointments (
    appointment_id serial primary key,
    title varchar(150) not null,
    appointment_description varchar(500) not null,
    appointment_location varchar(100) not null,
    perferred_contact varchar(50) not null,
    appointment_type varchar(50) not null,
    appointment_url varchar(50),
    start_timestamp timestamp not null,
    end_timestamp timestamp not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null
);

create if not exists user_business_hours (
    user_business_hours_id serial primary key,
    monday_day_off boolean not null,
    monday_start_time time not null,
    monday_end_time time not null,
    tuesday_day_off boolean not null,
    tuesday_start_time time not null,
    tuesday_end_time time not null,
    wednesday_day_off boolean not null,
    wednesday_start_time time not null,
    wednesday_end_time time not null,
    thursday_day_off boolean not null,
    thursday_start_time time not null,
    thursday_end_time time not null,
    friday_day_off boolean not null,
    friday_start_time time not null,
    friday_end_time time not null,
    saturday_day_off boolean not null,
    saturday_start_time time not null,
    saturday_end_time time not null,
    sunday_day_off boolean not null,
    sunday_start_time time not null,
    sunday_end_time time not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null
)

create if not exists roles (
    role_id serial primary key,
    role_name varchar(25) not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null
)

create if not exists users (
    user_id serial primary key,
    user_name varchar(50) not null,
    user_password varchar(500) not null,
    is_active boolean not null,
    user_business_hours_id int not null,
    role_id int not null,
    appointment_id int not null,
    created_at timestamp not null,
    created_by timestamp not null,
    modified_at timestamp not null,
    modified_by timestamp not null

    constraint fk_ubh_users foreign key(user_business_hours_id)
        references user_business_hours(user_business_hours_id)
        on delete cascade
    constraint fk_roles_users foreign key(role_id)
        references roles(role_id)
        on delete cascade
    constraint fk_appointment_users foreign key(appointment_id)
        references appointments(appointment_id)
        on delete cascade
);

