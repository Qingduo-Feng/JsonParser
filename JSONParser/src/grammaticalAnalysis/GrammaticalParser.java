package grammaticalAnalysis;

import java.util.ArrayList;
import java.util.HashMap;

import lexicalAnalysis.Token;
import lexicalAnalysis.TokenType;

/*
 * 该类传入TOKEN序列，进行语法分析，之后传出JSON_OBJECT或者JSON_ARRAY
 */

public class GrammaticalParser {
	private TokenIterate iterator;
	
	public GrammaticalParser(ArrayList<ArrayList<Token>> inputToken){
		iterator = new TokenIterate(inputToken);
	}
	
	public Json parse() throws GramException{
		Token firstToken = iterator.read();
		if (firstToken.getType() == TokenType.OBJECT_START) {
			Json result = parseObject();
			System.out.println("valid");
			return result;
		}
		else if (firstToken.getType() == TokenType.ARRAY_START) {
			Json result = parseArray();
			System.out.println("valid");
			return result;
		}
		else{
			throw new GramException(1, 1, "Unknown letter position: row: %s, col: %s");
		}
	}
	
	private JsonArray parseArray() throws GramException{
		ArrayList<Json> result = new ArrayList<Json>();
		do{
			Token element1 = iterator.read();
			if (element1.getType() == TokenType.ARRAY_START) {
				JsonArray tokenArray = parseArray();
				result.add(tokenArray);
			}
			else if (element1.getType() == TokenType.OBJECT_START) {
				JsonObject tokenObject = parseObject();
				result.add(tokenObject);
			}
			else if (element1.getType() == TokenType.NUMBER) {
				JsonValue value = new JsonValue(element1.getValue(), ObjectType.INTEGER);
				result.add(value);
			}
			else if (element1.getType() == TokenType.FLOAT) {
				JsonValue value = new JsonValue(element1.getValue(), ObjectType.DECIMITER);
				result.add(value);
			}
			else if (element1.getType() == TokenType.SCENTIFIC) {
				JsonValue value = new JsonValue(element1.getValue(), ObjectType.SCENTIFIC);
				result.add(value);
			}
			else if (element1.getType() == TokenType.TRUE) {
				JsonValue value = new JsonValue(element1.getValue(), ObjectType.TRUE);
				result.add(value);
			}
			else if (element1.getType() == TokenType.FALSE) {
				JsonValue value = new JsonValue(element1.getValue(), ObjectType.FALSE);
				result.add(value);
			}
			else if (element1.getType() == TokenType.NULL) {
				JsonValue value = new JsonValue(element1.getValue(), ObjectType.NULL);
				result.add(value);
			}
			else if (element1.getType() == TokenType.STRING) {
				JsonValue value = new JsonValue(element1.getValue(), ObjectType.STRING);
				result.add(value);
			}
			else if (element1.getType() == TokenType.ARRAY_END) {
				break;
			}
			else{
				throw new GramException(iterator.getRow(), iterator.getCol(), "unexpected letter");
			}
			
			Token element = iterator.read();
			if (element.getType() != TokenType.COMMA && element.getType() != TokenType.ARRAY_END) {
				throw new GramException(iterator.getRow(), iterator.getCol(), "expect ','");
			}
			else if (element.getType() == TokenType.ARRAY_END) {
				break;
			}
			else{
				continue;
			}
		}while(true);
		JsonArray resultArr = new JsonArray(result);
		return resultArr;
	}
	
	private JsonObject parseObject() throws GramException{
		HashMap<String, Json> result = new HashMap<String, Json>();
		do {
			Token element1 = iterator.read();
			Token element2 = iterator.read();
			Token element3 = iterator.read();
			if (element1.getType() == TokenType.STRING && element2.getType() == TokenType.COLON) {
				if (element3.getType() == TokenType.ARRAY_START) {
					JsonArray tokenArray = parseArray();
					result.put(element1.getValue(), tokenArray);
				}
				else if (element3.getType() == TokenType.OBJECT_START) {
					JsonObject tokenObject = parseObject();
					result.put(element1.getValue(), tokenObject);
				}
				else if (element3.getType() == TokenType.NUMBER) {
					JsonValue value = new JsonValue(element3.getValue(), ObjectType.INTEGER);
					result.put(element1.getValue(), value);
				}
				else if (element3.getType() == TokenType.FLOAT) {
					JsonValue value = new JsonValue(element3.getValue(), ObjectType.DECIMITER);
					result.put(element1.getValue(), value);
				}
				else if (element3.getType() == TokenType.SCENTIFIC) {
					JsonValue value = new JsonValue(element3.getValue(), ObjectType.SCENTIFIC);
					result.put(element1.getValue(), value);
				}
				else if (element3.getType() == TokenType.TRUE) {
					JsonValue value = new JsonValue(element3.getValue(), ObjectType.TRUE);
					result.put(element1.getValue(), value);
				}
				else if (element3.getType() == TokenType.FALSE) {
					JsonValue value = new JsonValue(element3.getValue(), ObjectType.FALSE);
					result.put(element1.getValue(), value);
				}
				else if (element3.getType() == TokenType.NULL) {
					JsonValue value = new JsonValue(element3.getValue(), ObjectType.NULL);
					result.put(element1.getValue(), value);
				}
				else if (element3.getType() == TokenType.STRING) {
					JsonValue value = new JsonValue(element3.getValue(), ObjectType.STRING);
					result.put(element1.getValue(), value);
				}
				else{
					throw new GramException(iterator.getRow(), iterator.getCol(), "unexpected letter");
				}
			}
			else if (element2.getType() != TokenType.COLON){
				throw new GramException(iterator.getRow(), iterator.getCol(), "expect ':' after a key");
			}
			else if (element1.getType() == TokenType.STRING){
				throw new GramException(iterator.getRow(), iterator.getCol(), "the key must be a string");
			}
			else{
				throw new GramException(iterator.getRow(), iterator.getCol(), "unknown exception");
			}
			
			Token element4 = iterator.read();
			if (element4.getType() != TokenType.COMMA && element4.getType() != TokenType.OBJECT_END) {
				throw new GramException(iterator.getRow(), iterator.getCol(), "expect ','");
			}
			else if (element4.getType() == TokenType.OBJECT_END){
				break;
			}
			else{
				continue;
			}
		}while (true);
		JsonObject resultObject = new JsonObject(result);
		return resultObject;
	}
}
