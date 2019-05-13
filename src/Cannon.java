import javax.swing.*;
import java.awt.*;

/**Cannon
  * A cannon JComponent that has an adjustable aim from 90 degrees to -90 degrees and adjustable height from the ground.
  * @author Daniel Liang
  * @since 2017/05/21
  */
public class Cannon extends JComponent
{
    //instance variables
    private double height; //the height from the ground in meters
    private double angle; //the angle the cannon is aiming to fire
    private Simulator sim; //the simulator this cannon is in
    
    /**Constructs a cannon.
      * @param height The height from the ground
      * @param angle The angle of the muzzle in degrees
      */
    public Cannon(double height, double angle, Simulator sim)
    {
        super();
        this.height = height;
        this.angle = angle;
        this.sim = sim;
    }
    
    /**Overrides the method from superclass and draws a cannon based on instance variables.
      * @param g The Graphics object provided for drawing
      */
    public void paintComponent(Graphics g)
    {
        //variables for positioning of the base
        int arcW = 50;
        int arcH = 40;
        int arcX = this.sim.pixelsX(0) - arcW/2;
        int arcY = this.sim.pixelsY(this.height) - arcH/2;
        int rectX = arcX - 10;
        int rectY = arcY + arcH - 20;
        int rectW = 20 + arcW;
        int rectH = 10;
        
        //prepare to draw
        super.paintComponent(g);
        
        //draw the muzzle
        g.setColor(new Color(80, 80, 80));
        g.fillPolygon(this.muzzleXY(arcX + arcW/2, rectY, 10, 50)[0], this.muzzleXY(arcX + arcW/2, rectY, 10, 50)[1], 4);
        
        //draw the base
        g.setColor(new Color(128,128,0));
        g.fillArc(arcX, arcY, arcW, arcH, 0, 180);
        g.setColor(Color.GRAY);
        g.fill3DRect(rectX, rectY, rectW, rectH, true);
        
        //draw stilts
        g.fillRect(rectX + 5, rectY + rectH, rectH, this.sim.pixelsH(this.height));
        g.fillRect(rectX + rectW - 5 - rectH, rectY + rectH, rectH, this.sim.pixelsH(this.height));
        
    }
    
    /**Changes the angle and redraws the cannon to have its muzzle pointing in the new angle.
      * @param angle The new angle the cannon is aiming at
      */
    public void setAngle(double angle)
    {
        this.angle = angle;
        this.repaint();
    }
    
    /**Changes the height and redraws the cannon.
      * @param height The new height the cannon is at
      */
    public void setHeight(double height)
    {
        this.height = height;
        this.repaint();
    }
    
    /**Determines the positions of each corner of the muzzle.
      * @param x The x location of the rotation point
      * @param y The y position of the rotation point
      * @param width The width of the muzzle
      * @param length The length of the muzzle
      */
    private int[][] muzzleXY(int x, int y, int width, int length)
    {
        double angle = Math.toRadians(this.angle); //Math works only in radians, not degrees
        
        //x positions
        int x1 = Calculator.round(x + 0.5 * width * Math.sin(angle));
        int x2 = Calculator.round(x1 + length * Math.cos(angle));
        int x3 = Calculator.round(x2 - width * Math.sin(angle));
        int x4 = Calculator.round(x3 - length * Math.cos(angle));
        
        //y positions
        int y1 = Calculator.round(y + 0.5 * width * Math.cos(angle));
        int y2 = Calculator.round(y1 - length * Math.sin(angle));
        int y3 = Calculator.round(y2 - width * Math.cos(angle));
        int y4 = Calculator.round(y3 + length * Math.sin(angle));
        
        return new int[][]{ {x1, x2, x3, x4}, {y1, y2, y3, y4} };
    }
    
    /**Gives the height of the cannon in meters.*/
    public double getCannonHeight()
    {
        return this.height;
    }
}//end class