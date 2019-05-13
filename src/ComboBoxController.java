import javax.swing.*;
import java.awt.event.*;

/**ComboBoxController
  * Controller for the comboBox
  * @author Kush Patel
  * @since 6/14/2017
  */
public class ComboBoxController implements ActionListener
{
    private ProjectileModel model; //The model
    private JComboBox box;         //The comboBox
    
    /**Constructor for the controller.
      * @param model The model of the program.
      * @param box The comboBox used to select different modes.
      */
    public ComboBoxController(ProjectileModel model, JComboBox box)
    {
        this.model = model;
        this.box = box;
    }
    
    /**ActionPerformed method. Determines what mode is selected. Then calls the setMode method, and sends the int value of that mode, based on the mode constant value found in the model.
      */
    public void actionPerformed(ActionEvent e)
    {
        //Determines what the user selected, and converts it to string.
        box = (JComboBox) e.getSource();
        String newSelection = (String) box.getSelectedItem();    
        
        int modeValue = 0; 
        
        //If mode one is chosen
        if(newSelection.equals("Initial Angle"))
        {
            modeValue = 1;
            this.model.setMode(modeValue);   
        }
        
        //If mode two is chosen
        else if (newSelection.equals("Initial Speed"))
        {
            modeValue = 2;
            this.model.setMode(modeValue);
        }
        
        //If mode three is chosen
        else if (newSelection.equals("Initial Angle, Initial Speed and Target Distance"))
        {
            modeValue = 3;
            this.model.setMode(modeValue);
        }
        
        //If mode four is chosen
        else if (newSelection.equals("Cannon Height"))
        {
            modeValue = 4;
            this.model.setMode(modeValue);
        }
        
        //If the default mode is chosen
        else
        {
            modeValue = 0;
            this.model.setMode(modeValue);
        }
    }
}//end class