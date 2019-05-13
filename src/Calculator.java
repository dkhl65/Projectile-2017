/**Calculator
  * A helper class with static methods that does calculations for ProjectileModel. Speed is in m/s, distance in m, angles in degrees and time in s.
  * @author Daniel Liang, Kush Patel
  * @since 2017/05/13
  */
public class Calculator
{
    /**Rounds a double to a specified number of decimal places. By Daniel Liang.
      * @param num The number to round
      * @param places The number of decimal places
      */
    public static double round(double num, int places)
    {
        return Double.parseDouble(String.format("%."+places+"f", num));
    }
    
    /**Rounds a double to an int. By Daniel Liang.
      * @param num The number to round
      */
    public static int round(double num)
    {
        return (int)(Math.round(num));
    }
    
    /**Calculates the magnitude of the velocity at any time. By Daniel Liang.
      * @param initSpeed The initial speed of the projectile
      * @param initAngle The initial angle of the cannon
      * @param time The current time at which the projectile is at the returned speed
      */
    public static double speed(double initSpeed, double initAngle, double time)
    {
        return Math.sqrt(Math.pow(xVelocity(initSpeed, initAngle), 2) + Math.pow(yVelocity(initSpeed, initAngle, time), 2));
    }
    
    /**Calculates the horizontal velocity of the projectile. Positive means to the right. By Daniel Liang.
      * @param initSpeed The initial speed of the projectile
      * @param initAngle The initial angle of the cannon
      */
    public static double xVelocity(double initSpeed, double initAngle)
    {
        initAngle = Math.toRadians(initAngle);
        return initSpeed * Math.cos(initAngle);
    }
    
    /**Calculates the vertical velocity of the projectile at any time. Positive means up, negative means down. By Daniel Liang.
      * @param initSpeed The initial speed of the projectile
      * @param initAngle The initial angle of the cannon
      * @param time The current time at which the projectile is at the returned speed
      */
    public static double yVelocity(double initSpeed, double initAngle, double time)
    {
        initAngle = Math.toRadians(initAngle);
        return initSpeed * Math.sin(initAngle) + (-9.8) * time;
    }
    
    /**Calculates the horizontal distance of the projectile away from the cannon. By Daniel Liang.
      * @param initSpeed The initial speed of the projectile
      * @param initAngle The initial angle of the cannon
      * @param time The current time at which the projectile is at the returned position
      */
    public static double xDistance(double initSpeed, double initAngle, double time)
    {
        return xVelocity(initSpeed, initAngle) * time;
    }
    
    /**Calculates the vertical distance of the projectile above the ground. By Daniel Liang.
      * @param initSpeed The initial speed of the projectile
      * @param initAngle The initial angle of the cannon
      * @param cannonHeight The height above the ground the projectile was launched at
      * @param time The current time at which the projectile is at the returned position
      */
    public static double yDistance(double initSpeed, double initAngle, double cannonHeight, double time)
    {
        return yVelocity(initSpeed, initAngle, 0) * time + (-4.9) * Math.pow(time, 2) + cannonHeight;
    }
    
    /**Calculates the current angle of the projectile with the horizontal. Positive means above the horizontal, negative means below. By Daniel Liang.
      * @param initSpeed The initial speed of the projectile
      * @param initAngle The initial angle of the cannon
      * @param time The current time at which the projectile is at the returned angle
      */
    public static double angle(double initSpeed, double initAngle, double time)
    {
        if(time == 0) return initAngle;
        if(xVelocity(initSpeed, initAngle) == 0) return -90;
        return Math.toDegrees(Math.atan(yVelocity(initSpeed, initAngle, time) / xVelocity(initSpeed, initAngle)));
    }
    
    /**Calculated what initial speed the projectile must be launched at to hit the target. By Daniel Liang.
      * @param initAngle The initial angle of the cannon
      * @param targetX The distance of the target away from the cannon
      * @param targetY The height of the target above the ground
      * @param cannonHeight The height of the cannon above the ground
      */
    public static double initialSpeed(double initAngle, double targetX, double targetY, double cannonHeight)
    {
        initAngle = Math.toRadians(initAngle);
        if(targetX == 0 && targetY > cannonHeight && initAngle == Math.PI/2) return Math.sqrt(19.6 * (targetY - cannonHeight));
        if((targetX == 0 && targetY > cannonHeight && initAngle != Math.PI/2) || (initAngle == Math.PI/2 && targetX > 0)) return Double.NaN; //cause the correct error message
        return Math.sqrt(-4.9 * Math.pow(targetX, 2) / (Math.pow(Math.cos(initAngle), 2) * (targetY - cannonHeight - targetX * Math.tan(initAngle))));
    }
    
    /**Calculate the angle the cannon must aim at to hit the target. By Daniel Liang.
      * @param initSpeed The projectile firing speed
      * @param targetX The distance of the target away from the cannon
      * @param targetY The height of the target above the ground
      * @param cannonHeight The height of the cannon above the ground
      */
    public static double initialAngle(double initSpeed, double targetX, double targetY, double cannonHeight)
    {
        if(targetX == 0 && initSpeed == 0) return 0;
        if(targetX == 0) return -90;
        return Math.toDegrees(Math.atan((Math.pow(initSpeed, 2) * targetX - Math.sqrt(Math.pow(initSpeed, 4) * Math.pow(targetX, 2) - 96.04 * Math.pow(targetX, 4) - 19.6 * (targetY - cannonHeight) * Math.pow(targetX, 2) * Math.pow(initSpeed, 2))) / (9.8 * Math.pow(targetX, 2))));
    }
    
