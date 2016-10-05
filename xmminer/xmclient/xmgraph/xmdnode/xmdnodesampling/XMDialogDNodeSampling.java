// Modified by Sun Jee Hun ///////////////////////////////////////////////////
//																			//
//		Title : XM-Miner Ŭ���̾�Ʈ ���ø� GUI ���							//
//	 	2001�� 3�� 16�� ���� ���� (by ������)								//
//		2001�� 3�� 16�� ���� ���� ���ø��� Ŭ������ ���ø� �����Ǿ�����		//
//		Last update : 2001�� 4�� 18�� ������								//
//			- ü���� ǥ������ �Ϸ� (3�� 21��)								//
//			- ��ü�� �߰��� ����(���ø� ���, ������ ���� etc)�� ���� ����	//
//			- ��ȭ ���ø� GUI �ϼ�											//
//			- ��ȭ ���ø����� ������ �÷� ���� �о���� �ϼ�(3�� 26��)		//
//																			//
//////////////////////////////////////////////////////////////////////////////

package xmminer.xmclient.xmgraph.xmdnode.xmdnodesampling;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import xmminer.xmclient.xmgraph.*;
import xmminer.xmclient.xmgraph.XMDialogGraphElement;
import xmminer.xmclient.xmgraph.xmdnode.xmdnodesampling.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling.*;
import xmminer.xmserver.xmgraph.xmdnode.xmdnodesampling.XMBDNodeSampling;

public class XMDialogDNodeSampling extends JDialog
{
  //*** developer declared class&variable
  XMBDNodeSampling sds;	// ���� ���
  XMDNodeSampling cds;	// Ŭ���̾�Ʈ ���

// Modified by Sun jee Hun ////////////////////////////////////////////////////////
  public String[] column_list;
  public String[] column_type;
  public String project_name;
  private boolean Modified = false;	// ��ҹ�ư�� ���������� Ȯ���� ���������� �ܺο��� �˱� ����
///////////////////////////////////////////////////////////////////////////////////

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
  int h_size;
  Hashtable column_h;
  Hashtable type_h;
  boolean[] cal_opt = new boolean[5];
  int[] cal_option_list = new int[5];
  String[] cal_column_list;
  String[] cal_column_type;
///////////////////////////////////////////////////////////////////////////////////

  String previous_arc;
  String next_arc;
  String arc = "arc";
  int selected_index;
  int meta_status;
  JFrame m_frame;
  JOptionPane optionPane;

  //***
// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
  JPanel jpnlSystematicSampling = new JPanel();	//ü���� ǥ�� ���� ��
//  JPanel jpnlStratifiedSampling = new JPanel();	//��ȭ ���� ��
///////////////////////////////////////////////////////////////////////////////////

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JButton button_ok = new JButton();
  JButton button_cancel = new JButton();
  Border border1;
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder1;
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel5 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel10 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel13 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JPanel jPanel21 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel22 = new JPanel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField3 = new JTextField();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel23 = new JPanel();
  JPanel jPanel25 = new JPanel();
  JPanel jPanel26 = new JPanel();
  JPanel jPanel28 = new JPanel();
  JPanel jPanel30 = new JPanel();
  JLabel jLabel4 = new JLabel();
  JTextField jTextField4 = new JTextField();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField5 = new JTextField();
  FlowLayout flowLayout2 = new FlowLayout();
  FlowLayout flowLayout3 = new FlowLayout();
  FlowLayout flowLayout6 = new FlowLayout();
  FlowLayout flowLayout7 = new FlowLayout();
  FlowLayout flowLayout8 = new FlowLayout();
  FlowLayout flowLayout9 = new FlowLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  FlowLayout flowLayout13 = new FlowLayout();
  Border border2;
  BorderLayout borderLayout7 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  JCheckBox jCheckBox2 = new JCheckBox();
  GridLayout gridLayout17 = new GridLayout();
  JPanel jPanel35 = new JPanel();
  JPanel jPanel34 = new JPanel();
  JTextField jTextField6 = new JTextField();
  FlowLayout flowLayout11 = new FlowLayout();
  JPanel jPanel32 = new JPanel();
  JLabel jLabel6 = new JLabel();
  BorderLayout borderLayout8 = new BorderLayout();
  BorderLayout borderLayout9 = new BorderLayout();
  BorderLayout borderLayout10 = new BorderLayout();
  JCheckBox jCheckBox1 = new JCheckBox();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel jpnlSysSampling_1 = new JPanel();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout11 = new BorderLayout();
  JLabel jLabel7 = new JLabel();
  JTextField jtxtfSysSampling = new JTextField();
  FlowLayout flowLayout12 = new FlowLayout();
  JPanel jPanel6 = new JPanel();
  FlowLayout flowLayout10 = new FlowLayout();
  JLabel jLabel8 = new JLabel();
  JTextField jtxtfSysSampling_K = new JTextField();
  JPanel jPanel8 = new JPanel();
  FlowLayout flowLayout14 = new FlowLayout();
  BorderLayout borderLayout12 = new BorderLayout();

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
//  JPanel jpnlStratifiedSampling_1 = new JPanel();
///////////////////////////////////////////////////////////////////////////////////

//  BorderLayout borderLayout13 = new BorderLayout();
//  JPanel jpnlStratifiedSampling_sub1 = new JPanel();
//  JPanel jpnlStratifiedSampling_sub2 = new JPanel();
//  JPanel jpnlStratifiedSampling_sub3 = new JPanel();
//  BorderLayout borderLayout14 = new BorderLayout();
//  JPanel jPanel12 = new JPanel();
//  FlowLayout flowLayout15 = new FlowLayout();
//  JLabel jLabel9 = new JLabel();
//  JTextField jtxtfStratifiedClusterNumber = new JTextField();
//  JLabel jLabel10 = new JLabel();
//  JTextField jtxtfStratifiedSamplingNumber = new JTextField();
//  JLabel jLabel11 = new JLabel();
//  JTextField jtxtfStratifiedSamplingRate = new JTextField();
//  GridLayout gridLayout1 = new GridLayout();
//  JCheckBox jCheckBox3 = new JCheckBox();
//  JPanel jPanel9 = new JPanel();
//  JPanel jPanel14 = new JPanel();
//  JPanel jPanel15 = new JPanel();
//  FlowLayout flowLayout16 = new FlowLayout();
//  JPanel jPanel16 = new JPanel();
//  FlowLayout flowLayout17 = new FlowLayout();
//  FlowLayout flowLayout4 = new FlowLayout();
//  JLabel jLabel12 = new JLabel();
//  JComboBox jcboStandardColumn = new JComboBox();
//  BorderLayout borderLayout15 = new BorderLayout();
//  JPanel jPanel17 = new JPanel();
//  JButton jbtnFiltering = new JButton();
//  JPanel jPanel18 = new JPanel();
//  FlowLayout flowLayout5 = new FlowLayout();
//  FlowLayout flowLayout18 = new FlowLayout();


// ������
// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
  public XMDialogDNodeSampling(JFrame frame,
							   String title, boolean modal,
							   XMBDNodeSampling server_module,
							   XMDNodeSampling client_module)
  {
    super(frame, title, modal);
    try {
      jbInit();
      m_frame = frame;
      cds = client_module;
	  project_name = cds.project_name;
      setPreviousArc();
      setNextArc();
      sds = server_module;
      meta_status = sds.setFileStatus(cds.project_name,previous_arc,next_arc,"edit");
      setInitStatus();
	  getSamplingObjectStatus();
	  save_action();
    }
    catch (Exception e)
	{}
    pack();
  }
///////////////////////////////////////////////////////////////////////////////////

