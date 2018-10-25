package Project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ConFo2 extends JFrame {
	private JPanel jpMain;
	private JLabel lblScore;
	CFBoard jpBoard;
	
	public int numGame = 0;
	private static int counter1 =0;
	private static int counter2=0;
	
	private Player currPlayer;
	private Player player1;
	private Player player2;
	private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	
	public static String [] JBTN_NAMES = {"1","2","3","4", "5", "6", "7"};
	public static final int NUM_JBTNS = JBTN_NAMES.length;
	
public  ConFo2() {
	
	player1 = new Player("Red", "R");
	player2 = new Player("Blue", "B");
	currPlayer = player1;
	
	jpMain = new JPanel();
	jpMain.setLayout(new BorderLayout());
	
	lblScore = new JLabel("Connect Four! First turn for "+currPlayer.getName());
	lblScore.setLayout(new BorderLayout());
	lblScore.setFont(font);
	
	jpBoard = new CFBoard();
	
	jpMain.add(BorderLayout.CENTER, jpBoard);
	jpMain.add(BorderLayout.NORTH, lblScore);
	jpMain.setBackground(Color.WHITE);

	add(jpMain);
	setSize(600,700);
	setVisible(true);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
}

private class CFBoard extends JPanel implements GameBoardInterface,
GamePlayerInterface, ActionListener{

	private JLabel board[][];
	private JButton [] jbtns;
	private final int ROWS = 6;
	private final int COLS = 7;
	private int[] cellDrop = {5,5,5,5,5,5,5};
	private String x;
	public CFBoard(){
		
		board = new JLabel[ROWS][COLS];
		setLayout(new GridLayout(ROWS+1,COLS));
		
		jbtns = new JButton[NUM_JBTNS];
		displayBoard();	
		x=JOptionPane.showInputDialog(getComponent(0), "Do you wanna play 4, 5 or 6?", "Title", JOptionPane.OK_CANCEL_OPTION);
}	
@Override
	public void displayBoard() {
		 for (int i =0; i < NUM_JBTNS; i++) {
				jbtns[i] = new JButton (JBTN_NAMES[i]);
				jbtns [i].setFont(font);
				jbtns[i].setBackground (Color.CYAN);
				jbtns[i].setForeground (Color.BLACK);
				jbtns[i].addActionListener(this);
					
				add(jbtns[i]);
	
			}
		 for (int row = 0; row < 6; row++) {
	         for (int col = 0; col<7; col++) {
	         
				board[row][col] = new JLabel("");
				board[row][col].setBackground (Color.WHITE);
				board [row][col].setOpaque(true);
				board [row][col].setFont (font);
				board [row][col].setHorizontalAlignment(NORMAL);
				board [row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
	             add(board[row][col]);
	             
	         }
		 }
	
}
@Override 
public void showScore () {
	
	if(currPlayer.getSymbol().equals("R")) {
		
		counter1= counter1+ currPlayer.getNumWins();
		counter1++;
		lblScore.setText("Red: " + counter1 + " Blue: "+ counter2);
	}else if(currPlayer.getSymbol().equals("B")) {
		counter2 = counter2+ currPlayer.getNumWins();
		counter2++;
		lblScore.setText("Red: " + counter1 + " Blue: "+ counter2);
}
}

@Override
public void actionPerformed(ActionEvent e) {
	Image img = new ImageIcon(this.getClass().getResource("/p0.png")).getImage();
	Image img2 = new ImageIcon(this.getClass().getResource("/p1.png")).getImage();

	JButton btnClicked = (JButton) e.getSource();
	btnClicked.setEnabled(true);
	for (int i=0; i< jbtns.length; i++) {
		if(jbtns[i] == btnClicked) {
			int cols=i;
			board[cellDrop[cols]][cols].setText(currPlayer.getSymbol());
			if(currPlayer.getSymbol().equals("R")) {
			board[cellDrop[cols]][cols].setIcon(new ImageIcon(img));
			}else if(currPlayer.getSymbol().equals("B")) {
			board[cellDrop[cols]][cols].setIcon(new ImageIcon(img2));
			}	
			
			cellDrop[cols]--;
		}
	}
if(isWinner()){
				showScore ();
				JOptionPane.showMessageDialog(null, "WINNER= "+currPlayer.getName());;
				playAgain();
				clearBoard();
}
else if(isFull()){
	JOptionPane.showMessageDialog(null,"IS FULL... DRAW");
	playAgain();
	clearBoard();
}
	takeTurn();
	 lblScore.setText("Turn: "+currPlayer.getName() +" | Score:Red " + counter1 + " Blue "+ counter2);
	
}
@Override
public void clearBoard() {
	for(int row=0; row<board.length; row++){
		for(int col=0; col<board[row].length; col++){
			board[row][col].setText("");
			board[row][col].setIcon(null);
		}
	}for(int row=0; row<cellDrop.length; row++) {
	cellDrop [row]=5;
}
}
@Override
public boolean isEmpty() {
	return false;
}
@Override
public boolean isFull() {
	for(int row=0; row < board.length; row++){
		for(int col=0; col< board[row].length; col++){
				String cellContent = board[row][col].getText().trim();
				if(cellContent.isEmpty()){
					return false;
				}
		}
}
	return true;
}
@Override
public boolean isWinner() {
	if(isWinnerInRow() || isWinnerInCol() || isWinnerInMainDiag() ||
	isWinnerInSecDiag() ){
		return true;
	}
	return false;
}

private boolean isWinnerInSecDiag() {
	if (x.equals("4")) {
	for(int startRow =3; startRow<6; startRow++) {
	int numMatches = 0;
	int row = startRow;
	int col = 0;
	String symbol = currPlayer.getSymbol();
	while (row >=0 && col <7) {
		if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
			numMatches++;
		if(numMatches == 4){
			
		return true;
	}
	}else {
		numMatches=0;
	  }
		
	row--;
	col++;

}
	}
	for(int startCol =1; startCol<4; startCol++) {
		int numMatches= 0;
		int row = 5;
		int col =startCol;
		
		
		String symbol = currPlayer.getSymbol();
		while (row >=0 && col <7) {
			if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
				numMatches++;
			if(numMatches == 4){
			return true;
			}
		}else {
			numMatches=0;
		
		}row--;
		col++;
	
	}
	}
	}
// connect 5!	*******************************************************
	else if (x.equals("5") ){
		for(int startRow =4; startRow<6; startRow++) {
			int numMatches = 0;
			int row = startRow;
			int col = 0;
			String symbol = currPlayer.getSymbol();
			while (row >=0 && col <7) {
				if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					numMatches++;
				if(numMatches == 5){
					
				return true;
			}
			}else {
				numMatches=0;
			  }
				
			row--;
			col++;

		}
			}
			for(int startCol =1; startCol<3; startCol++) {
				int numMatches= 0;
				int row = 5;
				int col =startCol;
				
				
				String symbol = currPlayer.getSymbol();
				while (row >=0 && col <7) {
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatches++;
					if(numMatches == 5){
					return true;
					}
				}else {
					numMatches=0;
				
				}row--;
				col++;
			
			}
			}
	}
	// CONNECT 6!*********************************************
	else if (x.equals("6")) {
		for(int startRow =5; startRow<6; startRow++) {
			int numMatches = 0;
			int row = startRow;
			int col = 0;
			String symbol = currPlayer.getSymbol();
			while (row >=0 && col <7) {
				if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					numMatches++;
				if(numMatches == 6){
					
				return true;
			}
			}else {
				numMatches=0;
			  }
				
			row--;
			col++;

		}
			}
			for(int startCol =1; startCol<2; startCol++) {
				int numMatches= 0;
				int row = 5;
				int col =startCol;
				
				
				String symbol = currPlayer.getSymbol();
				while (row >=0 && col <7) {
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatches++;
					if(numMatches == 6){
					return true;
					}
				}else {
					numMatches=0;
				
				}row--;
				col++;
			
			}
			}
	}
	return false;
}



