CREATE DATABASE `pao_project`;
USE `pao_project`;

CREATE TABLE `patient` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `firstName` varchar(100) NOT NULL,
                                `lastName` varchar(100) NOT NULL,
                                `age` int(3) NOT NULL,
                                `sex` varchar(100) NOT NULL,
                                `phoneNumber` varchar(11) NOT NULL,
                                `disease` varchar(100) NOT NULL,
                                PRIMARY KEY (`id`));

CREATE TABLE `doctor` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `firstName` varchar(100) NOT NULL,
                            `lastName` varchar(100) NOT NULL,
                            `age` int(3) NOT NULL,
                            `sex` varchar(100) NOT NULL,
                            `phoneNumber` varchar(11) NOT NULL,
                            `salary` double NOT NULL,
                            `experience` int(2) NOT NULL,
                            `branch` varchar(100) NOT NULL,
                            PRIMARY KEY (`id`));

CREATE TABLE `assistant` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `idDoctor` int(11),
                             `firstName` varchar(100) NOT NULL,
                             `lastName` varchar(100) NOT NULL,
                             `age` int(3) NOT NULL,
                             `sex` varchar(100) NOT NULL,
                             `phoneNumber` varchar(11) NOT NULL,
                             `salary` double NOT NULL,
                             `experience` int(2) NOT NULL,
                             `resident` int(1) NOT NULL,
                             CONSTRAINT `FK_doctor_assistant` FOREIGN KEY (`idDoctor`) REFERENCES `doctor` (`id`) ON DELETE SET NULL ON UPDATE NO ACTION,
                             PRIMARY KEY (`id`));

CREATE TABLE `consultation` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `date` varchar(100) NOT NULL,
                                `price` double NOT NULL,
                                `idDoctor` int(11) NOT NULL,
                                `idPatient` int(11) NOT NULL,
                                `disease` varchar(100) NOT NULL,
                                CONSTRAINT `FK_doctor_consultation` FOREIGN KEY (`idDoctor`) REFERENCES `doctor` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                                CONSTRAINT `FK_patient_consultation` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                                PRIMARY KEY (`id`));

CREATE TABLE `surgery` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `date` varchar(100) NOT NULL,
                                `price` double NOT NULL,
                                `idDoctor` int(11) NOT NULL,
                                `idPatient` int(11) NOT NULL,
                                `type` varchar(100) NOT NULL,
                                `idAssistant` int(11) NOT NULL,
                                CONSTRAINT `FK_doctor_surgery` FOREIGN KEY (`idDoctor`) REFERENCES `doctor` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                                CONSTRAINT `FK_patient_surgery` FOREIGN KEY (`idPatient`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                                CONSTRAINT `FK_assistant_surgery` FOREIGN KEY (`idAssistant`) REFERENCES `assistant` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                                PRIMARY KEY (`id`));