insert into mstx_head(tid,tname,tdis,uid) 
	values(0,"Ĭ��ͷ��","���Ǹ�Ĭ�ϵ�ͷ�񣬲�û������",0);/*����ͷ��*/
insert into mstx_user(uid,u_name,u_qq,u_Email,u_dis,u_head,
		u_pwd,u_admin,u_mood,u_integral,u_hobby,u_level,u_number) 
	values(0,"����Ա�˺�","250178767","250178767@qq.com",
		"���Ǹ����Թ����û�",0,111,true,"����",376,"����",3,23);/*�����û�*/
insert into mstx_max values();/*����һ������ż�¼*/
update mstx_max set mstx_head = 10;
update mstx_max set mstx_user = 10;
commit;