private boolean isWinnerInMainDiag() {
	
	if(x.equals("4")) {
	for(int startRow =0; startRow<3; startRow++) {
		int numMatches = 0;
		int row = startRow;
		int col = 0;
		String symbol = currPlayer.getSymbol();
		while (row <3 && col>7) {
			if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
				numMatches++;
			if(numMatches == 4){
			return true;
			}
		}else {
			numMatches=0;
		}
		row++;
		col++;
		
	}
	}
		for(int startCol =1; startCol<4; startCol++) {
			int numMatches= 0;
			int row = 0;
			int col =startCol;
			String symbol = currPlayer.getSymbol();
			while (row<6 && col <7) {
				if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					numMatches++;
				if(numMatches == 4){
				return true;
				}
				
			}else {
				numMatches=0;
			}
			row++;
			col++;
		
		}
		}
	}
	// CONNECT 5 **************************************************
	else if (x.equals("5") ){
		for(int startRow =0; startRow<2; startRow++) {
			int numMatches = 0;
			int row = startRow;
			int col = 0;
			String symbol = currPlayer.getSymbol();
			while (row <2 && col>7) {
				if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					numMatches++;
				if(numMatches == 5){
				return true;
				}
			}else {
				numMatches=0;
			}
			row++;
			col++;
			
		}
		}
			for(int startCol =1; startCol<3; startCol++) {
				int numMatches= 0;
				int row = 0;
				int col =startCol;
				String symbol = currPlayer.getSymbol();
				while (row<6 && col <7) {
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatches++;
					if(numMatches == 5){
					return true;
					}
					
				}else {
					numMatches=0;
				}
				row++;
				col++;
			
			}
			}
	}
	//CONNECT 6***************************************************
	else if(x.equals("6")) {
		for(int startRow =0; startRow<1; startRow++) {
			int numMatches = 0;
			int row = startRow;
			int col = 0;
			String symbol = currPlayer.getSymbol();
			while (row <1 && col>7) {
				if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					numMatches++;
				if(numMatches == 6){
				return true;
				}
			}else {
				numMatches=0;
			}
			row++;
			col++;
			
		}
		}
			for(int startCol =1; startCol<2; startCol++) {
				int numMatches= 0;
				int row = 0;
				int col =startCol;
				String symbol = currPlayer.getSymbol();
				while (row<6 && col <7) {
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatches++;
					if(numMatches == 6){
					return true;
					}
					
				}else {
					numMatches=0;
				}
				row++;
				col++;
			
			}
			}
	}
	
		return false;
	}
