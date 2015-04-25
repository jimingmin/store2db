package cn.imqiba.main;

import java.sql.Connection;
import java.sql.PreparedStatement;

import cn.imqiba.sql.IMakeSql;

public class DBTask implements Runnable
{
	private DBConnPool pool = null;
	private IMakeSql makeSql = null;
	private String key = null;
	private String val = null;
	
	public DBTask(DBConnPool pool, IMakeSql makeSql, String key, String val)
	{
		this.pool = pool;
		this.makeSql = makeSql;
		this.key = key;
		this.val = val;
	}
	
	@Override
	public void run()
	{
		try
		{
			Connection conn = pool.getConnection();
			String sql = makeSql.writeToSql(key, val);
			//Statement st = conn.createStatement();
			PreparedStatement st = conn.prepareStatement(sql);
			st.execute(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
