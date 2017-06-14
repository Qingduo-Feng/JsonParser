package prettyOutput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import grammaticalAnalysis.*;

/*
 * 传入命令行参数中的文件名，country.json
 * 首先拆分字符串，打开或新建一个文件country.pretty.json
 * 然后进行tostring，变成一整行，遇到{or[进行格式化输出
 */

public class prettyOutput {
	private String fileName = null;
	Json parseResult = null;
	
	public prettyOutput(String fileName, Json parseResult){
		this.fileName = fileName;
		this.parseResult = parseResult;
	}
	
	public void output(){
		String[] splitResult = fileName.split("\\.");
		if (splitResult.length > 0) {
			fileName = splitResult[0] + ".pretty.json";
		}
		File file = new File(fileName);
		if (file.exists()) {  
            System.out.println("存在该文件");  
        } else {  
            try {  
                file.createNewFile();
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }
		
		BufferedWriter writer = null;
		int indent = 0;//控制缩进
		try {
			writer = new BufferedWriter(new FileWriter(file));
			String startString = parseResult.toString();
			int length = startString.length();
			for (int i = 0; i < length; i++) {
				char currentChar = startString.charAt(i);
				//如果遇到{或者[，缩进加4并且换行，输出缩进
				if (currentChar == '{' || currentChar == '[') {
					indent += 4;
					writer.write(currentChar);
					writer.newLine();
					writer.write(getIndent(indent));
				}
				//如果遇到}或者]，缩进减4
				else if (currentChar == ']' || currentChar == '}') {
					indent -= 4;
					writer.newLine();
					writer.write(getIndent(indent));
					writer.write(currentChar);
				}
				//如果遇到逗号，缩进不变，换行
				else if (currentChar == ',') {
					writer.write(currentChar);
					writer.newLine();
					writer.write(getIndent(indent));
				}
				//如果遇到冒号，空一格
				else if (currentChar == ':') {
					writer.write(currentChar);
					writer.write(" ");
				}
				else {
					writer.write(currentChar);
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private String getIndent(int indent){
		StringBuffer result = new StringBuffer();  
        for(int i = 0; i < indent; i++)  
        {  
            result.append(" ");  
        }  
        return result.toString(); 
	}
}
