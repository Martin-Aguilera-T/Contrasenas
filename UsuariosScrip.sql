create database contrasenasBD;
use contrasenasBD;
describe usuarios;

create table usuarios (
  userId varchar(50) unique not null,
  firstName varchar(50) not null,
  lastName varchar(50) not null,
  emailAddress varchar(100) not null,
  userStatus varchar(20) default 'Active',
  role enum('Jefe', 'Empleado') not null,
  createdDate timestamp default current_timestamp,
  password varchar(128) not null,
  CURP varchar(50),
  RFC varchar(50),
  specialty varchar(50),
  notes text,
  
  phoneEncrypted varbinary(255),
  addressEncrypted varbinary(255),
  salaryEncrypted varbinary(255)
);


insert into usuarios(userId, firstName, lastName, emailAddress, userStatus, role, password, curp, rfc, specialty, notes, phoneEncrypted, addressEncrypted, salaryEncrypted) 
values ('324', 'Martin', 'Aguilera', 'martin.ferre@gmail.com', 'Active', 'Jefe', sha2('12345', 512), 'AUTM1009HGTGRRA8', 'AUTM1034DSF', 'Tornilleria', '.', aes_encrypt('4451234567', 'contra'), aes_encrypt('Emiliano Zapata 13', 'contra'), aes_encrypt('20000', 'contra'));

select u.userId, u.password, u.role from usuarios u where u.userId = '324';

select * from usuarios; 