  private void jbInit() throws Exception {
    border1 = BorderFactory.createRaisedBevelBorder();
    titledBorder1 = new TitledBorder("");
    border2 = BorderFactory.createEmptyBorder(5,5,0,5);
    jPanel1.setLayout(flowLayout13);
    button_ok.setFont(new java.awt.Font("Dialog", 0, 12));

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
//    jbtnFiltering.setFont(new java.awt.Font("Dialog", 0, 12));
///////////////////////////////////////////////////////////////////////////////////

    button_ok.setBorder(border1);
    button_ok.setMinimumSize(new Dimension(90, 30));
    button_ok.setPreferredSize(new Dimension(90, 30));
    button_ok.setText("Ȯ��");
    button_ok.addActionListener(new XMDialogDNodeSampling_button_ok_actionAdapter(this));
    button_cancel.setFont(new java.awt.Font("Dialog", 0, 12));
    button_cancel.setBorder(border1);
    button_cancel.setMaximumSize(new Dimension(90, 30));
    button_cancel.setMinimumSize(new Dimension(90, 30));
    button_cancel.setPreferredSize(new Dimension(90, 30));
    button_cancel.setText("���");
    button_cancel.addActionListener(new XMDialogDNodeSampling_button_cancel_actionAdapter(this));
    this.addWindowListener(new XMDialogDNodeSampling_this_windowAdapter(this));
    panel1.setLayout(borderLayout6);
    panel1.setMinimumSize(new Dimension(500, 230));
    panel1.setPreferredSize(new Dimension(430, 350));
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel1.setPreferredSize(new Dimension(500, 45));
    panel2.setLayout(borderLayout1);
    jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
    jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {

      public void stateChanged(ChangeEvent e) {
        jTabbedPane1_stateChanged(e);
      }
    });
    jPanel2.setLayout(borderLayout2);
    borderLayout2.setVgap(5);
    jPanel7.setLayout(borderLayout7);
    jPanel10.setLayout(flowLayout2);
    jPanel13.setLayout(flowLayout3);
    jPanel11.setLayout(borderLayout10);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("���� �� ����");
    jTextField1.setHorizontalAlignment(SwingConstants.RIGHT);
    jTextField1.addKeyListener(new XMDialogDNodeSampling_jTextField1_keyAdapter(this));

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
//	jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
//	jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
//	jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
///////////////////////////////////////////////////////////////////////////////////

	jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel2.setText("���� ����(%)");
    jTextField2.setHorizontalAlignment(SwingConstants.RIGHT);

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
/*
	jTextField2.addActionListener(new java.awt.event.ActionListener() {


	  public void actionPerformed(ActionEvent e) {
        jTextField2_actionPerformed(e);
      }
    });
*/
///////////////////////////////////////////////////////////////////////////////////

