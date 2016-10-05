package xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule;

import java.util.*;          
import java.io.*;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class SeqPattern {
  private int numLitemsets;

  static int min_sup;
  private String dataFile;
  private String projectName;
  private String projectPath;

  static int num_trans;  //customer-transaction ��
  private String modelName;

  private String transIDFld;  //������ �Ǵ� �ʵ��(transaction ID)
  private String targetFld;   //item �ʵ��
  private String timeFld;     //time �ʵ��

  static Vector LS1set;  //large 1-sequence�� mapping������ ������ �ִ� Vector

  public SeqPattern(int num, int support, String model, int transNumber, String project, String data, String path){
    projectName = project;
    projectPath = path;

    min_sup = support;
    numLitemsets = num;

    dataFile = data;
    modelName = model;
    num_trans = transNumber;
  }

  public SeqPattern(){
  }

  private Hashtable getL1Sequence(String transIDFldName, String targetFldName, String timeFldName) throws IOException{
    //custom transaction File���� Large 1-sequences�� ���Ѵ�.
    Hashtable L1sequence = new Hashtable();

    int k = 1;
    int num_litems;            //������ large sequence�� ����
    LSitemset litemsetTmp = new LSitemset(k);

    //Apriori
    do{
        LSitemset litemset = new LSitemset(k, min_sup, modelName, num_trans, projectName, dataFile, projectPath);  //num_items, min_sup
        litemset.setParam(transIDFldName, targetFldName, timeFldName); //k�� �����Ҷ����� Litemset�� �ٽ� ��������Ƿ�..

        if(k == 1){
          litemset.itemsDataSaver();
          XMBMNodeSequenceRule.m_pbValue = 42;
          litemset.readAllItem();    //Candidate 1-Itemset�� ���Ѵ�.
          XMBMNodeSequenceRule.m_pbValue = 44;
        }else{
          num_litems = litemsetTmp.aprioriGen(litemset);  //Candidate itemset�� �����Ѵ�.
          if(num_litems == 0) break;
        }

        num_litems = litemset.genLitemset(L1sequence);  //Hashtable

        //�� pass���� ������ Large 1-sequence�� Vector L1sequence�� �����Ѵ�.
        //=> genLitemset()�ȿ��� writeItemset()�� ����..

        litemsetTmp = litemset;
        k++;
    }while(num_litems != 0);

    XMBMNodeSequenceRule.m_pbValue = 46;

    return L1sequence;
  }

  public Hashtable genL1Sequence(String transIDFldName, String targetFldName, String timeFldName) throws IOException{
    transIDFld = transIDFldName;
    targetFld = targetFldName;
    timeFld = timeFldName;

    Hashtable L1sequence = getL1Sequence(transIDFld, targetFld, timeFld);

    LS1set = new Vector();
    Hashtable map_L1sequence = new Hashtable();
    String itemset = new String();
    Integer sup;

    //large 1-sequence�� ���� mapping������ Vector LS1set�� �ִ´�.
    int i = 0;
    Enumeration keySet = L1sequence.keys();
    while(keySet.hasMoreElements()){
      itemset = (String)keySet.nextElement();
      sup = (Integer)L1sequence.get(itemset);

      //mapping�� ���� itemset�� ��ġ�Ͽ� map_L1sequence Hashtable�� �ִ´�.
      Integer ii = new Integer(i);
      map_L1sequence.put(ii.toString(), sup);

      LS1set.add(i, itemset);
      i++;
    }
    //L1sequence => (Gin; 2) (Beer, Cider; 4) (Water; 2) (Cider; 5) ... => getL1Sequence()�� ���
    //LS1set => 0(Gin) 1(Cider) 2(Water) 3(Beer, Cider) ... => transform(), reTransform()�� �� �ʿ�
    //map_L1sequence => (0; 2) (3; 4) (2; 2) (1; 5) ... => rule�� ������ ��(confidence���) �ʿ�

    transform();  //������� Vector LS1set�� �̿��Ͽ� nctrans.txt�����Ѵ�. //46-52

    return map_L1sequence;
  }


  private void writeItemset(Hashtable map_L1sequence) throws IOException{
    //���Ͽ� mapping_number�� itemset�� support�� �����Ѵ�.
    //getL1Sequence()�� pass��ŭ ȣ��Ǿ� LS1set.txt�� �����Ҷ��� ���δ�.
    String fname = projectPath + projectName + "\\" + modelName + "_LS1set.txt";
    RandomAccessFile litemFile = new RandomAccessFile(fname, "rw");

    String temp = new String();    //line�� ������ �ӽ� ����
    String itemset = new String();
    Integer sup;
    int sup_tmp;

    //LS1set => 0(Gin) 1(Cider) 2(Water) 3(Beer, Cider) ...
    //map_L1sequence => (0; 2) (3; 4) (2; 2) (1; 5) ...
    int size = LS1set.size();
    for(int i = 0; i < size; i++){
      Integer ii = new Integer(i);
      sup = (Integer)map_L1sequence.get(ii.toString());
      sup_tmp = sup.intValue();
      itemset = (String)LS1set.elementAt(i);

      temp = i + ";" + sup_tmp + ";(" + itemset + ")" +  "\r\n";
      litemFile.writeBytes(temp);
    }

    litemFile.close();
  }

  public String reTransform(String project, String itemset, String model) throws IOException {
    //itemset�� ���� item������ �纯ȯ�Ͽ� �Ѱ��ش�.
    String result = new String();     //result : transformed_itemset
    String temp = new String();
    Integer i;

    StringTokenizer tokenizedItemset = new StringTokenizer(itemset, ",");
    while(tokenizedItemset.hasMoreTokens()){
      String item = tokenizedItemset.nextToken();

      i = new Integer(item);
      temp = (String)LS1set.elementAt(i.intValue());

      if(result.length() > 0){
        result += ';' + temp;
      }
      else{
        result = temp;
      }
    }//end while

    return result;
  }


  private void transform() throws IOException {
    //custom transaction File�� L1sequence���� transform&mapping �Ѵ�.
    //nctrans.txt ���� ����

    String newDBFile = projectPath + projectName + "\\" + modelName + "_nctrans.txt";
    RandomAccessFile newDb_File = new RandomAccessFile(newDBFile, "rw");

    String itemset, trans, transLine;
    String lineTmp = new String();

    int m = 1;

    CXMTableReader transData2 = new CXMTableReader();  //transaction data file
    transData2.setFileStatus(projectName, modelName + "_seq_trans");

    for(int t = 0; t < num_trans; t++){  //transaction ��
      //cxmtable���� �ϳ��� customer-transaction�� �о� transaction Vector�� �����Ѵ�.
      transLine = transData2.getString(t + 1);  //ex)"Juice;Cider,Water;Beer,Coke"
      StringTokenizer tokened_trans = new StringTokenizer(transLine, ";");
      while(tokened_trans.hasMoreTokens()){
        trans = tokened_trans.nextToken();  //customer-transaction���� �ϳ��� transaction �и�

		Enumeration litemsets = LS1set.elements();
        while(litemsets.hasMoreElements()){
          itemset = (String)litemsets.nextElement();  //large 1-sequence�� �ϳ��� ������.

          //trans�� itemset�� ���ԵǾ� ������...
          if(checkContains(trans, itemset) == true){
            lineTmp += LS1set.indexOf((String)itemset) + ",";   //mapping�� ���� �ִ´�.
          }
        }//end while    --transaction �ϳ� ����...
        if(lineTmp.length() != 0){
          lineTmp = lineTmp.substring(0, lineTmp.length()-1) + ";";
        }
      }//end while    --customer sequence �ϳ� ����...

	  if(lineTmp.length() != 0){
        lineTmp = lineTmp.substring(0, lineTmp.length()-1) + "\r\n";
        newDb_File.writeBytes(lineTmp);
        lineTmp = "";
      }

      if((t == 500*m) && (t <= 2500)){ //46-51
        XMBMNodeSequenceRule.m_pbValue += 1;
        m++;
      }//end if

    }//end for

	XMBMNodeSequenceRule.m_pbValue = 52;

    newDb_File.close();
    transData2.close();
  }


  public boolean checkContains(String trans, String itemset){
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
        check = false;
        break;
      }
    }//end while

    return check;
  }


  public boolean checkOrderContains(String maxSequen, String sequen){
    //maxSequen�� sequen�� ���ԵǸ� true�� �����Ѵ�.
    //sequen�� �� item���� ������ ����Ͽ� ���ԵǾ� �ִ°��� Ȯ���Ѵ�.
    String item = new String();
    boolean check = true;
    int index = -1;
    int index2;

    Vector imsi = new Vector();
    StringTokenizer tokenized_maxSequen  = new StringTokenizer(maxSequen, ",");
    while(tokenized_maxSequen.hasMoreTokens()){
	    item = tokenized_maxSequen.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenizedSequen = new StringTokenizer(sequen, ",");
    while(tokenizedSequen.hasMoreTokens()){
      item = tokenizedSequen.nextToken();

      //��� item�� maxSequen�� substring���� ���ԵǾ� �ִ��� Ȯ���Ѵ�...
      if(imsi.contains(item) == false){
        check = false;
        break;
      }
      else{ //���ԵǾ� ������...
        index2 = imsi.indexOf(item);
        if(index2 > index){
          index = index2;
        }else{  //������ ���� ������ break
          check = false;
          break;
        }
      }//end else
    }//end while

    return check;
  }

  public int getNumItems(String litemset){
    //litemset�� ��� item���� �̷�������� ������ �����Ѵ�.
    StringTokenizer tokend_litem = new StringTokenizer(litemset, ",");
    int num = tokend_litem.countTokens();

    return num;
  }


  public Vector maximalSequence(int k, Hashtable largeHash) throws IOException{
    //Large k-sequence������ ��� sequence���� maximal�� �͸� ã�� ���Ͽ� �����Ѵ�.

    //����� ����..
    String ofname = projectPath + projectName + "\\" + modelName + "_maxLS.txt";
    RandomAccessFile litemFile = new RandomAccessFile(ofname, "rw");

    String sequen = new String();
    String line = new String();
    String maxSequen = new String();

    Vector maxSequence = new Vector();
    boolean maximal = false;
    int num;

    //large-sequence �ϳ��� ���� ���� item���� ���� largeHashClone�� �ִ´�.
    Hashtable largeHashClone = (Hashtable)largeHash.clone();
    Enumeration keySet = largeHashClone.keys();
    while(keySet.hasMoreElements()){
      sequen = (String)keySet.nextElement();
      largeHashClone.remove(sequen);

      num = getNumItems(sequen); //��� item���� �̷�����ֳ�
      largeHashClone.put(sequen, new Integer(num));
    }//end while

    int m = 1;

    for(int i = k; i > 1; i--){
      keySet = largeHashClone.keys();

      while(keySet.hasMoreElements()){
        sequen = (String)keySet.nextElement();
        Integer numInt = (Integer)largeHashClone.get(sequen);
        num = numInt.intValue();  //���� item��

        if(num == i){
          if(num == k) maximal = true;
          else{
            //sequen�� maximal���� Ȯ���Ѵ�.
            Enumeration maxSequens = maxSequence.elements();
            while(maxSequens.hasMoreElements()){
              maxSequen = (String)maxSequens.nextElement();  //maxSequen

              //sequen�� maximal sequence�� �ƴϸ�,
              if(checkOrderContains(maxSequen, sequen) == true){
                maximal = false;
                break;
              }
            }//end while
          }//END ELSE
          largeHashClone.remove(sequen);
        }//end if

        if(maximal == true){
          maxSequence.addElement((String)sequen);

          //maximal sequence�� Ȯ�ε� sequen�� support�� �����ͼ� �����Ѵ�.
          Integer support = (Integer)largeHash.get(sequen);
          line = sequen + ";" + support + "\r\n";
          litemFile.writeBytes(line);
        }

		maximal = false;    //�ʱ�ȭ

      }//end while   ���� sequence�� �Ѿ��.


      if(m <= 5){  //68-73
	    XMBMNodeSequenceRule.m_pbValue += 1;
	  }else{   // KeepRunning �� false
	    System.out.println("����ڿ� ���� Association Rule ���� �۾� ����");
	    //return;
	  }
      m++;

    }//end for

	XMBMNodeSequenceRule.m_pbValue = 74;

    litemFile.close();

    return maxSequence;
  }

}