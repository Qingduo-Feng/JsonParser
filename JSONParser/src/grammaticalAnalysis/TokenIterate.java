package grammaticalAnalysis;

import java.util.ArrayList;

import lexicalAnalysis.Token;

/*
 * 新建一个迭代器，用来存储词法分析得到的list并记录当前的行数与列数
 */

public class TokenIterate {
	private int currentRow;
	private int currentCol;
	private ArrayList<ArrayList<Token>> content = null;
	
	public TokenIterate(ArrayList<ArrayList<Token>> input){
		content = input;
		currentRow = 0;
		currentCol = 0;
	}
	
	public Token read() throws GramException{
		Token token = content.get(currentRow).get(currentCol);
		currentCol++;
		if (currentCol >= content.get(currentRow).size()) {
			currentRow++;
			currentCol = 0;
		}
		//越界
		if (currentRow > content.size()) {
			throw new GramException(currentRow, currentCol, "out of range");
		}
		return token;
	}
	
	public int getRow(){
		return currentRow;
	}
	
	//获取报错的列，但是没有考虑空格···
	public int getCol(){
		int col = 0;
		for (Token token : content.get(currentRow)) {
			col += token.getValue().length();
		}
		return col;
	}
}
