package grammaticalAnalysis;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * 如果以[开头，那么便是array
 * 语法分析过程中读取[]之间的内容，因为不一定都是jsonObject类型，所以array内部申明为Json类型，可以为jsonObject，element等等
 * 以,为分界，将值传入JsonArray对象
 */

public class JsonArray extends Json{
	private ArrayList<Json> arrays = null;
	   
	public JsonArray(ArrayList<Json> inputArray){
		arrays = new ArrayList<Json>();
		arrays = inputArray;
	}
	
	public Json getElement(int index){
		return arrays.get(index);
	}
	
	public ArrayList<Json> getArrays() {
		return arrays;
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("[");
		for (int i = 0; i < arrays.size(); i++) {
			if (i != 0) {
				strBuilder.append(",");
			}
            strBuilder.append(arrays.get(i).toString());
        } 
		strBuilder.append("]");
		return strBuilder.toString();
	}

	@Override
	public Json getObject(String condition) {
		Json result = null;
		int index = Integer.parseInt(condition);
		if (index < arrays.size()) {
			result = arrays.get(index);
		}
		return result;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "ARRAY";
	}
}
