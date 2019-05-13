import javax.swing.*;

/**ProjectileModel
  * Receives data from the user, calculates the path of the projectile and feeds data to the view for animation.
  * @author Daniel Liang, Kush Patel
  * @since 2017/05/26
  */
public class ProjectileModel implements Runnable
{
    //instance variables
    private ProjectileView view; //the gui which it communicates with
    
    //variables relating to the animation
    private double targetX;
    private double targetY;
    private double cannonHeight;
    private double projectileX;
    private double projectileY;
    private double xSpeed;
    private double ySpeed;
    private double currentSpeed;
    private double currentAngle;
    private double initialSpeed;
    private double initialAngle;
    private double finalAngle;
    private double finalSpeed;
    private double time;
    private int scale = 52; //the appropriate scale which to set the screen
    private int mode; //the mode, which decides which varibles to solve for
    private boolean animating = false; //tells the view to perform the animation
    private boolean resetted = false; //tells the view to reset
    private boolean paused = false; //true if animation is to be paused
    
    //constants for mode
    public static int DEFAULT = 0;
    public static int INIT_ANGLE = 1;
    public static int INIT_SPEED = 2;
    public static int INIT_VELOCITY = 3;
    public static int HEIGHT = 4;
    
    /**Constructs the model*/
    public ProjectileModel()
    {
        this.mode = DEFAULT; //default mode
    }
    
    /**Sets the view for input and animation.
      * @param view The gui to communicate with
      */
    public void setGUI(ProjectileView view)
    {
        this.view = view;
    }
    
    /**Sets the x location of the target.
      * @param x The horizontal distance from the cannon in meters
      */
    public void setTargetX(double x)
    {
        this.targetX = x;
    }
    
    /**Sets the y location of the target. By Kush Patel.
      * @param y The vertical distance from the cannon in meters
      */
    public void setTargetY(double y)
    {
        this.targetY = y;
    }
    
    /**Sets the height of the cannon. By Kush Patel.
      * @param height The height of the cannon in meters
      */
    public void setCannonHeight(double height)
    {
        this.cannonHeight = height;
    }
    
    /**Sets the mode of the program. By Kush Patel and Daniel Liang.
      */
    public void setMode(int mode)
    {
        this.mode = mode;
        this.view.update();
    }
    
    /**Sets the initial velocity of the projectile. By Kush Patel.
      * @param initialSpeed The initial velocity of the projectile in m/s.
      */
    public void setInitialSpeed(double initialSpeed)
    {
        this.initialSpeed = initialSpeed;
    }
    
    /**Sets the initial angle of the projectile. By Kush Patel.
      * @param initialAngle The initial angle of the projectile in degrees
      */
    public void setInitialAngle(double initialAngle)
    {
        this.initialAngle = initialAngle;
    }
    
    /**Sets the final angle of the projectile. By Kush Patel.
      * @param finalAngle The final angle of the projectile in degrees.
      */
    public void setFinalAngle(double finalAngle)
    {
        this.finalAngle = finalAngle;
    }
    
    /**Sets the final speed of the projectile. By Kush Patel.
      * @param finalSpeed The final velocity of the projectile in m/s.
      */
    public void setFinalSpeed(double finalSpeed)
    {
        this.finalSpeed = finalSpeed;
    }
    
    /**Gets the x location of the target in meters. By Kush Patel.*/
    public double getTargetX()
    {
        return this.targetX;
    }
    
    /**Gets the y location of the target in meters. By Kush Patel.*/
    public double getTargetY()
    {
        return this.targetY;
    }
    
    /**Gets the height of the cannon in meters. By Kush Patel.*/
    public double getCannonHeight()
    {
        return this.cannonHeight;
    }
    
    /**Gets the x location of the projectile. By Kush Patel.*/
    public double getProjectileX()
    {
        return this.projectileX;
    }
    
    /**Gets the y location of the projectile. By Kush Patel.*/
    public double getProjectileY()
    {
        return this.projectileY;
    }
    
    /**Gets the mode of the program. By Kush Patel.*/
    public int getMode()
    {
        return this.mode;
    }
    
    /**Gets the initial angle of the projectile. By Kush Patel.*/
    public double getInitialAngle()
    {
        return this.initialAngle;
    }
    
    /**Gets the initial speed of the projectile. By Kush Patel.*/
    public double getInitialSpeed()
    {
        return this.initialSpeed;
    }
    
