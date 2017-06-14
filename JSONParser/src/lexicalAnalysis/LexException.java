package lexicalAnalysis;

public class LexException extends Exception {
	private static String defaultMessage = "LexException: Unknown exception"; 
	
	public LexException(int row, int col, String exceptionMessage){
		super(String.format("LexExcepetion: %s  position: row: %s, col: %s", exceptionMessage, row, col));
	}
	
	public LexException(){
		super(defaultMessage);
	}
	
	@Override
	public void printStackTrace(){
		super.printStackTrace();
	}
}
