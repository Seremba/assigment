create table Aquarium (
       id varchar(50) not null,
       glass_type varchar(255) not null,
       size_liters int not null,
       shape varchar(255),
       constraint PK_Aquarium primary key (id)
);

create table Fish (
       specie varchar(255) not null,
       fins int not null,
       color varchar(255) not null,
       stock int not null,
       aquarium_id varchar(50) not null,
       constraint PK_Fish primary key (specie),
       constraint FK_aquarium_specie foreign key (aquarium_id) references Aquarium (id)
);


create table Fish_no_compatible (
       specie_a varchar(255),
       specie_b varchar(255),

       constraint PK_fish_no_compatible primary key (specie_a, specie_b),
       constraint FK_specie_a foreign key (specie_a) references Fish (specie)
);

create index IDX_fish_by_aquarium on Fish (aquarium_id);

insert into Aquarium values ('Aquarium1', 'STRONG', 199, 'RECTANGLE');
insert into Aquarium values ('Aquarium2', 'STRONG', 300, 'RECTANGLE');
insert into Aquarium values ('Aquarium3', 'STRONG', 30, 'RECTANGLE');

insert into Fish(specie, fins, color, stock, aquarium_id) values ('goldfish', 2, 'GOLDEN', 12, 'Aquarium1');

insert into Fish(specie, fins, color, stock, aquarium_id) values ('guppy', 2, 'RED', 60, 'Aquarium2');

insert into Fish_no_compatible values ('goldfish', 'guppy');
insert into Fish_no_compatible values ('goldfish', 'swordfish');
