/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.38-0ubuntu0.14.04.1 : Database - user_manager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`user_manager` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `user_manager`;

/*Table structure for table `t_login_log` */

DROP TABLE IF EXISTS `t_login_log`;

CREATE TABLE `t_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `login_type` int(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_login_log` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `pass_word` varchar(150) DEFAULT NULL,
  `user_id` varchar(100) NOT NULL COMMENT '用户id',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `sex` int(2) DEFAULT NULL COMMENT '性别0:男 1:女',
  `birth_date` datetime DEFAULT NULL COMMENT '出生日期',
  `country` varchar(50) DEFAULT NULL COMMENT '国家',
  `caller_type` varchar(20) DEFAULT NULL COMMENT '调用者类别',
  `soft_name` varchar(255) DEFAULT NULL COMMENT '软件名称',
  `imsi` varchar(110) DEFAULT NULL COMMENT 'imsi',
  `device_no` varchar(110) DEFAULT NULL COMMENT '设备号',
  `imei` varchar(110) DEFAULT NULL COMMENT 'imei设备序列号',
  `image_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `user_name_type` int(2) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`user_name`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
