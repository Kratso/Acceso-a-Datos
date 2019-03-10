-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema competicionesFutbol
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema competicionesFutbol
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `competicionesFutbol` DEFAULT CHARACTER SET utf8 ;
USE `competicionesFutbol` ;

-- -----------------------------------------------------
-- Table `competicionesFutbol`.`equipo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `competicionesFutbol`.`equipo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `competicionesFutbol`.`posicion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `competicionesFutbol`.`posicion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `competicionesFutbol`.`jugador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `competicionesFutbol`.`jugador` (
  `licencia` INT NOT NULL,
  `idEquipo` INT NOT NULL,
  `nombre` VARCHAR(255) NOT NULL,
  `dorsal` INT NOT NULL,
  `idPosicion` INT NOT NULL,
  PRIMARY KEY (`licencia`),
  INDEX `fk_jugador_equipo_idx` (`idEquipo` ASC),
  INDEX `fk_jugador_posicion1_idx` (`idPosicion` ASC),
  CONSTRAINT `fk_jugador_equipo`
    FOREIGN KEY (`idEquipo`)
    REFERENCES `competicionesFutbol`.`equipo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jugador_posicion1`
    FOREIGN KEY (`idPosicion`)
    REFERENCES `competicionesFutbol`.`posicion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `competicionesFutbol`.`competicion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `competicionesFutbol`.`competicion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `fechaComienzo` DATE NOT NULL,
  `fechaFin` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `competicionesFutbol`.`partido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `competicionesFutbol`.`partido` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fechaHora` DATETIME NOT NULL,
  `idLocal` INT NOT NULL,
  `idVisitante` INT NOT NULL,
  `idComp` INT NOT NULL,
  `golesLocal` INT NOT NULL,
  `golesVisitante` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_partido_equipo1_idx` (`idLocal` ASC),
  INDEX `fk_partido_equipo2_idx` (`idVisitante` ASC),
  INDEX `fk_partido_competicion1_idx` (`idComp` ASC),
  CONSTRAINT `fk_partido_equipo1`
    FOREIGN KEY (`idLocal`)
    REFERENCES `competicionesFutbol`.`equipo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_partido_equipo2`
    FOREIGN KEY (`idVisitante`)
    REFERENCES `competicionesFutbol`.`equipo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_partido_competicion1`
    FOREIGN KEY (`idComp`)
    REFERENCES `competicionesFutbol`.`competicion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `competicionesFutbol`.`estadistica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `competicionesFutbol`.`estadistica` (
  `idPartido` INT NOT NULL,
  `licencia` INT NOT NULL,
  `goles` INT NOT NULL,
  `faltas` INT NOT NULL,
  `tarjAmarillas` INT NOT NULL,
  `tarjRojas` INT NOT NULL,
  INDEX `fk_estadistica_partido1_idx` (`idPartido` ASC),
  INDEX `fk_estadistica_jugador1_idx` (`licencia` ASC),
  PRIMARY KEY (`idPartido`, `licencia`),
  CONSTRAINT `fk_estadistica_partido1`
    FOREIGN KEY (`idPartido`)
    REFERENCES `competicionesFutbol`.`partido` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_estadistica_jugador1`
    FOREIGN KEY (`licencia`)
    REFERENCES `competicionesFutbol`.`jugador` (`licencia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `posicion` (`id`,`descripcion`) VALUES (11,"a"),(12,"ut"),(13,"vitae,"),(14,"magna."),(15,"in"),(16,"Sed"),(17,"enim."),(18,"purus"),(19,"diam"),(20,"et");
INSERT INTO `posicion` (`id`,`descripcion`) VALUES (1,"magnis"),(2,"nec"),(3,"pharetra."),(4,"ullamcorper"),(5,"Mauris"),(6,"massa."),(7,"sagittis"),(8,"turpis"),(9,"nostra,"),(10,"Cras");
INSERT INTO `equipo` (`id`,`nombre`) VALUES (1,"Sapien Corp."),(2,"Ac Sem PC"),(3,"Pharetra Nam LLP"),(4,"Sed Leo Cras PC"),(5,"Elit Incorporated"),(6,"Metus In LLC"),(7,"Ultricies Corporation"),(8,"Libero Donec Limited"),(9,"Vel Institute"),(10,"Urna Nullam Lobortis Corp.");
INSERT INTO `equipo` (`id`,`nombre`) VALUES (11,"Massa Rutrum Associates"),(12,"Adipiscing Lacus Company"),(13,"Nascetur Ltd"),(14,"Lacus Nulla Tincidunt Institute"),(15,"Est LLC"),(16,"Mi PC"),(17,"Tempus PC"),(18,"Curabitur Egestas LLP"),(19,"Aliquam Ornare Libero Incorporated"),(20,"Venenatis Lacus Etiam PC");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (1,"Xander L. Mcintosh",17,16,"17686"),(2,"Scarlet Q. Hamilton",15,2,"73149"),(3,"Evangeline G. Bernard",20,15,"69168"),(4,"Brent W. Ochoa",17,5,"09863"),(5,"Orson W. Hunter",15,14,"10823"),(6,"Kennan O. Blanchard",16,6,"54136"),(7,"Caldwell E. Delgado",14,17,"48672"),(8,"Hollee E. Patrick",9,17,"56499"),(9,"Quyn B. Blankenship",18,16,"86578"),(10,"Rebekah H. Trujillo",16,2,"70852");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (11,"Aurelia K. Payne",13,4,"71621"),(12,"Rigel Z. Harrison",8,14,"28566"),(13,"Isabelle A. Rosales",9,7,"98822"),(14,"Ocean A. Morrison",1,19,"20309"),(15,"Madeline T. Stout",9,10,"87940"),(16,"Chanda K. Tyson",5,8,"90841"),(17,"Byron V. Pugh",11,15,"86048"),(18,"Gemma A. Velasquez",9,20,"51476"),(19,"Keane M. Klein",20,6,"50863"),(20,"Leonard S. Macdonald",16,5,"69561");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (21,"Stone X. Kramer",13,11,"70195"),(22,"Prescott Z. Wilder",9,3,"79238"),(23,"Kim Z. Ellis",15,3,"79502"),(24,"Judah Y. Hudson",2,11,"19344"),(25,"Karyn T. Benson",17,14,"57887"),(26,"Kibo C. Waters",4,11,"16597"),(27,"Steel L. Garcia",7,1,"00142"),(28,"Warren S. Ross",2,7,"21240"),(29,"MacKensie D. Park",17,17,"94320"),(30,"Leigh R. Sargent",7,4,"38377");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (31,"Lavinia A. Greer",10,13,"51823"),(32,"Tanner R. Watkins",11,14,"36771"),(33,"Hedy X. Pittman",18,20,"03531"),(34,"Faith L. Le",1,8,"53763"),(35,"Preston Q. Hood",14,4,"17267"),(36,"Lucy J. Atkinson",6,13,"61851"),(37,"Keaton R. Jacobs",10,8,"64318"),(38,"Michael P. Snow",12,12,"72566"),(39,"Amy K. Walters",11,18,"15840"),(40,"Malik S. Ball",11,9,"19642");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (41,"Hiram K. Perez",7,18,"42635"),(42,"Reese X. Merrill",11,10,"90256"),(43,"Baker A. Wolfe",5,3,"47965"),(44,"Felix W. French",7,19,"16035"),(45,"Aurora U. Carter",5,2,"06111"),(46,"Melvin P. Oliver",6,7,"50358"),(47,"Denton A. Larsen",10,16,"62863"),(48,"Julian R. Morton",16,4,"90598"),(49,"Grady C. Kennedy",19,18,"52132"),(50,"Quinlan D. Sampson",8,12,"80049");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (51,"Jakeem W. Allen",5,3,"32775"),(52,"Chastity U. Nelson",6,3,"19122"),(53,"Prescott L. Mcmillan",10,16,"19198"),(54,"Keiko J. Booth",2,13,"85870"),(55,"Armando I. Barr",1,6,"89187"),(56,"Sonya A. Blackburn",7,7,"30303"),(57,"Yeo A. Hull",13,12,"32956"),(58,"Demetrius O. Goodwin",18,11,"37927"),(59,"Nicole W. Ray",12,4,"51284"),(60,"Hamish V. Juarez",1,11,"30979");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (61,"Sydney K. Parrish",1,11,"70344"),(62,"Hayley S. Welch",3,18,"05328"),(63,"Ashton I. Mcneil",16,1,"30245"),(64,"Jamalia Q. Gilmore",6,10,"44051"),(65,"Derek E. Bolton",2,17,"44362"),(66,"Yeo I. Ayers",10,5,"02040"),(67,"Cheyenne Q. Carter",1,19,"84789"),(68,"Emily D. Mathews",1,17,"20931"),(69,"Maryam U. Jennings",14,2,"86423"),(70,"Tatum E. Franklin",20,16,"92760");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (71,"Asher X. Ballard",7,13,"70475"),(72,"Colin B. Cobb",3,16,"38570"),(73,"Naida R. Cox",8,15,"97814"),(74,"Troy F. Guy",18,1,"11115"),(75,"Basil S. Bradley",10,3,"86157"),(76,"Cade K. Townsend",14,14,"39250"),(77,"Howard N. Vaughan",3,12,"92912"),(78,"Oscar L. Langley",5,15,"49494"),(79,"Vera S. Flowers",20,15,"92048"),(80,"Jordan U. Hansen",8,4,"03803");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (81,"Xanthus Y. Mccullough",20,11,"86946"),(82,"Wing A. Bond",4,13,"98316"),(83,"Otto X. Lindsey",14,7,"21891"),(84,"Indigo G. Byers",14,12,"29093"),(85,"Cameron H. Mcclain",7,17,"71239"),(86,"Ira S. Morales",20,1,"95863"),(87,"Cally G. Ballard",5,12,"11806"),(88,"Bernard G. Morris",15,15,"50102"),(89,"Avram H. Carr",10,5,"70448"),(90,"Raya T. Jimenez",11,1,"97797");
INSERT INTO `jugador` (`licencia`,`nombre`,`idEquipo`,`idPosicion`,`dorsal`) VALUES (91,"Amela I. George",8,17,"69801"),(92,"Daria Q. Evans",14,10,"11312"),(93,"Quentin X. Berry",11,15,"36942"),(94,"Patricia I. Sanford",8,10,"84673"),(95,"Hop J. Reeves",16,1,"82019"),(96,"Walker C. Lopez",17,18,"78194"),(97,"Belle B. Haney",14,3,"79950"),(98,"Aquila V. Cotton",16,1,"32092"),(99,"Raven D. Johnson",4,10,"77592"),(100,"Claire P. Peck",10,17,"38954");
