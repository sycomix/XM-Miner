
//Title:        XM-Miner
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Yong Uk Song
//Company:      DIS
//Description:  XM-Miner

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
//																				//
//		Title : XM-Miner Ŭ���̾�Ʈ ���ø� ��ü									//
//	 	2001�� 3�� 21�� ���� ���� (by ������)									//
//		2001�� 3�� 22�� ����													//
//			   ���� ���ø��� Ŭ������ ���ø�, ü���� ǥ�������� �����Ǿ�����	//
//		Last update : 2001�� 3�� 28�� ������									//
//			- ü���� ǥ������ �Ϸ� (3�� 22��)									//
//			- ��ü�� �߰��� ����(���ø� ���, ������ ���� etc)�� ���� ����		//
//			- Run �޼��忡���� ����ó��											//
//																				//
//////////////////////////////////////////////////////////////////////////////////

package xmminer.xmclient.xmgraph.xmdnode.xmdnodesampling;

import javax.swing.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;

import xmminer.xmlib.*;
import xmminer.xmclient.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodesampling.*;

public class XMDNodeSampling extends XMDNode implements Serializable
{

  //XMDialogDNodeSampling xds;
  public int previous_index;
  public int next_index;
  
  private int total_data_number = 0;	// ��ü ������ ��
  private int sampling_type = 0;	// ������ ��ü�� � ���ø� ����� ���õǾ��� �ִ°��� ���� ����
  private int sampling_number = 0;
  private int cluster_number = 1;
  private int cbox_idx = 0;
  private int sampling_rate = 0;
  private int K = 0;
  private int standard_column = 0;
  private int column_count = 0;

  public String project_name;
  private String previous_arc;
  private String next_arc;
  private String arc = "arc";

  private JOptionPane optionPane;	// ���� �޼��� ����� ���Ѱ�.

// Modified by Sun Jee Hun ///////////////////////////////////////////////////////
  // Constructors (������)
  public XMDNodeSampling(int stat)
  {
    super(stat);
  }

  // �Է� ���� �߻��� ���ڽ� ǥ��
  private void showWarningInputError(JFrame frame, String msg)
  {
	  optionPane.showMessageDialog(frame, msg, "�ʱ�ȭ ����", optionPane.WARNING_MESSAGE);
  }  
  
  // ��ü ������ �� ����
  public void setTotalDataNumber(int tmp)
  {
	  total_data_number = tmp;
  }
  
  // ��ü ������ �� ������
  public int getTotalDataNumber()
  {
	  return total_data_number;
  }
  
  // ���ø� ��� ������ ���� ���� ����
  public void setSamplingType(int tmp)
  {
	sampling_type = tmp;
  }

  // ���ø� ��� ������ ���� ���� ������
  public int getSamplingType()
  {
	return sampling_type;
  }

  // ������ ������ ���� ���� ����
  public void setSamplingNumber(int tmp)
  {
	sampling_number = tmp;
  }

  // ������ ������ ���� ���� ������
  public int getSamplingNumber()
  {
	return sampling_number;
  }

  // ���� ������ ���� ���� ����
  public void setSamplingRate(int tmp)
  {
	sampling_rate = tmp;
  }

  // ���� ������ ���� ���� ������
  public int getSamplingRate()
  {
	return sampling_rate;
  }

  // K�� ���� ���� ����
  public void setKValue(int tmp)
  {
	K = tmp;
  }

  // K�� ���� ���� ������
  public int getKValue()
  {
	return K;
  }

  // ���� ������ ���� ���� ���� (�� �޼���� ��ȭ ����� ���� ���⿡�� �����Ǿ� ���ȴ�.)
  public void setClusterNumber(int tmp)
  {
	cluster_number = tmp;
  }

  // ���� ������ ���� ���� ������ (�� �޼���� ��ȭ ����� ���� ���⿡�� �����Ǿ� ���ȴ�.)
  public int getClusterNumber()
  {
	return cluster_number;
  }

  // ���������ΰ� �ƴѰ��� ���� ���� ����
  public void setCboxValue(int tmp)
  {
	cbox_idx = tmp;
  }

  // ���������ΰ� �ƴѰ��� ���� ���� ������
  public int getCboxValue()
  {
	return cbox_idx;
  }

  // ���� �÷� ����
  public void setStandardColumn(int tmp)
  {
	  standard_column = tmp;
  }
  
  // ���� �÷� ���� ������
  public int getStandardColumn()
  {
	  return standard_column;
  }

  // ��ȭ���⿡�� ���õ� �������� �÷� ���� ����
  public void setColumnCount(int tmp)
  {
	  column_count = tmp;
  }

