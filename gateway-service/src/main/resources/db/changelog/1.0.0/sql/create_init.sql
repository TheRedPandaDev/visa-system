create sequence if not exists user_id_seq;

create table if not exists user_table
(
    id       bigint default nextval('user_storage.user_id_seq'::regclass) not null
        constraint user_pk
            primary key,
    username varchar                                                      not null,
    password varchar
);

create unique index if not exists user_username_uindex
    on user_table (username);