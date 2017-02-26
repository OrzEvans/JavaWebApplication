-- 数据库建表
-- 建立数据库project
DROP database IF EXISTS  project;
CREATE database project CHARACTER SET UTF8;
USE project;
SET names UTF8;

-- -----------------------------------------------------
-- Table `m_admin` 管理员表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `p_admin` ;

CREATE TABLE IF NOT EXISTS `p_admin` (
  `admin_id` INT NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `language_code` VARCHAR(10) NOT NULL DEFAULT 'ZH' COMMENT '语言代码',
  `account` VARCHAR(50) NOT NULL   COMMENT '管理员账号',
  `password` VARCHAR(64)  NOT NULL  COMMENT '管理员密码',
  `name` VARCHAR(20)  NOT NULL  COMMENT '管理员姓名',
  `department` VARCHAR(20)  NOT NULL  COMMENT '部门',
  `comment` TEXT  NULL  COMMENT '备注',
  `create_time` DATETIME  NULL DEFAULT NULL COMMENT '创建时间',
  `is_locked` ENUM('N','Y') NOT NULL DEFAULT 'N' COMMENT '是否锁定，Y为已锁定，N为未锁定',
  `is_delete` ENUM('N','Y') NOT NULL DEFAULT 'N' COMMENT '是否删除，Y为已删除，N为未删除',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`admin_id`))
ENGINE = InnoDB
COMMENT = '管理员表';
-- -----------------------------------------------------
-- Table `p_role` 角色表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `p_role` ;

CREATE TABLE IF NOT EXISTS `p_role` (
  `role_id` INT NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` VARCHAR(50)  NOT NULL  COMMENT '角色名称',
  `role_flag` VARCHAR(50)  NOT NULL  COMMENT '角色标记',
  `is_delete` ENUM('N','Y') NOT NULL DEFAULT 'N' COMMENT '是否删除，Y为已删除，N为未删除',
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
COMMENT = '角色表';

-- -----------------------------------------------------
-- Table `p_action` 权限表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `p_action` ;

CREATE TABLE IF NOT EXISTS `p_action` (
  `action_id` INT NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `action_name` VARCHAR(50)  NOT NULL  COMMENT '权限名称',
  `action_flag` VARCHAR(50)  NOT NULL  COMMENT '权限标记',
  `is_delete` ENUM('N','Y') NOT NULL DEFAULT 'N' COMMENT '是否删除，Y为已删除，N为未删除',
  PRIMARY KEY (`action_id`))
ENGINE = InnoDB
COMMENT = '权限表';
-- -----------------------------------------------------
-- Table `admin_role` 管理员与角色关系表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `admin_role` ;

CREATE TABLE IF NOT EXISTS `admin_role` (
  `admin_id` INT NOT NULL  COMMENT '管理员id',
  `role_id` INT NOT NULL  COMMENT '角色id',
  CONSTRAINT fk_admin_id1 FOREIGN KEY(admin_id) REFERENCES p_admin(admin_id) ,
	CONSTRAINT fk_role_id1 FOREIGN KEY(role_id) REFERENCES p_role(role_id)
)
ENGINE = InnoDB
COMMENT = '管理员与角色关系表';
-- -----------------------------------------------------
-- Table `role_action` 角色与权限关系表
-- -----------------------------------------------------
DROP TABLE IF EXISTS `role_action` ;

CREATE TABLE IF NOT EXISTS `role_action` (
  `role_id` INT NOT NULL  COMMENT '角色id',
  `action_id` INT NOT NULL  COMMENT '权限id',
  CONSTRAINT fk_role_id2 FOREIGN KEY(role_id) REFERENCES p_role(role_id) ,
	CONSTRAINT fk_action_id2 FOREIGN KEY(action_id) REFERENCES p_action(action_id)
)
ENGINE = InnoDB
COMMENT = '角色与权限关系表';
-- 模拟数据
INSERT INTO `admin_role` VALUES ('1', '1'), ('2', '2');
INSERT INTO `p_action` VALUES ('1', '增加管理员', 'admin:add', 'N'), ('2', '管理员列表', 'admin:list', 'N'), ('3', '删除管理员', 'admin:remove', 'N'), ('4', '修改管理员', 'admin:edit', 'N');
INSERT INTO `p_admin` VALUES ('1', '1', 'admin', 'V9nMu4Ugt+o=', 'XXX', 'XXX', 'XXX', '2017-02-21 09:09:37', 'Y', 'N', '2017-02-25 17:17:43'), ('2', '2', 'work', 'wMObmV7P42c=', 'YYY', 'YYY', 'YYY', '2017-02-21 09:51:44', 'N', 'N', '2017-02-25 16:45:30');
INSERT INTO `role_action` VALUES ('1', '1'), ('1', '2'), ('1', '3'), ('1', '4'), ('2', '1'), ('2', '2');
INSERT INTO `p_role` VALUES ('1', '超级管理员', 'super_admin', 'N'), ('2', '管理员', 'admin', 'N');
