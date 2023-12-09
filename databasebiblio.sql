create database gestion_bibliotheque_en_ligne ;
use gestion_bibliotheque_en_ligne ;
CREATE TABLE utilisateur (
    Id_Utilisateur INT AUTO_INCREMENT PRIMARY KEY,
    Nom VARCHAR(50) not null,
    Prenom VARCHAR(50) not null,
    Login VARCHAR(50) not null,
    Pwd VARCHAR(50) not null,
    Role VARCHAR(50) not null,
    check (Role in ("Etudiant","Enseignant","Bibliothecaire"))
);
describe utilisateur;
create table Livre (
	Id_Livre int AUTO_INCREMENT primary key,
	Titre varchar(50) not null ,
	Auteur varchar(50) not null,
	Genre varchar(50)  not null,
	Disponibilite bool NOT NULL
);
describe Livre;
create table Emprunt (
	Id_Emprunt int AUTO_INCREMENT  ,
	Id_Utilisateur int not null,
	FOREIGN KEY (Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur)on delete cascade,
	Id_Livre int not null,
	FOREIGN KEY (Id_Livre) REFERENCES Livre(Id_Livre)on delete cascade,
	Date_Emprunt date NOT NULL ,
	Date_Retour date NOT NULL , 
	Statut bool NOT NULL ,
    primary key(Id_Emprunt,Id_Utilisateur,Id_Livre)
);
describe Emprunt;
create table Reservation (
	Id_Reservation int AUTO_INCREMENT ,
	Id_Utilisateur int not null,
	FOREIGN KEY (Id_Utilisateur) REFERENCES Utilisateur(Id_Utilisateur) on delete cascade,
	Id_Livre int not null,
	FOREIGN KEY (Id_Livre) REFERENCES Livre(Id_Livre) on delete cascade,
	Date_Reservation DATETIME NOT NULL ,
	Statut varchar(50) NOT NULL ,
    check (Statut in ("attente","annulee","confirmee")),
    primary key(Id_Reservation,Id_Utilisateur,Id_Livre)
);
describe Reservation;
INSERT INTO Livre (Titre, Auteur, Genre, Disponibilite) VALUES
('Why Has Nobody Told Me This Before', 'Julie Smith', 'Psychologie', true),
('The Great Gatsby', 'F. Scott Fitzgerald', 'Fiction', true),
('The Da Vinci Code', 'Dan Brown', 'Mystery', true),
('To Kill a Mockingbird', 'Harper Lee', 'Classic', true),
('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'Non-Fiction', true),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy', true),
('The Catcher in the Rye', 'J.D. Salinger', 'Coming-of-Age', true),
('The Girl on the Train', 'Paula Hawkins', 'Thriller', true);

select * from livre;

INSERT INTO utilisateur (Nom, Prenom, Login, Pwd, Role) VALUES
('unvaliduser','invaliduser','invaliduser','invaliduser','Etudiant'),
('Montassar', 'Zouaoui', 'zouaoui.montassar@etudiant-fst.utm.tn', 'montassar123', 'Etudiant'),
('Achraf', 'Kaou', 'kaou.achraf@etudiant-fst.utm.tn', 'achraf123', 'Enseignant'),
('Admin', 'Admin', 'bibliotheque.enligne.fst@gmail.com', 'adminpassword', 'Bibliothecaire');
select * from utilisateur;
select*from emprunt;
select * from reservation;
select * from livre;
SET SQL_SAFE_UPDATES = 0;

