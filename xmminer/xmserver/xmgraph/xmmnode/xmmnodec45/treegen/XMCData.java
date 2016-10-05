
//Title:        C45_Classifier
//Version:
//Copyright:    Copyright (c) 1999
//Author:       �ִ��
//Company:      ���ϴ��б� ������а� ���������ý���
//Description:   data�� �ϰ� �����ϱ� ���� Ŭ���� C45�� �ֻ��� Ŭ����

////////////////////////////////////////////////////////////////////////////////
//
//
//
////////////////////////////////////////////////////////////////////////////////

package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.treegen;

import java.util.*;
import java.io.*;
import java.lang.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class XMCData
{
  public final static int    IGNORE1=1;       //Ư���� Į���� ������� ���� ���
  public final static int    DISCRETE=2;      //Į���� Data Type�� �������� �������� ����
  public final static int    CONTINUOUS=3;    //Į���� Data Type�� �������� �������� ����
  public final static double Unknown= -999;   //data���� �� ��� -999�� �Ҵ�
  public final static int    LEARN=101;       //data�� tree�� �����ϱ� ���� data�� ���
  public final static int    TEST=102;        //data�� tree�� �׽�Ʈ�ϱ� ���� data�� ���
  public final static int    EXCUTION=103;    //������ ���� data�� ���

  public final static int    TREE_BEFORE=1000; //Tree�� �����ϱ� ��
  public final static int    TREE_LEARN=1001;  //Tree�� �����ǰ�, ���Ͽ� ����� ����
  public final static int    TREE_LEARN_WITH_PRUNE=1002;  ////Tree�� �����ǰ�, pruning�Ǿ� ���Ͽ� ����� ����
  public final static int    RULE_BEFORE=2000;
  public final static int    RULE_LEARN_MODE=2001;
  public final static int    RULE_LEARN_WITH_PRUNE=2002;
  public static final String NOINPUTDATA                      = new String("NO INPUT DATA");
  public static final String NOOUTPUTDATA                     = new String("NO OUTPUT DATA");

  //meta ������ ������ �����ϱ� ���� �κ�
  public  int		   MaxAtt,          // input Į���� ���� ����
                   MaxClass,        // output Į���� data set�� ũ��
                   MaxDiscrVal;     // �� Į���� ���߿��� ���� ū data set ��
                                     // ���� ū ������ �迭�� �޸𸮸� �Ҵ��ϱ� ����
  public  int      MaxAttVal[];      //�� Į���� data set�� ũ�⸦ ����
  public  int		   MaxItem;          //CXMTable�� row number
  public  int      SpecialStatus[];  //�� Į���� data type :DISCRETE,CONTINUOUS,IGNORE1
  public  String   ClassName[];      //OUPUT Į���� data set�� ����
  public  String   ClassColName;     //OUPUT Į���� �̸��� ����
  public  String   AttName[];        //INPUT Į���� �̸��� ����
  public  String   AttValName[][];   //INPUT Į���� data set�� ����

  public  int      DataMode=LEARN;           //data�� Ʈ���� �����ϱ� ���� ������ �ƴϸ�
                                             //�׽�Ʈ�� ���� ������ ���� �� �Ǵ�
  public  int      TreeMode=TREE_BEFORE;     //data�� Ʈ���� �����ϱ� ���� ������ �ƴϸ�
                                             //�׽�Ʈ�� ���� ������ ���� �� �Ǵ�

  public String m_sProjectName=new String("testproject1");
  public String m_sModelName=new String("UtitledModel");
  public String m_sArcName;
  public String m_sLearnedArcName;
  public String m_sOutputArcName;
  public String[] m_sColumnList;
  public boolean m_bTestFlag;
  public int    m_iClassColumn;

  CXMC45TableSaver CDataSaver = null;
  CXMC45TableManager m_cC45DataTableManager=null;



////////////////////////////////////////////////////////////////////////////////
//
// ������
// public XMCData()
// public XMCData(String ProjectName, String Modelname)
// public XMCData(String ProjectName, String Modelname, String ArcName,String[] ColumnList,boolean test_flag)
//
////////////////////////////////////////////////////////////////////////////////


  public XMCData()
  {

  }
  public XMCData(String ProjectName, String Modelname)
  {
        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
  }
  public XMCData(String ProjectName, String Modelname, String ArcName,String[] ColumnList,boolean test_flag)
  {

        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
        m_sArcName=ArcName;
        m_sColumnList=ColumnList;
        m_bTestFlag=test_flag;
  }

  public void SetXMCData(String ProjectName, String Modelname, String ArcName,String[] ColumnList,boolean test_flag)
  {

        m_sProjectName = ProjectName;
        m_sModelName= Modelname;
        m_sArcName=ArcName;
        m_sColumnList=ColumnList;
        m_bTestFlag=test_flag;
  }
  public void SetDataUsage(boolean test_flag)
  {
       m_bTestFlag=test_flag;
  }



  public int ReadParameter_Data()//String m_sProjectName,String ms_modelname)
  {
    try
    {
      int i;
      CXMMetaDataReader Rcmdr= new CXMMetaDataReader();
      Rcmdr.setFileStatus(m_sProjectName,m_sModelName+"_par");
      if(!m_bTestFlag)
      {
          m_sArcName = new String(Rcmdr.getProfile("LEARNDATA"));
          m_sOutputArcName= new String(Rcmdr.getProfile("LEARNRESULT"));
      }
      else
      {
          m_sArcName= new String(Rcmdr.getProfile("TESTDATA"));
          m_sLearnedArcName= new String(Rcmdr.getProfile("LEARNED_ARCNAME"));
          m_sOutputArcName= new String(Rcmdr.getProfile("TESTRESULT"));
      }


      m_sColumnList=Rcmdr.getProfiles("COLUMN_LIST");

      Rcmdr.close();

      System.out.println( "m_sArcName  :"+m_sArcName);
      for(i=0;i<m_sColumnList.length;i++)
      {
          System.out.println( i+"'s cloumn  :"+m_sColumnList[i]);
      }
      
      return(0);
    }
      catch (Exception e) {
    	e.printStackTrace();
    	return(1);
    }
  }



  public void MakeDataTable()
  {
      CDataSaver = new CXMC45TableSaver();
      CDataSaver.setFileStatus(m_sProjectName, m_sArcName,m_sModelName, m_bTestFlag);
      if(m_bTestFlag)CDataSaver.setLearnedArcName(m_sLearnedArcName);
      CDataSaver.createC45Table(m_sColumnList);
      m_cC45DataTableManager = new  CXMC45TableManager();
      m_cC45DataTableManager.setFileStatus(m_sProjectName, m_sArcName,m_sModelName, m_bTestFlag);
  }

  public void SettingValue()
  {
      int i;
	  if( CDataSaver==null)  MakeDataTable();
      MaxAtt =  m_sColumnList.length-2;
      MaxItem = CDataSaver.GetMaxItem()-1;
      SpecialStatus= CDataSaver.GetSpecialStatus();
      MaxAttVal= CDataSaver.GetMaxAttVal();
      MaxClass= MaxAttVal[MaxAtt+1];
      for(i=0;i<SpecialStatus.length;i++)
	        System.out.println("SpecialStatus"+i+"="+SpecialStatus[i]);   
		 
	  System.out.println("MaxAtt="+MaxAtt+" MaxItem="+MaxItem+ "MaxAttVal="+MaxAttVal);
      ClassColName= new String(m_sColumnList[m_sColumnList.length-1]);

      AttName= new String[MaxAtt+2];
      MaxDiscrVal=-1;
      m_iClassColumn=MaxAtt+2;
      for(i=0;i<=MaxAtt;i++)
      {
          AttName[i]=new String(m_sColumnList[i]);
          if( MaxAttVal[i]>=  MaxDiscrVal)   MaxDiscrVal=MaxAttVal[i];
          System.out.println("MaxAttVal["+i+"]="+MaxAttVal[i]);
      }
      System.out.println("MaxAtt="+MaxAtt+" MaxItem="+MaxItem+ " m_iClassColumn="+m_iClassColumn+ "MaxClass="+MaxClass);
      CDataSaver.close();


  }

public String GetAttValName(int m_iAtt,int m_iAttVal)
{
    //System.out.println("GetAttValName#1"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal+"  m_sLearnedArcName="+m_sLearnedArcName);
    String m_sReturn=null;
    CXMC45TransValueReader m_cTransValueReader = new CXMC45TransValueReader();
    //System.out.println("GetAttValName#2"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal);
    if(m_bTestFlag)  m_cTransValueReader.setLearnedArcName(m_sLearnedArcName);
    m_cTransValueReader.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,AttName[m_iAtt],m_bTestFlag);
    //System.out.println("GetAttValName#3"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal);

	   m_sReturn=m_cTransValueReader.getTransValue(m_iAttVal);
     //System.out.println("GetAttValName#4"+ " m_iAtt   "+AttName[m_iAtt]+" m_iAttVal"+m_iAttVal);
	if(m_sReturn==null)
    m_sReturn= new String("null");
  //System.out.println("GetAttValName#5"+ " m_sReturn=   "+m_sReturn+" m_iAttVal"+m_iAttVal);
	m_cTransValueReader.close();
    return m_sReturn;
}
public String GetClassValName(int m_iAttVal)
{
    //System.out.println("GetClassValName#1"+" m_iAttVal"+m_iAttVal+"   ClassColName="+ClassColName+"  m_sLearnedArcName="+m_sLearnedArcName);
    String m_sReturn=null;
    CXMC45TransValueReader m_cTransValueReader = new CXMC45TransValueReader();
    if(m_bTestFlag)  m_cTransValueReader.setLearnedArcName(m_sLearnedArcName);
    //System.out.println("GetClassValName#2"+" m_iAttVal"+m_iAttVal+"   ClassColName="+ClassColName);
    m_cTransValueReader.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,ClassColName,m_bTestFlag);
    //System.out.println("GetClassValName#3"+" m_iAttVal"+m_iAttVal+"   ClassColName="+ClassColName);


	  m_sReturn=m_cTransValueReader.getTransValue(m_iAttVal);
    //System.out.println("GetClassValName#4"+" m_sReturn="+m_sReturn+"   ClassColName="+ClassColName);
	  if(m_sReturn==null)
      m_sReturn= new String("null");
	    m_cTransValueReader.close();
    return m_sReturn;
}

