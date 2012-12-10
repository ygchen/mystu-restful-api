package edu.stu.user.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


interface TypeConvertor
{
	Object getJavaValue(ResultSet rs,int index) throws SQLException;
	void setParam(PreparedStatement stmt,int index,Object value)  throws SQLException;		
	Class<?> getJavaType();
}

class ByteTypeConverter implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{		
		return rs.getByte(index);
	}
	
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		stmt.setInt(index, (Integer)value) ;
	}
	
	public Class<?> getJavaType()
	{
		return byte.class;
	}
}

class ByteObjectTypeConvertor extends IntTypeConvertar
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{	
		BigDecimal val=rs.getBigDecimal(index);
		if(val==null)
			return null;
		else
			return val.byteValue();
	}
	
	public Class<?> getJavaType()
	{
		return Byte.class;
	}
}

class IntTypeConvertar implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{		
		return rs.getInt(index);
	}
	
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		stmt.setInt(index, (Integer)value) ;
	}
	
	public Class<?> getJavaType()
	{
		return int.class;
	}
}

class IntegerTypeConvertor extends IntTypeConvertar
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{	
		BigDecimal val=rs.getBigDecimal(index);
		if(val==null)
			return null;
		else
			return val.intValue();
	}
	
	public Class<?> getJavaType()
	{
		return Integer.class;
	}
}

class LongTypeConvertor implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{
		return rs.getLong(index);
	}
	
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		stmt.setLong(index, (Long)value) ;
	}
	
	public Class<?> getJavaType()
	{
		return long.class;
	}
}

class LongObjectTypeConvertor extends LongTypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{	
		BigDecimal val=rs.getBigDecimal(index);
		if(val==null)
			return null;
		else
			return val.longValue();
	}
	
	public Class<?> getJavaType()
	{
		return Long.class;
	}
}

class FloatTypeConvertor implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{
		return rs.getFloat(index);
	}
	
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		stmt.setFloat(index, (Float)value) ;
	}
	
	public Class<?> getJavaType()
	{
		return float.class;
	}
}

class FloatObjectTypeConvertor extends LongTypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{	
		BigDecimal val=rs.getBigDecimal(index);
		if(val==null)
			return null;
		else
			return val.floatValue();
	}
	
	public Class<?> getJavaType()
	{
		return Float.class;
	}
}

class DoubleTypeConvertor implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{
		return rs.getFloat(index);
	}
	
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		stmt.setDouble(index, (Float)value) ;
	}
	
	public Class<?> getJavaType()
	{
		return double.class;
	}
}

class DoubleObjectTypeConvertor extends DoubleTypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{	
		BigDecimal val=rs.getBigDecimal(index);
		if(val==null)
			return null;
		else
			return val.doubleValue();
	}
	
	public Class<?> getJavaType()
	{
		return Double.class;
	}
}

class StringTypeConvertor implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{
		return rs.getString(index);
	}
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		stmt.setString(index, (String)value) ;
	}
	public Class<?> getJavaType()
	{
		return String.class;
	}
}

class BooleanTypeConvertor implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index) throws SQLException
	{
		return rs.getBoolean(index);
	}
	
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		stmt.setBoolean(index, (Boolean)value) ;
	}
	
	public Class<?> getJavaType()
	{
		return boolean.class;
	}
}

class BooleanObjectTypeConvertor extends BooleanTypeConvertor
{		
	
	public Class<?> getJavaType()
	{
		return Boolean.class;
	}
}

class DateTypeConvertor implements TypeConvertor
{
	public Object getJavaValue(ResultSet rs,int index)throws SQLException
	{
		return rs.getDate(index);

	}
	
	public void setParam(PreparedStatement stmt,int index,Object value) throws SQLException
	{
		
		stmt.setDate(index, new java.sql.Date(((java.util.Date)value).getTime())) ;
	}
	
	public Class<?> getJavaType()
	{
		return java.util.Date.class;
	}
}

