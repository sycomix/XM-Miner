package xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.awt.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;
import xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule.*;

public class LSitemset {
  private int num_items;
  Hashtable table = new Hashtable();

  static int min_sup;
  static int num_L1Sequence;    //�� litem(L1Sequence)��
  static int num_trans;  //customer-transaction ��

  private String dataFile;
  private String projectName;
  private String modelName;
  private String projectPath;

  private String transIDFld;  //������ �Ǵ� �ʵ��(transaction ID)
  private String targetFld;   //item �ʵ��
  private String timeFld;     //time �ʵ��

  public LSitemset(int num, int support, String model, int transNumber, String project, String data, String path){
    projectName = project;
    projectPath = path;
    dataFile = data;
	  
    num_items = num;
    min_sup = support;
    modelName = model;

    num_trans = transNumber;
  }

  public LSitemset(int num){
    num_items = num;
  }

  public void setParam(String transIDFldName, String targetFldName, String timeFldName){
    transIDFld = transIDFldName;
    targetFld = targetFldName;
    timeFld = timeFldName;
  }

  public void readAllItem() throws IOException{
    FileInputStream allItem_file = new FileInputStream(projectPath + projectName + "/data/" + dataFile + ".udat");
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


  public void itemsDataSaver(){
    //"items.txt(dataFile.udat)�� �����Ѵ�.
    num_L1Sequence = 0;   //�� litem(L1Sequence)�� (�ѹ��� �ҷ����Ƿ�)

    String[] columnList = new String[1];
    columnList[0] = targetFld;

    CXMUniqueValueFileSaver itemsData = new CXMUniqueValueFileSaver();
    itemsData.setFileStatus(projectName, dataFile);
    itemsData.makeUniqueValueFileInColumnList(columnList, false);
    itemsData.close();
  }


  public int readItemList() throws IOException{
    //metaData file���� �� transcation ���� �˾Ƴ���
    //data file�� �о� ��� item���� items.txt�� �����Ѵ�.
    int num_trans;
    CXMGetUniqueColumnValue transData = new CXMGetUniqueColumnValue();
    transData.setFileStatus(projectName, dataFile, transIDFld);
    num_trans = (transData.getUniqueValueTree()).size();
    transData.close(); 

    return num_trans;
  }

  public void putLitem(int num_litems){
    //Large 1 Sequence�� table�� �ִ´�...
    for(int i = 0; i < num_litems ; i++){
      String item = Integer.toString(i);    //Integer(i).toString();,Integer.toString(i);
      table.put(item, new Integer(0));
    }
  }

  private boolean checkContains(String trans, String itemset){
    //trans�� itemset�� ���ԵǸ� true�� �����Ѵ�.
    //itemset�� �� item���� �������� ��� item�� ���Ե� �ִ°����� Ȯ���Ѵ�.
    String item = new String();
    boolean check = true;

    Vector imsi = new Vector();
    StringTokenizer tokenized_trans  = new StringTokenizer(trans,",");
    while(tokenized_trans.hasMoreTokens()){
      item = tokenized_trans.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");
    while(tokenizedItemset.hasMoreTokens()){
      item = tokenizedItemset.nextToken();

      //��� item�� trans�� substring���� ���ԵǾ� �ִ��� Ȯ���Ѵ�...
      if(imsi.contains(item) == false){
      //if(trans.indexOf(item) == -1){     //���ԵǾ� ���� ������...
        check = false;
        break;
      }
    }//end while

    return check;
  }

  private Hashtable addHashtable(Hashtable child, Hashtable parent){
    //child�� item�� parent�� �߰��Ѵ�.

    String itemset = new String();
    Integer support;

    Enumeration keySet = child.keys();

    while(keySet.hasMoreElements()){
      itemset = (String)keySet.nextElement();
      support = (Integer)child.get(itemset);
      parent.put(itemset, support);
    }

    return parent;
  }

  public int genLitemset(Hashtable L1sequence) throws IOException{
    //large 1-sequence(ex)40,70)�� ���ϱ����� ȣ��Ǵ� �Լ�
    int num_litems;

    getSupport();
    num_litems = getLargeItemset();     //small itemset�� table���� �����Ѵ�.

    //�̹� pass���� ������ large 1-sequence�� Hashtable L1sequence�� �߰��Ѵ�.
    addHashtable(table, L1sequence);

    return num_litems;  //������ L1Sequence ���� ����
  }

  public int genLitemset2() throws IOException{
    //large 1-sequence�� transform&mapping�� nctrans.txt������ ������
    //large k-sequence(k>=2)�� ���ϱ����� ȣ��Ǵ� �Լ�
    int num_litems;

    getSupport2();
    num_litems = getLargeItemset();      //small itemset�� table���� �����Ѵ�.

    return num_litems;
  }

  private void getSupport() throws IOException{
    //transaction�� �о� Ct�� ã��, Hashtable�� �ش� support�� ������Ų��.
    //large 1-sequence�� ã������ ȣ��Ǵ� �Լ�
    String itemset, trans;
    Vector cusSequence, containedItemset;
    String transLine;

    CXMTableReader transData2 = new CXMTableReader();  //transaction data file
    transData2.setFileStatus(projectName, modelName + "_seq_trans");

    for(int t = 0; t < num_trans; t++){  //transaction ��
      cusSequence = new Vector();

      //cxmtable���� �ϳ��� customer-transaction�� �о� transaction Vector�� �����Ѵ�.
      transLine = transData2.getString(t + 1);  //ex)"Juice;Cider,Water;Beer,Coke"
      StringTokenizer tokened_trans = new StringTokenizer(transLine, ";");
      while(tokened_trans.hasMoreTokens()){
        trans = tokened_trans.nextToken();
        cusSequence.add(trans);
      }

      containedItemset = containedItemsets(cusSequence);  //a customer transaction
      int numCt = containedItemset.size();
      for(int i=0; i < numCt; i++){
        itemset = (String)containedItemset.elementAt(i);
        if(table.containsKey(itemset)){   //table�� itemset�� ����Ǿ� ������
          Integer sup = (Integer)table.get(itemset);
          int sup_tmp = sup.intValue();
          sup_tmp++;                    //�ش� item�� support�� ������Ų��.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      containedItemset.clear();

    }//end for

    transData2.close();
  }

  public void getSupport2() throws IOException{
    //nctrans.txt���Ͽ��� transaction�� �о� Ct�� ã��, Hashtable�� �ش� support�� ������Ų��.
    //large k-sequence(k>=2)�� ã������ ȣ��Ǵ� �Լ�(nctrans.txt)
    FileInputStream db_file = new FileInputStream(projectPath + projectName + "/" + modelName + "_nctrans.txt");
	BufferedReader db_data = new BufferedReader(new InputStreamReader(db_file));

    String line = new String();

    // num_items�� 2�̻��� ��
    Vector containedItemset; // = new Vector();
    String itemset = new String();

    while((line = db_data.readLine()) != null){   //c:\test\nctrans.txt
      containedItemset = containedItemsets2(line);    //Ct�� ���Ѵ�.
      int numCt = containedItemset.size();

      for(int i=0; i < numCt; i++){
        itemset = (String)containedItemset.elementAt(i);
        if(table.containsKey(itemset)){   //table�� itemset�� ����Ǿ� ������
          Integer sup = (Integer)table.get(itemset);
          int sup_tmp = sup.intValue();
          sup_tmp++;                    //�ش� item�� support�� ������Ų��.
          sup = new Integer(sup_tmp);
          table.put(itemset, sup);
        }//end if
      }//end for
      containedItemset.clear();
    }//end while

    db_file.close();
  }

  private Vector containedItemsets(Vector cusSequence){
    //table�� itemset�� �� �ش� cusSequence�� subset�� ���� ã�� Vector�� �����Ѵ�.
    String trans = new String();
    String item = new String();
    String itemset = new String();

    Vector containedItemset = new Vector();

    int size = cusSequence.size();
    for(int i = 0; i < size; i++){
      trans = (String)cusSequence.elementAt(i);
      Enumeration itemsets = table.keys();

      outsideLoop:   //---------------------outsideLoop:
      while(itemsets.hasMoreElements()){
        itemset = (String)itemsets.nextElement();

        StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");
        while(tokenizedItemset.hasMoreTokens()){
          item = tokenizedItemset.nextToken();

          //��� item�� cusSequence�� substring���� ���ԵǾ� �ִ��� Ȯ���Ѵ�...
          if( checkContains(trans, item) == false){
            continue outsideLoop;   //���Ե��� ������ ���� itemset���� �Ѿ��.
          }
        }//end while
        //item�� trans�� ���ԵǾ� ������ �̹� ����Ǿ� �ִ��� Ȯ���Ѵ�.
        if(containedItemset.contains(itemset) == false){
          containedItemset.addElement(itemset);
        }
      }//end outside while
    }

    return containedItemset;
  }

  private Vector containedItemsets2(String cusSequence){
    //table�� itemset�� �� �ش� cusSequence�� subset(Ct)�� ���� ã�� Vector�� �����Ѵ�.
    //large k-sequence(k>=2)�� ã������ ȣ��Ǵ� �Լ�
    String trans = new String();
    String item = new String();
    String itemset = new String();

    boolean itemCheck = false;    //item�� check�߳�?
    boolean itemExist = false;    //item�� trans�� �����ϳ�?

    Vector containedItemset = new Vector();

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      //�� itemset�� test�ϱ� ���� ��ó�� transaction���� �̵��Ѵ�.
      //itemsets���� ';'�� ����.
      StringTokenizer tokenizedCusSequence = new StringTokenizer(cusSequence, ";");

      itemset = (String)itemsets.nextElement();
      StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");

      outsideLoop:   //---------------------outsideLoop:
      while(tokenizedItemset.hasMoreTokens()){
        item = tokenizedItemset.nextToken();
        itemCheck = false;

        while(tokenizedCusSequence.hasMoreTokens()){
          trans = tokenizedCusSequence.nextToken();
          //item�� cusSequence�� ���ԵǾ� ���� ������,
          //next trans�� ���� item�� ���ԵǾ� �ִ��� Ȯ���Ѵ�..
          if( checkContains(trans, item) == false){
            itemExist = false;
            itemCheck = true;
          }
          //item�� cusSequence�� ���ԵǾ� ������,
          //next item�� next trans�� ���ԵǾ� �ִ��� Ȯ���Ѵ�..
          else{
            itemExist = true;
            itemCheck = true;
            continue outsideLoop;
          }
        }//end while(trans)
      }//end while(item)

      //������ item���� ���ԵǾ� ������ �����Ѵ�.
      if(itemExist && itemCheck){
        containedItemset.addElement(itemset);
        itemExist = false;      //�ʱ�ȭ
      }
    }//end while(itemset)

    return containedItemset;
  }


  public int aprioriGen(LSitemset Candiset){
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
      Vector candiset = join();
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

  public int aprioriGen2(LSitemset Candiset){
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

          //�����ٲ� candidate sequence�� �����Ѵ�.
          temp = qitemset + "," + pitemset;
          Candiset.table.put(temp, new Integer(0));
        }
      }//end for
    }else{     // num_items�� 2�̻��� ��
      Vector candiset = join();
      candiset = prune(candiset);

      //Litemset�� candidate itemset�� �����Ѵ�.
      int num_candisets = candiset.size();
      for(int i=0; i < num_candisets; i++){
        temp = (String)candiset.elementAt(i);
        Candiset.table.put(temp, new Integer(0));
      }
      //candiset.removeAllElements();
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
    int i;        //k-1�������� items�� ������ itemset���� ����� üũ�� ����
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

      subsets.addElement(temp);   //�ϳ��� subset�� �߰��Ѵ�.
      items_tmp = (Vector)items.clone();
    }
    return subsets;
  }

  public int getLargeItemset(){
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


  public static int getNumItem(String itemset){
    //��� item���� �̷�������� �� ���� �����Ѵ�.
    StringTokenizer tokenized_line = new StringTokenizer(itemset, ",");
    int num = tokenized_line.countTokens();

    return num;
  }


  private void writeItemset_imsi(String fname) throws IOException{
    //���Ͽ� mapping_number�� itemset�� support�� �����Ѵ�.
    //getL1Sequence()�� pass��ŭ ȣ��Ǿ� L1set.txt C1set.txt ������ �����Ҷ� ���δ�.
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");
    long fp;

    fp = litemFile.length();
    litemFile.seek(fp);       //file�� ������ �̵�

    String temp = new String();    //line�� ������ �ӽ� ����
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();

      temp = sup_tmp + ";(" + itemset + ")" +  "\r\n";
	  litemFile.writeBytes(temp);
    }

    litemFile.close();
  }


  public void writeItemset(String fname) throws IOException{
    //���Ͽ� mapping_number�� itemset�� support�� �����Ѵ�.
    //getL1Sequence()�� pass��ŭ ȣ��Ǿ� LS1set.txt�� �����Ҷ��� ���δ�.
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");
    long fp;

    fp = litemFile.length();
    litemFile.seek(fp);       //file�� ������ �̵�

    String temp = new String();    //line�� ������ �ӽ� ����
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();

      //������ Large 1-sequence�� Vector L1sequence�� �����Ѵ�.
      temp = num_L1Sequence + ";" + sup_tmp + ";(" + itemset + ")" +  "\r\n";
      num_L1Sequence++;     //mapping number�� ������Ų��.
      litemFile.writeBytes(temp);
    }
    
    litemFile.close();
  }


  public void writeItemset2(String fname) throws IOException{
    //���Ͽ� itemset�� support�� �����Ѵ�.
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");
    long fp;

    fp = litemFile.length();
    litemFile.seek(fp);       //file�� ������ �̵�

    String temp = new String();    //line�� ������ �ӽ� ����
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    Enumeration itemsets = table.keys();
    while(itemsets.hasMoreElements()){
      itemset = ((String)itemsets.nextElement());
      sup = (Integer)table.get(itemset);
      sup_tmp = sup.intValue();

      temp = itemset + ";" + sup_tmp + "\r\n";
	    litemFile.writeBytes(temp);
    }
   
    litemFile.close();
  }

}