create sequence if not exists address_id_seq;

create table if not exists address
(
    id        bigint default nextval('address_id_seq'::regclass) not null
        constraint address_pk
            primary key,
    region    varchar                                            not null,
    city      varchar                                            not null,
    street    varchar,
    house     varchar,
    section   varchar,
    building  varchar,
    apartment varchar
);

create sequence if not exists contact_info_id_seq;

create table if not exists contact_info
(
    id           bigint default nextval('contact_info_id_seq'::regclass) not null
        constraint contact_info_pk
            primary key,
    email        varchar                                                 not null,
    phone_number varchar                                                 not null
);

create sequence if not exists work_or_study_info_id_seq;

create table if not exists work_or_study_info
(
    id              bigint default nextval('work_or_study_info_id_seq'::regclass) not null
        constraint work_or_study_info_pk
            primary key,
    organization    varchar                                                       not null,
    job_title       varchar                                                       not null,
    contact_info_id bigint                                                        not null
        constraint work_or_study_info_contact_info_id_fk
            references contact_info (id),
    address_id      bigint                                                        not null
        constraint work_or_study_info_address_id_fk
            references address (id)
);

create sequence if not exists person_document_id_seq;

create table if not exists person_document
(
    id          bigint default nextval('person_document_id_seq'::regclass) not null
        constraint person_document_pk
            primary key,
    doc_no      varchar                                                    not null,
    series_code varchar,
    issue_date  date                                                       not null,
    valid_from  date,
    valid_to    date,
    issuer      varchar
);

create sequence if not exists visa_info_id_seq;

create table if not exists visa_info
(
    id            bigint default nextval('visa_info_id_seq'::regclass) not null
        constraint visa_info_pk
            primary key,
    category_type varchar                                              not null,
    entry_type    varchar                                              not null,
    valid_from    date                                                 not null,
    valid_to      date                                                 not null
);

create sequence if not exists applicant_info_id_seq;

create table if not exists applicant_info
(
    id                    bigint default nextval('applicant_info_id_seq'::regclass) not null
        constraint applicant_info_pk
            primary key,
    sex                   varchar                                                   not null,
    birth_country         varchar                                                   not null,
    birth_date            date                                                      not null,
    last_name             varchar                                                   not null,
    first_name            varchar                                                   not null,
    middle_name           varchar,
    citizenship           varchar                                                   not null,
    contact_info_id       bigint                                                    not null
        constraint applicant_info_contact_info_id_fk
            references contact_info (id),
    address_id            bigint                                                    not null
        constraint applicant_info_info_address_id_fk
            references address (id),
    person_document_id    bigint                                                    not null
        constraint applicant_info_person_document_id_fk
            references person_document (id),
    work_or_study_info_id bigint
        constraint applicant_info_work_or_study_info_id_fk
            references work_or_study_info (id)
);

create sequence if not exists visa_application_id_seq;

create table if not exists visa_application
(
    id                bigint default nextval('visa_application_id_seq'::regclass) not null
        constraint visa_application_pk
            primary key,
    attached_photo    bytea                                                       not null,
    applicant_info_id bigint                                                      not null
        constraint visa_application_applicant_info_id_fk
            references applicant_info (id),
    visa_info_id      bigint                                                      not null
        constraint visa_application_visa_info_id_fk
            references visa_info (id)
);

create sequence if not exists visit_address_id_seq;

create table if not exists visit_address
(
    id                  bigint default nextval('visit_address_id_seq'::regclass) not null
        constraint visit_address_pk
            primary key,
    region              varchar                                                  not null,
    city                varchar                                                  not null,
    visa_application_id bigint                                                   not null
        constraint visit_address_visa_application_id_fk
            references visa_application (id)
);

create sequence if not exists user_task_id_seq;

create table if not exists user_task
(
    id                  bigint                   default nextval('user_task_id_seq'::regclass) not null
        constraint user_task_pk
            primary key,
    visa_application_id bigint                                                                 not null
        constraint user_task_visa_application_id_fk
            references visa_application (id),
    create_dttm         timestamp with time zone default now()                                 not null,
    decision            varchar
)
