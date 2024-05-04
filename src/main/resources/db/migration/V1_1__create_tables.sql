create table if not exists users (
	id uuid primary key default gen_random_uuid(),
	username varchar(100) not null unique,
	first_name varchar(100),
	last_name varchar(100)
);

create table if not exists desks (
	id uuid primary key default gen_random_uuid(),
	name varchar(100) not null,
	creator uuid references users (id) on delete cascade on update cascade
);

create table if not exists cols (
	id uuid primary key default gen_random_uuid(),
	name varchar(100) not null,
	desk uuid references desks (id) on delete cascade on update cascade,
	position int
);

create table if not exists tasks (
	id uuid primary key default gen_random_uuid(),
	name varchar(100) not null,
	col uuid references cols (id) on delete cascade on update cascade,
	priority varchar(100),
	description text,
	assignee uuid references users (id) on delete set null on update cascade,
	created_at timestamp,
	last_update timestamp,
	status varchar(100),
	author uuid references users (id) on delete set null on update cascade,
	type varchar(50),
	epic uuid references tasks (id) on delete set null on update cascade
);

create table if not exists comments (
	id uuid primary key default gen_random_uuid(),
	comment_text text not null,
	created_at timestamp,
	author uuid references users (id) on delete set null on update cascade
);