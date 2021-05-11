create table department
(
    id              bigint       not null primary key,
    department_name varchar(255) not null
);

create sequence hibernate_sequence;

create table users
(
    id            bigint       not null,
    name          varchar(255) not null,
    department_id bigint
);

alter table users
    add constraint user_pk primary key (id);

alter table users
    add constraint user_department_fk
        foreign key (department_id)
            references department (id)
            on update cascade
            on delete no action;



create table task
(
    id          bigint       not null,
    topic       varchar(255) not null,
    description text,
    status      varchar(100)          not null,
    created_at  timestamp,
    author_id   bigint not null,
    assignee_id bigint
);

alter table task
    add constraint task_pk
        primary key (id);

alter table task
    add constraint author_fk
        foreign key (author_id)
            references users (id)
            on delete no action
            on update cascade;

alter table task
    add constraint assignee_fk
        foreign key (assignee_id)
            references users (id)
            on delete no action
            on update cascade;

create table task_comment
(
    id        bigint not null,
    task_id   bigint not null,
    author_id bigint not null,
    comment   tinytext not null
);

alter table task_comment
    add constraint task_comment_pk primary key (id);

alter table task_comment
    add constraint task_comment_author_fk
        foreign key (author_id) references users (id);

alter table task_comment
    add constraint task_comment_task_fk
        foreign key (task_id)
            references task (id);