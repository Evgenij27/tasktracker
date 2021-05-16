create schema if not exists rating;
create schema if not exists task_tracker;

CREATE USER 'user' IDENTIFIED BY 'pass';

GRANT ALL ON rating.* TO 'user';
GRANT ALL ON task_tracker.* TO 'user';