    jPanel21.setBorder(BorderFactory.createEtchedBorder());
    jPanel21.setPreferredSize(new Dimension(10, 40));
    jPanel21.setLayout(borderLayout3);
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel3.setText("������ ���� ���� ��ü ���");
    jTextField3.setPreferredSize(new Dimension(150, 22));
	jTextField3.setEditable(false);
    jTextField3.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel22.setLayout(flowLayout1);
    jPanel22.setPreferredSize(new Dimension(400, 22));
    jPanel5.setLayout(borderLayout4);
    jPanel3.setLayout(borderLayout8);
    jPanel23.setLayout(flowLayout6);
    jPanel26.setLayout(flowLayout8);
    jPanel25.setLayout(borderLayout9);
    jPanel28.setLayout(flowLayout7);
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel4.setPreferredSize(new Dimension(80, 18));
    jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel4.setText("���� �� ����");
    jPanel30.setLayout(flowLayout9);
    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setPreferredSize(new Dimension(80, 18));
    jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel5.setText("���� ����(%) ");

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
	/*
	jTextField4.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField4_actionPerformed(e);
      }
    });

	jTextField5.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jTextField5_actionPerformed(e);
      }
    });
	*/

    jTextField1.setPreferredSize(new Dimension(100, 22));
    jTextField2.setPreferredSize(new Dimension(100, 22));
    jTextField2.setEditable(false);
    jtxtfSysSampling_K.setPreferredSize(new Dimension (100, 22));
    jtxtfSysSampling.setPreferredSize(new Dimension (100, 22));
    jtxtfSysSampling.setHorizontalAlignment(SwingConstants.RIGHT);//�ؽ�Ʈ�ڽ� ������ ����
    jtxtfSysSampling.addKeyListener(new XMDialogDNodeSampling_jtxtfSysSampling_keyAdapter(this));
///////////////////////////////////////////////////////////////////////////////////

    jTextField4.setPreferredSize(new Dimension(100, 22));
    jTextField4.setHorizontalAlignment(SwingConstants.RIGHT);
    jTextField4.addKeyListener(new XMDialogDNodeSampling_jTextField4_keyAdapter(this));
    jTextField5.setPreferredSize(new Dimension(100, 22));
    jTextField5.setEditable(false);
    jTextField5.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel10.setBorder(BorderFactory.createEtchedBorder());
    jPanel10.setPreferredSize(new Dimension(400, 80));
    jPanel13.setBorder(BorderFactory.createEtchedBorder());
    jPanel13.setPreferredSize(new Dimension(400, 80));
    jPanel11.setPreferredSize(new Dimension(400, 60));
    jPanel23.setBorder(BorderFactory.createEtchedBorder());
    jPanel23.setPreferredSize(new Dimension(400, 65));
    jPanel26.setBorder(BorderFactory.createEtchedBorder());
    jPanel26.setPreferredSize(new Dimension(400, 65));
    jPanel25.setPreferredSize(new Dimension(400, 95));
    jPanel28.setPreferredSize(new Dimension(400, 30));
    jPanel30.setPreferredSize(new Dimension(400, 30));
    flowLayout13.setAlignment(FlowLayout.RIGHT);
    borderLayout6.setHgap(5);
    borderLayout6.setVgap(5);
    flowLayout2.setVgap(25);
    flowLayout3.setVgap(30);
    jCheckBox2.setFont(new java.awt.Font("Dialog", 0, 12));
    jCheckBox2.setText("  ���� ����");
    gridLayout17.setColumns(2);
    jPanel34.setLayout(gridLayout17);
    jPanel34.setPreferredSize(new Dimension(400, 30));
    jTextField6.setPreferredSize(new Dimension(100, 22));
    jTextField6.setHorizontalAlignment(SwingConstants.RIGHT);
    jPanel32.setLayout(flowLayout11);
    jPanel32.setBorder(BorderFactory.createEtchedBorder());
    jPanel32.setPreferredSize(new Dimension(400, 65));
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setPreferredSize(new Dimension(80, 18));
    jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel6.setText("���� ���� ");
    jPanel35.setPreferredSize(new Dimension(92, 20));
	flowLayout6.setVgap(15);
	flowLayout8.setVgap(15);
	flowLayout11.setVgap(20);
	this.setResizable(false);
	jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 12));
	jCheckBox1.setText("  ���� ����");
	jCheckBox1.setHorizontalAlignment(SwingConstants.CENTER);
	jpnlSystematicSampling.setLayout(borderLayout5);
    jpnlSysSampling_1.setLayout(borderLayout11);
    jPanel4.setBorder(BorderFactory.createEtchedBorder());
    jPanel4.setPreferredSize(new Dimension(400, 80));
    jPanel4.setLayout(flowLayout12);
    jLabel7.setText("���� �� ����");
    jPanel6.setBorder(BorderFactory.createEtchedBorder());
    jPanel6.setPreferredSize(new Dimension(400, 80));
    jPanel6.setLayout(flowLayout10);
    jLabel8.setText("K (��ü ������ �� / ���� �� ����)");
    jtxtfSysSampling_K.setEditable(false);
    jtxtfSysSampling_K.setHorizontalAlignment(SwingConstants.RIGHT);
    flowLayout12.setVgap(25);
    flowLayout10.setVgap(30);
    jPanel8.setLayout(flowLayout14);
    flowLayout14.setHgap(30);
    flowLayout14.setVgap(30);

