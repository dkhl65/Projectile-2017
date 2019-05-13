import java.awt.event.*;

/**ResetController
  * Controller for the reset button
  * @author Kush Patel
  * @since 6/14/2017
  */
public class ResetController implements ActionListener
{
    private ProjectileModel model; //The model
    
    /**Constructor for the controller.
      * @param model The model of the program.
      */
    public ResetController(ProjectileModel model)
    {  
        this.model = model;
    }
    
    /**ActionPerformed method. Calls the reset method in the model.
      */
    public void actionPerformed(ActionEvent e)
    {
        this.model.reset();
    }
}//end class