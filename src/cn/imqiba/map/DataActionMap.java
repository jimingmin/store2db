package cn.imqiba.map;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataActionMap
{
	private static DataActionMap m_stDataActionMap = new DataActionMap();
	private static Map<String, Class<?>> m_stKeyClassMap;
	
	public static DataActionMap getInstance()
	{
		return m_stDataActionMap;
	}
	
	// 根据类型得到对应的消息类的class对象  
	public static Class<?> getClassByKey(String key)
	{
		return m_stKeyClassMap.get(key);
	}
	
	public static void initClassMap(String path) throws ClassNotFoundException, IOException
	{
		Map<String, Class<?>> tmpMap = new HashMap<String, Class<?>>();  
		Set<Class<?>> classSet = ScanClasses.getClasses(path);
		if (classSet != null)
		{
			for (Class<?> clazz : classSet)
			{
				if (clazz.isAnnotationPresent(DataActionAnnotation.class))
				{
					DataActionAnnotation annotation = clazz.getAnnotation(DataActionAnnotation.class);
					tmpMap.put(annotation.key(), clazz);
				}
			}
		}
		m_stKeyClassMap = Collections.unmodifiableMap(tmpMap);  
	}
}