//    jpnlStratifiedSampling.setLayout(borderLayout12);

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
//    jpnlStratifiedSampling_1.setLayout(borderLayout13);
//    jpnlStratifiedSampling_sub1.setBorder(BorderFactory.createEtchedBorder());
//    jpnlStratifiedSampling_sub1.setPreferredSize(new Dimension(400, 49));
//    jpnlStratifiedSampling_sub1.setLayout(flowLayout4);
//    jpnlStratifiedSampling_1.setMinimumSize(new Dimension(106, 224));
//    jpnlStratifiedSampling_1.setPreferredSize(new Dimension(400, 215));
//    jpnlStratifiedSampling_sub2.setMinimumSize(new Dimension(106, 65));
//    jpnlStratifiedSampling_sub2.setPreferredSize(new Dimension(400, 65));
//    jpnlStratifiedSampling_sub2.setLayout(borderLayout15);
//    jpnlStratifiedSampling_sub3.setMinimumSize(new Dimension(92, 92));
//    jpnlStratifiedSampling_sub3.setPreferredSize(new Dimension(400, 78));
//    jpnlStratifiedSampling_sub3.setLayout(borderLayout14);
//    jPanel12.setBorder(BorderFactory.createEtchedBorder());
//    jPanel12.setMinimumSize(new Dimension(77, 45));
//    jPanel12.setPreferredSize(new Dimension(400, 49));
//    jPanel12.setLayout(flowLayout15);
//    jLabel9.setText("��ȭ�� ������");
//    jtxtfStratifiedClusterNumber.setMinimumSize(new Dimension(100, 22));
//    jtxtfStratifiedClusterNumber.setPreferredSize(new Dimension(100, 22));
//    jtxtfStratifiedClusterNumber.setHorizontalAlignment(SwingConstants.RIGHT);
//    jLabel10.setText("���� �� ����  ");
//    jtxtfStratifiedSamplingNumber.setMinimumSize(new Dimension(100, 22));
//    jtxtfStratifiedSamplingNumber.setPreferredSize(new Dimension(100, 22));
//    jtxtfStratifiedSamplingNumber.setHorizontalAlignment(SwingConstants.RIGHT);
//    jtxtfStratifiedSamplingNumber.addKeyListener(new XMDialogDNodeSampling_jtxtfStratifiedSamplingNumber_keyAdapter(this));
//    jLabel11.setText("���� ����(%)  ");
//    jtxtfStratifiedSamplingRate.setPreferredSize(new Dimension(100, 22));
//    jtxtfStratifiedSamplingRate.setEditable(false);
//    jtxtfStratifiedSamplingRate.setHorizontalAlignment(SwingConstants.RIGHT);
//    gridLayout1.setColumns(2);
//    jCheckBox3.setFont(new java.awt.Font("Dialog", 0, 12));
//    jCheckBox3.setText("  ���� ����");
//    jCheckBox3.setToolTipText("");
//    jPanel9.setMinimumSize(new Dimension(92, 20));
//    jPanel9.setPreferredSize(new Dimension(92, 20));
//    jPanel14.setLayout(gridLayout1);
//    jPanel14.setMinimumSize(new Dimension(92, 36));
//    jPanel14.setPreferredSize(new Dimension(400, 30));
//    jpnlStratifiedSampling.setMinimumSize(new Dimension(106, 224));
//    jPanel5.setPreferredSize(new Dimension(400, 220));
//    jpnlSysSampling_1.setMinimumSize(new Dimension(106, 224));
//    jPanel15.setLayout(flowLayout16);
//    jPanel16.setLayout(flowLayout17);
//    jPanel15.setBorder(BorderFactory.createEtchedBorder());
//	  jPanel15.setMinimumSize(new Dimension(85, 32));
//    jPanel15.setPreferredSize(new Dimension(400, 49));
//    jPanel16.setMinimumSize(new Dimension(92, 32));
//    jPanel16.setPreferredSize(new Dimension(400, 30));
//	  flowLayout16.setVgap(12);
//	  flowLayout1.setVgap(7);
//    jLabel12.setText("���� ���ɿ�");
//    jLabel12.setFont(new java.awt.Font("Dialog", 0, 12));
//    flowLayout4.setVgap(10);

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
//    jcboStandardColumn.setFont(new java.awt.Font("Dialog", 0, 12));
//    jcboStandardColumn.addItemListener(new XMDialogDNodeSampling_jcboStandardColumn_itemAdapter(this));
///////////////////////////////////////////////////////////////////////////////////

//    jPanel17.setBorder(BorderFactory.createEtchedBorder());
//    jPanel17.setMinimumSize(new Dimension(109, 49));
//    jPanel17.setPreferredSize(new Dimension(195, 49));
//    jPanel17.setLayout(flowLayout18);
//    jbtnFiltering.setMaximumSize(new Dimension(71, 25));
//    jbtnFiltering.setMinimumSize(new Dimension(71, 25));
//    jbtnFiltering.setPreferredSize(new Dimension(71, 25));
//    jbtnFiltering.setText("���͸�");
//    jbtnFiltering.addActionListener(new XMDialogDNodeSampling_jbtnFiltering_actionAdapter(this));

