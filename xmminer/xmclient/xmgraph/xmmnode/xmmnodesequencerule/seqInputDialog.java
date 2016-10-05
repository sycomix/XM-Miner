package xmminer.xmclient.xmgraph.xmmnode.xmmnodesequencerule;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.swing.border.*;
import javax.swing.table.TableColumn;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.lang.Integer;

public class seqInputDialog extends JDialog{
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel5 = new JPanel();
  ImageIcon imageBanner = new ImageIcon(xmminer.xmclient.xmgraph.xmmnode.xmmnodesequencerule.seqInputDialog.class.getResource("../../../images/seq_banner.jpg"));
  JLabel jLabel1 = new JLabel(imageBanner);
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel9 = new JPanel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JPanel jPanel10 = new JPanel();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  TitledBorder titledBorder1;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  TitledBorder titledBorder4;
  JLabel jLabel4 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JComboBox jComboBox1 = new JComboBox();
  JLabel jLabel5 = new JLabel();
  JTextField jTextField3 = new JTextField();
  JComboBox jComboBox2 = new JComboBox();
  JPanel jPanel12 = new JPanel();
  TitledBorder titledBorder5;
  JPanel jPanel13 = new JPanel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();
  FlowLayout flowLayout2 = new FlowLayout();
  JPanel jPanel14 = new JPanel();
  JPanel jPanel15 = new JPanel();
  JPanel jPanel16 = new JPanel();
  JButton jButton6 = new JButton();
  JButton jButton7 = new JButton();
  JLabel jLabel3 = new JLabel();
  JButton jButton8 = new JButton();
  JTextField jTextField4 = new JTextField();
  JLabel jLabel10 = new JLabel();
  JTextField jTextField5 = new JTextField();
  JLabel jLabel11 = new JLabel();
  JTextField jTextField6 = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable jTable1 = new JTable();   //table data input

  String modelName;     //�𵨸�
  String projectName;
  String metaFile;
  String projectpath;

  int num_instances;  //DB�� row��
  int num_attributes; //DB�� Attributes��
  int transNumber;    //transaction ��
  int num_rows;       //table�� row��(= DB�� Attributes��)
  int num_columns;    //column��(3��)

  float min_sup = 0f;
  float min_conf = 0f;
  int min_sup_type;   //min_conf_type

  seqTableModel seqModel = new seqTableModel();  //table model

  String transIDFld, targetFld, timeFld;

  XMMNodeSequenceRule m_xmmsequence;

  JLabel jLabel12 = new JLabel();
  JLabel jLabel13 = new JLabel();
  JPanel jPanel11 = new JPanel();
  JLabel jLabel14 = new JLabel();
  JLabel jLabel15 = new JLabel();
  FlowLayout flowLayout4 = new FlowLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  BorderLayout borderLayout7 = new BorderLayout();

