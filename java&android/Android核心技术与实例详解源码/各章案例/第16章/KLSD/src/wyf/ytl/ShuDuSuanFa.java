package wyf.ytl;
public class ShuDuSuanFa{
	int[][] n = new int[9][9];//存储数字的数组
    int[] num = {1,2,3,4,5,6,7,8,9};//生成随机数字的源数组，随机数字从该数组中产生
	public boolean checkLine(int col){// 检查列是否符合要求  
	    for(int j = 0;j < 8;j++){  
	        if(n[j][col] == 0){
	                continue;
	        }
	        for(int k =j + 1;k< 9;k++){
	            if(n[j][col] == n[k][col]){
	                return false;
	            }
	        }
	    }
	   return true;
	}
	public boolean checkNine(int row,int col){//检查3X3区域是否符合要求
		int j = row/3*3;//获得左上角的坐标
		int k = col/3*3;
		for(int i = 0;i < 8;i++){//循环比较
			if(n[j + i/3][k + i % 3] == 0){
				continue;
			}
			for(int m = i+ 1;m < 9;m++){
				if(n[j + i/3][k + i % 3] == n[j + m/3][k + m % 3]){
					return false;
                }
			}
		}
		return true;
	}    
    public boolean checkRow(int row){//检查行是否符合要求
    	for(int j = 0;j < 8;j++){
    		if(n[row][j] == 0){
    			continue;
    		}
    		for(int k =j + 1;k < 9;k++){
    			if(n[row][j] == n[row][k]){
    				return false;
    			}
    		}
    	}
    	return true;
	}
	public int generateNum(int row,int col,int time){//产生1-9之间的随机数字
		if(time == 0){//第一次尝试时，初始化随机数字源数组
			for(int i = 0;i < 9;i++){
				num[i] = i + 1;
			}
		}
		//第10次填充，表明该位置已经卡住，则返回0，由主程序处理退回
		if(time == 9){
			return 0;
		}  
		//不是第一次填充
		//生成随机数字，该数字是数组的下标，取数组num中该下标对应的数字为随机数字
		int ranNum = (int)(Math.random()*(9-time));
		//把数字放置在数组倒数第time个位置，
		int temp = num[8 - ranNum];
		num[8 - ranNum] = num[ranNum];
		num[ranNum] = temp;
		return num[8 - ranNum];  
	}
	public int[][] getShuDu(){//生成数字
        for(int i = 0;i < 9;i++){
        	int time = 0;//尝试填充的数字次数
        	for(int j = 0;j < 9;j++){//填充数字
        		n[i][j] = generateNum(i,j,time);    
        		//如果返回值为0，则代表卡住，退回处理
				if(n[i][j] == 0){
					if(j > 0){//不是第一列，则倒退一列
      					j-=2;
						continue;
					}else{//是第一列，则倒退到上一行的最后一列
                        i--;
                        j = 8;
                        continue;
                    }
				} 
				if(isCorret(i,j)){//成功
					time = 0;
				}else{
					time++;
					j--;
				}    
			}             
		}	
    	return n;
    }
	public boolean isCorret(int row,int col){//是否满足行、列和九宫区域不重复的要求
          return (checkRow(row)&checkLine(col)&checkNine(row,col));
    } 
}