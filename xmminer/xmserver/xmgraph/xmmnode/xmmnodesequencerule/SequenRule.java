package xmminer.xmserver.xmgraph.xmmnode.xmmnodesequencerule;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class SequenRule {
  static String projectName;
  static String modelName;
  static String dataFile;
  static String projectPath;

  static float min_conf;
  static int num_trans;

  String LSkset;
  int Lkset_sup;
  int k;

  static Hashtable largeHash;
  Hashtable premises;     //rule�� ���� �κа� confidence�� �����ϴ� ����

  public SequenRule(String litemset, int num_items, int sup){
    LSkset = litemset;
    k = num_items;        //Lkset�� ��� item��� �̷���� �ֳ�..
    Lkset_sup = sup;      //support�� ����

    premises = new Hashtable();
  }


  public static void setParam(Hashtable litemHash, float confidence, String model, int transNumber, String project, String data, String path){
    largeHash = litemHash; //��� litemset�� support�� ������ �ִ�.

    int size = largeHash.size();

    projectName = project;
    projectPath = path;
    dataFile = data;

    min_conf = confidence;
    num_trans = transNumber;

    modelName = model;
  }

  public static Hashtable read_Litemset(String fname) throws IOException{
    //Litemset ���Ͽ��� Lkset�� �ϳ��� �о� ó���Ѵ�.
    //������ �����ϴ��� üũ�Ѵ�.  �Ǵ� ������ �ִ���..
    FileInputStream litemset_file = new FileInputStream(fname);
    BufferedReader litemset_data = new BufferedReader(new InputStreamReader(litemset_file));

    String line =  new String();
    String litemset = new String();
    int sup;
    Hashtable litem_table = new Hashtable();

    //line���� �о� Hashtable�� �����Ѵ�.
    while((line = litemset_data.readLine()) != null){
      StringTokenizer tokenized_line = new StringTokenizer(line, ";");

      litemset = tokenized_line.nextToken();
      sup = Integer.parseInt(tokenized_line.nextToken());
      litem_table.put(litemset, new Integer(sup));
    }

    litemset_file.close();
    return litem_table;
  }

  public void gen_rule(int num_items, CXMTableSaver ruleData) throws IOException{
    //Lkset�� ������ ���� ������ rules�� premises���� ���Ѵ�.
    String item = new String();
    StringTokenizer tokenized_LSkset = new StringTokenizer(LSkset, ",");

    for(int i = 0; i < num_items-1; i++){
      if( i == 0 ){   //LSkset="a,b,c"�� ��  i=0 => "a",  i=1 => "a,b"
        item = tokenized_LSkset.nextToken();    //item : premise
      }else item += ',' + tokenized_LSkset.nextToken();

      //rule�� �����Ǹ� premises�� �����Ѵ�.
      check_rule(item, i+1);
    }

    if(premises.size() > 0 ){
      write_rules(ruleData);   //premises�� ����ִ� rule�� ã�� ���Ͽ� �����Ѵ�.
      premises.clear();
    }
  }

  private boolean check_rule(String premise, int num_items) throws IOException{
    //prmises�� ������ �� �ִ� rule�� min_conf�� �Ǵ��� Ȯ���Ѵ�.
    //�����Ǵ� rule�� confidence�� �Բ� premises�� �����Ѵ�.
    boolean check = false;

	//premise�� support�� �о�´�.
    Integer supInt = (Integer)largeHash.get(premise);
    int sup = supInt.intValue();

    float conf = Lkset_sup/(float)sup ; // /  (premises�� support)
    if(conf >= min_conf){
      //Hashtable�� confidence�� �Բ� �����Ѵ�.    float -> Float
      premises.put(premise, new Float(conf));
      check = true;
    }

    return check;
  }

  public String substract_itemset(String itemset){
    //LSkset���� ���ڷ� �Ѿ�� itemset�� �� ������ itemset�� �����Ѵ�.
    //LSkset-itemset;
    String remain = new String();
    String item  = new String();

    Vector imsi = new Vector();
    StringTokenizer tokenized_itemset  = new StringTokenizer(itemset,",");
    while(tokenized_itemset.hasMoreTokens()){
      item = tokenized_itemset.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenized_LSkset  = new StringTokenizer(LSkset,",");

    while(tokenized_LSkset.hasMoreTokens()){
      item = tokenized_LSkset.nextToken();
      if(imsi.contains(item) == false){
        remain += item + ",";
      }
    }//end while

	if(remain.length() > 0) remain = remain.substring(0, remain.length()-1);
    
	return remain;
  }

  private void write_rules(CXMTableSaver ruleData) throws IOException{
    //"premise => consequent; sup(), conf" �������� rule�� ���Ͽ� �����Ѵ�.
    //������ �����ϸ� ������ ��� �����Ѵ�.

    String temp = new String();
    String premise = new String();
    String antecedent = new String();
    String consequent = new String();
    Integer sup;

    //���������߰�
    int[] colIndex = new int[5];  //5���� column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup�� string Ÿ������ ��ȯ
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){
      premise = (String)itemsets.nextElement();     //mpping�� premise

      SeqPattern patten = new SeqPattern();
      antecedent = patten.reTransform(projectName, premise, modelName); //reTransform�� premise
      consequent = patten.reTransform(projectName, substract_itemset(premise), modelName);

      temp = antecedent + "=>" + consequent + ", ";

      //support�� confidence�� 0.000�������� ����ϱ� ����...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;   //support
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)
      temp += Lkset_sup + "(" + str_sup2 + "), ";

      f = ((Float)premises.get(premise)).floatValue();  // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)
      temp += str_conf;        //confidence

      row = antecedent.getBytes();   //premise
      colIndex[0] = row.length;
      column = consequent.getBytes();   //consequent
      row = addColAtRow(column, row, colIndex, 1);
      column = str_sup1.getBytes();     //support(����)
      row = addColAtRow(column, row, colIndex, 2);
      column = str_sup2.getBytes();     //support(%)
      row = addColAtRow(column, row, colIndex, 3);
      column = str_conf.getBytes();    //confidence(%)
      row = addColAtRow(column, row, colIndex, 4);

      System.out.println( "row = " + new String(row));
      ruleData.createRowData(row, colIndex);  //�ϳ��� rule�� �����Ѵ�.
      XMBMNodeSequenceRule.num_rows++;
    }//end while 
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

  private void write_rules_file() throws IOException{
    //"premise => consequent; sup(), conf" �������� rule�� ���Ͽ� �����Ѵ�.
    //������ �����ϸ� ������ ��� �����Ѵ�.

    //RandomAccessFile�� �̿��ؼ� �����δ�.
    RandomAccessFile rule_data = new RandomAccessFile(projectPath + projectName + "\\" + modelName + "_rules.txt", "rw");
    long fp ;

    String temp = new String();
    String premise = new String();
    String antecedent = new String();
    String consequent = new String();
    Integer sup;

    String rule_line = new String();
    int bExistRule  = 0;         //rule�� �̹� ����Ǿ� �ֳ�..

    //���������߰�
    int[] colIndex = new int[5];  //5���� column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup�� string Ÿ������ ��ȯ
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){
      premise = (String)itemsets.nextElement();     //mpping�� premise
      SeqPattern patten = new SeqPattern();
      antecedent = patten.reTransform(projectName, premise, modelName); //reTransform�� premise
      consequent = patten.reTransform(projectName, substract_itemset(premise), modelName);

      temp = antecedent + "=>" + consequent + ", ";

      //support�� confidence�� 0.000�������� ����ϱ� ����...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;   //support
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)
      temp += Lkset_sup + "(" + str_sup2 + "), ";

      f = ((Float)premises.get(premise)).floatValue();  // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)
      temp += str_conf;        //confidence

      //rule�� �����ϴ��� �˻����� �ʾƵ� �ȴ�...?????
      //maximal sequence�� �ƴ� rule�� �������� �ʴ´�...
      rule_data.seek(0);        // ������ ó�� ��ġ�� ��..
      while( (rule_line=rule_data.readLine()) != null) {
        if (rule_line.compareTo(temp.toString()) == 0){
          bExistRule = 1;       // rule�� �̹� ����..
          break;
        }
      }

      if(bExistRule == 0){
        temp += "\r\n";  //rule�� ������ �� ����
        rule_data.writeBytes(temp.toString());
      }

	  bExistRule = 0;

 	}//end while  

   rule_data.close();
  }

}