public void MakeResultTable()
{
//      System.out.println("MakeResultTable in  #1");
      CXMC45ResultFileSaver CResultSaver = new CXMC45ResultFileSaver();
//      System.out.println("MakeResultTable in  #2");
      CResultSaver.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,m_bTestFlag);
//      System.out.println("MakeResultTable in  #3");
      if(m_bTestFlag)CResultSaver.setLearnedArcName(m_sLearnedArcName);
//      System.out.println("MakeResultTable in  #4");
      CResultSaver.setTrainingColumn(ClassColName);
//      System.out.println("MakeResultTable in  #5");
      CResultSaver.createResultFile();
//      System.out.println("MakeResultTable in  #6");
      CResultSaver.close();

//      System.out.println("MakeResultTable in  #7");
      CXMC45OutputTableSaver OutputTableSaver= new  CXMC45OutputTableSaver();
//      System.out.println("MakeResultTable in  #8");
      OutputTableSaver.setFileStatus(m_sProjectName,m_sArcName,m_sModelName,m_sOutputArcName,m_bTestFlag);
//      System.out.println("MakeResultTable in  #9");
      OutputTableSaver.createC45OutputTable();
//      System.out.println("MakeResultTable in  #10");
      OutputTableSaver.close();
//      System.out.println("MakeResultTable in  #11");
//      readData();
}

