package ytl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
public class ServerAgent extends Thread{
	Socket sc;//声明Socket的引用
	ServerThread father;//声明ServerThread的引用
	DataInputStream din = null;//输入流
	DataOutputStream dout = null;//输出流
	private boolean flag=true;//循环变量 
	public ServerAgent(Socket sc,ServerThread father){//构造器
		this.sc=sc;//得到Socket
		this.father=father;//得到ServerThread的引用
		try{
			din=new DataInputStream(sc.getInputStream());//输入流
			dout=new DataOutputStream(sc.getOutputStream());//输入出流
		}
		catch(Exception e){//捕获异常
			e.printStackTrace();//打印异常
		}
	}
	public void run(){
		while(flag){//循环
			try{
				String msg=din.readUTF();//收消息 
				System.out.println("Client msg = " + msg);
				if(msg.startsWith("<#LOGIN#>")){//登录动作
					msg=msg.substring(9);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					String u_name = DBUtil.checkUser(str[0], str[1]);//检查数据库中是否含有该用户
					if(u_name == null){//当u_name为空时，登录失败
						System.out.println("u_name == null");
						dout.writeUTF("<#LOGINERROR#>");//通知手机客户端登录失败
					}
					else {//登录成功
						System.out.println(u_name+" <#LOGINOK#>");
						dout.writeUTF("<#LOGINOK#>"+u_name);//登录成功，将用户名返回
					}
					break;
				}
				else if(msg.startsWith("<#REGISTER#>")){//注册动作
					msg=msg.substring(12);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					int uid = DBUtil.insertUser(str[0], str[2], str[1], str[3], str[4]);
					dout.writeUTF("<#REGISTEROK#>"+uid+"|"+str[0]);//向客户端发送用户ID和用户名
					break;
				}
				else if(msg.startsWith("<#ClientDown#>")){//客户端下线
					try{
						din.close();//关闭输入流
						dout.close();//关闭输出流
						sc.close();//关闭Socket
						flag = false;
					}
					catch(Exception e){//捕获异常
						e.printStackTrace();//打印异常
					}
				}
				else if(msg.startsWith("<#SEARCH#>")){//搜索美食信息动作
					msg=msg.substring(10);//截取子串
					String[] str = msg.split("\\|");//分割字符串infoValues、searchSort、startPrice、endPrice、span、currentPageNo
					
					int totleNumber = DBUtil.getMstxInfoCountForPhone(str[0], Integer.parseInt(str[1]), str[2], str[3]);
					
					List<String[]> mstxInfos = DBUtil.getMstxInfoForPhone(str[0], Integer.parseInt(str[1]), 
								str[2], str[3], Integer.parseInt(str[4]), Integer.parseInt(str[5]));
					dout.writeUTF("<#SEARCHINFO#>"+mstxInfos.size()+"|"+totleNumber);//向客户端发送搜索的数据
					for(String[] mi : mstxInfos){//循环组织字符串发送到客户端
						dout.writeUTF(mi[0]+"|"+mi[1]+"|"+mi[2]+"|"+mi[3]+"|"+
								mi[4]+"|"+mi[5]+"|"+mi[6]+"|"+mi[7]);
					}					
					int[] mids = new int[mstxInfos.size()];//创建存放MID数组
					for(int i=0; i<mids.length; i++){
						mids[i] = Integer.parseInt(((String[])mstxInfos.get(i))[7]);
					}					
					
					ArrayList<Blob> blobs = DBUtil.getMstx_image(mids);
					for(Blob b : blobs){
						int size = (int)b.length();
						byte[] bs = b.getBytes(1, (int)size);
						dout.writeInt(size);//写入字节数组的长度
						dout.write(bs);//将字节数组发送到客户端
						dout.flush();//清空缓冲区,保证之前的数据发送出去
					}
				}
				else if(msg.startsWith("<#FAVOURITE#>")){//搜索我的收藏
					msg=msg.substring(13);//截取子串
					ArrayList<MstxInfo> mstxInfos = DBUtil.getFavourite(msg);
					dout.writeUTF("<#FAVOURITEINFO#>"+mstxInfos.size());//向客户端发送搜索的数据
					for(MstxInfo mi : mstxInfos){//循环组织字符串发送到客户端
						dout.writeUTF(mi.getInfo_title()+"|"+mi.getInfo_dis()+"|"+mi.getInfo_lon()+"|"+
							mi.getInfo_lat()+"|"+mi.getInfo_time()+"|"+mi.getUid()+"|"+mi.getMid());
					}	
					int[] mids = new int[mstxInfos.size()];//创建存放MID数组
					for(int i=0; i<mids.length; i++){
						mids[i] = Integer.parseInt(mstxInfos.get(i).getMid());
					}
					
					ArrayList<Blob> blobs = DBUtil.getMstx_image(mids);//得到需要的图片列表
					for(Blob b : blobs){
						int size = (int)b.length();
						byte[] bs = b.getBytes(1, (int)size);
						dout.writeInt(size);//写入字节数组的长度
						dout.write(bs);//将字节数组发送到客户端
						dout.flush();//清空缓冲区,保证之前的数据发送出去
					}					
				}
				else if(msg.startsWith("<#RECOMMEND#>")){//搜索推荐
					ArrayList<MstxInfo> mstxInfos = DBUtil.getMstx_recommend();
					dout.writeUTF("<#RECOMMENDINFO#>"+mstxInfos.size());//向客户端发送搜索的数据
					for(MstxInfo mi : mstxInfos){//循环组织字符串发送到客户端
						dout.writeUTF(mi.getInfo_title()+"|"+mi.getInfo_dis()+"|"+mi.getInfo_lon()+"|"+
							mi.getInfo_lat()+"|"+mi.getInfo_time()+"|"+mi.getUid()+"|"+mi.getMid());
					}	
					int[] mids = new int[mstxInfos.size()];//创建存放MID数组
					for(int i=0; i<mids.length; i++){
						mids[i] = Integer.parseInt(mstxInfos.get(i).getMid());
					}
					
					ArrayList<Blob> blobs = DBUtil.getMstx_image(mids);
					for(Blob b : blobs){
						int size = (int)b.length();//得到图片的长度
						byte[] bs = b.getBytes(1, (int)size);//将Blob转换成成字节数组
						dout.writeInt(size);//写入字节数组的长度
						dout.write(bs);//将字节数组发送到客户端
						dout.flush();//清空缓冲区,保证之前的数据发送出去
					}
				}
				else if(msg.startsWith("<#DELETEMSTXCOL#>")){//删除收藏
					msg=msg.substring(17);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					DBUtil.deleteMstxCol(str[0], str[1]);//删除数据库中的该收藏
				}
				else if(msg.startsWith("<#INSERTMSTXCOL#>")){//收藏动作
					msg=msg.substring(17);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					DBUtil.insertMstxCol(str[0], str[1], "");//第三个参数为评论，此处并没有实现
				}
				else if(msg.startsWith("<#INSERTMSTXINFO#>")){
					msg=msg.substring(18);//截取子串
					String[] str = msg.split("\\|");//分割字符串
					int mid = DBUtil.insertMstx_info(str[0], str[1], str[2], str[3], str[4],str[5],Integer.parseInt(str[6]),2,str[7]);

					int size = din.readInt();//读取图片数组的长度
					byte[] image = new byte[size];//创建图片数组
					din.read(image);//读取图片数组

					DBUtil.insertMstx_image(image, mid);//插入到数据库
				}
				else if(msg.startsWith("<#MSTXSORT#>")){//获取美食种类
					msg=msg.substring(12);//截取子串
					List<String[]> sorts = DBUtil.getMstx_sort();
					dout.writeUTF("<#MSTXSORTINFO#>"+sorts.size()+"|"+msg);//向客户端发送搜索的数据
					for(String[] sort : sorts){//循环组织字符串发送到客户端
						dout.writeUTF(sort[0] + "|" + sort[1]);
					}					
				}
			}
			catch(EOFException e){//捕获异常
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
			catch(Exception e){//捕获异常
				e.printStackTrace();//打印异常
			}
		}
	}
	public void setFlag(boolean flag){//循环标志位的设置方法
		this.flag = flag;
	}
}
