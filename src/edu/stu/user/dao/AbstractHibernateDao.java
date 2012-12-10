package edu.stu.user.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jdbc.Batcher;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.stu.util.PageResult;

public abstract class AbstractHibernateDao extends HibernateDaoSupport{

	private static Map<Class<?>,TypeConvertor> typeMaps=new HashMap<Class<?>,TypeConvertor>();
	private static Map<Class<?>,Map<String,Method>> classMap=new HashMap<Class<?>,Map<String,Method>>();

	static{
		typeMaps.put(boolean.class, new BooleanTypeConvertor());
		typeMaps.put(Boolean.class, new BooleanObjectTypeConvertor());
		typeMaps.put(byte.class, new ByteTypeConverter());
		typeMaps.put(Byte.class, new ByteObjectTypeConvertor());
		typeMaps.put(int.class, new IntTypeConvertar());
		typeMaps.put(Integer.class, new IntegerTypeConvertor());
		typeMaps.put(String.class, new StringTypeConvertor());
		typeMaps.put(java.util.Date.class, new DateTypeConvertor());
		typeMaps.put(long.class, new LongTypeConvertor());
		typeMaps.put(Long.class, new LongObjectTypeConvertor());
		typeMaps.put(float.class, new FloatTypeConvertor());
		typeMaps.put(Float.class, new FloatObjectTypeConvertor());
		typeMaps.put(double.class, new DoubleTypeConvertor());
		typeMaps.put(Double.class, new DoubleObjectTypeConvertor());
	}

	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，规则：
	 * <br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，则抛出异常
	 * <br/>注意：此查询会返回满足条件的所有记录，慎用
	 * @param sql 数据查询SQL
	 * @param returnEntityType  要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find( String sql,Class<?> returnEntityType)
	{
		return this.find(sql, returnEntityType,false);
	}
	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，则抛出异常
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param rows 返回的行数
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find( String sql,Class<?> returnEntityType,int rows)
	{
		return this.find(sql, returnEntityType,rows,false);
	}

	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，则抛出异常
	 *  此查询会返回满足条件的所有记录，慎用
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param params 查询条件
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find(String sql,Class<?> returnEntityType,Object[] params)
	{
		return this.find(sql, returnEntityType, params,false);
	}

	/**
	 * 按分页查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，则抛出异常
	 * @param countSql 总条数查询SQL
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param pageNo
	 * @param pageSize
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected PageResult find(String countSql, String sql, Class<?> returnEntityType, int pageNo,int pageSize)
	{
		return this.find(countSql, sql, returnEntityType, pageNo, pageSize,false);
	}

	/**
	 * 按分页查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，则抛出异常
	 * @param countSql 总条数查询SQL
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param params 查询条件
	 * @param pageNo
	 * @param pageSize
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected PageResult find(String countSql, String sql, Class<?> returnEntityType, Object[] params,int pageNo,int pageSize)
	{
		return this.find(countSql, sql, returnEntityType, params, pageNo, pageSize,false);
	}

	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，则抛出异常
	 * @param sql
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param params 查询条件
	 * @param rows 返回数据条数
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find(String sql,Class<?> returnEntityType,Object[] params,int rows)
	{
		return this.find(sql, returnEntityType,params, rows, false);
	}


	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，则抛出异常
	 * <br/>注意：此查询会返回满足条件的所有记录，慎用
	 * @param sql 数据查询SQL
	 * @param returnEntityType  要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param ignoreFieldNotExist 是否忽略类属性不存在
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find( String sql,Class<?> returnEntityType,boolean ignoreFieldNotExist)
	{
		return this.find(sql, returnEntityType, null, ignoreFieldNotExist);
	}
	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配， 如果找不到与字段对应的类属性，
	 * 则根据ignoreFieldNotExist确定是否抛出异常
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param rows 返回的行数
	 * @param ignoreFieldNotExist 是否忽略累属性不存在
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find( String sql,Class<?> returnEntityType,int rows,boolean ignoreFieldNotExist)
	{
		return this.find(sql, returnEntityType, null, rows, ignoreFieldNotExist);
	}

	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，
	 * 则根据ignoreFieldNotExist确定是否抛出异常
	 * <br/>此查询会返回满足条件的所有记录，慎用
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param params 查询条件
	 * @param ignoreFieldNotExist 是否忽略类属性不存在
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find(String sql,Class<?> returnEntityType,Object[] params,boolean ignoreFieldNotExist)
	{
		return (List<?>)this.internalFind(null,sql, returnEntityType, params,0,0,ignoreFieldNotExist);
	}

	/**
	 * 按分页查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，
	 * 则根据ignoreFieldNotExist确定是否抛出异常
	 * @param countSql 总条数查询SQL
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param pageNo
	 * @param pageSize
	 * @param ignoreFieldNotExist 是否忽略类属性不存在
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected PageResult find(String countSql, String sql, Class returnEntityType, int pageNo,int pageSize,boolean ignoreFieldNotExist)
	{
		return this.find(countSql, sql, returnEntityType, null, pageNo, pageSize,ignoreFieldNotExist);
	}

	/**
	 * 按分页查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，
	 * 则根据ignoreFieldNotExist确定是否抛出异常
	 * @param countSql 总条数查询SQL
	 * @param sql 数据查询SQL
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param params 查询条件
	 * @param pageNo
	 * @param pageSize
	 * @param ignoreFieldNotExist 是否忽略类属性不存在
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected PageResult find(String countSql, String sql, Class returnEntityType, Object[] params,int pageNo,int pageSize,boolean ignoreFieldNotExist)
	{
		sql="select * from ( select row_123_.*, rownum rownum__ from ("+sql+") row_123_ where rownum <= "+(pageNo*pageSize)+") where rownum__ > "+((pageNo-1)*pageSize);
		return (PageResult)this.internalFind(countSql, sql, returnEntityType, params, pageNo, pageSize,ignoreFieldNotExist);
	}

	/**
	 * 查询，返回按提供的类组装的数据集合。组装数据时，按数据库字段（或别名）与类属性名进行匹配，
	 * 规则：<br/>根据字段名转为小写后匹配对象属性，如果字段名含下划线则去掉并把其后的第一字母转为大写再进行匹配，如果找不到与字段对应的类属性，
	 * 则根据ignoreFieldNotExist确定是否抛出异常
	 * @param sql
	 * @param returnEntityType 要返回的数据对象类型，任何类均可，Hibernate；类映射无关。
	 * @param params 查询条件
	 * @param rows 返回数据条数
	 * @param ignoreFieldNotExist 是否忽略类属性不存在
	 * @return 按提供的类组装的数据集合。这些数据对象的属性值可能是不完整的，如果要用来更新数据库，请慎用。
	 */
	protected List<?> find(String sql,Class<?> returnEntityType,Object[] params,int rows,boolean ignoreFieldNotExist)
	{
		sql="select row_123_.*, rownum rownum__ from ("+sql+") row_123_ where rownum <= "+rows;
		return this.find(sql, returnEntityType, params,ignoreFieldNotExist);
	}


