
/**
 * Minesweeper is a basically a single-player video game. The object is to clear an abstract minefield without denoting a mine. 
 * In the board, there are three types of information stored in each grid. 
 * The first is a mine graphic, which means there is a mine in that square. 
 * The second is a digit between 1 and 8 (containing 1 and 8), indicating the number of adjacent squares (typically, out of the possible 8) which contain mines. 
 * However, if the grid is at the edge of the board, only part of the adjacent squares count. 
 * The last type is a blank grid, which means there is no mines at the square and there are no mines around the grid either. 
 * For every click, the user can either clear the square, or put a flag on there to mark a mine. 
 * The user can also change the flag back to a nomal grid by right clicking again. 
 * If the user hits a mine, game will be over and the whole board will be shown. 
 * If the whole board is cleared, and at the same time the mines are marked correctly, the user will win the game. 
 * The clock will stop counting as soon the game is over or the user wins. 
 * 
 * @Jack Zhang
 * @June 16th, 2010
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class mineSweeper extends JFrame
implements MouseListener,ActionListener 
{
    protected static int size; // The size of the board
    protected static int mineNum;   // The number of mines
    protected static int[][] board= new int[100][100]; // The array which stores the whole board, consisting of a number from 0 to 9
    protected static int[][] status=new int[100][100]; // The array which tracks the status of the board (cleard, unopend, marked), consisting of a number from 1 to 2
    
    private boolean ifSetUp; // True if the board has already been set up; False if the board has not been set up
    private JPanel buttonPanel,panel; // The two panels: panel has the time counter and the number of mines/ buttonPanel has all the grids of the board
    private static JButton button[]=new JButton[1000]; // The "graphic board", contains images
    private JButton button_start=new JButton(); // The smiling face icon
    private JTextField text1=new JTextField("",3); // The number of mines
    private JTextField text2=new JTextField("",3); // Time counter
    private static Container c; // Contains components such as buttons, textfields
    private static Icon icon; // The icon that will be put on the buttons
    javax.swing.Timer timer; // Declear the timer
    
    /** 
     * Constructor. Initialize all the variables, arrays, and buttons depending on the level
     */
    public mineSweeper(int level)
    {
        super("Minesweeper");
        // Set up the size and the number of mines depends on the level user chooses
        if (level==1){
            size=9;
            mineNum=10;
        } else if (level==2){
            size=16;
            mineNum=40;
        } else {
            size=20;
            mineNum=70;
        }
        text1.setText(Integer.toString(mineNum)); // Display the correct number of mines in textfield 1
        text2.setText("0"); // Timer starts at 0
        c=getContentPane();
        buttonPanel=new JPanel();
        buttonPanel.setLayout(new GridLayout(size,size,1,1)); // Display the board with the size*size and 1 seperation distance
        // Add a mouselistener to each element of the button array
        for (int i=0;i<size*size;i++)
        {
            button[i]=new JButton();
            buttonPanel.add(button[i]);
            button[i].addMouseListener(this);
        }
        
        c.add(buttonPanel);
        panel=new JPanel();
        panel.add(text1);
        text1.setFont(new Font("Serif",Font.BOLD,16)); // Set up the font and the size of the numbers
        text1.setEditable(false); // Prevent user from changing the display numbers
        panel.add(button_start);
        panel.add(text2);
        text2.setFont(new Font("Serif",Font.BOLD,16));// Set up the font and the size of the numbers
        text2.setEditable(false); // Prevent user from changing the display numbers
        button_start.addActionListener(this); // Add a action listener to the smiling face
        icon=new ImageIcon("j14.gif"); 
        button_start.setIcon(icon);
        c.add(panel,BorderLayout.NORTH);
        timer=new javax.swing.Timer(1000,this); // Set the timer 
        // Set up the size of the form in order to fit the board
        if (level==1)
            setSize(200,280);
         else if (level==2) 
            setSize(340,430);
         else 
            setSize(400,500);
        
        setVisible(true); // Make the window visible to the user
        ifSetUp=false; // Initialise ifSetUp variable to false
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                board[i][j]=0;
                status[i][j]=0;
                icon=new ImageIcon("j10.gif"); // icon receives the image
                button[i*size+j].setIcon(icon);// Set the buttons as unopened
            }
        }
    }
    
    /**
     * Start the game by giving the user instructions in a message box
     * Let the user choose a level from level, level 2, level 3
     */
    public static void main(String[] args){
        
        JOptionPane.showMessageDialog(null,
            "Now you are able clear any grid on the board by left clicking on it. \n"+
            "After single left click, a single number may be revealed, or an area consists of numbers and blanks may be exhibited. \n"+
            "The blank grid represents there is neither a number nor mine on that square. \n"+
            "A digit indicating the number of adjacent squares (typically, out of the possible 8) which contain mines. \n"+
            "However, if the grid is at the edge of the board, only part of the adjacent squares count. \n"+
            "By using logic, you can figure out the positions of the mines and mark them.\n"+
            "You can place a flag graphic on any square believed to contain a mine by right-clicking on the square. \n"+
            "Every time you place a flag, the number on the top which indicates how many mines are left will decrease by 1. \n"+
            "Notice that you cannot mark more mines than the given number. \n"+
            "You can always change a flag back to the gray grid simply by right clicking on it again. \n"+
            "You task is to clear the whole board and mark the mines correctly. \n"+
            "Once you accidently hit a mine, game will be over and the whole board will be revealed. \n"+
            "Remember there is a time counter on the upper right corner. Therefore, be quick! Good luck.\n"+
            "Next,please choose level.",
            "Game Rules",
         JOptionPane.PLAIN_MESSAGE);               
         String s=JOptionPane.showInputDialog("Please enter 1,2 or 3\n"+
               "Level1: 9*9 board with 10 mines\n"+
               "Level2: 16*16 board with 40 mines\n"+
               "Level3: 20*20 board with 70 mines");
         if (s!=null){ // If user presses cancel, the return type is a null. Therefore, this conditional statement avoids this problem
             if (s.equals("1")) 
             {
                mineSweeper app=new mineSweeper(1); // Create a new object of the class
                app.addWindowListener(new MyWindowListener()); // Add the listener to windows
            }
            else if (s.equals("2"))
            {
                mineSweeper app=new mineSweeper(2);
                app.addWindowListener(new MyWindowListener());
            } 
            else if (s.equals("3"))
            {
                mineSweeper app=new mineSweeper(3);
                app.addWindowListener(new MyWindowListener());
            }
        }
    }
    
    /** My major method, where most of the work is done
     * Get user's click and perform the corresponding actions
     */
    public void mouseClicked(MouseEvent e)
    {
        // index: the position of user's click 
        // bestRecord: stores the least amount of time
        int index,i,j,bestRecord;  
        boolean ifWin=false; // True if the user wins the game; False if the user loses the game
        boolean ifFinished=false; // True if the user has finished the game; False if the user has not finished the game
        String mine;
        bestRecord=0; // Will be used when comparing two records
        Scanner input;
        
        // The loop that finds the position of where the user clicks
        for (int k=0;k<size*size;k++)
        {
            if (e.getSource()==button[k]) 
            {          
                index=k;// index stores the position in terms of one dimensional array
                // i,j also stores the position in terms of two dimensional array
                // change index to i and j
                i=(int)(k/size);
                j=k%size;
                // if the program has not set up, then call the mineSetUp method to set up the board
                if (ifSetUp==false){
                    mineSetUp(i,j);
                    ifSetUp=true;
                    timer.start(); // Start counting time from user's first click
                }
                if (status[i][j]!=1){ // If the grid has not been cleared yet
                    if (e.getButton() == MouseEvent.BUTTON1 && status[i][j]!=2) // If it is a left click, and the grid has not been marked
                    {
                        // If there is a mine, show the mine and finish the game
                        if (board[i][j]==9){
                            icon=new ImageIcon("j9.gif"); 
                            button[index].setIcon(icon);
                            status[i][j]=1; // Clear the grid
                            // Finish the game and the user loses
                            ifFinished=true;
                            ifWin=false;
                            printMine(); // Print the rest of the mines
                        } else {
                            boardClear(i,j); // If it is not a mine, call the boardClear method to clear the grids
                        }
                    } else if (e.getButton() == MouseEvent.BUTTON3) // If it is a right click
                    {
                        // If the grid has not been marked, mark it
                        if (status[i][j]==0 && mineNum>0){
                            status[i][j]=2;// Change the status to "Marked"
                            icon=new ImageIcon("j11.gif"); // Change the icon to a flag
                            button[index].setIcon(icon);
                            mineNum--; // decrease the number of mines by 1
                            // Change the display of number of mines. Decrease it by 1
                            mine=text1.getText(); 
                            text1.setText(Integer.toString(Integer.parseInt(mine)-1));
                        } else if (status[i][j]==2){ // If the grid has been marked, unmark it
                            status[i][j]=0; // Change the status to "Unmarked"
                            icon=new ImageIcon("j10.gif"); // Change the icon to the nomal "unopened" appearance
                            button[index].setIcon(icon);
                            mineNum++; // increase the number of mines by 1
                            // Change the display of number of mines. Increase it by 1
                            mine=text1.getText();
                            text1.setText(Integer.toString(Integer.parseInt(mine)+1));
                        }
                    }
                }
            }
        }
        if (ifFinished==false){
            // If the user has not lost the game, check if the user has won the game
            if (checkWin()) {
                ifFinished=true;
                ifWin=true;
            }
        }
        // If the user has finished the game, inform the user if he/she has won or lost, and exit the game
        if (ifFinished){
            timer.stop();// The timer stops counting
            if (ifWin==true){
                // Read the record from file
                try{
                    input=new Scanner(new File("record.txt"));
                    bestRecord=input.nextInt();
                    input.close(); 
                } catch(FileNotFoundException f){
                    bestRecord=Integer.parseInt(text2.getText());// If there is no file, set the best record to the current time
                }
                if (Integer.parseInt(text2.getText())<bestRecord){
                    bestRecord=Integer.parseInt(text2.getText()); // If time is smaller than the best record, save the time
                }
                PrintWriter out=null;
                try {
                    out = new PrintWriter(new FileWriter("record.txt"));
                } catch (Exception f) {
                }
                out.println(bestRecord); // Store the best record into the same file
                out.close(); // Close the output file
                
                // If the user wins the game, congratualates to the user
                JOptionPane.showMessageDialog(
                    null,
                    "Congratualations, you won the game!\n"+
                    "Your best record is:"+Integer.toString(bestRecord)+"s",
                    "Game Won",
                    JOptionPane.PLAIN_MESSAGE);
            } else if (ifWin==false)
                // Inform the user if he/she loses the game
                JOptionPane.showMessageDialog(
                    null,
                    "Sorry, you lost the game. Better luck next time!",
                    "Game Lost",
                    JOptionPane.PLAIN_MESSAGE);
                              
            System.exit(0); // Close the windows
        }
    }
    
    /**
     *  Although I'm not using these mouse events, I have to list them in order to let the program work
     */
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}  
    public void mouseReleased(MouseEvent e) {} 
    public void mousePressed(MouseEvent e) {} 
    
    /**
     * This will automatically run every second
     */
    public void actionPerformed(ActionEvent e)
    {
        String time;
        
        // If user does not press the smiling, then increase the time counter by 1
        if (e.getSource()!=button_start){
            time=text2.getText();
            text2.setText(Integer.toString(Integer.parseInt(time)+1));
        } else {
              String s=JOptionPane.showInputDialog("Please enter 1, 2 or 3\n"+
                    "Level1: 9*9 board with 10 mines\n"+
                    "Level2: 16*16 board with 40 mines\n"+
                    "Level3: 20*20 board with 70 mines");
              if (s!=null){
              if (s.equals("1")) 
              {
                  setVisible(false);
                  mineSweeper app=new mineSweeper(1);
                  app.addWindowListener(new MyWindowListener());
               }
               else if (s.equals("2"))
               {
                   setVisible(false);
                   mineSweeper app=new mineSweeper(2);
                   app.addWindowListener(new MyWindowListener());
               }
               else if (s.equals("3"))
               {
                   setVisible(false);
                   mineSweeper app=new mineSweeper(3);
                   app.addWindowListener(new MyWindowListener());
               }
            }
         }
    } 
   
    /** 
     *Set up all the mines in random spots
     * The methods makes sure that the spot where user clicks will not be a mine
     */
    public static void mineSetUp(int i, int j){
        int n=mineNum;
        int x,y=0; // Coordinates
        Random generator=new Random();
        
        while (n>0){
            // Generate a random position in the array
            do {
                x=generator.nextInt(size);
                y=generator.nextInt(size);
            } while(board[x][y]==9 || (x==i && y==j)); // If there is a mine in the spot already, or the position is where the user clicks, contine generating random numbers
            board[x][y]=9;// Put a mine there
            numberSetUp(x,y); // Seet up the numbers
            n--; // Decrease the number of mines by 1
        }
    }            
    
    /**
     *  Set up the numbers depending on the mines
     */
    public static void numberSetUp(int x, int y){
        // Check every connecting grids seperately
        // If it is a vivid grid (not outside of the board), then increase its value in board array by 1, as there is a mine 
        if(x>0 && y>0 && board[x-1][y-1]!=9){
            board[x-1][y-1]++;
        }
        if(x>0 && board[x-1][y]!=9){
            board[x-1][y]++;
        }
        if(x>0 && y<size-1 && board[x-1][y+1]!=9){
            board[x-1][y+1]++;
        }
        if(y<size-1 && board[x][y+1]!=9){
            board[x][y+1]++;
        }
        if(x<size-1 && y<size-1 && board[x+1][y+1]!=9){
            board[x+1][y+1]++;
        }
        if(x<size-1 && board[x+1][y]!=9){
            board[x+1][y]++;
        }
        if(x<size-1 && y>0 && board[x+1][y-1]!=9){
            board[x+1][y-1]++;
        }
        if(y>0 && board[x][y-1]!=9){
            board[x][y-1]++;
        }
    }
    
    /** 
     * Clear numbers and connecting blank grids
     */
    public static void boardClear(int i, int j){
        if (i>=0 && i<size && j>=0 && j<size && status[i][j]==0){ // if the coordinate (i,j) is a valid point and it has not been cleared yet
            if (board[i][j]!=0){ // If it is not a blank grid
                status[i][j]=1; // Change the status to cleared
                print(i,j); // Display this grid
            } else { // If it is a blank grid
                print(i,j); // Display the blank space
                status[i][j]=1; // Change the status to cleared
                
                // Clear all eight connecting grids
                boardClear(i-1,j-1);
                boardClear(i-1,j);
                boardClear(i-1,j+1);
                boardClear(i,j+1);
                boardClear(i+1,j+1);
                boardClear(i+1,j);
                boardClear(i+1,j-1);
                boardClear(i,j-1);
            }
        }
    }

    /**
     * This method converts the board array (consisting of numbers) to the graph (consisting of images from the file)
     */
    public static void print(int i, int j){
        int index;
        index=i*size+j; // Convert the coordinates in the two dimensional array to the coordinate in a one dimensional array
  
        if (board[i][j]==1){
            icon=new ImageIcon("j1.gif"); 
            button[index].setIcon(icon);
        } else if (board[i][j]==2){
            icon=new ImageIcon("j2.gif"); 
            button[index].setIcon(icon);
        } else if (board[i][j]==3){
            icon=new ImageIcon("j3.gif");
            button[index].setIcon(icon);
        } else if (board[i][j]==4){
            icon=new ImageIcon("j4.gif"); 
            button[index].setIcon(icon);
       } else if (board[i][j]==5){
           icon=new ImageIcon("j5.gif"); 
           button[index].setIcon(icon);
        } else if (board[i][j]==6){
            icon=new ImageIcon("j6.gif"); 
            button[index].setIcon(icon);
        } else if (board[i][j]==7){
            icon=new ImageIcon("j7.gif"); 
            button[index].setIcon(icon);
        } else if (board[i][j]==8){
            icon=new ImageIcon("j8.gif"); 
            button[index].setIcon(icon);
        } else if (board[i][j]==9){
            icon=new ImageIcon("j9.gif"); 
            button[index].setIcon(icon);
        } else if (board[i][j]==0){
            icon=new ImageIcon("");
            button[index].setIcon(icon);
            button[index].setBackground(Color.gray); // If the grid is a blank space, print out gray colour
        }
    } 
    
    /**
     * Check if the user wins the game
     * Return true if the entire board are marked or cleared
     * Return false if one grid is unopened
     */
    public static boolean checkWin(){
        boolean ifWin=true;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                if (status[i][j]==0){
                    ifWin=false;
                }
            }
        }
        return ifWin;
    }
    
    /**
     * Print out all the mines and wrongly marked the mines after user loses the game.
     */
    public static void printMine(){
        int index;
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                index=i*size+j; // Convert the coordinates into a one dimensional array index
                if (status[i][j]==2 && board[i][j]!=9){ // If the grid is marked as a mine, but it is actually not a mine
                   icon=new ImageIcon("j12.gif");  // Print out the image with a X on the flag
                   button[index].setIcon(icon);
                };
                if (board[i][j]==9 && status[i][j]!=2){ // If there is a mine, but the user has not marked it.
                   icon=new ImageIcon("j9.gif"); // Display the mine on that grid
                   button[index].setIcon(icon);
                };
            }
        }
    }            
}