private boolean isWinnerInCol() {
	String symbol = currPlayer.getSymbol();
	if (x.equals("4")) {
	for(int col=0; col< 7; col++){
	int numMatchesInCol = 0; 
		for(int row=0; row<6; row++){
			if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
				numMatchesInCol++;
				if(numMatchesInCol == 4){
					return true;
}
			
			}else { numMatchesInCol =0;
}
		}
	}
	}
	//CONECT 5*************************************
	else if (x.equals("5")) {
		for(int col=0; col< 7; col++){
			int numMatchesInCol = 0; 
				for(int row=0; row<6; row++){
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInCol++;
						if(numMatchesInCol == 5){
							return true;
		}
					
					}else { numMatchesInCol =0;
		}
		}
		}
	}
	
	//CONECT 6*************************************
	
	else if(x.equals("6")) {
		for(int col=0; col< 7; col++){
			int numMatchesInCol = 0; 
				for(int row=0; row<6; row++){
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInCol++;
						if(numMatchesInCol == 6){
							return true;
		}
					
					}else { numMatchesInCol =0;
		}
		}
		}
		
	}
	return false;
}
public boolean isWinnerInRow(){
	String symbol = currPlayer.getSymbol();
	if (x.equals("4")) {
	for(int row=0; row < 6; row++){
		int numMatchesInRow = 0; //reset on the next row
		for(int col=0; col< 7; col++){
			if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
				numMatchesInRow++;
				if(numMatchesInRow == 4){
					return true;
				}
				}else { numMatchesInRow =0;
}
}
}	
}
	else if (x.equals("5")) {
		for(int row=0; row < 6; row++){
			int numMatchesInRow = 0; //reset on the next row
			for(int col=0; col< 7; col++){
				if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					numMatchesInRow++;
					if(numMatchesInRow == 5){
						return true;
					}
					}else { numMatchesInRow =0;
	}
	}
	}	
	}
	else if(x.equals("6")) {
		for(int row=0; row < 6; row++){
			int numMatchesInRow = 0; //reset on the next row
			for(int col=0; col< 7; col++){
				if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
					numMatchesInRow++;
					if(numMatchesInRow == 6){
						return true;
					}
					}else { numMatchesInRow =0;
	}
	}
	}	
	}
return false;
}


@Override
public void playAgain() {
int yesno= JOptionPane.showConfirmDialog(null, "play again?");
if(yesno== JOptionPane.NO_OPTION) {
System.exit(0);
}
}
@Override
public void takeTurn() {
	if(currPlayer.equals(player1)){
		currPlayer = player2;
	}
	else{
		currPlayer = player1;
	}
	}
}

} 