//    jPanel18.setMinimumSize(new Dimension(95, 28));
//    jPanel18.setPreferredSize(new Dimension(191, 30));
//    jPanel18.setLayout(flowLayout5);
//    flowLayout18.setVgap(10);
//    flowLayout5.setVgap(3);
	panel1.add(panel2, BorderLayout.CENTER);
    panel2.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel2, "   ������ ����   ");
    jPanel2.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(jPanel10, BorderLayout.NORTH);
    jPanel10.add(jLabel1, null);
    jPanel10.add(jTextField1, null);
    jPanel7.add(jPanel13, BorderLayout.CENTER);
    jPanel13.add(jLabel2, null);
    jPanel13.add(jTextField2, null);
    jPanel7.add(jPanel11, BorderLayout.SOUTH);
	jPanel11.add(jCheckBox1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel5, "      ���� ����      ");
    jPanel5.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jPanel23, BorderLayout.NORTH);
    jPanel23.add(jPanel28, null);
    jPanel28.add(jLabel4, null);
    jPanel28.add(jTextField4, null);
    jPanel3.add(jPanel26, BorderLayout.CENTER);
    jPanel26.add(jPanel30, null);
    jPanel30.add(jLabel6, null);
    jPanel30.add(jTextField6, null);
    jPanel3.add(jPanel25, BorderLayout.SOUTH);
    jPanel25.add(jPanel32, BorderLayout.CENTER);
    jPanel32.add(jLabel5, null);
    jPanel32.add(jTextField5, null);
    jPanel25.add(jPanel34, BorderLayout.SOUTH);
    jPanel34.add(jPanel35, null);
    jPanel35.add(jCheckBox2, null);
    panel2.add(jPanel21, BorderLayout.NORTH);
    jPanel21.add(jPanel22, BorderLayout.CENTER);
    jPanel22.add(jLabel3, null);
    jPanel22.add(jTextField3, null);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(button_ok, null);
    jPanel1.add(button_cancel, null);

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
    jTabbedPane1.add(jpnlSystematicSampling, " ü���� ǥ������ ");
    jpnlSystematicSampling.add(jpnlSysSampling_1, BorderLayout.CENTER);
    jpnlSysSampling_1.add(jPanel4, BorderLayout.NORTH);
    jPanel4.add(jLabel7, null);
    jPanel4.add(jtxtfSysSampling, null);
    jpnlSysSampling_1.add(jPanel6, BorderLayout.CENTER);
    jPanel6.add(jLabel8, null);
    jPanel6.add(jtxtfSysSampling_K, null);
    jpnlSysSampling_1.add(jPanel8, BorderLayout.SOUTH);

//    jTabbedPane1.add(jpnlStratifiedSampling, "      ��ȭ ����      ");
//    jpnlStratifiedSampling.add(jpnlStratifiedSampling_1, BorderLayout.CENTER);
//    jpnlStratifiedSampling_1.add(jpnlStratifiedSampling_sub1, BorderLayout.NORTH);
//    jpnlStratifiedSampling_sub1.add(jLabel12, null);
//    jpnlStratifiedSampling_sub1.add(jcboStandardColumn, BorderLayout.CENTER);
//    jpnlStratifiedSampling_sub1.add(jbtnFiltering, null);
//    jpnlStratifiedSampling_1.add(jpnlStratifiedSampling_sub2, BorderLayout.CENTER);
//    jpnlStratifiedSampling_sub2.add(jPanel15, BorderLayout.NORTH);
//    jPanel15.add(jLabel9, null);
//    jPanel15.add(jtxtfStratifiedClusterNumber, null);
//    jpnlStratifiedSampling_sub2.add(jPanel17, BorderLayout.CENTER);
//    jPanel17.add(jPanel18, null);
//    jPanel18.add(jLabel10, null);
//    jPanel18.add(jtxtfStratifiedSamplingNumber, null);
//    jpnlStratifiedSampling_1.add(jpnlStratifiedSampling_sub3, BorderLayout.SOUTH);
//    jpnlStratifiedSampling_sub3.add(jPanel12, BorderLayout.CENTER);
//    jPanel12.add(jPanel16, null);
//    jPanel16.add(jLabel11, null);
//    jPanel16.add(jtxtfStratifiedSamplingRate, null);
//    jpnlStratifiedSampling_sub3.add(jPanel14, BorderLayout.SOUTH);
//    jPanel14.add(jPanel9, null);
//    jPanel9.add(jCheckBox3, null);
///////////////////////////////////////////////////////////////////////////////////
    getContentPane().add(panel1);
}

  void setInitStatus()
  {
    if (meta_status!=0)
    {
       showWarningDialog();
       dispose();
    }
    else
    {
       jTextField3.setText(String.valueOf(sds.getTotalRows()));

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
	   column_list = sds.getColumnList();
//	   column_type = sds.getColumnType();
	   setComboBox();
///////////////////////////////////////////////////////////////////////////////////
	}
  }

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
  public boolean getisModified()	// Ȯ�ι�ư�� ������ true, ����̸� false�̴�.
  {
	  return Modified;
  }

  private void setComboBox()
  {
     for (int i=0; i < column_list.length; i++)
     {
//        jcboStandardColumn.addItem(column_list[i]);
     }
  }

  // �Է� ���� �߻��� ��Ÿ���� ��� �ڽ�
  private void showWarningInputError(String msg)
  {
	  optionPane.showMessageDialog(m_frame, msg, "�Է¿���", optionPane.WARNING_MESSAGE);
  }

  private void showWarningDialog()
  {
     optionPane.showMessageDialog(m_frame,"��Ÿ ������ �������� �ʽ��ϴ�.","��Ÿ ���� ó�� Ȯ��", optionPane.WARNING_MESSAGE);
  }

  // OK ��ư
  void button_ok_actionPerformed(ActionEvent e)
  {
      //int sampling_percent = Integer.parseInt(jTextField4.getText()); //sampling percentage ó�� ���� ����
    try
    {
	  int cbox_idx = 0;
      int total_data_number = 0;  // ��ü ������ ��
      int K = 0;  // (��ü ���̼� �� / ���� ��)
      int sampling_number = 0;
	  int cluster_number = 1;
	  int standard_column = 0;

	  total_data_number = Integer.parseInt(jTextField3.getText());

      if (selected_index==0)  // ���� ���ø� (������ ����)
      {
		if (jCheckBox1.isSelected())
		{
			cbox_idx = 1; // ��������
		}
		else
		{
			cbox_idx = 0; // �񺹿�����
		}
        sampling_number = Integer.parseInt(jTextField1.getText()); //sampling ���� ó�� ���� ����
      }
      else if (selected_index == 1)  // Ŭ������ ���ø� (���� ����)
      {
 	      if (jCheckBox2.isSelected())
		  {
			cbox_idx = 1; //���� ����
		  }
		  else
		  {
			cbox_idx = 0; //�񺹿� ����
		  }

		sampling_number = Integer.parseInt(jTextField4.getText());
		cluster_number = Integer.parseInt(jTextField6.getText());
        sds.setClusteringNumber(cluster_number);
      }

      else if (selected_index == 2) // ü���� ǥ������
      {
        if (Integer.parseInt(jtxtfSysSampling.getText()) > 0)
		{
			sampling_number = Integer.parseInt(jtxtfSysSampling.getText());
			K = total_data_number / sampling_number;
			cbox_idx = 1;	// ü���� ǥ�� ������ ������ �񺹿� �����̴�.
		}					// �׷��� �������� ����Ҷ� m_pbMax ���� ���ø����� �״�� �ޱ�����
							// cbox_idx ������ 1�� �����Ѵ�.
      }

//      else if (selected_index == 3) // ��ȭ ����
//      {
// 	      if (jCheckBox3.isSelected())
//		  {
//			cbox_idx = 1; //���� ����
//		  }
//		  else
//		  {
//			cbox_idx = 0; //�񺹿� ����
//		  }
//
//		sampling_number = Integer.parseInt(jtxtfStratifiedSamplingNumber.getText());
//		cluster_number = Integer.parseInt(jtxtfStratifiedClusterNumber.getText());
//		standard_column = jcboStandardColumn.getSelectedIndex();
//
//		sds.setClusteringNumber(cluster_number);	// ��ȭ�� ������ �� ����(�������� ������.)
//      }

	  if (sampling_number > 0 && sampling_number <= total_data_number)
	  {
		  sds.setSelectedIndex(selected_index); // ���° ���� �����ߴ°� ?
		  sds.setSamplingNumber(sampling_number); // ��� �����͸� �����ϰ��� �ϴ°� ? 1���ٴ� Ŀ���Ѵ�.
		  sds.setCheckBoxValue(cbox_idx);
		  sds.close();
		  dispose();

		  // ���ø� ��ü�� ���� ���¸� �����Ѵ�.
		  setSamplingObjectStatus(selected_index, sampling_number,
								  cbox_idx, cluster_number,
								  standard_column);
		  Modified = true;
	  }
	  else if (sampling_number <= 0)
	  {
		  showWarningInputError("���� ���� ���� 0 ���� Ŀ�� �մϴ�!");
	  }
	  else if (sampling_number > total_data_number)
	  {
		  showWarningInputError("���� ���� ���� ��ü ������ ������ ����� �մϴ�!");
	  }

    }
    catch(Exception ae)
    {}
  }
