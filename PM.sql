/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.12 : Database - pm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `pm`;

/*Table structure for table `pm_pro_user_rel_tab` */

DROP TABLE IF EXISTS `pm_pro_user_rel_tab`;

CREATE TABLE `pm_pro_user_rel_tab` (
  `id` varchar(100) NOT NULL COMMENT '编号',
  `projectid` varchar(100) NOT NULL COMMENT '项目id',
  `userid` varchar(100) NOT NULL COMMENT '人员id',
  `role` int(11) NOT NULL COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目人员关系表';

/*Data for the table `pm_pro_user_rel_tab` */

/*Table structure for table `pm_project_tab` */

DROP TABLE IF EXISTS `pm_project_tab`;

CREATE TABLE `pm_project_tab` (
  `id` varchar(100) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '项目名',
  `description` varchar(1000) DEFAULT NULL COMMENT '项目描述',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目表';

/*Data for the table `pm_project_tab` */

/*Table structure for table `pm_task_tab` */

DROP TABLE IF EXISTS `pm_task_tab`;

CREATE TABLE `pm_task_tab` (
  `id` varchar(100) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '任务名称',
  `description` varchar(2000) DEFAULT NULL COMMENT '描述',
  `bug` varchar(2000) DEFAULT NULL COMMENT 'bug描述',
  `jhstarttime` datetime DEFAULT NULL,
  `jhendtime` datetime DEFAULT NULL,
  `sjstarttime` datetime DEFAULT NULL,
  `sjendtime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `userid` varchar(100) NOT NULL COMMENT '执行人',
  `testerid` varchar(100) DEFAULT NULL COMMENT '测试人',
  `projectid` varchar(100) NOT NULL COMMENT '所属项目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

/*Data for the table `pm_task_tab` */

/*Table structure for table `pm_user_tab` */

DROP TABLE IF EXISTS `pm_user_tab`;

CREATE TABLE `pm_user_tab` (
  `id` varchar(100) NOT NULL COMMENT '编号',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `name` varchar(100) NOT NULL COMMENT '真实姓名',
  `pwd` varchar(100) NOT NULL COMMENT '密码',
  `memo` varchar(1000) DEFAULT NULL COMMENT '备注',
  `pinyin` varchar(100) DEFAULT NULL COMMENT '拼音',
  `jianpin` varchar(100) DEFAULT NULL COMMENT '简拼',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `pm_user_tab` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
