package lexicalAnalysis;

import java.util.ArrayList;

public class LexAnalysis {
	/*
	 * 进行词法分析，对于源文件读取处理之后的char数组进行DFA处理，获取TOKEN序列并返回
	 * 初始化方法，传入string数组，拆分成char[]数组之后进行处理，方便统计出错行数
	 * 
	 */
	private String[] fileStr = null;
	private ArrayList<Token> result = null;
	
	public LexAnalysis(String[] file){
		int len = file.length;
		fileStr = new String[len];
		fileStr = file;
	}
	
	public ArrayList<ArrayList<Token>> analysis() throws Exception{
		ArrayList<ArrayList<Token>> result = new ArrayList<ArrayList<Token>>(); 
		for (int i = 0; i < fileStr.length; i++) {
			result.add(AnalysisOneLine(i, fileStr[i]));
		}
		return result;
	}
	
	//row即fileStr数组当前的位置
	private ArrayList<Token> AnalysisOneLine(int row, String inputStr) throws Exception{
		char[] content = new char[inputStr.length()];
		content = inputStr.toCharArray();
		if (content != null) {
			result = new ArrayList<Token>();
			int i;
			for (i = 0;  i< content.length; i++) {
				char c = content[i];
				if (c == 't' || c == 'T') {
					if (readTrue(content, row, i)) {
						result.add(new Token(TokenType.TRUE, "true"));
					}
					i += 3;
				}
				else if (c == 'f' || c == 'F') {
					if (readFalse(content, row, i)) {
						result.add(new Token(TokenType.FALSE, "false"));
					}
					i += 4;
				}
				else if (c == 'n' || c == 'N') {
					if (readNull(content, row, i)) {
						result.add(new Token(TokenType.NULL, "null"));
					}
					i += 3;
				}
				else if (isNum(c)) {
					int forward = readNum(content, row, i);
					i += forward;
					i--;
				}
				else if (c == '-') {
					int forward = readNum(content, row, i);
					i += forward;
					i--;
				}
				else if (c == ' ' || c == '\t') {
					continue;
				}
				else if (c == ':') {
					Token token = new Token(TokenType.COLON, ":");
					result.add(token);
				}
				else if (c == ';') {
					Token token = new Token(TokenType.SEMICOLON, ";");
					result.add(token);
				}
				else if (c == '"') {
					int forward = readString(content, row, i);
					i += forward;
				}
				else if (c == '{') {
					Token token = new Token(TokenType.OBJECT_START, "{");
					result.add(token);
				}
				else if (c == '}') {
					Token token = new Token(TokenType.OBJECT_END, "}");
					result.add(token);
				}
				else if (c == '[') {
					Token token = new Token(TokenType.ARRAY_START, "[");
					result.add(token);
				}
				else if (c == ']') {
					Token token = new Token(TokenType.ARRAY_END, "]");
					result.add(token);
				}
				else if (c == ',') {
					Token token = new Token(TokenType.COMMA, ",");
					result.add(token);
				}
				else {
					throw new LexException(row+1, i+1, "unexpected character");
				}
			}
		}
		return result;
	}
	
	private boolean isNum(char c){
		 if(c >= '0' && c <='9'){
		     return true;
		   }
		   return false;
	}
	
