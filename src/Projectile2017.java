import javax.swing.*;

/**Projectile2017
  * This is the startup class for the Projectile 2017 program. This file contains the main method which will run the entire program.
  * @author Kush Patel
  * @since 2017/06/06
  */
public class Projectile2017
{
     public static void main (String [] args)
     {
          ProjectileModel model = new ProjectileModel();    //The model
          ProjectileView view = new ProjectileView(model);  //The view
          
          //Initialize the Frame
          JFrame f = new JFrame("Projectile 2017");
          f.setResizable(false);
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.setContentPane(view);
          f.pack();
          f.setVisible(true);
     }//end main
}//end class