package lexicalAnalysis;

public class Token {
	private TokenType type;
	private String value;
	
	public String getValue(){
		return value;
	}
	
	public TokenType getType(){
		return type;
	}
	
	public Token(TokenType inputType, String inputValue){
		type = inputType;
		value = inputValue;
	}
	
}
