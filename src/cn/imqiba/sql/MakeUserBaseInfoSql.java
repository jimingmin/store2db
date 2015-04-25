package cn.imqiba.sql;

import org.json.JSONObject;

import cn.imqiba.map.DataActionAnnotation;

@DataActionAnnotation(key = "user:baseinfo:")
public class MakeUserBaseInfoSql implements IMakeSql
{
	public String writeToSql(String key, String val)
	{
		JSONObject object = new JSONObject(val);
		int uin = object.has("uin") ? object.getInt("uin") : 0;
		int version = object.has("version") ? object.getInt("version") : 1;
		long accountName = object.has("accountname") ? object.getLong("accountname") : 0L;
		int accountID = object.has("accountid") ? object.getInt("accountid") : 0;
		String nickName = "'" + (object.has("nickname") ? object.getString("nickname") : "") + "'";
		String oneselfWords = "'" + (object.has("oneselfwords") ? object.getString("oneselfwords") : "") + "'";
		int gender = object.has("gender") ? object.getInt("gender") : 1;
		String school = "'" + (object.has("school") ? object.getString("school") : "") + "'";
		String hometown = "'" + (object.has("hometown") ? object.getString("hometown") : "") + "'";
		String livePlace = "'" + (object.has("liveplace") ? object.getString("liveplace") : "") + "'";
		String job = "'" + (object.has("job") ? object.getString("job") : "") + "'";
		int height = object.has("height") ? object.getInt("height") : 0;
		int weight = object.has("weight") ? object.getInt("weight") : 0;
		String headImage = "'" + (object.has("headimage") ? object.getString("headimage") : "") + "'";
		String photoWall = "'" + (object.has("photowall") ? object.getString("photowall") : "") + "'";
		long createTime = object.has("createtime") ? object.getLong("createtime") : 0L;
		String birthday = "'" + (object.has("birthday") ? object.getString("birthday") : "") + "'";
		int age = object.has("age") ? object.getInt("age") : 0;
		int followersCount = object.has("followers_count") ? object.getInt("followers_count") : 0;
		int fansCount = object.has("fans_count") ? object.getInt("fans_count") : 0;
		int friendsCount = object.has("friends_count") ? object.getInt("friends_count") : 0;
		int pubTopicCount = object.has("pub_topic_count") ? object.getInt("pub_topic_count") : 0;
		int joinTopicCount = object.has("join_topic_count") ? object.getInt("join_topic_count") : 0;
		
		String sql = "INSERT INTO user_base_info VALUES (" + uin + ", " + version + ", " + accountName + ", " + accountID + ", "
				+ nickName + ", " + oneselfWords
				+ ", " + gender + ", " + school + ", " + hometown + ", " + livePlace + ", " + job + ", " + height + ", " + weight
				+ ", " + headImage + ", " + photoWall + ", " + "FROM_UNIXTIME(" + createTime + ", '%Y-%m-%d %h:%i:%s')" + ", " + birthday + ", " + age + ", " + followersCount
				+ ", " + fansCount + ", " + friendsCount + ", " + pubTopicCount + ", " + joinTopicCount + 
				") ON DUPLICATE KEY UPDATE " + "uin=" + uin + ", version=" + version + ", accountname=" + accountName
				+ ", accountid=" + accountID + ", nickname=" + nickName + ", oneselfwords=" + oneselfWords
				+ ", gender=" + gender + ", school=" + school + ", hometown=" + hometown + ", liveplace=" + livePlace
				+ ", job=" + job + ", height=" + height + ", weight=" + weight + ", headimage=" + headImage
				+ ", photowall=" + photoWall + ", createtime=FROM_UNIXTIME(" + createTime + ", '%Y-%m-%d %h:%i:%s'), birthday=" + birthday + ", age=" + age
				+ ", followerscount=" + followersCount + ", fanscount=" + fansCount + ", friendscount=" + friendsCount
				+ ", pubtopiccount=" + pubTopicCount + ", jointopiccount=" + joinTopicCount + ";";

		return sql;
	}
}