///////////////////////////////////////////////////////////////////////////////////

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
  // ���ø� ��ü ���� �Լ�
  void setSamplingObjectStatus(int selected_index, int sampling_number,
							   int cbox_idx, int cluster_number,
							   int standard_column)
  {
	int total_rows = sds.getTotalRows();
	int sampling_rate = sds.calNumToPer(sampling_number);
	int KValue = sds.calKValue(sampling_number);
	int column_count = column_list.length;

	cds.setTotalDataNumber(total_rows);
	cds.setSamplingType(selected_index);
	cds.setSamplingNumber(sampling_number);
	cds.setCboxValue(cbox_idx);
	cds.setClusterNumber(cluster_number);
	cds.setKValue(KValue);
	cds.setSamplingRate(sampling_rate);
	cds.setStandardColumn(standard_column);
	cds.setColumnCount(column_count);

  }

  // ���õ� ���ø���ü�� ���� ��������
  void getSamplingObjectStatus()
  {
	int total_data_number_in_object = cds.getTotalDataNumber();
	int total_data_number_in_predata = sds.getTotalRows();
	int Sampling_Type = cds.getSamplingType();
	int Sampling_Number = cds.getSamplingNumber();
	int cbox_idx = cds.getCboxValue();
	int Cluster_Number = cds.getClusterNumber();
	int Sampling_Rate = sds.calNumToPer(Sampling_Number);
	int K = sds.calKValue(Sampling_Number);
	int standard_column = cds.getStandardColumn();

	boolean status;

    if (cbox_idx == 1)
    {
      status = true;
    }
    else
    {
      status = false;
    }

	jTabbedPane1.setSelectedIndex(Sampling_Type);

    switch (Sampling_Type)
    {
      case 0:	// ������ ����
        if (Sampling_Number > 0)
        {
          jTextField1.setText(Sampling_Number + "");
	      jTextField2.setText(Sampling_Rate + "");
        }
        jCheckBox1.setSelected(status);	// status ������ ���¿� ���� üũ�ڽ��� ���� ������ �˼� �ִ�.
		break;

      case 1:	// ���� ����
        if (Sampling_Number > 0)
        {
          jTextField4.setText(Sampling_Number + "");
	      jTextField5.setText(Sampling_Rate + "");
          jTextField6.setText(Cluster_Number + "");
        }
        jCheckBox2.setSelected(status);
		break;

      case 2:	// ü���� ǥ�� ����
        if (Sampling_Number > 0)
        {
          jtxtfSysSampling.setText(Sampling_Number + "");
	      jtxtfSysSampling_K.setText(K + "");
        }
		break;

//	  case 3:	// ��ȭ ����
//        if (Sampling_Number > 0)
//        {
//          jtxtfStratifiedClusterNumber.setText(Cluster_Number + "");
//		  jtxtfStratifiedSamplingNumber.setText(Sampling_Number + "");
//	      jtxtfStratifiedSamplingRate.setText(Sampling_Rate + "");
//		  jcboStandardColumn.setSelectedIndex(standard_column);
//        }
//		jCheckBox3.setSelected(status);
//		break;

      default:
    }
  }