	private Object internalFind(final String countSql,final String sql,final Class<?> returnEntityType,final Object[] params,final int pageNo,final int pageSize,final boolean ignoreFieldNotExist)
	{
		Batcher batcher=((SessionImpl)this.getSession()).getBatcher();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		PageResult result=null;
		List entityList=new ArrayList(pageSize==0?20:pageSize);

		try
		{
			//分页查询
			if(countSql!=null)
			{
				result=new PageResult();
				result.setPageNo(pageNo);
				result.setPageSize(pageSize);
				result.setValues(entityList);

				stmt=batcher.prepareSelectStatement(countSql);
				setParams(stmt,params);
				rs=batcher.getResultSet(stmt);
				rs.next();
				result.setTotal(rs.getInt(1));
				batcher.closeQueryStatement(stmt, rs);
			}

			//查询实际数据
			stmt=batcher.prepareSelectStatement(sql);
			setParams(stmt,params);
			rs=batcher.getResultSet(stmt);
			ResultSetMetaData metas= stmt.getMetaData();

			Object entity;
			Object propVal;

			String methodName;
			Map<String,Method> methodMap=null;
			synchronized(returnEntityType)
			{
				methodMap=classMap.get(returnEntityType);
				if(methodMap==null)
				{
					methodMap=new HashMap<String,Method>();
					classMap.put(returnEntityType, methodMap);
				}
			}
			Method method;

			while(rs.next())
			{

				//组装数据
				try {
					entity=returnEntityType.newInstance();
				} catch (Exception e1) {
					throw new HibernateException(e1);
				}
				for(int i=1;i<=metas.getColumnCount();i++)
				{
					if(metas.getColumnName(i).equalsIgnoreCase("rownum__"))
						continue;

					methodName=toLegalMethod(metas.getColumnName(i));
					method=methodMap.get(methodName);
					if(method==null)
					{
						for(Method m:returnEntityType.getMethods())
						{
							if(m.getName().equals(methodName))
							{
								method=m;
								methodMap.put(methodName, method);
							}
						}
					}
					if(method==null)
					{
						if(ignoreFieldNotExist)
							continue;
						else
							throw new HibernateException("类"+returnEntityType+"不存在方法："+methodName);
					}

					//得到标属性值

					propVal=typeMaps.get(method.getParameterTypes()[0]).getJavaValue(rs,i);

					if(propVal!=null)
						try {
							method.invoke(entity, propVal);
						} catch (Exception e) {
							throw new HibernateException(e);
						}

				}
				entityList.add(entity);
			}
		}
		catch(SQLException e)
		{
			throw new HibernateException(e);
		}
		finally
		{

			try {
				batcher.closeQueryStatement(stmt, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//跟据是否分页查询返回不同的结果
		if(result!=null)
			return result;
		else
			return entityList;
	}

	private void setParams(PreparedStatement stmt, Object[] params) throws SQLException
	{
		if(params==null)
			return;
		int index=1;
		for(Object param:params)
		{
			typeMaps.get(param.getClass()).setParam(stmt, index++, param);
		}
	}

	private static String toLegalVar(String sqlFieldName)
	{

		StringBuilder sb=new StringBuilder(sqlFieldName.toLowerCase());
		int pos=sb.indexOf("_");
		char c;
		while(pos>=0)
		{
			c=sb.charAt(pos+1);
			c=Character.toUpperCase(c);
			sb.delete(pos, pos+2);
			sb.insert(pos, c);
			pos=sb.indexOf("_");
		}
		return sb.toString();
	}

	private String toLegalMethod(String sqlFieldName)
	{
		if(sqlFieldName.equalsIgnoreCase("row_id"))
		{
			return "setId";
		}
		StringBuilder sb=new StringBuilder(sqlFieldName.toLowerCase());
		int pos=sb.indexOf("_");
		char c;
		while(pos>=0)
		{
			c=sb.charAt(pos+1);
			c=Character.toUpperCase(c);
			sb.delete(pos, pos+2);
			sb.insert(pos, c);
			pos=sb.indexOf("_");
		}
		c=Character.toUpperCase(sb.charAt(0));
		sb.delete(0, 1);
		sb.insert(0, c);
		sb.insert(0, "set");
		return sb.toString();
	}


	public static String toLike(String str) {
		if (str == null || str.length() == 0)
			return null;
		str = str.trim();
		if (str.length() == 0)
			return null;
		return "%" + str + "%";
	}

	public static PageResult executeQuery(Query countQuery, Query generalQuery,
			int pageNo, int pageSize) {
		PageResult result = new PageResult();
		result.setPageSize(pageSize);
		result.setPageNo(pageNo);
		result.setTotal(((Number)countQuery.uniqueResult()).intValue());

		if (result.getTotal() == 0)
			return new PageResult();
		generalQuery.setFirstResult((result.getPageNo() - 1) * pageSize);
		generalQuery.setMaxResults(pageSize);
		result.setValues(generalQuery.list());
		return result;
	}

	/**
	 * 根据查询条件进行分页查询
	 *
	 * @param detachedCriteria
	 *            查询条件
	 * @param page
	 *            第几页
	 * @param pageSize
	 *            每页显示多少行
	 * @return 分页对象
	 * @throws DataAccessException
	 */
	public PageResult findPageResultByCriteria(
			final DetachedCriteria detachedCriteria, final int pageNo,
			final int pageSize) throws DataAccessException {
		return (PageResult) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						org.hibernate.impl.CriteriaImpl criteria = (org.hibernate.impl.CriteriaImpl) detachedCriteria
								.getExecutableCriteria(session);

						Projection projection = criteria.getProjection();

						int totalCount = ((Integer) criteria.setProjection(
								Projections.rowCount()).uniqueResult())
								.intValue();

						PageResult pg = new PageResult();
						pg.setPageSize(pageSize);
						pg.setPageNo(pageNo);
						pg.setTotal(totalCount);

						if (totalCount == 0) {
							return new PageResult();
						}

						criteria.setProjection(projection);
						if (projection == null) {
							criteria
									.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
						}


						int startIndex = (pg.getPageNo() - 1) * pageSize;
						List list = criteria.setFirstResult(startIndex)
								.setMaxResults(pageSize).list();

						pg.setValues(list);
						return pg;
					}
				});
	}

