package xmminer.xmclient.xmgraph.xmmnode.xmmnodeassociationrule;

import javax.swing.table.AbstractTableModel;
import javax.swing.JCheckBox;
import java.util.Vector;

class assoTableModel extends AbstractTableModel{
  private Object[][] i_data; // = new Object[100][4];  //(frolle)

  String[] cn = {"��ȣ", "����", "�÷� ��", "Ÿ��"};
  int num_attributes = 0;   //DB�� Attributes ��(=talbe�� row ��)

  public int getColumnCount(){ //4
    return cn.length;
  }

  public int getRowCount(){  //DB�� Attributes ��(=talbe�� row ��)
    return num_attributes;
  }

  public String getColumnName(int col){
    return cn[col];
  }

  public Object getValueAt(int row, int col){
    return i_data[row][col];
  }

  public Class getColumnClass(int c){
    return getValueAt(0, c).getClass();
  }

  public boolean isCellEditable(int row, int col){
    if (col == 1) return true;
    else return false;
  }

  public void setValueAt(Object value, int row, int col){
    i_data[row][col] = value;
    fireTableCellUpdated(row, col);
  }

  public void setDataList(Object[][] value){
    i_data = value;
  }

  public void setHeaderList(String[] val, Object[][] value){
    cn = val;
    i_data = value;
  }

  //"All" button�� �������� �ҷ��� �Լ�
  public void includeAll(){
    for (int i = 0; i < num_attributes; i++) //table�� row �� ��ŭ
	    i_data[i][1] = new Boolean(true);
    fireTableRowsUpdated(0, num_attributes);
  }

  //"None" button�� �������� �ҷ��� �Լ�
  public void removeAll() {
    for (int i = 0; i < num_attributes; i++) //table�� row �� ��ŭ
      i_data[i][1] = new Boolean(false);
    fireTableRowsUpdated(0, num_attributes);
  }

  //"Invert" button�� �������� �ҷ��� �Լ�
  public void invert(){
    for(int i = 0; i < num_attributes; i++){ //table�� row �� ��ŭ
      if(((Boolean)i_data[i][1]).booleanValue() == true) i_data[i][1] = new Boolean(false);
      else i_data[i][1] = new Boolean(true);
    }
    fireTableRowsUpdated(0, num_attributes);
  }

  //assoInputDialog�� �����ڿ��� �ҷ����� �Լ�
  public void setData(Vector attriName, Vector attriType){
    num_attributes = attriName.size(); //table�� row ��
    i_data = new Object[num_attributes][4];

    for(int i = 0; i < num_attributes;i ++){
      //No. �ֱ�
      i_data[i][0] = new Integer( i + 1 );

      //Boolean(check) �ֱ�
      i_data[i][1] = new Boolean(true);

	  //attribute name cell �ֱ�
      i_data[i][2] = (String)attriName.elementAt(i);

      //attribute type cell �ֱ�
      i_data[i][3] = (String)attriType.elementAt(i);
    }
  }

}