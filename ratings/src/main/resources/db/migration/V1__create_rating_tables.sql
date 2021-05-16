create table user_rating
(
    user_id bigint not null primary key,
    rating  int    not null
);

create sequence hibernate_sequence;