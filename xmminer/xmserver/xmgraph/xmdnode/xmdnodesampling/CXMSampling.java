
//Title:        Data Manager
//Version:
//Copyright:    Copyright (c) 1999
//Author:       �̽���
//Company:      ��������ý���
//Description:  Your description
package xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling;

import java.io.*;
import java.lang.Math;
//import java.util.Random;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;
import xmminer.xmlib.xmtable.*;
import xmminer.xmserver.xmgraph.xmdnode.xmredundantcheck.*;


public class CXMSampling //Sampling Class
{  
  private String temp_row_num;
  private String n_arc;
  private String p_arc;
  private int total_rows=0;
  private int data_limit = 0;
  private int s_cnt = 0;
  private Vector v = new Vector();
  private CXMIndexSaver cis;
  private CXMRedundantCheck crc;

  public void setTotalRows(int i_num)
  {
    total_rows = i_num;    
  }

  public void setArc(String new_arc, String pre_arc)
  {
    n_arc = new_arc;
    p_arc = pre_arc;    
  }

  public int getTotalRows()
  {
    return total_rows;
  }

// Modified by Sun Jee Hun ---------------------//
// ü���� ǥ�� ���⿡�� ���Ǵ� K���� ���(��ü�ڷ� / ���ü�)
  public int calKValue(int sampling_number)
  {
	int K = total_rows / sampling_number;
	return K;
  }
// ---------------------------------------------//


  public int calNumToPer(int i_n) //����ڰ� �Է��� ������ ������� �ٲپ� ��
  {
    float s_p = ((i_n*100)/total_rows);
    int r_s_p = java.lang.Math.round(s_p); //������� �ݿø�
    return r_s_p;
  }

  public int calPerToNum(int i_p) //����ڰ� �Է��� ������� ������ �ٲپ� ��
  {
    float s_r = (total_rows*i_p)/100;
    int r_s_r = java.lang.Math.round(s_r); //������ �ݿø�
    return r_s_r;
  }
  
  public void setPreSamplingStatus(String project_name, int cbox_idx)
  {    
    if (cbox_idx==0)
    {
      crc = new CXMRedundantCheck(project_name, getByteSize()); //total number�� setting�� �� ����
      crc.fileOpen();
    }
    cis = new CXMIndexSaver();
    cis.setFileStatus(project_name, n_arc,p_arc,total_rows);
  }
  
  public void setIndexFile()
  {
     crc.fileClose();
     crc.fileOpen();     
  }
  
  public void setPostSamplingStatus(int cbox_idx)
  {
     if (cbox_idx==0)
     {
     	crc.fileClose();
     }
     cis.close();  	
  }
  
  public void indexDataCreate(int i_int)
  {
     crc.dataInsert(i_int);
  }
  
  public boolean dataFind(int i_int)
  {
  	return crc.dataFind(i_int);
  }
  
  public void setNumberIndex(int i_int)
  {
     cis.setNumberIndex(i_int);
  }

  public int getByteSize()
  {
    int temp_value = total_rows;
    int quot;
    int mod;
    int cnt = 0;
    do
    {
      quot = temp_value/128;
      mod = temp_value%128;
      temp_value = quot;
      cnt++;
    } while(quot!=0);
    return cnt;
  }  
   
}




