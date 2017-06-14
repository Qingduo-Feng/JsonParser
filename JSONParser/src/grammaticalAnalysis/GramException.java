package grammaticalAnalysis;

public class GramException extends Exception {
	private static String defaultMessage = "GramException: Unknown exception"; 
	
	public GramException(int row, int col, String exceptionMessage){
		super(String.format("GramExcepetion: %s  position: row: %s, col: %s", exceptionMessage, row, col));
	}
	
	public GramException(){
		super(defaultMessage);
	}
	
	@Override
	public void printStackTrace(){
		super.printStackTrace();
	}
}
