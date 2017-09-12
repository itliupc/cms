CREATE DATABASE cms;
use cms;

CREATE TABLE `ps_user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(64) NOT NULL COMMENT '姓名',
  `user_name` VARCHAR(255) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `status` INT(2) NOT NULL COMMENT '用户的状态，0为可用，1为不可用',
  `user_authority` INT(2) NOT NULL COMMENT '用户权限',
  `latest_login_time` DATETIME DEFAULT NULL COMMENT '最近一次登录时间',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- admin/admin
INSERT INTO `ps_user` (`user_id`, `name`, `user_name`, `password`, `status`, `user_authority`, `latest_login_time`, `create_time`, `update_time`) VALUES('1','系统管理员','admin','$2a$10$tG/aBbjRLB2lM1U4vXthRe8SO0/NWDirluHpq96B.pNrxMasOyOs6','0','0','2017-08-23 09:04:00','2017-08-18 09:32:24','2017-08-22 16:55:08');