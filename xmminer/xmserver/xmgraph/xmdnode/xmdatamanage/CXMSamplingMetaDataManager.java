// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
//																				//
//		Title : XM-Miner ���� ���ø� ���										//
//	 	2001�� 3�� 26�� ���� ���� (by ������)									//
//		Last update : 2001�� 3�� 26�� ������									//
//			- ��ȭ ������ ���� �����ͼ��� �÷� ���� �о���� ������				//
//																				//
//////////////////////////////////////////////////////////////////////////////////

package xmminer.xmserver.xmgraph.xmdnode.xmdatamanage;

import java.util.*;
import xmminer.xmlib.xmfileprocess.*;
import xmminer.xmlib.xmtable.*;


public class CXMSamplingMetaDataManager
{
  private CXMMetaDataReader cdr;  
  private CXMMetaDataSaver cds1; 
  private CXMMetaDataSaver cds2; 
  private String project;
  private String node;
  private String p_arc;
  private String n_arc;  
  private String path;
  private String root_path = System.getProperty("minerdir") + "/xmminer/user/";//"/XM-Miner/xmminer/user"); ���� 2002.09.22
  private String meta_path = "/meta/";   
  private int total_rows;
  private int new_row_num; 
  private int selected_index;
  private int cbox_idx;  
  private int clustering_number;
  
  // Modified by Sun Jee Hun ----------------------------------------------------//
  private String[] column_list;
  private String[] column_index;
  // ----------------------------------------------------------------------------//

  public CXMSamplingMetaDataManager()
  {}

  public int setMetaData(String project_name, String previous_arc, String next_arc, String node_name)
  {
    project = project_name;
    p_arc = previous_arc;
    n_arc = next_arc;   
    node = node_name;                  
    path = root_path + project + meta_path;
    return(setInitStatus());          
  }
  
  public void close()
  {  	
  	saveMetaData();
  }  

  private int setInitStatus()
  {    
      try
      {
          cdr = new CXMMetaDataReader();
          cdr.setFileStatus(project,p_arc);
          total_rows = Integer.parseInt(cdr.getProfile("NUMBER_OF_ROWS"));

		  //Modified by Sun Jee Hun --------------------------------------------------//
		  // ��ȭ����� �ʿ��� �÷��� ���� ����(�÷��̸�, ���� ���)�� �������� ���Ѱ�.
		  column_list = cdr.getProfiles("COLUMN_LIST");
		  // -------------------------------------------------------------------------//

          cdr.close();
          CXMFileCopy cfc = new CXMFileCopy();
          cfc.fileCopy(path+p_arc+".meta",path+n_arc+".meta");                   
          return(0); 
      } catch (Exception ie)
         {
             System.out.println(node+"�� ���� VNODE ��Ÿ ������ �������� �ʽ��ϴ�");
             return(1);
         }
  }

  private void saveMetaData()
  {    
    setPreviousArcMetaData();
    setNextArcMetaData();
  }   
  
  private void setPreviousArcMetaData()
  {
    cds1 = new CXMMetaDataSaver();
    cds1.setFileStatus(project,p_arc);    
    cds1.setProfile("NEXT_NODE",node);      
    cds1.close();	
  }
  
  private void setNextArcMetaData()
  {
    cds2 = new CXMMetaDataSaver();
    cds2.setFileStatus(project,n_arc);
    cds2.setProfile("PREVIOUS_NODE",node);
    cds2.deleteProfile("NEXT_NODE");    
    cds2.setProfile("ROW_INDEX",n_arc);
    cds2.setProfile("NUMBER_OF_ROWS",String.valueOf(new_row_num));  
    cds2.setProfile("sampling_index",String.valueOf(selected_index));
    cds2.setProfile("sampling_select_option",String.valueOf(cbox_idx));
    if (selected_index==1)
    {
        cds2.setProfile("sampling_cluster_number",String.valueOf(clustering_number));
    }    
    cds2.close(); 	
  } 

  public int getTotalRows()
  {
    return total_rows;
  }

  public void setNewRowNum(int i_num)
  {
    new_row_num = i_num;
  }
  
  public void setSelectedIndex(int i_num)
  {
    selected_index = i_num;
  }
  
  public void setCboxIndex(int i_num)
  {
    cbox_idx = i_num;
  }
  
  public void setClusteringNumber(int i_num)
  {
    clustering_number = i_num;
  }
  
  public String getPreviousArc()
  {
    return p_arc;
  }

  public String getNextArc()
  {
    return n_arc;
  }   

// Modified by Sun Jee Hun ----------------------------------------------------//
  public String [] getColumnList()
  {
	  return column_list;
  }
// ----------------------------------------------------------------------------//

}