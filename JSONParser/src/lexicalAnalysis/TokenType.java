package lexicalAnalysis;

/*
 * 对词法分析之后得到的token序列的不同类型进行定义
 * 分别是字符串，整数，科学计数法，浮点型，布尔型，数组开始([)，数组结束(])，json开始({),json结束(})，逗号，冒号，分号，引号，空值
 */
public enum TokenType {
	STRING, NUMBER, SCENTIFIC, FLOAT, TRUE, FALSE, ARRAY_START, ARRAY_END, OBJECT_START, OBJECT_END, COMMA, COLON, SEMICOLON, QUATATIONM, NULL 
}
