create database mstx;

use mstx;


CREATE TABLE mstx_head(/*ͷ���*/
	tid		int		NOT NULL,
	tname	varchar(50),
	tdis	char(255),
	tdata	blob,
	uid 	int,
	ttime	TIMESTAMP,
	PRIMARY KEY(tid)
);


CREATE TABLE mstx_user(/*�û���*/
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


CREATE TABLE mstx_sort(/*��ʳ�����*/
	sid		int  NOT NULL,
	info_sort	char(50)  NOT NULL,
	PRIMARY KEY(sid)
);
	

CREATE TABLE mstx_info(/*��ʳ��Ϣ��*/
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


CREATE TABLE mstx_image(/*��ʳͼƬ��*/
	id  		int  		NOT NULL,
	mid  		int  		NOT NULL,
	image_data  	blob,
	image_time  	TIMESTAMP,
	PRIMARY KEY(id),
	FOREIGN KEY(mid) REFERENCES mstx_info(mid)
);


CREATE TABLE mstx_recommend(/*ÿ���Ƽ���*/
	id  		int  		NOT NULL,
	mid  		int  		NOT NULL,
	recommend_time  	TIMESTAMP,
	PRIMARY KEY(id),
	FOREIGN KEY(mid) REFERENCES mstx_info(mid)
);



CREATE TABLE mstx_col(/*�ҵ��ղر�*/
	mid	int		NOT NULL,
	uid	int		NOT NULL,
	comment	char(255),
	FOREIGN KEY(mid) REFERENCES mstx_info(mid),
	FOREIGN KEY(uid) REFERENCES mstx_user(uid)
);




CREATE TABLE mstx_ads(/*����*/
	gid		int		NOT NULL,
	gdis	char(255),
	PRIMARY KEY(gid)
);


CREATE TABLE mstx_ads_image(/*���ͼƬ��*/
	gpid	int		NOT NULL,
	gid		int		NOT NULL,
	gdata	blob,
	PRIMARY KEY(gpid),
	FOREIGN KEY(gid) REFERENCES mstx_ads(gid)
);


CREATE TABLE mstx_max(/*����ű�*/
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
