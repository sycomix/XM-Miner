//Copyright:    Copyright (c) 1999
//Author:       �ִ��
//Company:      ���ϴ��б� ������а� ���������ý���
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef;



public class XMCCondition
{
    public XMCTest		CondTest;	//test part of condition
    public int        TestValue;	// specified outcome of test
    
    public    XMCCondition()
    {
    	   CondTest = new   XMCTest();
    }
}