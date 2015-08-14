create database mstx;

use mstx;


CREATE TABLE mstx_head(/*头像表*/
	tid		int		NOT NULL,
	tname	varchar(50),
	tdis	char(255),
	tdata	blob,
	uid 	int,
	ttime	TIMESTAMP,
	PRIMARY KEY(tid)
);


CREATE TABLE mstx_user(/*用户表*/
	uid		int		NOT NULL,
	u_name	char(50)	NOT NULL,
	u_qq	varchar(15),
	u_pwd	varchar(50),
	u_Email	varchar(50),
	u_dis	char(255),
	u_head	int,
	u_admin	boolean NOT NULL,
	u_mood	varchar(50),
	u_integral	int,
	u_hobby	varchar(50),
	u_level	int,
	u_number	int,
	PRIMARY KEY(uid),
	FOREIGN KEY(u_head) REFERENCES mstx_head(tid)
);


CREATE TABLE mstx_sort(/*美食种类表*/
	sid		int  NOT NULL,
	info_sort	char(50)  NOT NULL,
	PRIMARY KEY(sid)
);
	

CREATE TABLE mstx_info(/*美食信息表*/
	mid  			int NOT NULL,	
	info_title		char(50)	NOT NULL,
	info_dis		text,
	info_lon		float(17,14),
	info_lat		float(17,14),
	info_sort		int,
	info_price		double,
	info_time		TIMESTAMP,
	uid				int,
	hotel_name		char(50),
	PRIMARY KEY(mid),
	FOREIGN KEY(uid) REFERENCES mstx_user(uid),
	FOREIGN KEY(info_sort) REFERENCES mstx_sort(sid)
);


CREATE TABLE mstx_image(/*美食图片表*/
	id  		int  		NOT NULL,
	mid  		int  		NOT NULL,
	image_data  	blob,
	image_time  	TIMESTAMP,
	PRIMARY KEY(id),
	FOREIGN KEY(mid) REFERENCES mstx_info(mid)
);


CREATE TABLE mstx_recommend(/*每日推荐表*/
	id  		int  		NOT NULL,
	mid  		int  		NOT NULL,
	recommend_time  	TIMESTAMP,
	PRIMARY KEY(id),
	FOREIGN KEY(mid) REFERENCES mstx_info(mid)
);



CREATE TABLE mstx_col(/*我的收藏表*/
	mid	int		NOT NULL,
	uid	int		NOT NULL,
	comment	char(255),
	FOREIGN KEY(mid) REFERENCES mstx_info(mid),
	FOREIGN KEY(uid) REFERENCES mstx_user(uid)
);




CREATE TABLE mstx_ads(/*广告表*/
	gid		int		NOT NULL,
	gdis	char(255),
	PRIMARY KEY(gid)
);


CREATE TABLE mstx_ads_image(/*广告图片表*/
	gpid	int		NOT NULL,
	gid		int		NOT NULL,
	gdata	blob,
	PRIMARY KEY(gpid),
	FOREIGN KEY(gid) REFERENCES mstx_ads(gid)
);


CREATE TABLE mstx_max(/*最大编号表*/
	mstx_head 	int	default 0,
	mstx_user 	int	default 0,
	mstx_info 	int	default 0,
	mstx_image	int	default 0,
	mstx_ads 	int	default 0,
	mstx_ads_image	int	default 0,
	mstx_recommend	int	default 0,
	mstx_sort	int default 0
);

commit;
show tables;
