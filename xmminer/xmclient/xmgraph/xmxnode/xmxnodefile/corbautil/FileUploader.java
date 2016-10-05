package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.corbautil;

import java.io.*;
import javax.swing.JPanel;

import xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil.*;
import xmminer.xmserver.xmgraph.xmxnode.xmxnodefile.*;

public class FileUploader {

  XMBXNodeFile bxnodefile = null;
  	
  public FileUploader(XMBXNodeFile extractor)
  {
  	bxnodefile = extractor;
  }
  public String questionDialog(String oldfilename)
  {
  	return CXMDialog.getInputStringDialog(new JPanel(), oldfilename + "\n"
          + "�� ������ ������ Upload �Ͻðڽ��ϱ�? \n �׷��ôٸ� ���������� ���� �̸��� �Է����ֽʽÿ�.");
  }
  public boolean upload(String oldfilename, String name)
  {
    try {
      InputStream inStream = new FileInputStream(oldfilename);
      int size = inStream.available();
      byte b[] = new byte[size];
      int res = inStream.read(b);
      inStream.close();
      bxnodefile.fileUpload(name, b);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return true;
  }
}