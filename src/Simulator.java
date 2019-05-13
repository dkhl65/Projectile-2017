import javax.swing.*;
import java.awt.*;
import java.util.*;

/**Simulator
  * The virtual environment where the cannon fires a projectile, which may land on a target. There are also scales for distance measurement.
  * @author Daniel Liang
  * @since 2017/05/22
  */
public class Simulator extends JComponent
{
    //instance variables
    private Cannon cannon; //the cannon which fires a projectile
    private Target target; //the target
    private int scale; //the maximum length of the x and y scales in meters
    private int projX; //the current x position of the projectile
    private int projY; //the current y position of the projectile
    private ArrayList<Integer> prevX = new ArrayList<Integer>(); //the previous x positions of the projectile
    private ArrayList<Integer> prevY = new ArrayList<Integer>(); //the previous y positions of the projectile
    private boolean showProj = false; //whether the projectile and path is show
    private int simWidth; //the width of the simulator environment
    
    //constants
    private static final int SCALE_GAP = 50; //pixels between the scale and the edge of the screen

    /**Creates a simulator with a cannon and target at default locations.
      * @param scale The maximum length of the horizontal and vertical scales
      */
    public Simulator(int scale)
    {
        super();
        
        //set the scale size and optimal screen size
        this.simWidth = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()) - SCALE_GAP - 80;
        this.scale = scale;
        this.setPreferredSize(new Dimension(this.simWidth + SCALE_GAP, this.simWidth + SCALE_GAP));
        
        //initialize the JComponents
        this.cannon = new Cannon(0, 0, this);
        this.target = new Target(this.scale/2, 0, this);
        
        //get the projectile ready inside the cannon
        this.projX = this.pixelsX(0);
        this.projY = this.pixelsY(0);
        this.prevX.add(this.projX);
        this.prevY.add(this.projY);
    }
    
    /**Overrides the method from superclass to automatically draw the simulator.
      * @param g The Graphics object automatically provided for drawing
      */
    public void paintComponent(Graphics g)
    {
        int projSize = 10; //the diameter to the projectile
        String num; //the numbers on the scale
        
        super.paintComponent(g);
        
        //draw the background
        g.setColor(new Color(150, 230, 255));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(new Color(100, 200, 100));
        g.fillRect(0, this.pixelsY(0), this.getWidth(), SCALE_GAP);
        
        //draw the cannon and the target
        this.target.paintComponent(g);
        this.cannon.paintComponent(g);
        
        //draw the projectile and path
        if(showProj)
        {
            g.setColor(Color.BLUE);
            for(int i = 0; i < this.prevX.size()-1; i++){
                g.drawLine(this.prevX.get(i), this.prevY.get(i), 
                           this.prevX.get(i+1), this.prevY.get(i+1));
            }
            g.setColor(Color.ORANGE);
            g.fillOval(this.projX-projSize/2, this.projY-projSize/2, projSize, projSize);
        }
        
        //draw the scales
        g.setColor(Color.BLACK);
        g.drawLine(this.pixelsX(0), this.pixelsY(0), this.getWidth(), this.pixelsY(0));
        g.drawLine(this.pixelsX(0), this.pixelsY(0), this.pixelsX(0), 0);
        for(int i = 0; i <= scale; i += scale/10){
            num = ""+i;
            //scientific notation if necessary
            if(scale > 500000 && i > 0) num = num.substring(0,1) + "." + num.substring(1,2) + "e+" + (num.length()-1);
            
            //draw horizontal scale
            g.drawLine(this.pixelsX(i), this.pixelsY(0), this.pixelsX(i), this.pixelsY(0)+5);
            g.drawString(num, this.pixelsX(i)-3*num.length(), this.pixelsY(0)+18);
            
            //draw vertical scale
            g.drawLine(this.pixelsX(0), this.pixelsY(i), this.pixelsX(0)-5, this.pixelsY(i));
            g.drawString(num, this.pixelsX(0)-8-7*num.length(), this.pixelsY(i)+5);
        }
    }
    
    /**Changes the scale length.
      * @param scale The new scale in meters
      */
    public void setScale(int scale)
    {
        this.scale = scale;
    }
    
    /**Changes the height of the cannon.
      * @param height The new height of the cannon
      */
    public void setCannonHeight(double height)
    {
        this.cannon.setHeight(height);
        this.projY = this.pixelsY(height);
        this.prevY.set(0, this.projY);
        this.repaint();
    }
    
    /**Changes the angle of the cannon.
      * @param angle The new angle of the cannon
      */
    public void setCannonAngle(double angle)
    {
        this.cannon.setAngle(angle);
        this.repaint();
    }
    
    /**Changes the location of the target.
      * @param x The new horizontal distance from the cannon in meters
      * @param y The new height off the ground in meters
      */
    public void setTargetLocation(double x, double y)
    {
        this.target.setLocation(x, y);
        this.repaint();
    }
    
    /**Converts meters into pixels, depending on the scale, for the x position
      * @param meters The number of meters to convert
      */
    public int pixelsX(double meters)
    {
        return Calculator.round(meters * this.simWidth / this.scale + SCALE_GAP);
    }
    
    /**Converts meters into pixels, depending on the scale, for the y position
      * @param meters The number of meters to convert
      */
    public int pixelsY(double meters)
    {
        return Calculator.round(this.simWidth - meters * this.simWidth / this.scale);
    }
    
    /**Converts meters into pixels, depending on the scale, for the height off the ground
      * @param meters The number of meters to convert
      */
    public int pixelsH(double meters)
    {
        return Calculator.round(meters * this.simWidth / this.scale);
    }
    
    /**Makesw the projecile visible and mves it to the designated location.
      * @param x The x location of the projectile in meters
      * @param y The y location of the projectile in meters
      */
    public void moveProjectile(double x, double y)
    {
        this.showProj = true;
        this.projX = this.pixelsX(x);
        this.projY = this.pixelsY(y);
        this.prevX.add(this.projX);
        this.prevY.add(this.projY);
        this.repaint();
    }
    
    /**Clears the path, hides the projectile and returns it to the cannon position.*/
    public void reset()
    {
        this.showProj = false;
        this.projX = this.pixelsX(0);
        this.projY = this.pixelsY(this.cannon.getCannonHeight());
        this.prevX.clear();
        this.prevY.clear();
        this.prevX.add(this.projX);
        this.prevY.add(this.projY);
        this.repaint();
    }
}//end class