package sourceInput;

import java.util.ArrayList;
import java.util.Scanner;

import SearchItem.SearchItem;
import grammaticalAnalysis.GrammaticalParser;
import grammaticalAnalysis.Json;
import lexicalAnalysis.*;
import prettyOutput.prettyOutput;

public class Main {
	public static void main(String[] args){
		try {
			if (args.length > 3 || args.length < 1) {
				throw new Exception("invalid parameter");
			}
			else if (args.length == 1) {
				readFile rf = new readFile();
				//简单解析
				String [] readString = rf.readFromFile(args[0]);
				//词法分析
				LexAnalysis la = new LexAnalysis(readString);
				ArrayList<ArrayList<Token>> analysisToken = la.analysis();
				//语法分析
				GrammaticalParser gramParser= new GrammaticalParser(analysisToken);
				gramParser.parse();
			}
			else if (args.length == 2) {
				if (args[0].equals("-pretty")) {
					readFile rf = new readFile();
					//简单解析
					String [] readString = rf.readFromFile(args[1]);
					//词法分析
					LexAnalysis la = new LexAnalysis(readString);
					ArrayList<ArrayList<Token>> analysisToken = la.analysis();
					//语法分析
					GrammaticalParser gramParser= new GrammaticalParser(analysisToken);
					Json jsonResult = gramParser.parse();
					//格式化输出
					prettyOutput prettyOutput = new prettyOutput(args[1], jsonResult);
					prettyOutput.output();	
				}
				else {
					throw new Exception("invalid parameter");
				}
			}
			else if (args.length == 3) {
				if (args[0].equals("-search")) {
					readFile rf = new readFile();
					//简单解析
					String [] readString = rf.readFromFile(args[1]);
					//词法分析
					LexAnalysis la = new LexAnalysis(readString);
					ArrayList<ArrayList<Token>> analysisToken = la.analysis();
					//语法分析
					GrammaticalParser gramParser= new GrammaticalParser(analysisToken);
					Json jsonResult = gramParser.parse();
					//查找
					String condition = args[2];
					SearchItem searchItem = new SearchItem(condition, jsonResult);
					searchItem.search();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			//读取源文件
//			System.out.println("Please input the path of source file:");
//			readFile rf = new readFile();
//			Scanner scanner = new Scanner(System.in);
//			String fileName = scanner.nextLine();
//			String [] readString = rf.readFromFile(fileName);
//			//词法分析
//			LexAnalysis la = new LexAnalysis(readString);
//			ArrayList<ArrayList<Token>> analysisToken = la.analysis();
//			//语法分析
//			GrammaticalParser gramParser= new GrammaticalParser(analysisToken);
//			Json jsonResult = gramParser.parse();
//			//格式化输出
//			//prettyOutput prettyOutput = new prettyOutput(fileName, jsonResult);
//			//prettyOutput.output();	
//			//查找
//			Scanner scanner1 = new Scanner(System.in);
//			String condition = scanner1.nextLine();
//			SearchItem searchItem = new SearchItem(condition, jsonResult);
//			searchItem.search();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
