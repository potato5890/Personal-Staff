package wyf.ytl;
public class ShuDuSuanFa{
	int[][] n = new int[9][9];//�洢���ֵ�����
    int[] num = {1,2,3,4,5,6,7,8,9};//����������ֵ�Դ���飬������ִӸ������в���
	public boolean checkLine(int col){// ������Ƿ����Ҫ��  
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
	public boolean checkNine(int row,int col){//���3X3�����Ƿ����Ҫ��
		int j = row/3*3;//������Ͻǵ�����
		int k = col/3*3;
		for(int i = 0;i < 8;i++){//ѭ���Ƚ�
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
    public boolean checkRow(int row){//������Ƿ����Ҫ��
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
	public int generateNum(int row,int col,int time){//����1-9֮����������
		if(time == 0){//��һ�γ���ʱ����ʼ���������Դ����
			for(int i = 0;i < 9;i++){
				num[i] = i + 1;
			}
		}
		//��10����䣬������λ���Ѿ���ס���򷵻�0�������������˻�
		if(time == 9){
			return 0;
		}  
		//���ǵ�һ�����
		//����������֣���������������±꣬ȡ����num�и��±��Ӧ������Ϊ�������
		int ranNum = (int)(Math.random()*(9-time));
		//�����ַ��������鵹����time��λ�ã�
		int temp = num[8 - ranNum];
		num[8 - ranNum] = num[ranNum];
		num[ranNum] = temp;
		return num[8 - ranNum];  
	}
	public int[][] getShuDu(){//��������
        for(int i = 0;i < 9;i++){
        	int time = 0;//�����������ִ���
        	for(int j = 0;j < 9;j++){//�������
        		n[i][j] = generateNum(i,j,time);    
        		//�������ֵΪ0�������ס���˻ش���
				if(n[i][j] == 0){
					if(j > 0){//���ǵ�һ�У�����һ��
      					j-=2;
						continue;
					}else{//�ǵ�һ�У����˵���һ�е����һ��
                        i--;
                        j = 8;
                        continue;
                    }
				} 
				if(isCorret(i,j)){//�ɹ�
					time = 0;
				}else{
					time++;
					j--;
				}    
			}             
		}	
    	return n;
    }
	public boolean isCorret(int row,int col){//�Ƿ������С��к;Ź������ظ���Ҫ��
          return (checkRow(row)&checkLine(col)&checkNine(row,col));
    } 
}