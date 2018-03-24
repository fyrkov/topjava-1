DROP TABLE user_roles;
drop table meals;
DROP TABLE users;
DROP SEQUENCE IF EXISTS global_seq;
commit;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
  calories_per_day INTEGER DEFAULT 2000    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

create table meals (
    id integer primary key default nextval ('global_seq'),
    user_id integer not null,
    datetime timestamp not null,
    description varchar not null,
    calories integer not null,
    foreign key (user_id) references users (id) on delete cascade
);

CREATE UNIQUE INDEX meals_unique_user_id_datetime_idx ON meals (user_id, datetime);