	public List findAllByCriteria(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				return criteria.list();
			}
		}, true);
	}

	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		Integer count = (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						return criteria.setProjection(Projections.rowCount())
								.uniqueResult();
					}
				}, true);
		return count.intValue();
	}

        protected PageResult find(String dataHql,int pageNo, int pageSize)
       {
           return this.find(generateCountHql(dataHql),dataHql,pageNo,pageSize);
       }

        protected PageResult find( String countHql, String dataHql,int pageNo, int pageSize)
        {
            return this.find(countHql,dataHql,(Object[])null,pageNo,pageSize);
        }

        private String generateCountHql(String dataHql)
        {

            String copy=dataHql.toLowerCase();
            int idx=copy.indexOf("from");
            dataHql=dataHql.substring(idx);

            copy=dataHql.toLowerCase();
            idx=copy.lastIndexOf("order");
            if(idx>0)
            {
                dataHql=dataHql.substring(0,idx);
            }
            if(copy.indexOf("fetch")>0)
            {
                dataHql=dataHql.replaceAll("fetch","");
            }
            return "select count(*) "+dataHql;
        }

         protected PageResult find( String dataHql, Object[] params, int pageNo, int pageSize)
         {
             return this.find(this.generateCountHql(dataHql),dataHql,params,pageNo,pageSize);
         }

         protected PageResult find(final String dataHql,final Object param,final int pageNo,final int pageSize)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
