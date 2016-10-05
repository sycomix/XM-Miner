package xmminer.xmclient.xmgraph.xmmnode.xmmnodesequencerule;

import javax.swing.table.AbstractTableModel;
import javax.swing.JCheckBox;
import java.util.Vector;

class seqTableModel extends AbstractTableModel{
  Object[][] i_data;  //(frolle)

  String[] cn = {"��ȣ", "�÷� ��", "Ÿ��"};
  int num_attributes = 0;   //DB�� Attributes ��(=talbe�� row ��)

  public int getColumnCount() { //3
    return cn.length;
  }

  public int getRowCount() {  //DB�� Attributes ��(=talbe�� row ��)
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
    return false;
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

  //assoInputDialog�� �����ڿ��� �ҷ����� �Լ�
  public void setData(Vector attriName, Vector attriType){
    num_attributes = attriName.size(); //table�� row ��
    i_data = new Object[num_attributes][3];

    for(int i = 0; i < num_attributes;i ++){
      //No. �ֱ�
      i_data[i][0] = new Integer( i + 1 );

      //attribute name cell �ֱ�
      i_data[i][1] = (String)attriName.elementAt(i);

      //attribute type cell �ֱ�
      i_data[i][2] = (String)attriType.elementAt(i);
    }
  }

}