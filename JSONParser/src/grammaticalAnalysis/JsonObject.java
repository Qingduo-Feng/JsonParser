package grammaticalAnalysis;

import java.util.HashMap;
import java.util.Iterator;
/*
 * 如果以{开头，那么便是object
 * 在语法分析过程中，读取{}之间的所有信息，存入hashmap中，传给jsonobject对象
 */

public class JsonObject extends Json{
	private HashMap<String, Json> objects = null;
	
	public JsonObject(HashMap<String, Json> value){
		objects = new HashMap<String, Json>();
		objects = value;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		Iterator it = objects.keySet().iterator();  
		strBuilder.append("{");
		int i = 0;
		while(it.hasNext()) {
			if (i != 0) {
				strBuilder.append(",");
			}
            String key = (String)it.next();  
            strBuilder.append(key);
            strBuilder.append(":");
            strBuilder.append(objects.get(key).toString());
            i++;
        } 
		strBuilder.append("}");
		return strBuilder.toString();
	}

	@Override
	public Json getObject(String condition){
		Json result = objects.get(condition);
		if (result != null) {
			return result;
		}
		else{
			return null;
		}

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "OBJECT";
	}
	
}
