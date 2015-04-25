package cn.imqiba.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.imqiba.map.DataActionMap;
import cn.imqiba.sql.IMakeSql;

public class Store2DB
{
	public static void main(String[] args)
	{
		int coreCounts = Runtime.getRuntime().availableProcessors();
		ExecutorService threadPool = Executors.newFixedThreadPool(coreCounts * 2);
		
		DBConnPool dbPool = new DBConnPool("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.80.132:3306/user?useUnicode=true&characterEncoding=utf8", "root", "123456");
		try
		{
			dbPool.createPool();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			DataActionMap.initClassMap("cn.imqiba.sql");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		BufferedReader reader = null;
		try
		{
			Process process = Runtime.getRuntime().exec("rdb --command json dump.rdb");
			
			InputStream stderr = process.getErrorStream();
			getErrStream(stderr);
			
			InputStream input = process.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
			
			String data = null;
			String endFlag = null;
			while((data = reader.readLine()) != null)
			{
				if(data.startsWith("[{", 0))
				{
					continue;
				}
				
				if(data.endsWith("}]"))
				{
					endFlag = "}]";
				}
				else
				{
					endFlag = ",";
				}
				
				String sFlag = ":{";
				int flagIndex = data.indexOf(sFlag);
				if(flagIndex == -1)
				{
					continue;
				}
				
				String key = data.substring(0, data.indexOf(sFlag) + 1);
				String val = data.substring(data.indexOf(sFlag) + 1, data.lastIndexOf(endFlag));
				key = key.substring(1, key.length() - 2);
				
				String classKey = key.substring(0, key.lastIndexOf(":") + 1);
				Class<?> clazz = DataActionMap.getClassByKey(classKey);
				if(clazz == null)
				{
					continue;
				}
				System.out.println(data);
				
				threadPool.execute(new DBTask(dbPool, (IMakeSql)clazz.newInstance(), key, val));
			}
			
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private static void getErrStream(final InputStream errStream)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					BufferedReader reader = new BufferedReader(new InputStreamReader(errStream));
					String data = null;
					while((data = reader.readLine()) != null)
					{
						System.out.println(data);
					}
				}
				catch (IOException e)
				{

				}
			}
		}.start();
	}

}
