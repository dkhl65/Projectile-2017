import javax.swing.*;
import java.awt.event.*;
 
/**MenuController
  * Controller that sets all the inputs
  * @author Kush Patel, Daniel Liang
  * @since 6/7/2017
  */
public class MenuController implements ActionListener
{
  //Instance Variables
  private ProjectileModel model; // The model
  
  //JTextFields 
  private JTextField initialVelocity;
  private JTextField initialHeight;
  private JTextField initialAngle;
  private JTextField targetX;
  private JTextField targetY;
  private JTextField finalVelocity;
  private JTextField finalAngle;
  
  /**Constructor for the controller.
    * @param model The model of the program.
    * @param initialVelocity The initial Velocity of the projectile
    * @param initialHeight The cannon height
    * @param initialAngle The initial angle of the projectile
    * @param targetX The horizontal target distance
    * @param targetY The vertical target distance
    * @param finalVelocity The final velocity of the projectile
    * @param finalAngle The final angle of the projectile
    */
  public MenuController(ProjectileModel model, JTextField initialVelocity, JTextField initialHeight, JTextField initialAngle,
                        JTextField targetX, JTextField targetY, JTextField finalVelocity, JTextField finalAngle)
  {
    this.model = model;
    this.initialVelocity = initialVelocity;
    this.initialHeight = initialHeight;
    this.initialAngle = initialAngle;
    this.targetX = targetX;
    this.targetY = targetY;
    this.finalVelocity = finalVelocity;
    this.finalAngle = finalAngle;
  }
  
