package xmminer.xmserver.xmgraph.xmmnode.xmmnodeassociationrule;

import java.util.*;
import java.io.*;
import java.lang.*;
import java.text.DecimalFormat;
import xmminer.xmlib.xmtable.*;
import xmminer.xmlib.xmcompute.*;

public class AssoRule{
  static String projectName;
  static String projectPath;
  static String dataFile;

  private String Lkset;
  private int Lkset_sup;
  private int k;

  static float min_conf;
  static String ruleFile;
  static int num_trans;
  static String modelName;

  static Hashtable largeHash;

  Hashtable premises;     //rule�� ���� �κа� confidence�� �����ϴ� ����

  public AssoRule(String litemset, int num, int sup){
    Lkset = litemset;
    k = num;              //Lkset�� ��� item��� �̷���� �ֳ�..
    Lkset_sup = sup;      //support�� ����

    premises = new Hashtable();
  }

  public static void setParam(Hashtable litemHash, float confidence, String model, int transNumber, String project, String data, String path){
    largeHash = litemHash; //��� litemset�� support�� ������ �ִ�.
    
	projectName = project;
    projectPath = path;
    dataFile = data;

    min_conf = confidence;
    num_trans = transNumber;

    modelName = model;
    ruleFile = projectPath + projectName + "\\" + modelName + "_rule.txt";
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
      //int num_itemset = tokenized_line.countTokens();

      litemset = tokenized_line.nextToken();
      sup = Integer.parseInt(tokenized_line.nextToken());
      litem_table.put(litemset, new Integer(sup));
    }

    litemset_file.close();

