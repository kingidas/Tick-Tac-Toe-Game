/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.Timer;
/**
 *
 * @author 
 */
public class Chess {
    Move playerPosition;
    Move comPosition;
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    
    private JTextField rowInput=new JTextField();
    private JTextField colInput=new JTextField();
    private final JLabel message = new JLabel(
            "Capture Game");
    private static final String COLS = "12345678";
    
    Chess() {
        initializeGui();
    }
    
    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("Enter Row and Column to Move")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JLabel("Row"));
        tools.add(rowInput); // TODO - add functionality!
        tools.add(new JLabel("Column"));
        tools.add(colInput);
        JButton b1 = new JButton("Move");
        b1.addActionListener(new java.awt.event.ActionListener() {
            
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                String x=rowInput.getText();
                String y=colInput.getText();
                if(!x.isEmpty() && !y.isEmpty())
                {
                    try {
                        int xInt= Integer.parseInt(x);
                        int yInt= Integer.parseInt(y);
                        xInt=xInt-1;yInt=yInt-1;
                        
                        if(xInt>=0 && xInt<8 && yInt>=0 && yInt<8 && chessBoardSquares[xInt][yInt].getBackground() != Color.yellow
                           && checkPath(playerPosition.row, playerPosition.col, xInt, yInt) && (playerPosition.row == xInt || playerPosition.col == yInt || (playerPosition.row -xInt == playerPosition.col -yInt || playerPosition.row -xInt == -1 * (playerPosition.col -yInt)
                            || -1 * (playerPosition.row-xInt) == playerPosition.col -yInt))
                                ){
                            System.out.print(x);
                            System.out.print(y);
                            chessBoardSquares[xInt][yInt].setBackground(Color.orange);
                            chessBoardSquares[xInt][yInt].setText("X");
                            chessBoardSquares[playerPosition.row][playerPosition.col].setText("*");
                            playerPosition.setCol(yInt);
                            playerPosition.setRow(xInt);
                            
                            //perfrom computer move
                            long startTime = System.currentTimeMillis();
                            Move m =getComputerMove();
                            long endTime   = System.currentTimeMillis();
                            long totalTime = endTime - startTime;
                            if(m!=null && totalTime < 60000 ){
                                chessBoardSquares[comPosition.row][comPosition.col].setText("*");
                                comPosition.setCol(m.col);
                                comPosition.setRow(m.row);
                                chessBoardSquares[comPosition.row][comPosition.col].setBackground(Color.orange);
                                chessBoardSquares[comPosition.row][comPosition.col].setText("O");
                            }
                            else if (totalTime > 60000)
                            {
                                endGame("Player wins, computer ran out of time");
                            }
                            else
                            {
                                endGame("Player wins, computer ran out of moves");
                            }
                            
                            //end
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Input must be inbetween 1 and 8 and a Valid move");
                        }
                    }
                    catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(null, "Wrong Input Format");
                    }
                    
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Enter row or column");
                }
                
            }
        });
        tools.add(b1);
        tools.addSeparator();
        tools.add(message);
        
        gui.add(new JLabel("Row"), BorderLayout.LINE_START);
        
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        
        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                
               b.setFont(new Font("Courier New", Font.BOLD, 18));
                if(i==0 && j==0)
                {
                       b.setText("X");  
                       playerPosition= new Move(0, 0);
                       
                }
                else if(i==7 && j==7)
                {
                       b.setText("O");  
                       comPosition= new Move(7,7);
                       
                }else {
                       b.setText("-"); 
                }
               
                if ((j % 2 == 1 && i % 2 == 1)
                        //) {
                        || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(Color.white);
                } else {
                    b.setBackground(Color.blue);
                }
                chessBoardSquares[j][i] = b;
            }
        }
        chessBoardSquares[0][0].setBackground(Color.orange);
        chessBoardSquares[7][7].setBackground(Color.orange);

        //fill the chess board
        chessBoard.add(new JLabel("L Daly"));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[ii][jj]);
                }
            }
        }
    }
    
    public final JComponent getChessBoard() {
        return chessBoard;
    }
    
    public final JComponent getGui() {
        return gui;
    }
    
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            
            @Override
            public void run() {
                Chess cb =
                        new Chess();
                
                JFrame f = new JFrame("Capture Game");
                f.add(cb.getGui());
                // in order display the components within it
                f.pack();
                f.setSize(600,600);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                
                // ensures the frame is the minimum size it needs to be
                
                // ensures the minimum size is enforced.
             //   f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
    public Move getComputerMove()    
    {
        Vector<MoveAndScore> availableMoves = getAllMoves(comPosition.row, comPosition.col,new Vector<Move>(),0);
        return getMax(availableMoves);
        
    }
    public Move getMax(Vector<MoveAndScore> availableMoves)
    {
        Move best= null;
        MoveAndScore cur = null;
        int score =0;
        Iterator i = availableMoves.iterator();
        while(i.hasNext()){
             cur = (MoveAndScore)i.next();
            if (score < cur.getScore())
                best = cur.getPoint();
        }
        return best;
    }
    public Vector<MoveAndScore> getAllMoves(int row, int col, Vector<Move> currently_moved, int depth)
    {
        
        Vector<Move> playerMoves = new Vector<Move>();
        int playerRow = playerPosition.row;
        int playerCOl = playerPosition.col;
         for(int x=0;x < 8;x++)
            for (int y=0;y < 8; y++){
                Move m= new Move(x,y);
                if(chessBoardSquares[x][y].getBackground()!=Color.orange && !(x == playerRow && y == playerCOl ) && !findMove(currently_moved,m) )
                    if((playerRow == x || playerCOl == y || (playerRow-x == playerCOl -y || playerRow-x == -1 * (playerCOl -y)
                            || -1 * (playerRow-x) == playerCOl -y)) && checkPath(playerRow, playerCOl,x,y) )
                        playerMoves.add(m);
            }
         if(playerMoves.size() == 0 && currently_moved.size() ==0 )
             endGame("Computer wins");
         
         Iterator i = playerMoves.iterator();
        Vector<MoveAndScore> moves= new Vector<MoveAndScore>();
        for(int x=0;x < 8;x++)
            for (int y=0;y < 8; y++){
                Move m = new Move(x, y);
                if(chessBoardSquares[x][y].getBackground()!=Color.orange && !(x == row && y == col) && !findMove(currently_moved,m) )
                    if((row == x || col == y || (row-x == col -y || row-x == -1 * (col -y)  
                            || -1* (row-x) == col -y)) && checkPath(row, col, x, y))
                        moves.add(new MoveAndScore(getMoveScore(x,y,i,currently_moved,depth),m));
            }
                                       
        if(moves.size() == 0  && currently_moved.size() ==0 )
             endGame("Player wins");
        
        if(depth == 4)
            currently_moved = new Vector<>();
        return moves;
        
    }
    
    public int getMoveScore(int row, int col,Iterator i, Vector<Move> curr_moves, int depth){
        int max =0 ;
        curr_moves.add(new Move(row, col));
        if(depth != 4){
         Vector<MoveAndScore> moves_and_score = getAllMoves(row, col, curr_moves,depth +1 );
         Iterator i1 = moves_and_score.iterator();
         
         while(i1.hasNext()){
             MoveAndScore mNs = (MoveAndScore)i1.next();
             if (mNs.getScore() > max)
                 max = mNs.getScore();
             
         }
        }
        
       while(i.hasNext())
       {
          Move m = (Move)i.next();
          if(m.col == col || m.row == row || (m.row - row == m.col - col))
              return 2 + max;
          
       }
        return 1 + max;
    }
    public void endGame(String s)
    {
      message.setText(s);
    }
            
    public boolean findMove(Vector<Move> currently_moved,Move m){
        Iterator i = currently_moved.iterator();
        while(i.hasNext())
       {
          Move m_p = (Move)i.next();
          if(m.col == m_p.col && m_p.row == m.row)
              return true;
          
       }
        return false;
    }
    public boolean checkPath(int row, int col, int mRow, int mCol){
        
       if(row == mRow && col!=mCol){
        if(col < mCol){
            for(col++;col < mCol; col++)
                if(chessBoardSquares[row][col].getBackground() == Color.orange)
                    return false;
        }
        else{
            for(col--;col > mCol; col--)
                if(chessBoardSquares[row][col].getBackground() == Color.orange)
                    return false;
                    
       }
       }
       else if(col == mCol && row != mRow){
        if(mRow > row){
            for(row++;row < mRow; row++)
                if(chessBoardSquares[row][col].getBackground() == Color.orange)
                    return false;
        }
        else{
            for(row--;row > mRow; row--)
                if(chessBoardSquares[row][col].getBackground() == Color.orange)
                    return false;
        }
       }
       else if(col - mCol == row -mRow){
         if(col - mCol > 0 ){
         for(col--,row--;col > mCol; col--,row--)
            if(chessBoardSquares[row][col].getBackground() == Color.orange)
                return false;
         }
         else{
            for(col++,row++;col < mCol; col++,row++)
                if(chessBoardSquares[row][col].getBackground() == Color.orange)
                    return false;
         }
       }
       else if(-1*(col - mCol) == row - mRow){
           for(col++,row--;row > mRow; col++,row--)
            if(chessBoardSquares[row][col].getBackground() == Color.orange)
                return false;
       }
       else if(col - mCol == -1*(row - mRow)){
           for(col--,row++;col > mCol; col--,row++)
            if(chessBoardSquares[row][col].getBackground() == Color.orange)
                return false;
       }
       
        return true;
    }
}
