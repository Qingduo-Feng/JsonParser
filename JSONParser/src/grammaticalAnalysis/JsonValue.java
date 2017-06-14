package grammaticalAnalysis;

public class JsonValue extends Json{
	private String value;
	private ObjectType type;
	
	public JsonValue(String inputValue, ObjectType inputType){
		value = inputValue;
		type = inputType;
	}
	
	@Override
	public String toString(){
		return value;
	}
	
	@Override
	public String getType(){
		return type.toString();
	}

	@Override
	public Json getObject(String condition) {
		return null;
	}
	
	
}