    return litem_table;
  }

  private Litemset getH1() throws IOException {
    //consequents�� 1 item�� premises_tmp�� ���� rule�� consequent�� �����Ѵ�.
    Vector subsets = Litemset.subset(Lkset);
    Litemset consequents = new Litemset(1);

    int s = subsets.size();
    for(int i=0; i < s; i++){
      String premise = (String)subsets.elementAt(i);
      if(check_rule(premise, k-1)== true){
        //premise�� consequent�� ���� consequents�� �����Ѵ�.
        String consequent = substract_itemset(premise);
        consequents.table.put(consequent, new Integer(0));
      }
    }//end for

    return consequents;
  }

  public void gen_rule(String litemset, int num_items,CXMTableSaver ruleData) throws IOException { //simple��
    //Lkset�� ������ ���� ������ rules�� premises���� ���Ѵ�.
    Vector subsets = Litemset.subset(litemset);
    int s = subsets.size();

    int m = num_items;  //k
    for(int i = 0; i < s; i++){
      String premise = (String)subsets.elementAt(i);
      if(check_rule(premise, m-1) == true){    //rule�� min_conf�� �����ϸ�,
        if( m-1 > 1){
          gen_rule(premise, m-1,ruleData);
        }
      }//end if
    }//end for

    if(premises.size() > 0 ){
      write_rules(ruleData);
      premises.clear();     //premises Hashtable�� ����.
    }
  }

  public void gen_rule2(String litemset, int num_items, CXMTableSaver ruleData) throws IOException { //faster��
    //Lkset�� ������ ���� ������ rules�� premises���� ���Ѵ�.
    Litemset litemH1 = getH1();

    apGenRules(litemH1, 1);    //apriori_gen()�� �ϱ����� Litemset������ ����

    write_rules(ruleData);
  }

  private void apGenRules(Litemset litemHm, int num_items) throws IOException {
    int m = num_items + 1;
    if(k > m){
      Litemset litemHmm = new Litemset(m);
      litemHm.apriori_gen(litemHmm);   //litemHm : Hm, litemHmm : Hm+1

      Enumeration consequents = litemHmm.table.keys();
      while(consequents.hasMoreElements()){
        String consequent = (String)consequents.nextElement();

        //check_rule()���� �����Ǵ� rule�� premise�� premises�� �����Ѵ�.
        if(check_rule(substract_itemset(consequent), k-m) == false){
          litemHmm.table.remove(consequent);
        }
      }//end while
      apGenRules(litemHmm, m);
    }//end if
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

    //Lkfile.close();
    return check;
  }

  private String substract_itemset(String itemset){
    //Lkset���� ���ڷ� �Ѿ�� itemset�� �� ������ itemset�� �����Ѵ�.
    //Lkset-itemset;
    String remain = new String();
    String item = new String();

    Vector imsi = new Vector();
    StringTokenizer tokenized_itemset  = new StringTokenizer(itemset,",");
    while(tokenized_itemset.hasMoreTokens()){
	    item = tokenized_itemset.nextToken();
      imsi.addElement(item);
    }

    StringTokenizer tokenized_Lkset  = new StringTokenizer(Lkset,",");
    while(tokenized_Lkset.hasMoreTokens()){
	    item = tokenized_Lkset.nextToken();

      //itemset�� ��� �����ۿ� ���Ͽ� item�� �������������� remain�� item�� ���Ѵ�.
      if( imsi.contains(item) == false){
        remain += item + ",";
      }
      /*if(itemset.indexOf(item)== -1){
	      remain += item + ",";
      } */
    }//end while
    if ( remain.length() > 0 ){
      remain = remain.substring(0, remain.length()-1);
    }
    return remain;
  }

  private void write_rules(CXMTableSaver ruleData) throws IOException{
    //"premise;consequent;sup,conf" �������� rule�� ���Ͽ� �����Ѵ�.
    //������ �����ϸ� ������ ��� �����Ѵ�.

    //temp�� StringBuffer�� �ٲ㺸��....!
    //sup�� int������ ��ȯ�ʰ� �ٷ� ���Ͽ� ����.(append function)
    String premise = new String();
    String consequent = new String();
    Integer sup;

    int[] colIndex = new int[5];  //5���� column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup�� string Ÿ������ ��ȯ
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

    //cxmtable�� rule ����
    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){
      premise = ((String)itemsets.nextElement());
      consequent = substract_itemset(premise);

      //support�� confidence�� 0.000�������� ����ϱ� ����...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)

      f = ((Float)premises.get(premise)).floatValue();    // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)

      row = premise.getBytes();   //premise
      colIndex[0] = row.length;
      column = consequent.getBytes();   //consequent
      row = addColAtRow(column, row, colIndex, 1);
      column = str_sup1.getBytes();     //support(����)
      row = addColAtRow(column, row, colIndex, 2);
      column = str_sup2.getBytes();     //support(%)
      row = addColAtRow(column, row, colIndex, 3);
      column = str_conf.getBytes();    //confidence(%)
      row = addColAtRow(column, row, colIndex, 4);

	  ruleData.createRowData(row, colIndex);  //�ϳ��� rule�� �����Ѵ�.
      //XMBMNodeAssociationRuleImpl.num_rows++;
      XMBMNodeAssociationRule.num_rows++;
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
    //"premise;consequent;sup,conf" �������� rule�� ���Ͽ� �����Ѵ�.
    //������ �����ϸ� ������ ��� �����Ѵ�.

    //RandomAccessFile�� �̿��ؼ� �����δ�.
    RandomAccessFile rule_data = new RandomAccessFile(ruleFile, "rw");
    long fp ;


    //temp�� StringBuffer�� �ٲ㺸��....!
    //sup�� int������ ��ȯ�ʰ� �ٷ� ���Ͽ� ����.(append function)
    String temp = new String();
    String premise = new String();
    String consequent = new String();
    Integer sup;

    String rule_line = new String();
    int bExistRule  = 0;         //rule�� �̹� ����Ǿ� �ֳ�..

    int[] colIndex = new int[5];  //5���� column
    byte[] row, column;

    String str_sup1, str_sup2, str_conf;
    //Lkset_sup�� string Ÿ������ ��ȯ
    Integer ii = new Integer(Lkset_sup);
    str_sup1 = ii.toString();

 
    Enumeration itemsets = premises.keys();
    while(itemsets.hasMoreElements()){

      premise = ((String)itemsets.nextElement());
      consequent = substract_itemset(premise);
      temp = premise + "=>" + consequent + "; ";

      //support�� confidence�� 0.000�������� ����ϱ� ����...
      DecimalFormat fourDigits = new DecimalFormat("0.000");
      float f = (float)Lkset_sup/num_trans;
      str_sup2 = fourDigits.format(f).toString();   //supprt(%)
      temp += Lkset_sup + "(" + str_sup2 + "), ";

      f = ((Float)premises.get(premise)).floatValue();    // Float -> float
      str_conf = fourDigits.format(f).toString();  //confidence(%)
      temp += str_conf + "\r\n";

      long leng = rule_data.length();
      rule_data.seek(leng);
    }//end while
    rule_data.close();
  }

}