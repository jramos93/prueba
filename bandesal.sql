CREATE DATABASE `bandesal`;

USE `bandesal`;

CREATE TABLE `readers` (
	`id` INT(8) NOT NULL COMMENT 'pk tabla',
	`name` VARCHAR(8) NULL DEFAULT NULL COMMENT 'nombre de lector',
	PRIMARY KEY (`id`)
);

CREATE TABLE `blogs` (
	`id` INT(8) NOT NULL COMMENT 'pk tabla',
	`title` VARCHAR(50) NULL DEFAULT NULL COMMENT 'titulo de blog',
	`description` VARCHAR(4000) NULL DEFAULT NULL COMMENT 'descripción de blog',
	PRIMARY KEY (`id`)
)
COLLATE='latin1_spanish_ci'
;

CREATE TABLE `blogs_readers` (
	`r_id` INT NULL COMMENT 'fk a readers',
	`b_id` INT NULL COMMENT 'fk a blogs'
)
COLLATE='latin1_spanish_ci'
;

ALTER TABLE `blogs_readers`
	ADD CONSTRAINT `FK_blogs_readers_readers` FOREIGN KEY (`r_id`) REFERENCES `readers` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION,
	ADD CONSTRAINT `FK_blogs_readers_blogs` FOREIGN KEY (`b_id`) REFERENCES `blogs` (`id`) ON UPDATE NO ACTION ON DELETE NO ACTION;

	ALTER TABLE `blogs`
	CHANGE COLUMN `id` `id` INT(8) NOT NULL AUTO_INCREMENT COMMENT 'pk tabla' FIRST;

	ALTER TABLE `readers`
	CHANGE COLUMN `id` `id` INT(8) NOT NULL AUTO_INCREMENT COMMENT 'pk tabla' FIRST;