create table if not exists "user" (
    id bigserial primary key,
    email varchar(100) not null,
    firstName varchar(100) not null,
    lastName varchar(100) not null,
    profilePicBase64 varchar,
    createdDateTime bigint,
    updatedDateTime bigint,
);