//                	System.err.println(generateCountHql(dataHql));
                    Query q1=session.createQuery(generateCountHql(dataHql));
                    Query q2=session.createQuery(dataHql);
                    q1.setParameter(0,param);
                    q2.setParameter(0,param);
                    return executeQuery(q1,q2,pageNo,pageSize);
                }
            };

            return (PageResult) super.getHibernateTemplate().execute(action);
        }


        protected PageResult find(final String countHql,final String dataHql,final Object[] params,final int pageNo,final int pageSize)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q1=session.createQuery(countHql);
                    Query q2=session.createQuery(dataHql);
                    fillParams(q1,params);
                    fillParams(q2,params);
                    return executeQuery(q1,q2,pageNo,pageSize);
                }
            };

            return (PageResult) super.getHibernateTemplate().execute(action);
        }


        protected List find(final String dataHql,final Object[] params, final int row)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.createQuery(dataHql);
                    fillParams(q2,params);
                    q2.setMaxResults(row);
                    return q2.list();

                }
            };

            return (List) getHibernateTemplate().execute(action);
        }

        protected Object findUnique(final String dataHql,final Object[] params)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.createQuery(dataHql);
                    fillParams(q2,params);
                    return q2.uniqueResult();

                }
            };
                       
            return  getHibernateTemplate().execute(action);
        }
        
        protected Object findUniqueByNameSql(final String nameSql,final Object[] params)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.getNamedQuery(nameSql);
                    fillParams(q2,params);
                    return q2.uniqueResult();
                }
            };

            return  getHibernateTemplate().execute(action);
        }
        
        
        protected Object findUniqueBySql(final String sql,final Object[] params)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.createSQLQuery(sql);
                    fillParams(q2,params);
                    return q2.uniqueResult();

                }
            };

            return  getHibernateTemplate().execute(action);
        }
        
        
        protected Object findUnique(final String dataHql,final Object param)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.createQuery(dataHql);
                    q2.setParameter(0, param);
                    return q2.uniqueResult();

                }
            };

            return  getHibernateTemplate().execute(action);
        }
        
        
        
        protected Object findUniqueBySql(final String sql,final Object param)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.createSQLQuery(sql);
                    q2.setParameter(0, param);
                    return q2.uniqueResult();

                }
            };

            return  getHibernateTemplate().execute(action);
        }
        
        protected Object findUniqueByNameSql(final String nameSql,final Object param)
        {
            HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.getNamedQuery(nameSql);
                    q2.setParameter(0, param);
                    return q2.uniqueResult();

                }
            };

            return  getHibernateTemplate().execute(action);
        }
        
        protected List find(final String dataHql,final int row)
        {
        	HibernateCallback action= new HibernateCallback() {
                public Object doInHibernate(Session session) throws
                        HibernateException {
                    Query q2=session.createQuery(dataHql);
                    q2.setMaxResults(row);
                    return q2.list();

                }
            };

            return (List) getHibernateTemplate().execute(action);
        }
        protected List find(final String dataHql,final Object param, final int row)
       {
           HibernateCallback action= new HibernateCallback() {
               public Object doInHibernate(Session session) throws
                       HibernateException {
                   Query q2=session.createQuery(dataHql);
                   q2.setParameter(0,param);
                   q2.setMaxResults(row);
                   return q2.list();

               }
           };

           return (List) getHibernateTemplate().execute(action);
       }

        private void fillParams(Query q,Object[] params)
        {
            if(params==null)
                return;
            for(int i=0;i<params.length;i++)
            {
                q.setParameter(i,params[i]);
            }
        }
        
        public static String generateUuid()
        {
        	return UUID.randomUUID().toString().replace("-", "");
        }
        
        public static void main(String[] args)
        {
        	System.err.println(generateUuid());
        }
        
        protected Object delete(final Class<?> entityType, final Serializable id){
        	 HibernateCallback action= new HibernateCallback() {
                 public Object doInHibernate(Session session) throws
                         HibernateException {
                	 Object o=session.get(entityType, id);
                	 if(o!=null)
                		 session.delete(o);
                	 return o;
                 }
        	 };
        	return this.getHibernateTemplate().execute(action);
        }
        
       protected <T> T getEntity(Class<T> clazz, Serializable id)
       {
    	   return (T) getHibernateTemplate().get(clazz,id);
       }
}