///////////////////////////////////////////////////////////////////////////////////


// Cancel��ư
// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
// ��ҹ�ư�� ������ ���� ��ü�� ������ �ִ� ����� ������ �����Ѵ�.
  void button_cancel_actionPerformed(ActionEvent e)
  {
		save_action();
		dispose();
  }

  void this_windowClosing(WindowEvent e)
  {
	  save_action();
      dispose();
  }

  // ĵ����ư�� �����ų� ������ ����(X) ��ư�� ������ ����Ʈ�ڽ��� ������ ���
  void save_action()
  {
	int cluster_number = cds.getClusterNumber();
	int sampling_number = cds.getSamplingNumber();
	int cbox_idx = cds.getCboxValue();
	int standard_column = cds.getStandardColumn();

//	jcboStandardColumn.setSelectedIndex(standard_column);
	sds.setClusteringNumber(cluster_number);
	sds.setSelectedIndex(selected_index); // ���° ���� �����ߴ°� ?
    sds.setSamplingNumber(sampling_number); // ��� �����͸� �����ϰ��� �ϴ°� ?
    sds.setCheckBoxValue(cbox_idx);
    sds.close();

	Modified = false;
  }
///////////////////////////////////////////////////////////////////////////////////

  void jTabbedPane1_stateChanged(ChangeEvent e)
  {
      selected_index = jTabbedPane1.getSelectedIndex();
  }

  private void setPreviousArc()
  {
      previous_arc = arc+cds.previous_index;
  }

  private void setNextArc()
  {
      next_arc = arc+cds.next_index;
  }

/*
  void jTextField4_actionPerformed(ActionEvent e)
  {
       int sampling_number = 0;
       int tmp_percent = 0;
       sampling_number = Integer.parseInt(jTextField4.getText());
       if (sampling_number > 0)
       {
           tmp_percent = sds.calNumToPer(sampling_number); //sampling ������ ������� ��ȯ
           jTextField5.setText(String.valueOf(tmp_percent));
       }
  }
*/

/*
  void jTextField5_actionPerformed(ActionEvent e)
  {
      int sampling_percent = 0; //����� ó�� ���� ����
      int tmp_number = 0; //����� sampling ���� ��ȯ�� ó�� ���� ����
      sampling_percent = Integer.parseInt(jTextField5.getText());

      if (sampling_percent > 0)
      {
         tmp_number = sds.calPerToNum(sampling_percent); //������� sampling ������ ��ȯ
         jTextField4.setText(String.valueOf(tmp_number));
      }
  }
*/

// Modified by Sun Jee Hun ////////////////////////////////////////////////////////
  //ü���� ǥ�� �������� ���� �� ������ ���ڸ� �Է��� ����� �̺�Ʈ
  //������ ���� �Ʒ��� K�� �ش��ϴ� �ؽ�Ʈ�ڽ��� K���� ���Ǿ� ��.
  void jtxtfSysSampling_keyReleased(KeyEvent e)
  {
    int K = 0;
    int sampling_number = 0;
    int total_data_number = 0;

	try
	{
		// ��ü ������ ����
		total_data_number = Integer.parseInt(jTextField3.getText());

		if (jtxtfSysSampling.getText().equals("") || Integer.parseInt(jtxtfSysSampling.getText()) <= 0)
		{
		  jtxtfSysSampling_K.setText("");
		}
		else
		{
		  sampling_number = Integer.parseInt(jtxtfSysSampling.getText());
		  K = total_data_number / sampling_number;
		  jtxtfSysSampling_K.setText(String.valueOf(K));
		}
	}
	catch (Exception e3)
	{}
  }

  // ���� ���ø����� ǥ�� ũ�� ���Կ� ���� ���ϴ� ǥ�� �ۼ�Ʈ�� ���� �̺�Ʈ
  void jTextField1_keyReleased(KeyEvent e)
  {
	int sampling_number = 0;
	int tmp_percent = 0;

	try
	{
		if (jTextField1.getText().equals(""))
		{
			jTextField2.setText("");
		}
		else if(Integer.parseInt(jTextField1.getText()) >= 0)
		{
		  sampling_number = Integer.parseInt(jTextField1.getText());
		  tmp_percent = sds.calNumToPer(sampling_number); //sampling ������ ������� ��ȯ
		  jTextField2.setText(String.valueOf(tmp_percent));
		}
	}
	catch (Exception e1)
	{}
  }

  // ���� ���ø����� ǥ�� ũ�� ���Կ� ���� ���ϴ� ǥ�� �ۼ�Ʈ�� ���� �̺�Ʈ
  void jTextField4_keyReleased(KeyEvent e)
  {
    int sampling_number = 0;
    int tmp_percent = 0;

	try
	{
		if (jTextField4.getText().equals(""))
		{
			jTextField5.setText("");
		}
		else if(Integer.parseInt(jTextField4.getText()) >= 0)
		{
		  sampling_number = Integer.parseInt(jTextField4.getText());
		  tmp_percent = sds.calNumToPer(sampling_number); //sampling ������ ������� ��ȯ
		  jTextField5.setText(String.valueOf(tmp_percent));
		}
	}
	catch (Exception e2)
	{}
  }

  // ��ȭ ���ø� �ǿ��� �������� ���� �������� ���� �����ϴ� �޺��ڽ� �� �̺�Ʈ
  void jcboStandardColumn_itemStateChanged(ItemEvent e)
  {
	  ComboBoxAction();
  }

  private void ComboBoxAction()
  {
//	  System.out.println(jcboStandardColumn.getSelectedItem());
  }

  // ��ȭ ���ø����� �������� ���� �����ϴ°Ͱ� ���ÿ� ���� ������ ����Ѵ�.
  void jtxtfStratifiedSamplingNumber_keyReleased(KeyEvent e)
  {
//    int sampling_number = 0;
//    int tmp_percent = 0;
//
//	try
//	{
//		if (jtxtfStratifiedSamplingNumber.getText().equals(""))
//		{
//			jtxtfStratifiedSamplingRate.setText("");
//		}
//		else if(Integer.parseInt(jtxtfStratifiedSamplingNumber.getText()) >= 0)
//		{
//		  sampling_number = Integer.parseInt(jtxtfStratifiedSamplingNumber.getText());
//		  tmp_percent = sds.calNumToPer(sampling_number); //sampling ������ ������� ��ȯ
//		  jtxtfStratifiedSamplingRate.setText(String.valueOf(tmp_percent));
//		}
//	}
//	catch (Exception e4)
//	{}
  }

  // ��ȭ ���⿡�� ���͸� ��ư�� �̺�Ʈ ó��
  void jbtnFiltering_actionPerformed(ActionEvent e)
  {
  }
