create table Aquarium (
       id binary(16) not null,
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
       aquarium_id binary(16) not null,
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

insert into Aquarium values (UUID(), 'STRONG', 199, 'RECTANGLE');
insert into Aquarium values (UUID(), 'STRONG', 300, 'RECTANGLE');
insert into Aquarium values (UUID(), 'STRONG', 99, 'RECTANGLE');

insert into Fish(specie, fins, color, stock, aquarium_id)
select 'goldfish', 2, 'GOLDEN', 12, id
from Aquarium where size_liters = 300;

insert into Fish(specie, fins, color, stock, aquarium_id)
select 'guppy', 2, 'RED', 60, id
from Aquarium where size_liters = 199;

insert into Fish_no_compatible values ('goldfish', 'guppy');
insert into Fish_no_compatible values ('goldfish', 'swordfish');
