package SearchItem;

import grammaticalAnalysis.Json;
import grammaticalAnalysis.JsonArray;
/*
 * 查询的实现，类似于XPATH，输入查询条件，/代表从根节点选取
 * 
 */
public class SearchItem {
	private String searchCondition = null;
	Json resultJson;
	
	public SearchItem(String searchConStr, Json resultJson){
		searchCondition = searchConStr;
		this.resultJson = resultJson;
	}
	
	public void search() throws Exception{
		String[] conditionArr = searchCondition.split("/");
		if (conditionArr[0] == null) {
			throw new Exception("the condition must start with '/'");
		}
		Json currentJson = resultJson;
		for (int i = 1; i < conditionArr.length; i++) {
			String condition = conditionArr[i];
			if (condition.charAt(0) == '[') {
				if (currentJson.getClass() == JsonArray.class) {
					currentJson = currentJson.getObject(condition.substring(condition.indexOf('[') + 1, condition.indexOf(']')));
					if (currentJson == null) {
						break;
					}
				}
				else{
					//报错
					throw new Exception("对JsonObject使用下标");
				}
			}
			else{
				if (condition.contains("[")) {
					//获取对象中的数组元素
					String conditionStr = condition.substring(0, condition.indexOf('['));
					currentJson = currentJson.getObject("\"" + conditionStr + "\"");
					if (currentJson == null) {
						break;
					}
					currentJson = currentJson.getObject(condition.substring(condition.indexOf('[') + 1, condition.indexOf(']')));
					if (currentJson == null) {
						break;
					}
				}
				else{
					currentJson = currentJson.getObject("\"" + condition + "\"");
					if (currentJson == null) {
						break;
					}
				}
			}
		}
		if (currentJson == null) {
			System.out.println("NULL");
		}
		else{
			System.out.println(currentJson.toString());
			System.out.println(currentJson.getType());
		}
	}
}