  //constructor
  public seqInputDialog(Frame frame, String title, boolean modal, XMMNodeSequenceRule xmmnode) {
    super(frame, title, modal);
    num_columns = 3;
    m_xmmsequence = xmmnode;

    //XMMNodeAssociationRule Ŭ������ ������� ���� �ҷ��´�.
    projectName = m_xmmsequence.ProjectName;
	metaFile = m_xmmsequence.MetaFileName;
    modelName = m_xmmsequence.model;

    transNumber = m_xmmsequence.transNumber;    //transaction ��

    min_sup = m_xmmsequence.min_sup;
    min_conf = m_xmmsequence.min_conf;
    min_sup_type = m_xmmsequence.min_sup_type;

    transIDFld = m_xmmsequence.transIDFld;
    targetFld = m_xmmsequence.targetFld;
    timeFld = m_xmmsequence.timeFld;

    try{
      jbInit();

      tableSetting();   //table setting
 	  setParameter();

      SwingUtilities.updateComponentTreeUI(this);

      pack();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }


  public seqInputDialog(Frame frame, String title, boolean modal){
    super(frame, title, modal);

    try{
      jbInit();
      pack();
    }catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public seqInputDialog(){
    this(null, "", false);
  }

  void jbInit() throws Exception{
    titledBorder1 = new TitledBorder("�����ͺ��̽� ����");
    titledBorder2 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"����Ÿ���̽� ����");
    titledBorder3 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"�ʵ� ����");
    titledBorder4 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"����");
    titledBorder5 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)),"Ʈ����� ����");
    panel1.setLayout(borderLayout1);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 18));
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel5.setLayout(borderLayout4);
    jPanel3.setLayout(borderLayout2);
    jPanel6.setLayout(borderLayout3);
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel2.setText("�� ��");
    jTextField1.setPreferredSize(new Dimension(120, 22));
    jTextField1.setText("model01");
    jTextField1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jTextField1_actionPerformed(e);
      }
    });
    jPanel9.setLayout(borderLayout5);
    jPanel10.setBorder(titledBorder4);
    jPanel10.setPreferredSize(new Dimension(520, 160));
    jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton1.setPreferredSize(new Dimension(90, 29));
    jButton1.setText("Ȯ��");
    jButton1.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton1_actionPerformed(e);
      }
    });
    jButton2.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
    jButton2.setPreferredSize(new Dimension(90, 29));
    jButton2.setText("���");
    jButton2.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton2_actionPerformed(e);
      }
    });
    jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel4.setText("Minimum Support                           ");
    jTextField2.setPreferredSize(new Dimension(100, 22));
    jTextField2.setText("20");
    jTextField2.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jTextField2_actionPerformed(e);
      }
    });
    jComboBox1.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox1.setPreferredSize(new Dimension(70, 27));
    String items[] ={"��", "%"};
    for (int i = 0; i < 2; i++) jComboBox1.addItem(items[i]);
    jComboBox1.setSelectedItem("��");
    jComboBox1.setEditable(false);

    jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel5.setText("Minimum Confidence                     ");
    jTextField3.setPreferredSize(new Dimension(100, 22));
    jTextField3.setText("0.6");
    jTextField3.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jTextField3_actionPerformed(e);
      }
    });
    jComboBox2.setFont(new java.awt.Font("Dialog", 0, 12));
    jComboBox2.setPreferredSize(new Dimension(70, 27));
    jComboBox2.addItem("%");
    jComboBox2.setSelectedItem("%");
    jComboBox2.setEditable(false);

    jPanel12.setBorder(titledBorder2);
    jPanel12.setPreferredSize(new Dimension(520, 60));
    jPanel13.setBorder(titledBorder3);
    jPanel13.setPreferredSize(new Dimension(520, 195));
    jPanel13.setLayout(flowLayout2);
    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setPreferredSize(new Dimension(80, 18));
    jLabel6.setText("�ν��Ͻ� �� :");
    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setPreferredSize(new Dimension(70, 18));
    jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel8.setPreferredSize(new Dimension(60, 18));
    jLabel8.setText("�÷� �� :");
    jLabel9.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel9.setPreferredSize(new Dimension(70, 18));

    jPanel14.setPreferredSize(new Dimension(240, 160));
    jPanel14.setLayout(borderLayout7);
    jPanel15.setPreferredSize(new Dimension(50, 80));
    jPanel16.setPreferredSize(new Dimension(150, 160));
    jButton6.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton6.setPreferredSize(new Dimension(33, 22));
    jButton6.setMargin(new Insets(2, 10, 2, 10));
    jButton6.setText(">");
    jButton6.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton6_actionPerformed(e);
      }
    });
    jButton7.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton7.setPreferredSize(new Dimension(33, 22));
    jButton7.setMargin(new Insets(2, 10, 2, 10));
    jButton7.setText(">");
    jButton7.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton7_actionPerformed(e);
      }
    });
    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setPreferredSize(new Dimension(110, 18));
    jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel3.setText("�� ID");
    jButton8.setFont(new java.awt.Font("Dialog", 0, 12));
    jButton8.setPreferredSize(new Dimension(33, 22));
    jButton8.setMargin(new Insets(2, 10, 2, 10));
    jButton8.setText(">");
    jButton8.addActionListener(new java.awt.event.ActionListener(){
      public void actionPerformed(ActionEvent e){
        jButton8_actionPerformed(e);
      }
    });
    jTextField4.setPreferredSize(new Dimension(110, 22));
    jLabel10.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel10.setPreferredSize(new Dimension(110, 18));
    jLabel10.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel10.setText("��ǰ �ʵ�");
    jTextField5.setPreferredSize(new Dimension(110, 22));
    jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel11.setPreferredSize(new Dimension(110, 18));
    jLabel11.setToolTipText("");
    jLabel11.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel11.setText("�ð� �ʵ�");
    jTextField6.setPreferredSize(new Dimension(110, 22));
    jScrollPane1.setPreferredSize(new Dimension(240, 150));

    jLabel12.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel12.setPreferredSize(new Dimension(80, 18));
    jLabel12.setText("Ʈ����� �� :");
    jLabel13.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel13.setPreferredSize(new Dimension(70, 18));
    jPanel11.setBorder(titledBorder5);
    jPanel11.setPreferredSize(new Dimension(520, 60));
    jLabel14.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel14.setText("Ʈ����� �� : ");
    jLabel15.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel15.setPreferredSize(new Dimension(70, 18));
    jTabbedPane1.setFont(new java.awt.Font("Dialog", 0, 12));
    jPanel1.setLayout(flowLayout4);
    flowLayout4.setAlignment(FlowLayout.RIGHT);
    flowLayout4.setHgap(10);
    flowLayout4.setVgap(10);
    jPanel1.setBorder(BorderFactory.createEtchedBorder());
    jPanel5.setPreferredSize(new Dimension(500, 50));
    jPanel8.setBorder(BorderFactory.createEtchedBorder());
    jPanel7.setLayout(borderLayout6);
    getContentPane().add(panel1);
    panel1.add(jPanel3, BorderLayout.CENTER);
    jPanel3.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel6, "�Էº��� ����");
    jPanel6.add(jPanel8, BorderLayout.NORTH);
    jPanel8.add(jLabel2, null);
    jPanel8.add(jTextField1, null);
    jPanel6.add(jPanel9, BorderLayout.CENTER);
    jPanel9.add(jPanel11, BorderLayout.NORTH);
    jPanel11.add(jLabel14, null);
    jPanel11.add(jLabel15, null);
    jPanel9.add(jPanel10, BorderLayout.CENTER);
    jPanel10.add(jLabel4, null);
    jPanel10.add(jTextField2, null);
    jPanel10.add(jComboBox1, null);
    jPanel10.add(jLabel5, null);
    jPanel10.add(jTextField3, null);
    jPanel10.add(jComboBox2, null);
    jTabbedPane1.add(jPanel7, "�÷� ����");
    jPanel7.add(jPanel12, BorderLayout.NORTH);
    jPanel12.add(jLabel6, null);
    jPanel12.add(jLabel7, null);
    jPanel12.add(jLabel8, null);
    jPanel12.add(jLabel9, null);
    jPanel12.add(jLabel12, null);
    jPanel12.add(jLabel13, null);
    jPanel7.add(jPanel13, BorderLayout.CENTER);
    jPanel13.add(jPanel14, null);
    jPanel14.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTable1, null);
    jPanel13.add(jPanel15, null);
    jPanel15.add(jButton6, null);
    jPanel15.add(jButton7, null);
    jPanel15.add(jButton8, null);
    jPanel13.add(jPanel16, null);
    jPanel16.add(jLabel3, null);
    jPanel16.add(jTextField4, null);
    jPanel16.add(jLabel10, null);
    jPanel16.add(jTextField5, null);
    jPanel16.add(jLabel11, null);
    jPanel16.add(jTextField6, null);
    panel1.add(jPanel5, BorderLayout.NORTH);
    jPanel5.add(jLabel1, BorderLayout.CENTER);
    panel1.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(jButton1, null);
    jPanel1.add(jButton2, null);
    this.setResizable(false);
  }

  public String int2Str(int i){
    Integer ii = new Integer(i);
    return ii.toString();
  }

  public String float2Str(float f){
    Float ff = new Float(f);
    return ff.toString();
  }

  public int str2Int(String str){
    Integer i = new Integer(str);
   	return i.intValue();
  }

  public float str2Float(String str){
    Float f = new Float(str);
   	return f.floatValue();
  }

  private void tableSetting(){
    //�ѹ��� �ϳ��� row�� ���õǵ��� �Ѵ�.(default�� ��Ƽ row ���� ����)
    jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    Vector attriName = new Vector();  //attribute name�� ������ Vector
    Vector attriType = new Vector();  //attribute type�� ������ Vector
    getAttributesList(attriName, attriType);

    seqModel.setData(attriName, attriType);  //tableModel�� data���� setting
    jTable1.tableChanged(new TableModelEvent(seqModel) );
    jTable1.setModel(seqModel);

    //2��° Į��(checkbox)�� edit �����ϰ�(frolle)

    //column width �����ϱ�
    TableColumn column = null;
    for (int i = 0; i < 3; i++){
      column = jTable1.getColumnModel().getColumn(i); //return : TableColumnModel, TableColumn
      if(i == 0){
        column.setPreferredWidth(40);
      }else if(i == 1){
        column.setPreferredWidth(120);
      }else{
        column.setPreferredWidth(80);
      }
    }//end for
  }

  private void getAttributesList(Vector attriName, Vector attriType){

    //project���� dataFile���� �����´�.

    //meta������ �о� num_rows�� num_columns�� �ʱ�ȭ�Ѵ�. (frolle)
    String rowNumStr = m_xmmsequence.m_sMNodeSequence.getProfile(projectName, metaFile, "NUMBER_OF_ROWS");
    String colNumStr = m_xmmsequence.m_sMNodeSequence.getProfile(projectName, metaFile, "NUMBER_OF_COLUMNS"); //attributes ��
    String colList =  m_xmmsequence.m_sMNodeSequence.getProfile(projectName, metaFile, "COLUMN_LIST");
    String newColList = m_xmmsequence.m_sMNodeSequence.getProfile(projectName, metaFile, "NEW_COLUMN_LIST");
    String colIndex = m_xmmsequence.m_sMNodeSequence.getProfile(projectName, metaFile, "COLUMN_INDEX");
    String newColIndex = new String();

    if(!newColList.equals("null")) newColIndex = m_xmmsequence.m_sMNodeSequence.getProfile(projectName, metaFile, "NEW_COLUMN_INDEX");

    //DB�� instances ���� set�Ѵ�.
    if(!rowNumStr.equals("null")) num_instances = Integer.parseInt(rowNumStr);
    else num_instances = -1; //�ӽ� ����

    //colList�� colIndex�� ������ attribute name�� ���Ѵ�.
    StringTokenizer tokend_colList = new StringTokenizer(colList, "^");
    while(tokend_colList.hasMoreElements()){
      attriName.add( (String)tokend_colList.nextElement() ); //column list�� temp ���Ϳ� �ִ´�.
    }
    StringTokenizer tokend_colIndex = new StringTokenizer(colIndex, "^");
    int num_rows = tokend_colIndex.countTokens();

    //newColList�� newColIndex�� ������ ������ attribute name�� ���Ѵ�.
    if(!newColList.equals("null")){
	  StringTokenizer tokend_newColList = new StringTokenizer(newColList, "^");
	  while(tokend_newColList.hasMoreElements()) attriName.add( (String)tokend_newColList.nextElement() ); //column list�� temp ���Ϳ� �ִ´�.
	  StringTokenizer tokend_newColIndex = new StringTokenizer(newColIndex, "^");
      num_rows += tokend_newColIndex.countTokens();
	}

	//DB�� Attributes ���� set�Ѵ�.
    num_attributes = num_rows;    //table�� row��(= DB�� Attributes��)

    //column�� type�� ���� attriType Vector�� �����Ѵ�.
    String colName, colType, colProperty;
    int size = attriName.size();
    for(int i = 0; i < size; i++){
      colName = (String)attriName.elementAt(i);
      colProperty =  m_xmmsequence.m_sMNodeSequence.getProfile(projectName, metaFile, colName);  //�ش� column �Ӽ��� �д´�.
      StringTokenizer tokend_colProperty = new StringTokenizer(colProperty, "^");
      colType = (String)tokend_colProperty.nextElement(); //column �Ӽ����� column type�� ��´�.
      attriType.add(colType);
    }
  }

  private void setParameter(){   //���Ͽ��� �о� �Ķ���� ���� �����Ѵ�~~
    //setting
    jTextField1.setText(modelName);
    jComboBox1.setSelectedIndex(min_sup_type);  //0: ��, 1: %

    if( min_sup_type == 0){  //"��"
      jTextField2.setText(int2Str((int)min_sup));  //min_sup
    }else{  //"%"
      Float ff = new Float(min_sup);
      jTextField2.setText(ff.toString()); //min_sup
    }

    Float ff = new Float(min_conf);
    jTextField3.setText(ff.toString());   //min_conf

    if(num_instances != -1){
      String num = int2Str(num_instances);
      jLabel7.setText(num);   //�ι�° ���� instance ��
    }else{
      jLabel7.setText("");   //�ι�° ���� instance ��
    }

    if(transNumber != -1){
      String num = int2Str(transNumber);
      jLabel15.setText(num);  //ù��° ���� transaction ��
      jLabel13.setText(num);  //�ι�° ���� transaction ��
    }else{
      jLabel15.setText("");  //ù��° ���� transaction ��
      jLabel13.setText("");  //�ι�° ���� transaction ��
    }

    jLabel9.setText(int2Str(num_attributes));    //�ι�° ���� Attributes ��

    jTextField4.setText(transIDFld);
    jTextField5.setText(targetFld);
    jTextField6.setText(timeFld);

    jTextField4.setEditable(false);
	  jTextField5.setEditable(false);
	  jTextField6.setEditable(false);
  }

  void jTextField2_actionPerformed(ActionEvent e) { //minimum support check
    String temp = jTextField2.getText();
    if(temp.length() > 0) min_sup = Float.parseFloat(temp);
    int SelectedIndex = jComboBox1.getSelectedIndex();

    if(SelectedIndex == 0){  //��
      //������ ���� �ʴٸ� MessageBox�� ����Ѵ�.
      if( min_sup <= 0f){
        MessageBox  err_box = new MessageBox(this,"�����޽���",true,"�Է� ���� ����","0�̻��� ������ �Է��ϼ���");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = err_box.getSize();
        if(frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
      }
    }else{
      //������ ���� �ʴٸ� MessageBox�� ����Ѵ�.
      if( min_sup <= 0f || min_sup > 100f ){
        MessageBox  err_box = new MessageBox(this,"�����޽���",true,"�Է� ���� ����","0���� 100������ ���� �Է��ϼ���");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = err_box.getSize();
        if(frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        err_box.setVisible(true);
        return;
      }
    }//end else
  }

  void jTextField3_actionPerformed(ActionEvent e){   //minimum confidence check
    String temp = jTextField3.getText();
    if(temp.length() > 0) min_conf = Float.parseFloat(temp);

    //������ ���� �ʴٸ� MessageBox�� ����Ѵ�.
    if(min_conf <= 0f || min_conf > 100f){
      MessageBox  err_box = new MessageBox(this,"�����޽���",true,"�Է� ���� ����","0���� 100������ ���� �Է��ϼ���");
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = err_box.getSize();
      if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
      err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      err_box.setVisible(true);
      return;
    }
  }

  void jTextField1_actionPerformed(ActionEvent e){ //model name
    modelName = jTextField1.getText();
  }

  void jButton6_actionPerformed(ActionEvent e){ //transIDFld
    //transIDFld�� table���� ���õ� attribute name�� �ִ´�.
    int selectColumn = jTable1.getSelectedRow();
    String columnName = (String)seqModel.getValueAt(selectColumn, 1);
    transIDFld = columnName;
    jTextField4.setText(columnName);
  }

  void jButton7_actionPerformed(ActionEvent e) {  //targetFld
    //targetFld�� table���� ���õ� attribute name�� �ִ´�.
    int selectColumn = jTable1.getSelectedRow();
    String columnName = (String)seqModel.getValueAt(selectColumn, 1);
    targetFld = columnName;
    jTextField5.setText(columnName);
  }

  void jButton8_actionPerformed(ActionEvent e) {  //timeFld
    //timeFld�� table���� ���õ� attribute name�� �ִ´�.
    int selectColumn = jTable1.getSelectedRow();
    String columnName = (String)seqModel.getValueAt(selectColumn, 1);
    String columnType = (String)seqModel.getValueAt(selectColumn, 2);

    timeFld = columnName;
    jTextField6.setText(columnName);
  }

  //"OK" Button
  void jButton1_actionPerformed(ActionEvent e){  //"OK" Button
    modelName = jTextField1.getText();
    String sup_temp = jTextField2.getText();  //min_sup
    min_sup_type = jComboBox1.getSelectedIndex(); //0 :��, 1: %
    if( sup_temp.length() > 0 ){
      min_sup = Float.parseFloat(sup_temp);
      if(min_sup_type == 1) min_sup = min_sup/100f;
    }

    String conf_temp = jTextField3.getText();     //min_conf
    if( conf_temp.length() > 0 ){
      min_conf = Float.parseFloat(conf_temp);
      min_conf = min_conf/100f; //%
    }

    transIDFld = jTextField4.getText();
    targetFld = jTextField5.getText();
    timeFld = jTextField6.getText();

    //����ִ� �ʵ尡 ������ �����޽��� ���
    if((modelName.length() == 0) || (sup_temp.length() == 0) || (conf_temp.length() == 0) || (transIDFld.length() == 0) || (targetFld.length() == 0) || (timeFld.length() == 0)){
      MessageBox  err_box = new MessageBox(this,"�����޽���",true,"�Է� ����","  ����ִ� �ʵ尡 �ֽ��ϴ�. ���� �Է��ϼ���");
      java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      java.awt.Dimension frameSize = err_box.getSize();
      if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
      if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
      err_box.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      err_box.setVisible(true);
      return;
    }

    String parameterFile = modelName+"_seq_par";
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"PROJECT_NAME", projectName);
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"PROJECT_PATH", m_xmmsequence.ProjectPath);
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"METAFILE_NAME", metaFile);
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"MODEL_NAME", modelName);
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"MIN_SUP_TYPE", int2Str(min_sup_type));
    if(min_sup_type == 0) m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"MIN_SUP",  int2Str((int)min_sup));
	else m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"MIN_SUP",  float2Str(min_sup));
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"NUMBER_OF_TRANS",  int2Str(transNumber));
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"MIN_CONF", float2Str(min_conf));
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"TRANSID_FLD", transIDFld);
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"TARGET_FLD", targetFld);
    m_xmmsequence.m_sMNodeSequence.setProfile(projectName, parameterFile,"TIME_FLD", timeFld);
    m_xmmsequence.m_sMNodeSequence.setSchema(m_xmmsequence.ProjectName,modelName+"_seq_rule");
    m_xmmsequence.setParameterName(parameterFile,true,modelName);


    //XMMNodeSequenceRule Ŭ������ ��������� ���� �����Ѵ�.
    m_xmmsequence.ProjectName = projectName;
    m_xmmsequence.MetaFileName = metaFile;
    m_xmmsequence.model = modelName;

    m_xmmsequence.transNumber = transNumber;
    min_sup = Float.parseFloat(sup_temp); //�Է¹��� �״�� �����ϱ�����..
    m_xmmsequence.min_sup = min_sup;
    min_conf = Float.parseFloat(conf_temp); //�Է¹��� �״�� �����ϱ�����..
    m_xmmsequence.min_conf = min_conf;
    m_xmmsequence.min_sup_type = min_sup_type;

    m_xmmsequence.transIDFld = transIDFld;
    m_xmmsequence.targetFld = targetFld;
    m_xmmsequence.timeFld = timeFld;

    this.dispose();
  }

  void jButton2_actionPerformed(ActionEvent e){
    this.dispose();
  }

  private boolean nullCheck(String i_str){
    boolean out_bool = false;
    if (i_str==null) out_bool = true;
    else{
      if(i_str.equals("")) out_bool = true;
    }
    return out_bool;
  }

  private boolean nullCheck(String[] i_str){
    if (i_str.length==0) return true;
    else return false;
  }

}