	private int readNum(char[] content, int row, int i){
		StringBuilder strBuilder = new StringBuilder();
		int temp = i;
		if (content[temp] == '-') {
			strBuilder.append("-");
			temp++;
		}
		
		if (content[temp] == '0') {
			strBuilder.append("0");
			temp++;
		}
		else if (content[temp] >= '1' && content[temp] <= '9') {
			do {	
				strBuilder.append(content[temp]);
				temp++;
			} while (content[temp] <= '9' && content[temp] >= '0');
		}
		
		if (content[temp] == '.') {
			do {		
				strBuilder.append(content[temp]);
				temp++;
			} while (content[temp] <= '9' && content[temp] >= '0' && temp < content.length -1);
		}
		
		if (content[temp] == 'e' || content[temp] == 'E') {
			strBuilder.append(content[temp]);
			temp++;
			if (content[temp] == '+' || content[temp] == '-' || (content[temp] < '9' && content[temp] > '0')) {
				do {
					strBuilder.append(content[temp]);
					temp++;
					if (temp == content.length) {
						break;
					}
				} while (content[temp] <= '9' && content[temp] >= '0');
				temp--;
			}
		}
		
		//判断数字类型
		String strResult = strBuilder.toString();
		if (strResult.contains("e") || strResult.contains("E")) {
			result.add(new Token(TokenType.SCENTIFIC, strResult));
		}
		else if (strResult.contains(".")) {
			result.add(new Token(TokenType.FLOAT, strResult));
		}
		else{
			result.add(new Token(TokenType.NUMBER, strResult));
		}
		
		return (temp - i);
	}
	
	private int readString(char[] content, int row, int i) throws LexException{
		int temp = i;
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\"");
		while (true) {
			temp++;
			if (temp == content.length) {
				throw new LexException(row, i+1, "no legal character found");
			}
			if (content[temp] == '"') {
				strBuilder.append("\"");
				break;
			}
			else if (content[temp] == '\\') {
				temp++;
				if (content[temp] == '\"') {
					
				}
				else if (content[temp] == '\\') {
					
				}
				else if (content[temp] == '/') {
					
				}
				else if (content[temp] == 'b') {
					
				}
				else if (content[temp] == 'f') {
					
				}
				else if (content[temp] == 'n') {
					
				}
				else if (content[temp] == 'r') {
					
				}
				else if (content[temp] == 't') {
					
				}
				else if (content[temp] == 'u') {
					
				}
				else {
					throw new LexException(row, i+1, "no legal character found");
				}
			}
			else {
				strBuilder.append(content[temp]);
			}
		}
		result.add(new Token(TokenType.STRING, strBuilder.toString()));
		return (temp - i);
	}
	
	private boolean readTrue(char[] content, int row, int i) throws LexException{
		i++;
		if (content[i] == 'r' || content[i] == 'R') {
			i++;
			if (content[i] == 'u' || content[i] == 'U'){
				i++;
				if (content[i] == 'e' || content[i] == 'E'){
					return true;
				}
				else {
					throw new LexException(row, i+1, "expect letter 'l' or 'L'");
				}
			}
			else{
				throw new LexException(row, i+1, "expect letter 'u' or 'U'");
			}
		}
		else{
			throw new LexException(row, i+1, "expect letter 'r' or 'R'");
		}
	}
	
	private boolean readFalse(char[] content, int row, int i) throws LexException{
		i++;
		if (content[i] == 'a' || content[i] == 'A') {
			i++;
			if (content[i] == 'l' || content[i] == 'L'){
				i++;
				if (content[i] == 's' || content[i] == 'S'){
					i++;
					if (content[i] == 'e' || content[i] == 'E'){
						return true;
					}
					else {
						throw new LexException(row, i+1, "expect an 'e' or 'E'");
					}
				}
				else{
					throw new LexException(row, i+1, "expect letter 's' or 'S'");
				}
			}
			else {
				throw new LexException(row, i+1, "expect letter 'l' or 'L'");
			}
		}
		else {
			throw new LexException(row, i+1, "expect letter 'a' or 'A'");
		}
	}
	
	private boolean readNull(char[] content, int row, int i) throws LexException{
		i++;
		if (content[i] == 'u' || content[i] == 'U') {
			i++;
			if (content[i] == 'l' || content[i] == 'L'){
				i++;
				if (content[i] == 'l' || content[i] == 'L'){
					return true;
				}
				else{
					throw new LexException(row, i+1, "expect letter 'l' or 'L'");
				}
			}
			else{
				throw new LexException(row, i+1, "expect letter 'l' or 'L'");
			}
		}
		else{
			throw new LexException(row, i+1, "expect letter 'u' or 'U'");
		}
	}
}