  /**ActionPerformed method. Gets the input from the user and uses the set methods from the model to set the values for the instance variables.
    */
  public void actionPerformed(ActionEvent e)
  {
    //Variables to store the values of each individual textfield
    double cannonValue = 0;
    double speedIValue = 0;
    double angleIValue = 0;
    double targetXValue = 0;
    double targetYValue = 0;
    double speedFValue = 0;
    double angleFValue = 0;
    
    boolean numError = true;       //Used to check if user entered all numbers
    boolean negNumberError = true; //Used to check if user enters all positive numbers
    
    //Allows the user to pause the simulator, if the animation has already started.
    if (this.model.isAnimating())  
    {
      this.model.pause();
    }
    
    //Set the values of the instance variables depending on what mode is chosen. Also show error messages if there are errors.
    else
    {
      try 
      {
        //Set the initial height if any of these modes are chosen. Uses the mode constants found in the model to determine which mode is chosen.
        if(this.model.getMode() == ProjectileModel.DEFAULT || this.model.getMode() == ProjectileModel.INIT_ANGLE || 
           this.model.getMode() == ProjectileModel.INIT_SPEED || this.model.getMode() == ProjectileModel.INIT_VELOCITY)
        {
          //Setting the Initial Height
          cannonValue = Double.parseDouble(initialHeight.getText());
          
          //Check if initialHeight is a negative number. If it is, thrown exception.
          if (cannonValue < 0) 
          {
            throw new IllegalArgumentException();
          }
          else
          {
            this.model.setCannonHeight(cannonValue); //Set cannonHeight
          }
        }
        
        //Set the initial velocity, targetX, and targetY if any of these modes are chosen.
        if(this.model.getMode() == ProjectileModel.DEFAULT || this.model.getMode() == ProjectileModel.INIT_ANGLE ||
           this.model.getMode() == ProjectileModel.HEIGHT)
        {
          //Setting the Initial Velocity
          speedIValue = Double.parseDouble(initialVelocity.getText());
          
          //Check if initial velocity is a negative number. If it is, throw exception.
          if (speedIValue < 0) 
          {
            throw new IllegalArgumentException(); 
          }
          else
          {
            this.model.setInitialSpeed(speedIValue); //Set initial speed 
          }
          
          //Setting Target X 
          targetXValue = Double.parseDouble(targetX.getText());
          
          //Check if targetX is a negative number. If it is, throw exception.
          if (targetXValue < 0) 
          {
            throw new IllegalArgumentException();
          }
          else
          {
            this.model.setTargetX(targetXValue); //Set targetX
          }
          
          //Setting Target Y
          targetYValue = Double.parseDouble(targetY.getText());
          
          //Check if targetY is a negative number. If it is, throw exception.
          if (targetYValue < 0) 
          {
            throw new IllegalArgumentException();
          }
          else
          {
            this.model.setTargetY(targetYValue); //Set targetY
          }
        }
        
        //Set the final velocity, final angle and targetY if mode 3 is chosen.
        if(this.model.getMode() == ProjectileModel.INIT_VELOCITY)
        {
          //Setting the Final Velocity
          speedFValue = Double.parseDouble(finalVelocity.getText());
          
          //Check if finalVelocity is a negative number. If it is, throw exception.
          if (speedFValue < 0) 
          {
            throw new IllegalArgumentException();
          }
          else
          {
            this.model.setFinalSpeed(speedFValue); //Set finalVelocity
          }
          
          //Setting the Final Angle
          angleFValue = Double.parseDouble(finalAngle.getText());
          
          
          if (angleFValue < 0) //Check if finalAngle is a negative number. If it is, throw exception.
          {
            throw new IllegalArgumentException();
          }
          else if(angleFValue >= 90)  //Check if finalAngle is more than or equal to 90. If it is, show error message.
          {
            JOptionPane.showMessageDialog(this.finalAngle, "Final Angle must be >= 0 and < 90.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          else
          {
            this.model.setFinalAngle(angleFValue); //Set finalAngle
          }
          
          //Setting Target Y
          targetYValue = Double.parseDouble(targetY.getText());
          
          //Check if targetY is a negative number. If it is, throw exception.
          if (targetYValue < 0) 
          {
            throw new IllegalArgumentException();
          }
          else
          {
            this.model.setTargetY(targetYValue); //Set targetY
          }
        }
        
        //Set the initial angle, targetX, and targetY if any of these modes are chosen
        if(this.model.getMode() == ProjectileModel.DEFAULT || this.model.getMode() == ProjectileModel.INIT_SPEED || this.model.getMode() == ProjectileModel.HEIGHT)
        {
          //Setting the Initial Angle
          angleIValue = Double.parseDouble(initialAngle.getText());
          
          //Check if initialAngle is within the required range. If not, show error message.
          if (angleIValue < -90 || angleIValue > 90) 
          {
            JOptionPane.showMessageDialog(this.initialAngle, "Initial Angle must be between -90 and 90.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
          }
          else
          {
            this.model.setInitialAngle(angleIValue); //Set initialAngle
          }
          
          //Setting Target X 
          targetXValue = Double.parseDouble(targetX.getText());
          
          //Check if targetX is a negative number. If it is, throw exception.
          if (targetXValue < 0) 
          {
            throw new IllegalArgumentException();
          }
          else
          {
            this.model.setTargetX(targetXValue); //Set targetX
          }
          
          //Setting Target Y
          targetYValue = Double.parseDouble(targetY.getText());
          
          //Check if targetY is a negative number. If it is, throw exception.
          if (targetYValue < 0) 
          {
            throw new IllegalArgumentException();
          }
          else
          {
            this.model.setTargetY(targetYValue);  //Set targetY
          }
        }
        
        //Check if one of the entered values is NaN or out of Double's range
        if(Calculator.oneIsNaN(cannonValue, speedIValue, angleIValue, targetXValue, targetYValue, speedFValue, angleFValue))
        {
          JOptionPane.showMessageDialog((JButton)(e.getSource()), "The simulator cannot show the projectile 10^9 m or further away from the origin.", "Out of Range", JOptionPane.ERROR_MESSAGE);
          return;
        }
      }//end try
      
      catch (NumberFormatException ex)   //Thrown if user doesn't enter a number.
      {
        numError = false;  
      }
      catch (IllegalArgumentException ex) //Thrown if user enters a negative number.
      {
        negNumberError = false;
      }
      
      //Begin animation if there are no errors. If there are errors, show the error messages.
      if (numError == true && negNumberError == true)
      {
        new Thread(this.model).start();
      }
      
      //Show error messages
      else if(numError == false) //If user didn't enter a number
      {
        JOptionPane.showMessageDialog((JButton)(e.getSource()), "At least one field is missing a number.", "Input Error", JOptionPane.ERROR_MESSAGE);  
      }
      
      else  //If user didn't enter a valid initial angle.
      {
        JOptionPane.showMessageDialog((JButton)(e.getSource()), "All fields except Initial Angle must have positive values.", "Input Error", JOptionPane.ERROR_MESSAGE);  
      }
    }//end try
  }//end else
}//end class