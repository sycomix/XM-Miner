//Copyright:    Copyright (c) 1999
//Author:       �ִ��
//Company:      ���ϴ��б� ������а� ���������ý���
//Description:  Your description
package xmminer.xmserver.xmgraph.xmmnode.xmmnodec45.rulegen.typedef;

public class XMCPR
{
    public int	          Size;		// number of conditions //
    public XMCCondition	  Lhs[];		// conditions themselves
    public int	          Rhs;		// class given by rule
    public double	        Error,		// estimated error rate
	          	            Bits;		// bits to encode rule
    public int	          Used,		// times rule used
                          Incorrect;      // times rule incorrect
    public    XMCPR()
    {
    	
    }			
 
}