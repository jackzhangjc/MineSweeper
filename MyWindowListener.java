import java.awt.*;
import java.awt.event.*;
class MyWindowListener extends WindowAdapter 
{
    public void windowClosing(WindowEvent e)
    {
        System.exit(1); // When the user close the window, the program will end
    }
}