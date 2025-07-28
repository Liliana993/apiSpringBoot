create table medicos(

                        id bigint not null auto_increment,
                        nombre varchar(100) not null,
                        email varchar(100) not null unique,
                        documento varchar(12) not null unique,
                        telefono varchar(20) not null,
                        especialidad varchar(100) not null,
                        calle varchar(100) not null,
                        barrio varchar(100) not null,
                        codigo varchar(100) not null,
                        complemento varchar(100),
                        numero varchar(20),
                        provincia varchar(100) not null,
                        ciudad varchar(100) not null,

                        primary key(id)

);