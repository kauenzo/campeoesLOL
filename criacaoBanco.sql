-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`classe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`classe` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `descricao` TEXT NOT NULL,
  `funcao_principal` TEXT NOT NULL,
  `estrategia_recomendada` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`regiao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`regiao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `descricao` TEXT NOT NULL,
  `historia` TEXT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`campeao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`campeao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `classe_id` INT NOT NULL,
  `regiao_id` INT NOT NULL,
  PRIMARY KEY (`id`, `classe_id`, `regiao_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_campeao_classe1_idx` (`classe_id` ASC) VISIBLE,
  INDEX `fk_campeao_regiao1_idx` (`regiao_id` ASC) VISIBLE,
  CONSTRAINT `fk_campeao_classe1`
    FOREIGN KEY (`classe_id`)
    REFERENCES `mydb`.`classe` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_campeao_regiao1`
    FOREIGN KEY (`regiao_id`)
    REFERENCES `mydb`.`regiao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tipo_de_habilidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tipo_de_habilidade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `descricao` TEXT NOT NULL,
  `efeito_principal` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`habilidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`habilidade` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `descricao` TEXT NOT NULL,
  `tipo_de_habilidade_id` INT NOT NULL,
  `id_campeao` INT NOT NULL,
  PRIMARY KEY (`id`, `tipo_de_habilidade_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_habilidade_tipo_de_habilidade1_idx` (`tipo_de_habilidade_id` ASC) VISIBLE,
  INDEX `id_campeao_idx` (`id_campeao` ASC) VISIBLE,
  CONSTRAINT `fk_habilidade_tipo_de_habilidade1`
    FOREIGN KEY (`tipo_de_habilidade_id`)
    REFERENCES `mydb`.`tipo_de_habilidade` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_campeao`
    FOREIGN KEY (`id_campeao`)
    REFERENCES `mydb`.`campeao` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`skin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`skin` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `preco` INT NOT NULL,
  `id_campeao` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_campeao_id_idx` (`id_campeao` ASC) VISIBLE,
  CONSTRAINT `fk_campeao_id`
    FOREIGN KEY (`id_campeao`)
    REFERENCES `mydb`.`campeao` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
