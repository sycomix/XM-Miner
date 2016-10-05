package xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.xmutil;
import java.io.*;
import java.lang.*;
import java.util.StringTokenizer ;
import java.util.Vector;
import java.util.Enumeration;


public class CXMMetaDataManager{
/************************* CLASS START*************************/

        String                 _Path;                          //�����ϰų� �Է��� ȭ�ϸ��� ������ path�� �����Ѵ�.
        String                 _Comma = ",";             //���Ͽ� ����� ������
        int                      _Index ;                      //�����ڷ� ǥ�ŵ� value������ Index�� ���Ѵ�.
        static Vector     _Vec ;      
        String[]              _Values;                   

      public CXMMetaDataManager(String fileName){
               _Path = fileName;
               File f = new File(_Path);
               try{
                    if (f.exists() == false) f.createNewFile();
               }catch(IOException e){
                    System.out.println(e.getMessage());
               }
       } 


/******************************************************************
* ȯ�����Ͽ� ���� �ִ� �޽��� input�δ� key���� value�� �־� �ش�. 
* �����ϸ� "success"�� output�� �����ش�. 
******************************************************************/
        public void setProfile(String key, String value) //throws IOException
        {
                String dataKey = "["+key+"]"+value+"^";  //���� ��Ÿ���� [key]value�������� ��
                String text =  isIniFileWrite(key, dataKey, 0);

                String check= fileWriterCreate(text);
        }
        
        public void setProfile(String key, String[] values) //throws IOException
        {
                int  i  = values.length;
                String value = "";

                for(int arrRow = 0 ; arrRow < i ; arrRow++)
                {
                        if(arrRow == i - 1)  value = value  + values[arrRow];
                        else value = value  + values[arrRow] + _Comma;
                }

                String dataKey = "["+key+"]"+value+"^";  //���� ��Ÿ���� [key]value�������� ��
                String text =  isIniFileWrite(key, dataKey, 0);
                String check= fileWriterCreate(text);
        }

/******************************************************************
* ȯ�����Ͽ� �ִ� key���� �Ѱ��ش�. input���δ� key���� �Ѱ��ش�.
* �����ϸ� key���� �´� value���� �����ϸ� 
******************************************************************/
        public String getProfile(String key) //throws IOException//, MatchKeyNotFoundException
        {
                String text =  isIniFileWrite(key, "", 1);
                return text;
        }

        public String getProfile(String key, int index) //throws IOException//, MatchKeyNotFoundException
        {
                _Index = index;
                String text =  isIniFileWrite(key, "", 1);
                text = returnIndexOfString(text);
                return text;
        }

       public String[] getProfiles(String key) //throws IOException//, MatchKeyNotFoundException
       {
                try{                
                        String text =  isIniFileWrite(key, "", 1);
                        _Values = valueToStringArray(text);
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                }
                return _Values;
        }
        
/******************************************************************
* ȯ�������� �� ���ξ� �о üũ�Ѵ�.
* 1. setProfileString�϶��� ��ü�� üũ�ϰ� �ߺ���  ���� ������ append��Ų��.
* 2. getProfilestring�϶��� key���� ��ġ�� �ϴ� ���� �ִ� �� ������üũ�ϰ� ������ output���� 
*   �׿� �ش��ϴ� value���� �����Ѵ�. 
******************************************************************/
        private String isIniFileWrite(String key, String dataLine, int chk) //throws IOException//, MatchKeyNotFoundException
        {
               try
               {
                       StringWriter sw = new StringWriter();
                       PrintWriter pw = new PrintWriter(sw);

                       FileReader fr = new FileReader(_Path);
                       BufferedReader br = new BufferedReader(fr);

                       String line, val;

                       int j = 0;
                       
                       while((line = br.readLine()) != null) {
                               val = checkEqualString(key, line);
                               if(val.compareTo("No") != 0 ) {  
                                       if(chk == 1) {
                                               line = val;
                                               break;
                                       }
                                       line = dataLine;
                                       j = 1;
                               }
                               try {
                                       pw.println(line);
                               }catch(Exception e) {
                                          System.out.println(e);
                               }
                       }//end while

                       if(j == 0 && chk != 1) {
                               try {
                                          pw.println(dataLine);        
                               }catch(Exception e) {
                                          System.out.println(e);
                               }
                       }
                       if(chk != 1) line = sw.toString();
                       return line;
                }//end try
                catch(IOException ioe) {
                        System.out.println(ioe.getMessage());
                        return "Error";
                }
        }

/******************************************************************
*key���� ���� ���� �ִ� ���  value���� ��ȯ�Ѵ�. 
******************************************************************/
      private String  checkEqualString(String arg, String arg1) 
       {
                 try{
                         StringTokenizer st = new StringTokenizer(arg1, "]^");
                         String key = st.nextToken();
                         
                         key = key.substring(1);
                         String val = st.nextToken();

                         if(key.compareTo(arg) == 0) return val;
                         else return "No";
                 } catch(Exception e){
//                         System.out.print("checkEqualString : ");
                         return "Error";
                 }
       }

/******************************************************************
*
******************************************************************/
      private String  returnIndexOfString(String arg) //throws ColumnNotFitException
      {
                 try{
                         StringTokenizer st = new StringTokenizer(arg, _Comma);
                         String key = "";
                         for(int i = 0; i < _Index ; i++){
                                 key = st.nextToken();
                         }
                         return key;
                 } catch(Exception e){
//                         System.out.print("returnIndexOfString : ");
                         System.out.println(e.getMessage());
                         return "Error";
                 }
       }

/******************************************************************
*
******************************************************************/
      private String[]  valueToStringArray(String arg)  //throws IOException, Exception //, MatchKeyNotFoundException
      {        
              _Vec = new Vector();
                 try{
                         StringTokenizer st = new StringTokenizer(arg, _Comma);
                         String key = "";
                         
                         while((key = st.nextToken()) != null ){
                                   _Vec.addElement(key);
                         }              

                 } catch(Exception e){
                 }

                 try{
                         vectorToStringArray();
                 } catch(Exception e){
//                         System.out.print("valueToStringArray2 : ");
                         System.out.println(e.getMessage());
                 }                 
         
                 return _Values;
       }
/******************************************************************
*
******************************************************************/
      private void vectorToStringArray() //throws IOException, Exception
      {        
                 int rowCount = _Vec.size();
                 _Values  = new String[rowCount];

                 try{
                         Enumeration vEum = _Vec.elements();

                         String val = "";
                         int i = 0;
                         while(vEum.hasMoreElements())
                         {
                                 _Values[i] = (String)vEum.nextElement();
                                 i++;
                         }

                         _Vec.removeAllElements();             

                 } catch(Exception e){
//                         System.out.print("vectorToStringArray : ");
                         System.out.println(e.getMessage());
                 }
       }

/******************************************************************
*
******************************************************************/
       String  fileWriterCreate(String arg) //throws IOException
        {

                try{
                       FileWriter _FW = new FileWriter(_Path);
                       _FW.write(arg);
                       _FW.close();
                       return "Success";
                } catch(IOException Em_e) {
//                         System.out.print("fileWriterCreate : ");
                         System.out.println(Em_e.getMessage());
                         return "ERROR";
                }
       }
       
/************************* THE END*************************/
}