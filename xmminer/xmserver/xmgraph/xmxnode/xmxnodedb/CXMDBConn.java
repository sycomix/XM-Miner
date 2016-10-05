package xmminer.xmserver.xmgraph.xmxnode.xmxnodedb;

import java.sql.*;

public class CXMDBConn {

  CXMSQLManager _SQLM ;

  Connection	_con = null ;
  String _Driver;
  String _DBName;

  boolean bb = false;

  public CXMDBConn() {}

  //*--------------------------[dbConnect]---------------------------------
  //DB�� �����ϴ� Method
  //���ϰ����� �����ϸ� true, �����ϸ� false�� ���� �Ѵ�.
  public boolean dbConnect(String driver, String url, String loginID, String password) throws JavaException{
    try{
      _Driver = driver;
      Class.forName (driver);
      java.util.Properties props = new java.util.Properties();
	  props.put("user", loginID);      // �ʿ��ϸ� �����Ѵ�.
	  props.put("password", password);  // �ʿ��ϸ� �����Ѵ�.
	  System.out.println(_Driver + ", " + url + ", " + loginID + ", " + password);
	  _con = DriverManager.getConnection(url, props);
      _DBName = "use "+_con.getCatalog();
    }catch(SQLException e){
		e.printStackTrace();
      throw new JavaException(e.getMessage());
    }catch(ClassNotFoundException e){
		e.printStackTrace();
      throw new JavaException(e.getMessage());
    }
    DBMSDriverName(driver);
    bb = true;
	return true;
  }

  //*--------------------------[DBMSDriverName]---------------------------------*//
  //������ DBMS�� ���� SQL Ŭ������ ������Ų��.
  void DBMSDriverName(String driver){
    if(driver.compareTo("oracle.jdbc.driver.OracleDriver") == 0){
      _SQLM = new CXMORASQL();
      _DBName = "";
    }else{
      _SQLM = new CXMMSSQL();
    }
  }

  //*--------------------------[dbDisConnect]---------------------------------*//
  //DB���� ������ ���� Method
  //���ϰ����� �����ϸ� true, �����ϸ� false�� ���� �Ѵ�.
  public boolean dbDisConnect() throws JavaException{
    try {
	  if (_con != null) _con.close();
      bb = false;
	}catch(SQLException  e4){
      throw new JavaException(e4.getMessage());
	}
	return true;
  }

  public Connection getConn(){
    return _con;
  }

  public boolean ConCheck(){
    return bb;
  }

  public String getDBName(){
    return _DBName+" ";
  }

  public String getDriverName(){
    return _Driver;
  }

  public CXMSQLManager getSQLManager(){
    return _SQLM;
  }

}