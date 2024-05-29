alter table users add column password varchar(255) not null default 'strongpassword';

create table if not exists desks_memberships (
    id uuid primary key default gen_random_uuid(),
	desk uuid references desks (id) on delete cascade on update cascade,
	member uuid references users (id) on delete cascade on update cascade,
	role varchar(100) not null
);