    /**Gets the final velocity of the projectile. By Kush Patel.*/
    public double getFinalSpeed()
    {
        return this.finalSpeed;
    }
    
    /**Gets the final angle of the projectile. By Kush Patel.*/
    public double getFinalAngle()
    {
        return this.finalAngle;
    }
    
    /**Gets the time in seconds. By Kush Patel.*/
    public double getTime()
    {
        return this.time;
    }
    
    /**Gets the current speed of the projectile. By Kush Patel.*/
    public double getCurrentSpeed()
    {
        return this.currentSpeed;
    }
    
    /**Gets the current angle of the projectile. By Kush Patel.*/
    public double getCurrentAngle()
    {
        return this.currentAngle;
    }
    
    /**Gets the horizontal speed of the projectile. By Kush Patel.*/
    public double getXSpeed()
    {
        return this.xSpeed;
    }
    
    /**Gets the current vertical speed of the projectile. By Kush Patel.*/
    public double getCurrentYSpeed()
    {
        return this.ySpeed;
    }
    
    /**Tells if the animation is currently underway or not. By Daniel Liang.
      * @returns true if the model is feeding animation data
      */
    public boolean isAnimating()
    {
        return this.animating;
    }
    
    /**Tells if everything is being reset or not. By Daniel Liang.
      * @returns true if the model is feeding animation data
      */
    public boolean isResetted()
    {
        return this.resetted;
    }
    
    /**Gets the appropriate scale size for the simulator. By Daniel Liang.*/
    public int getScale()
    {
        return this.scale;
    }
    
    /**Pauses the current thread. By Daniel Liang.
      * @param ms The number of milliseconds to delay
      */
    private static void delay(long ms)
    {
        try{
            Thread.currentThread().sleep(ms);
        }catch(InterruptedException ex){}
    }
    
    /**Pauses or continues the animation. By Daniel Liang.*/
    public void pause()
    {
        this.paused = !this.paused;
    }
    
    /**Performs the animation, feeding data to the View. By Daniel Liang.*/
    public void run()
    {
        double endTime; //the time the projectile must take to reach the target or ground
        double frameRate = 100.0; //the frame rate of the animation
        double lag = 0.9; //correction factor for latency
        boolean force = false; //true if the projectile is to be forced to the correct location due to rounding
        
        this.animating = true; //for View to know that animation is about to start
        
        //solve for unknowns
        if(this.mode == INIT_ANGLE) this.initialAngle = Calculator.initialAngle(this.initialSpeed, this.targetX, this.targetY, this.cannonHeight);
        else if(this.mode == INIT_SPEED) this.initialSpeed = Calculator.initialSpeed(this.initialAngle, this.targetX, this.targetY, this.cannonHeight);
        else if(this.mode == INIT_VELOCITY){
            this.initialSpeed = Calculator.initialSpeed2(this.finalSpeed, this.finalAngle, this.targetY, this.cannonHeight);
            this.initialAngle = Calculator.initialAngle2(this.finalSpeed, this.finalAngle, this.targetY, this.cannonHeight);
            this.targetX = Calculator.targetX(this.finalSpeed, this.finalAngle, this.targetY, this.cannonHeight);
        }
        else if(this.mode == HEIGHT) this.cannonHeight = Calculator.cannonHeight(this.initialSpeed, this.initialAngle, this.targetX, this.targetY);
        
        //check if anything is NaN and exit if so
        if(Calculator.oneIsNaN(this.initialAngle, this.initialSpeed, this.targetX, this.cannonHeight) || this.cannonHeight < 0 || this.targetX < 0){
            JOptionPane.showMessageDialog(this.view, "The projectile will not reach the target with the given conditions.", "Impossible Values", JOptionPane.ERROR_MESSAGE);
            this.animating = false;
            return;
        }
        
        //set the scale and landing time of projectile
        if(this.mode == DEFAULT)
            endTime = Calculator.maxTime(this.initialSpeed, this.initialAngle, this.cannonHeight);
        else if(this.targetX == 0 && this.mode != HEIGHT)
            endTime = Calculator.maxTime(this.initialSpeed, this.initialAngle, this.cannonHeight-this.targetY);
        else endTime = Calculator.timeAtX(this.initialSpeed, this.initialAngle, this.targetX);
        this.setScale(endTime);
        if(!this.animating) return; //if something went wrong with scale setting, exit animation.
        
        //begin animating
        for(int i = 0; i <= Calculator.round(endTime * frameRate); i++){
            while(this.paused){System.out.print("");} //pause if command is received
            if(this.resetted || !this.animating) return; //stop if command is received
            this.time = i/frameRate;
            this.updateFields();
            this.view.update();
            delay(Math.round(1000/frameRate*lag));
            if(this.mode == DEFAULT && Calculator.inRange(this.targetY, this.projectileY, 0.003*this.scale) //if the projectile lands on the target in default mode
                   && Calculator.inRange(this.targetX, this.projectileX, 0.003*this.scale)){
                force = false;
                break;
            }
            else force = true;
        }
        //complete the animation by moving the projectile to correct rounding error
        if(force){
            this.time = endTime;
            this.updateFields();
            if(this.mode == DEFAULT) this.projectileY = 0;
            else this.projectileY = this.targetY;
        }
        this.view.update();
        this.animating = false; //animation is over
        this.view.update();
    }
    
