package xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule;

import java.util.*;
import java.io.*;

import xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class Litemset{
  private int num_items;
  public Hashtable table = new Hashtable();

  static int min_sup;
  static int num_trans;
  static String dataFile;

  private String projectName;
  private String projectPath;
  private String modelName;

  private int dbType;
  private String[] selAttributes;   //dbType = 1
  private String[] columnList;      //dbType = 2

  private String transIDFld;  //Type 2���� ������ �Ǵ� �ʵ��(transaction ID)
  private String targetFld;   //Type 2���� item �ʵ��

  public Litemset(int num, int support, String model, int transNumber, String project, String data, String path){
    projectName = project;
    projectPath = path;
    dataFile = data;

    num_items = num;
    min_sup = support;
    modelName = model;

    num_trans = transNumber;
  }

  public Litemset(int num){
    num_items = num;
  }

  public void setParam1(String[] attributes){ //dbType = 1
    //���õ� Į������ �Ľ��Ͽ� selAttributes ��������� �ʱ�ȭ�Ѵ�.
    dbType = 1;
    selAttributes = attributes;
  }

  public void setParam2(String transIDFldName, String targetFldName, String[] cols){ //dbType = 2
    dbType = 2;

    transIDFld = transIDFldName;
    targetFld = targetFldName;

    columnList = cols;
  }

  public int gen_litemset() throws IOException{  //dbType = 1
    int num_litems;

    get_support();
    num_litems = get_large_itemset();      //small itemset�� table���� �����Ѵ�.

    return num_litems;
  }

  public int gen_litemset2() throws IOException{  //dbType = 2
    int num_litems;

    get_support2();
    num_litems = get_large_itemset();      //small itemset�� table���� �����Ѵ�.

    return num_litems;
  }

  public void read_allItem() throws IOException{
    FileInputStream allItem_file = null;
    allItem_file = new FileInputStream(projectPath + projectName + "/data/" + dataFile + ".udat");
    BufferedReader allItem_data = new BufferedReader(new InputStreamReader(allItem_file));

    String item = new String();
    String line = allItem_data.readLine();
    StringTokenizer tokenized_line = new StringTokenizer(line, ",");

    while(tokenized_line.hasMoreTokens()){
      item = tokenized_line.nextToken();
      table.put(item, new Integer(0));
    }

    allItem_file.close();
  }

  public int itemsDataSaver(){ //dbType=1�� association���� ��� ����
    CXMUniqueValueFileSaver itemsData = new CXMUniqueValueFileSaver();
    itemsData.setFileStatus(projectName, dataFile);

    itemsData.makeUniqueValueFileInColumnList(selAttributes, true);
    itemsData.close();

    return num_trans;
  }

  public void itemsDataSaver2(){
    String[] colList = new String[1];
    colList[0] = targetFld;

    CXMUniqueValueFileSaver itemsData = new CXMUniqueValueFileSaver();
    itemsData.setFileStatus(projectName, dataFile);
    itemsData.makeUniqueValueFileInColumnList(colList, false);
    itemsData.close();
  }

  private void get_support() throws IOException{  //dbType = 1
    //transaction�� �о� Ct�� ã��, Hashtable�� �ش� support�� ������Ų��.
    String itemset;    //item;
    Vector transaction, contained_itemset;

    CXMTableQuerier transData = new CXMTableQuerier();  //transaction data file
    transData.setFileStatus(projectName, dataFile, null, selAttributes);

    for(int t = 0; t < num_trans; t++){  //transaction ��
      transaction = transData.getTransaction(t + 1);

      contained_itemset = contained_itemsets(transaction);  //Vector
      int num_Ct = contained_itemset.size();

      for(int i=0; i < num_Ct; i++){

        itemset = (String)contained_itemset.elementAt(i);
        if(table.containsKey(itemset)){   //table�� itemset�� ����Ǿ� ������
          Integer sup = (Integer)table.get(itemset);

          int sup_tmp = sup.intValue();
          sup_tmp++;                    //�ش� item�� support�� ������Ų��.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      contained_itemset.clear();

    }//end for

    transData.close();
  }

  private void get_support2() throws IOException{  //dbType = 2
    //transaction�� �о� Ct�� ã��, Hashtable�� �ش� support�� ������Ų��.
    String itemset, item;      //, colUniqueValue;
    Vector transaction, contained_itemset;

    String transLine;

    CXMTableReader transData2 = new CXMTableReader();  //transaction data file
    transData2.setFileStatus(projectName, modelName + "_asso_trans");

    for(int t = 0; t < num_trans; t++){  //transaction ��
      transaction = new Vector();

      //cxmtable���� �ϳ��� transaction�� �о� transaction Vector�� �����Ѵ�.
      transLine = transData2.getString(t + 1);
      StringTokenizer tokened_trans = new StringTokenizer(transLine, ",");
      while(tokened_trans.hasMoreTokens()){
        item = tokened_trans.nextToken();
        transaction.add(item);
      }

      contained_itemset = contained_itemsets(transaction);  //Vector
      int num_Ct = contained_itemset.size();

      for(int i=0; i < num_Ct; i++){
        itemset = (String)contained_itemset.elementAt(i);
        if(table.containsKey(itemset)){   //table�� itemset�� ����Ǿ� ������
          Integer sup = (Integer)table.get(itemset);
          int sup_tmp = sup.intValue();
          sup_tmp++;                    //�ش� item�� support�� ������Ų��.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      contained_itemset.clear();
    }//end for
    transData2.close();
  }

  public Vector contained_itemsets(Vector transaction){
    //table�� itemset�� �� �ش� trans�� subset(Ct)�� ���� ã�� Vector�� �����Ѵ�.
    String item = new String();
    String itemset = new String();
    Vector contained_itemset = new Vector();

    Enumeration itemsets = table.keys();

    outsideLoop:   //---------------------
    while(itemsets.hasMoreElements()){
      itemset = (String)itemsets.nextElement();

      StringTokenizer tokenized_itemset = new StringTokenizer(itemset, ",");
      while(tokenized_itemset.hasMoreTokens()){
        item = tokenized_itemset.nextToken();

        //��� item�� trans�� substring���� ���ԵǾ� �ִ��� Ȯ���Ѵ�...
        if( transaction.contains(item) == false ){
          continue outsideLoop;   //���Ե��� ������ ���� itemset���� �Ѿ��.
        }
      }//end inside while
      contained_itemset.addElement(itemset);
    }//end outside while
    return contained_itemset;
  }

  public int apriori_gen(Litemset Candiset){
  //candidate itemsets�� ���Ѵ��� ������ itemset�� ���� �����Ѵ�.
    String pitemset = new String();
    String qitemset = new String();
    String temp = new String();

    if(num_items == 1){   // 1���� item���� �̷���� itemset����..
      int num_keys = table.size();
      String[] items = new String[num_keys];

      //items�� �����Ѵ�.
      int j = 0;
      Enumeration itemsets = table.keys();
      while(itemsets.hasMoreElements()){
        items[j++] = (String)itemsets.nextElement();
      }
      Arrays.sort(items);

      //String items[]�� ������ join�۾��� �����Ѵ�.
      for(int i = 0; i < num_keys; i++){
        for(int k=i+1; k < num_keys; k++){
          pitemset = items[i];
          qitemset = items[k];
          temp = pitemset + "," + qitemset;
          Candiset.table.put(temp, new Integer(0));
        }
      }//end for
    }else{     // num_items�� 2�̻��� ��
      Vector candiset;

      if (dbType == 1 ){ //dbType = 1
        candiset = join();
      }else{    //dbType = 2
        candiset = join2();
      }
      candiset = prune(candiset);

      //Litemset�� candidate itemset�� �����Ѵ�.
      int num_candisets = candiset.size();
      for(int i=0; i < num_candisets; i++){
        temp = (String)candiset.elementAt(i);
        Candiset.table.put(temp, new Integer(0));
      }
    }//end else

    return Candiset.table.size();
  }

  private Vector join() throws IndexOutOfBoundsException{
    //table�� itemset���� join�Ͽ� �ӽ� candidate itemset�� �����Ѵ�.
    Vector candiset = new Vector();
    String temp;
    String cmp_itemset = new String();
    int capacityIncrement = 0;   //String itemsets[]������ ������
    //k-1�������� items�� ������ itemset���� sort�ϱ� ���� ��Ƶ� ����
    //�������� �Ҵ��ϴ� �� �����غ���..!(system.arraycopy)
    int i;        //������  block�� ���ϴ� itemset ��
    int index;    //k-1�������� items�� �����ϱ� ���� ����

    //table�� itemsets�� Vector�� ������ �����Ѵ�.
    Vector table_tmp = new Vector();
    Enumeration itemsets_tmp = table.keys();
    while(itemsets_tmp.hasMoreElements()){
      temp = (String)itemsets_tmp.nextElement();
      table_tmp.addElement(temp);
    }

    //����� Vector�� ���� table_tmp�� ������ �ϳ��� �����´�.
    itemsets_tmp = table_tmp.elements();
    while(table_tmp.isEmpty() == false){ //empty�� �ƴ� ����...
      String itemsets[] = new String[10];   //k-1������ item�� ������ itemset�� ��Ƶ� ����
      i = 0;
      capacityIncrement = 0;
      itemsets_tmp = table_tmp.elements();
      temp =(String)itemsets_tmp.nextElement();
      itemsets[i++] = temp;
      capacityIncrement = capacityIncrement + 1;

      index = temp.lastIndexOf(',');
      cmp_itemset = temp.substring(0, index + 1);   //','���� ���Ե� cmp_itemset

      //cmp_itemset �κ��� ������ itemset���� ���Ͽ� itemsets[]�� �����Ѵ�.
      while(itemsets_tmp.hasMoreElements()){
        temp = (String)itemsets_tmp.nextElement();

        //itemset_tmp�� qitemset�� �� �� �ִ��� Ȯ���Ѵ�.
        if(temp.indexOf(cmp_itemset) == 0){   //k-1�������� items�� �����ϴٸ�,
          itemsets[i++] = temp;

          //capacityIncrement�� 10�� �Ǹ� �޸𸮸� �÷��ش�...!!
          capacityIncrement = capacityIncrement + 1;
          if(capacityIncrement == 10){
            capacityIncrement = 0;            //�ٽ� 0���� �ʱ�ȭ

            int oldCapacity = itemsets.length;
            String oldData[] = itemsets;
            int newCapacity = oldCapacity + 10;
            itemsets = new String[newCapacity];
            System.arraycopy(oldData, 0, itemsets, 0, oldCapacity);
          }
        }
      }//end while
      Arrays.sort(itemsets, 0, i);     //String itemsets[]�� �����Ѵ�.(i-1)???

      //String itemsets[]�� ������, ���� join�۾��� �����Ѵ�.
      String pitemset, qitemset;

      for(int j=0; j < i; j++){
        for(int k=j+1; k < i; k++){

          pitemset = itemsets[j];
          qitemset = itemsets[k];

          index = pitemset.lastIndexOf('_');
          temp = pitemset.substring(0, index);
          index = qitemset.lastIndexOf('_');

          if( ! temp.equals( qitemset.substring(0, index) )){ //pitemset�� qitemset�� ���� Į���� �ƴϸ�,

            temp = cmp_itemset;
            index = pitemset.lastIndexOf(',');
            temp += pitemset.substring(index+1) + ","; //pitemset�� ������ item �߰�

            index = qitemset.lastIndexOf(',');
            temp += qitemset.substring(index+1);  //qitemset�� ������ item �߰�

            candiset.addElement(temp);     //Vector ������ ����
          }
        }
      }//end for
      for(int j=0; j < i; j++){
        temp = itemsets[j];
        table_tmp.removeElement(temp);
      }
    }//end while

    return candiset;
  }

  private Vector join2() throws IndexOutOfBoundsException{
    //table�� itemset���� join�Ͽ� �ӽ� candidate itemset�� �����Ѵ�.
    Vector candiset = new Vector();
    String temp;
    String cmp_itemset = new String();
    int capacityIncrement = 0;   //String itemsets[]������ ������
    //k-1�������� items�� ������ itemset���� sort�ϱ� ���� ��Ƶ� ����
    //�������� �Ҵ��ϴ� �� �����غ���..!(system.arraycopy)
    int i;        //������  block�� ���ϴ� itemset ��
    int index;    //k-1�������� items�� �����ϱ� ���� ����

    //table�� itemsets�� Vector�� ������ �����Ѵ�.
    Vector table_tmp = new Vector();
    Enumeration itemsets_tmp = table.keys();
    while(itemsets_tmp.hasMoreElements()){
      temp = (String)itemsets_tmp.nextElement();
      table_tmp.addElement(temp);
    }

    //����� Vector�� ���� table_tmp�� ������ �ϳ��� �����´�.
    itemsets_tmp = table_tmp.elements();
    while(table_tmp.isEmpty() == false){ //empty�� �ƴ� ����...
      String itemsets[] = new String[10];   //k-1������ item�� ������ itemset�� ��Ƶ� ����
      i = 0;
      capacityIncrement = 0;
      itemsets_tmp = table_tmp.elements();
      temp =(String)itemsets_tmp.nextElement();
      itemsets[i++] = temp;
      capacityIncrement = capacityIncrement + 1;

      index = temp.lastIndexOf(',');
      cmp_itemset = temp.substring(0, index + 1);   //','���� ���Ե� cmp_itemset

      while(itemsets_tmp.hasMoreElements()){
        temp = (String)itemsets_tmp.nextElement();

        //itemset_tmp�� qitemset�� �� �� �ִ��� Ȯ���Ѵ�.
        if(temp.indexOf(cmp_itemset) == 0){   //k-1�������� items�� �����ϴٸ�,
          itemsets[i++] = temp;

          //capacityIncrement�� 10�� �Ǹ� �޸𸮸� �÷��ش�...!!
          capacityIncrement = capacityIncrement + 1;
          if(capacityIncrement == 10){
            capacityIncrement = 0;            //�ٽ� 0���� �ʱ�ȭ

            int oldCapacity = itemsets.length;
            String oldData[] = itemsets;
            int newCapacity = oldCapacity + 10;
            itemsets = new String[newCapacity];
            System.arraycopy(oldData, 0, itemsets, 0, oldCapacity);
          }
        }
      }//end while
      Arrays.sort(itemsets, 0, i);     //String itemsets[]�� �����Ѵ�.(i-1)???

      //String itemsets[]�� ������, ���� join�۾��� �����Ѵ�.
      String pitemset, qitemset;

      for(int j=0; j < i; j++){
        for(int k=j+1; k < i; k++){
          temp = cmp_itemset;

          pitemset = itemsets[j];
          qitemset = itemsets[k];

          index = pitemset.lastIndexOf(',');
          temp += pitemset.substring(index+1) + ","; //pitemset�� ������ item �߰�

          index = qitemset.lastIndexOf(',');
          temp += qitemset.substring(index+1);  //qitemset�� ������ item �߰�

          candiset.addElement(temp);     //Vector ������ ����
        }
      }//end for

      for(int j=0; j < i; j++){
        temp = itemsets[j];
        table_tmp.removeElement(temp);
      }
    }//end while
    return candiset;
  }

  private Vector prune(Vector candiset){
    //join���� ������ itemset���� table�� itemset�� ���Ͽ�
    //table�� itemset�� subset�� �ƴϸ� pruning�Ѵ�.
    //candiset : join���� ������ itemsets

	int num_candisets = candiset.size();

    for(int i = 0; i < num_candisets; i++){
      String itemset = (String)candiset.elementAt(i);
      Vector subsets = subset(itemset);

      int num_subsets = subsets.size();
      //num_subsets���� subset ��� table�� ���ԵǾ� �־��
      //candiset�� itemset�� pruning��Ű�� �ʴ´�.
      for(int j=0; j < num_subsets; j++){

        String subset = (String)subsets.elementAt(j);
        if(table.containsKey(subset) == false){ //subset�� table�� ���Ե��� ������,
          candiset.remove(i);          //�ش� candidate itemset�� �����Ѵ�.
          i--;
          num_candisets--;
          break;
        }
      }//end for
    }//end outside for

    return candiset;
  }

  public static Vector subset(String itemset){
    //��� ������ k-1���� items�� �̷���� subset�� ���Ѵ�.
    Vector items = new Vector();
    Vector subsets = new Vector();
    String temp;

    StringTokenizer tokenized_itemset = new StringTokenizer(itemset, ",");
    while(tokenized_itemset.hasMoreTokens()){
      temp = tokenized_itemset.nextToken();
      items.addElement(temp);
    }
    int s = items.size();
    //item_tmp�� ������������ �����ذ��� subset�� �����Ѵ�.
    Vector items_tmp = (Vector)items.clone();
    for(int i = s-1; i > -1; i--){
      items_tmp.remove(i);
      
      temp = new String();
      int d = s -1;
      for(int j = 0; j < d; j++){
        temp += (String)items_tmp.elementAt(j) + ",";
      }
      temp = temp.substring(0, temp.length() - 1);

      if(temp.length() > 0){
        subsets.addElement(temp);   //�ϳ��� subset�� �߰��Ѵ�.
      }
      items_tmp = (Vector)items.clone();
    }
    return subsets;
  }

  private int get_large_itemset(){
    //���� itemset �� large�� �͸��� ��󳽴�.
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = (String)itemsets.nextElement();
      sup = (Integer)table.get(itemset);       //support
      sup_tmp = sup.intValue();

      if(sup_tmp < min_sup){
        table.remove(itemset);   //small itemset�� Hashtable���� �����Ѵ�.
      }
    }//end while

    return table.size();     //large itemset�� ������ �����Ѵ�.
  }

  private void write_itemset(String fname) throws IOException{
    //���Ͽ� itemset�� support�� �����Ѵ�.

    FileOutputStream itemset_file = null;
    DataOutputStream itemset_data = null;

    itemset_file = new FileOutputStream(fname);
    itemset_data = new DataOutputStream(itemset_file);

    //temp�� StringBuffer�� �ٲ㺸��....!
    //sup�� int������ ��ȯ�ʰ� �ٷ� ���Ͽ� ����.(append function)
    String temp = new String();    //line�� ������ �ӽ� ����
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();
      temp = itemset + ";" + new Integer(sup_tmp).toString() + "\r\n";
	  itemset_data.writeBytes(temp);
    }

    itemset_file.close();
  }

  public static byte[] addColAtRow(byte col[], byte row[], int[] colIndex, int j){
    //���� row���� col�� �����̰�, colIndex[j]�� col�� length�� �߰��ϴ� ��ƾ�̴�.
    int colLength = col.length;
    int rowLength = row.length;
    int newCapacity = colLength + rowLength;

    byte[] row_tmp = new byte[newCapacity];

    System.arraycopy(row, 0, row_tmp, 0, rowLength);
    System.arraycopy(col, 0, row_tmp, rowLength, colLength);

    colIndex[j] = colLength;

    return row_tmp;
  }

}