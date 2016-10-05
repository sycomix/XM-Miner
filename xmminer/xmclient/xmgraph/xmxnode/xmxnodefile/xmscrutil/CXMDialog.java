package xmminer.xmclient.xmgraph.xmxnode.xmxnodefile.xmscrutil;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class CXMDialog //extends JPanel
{
	
	public static String selectLocation(JFrame frame,String[] loc, String initPos)
	{
    ImageIcon icon = null;//new ImageIcon("orange.gif");
		return (String)JOptionPane.showInputDialog(frame,
					"���ϴ� �������� ��ġ�� �����ϼ���.",
					"File Extraction",
					JOptionPane.PLAIN_MESSAGE,
					icon,
					loc,
					initPos);
	}
  public static String getInputStringDialog(JFrame frame, String msg)
  {
    return JOptionPane.showInputDialog(frame,
            "Error: Get Input String",
            "File Extraction",
            JOptionPane.QUESTION_MESSAGE);
  }

	public static String selectType(JFrame frame,String[] type, String initPos)
	{
    ImageIcon icon = null;//new ImageIcon("orange.gif");
		return (String)JOptionPane.showInputDialog(frame,
					"���ϴ� �������� Ÿ���� �����ϼ���.",
					"File Extraction",
					JOptionPane.PLAIN_MESSAGE,
					icon,
					type,
					initPos);
	}

	public static void notReadyMessage(JPanel panel)
	{
		JOptionPane.showMessageDialog(panel,
					"���� ȭ���� �غ� ���� �ʾҽ��ϴ�.",
					"File Extraction",
					JOptionPane.INFORMATION_MESSAGE);
	}

	public static void msgDialog(JPanel panel, String msg)
	{
		JOptionPane.showMessageDialog(panel,
//					"ȭ�� �����Դϴ�.",
					"���۵Ǿ����ϴ�.",
					"File Extraction",
					JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errorDialog(JPanel panel, String msg)
	{
		JOptionPane.showMessageDialog(panel,
					"ȭ�� �����Դϴ�.",
					"File Extraction",
					JOptionPane.ERROR_MESSAGE);
	}

	public static void errorDialog(JFrame frame, String msg)
	{
		JOptionPane.showMessageDialog(frame,
					"ȭ�� �����Դϴ�.",
					"File Extraction",
					JOptionPane.ERROR_MESSAGE);
	}

  public static int confirmExtractionDialog(JFrame frame, String datafile, String headerfile)
  {
    Object[] options = {"Yes", "No"};
    return JOptionPane.showOptionDialog(frame,
          "New datafile name : " + datafile + "\n" + "New headerfile name : " + headerfile + "\n"
          +"���� �̸����� ���ο� �����Ϳ� ����� �����Ͻðڽ��ϱ�?",
          "File Extraction",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options,
          options[0]);
  }

  public static int questionDialog(JPanel panel, String msg)
  {
    Object[] options = {"Yes", "No"};
    return JOptionPane.showOptionDialog(panel,
          msg,
          "File Extraction",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options,
          options[0]);
  }

  public static int confirmExtractionDialog(JPanel panel, String datafile)
  {
    Object[] options = {"Yes", "No"};
    return JOptionPane.showOptionDialog(panel,
          "New datafile name : " + datafile + "\n" 
          +"���� �̸����� ���ο� �����Ϳ� ����� �����Ͻðڽ��ϱ�?",
          "File Extraction",
          JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          options,
          options[0]);
  }

  public static String getInputStringDialog(JPanel panel, String msg)
  {
    return JOptionPane.showInputDialog(panel,
            "Error: Get Input String",
            "File Extraction",
            JOptionPane.QUESTION_MESSAGE);
  }

}