  // ��ȭ���⿡�� ���õ� �������� �÷� ���� ������
  public int getColumnCount()
  {
	  return column_count;
  }
//////////////////////////////////////////////////////////////////////////////////

  public int OnCreate()
  {
    // Do not touch
    int err;
    if ((err = super.OnCreate()) != 0)
    {
      return(err);
    }
    return(0);
  }

  public int OnDelete()
  {
    // Do not touch
    int err;
    if ((err = super.OnDelete()) != 0)
    {
      return(err);
    }
    return(0);
  }

  public static XMGraphElement Create(int stat)
  {
    return(new XMDNodeSampling(stat));
  }

  public String toString()
  {
    //return(super.toString() + ".XMDNodeSampling");
    return("XMDNodeSampling");
  }

  public String GetText()
  {
    //return(super.toString() + ".XMDNodeSampling");
    return("Sampling");
  }

  // Operation
  public int OnConnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnConnectedFrom(frame, graph, element)) != 0)
    {
      return(err);
    }

    // chkeck validity of connectin from the 'element'
    if (GetInCount(graph) >= 1)   // maximumn in = 1
    {
      return(-1);
    }

    return(0);
  }

  public int OnConnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    int err;
    if ((err = super.OnConnectedTo(frame, graph, element)) != 0)
    {
      return(err);
    }

    // chkeck validity of connectin to the 'element'
    if (GetOutCount(graph) >= 1)  // maximumn out = 1
    {
      return(-1);
    }

    return(0);
  }

  public int OnDisconnectedFrom(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnDisconnectedFrom(frame, graph, element)) != 0)
    {
      return(err);
    }

    return(0);
  }

  public int OnDisconnectedTo(JFrame frame, XMGraph graph, XMGraphElement element)
  {
    // Do not touch
    int err;
    if ((err = super.OnDisconnectedTo(frame, graph, element)) != 0)
    {
      return(err);
    }

    return(0);
  }

  public int SetSchema(XMGraph graph)
  {
    XMVNode inNode;
    if ((inNode = (XMVNode)GetInElement(0, graph)) == null)
    {
      System.out.println("Input node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    XMVNode outNode;
    if ((outNode = (XMVNode)GetOutElement(0, graph)) == null)
    {
      System.out.println("Output node is not defined.");
      return(XMGraphElement.XMGESTAT_RUN_ERROR);
    }
    next_index = GetInElement(0, graph).GetUniqueId();
    return(0);
  }

  // Paint

  //CORBA
  //transient xmminer.xmvbj.xmgraph.xmdnode.xmdnodesampling.XMBDNodeSampling m_sDNodeSampling = null;
  transient xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling.XMBDNodeSampling m_sDNodeSampling = null;

  private void SetCORBA()
  {
  	if (m_sDNodeSampling == null)
  	{
  		m_sDNodeSampling = new xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling.XMBDNodeSampling();
  	}
  }

  // Edit
  public int Edit(JFrame frame, XMGraph graph)
  {
	SetCORBA();
	XMNode errNode;
    if ((errNode = GetSchema(graph)) != null)
    {
      JOptionPane.showMessageDialog(frame, "Cannot get the schema of " + errNode.GetName() + ".");
      return(XMGESTAT_EDIT_NOCHANGE);
    }

	previous_index = GetInElement(0, graph).GetUniqueId();
    next_index = GetOutElement(0, graph).GetUniqueId();
	project_name = graph.GetProjectName();

  	XMDialogDNodeSampling dds = new XMDialogDNodeSampling(frame, "������ ����(Sampling)",true,m_sDNodeSampling,this);
  	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = dds.getSize();
    if (frameSize.height > screenSize.height)
        frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
        frameSize.width = screenSize.width;
    dds.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    dds.setVisible(true);

// Modified by Sun Jee Hun ------------------------------------------------------//
	if (dds.getisModified() == true)	// ����Ʈ �ڽ����� Ȯ���� ������ ��ȭ�� �ִ�.
	{
		return XMGESTAT_EDIT_CRITICALCHANGE;
	}
	else	// �׷��� �ʰ� ��Ҹ� ������ ��ȭ ����.
	{
		return XMGESTAT_EDIT_NOCHANGE;
	}
// ------------------------------------------------------------------------------//
  }

  // Run

  transient Thread m_threadRun = null;
  transient Thread m_threadProgress = null;
  JDialog m_dialog;
  boolean m_bStopped;
  boolean m_bSuccess;
  int m_pbMin;
  int m_pbMax;

  public JDialog CreateDialog(JFrame frame)
  {
    return(new DNodeDialogPrgBar(frame, "������ ����(Sampling)", true, this));
  }

  public class ThreadProgress implements Runnable {
    public void run(){
        JProgressBar progressBar = ((DNodeDialogPrgBar)m_dialog).jProgressBar1;

        int x = m_pbMin;
        int prevx = x;
        progressBar.setMinimum(m_pbMin);
        progressBar.setMaximum(m_pbMax);
        progressBar.setValue(x);
        while(IsRunning())
        {
            x = m_sDNodeSampling.GetPBValue();
            progressBar.setValue(x);
        }
        m_dialog.dispose();
    }
  }

  public class ThreadRun implements Runnable{
    public void run() {
        m_bSuccess = (m_sDNodeSampling.Run() == 0) && !m_bStopped;
    }
  }

  public boolean IsRunning () {
    return (m_sDNodeSampling.IsRunning());
  }

  public void StopRunning(){
    m_sDNodeSampling.StopRunning();
    m_bStopped = true;
  }

// RUN (����)
// Modified by Sun Jee Hun ------------------------------------------------------//
  public int Run(JFrame frame, XMGraph graph)
  {
	try{

		SetCORBA();
		try
		{
			previous_index = GetInElement(0, graph).GetUniqueId();
		}  catch(NullPointerException ne1)
			{
				 showWarningInputError(frame, "�Է� Data Node�� �����ϴ�!");
				 return(1);
			}
		try
		{
			next_index = GetOutElement(0, graph).GetUniqueId();
		} catch (NullPointerException ne2)
		   {
				 showWarningInputError(frame, "��� Data Node�� �����ϴ�!");
				 return(1);
		   }

		project_name = graph.GetProjectName();
		setPreviousArc();
		setNextArc();

		m_dialog = CreateDialog(frame);
		m_dialog.setSize(400, 120);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dialogSize = m_dialog.getSize();
		if (dialogSize.height > screenSize.height)
		  dialogSize.height = screenSize.height;
		if (dialogSize.width > screenSize.width)
		  dialogSize.width = screenSize.width;
		m_dialog.setLocation((screenSize.width - dialogSize.width) / 2, (screenSize.height - dialogSize.height) / 2);
		// GraphElement Server

		m_bStopped = false;
		m_bSuccess = false;   

		int r = m_sDNodeSampling.setFileStatus(project_name,previous_arc, next_arc,"run");
		
		if (r!=0)
		{
			showWarningInputError(frame, "��ü�� ������ �����ϰ� �ʱ�ȭ ���� �ʾҽ��ϴ� !");
			return XMGESTAT_RUN_ERROR;
		}  

		String [] column_list = m_sDNodeSampling.getColumnList();

		//(column_list�� �������� ���� �÷��� ����, column_count�� ��ü�� ���� ������ �ִ� �÷��� �����̴�.
		if (column_count == 0)
		{
			showWarningInputError(frame, "��ü�� ������ �����ϰ� �ʱ�ȭ ���� �ʾҽ��ϴ� !");
			return(1);
		}

		if (column_list.length != column_count) 
		{
			showWarningInputError(frame, "�����Ϳ� ������ �ְų� �����Ͱ� �غ���� �ʾҽ��ϴ� !");
			return (1);
		}
		
		// m_sDNodeSampling.getTotalRows()�� �������� ���� ��ü ������ �� ����, total_data_number��
		// ��ü�� ������ �ִ� ��ü ������ �� �����̴�.
		if (m_sDNodeSampling.getTotalRows() != total_data_number)
		{
			showWarningInputError(frame, "�����Ϳ� ������ �ְų� �����Ͱ� �غ���� �ʾҽ��ϴ� !");
			return (1);
		}

		m_pbMin = m_sDNodeSampling.GetPBMin();
		m_pbMax = m_sDNodeSampling.GetPBMax();

		// Thread
		if (m_threadRun == null || !m_threadRun.isAlive())
		{
		  m_threadRun = new Thread(new ThreadRun());
		  m_threadRun.start();
		}
		if (m_threadProgress == null || !m_threadProgress.isAlive())
		{
		  m_threadProgress = new Thread(new ThreadProgress());
		  m_threadProgress.start();
		}

		m_dialog.show();
		System.out.println("m_bStopped="+m_bStopped);
		System.out.println("m_bSuccess="+m_bSuccess);
		
		return(m_bStopped ? XMGESTAT_RUN_NORUN : XMGESTAT_RUN_SUCCESS);
	}
	catch (Exception e)
	{
		showWarningInputError(frame, "�����Ϳ� ������ �ְų� �����Ͱ� �غ���� �ʾҽ��ϴ� !");
		return (1);
	}
	finally 
    {
		m_sDNodeSampling.close();
    }

  }
// ------------------------------------------------------------------------------//

  private void setPreviousArc()
  {
      previous_arc = arc+previous_index;
  }

  private void setNextArc()
  {
      next_arc = arc+next_index;
  }
}