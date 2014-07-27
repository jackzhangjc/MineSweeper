Readme File

Outline:
The basic idea of this game is that the user use logic to mark all the mines and clear all the grids correctly in the most efficient way and in the least amount of time. Firstly, I have my main method, which the program will run first. In the main method, a message box including the user's instruction will automatically pop out. Then a text book will be displayed, user can select a level from the three levels. A String will be read in and converted to an integer. Secondly, the integer is passed the mineSweeper constructer to initialise all of the variables and buttons. In other words, the board is exhibited. I also assigned a mouse listener to each button and action listener for the smiling face button and timer. The mouseClicked method receives an event. A loop searches for the whole program and find the position in the board that matches user's click. If it is the first time user clicks a bottom, start the game by setting up all the mines and numbers(call the mineSetUp method and numberSetUp method). This ensures that the user will not lose on his or her first click. Additionally, start the timer after user's first click. As the period of timer is set to 1s, it will automatically go the actionPerformed method and adds the counter by 1 in text 2. If it is not user's first click, then check whether it is a right click or left click. If it is a left click, reveal this grid and keep clearing the other grids by calling the method boardClear(i,j). However, if it is a mine, set ifFinished to true, inform the user, print out the mines using method printMine and ends the game. The boardClear method is in a recursive structure. In the boardClear method, first check if the point the method receives is a valid point. If it is, print out the grid by calling the method print. If it is a blank space, keep clearing all eight connecting points. On the other hand, if the user performs a right click, mark or unmark the grid depending on the current situation. The mineNum counter will also decrease or increase by 1. After one click, the program will always check if the game is finished. If it is not, it means that there is a possibility in which the user has cleared all the entire board and marked all the mines. Furthermore, I already know that if the user marks one mine wrong, he will hit a mine at some point in the game, and he will have lost the game. Therefore, in the checkWin method, I just use a loop to determine if the user has cleared the entire board (All the elements in status array are not 0). If it is true, the user wins the game. Store the time in record.txt file if it is less that the best record. Send a message to the screen saying that the user won the game. Finally, ends the game. The purpose of myWindowsListener is that the program will end when user hit the close bottom on the window.

Progress Log

Date: 05/30/2010	
Additions: Add mineSetUp, numberSetUp, and main methods. Board and gameboard arrays.
Changes:
Problems: 

Date: 05/31/2010
Additions: Methods: boardClear Arrays: status Variable: ifFinished 
Changes: In the main method, initialise the status array
Problems: Can¡¯t convert the numbers in the board array into String type.
Infinite recursion. When clearing the grids, I forget to change status[i][j]

Date: 06/01/2010
Additions: Methods: checkWin Variable: ifWin
Conditional statements that check if the user left clicks the grid or right clicks the square. If it is a right click, then change status[a][b] to 2.
Changes: Change the condition of receiving a suitable grid to (if status[a][b]=0 || it¡¯s a right click)
Every time the user marks a grid, decrease mineNum by 1. The reason is that the program prohibits the user from putting more flags than the number of mines. 
Problems: 

Date: 06/02/2010
Additions: Methods: printMine
Changes: Change the modifiers from public to protected
After the user hit a mine, call the method printMine
Problems: The wrongly marked mines stay the same (still have the flag), after the user hit a mine and finish the game. I use an extra conditional statement if (status[i][j]==2 && board[i][j]!=9) to fix this problem

Date: 06/12/2010
Additions: Methods: mouseClick, constructor: mineSweeper
Changes: I remove most of the things in the main method. I put them in mouseClick method.
Problems: The event e used in the parameters in the method contradicts the event e used in the try exception when opening a file. I solved the problem by using FileNotFoundException f.

Date: 06/13/2010
Additions: Method: actionPerformed(ActionEvent e)
Changes: Instead of starting the program by a level 1, I put the message box consisting of the user¡¯s instructions in the beginning of the main method. User can also choose a level to start the game. The program is more user friendly in this manner. 
Problems: The message box written in the actionPerformed method keeps popping out every second. I fix this by adding a conditional statement: (e.getSource()=button_start) , so the message will only be displayed when the user hits the smiling face.

