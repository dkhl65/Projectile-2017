import javax.swing.*;
import java.awt.*;

/**Target
  * A JComponent that represents a ring target on a pole. The height is adjustable.
  * @author Daniel Liang
  * @since 2017/05/25
  */
public class Target extends JComponent
{
    //instance variables
    private double x; //the horizontal distance from the cannon
    private double y; //the height from the ground
    private Simulator sim; //the simulator this target is in
    
    /**Constructs a target
      * @param x The horizontal distance of the target from the cannon in meters
      * @param y The height off the ground in meters
      */
    public Target(double x, double y, Simulator sim)
    {
        super();
        this.setLocation(x, y);
        this.sim = sim;
    }
    
    /**Overrides the method from superclass and draws a target based on instance variables.
      * @param g The Graphics object provided for drawing
      */
    public void paintComponent(Graphics g)
    {
        int width = 50; //the width of the target and pole
        
        //prepare to draw
        super.paintComponent(g);
        
        //draw the pole
        g.setColor(new Color(100, 100, 50));
        g.fillRect(this.sim.pixelsX(this.x)-width/2, this.sim.pixelsY(this.y)-width/2, width, this.sim.pixelsH(this.y)+width);
        
        //draw the ring target
        for(int i = width; i >= 10; i -= 10){
            g.setColor(((i-10)/10 % 2 == 0)?Color.RED:Color.WHITE);
            g.fillOval(this.sim.pixelsX(this.x)-i/2, this.sim.pixelsY(this.y)-i/2, i, i);
        }
    }
    
    /**Changes the location of the target.
      * @param x The new horizontal distance from the cannon in meters
      * @param y The new height off the ground in meters
      */
    public void setLocation(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
}//end class