///////////////////////////////////////////////////////////////////////////////////
}

class XMDialogDNodeSampling_button_ok_actionAdapter implements ActionListener {
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_button_ok_actionAdapter(XMDialogDNodeSampling adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_ok_actionPerformed(e);
  }
}

class XMDialogDNodeSampling_button_cancel_actionAdapter implements ActionListener {
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_button_cancel_actionAdapter(XMDialogDNodeSampling adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.button_cancel_actionPerformed(e);
  }
}

class XMDialogDNodeSampling_this_windowAdapter extends WindowAdapter {
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_this_windowAdapter(XMDialogDNodeSampling adaptee) {
    this.adaptee = adaptee;
  }

  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

//Modified By Sun Jee Hun -------------------------------------------//
class XMDialogDNodeSampling_jtxtfSysSampling_keyAdapter extends java.awt.event.KeyAdapter
{
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_jtxtfSysSampling_keyAdapter(XMDialogDNodeSampling adaptee)
  {
    this.adaptee = adaptee;
  }

  //ü���� ǥ�� �������� ���� �� ������ ���ڸ� �Է��� ����� �̺�Ʈ
  public void keyReleased(KeyEvent e)
  {
    adaptee.jtxtfSysSampling_keyReleased(e);
  }
}

class XMDialogDNodeSampling_jTextField1_keyAdapter extends java.awt.event.KeyAdapter
{
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_jTextField1_keyAdapter(XMDialogDNodeSampling adaptee)
  {
    this.adaptee = adaptee;
  }
  public void keyReleased(KeyEvent e)
  {
    adaptee.jTextField1_keyReleased(e);
  }
}

class XMDialogDNodeSampling_jTextField4_keyAdapter extends java.awt.event.KeyAdapter
{
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_jTextField4_keyAdapter(XMDialogDNodeSampling adaptee)
  {
    this.adaptee = adaptee;
  }
  public void keyReleased(KeyEvent e)
  {
    adaptee.jTextField4_keyReleased(e);
  }
}

// ��ȭ ���ø��� �����÷��� ���õɶ����� �߻��ϴ��̺�Ʈ ó��
class XMDialogDNodeSampling_jcboStandardColumn_itemAdapter implements java.awt.event.ItemListener
{
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_jcboStandardColumn_itemAdapter(XMDialogDNodeSampling adaptee)
  {
    this.adaptee = adaptee;
  }
  public void itemStateChanged(ItemEvent e)
  {
    adaptee.jcboStandardColumn_itemStateChanged(e);
  }
}

// ��ȭ ���ø����� �����హ���� ������ ������ ���� ���� ����� ���ϴ� �̺�Ʈ ó��
class XMDialogDNodeSampling_jtxtfStratifiedSamplingNumber_keyAdapter extends java.awt.event.KeyAdapter
{
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_jtxtfStratifiedSamplingNumber_keyAdapter(XMDialogDNodeSampling adaptee)
  {
    this.adaptee = adaptee;
  }
  public void keyReleased(KeyEvent e)
  {
    adaptee.jtxtfStratifiedSamplingNumber_keyReleased(e);
  }
}
///////////////////////////////////////////////////////////////////////////////////

class XMDialogDNodeSampling_jbtnFiltering_actionAdapter implements java.awt.event.ActionListener
{
  XMDialogDNodeSampling adaptee;

  XMDialogDNodeSampling_jbtnFiltering_actionAdapter(XMDialogDNodeSampling adaptee)
  {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e)
  {
    adaptee.jbtnFiltering_actionPerformed(e);
  }
}