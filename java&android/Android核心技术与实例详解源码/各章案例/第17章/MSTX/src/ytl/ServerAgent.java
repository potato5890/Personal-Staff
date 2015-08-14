package ytl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
public class ServerAgent extends Thread{
	Socket sc;//����Socket������
	ServerThread father;//����ServerThread������
	DataInputStream din = null;//������
	DataOutputStream dout = null;//�����
	private boolean flag=true;//ѭ������ 
	public ServerAgent(Socket sc,ServerThread father){//������
		this.sc=sc;//�õ�Socket
		this.father=father;//�õ�ServerThread������
		try{
			din=new DataInputStream(sc.getInputStream());//������
			dout=new DataOutputStream(sc.getOutputStream());//�������
		}
		catch(Exception e){//�����쳣
			e.printStackTrace();//��ӡ�쳣
		}
	}
	public void run(){
		while(flag){//ѭ��
			try{
				String msg=din.readUTF();//����Ϣ 
				System.out.println("Client msg = " + msg);
				if(msg.startsWith("<#LOGIN#>")){//��¼����
					msg=msg.substring(9);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					String u_name = DBUtil.checkUser(str[0], str[1]);//������ݿ����Ƿ��и��û�
					if(u_name == null){//��u_nameΪ��ʱ����¼ʧ��
						System.out.println("u_name == null");
						dout.writeUTF("<#LOGINERROR#>");//֪ͨ�ֻ��ͻ��˵�¼ʧ��
					}
					else {//��¼�ɹ�
						System.out.println(u_name+" <#LOGINOK#>");
						dout.writeUTF("<#LOGINOK#>"+u_name);//��¼�ɹ������û�������
					}
					break;
				}
				else if(msg.startsWith("<#REGISTER#>")){//ע�ᶯ��
					msg=msg.substring(12);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					int uid = DBUtil.insertUser(str[0], str[2], str[1], str[3], str[4]);
					dout.writeUTF("<#REGISTEROK#>"+uid+"|"+str[0]);//��ͻ��˷����û�ID���û���
					break;
				}
				else if(msg.startsWith("<#ClientDown#>")){//�ͻ�������
					try{
						din.close();//�ر�������
						dout.close();//�ر������
						sc.close();//�ر�Socket
						flag = false;
					}
					catch(Exception e){//�����쳣
						e.printStackTrace();//��ӡ�쳣
					}
				}
				else if(msg.startsWith("<#SEARCH#>")){//������ʳ��Ϣ����
					msg=msg.substring(10);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���infoValues��searchSort��startPrice��endPrice��span��currentPageNo
					
					int totleNumber = DBUtil.getMstxInfoCountForPhone(str[0], Integer.parseInt(str[1]), str[2], str[3]);
					
					List<String[]> mstxInfos = DBUtil.getMstxInfoForPhone(str[0], Integer.parseInt(str[1]), 
								str[2], str[3], Integer.parseInt(str[4]), Integer.parseInt(str[5]));
					dout.writeUTF("<#SEARCHINFO#>"+mstxInfos.size()+"|"+totleNumber);//��ͻ��˷�������������
					for(String[] mi : mstxInfos){//ѭ����֯�ַ������͵��ͻ���
						dout.writeUTF(mi[0]+"|"+mi[1]+"|"+mi[2]+"|"+mi[3]+"|"+
								mi[4]+"|"+mi[5]+"|"+mi[6]+"|"+mi[7]);
					}					
					int[] mids = new int[mstxInfos.size()];//�������MID����
					for(int i=0; i<mids.length; i++){
						mids[i] = Integer.parseInt(((String[])mstxInfos.get(i))[7]);
					}					
					
					ArrayList<Blob> blobs = DBUtil.getMstx_image(mids);
					for(Blob b : blobs){
						int size = (int)b.length();
						byte[] bs = b.getBytes(1, (int)size);
						dout.writeInt(size);//д���ֽ�����ĳ���
						dout.write(bs);//���ֽ����鷢�͵��ͻ���
						dout.flush();//��ջ�����,��֤֮ǰ�����ݷ��ͳ�ȥ
					}
				}
				else if(msg.startsWith("<#FAVOURITE#>")){//�����ҵ��ղ�
					msg=msg.substring(13);//��ȡ�Ӵ�
					ArrayList<MstxInfo> mstxInfos = DBUtil.getFavourite(msg);
					dout.writeUTF("<#FAVOURITEINFO#>"+mstxInfos.size());//��ͻ��˷�������������
					for(MstxInfo mi : mstxInfos){//ѭ����֯�ַ������͵��ͻ���
						dout.writeUTF(mi.getInfo_title()+"|"+mi.getInfo_dis()+"|"+mi.getInfo_lon()+"|"+
							mi.getInfo_lat()+"|"+mi.getInfo_time()+"|"+mi.getUid()+"|"+mi.getMid());
					}	
					int[] mids = new int[mstxInfos.size()];//�������MID����
					for(int i=0; i<mids.length; i++){
						mids[i] = Integer.parseInt(mstxInfos.get(i).getMid());
					}
					
					ArrayList<Blob> blobs = DBUtil.getMstx_image(mids);//�õ���Ҫ��ͼƬ�б�
					for(Blob b : blobs){
						int size = (int)b.length();
						byte[] bs = b.getBytes(1, (int)size);
						dout.writeInt(size);//д���ֽ�����ĳ���
						dout.write(bs);//���ֽ����鷢�͵��ͻ���
						dout.flush();//��ջ�����,��֤֮ǰ�����ݷ��ͳ�ȥ
					}					
				}
				else if(msg.startsWith("<#RECOMMEND#>")){//�����Ƽ�
					ArrayList<MstxInfo> mstxInfos = DBUtil.getMstx_recommend();
					dout.writeUTF("<#RECOMMENDINFO#>"+mstxInfos.size());//��ͻ��˷�������������
					for(MstxInfo mi : mstxInfos){//ѭ����֯�ַ������͵��ͻ���
						dout.writeUTF(mi.getInfo_title()+"|"+mi.getInfo_dis()+"|"+mi.getInfo_lon()+"|"+
							mi.getInfo_lat()+"|"+mi.getInfo_time()+"|"+mi.getUid()+"|"+mi.getMid());
					}	
					int[] mids = new int[mstxInfos.size()];//�������MID����
					for(int i=0; i<mids.length; i++){
						mids[i] = Integer.parseInt(mstxInfos.get(i).getMid());
					}
					
					ArrayList<Blob> blobs = DBUtil.getMstx_image(mids);
					for(Blob b : blobs){
						int size = (int)b.length();//�õ�ͼƬ�ĳ���
						byte[] bs = b.getBytes(1, (int)size);//��Blobת���ɳ��ֽ�����
						dout.writeInt(size);//д���ֽ�����ĳ���
						dout.write(bs);//���ֽ����鷢�͵��ͻ���
						dout.flush();//��ջ�����,��֤֮ǰ�����ݷ��ͳ�ȥ
					}
				}
				else if(msg.startsWith("<#DELETEMSTXCOL#>")){//ɾ���ղ�
					msg=msg.substring(17);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					DBUtil.deleteMstxCol(str[0], str[1]);//ɾ�����ݿ��еĸ��ղ�
				}
				else if(msg.startsWith("<#INSERTMSTXCOL#>")){//�ղض���
					msg=msg.substring(17);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					DBUtil.insertMstxCol(str[0], str[1], "");//����������Ϊ���ۣ��˴���û��ʵ��
				}
				else if(msg.startsWith("<#INSERTMSTXINFO#>")){
					msg=msg.substring(18);//��ȡ�Ӵ�
					String[] str = msg.split("\\|");//�ָ��ַ���
					int mid = DBUtil.insertMstx_info(str[0], str[1], str[2], str[3], str[4],str[5],Integer.parseInt(str[6]),2,str[7]);

					int size = din.readInt();//��ȡͼƬ����ĳ���
					byte[] image = new byte[size];//����ͼƬ����
					din.read(image);//��ȡͼƬ����

					DBUtil.insertMstx_image(image, mid);//���뵽���ݿ�
				}
				else if(msg.startsWith("<#MSTXSORT#>")){//��ȡ��ʳ����
					msg=msg.substring(12);//��ȡ�Ӵ�
					List<String[]> sorts = DBUtil.getMstx_sort();
					dout.writeUTF("<#MSTXSORTINFO#>"+sorts.size()+"|"+msg);//��ͻ��˷�������������
					for(String[] sort : sorts){//ѭ����֯�ַ������͵��ͻ���
						dout.writeUTF(sort[0] + "|" + sort[1]);
					}					
				}
			}
			catch(EOFException e){//�����쳣
				System.out.println("Client Down");
				flag = false;
				try{
					if(din != null){
						din.close();
						din = null;
					}
				}
				catch(Exception el){
					el.printStackTrace();
				}
				try{
					if(dout != null){
						dout.close();
						dout = null;
					}
				}
				catch(Exception el){
					el.printStackTrace();
				}
				try{
					if(sc != null){
						sc.close();
						sc = null;
					}
				}
				catch(Exception el){
					el.printStackTrace();
				}		
			}
			catch(Exception e){//�����쳣
				e.printStackTrace();//��ӡ�쳣
			}
		}
	}
	public void setFlag(boolean flag){//ѭ����־λ�����÷���
		this.flag = flag;
	}
}