    /**Calculates the time for the projectile to hit the ground in default mode (i.e. the longest possible time before termination of animation). By Daniel Liang.
      * @param initSpeed The projectile firing speed
      * @param initAngle The initial angle of the cannon
      * @param cannonHeight The height of the cannon above the ground
      */
    public static double maxTime(double initSpeed, double initAngle, double cannonHeight)
    {
        initAngle = Math.toRadians(initAngle);
        double v1y = initSpeed * Math.sin(initAngle);
        return (v1y + Math.sqrt(Math.pow(v1y, 2) + 19.6 * cannonHeight)) / 9.8;
    }
    
    /**Calculates the launch speed at which the projectile must be fired at to hit the target. By Kush Patel.
      * @param finalSpeed The final speed of the projectile
      * @param finalAngle The final angle of the projectile
      * @param targetX The distance of the target away from the cannon
      * @param targetY The height of the target above the ground
      * @param cannonHeight The height of the cannon above the ground
      */
    public static double initialSpeed2(double finalSpeed, double finalAngle, double targetY, double cannonHeight)
    {
        return Math.sqrt((Math.pow(initialSpeedX2(finalSpeed,finalAngle),2)) + (Math.pow(initialSpeedY2(finalSpeed,finalAngle,targetY,cannonHeight),2)));
    }
    
    /**Calculates the initial horizontal velcoity of the projectile. By Kush Patel.
      * @param finalSpeed The final speed of the projectile
      * @param finalAngle The final angle of the projectile
      */
    public static double initialSpeedX2(double finalSpeed, double finalAngle)
    {
        finalAngle = Math.toRadians(finalAngle);
        return (finalSpeed * Math.cos(finalAngle));
    }
    
    /**Calculates initial vertical velocity of the projectile. By Kush Patel.
      * @param finalSpeed The final speed of the projectile
      * @param finalAngle The final angle of the projectile
      * @param targetY The height of the target above the ground
      * @param cannonHeight The height of the cannon above the ground
      */
    public static double initialSpeedY2(double finalSpeed, double finalAngle, double targetY, double cannonHeight)
    {
        finalAngle = Math.toRadians(finalAngle);
        return Math.sqrt((Math.pow(Math.sin(finalAngle)*finalSpeed,2)) - (2 * (-9.8) * (targetY-cannonHeight)));
    }
    
    /**Calculates the time at x distance. By Kush Patel.
      * @param xDistance The x distance of the projectile
      * @param initSpeed The initial speed of the projectile
      * @param initAngle The initial angle of the projectile
      */
    public static double timeAtX(double initSpeed, double initAngle, double xDistance)
    {
        initAngle = Math.toRadians(initAngle);
        if(initSpeed == 0 || Math.cos(initAngle) == 0) return 0;
        return xDistance / (initSpeed * Math.cos(initAngle));
    }
    
    /**Calculates initial angle of the projectile. By Daniel Liang.
      * @param finalSpeed The final speed of the projectile
      * @param finalAngle The final angle of the projectile
      * @param targetY The height of the target above the ground
      * @param cannonHeight The height of the cannon above the ground
      */
    public static double initialAngle2(double finalSpeed, double finalAngle, double targetY, double cannonHeight)
    {
        return Math.toDegrees(Math.atan(initialSpeedY2(finalSpeed, finalAngle, targetY, cannonHeight) / initialSpeedX2(finalSpeed, finalAngle)));
    }
    
    /**Calculates the height of the cannon. By Kush Patel.
      * @param initSpeed The inital speed of the projectile
      * @param initAngle The initial angle of the projectile
      * @param targetX The x distance of the target away from the cannon
      * @param targetY The height of the target above the ground
      */
    public static double cannonHeight(double initSpeed, double initAngle, double targetX, double targetY)
    {
        if(initSpeed == 0 && targetX == 0) return targetY;
        initAngle = Math.toRadians(initAngle);
        return (targetY - (targetX * Math.tan(initAngle)) + ( 4.9 * (Math.pow(targetX / (initSpeed * Math.cos(initAngle)),2)))); 
    }
    
    /**Calculates distance of the target away from the cannon. By Kush Patel.
      * @param finalSpeed The final speed of the projectile
      * @param finalAngle The final angle of the projectile
      * @param cannonHeight The height of the cannon above the ground
      * @param targetY The height of the target above the ground
      */
    public static double targetX(double finalSpeed, double finalAngle, double targetY, double cannonHeight)
    {
        double v1y = initialSpeedY2(finalSpeed,finalAngle,targetY,cannonHeight);
        return initialSpeedX2(finalSpeed,finalAngle) * (v1y + Math.sqrt(Math.pow(v1y, 2) - 19.6 * (targetY-cannonHeight))) / 9.8;
    }
    
    /**Checks if the two numbers are within a certain range of each other.
      * @param num1 The first number
      * @param num2 The second number
      * @param range How close the number have to be to each other
      */                                                         
    public static boolean inRange(double num1, double num2, double range)
    {
        return (Math.abs(num1 - num2) <= range);
    }
    
    /**Calculates the maximum posible height of the projectile. By Daniel Liang.
      * @param initSpeed The inital speed of the projectile
      * @param initAngle The initial angle of the projectile
      * @param cannonHeight The height of the cannon above the ground
      */
    public static double maxHeight(double initSpeed, double initAngle, double cannonHeight)
    {
        initAngle = Math.toRadians(initAngle);
        if(initAngle <= 0) return cannonHeight;
        return Math.pow(initSpeed * Math.sin(initAngle), 2) / 19.6 + cannonHeight;
    }    
    
    /**Checks if any of the arguments are NaN or Infinity. By Daniel Liang.
      * @returns true if any of the provided arguments are NaN
      */
    public static boolean oneIsNaN(double... args)
    {
        for(int i = 0; i < args.length; i++) if(Double.isNaN(args[i]) || Double.isInfinite(args[i])) return true;
        return false;
    }
}//end class