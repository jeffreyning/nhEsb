# nhesb

CREATE TABLE `nhesb_service_address` (
  `uuid` varchar(50) default NULL,
  `sysid` varchar(50) default NULL,
  `ip` varchar(255) default NULL,
  `port` varchar(10) default NULL,
  `url` varchar(500) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