    /**Resets all the instance variables. By Daniel Liang.*/
    public void reset()
    {
        //stop the animation and begin reset
        this.resetted = true;
        this.paused = false;
        
        //set all current values to 0
        this.projectileX = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.currentSpeed = 0;
        this.currentAngle = 0;
        this.time = 0;
        
        //if just stoping animation, move projectile back to beginning
        if(animating){
            this.projectileY = this.cannonHeight;
        }
        
        //so perform full reset, set everything back to default
        else{
            this.cannonHeight = 0;
            this.projectileY = 0;
            this.initialSpeed = 0;
            this.initialAngle = 0;
            this.finalAngle = 0;
            this.finalSpeed = 0;
            this.scale = 52;
            this.targetX = this.scale/2;
            this.targetY = 0;
            this.resetted = true;
        }
        
        //inform the view of the reset
        this.view.update();
        this.resetted = false;
        this.animating = false;
    }
    
    /**Updates instance variables related to animation. By Daniel Liang.*/
    private void updateFields()
    {
        this.projectileX = Calculator.xDistance(this.initialSpeed, this.initialAngle, this.time);
        this.projectileY = Calculator.yDistance(this.initialSpeed, this.initialAngle, this.cannonHeight, this.time);
        this.currentSpeed = Calculator.speed(this.initialSpeed, this.initialAngle, this.time);
        this.xSpeed = Calculator.xVelocity(this.initialSpeed, this.initialAngle);
        this.ySpeed = Calculator.yVelocity(this.initialSpeed, this.initialAngle, this.time);
        this.currentAngle = Calculator.angle(this.initialSpeed, this.initialAngle, this.time);
    }
    
    /**Calculates the appropriate scale size to set. By Daniel Liang.
      * @param endTime The time the simulation is expected to take
      */
    private void setScale(double endTime)
    {
        //The largest distance the projectile may travel or largest target distance
        double maximum = Math.max(Math.max(Calculator.xDistance(this.initialSpeed, this.initialAngle, endTime),
                               Calculator.maxHeight(this.initialSpeed, this.initialAngle, this.cannonHeight)),
                                             Math.max(this.targetX, this.targetY));
        
        //special case for cannon height mode when target X is 0
        if(this.mode == HEIGHT && this.targetX == 0) maximum = targetY;
        
        //check if it is too large
        if(maximum >= Math.pow(10, 9) || Double.isNaN(maximum)){
            JOptionPane.showMessageDialog(this.view, "The simulator cannot show the projectile 10^9 m or further away from the origin.", "Out of Range", JOptionPane.ERROR_MESSAGE);
            this.animating = false;
            return;
        }
        int maxD = Calculator.round(maximum); //if it is OK, convert scale to int
        int divisions = 0; //the number of divisions of 10 possible
        int temp = maxD; //temporary variable for calculations
        
        if(maxD < 10) this.scale = 10;
        else{
            //power of 10 greater than the max distance
            while(temp >= 10){
                temp /= 10;
                divisions++;
            }
            divisions++; 
            
            //find a multiple of 20 or 50 greater than the max distance
            if(Math.pow(10, divisions) / 5 > maxD) this.scale = (int)(Math.pow(10, divisions)) / 5;
            else if(Math.pow(10, divisions) / 2 > maxD) this.scale = (int)(Math.pow(10, divisions)) / 2;
            else this.scale = (int)(Math.pow(10, divisions));
        }
        this.scale += 2;
    }
}//end class