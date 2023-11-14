USE tp3_mobile;

CREATE TABLE `user` (
	`user_id` INT(11) NOT NULL AUTO_INCREMENT,
	`nom` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`prenom` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`courriel` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`motdepasse` INT(11) NOT NULL DEFAULT '0',
	`pays` VARCHAR(255) NOT NULL COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`user_id`) USING BTREE
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDBuser
;