void readData()
{

    CXMTableReader ctr = new CXMTableReader();
    ctr.setFileStatus(m_sProjectName,m_sOutputArcName);
    System.out.println(  "m_sProjectName="+m_sProjectName+"   m_sOutputArcName="+m_sOutputArcName);
    for (int i=0; i<14; i++)
    {
       System.out.println(ctr.getStringInColumn(i+1,1));
       System.out.println(ctr.getIntInColumn(i+1,2));
       System.out.println(ctr.getIntInColumn(i+1,3));
       System.out.println(ctr.getStringInColumn(i+1,4));
       System.out.println(ctr.getStringInColumn(i+1,5));
       System.out.println(ctr.getStringInColumn(i+1,6));
    }

}
public int m_iPbCount_num=0;
int m_iPbInterVal=1;
int m_idivice=1;
int m_iOldReturn=0;

public int GetPBValue()
{
  int m_iReturn=0;
  m_iReturn=m_iPbCount_num/m_idivice;

  int temp=(m_iPbInterVal*5-10>0?m_iPbInterVal*5-10-10:0);
  if(m_iReturn>temp)
  {
      m_iReturn=m_iOldReturn;
	  m_idivice+=100;
  }
  else
  {
     if(m_iOldReturn>m_iReturn)
	   m_iReturn=m_iOldReturn;
     else
	   m_iOldReturn=m_iReturn;  
  }
   return m_iReturn;
}

public void SetPBInterVal(int interval)
{
   m_iPbCount_num=0;
   m_iPbInterVal=interval;
   m_idivice=1000;
   m_iOldReturn=0;
}

//end of class
}