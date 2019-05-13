import javax.swing.*;
import java.awt.*;

/**ProjectileView
  * Creates the view of the projectile motion simulator
  * @author Kush Patel, Daniel Liang
  * @since 5/28/2017
  */
public class ProjectileView extends JPanel
{
    //Instance Variables
    private Simulator simulator;
    private ProjectileModel model; //The model which it communicates with.
    private JComboBox box;
    
    //JLabels
    private JLabel mode = new JLabel("Mode:  ");
    private JLabel initialValues = new JLabel("Initial Values ");
    private JLabel finalValues = new JLabel("Final Values  ");
    private JLabel currentValues = new JLabel("Current Values  ");
    private JLabel cannonHeight = new JLabel("Cannon Height: ");
    private JLabel projectileSpeedI = new JLabel("Projectile Speed: ");
    private JLabel projectileSpeedF = new JLabel("Projectile Speed: ");
    private JLabel projectileAngleI = new JLabel("Projectile Angle: ");
    private JLabel projectileAngleF = new JLabel("Projectile Angle: ");
    private JLabel targetXLocation = new JLabel("Target X Location: ");
    private JLabel targetYLocation = new JLabel("Target Y Location: ");
    
    //JTextFields 
    private JTextField heightI = new JTextField(6);
    private JTextField speedI = new JTextField(6);
    private JTextField angleI = new JTextField(6);
    private JTextField targetX = new JTextField(6);
    private JTextField targetY = new JTextField(6);
    private JTextField speedF = new JTextField(6);
    private JTextField angleF = new JTextField(6);
    private JTextArea area;
    
    //JButtons
    private JButton start = new JButton("Start");
    private JButton reset = new JButton("Reset");
    
    /**Constructor for the view. By Kush Patel.
      * @param model The model of the program
      */
    public ProjectileView(ProjectileModel model)
    {
        super();
        this.model = model;
        this.model.setGUI(this);
        this.simulator = new Simulator(52);
        this.layoutView();
        this.registerControllers();
        this.update();
    }
    
    /**Sets the layout of the simulator. By Kush Patel.
      */
    public void layoutView()
    {
        JPanel projectile = new JPanel(); //Holds the simulator
        
        JPanel initialV = this.initialValue(); //Holds the JPanel for initial values
        JPanel finalV = this.finalValue();     //Holds the JPanel for final values
        JPanel top = this.modeValue();         //Holds the modeValue Jpanel
        JPanel currentV = this.currentValue(); //Holds the JPanel for current values
        JPanel buttonsPanel = this.buttons();  //Holds the JPanel for both buttons
        
        JPanel combined = new JPanel(new GridLayout(1,1));  //Holds initialV and finalV JPanels. These are all the input fields.
        JPanel rightTop = new JPanel();                     //Holds combined and top JPanels. This includes the combobox and all the input fields.
        JPanel rightSide = new JPanel();                    //Holds rightTop and currentV JPanels. This combines the output(current values) to the rest of the right side.
        
        //Adding initial and final value JPanels into combined JPanel
        combined.add(initialV);
        combined.add(finalV);
        
        //Adding the buttonsPanel,top and combined JPanels
        rightTop.setLayout(new BoxLayout(rightTop,BoxLayout.Y_AXIS));
        rightTop.add(top);
        rightTop.add(combined);
        rightTop.add(buttonsPanel);
        
        //Adding currentV and rightTop JPanels. This is all of the right side.
        rightSide.setLayout(new BoxLayout(rightSide,BoxLayout.Y_AXIS));
        rightSide.add(rightTop);
        rightSide.add(currentV);
        
        //Adding the simulator
        projectile.add(this.simulator);
        
        this.setLayout(new BorderLayout());                           //Setting layout of the main JPanel to BorderLayout
        this.add(rightSide,BorderLayout.EAST);                        //Adding rightSide JPanel to the main JPanel, and placing it on the right side.
        this.add(projectile,BorderLayout.CENTER);                     //Adding projectile JPanel to the main JPanel, and placing it at the center.
        this.setBorder(BorderFactory.createLineBorder(Color.black));  //Creates a black border for the main JPanel
    }
    
    /**JPanel that holds all the initial value components. This includes all the initial value JLabels, and JTextFields. By Kush Patel.
      */ 
    private JPanel initialValue()
    {
        JPanel leftSide = new JPanel(); //Holds all initial value components
        
        //Setting boxlayout for the leftSide JPanel. Used to display everything vertically.
        leftSide.setLayout(new BoxLayout (leftSide, BoxLayout.Y_AXIS));  
        
        JPanel heightIText = new JPanel(); //Holds the textfield for cannon height 
        JPanel speedIText= new JPanel();   //Holds the textfield for initial speed      
        JPanel angleIText = new JPanel();  //Holds the textfield for initial angle
        JPanel targetXText = new JPanel(); //Holds the textfield for target x location
        JPanel targetYText = new JPanel(); //Holds the textfield for target y location
        
        JPanel heightIText2 = new JPanel(); //Holds cannonHeight JLabel and heightIText JPanel 
        JPanel speedIText2 = new JPanel();  //Holds projectileSpeedI JLabel and speedIText JPanel
        JPanel angleIText2 = new JPanel();  //Holds projectileAngleI JLabel and angleIText JPanel
        JPanel targetXText2 = new JPanel(); //Holds targetX JLabel and targetXText JPanel
        JPanel targetYText2 = new JPanel(); //Holds targetY JLabel and targetYText JPanel
        
        //Setting layout of the JPanels to BoxLayout, so everything could be displayed vertically.
        heightIText2.setLayout (new BoxLayout (heightIText2, BoxLayout.Y_AXIS)); 
        speedIText2.setLayout (new BoxLayout (speedIText2, BoxLayout.Y_AXIS)); 
        angleIText2.setLayout (new BoxLayout (angleIText2, BoxLayout.Y_AXIS));  
        targetXText2.setLayout (new BoxLayout (targetXText2, BoxLayout.Y_AXIS)); 
        targetYText2.setLayout (new BoxLayout (targetYText2, BoxLayout.Y_AXIS));   
        
        //Setting Color of the JPanels
        heightIText.setBackground(Color.green);
        speedIText.setBackground(Color.green);
        angleIText.setBackground(Color.green);
        targetXText.setBackground(Color.green);
        targetYText.setBackground(Color.green);
        heightIText2.setBackground(Color.green);
        speedIText2.setBackground(Color.green);
        angleIText2.setBackground(Color.green);
        targetXText2.setBackground(Color.green);
        targetYText2.setBackground(Color.green);
        
        //Adding the textfields 
        heightIText.add(this.heightI);
        speedIText.add(this.speedI);
        angleIText.add(this.angleI);
        targetXText.add(this.targetX);
        targetYText.add(this.targetY);
        
        //Adding the JLabels and the JPanels with textfields
        heightIText2.add(this.cannonHeight);
        heightIText2.add(heightIText);
        speedIText2.add(this.projectileSpeedI);
        speedIText2.add(speedIText);
        angleIText2.add(this.projectileAngleI);
        angleIText2.add(angleIText);
        targetXText2.add(this.targetXLocation);
        targetXText2.add(targetXText);
        targetYText2.add(this.targetYLocation);
        targetYText2.add(targetYText);
        
        //Adding initialValues JLabel and JPanels containing all the initial values input fields. This includes the JLabels and JTextFields.
        leftSide.add(this.initialValues);
        leftSide.add(heightIText2);
        leftSide.add(speedIText2);
        leftSide.add(angleIText2);
        leftSide.add(targetXText2);
        leftSide.add(targetYText2);
        
        leftSide.setBackground(new Color(255,178,102)); //Setting color of the leftSide JPanel
        
        //Creating a black border for the leftSide JPanel
        leftSide.setBorder(BorderFactory.createLineBorder(Color.black));
        
        return leftSide; //Returning the leftSide Jpanel
    }
    
    /**JPanel that holds all the final value components. This includes all the final value JLabels and JTextFields. By Kush Patel.
      */ 
    private JPanel finalValue()
    {
        JPanel rightSide = new JPanel();   //Holds all final value components
        
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));
        
        JPanel speedFText = new JPanel();  //Holds the textfield for final speed
        JPanel angleFText = new JPanel();  //Holds the textfield for final angle
        
        JPanel speedFText2 = new JPanel();  //Holds projectileSpeedF JLabel and speedFText JPanel  
        JPanel angleFText2 = new JPanel();  //Holds projectileAngleF JLabel and angleFText JPanel
        
        //Setting layout of the JPanels to BoxLayout, so everything could be displayed vertically.
        speedFText2.setLayout (new BoxLayout (speedFText2, BoxLayout.Y_AXIS));
        angleFText2.setLayout (new BoxLayout (angleFText2, BoxLayout.Y_AXIS));   
        
        //Setting Color of the JPanels
        speedFText.setBackground(Color.green);
        angleFText.setBackground(Color.green);
        speedFText2.setBackground(Color.green);
        angleFText2.setBackground(Color.green);
        
        //Adding the textfields.
        speedFText.add(this.speedF);
        angleFText.add(this.angleF);
        
        //Adding the JLabels and the JPanels with textfields 
        speedFText2.add(this.projectileSpeedF);
        speedFText2.add(speedFText);
        angleFText2.add(this.projectileAngleF);
        angleFText2.add(angleFText);
        
        //Adding finalValues JLabel and JPanels containing all the final values components. This includes the JLabels and the JTextFields. 
        rightSide.add(this.finalValues);
        rightSide.add(speedFText2);
        rightSide.add(angleFText2);
        
        rightSide.setBackground(new Color(255,178,102)); //Setting the color of the rightSide JPanel
        
        //Creating a black border for the rightSide JPanel
        rightSide.setBorder(BorderFactory.createLineBorder(Color.black));
        
        return rightSide; //Returning the rightSide JPanel
        
    }  
    
    /**JPanel that holds the combo box and the mode JLabel. By Kush Patel.
      */ 
    private JPanel modeValue()
    {
        String[] diffModes = {"Default", "Initial Angle", "Initial Speed", "Initial Angle, Initial Speed and Target Distance", "Cannon Height"};  //The modes that will be displayed in the JComboBox
        
        this.box = new JComboBox(diffModes);
        
        JPanel modeText = new JPanel(); //Holds the mode JLabel and the JComboBox
        
        //Setting the layout of the modeText JPanel to BoxLayout
        modeText.setLayout(new BoxLayout(modeText, BoxLayout.Y_AXIS)); 
        
        //Left align the mode JLabel
        JPanel leftAlign = new JPanel();                      //JPanel that is going to be used to left align the mode JLabel
        leftAlign.setLayout(new FlowLayout(FlowLayout.LEFT)); //Setting layout of the JPanel to FlowLayout and making it left aligned
        leftAlign.add(this.mode);                             //Adding mode JLabel
        
        //Setting Color of the leftAlign JPanel
        leftAlign.setBackground(new Color(255,178,102));
        
        //Adding leftAlign JPanel and JComboBox to modeText JPanel
        modeText.add(leftAlign);
        modeText.add(this.box);
        
        //Creating a black border for the modeText JPanel
        modeText.setBorder(BorderFactory.createLineBorder(Color.black)); 
        
        return modeText; //Returning the modeText JPanel
    }
    
    /**JPanel that holds all the current value components. By Kush Patel.
      */ 
    private JPanel currentValue()
    {
        area = new JTextArea(); //JTextArea used to display the current values
        String output;          //String that contains the current value information
        
        JPanel panel = new JPanel();                            //JPanel that will hold the JTextArea for current values
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS)); //Setting layout of panel to BoxLayout to display everything vertically.
        
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); //Setting font of the textArea
        area.setBackground(panel.getBackground());               //Setting background of the textArea
        area.setEditable(false);                                 //Making the textArea not editable
        
        output = String.format("Time: %6.2f s%nX-Location: %6.2f m%nY-Location: %6.2f m%nSpeed: %6.2f m/s%nX-Speed: %6.2f m/s%nY-Speed: %6.2f m/s%nAngle: %6.2f degrees",
                               0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        
        area.setText(output); 
        
        //Left align the current values JLabel
        JPanel leftAlign = new JPanel();                      //JPanel that is going to be used to left align the currentValues JLabel
        leftAlign.setLayout(new FlowLayout(FlowLayout.LEFT)); //Setting layout of the JPanel to FlowLayout and making it left aligned
        leftAlign.add(this.currentValues);                    //Adding currentValues JLabel
        
        //Adding leftAlign JPanel and area to panel 
        panel.add(leftAlign);
        panel.add(area);
        
        //Creating a black border for the panel
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        return panel; //Returning panel
    }
    
    /**JPanel that holds both the start and reset buttons. By Kush Patel.
      */ 
    private JPanel buttons()
    {
        JPanel allButtons = new JPanel(); //Holds the start and reset buttons
        
        //Setting size of the JButtons
        this.start.setPreferredSize(new Dimension (200, 30));
        this.reset.setPreferredSize(new Dimension (200, 30));
        
        //Setting Color of the JPanel
        allButtons.setBackground(Color.green);
        
        //Adding start and reset buttons to allButtons JPanel
        allButtons.add(this.start);
        allButtons.add(this.reset);
        
        //Creating a black border for the allButtons JPanel
        allButtons.setBorder(BorderFactory.createLineBorder(Color.black));
        
        return allButtons; //Returning allButtons JPanel
    }
    
    /**Registers all the controllers. By Kush Patel.*/ 
    private void registerControllers()
    {
        MenuController input = new MenuController(this.model,this.speedI, this.heightI, this.angleI, this.targetX, this.targetY, this.speedF, this.angleF); //Instantiating MenuController 
        this.start.addActionListener(input); //Adding actionlistener to start button for the MenuController
        
        ComboBoxController boxController = new ComboBoxController(this.model, this.box); //Instantiating ComboBoxController
        this.box.addActionListener(boxController); //Adding actionListener to combobox for ComboBoxController
        
        ResetController buttonController = new ResetController(this.model); //Instantiating ResetController 
        this.reset.addActionListener(buttonController);  //Adding actionListener to reset button for ResetController
    }
    
    /**Updates the view. By Daniel Liang.*/ 
    public void update()
    {
        //perform animation if necessary
        if(this.model.isAnimating() && !this.model.isResetted()){
            //change start button to pause / continue and reset to stop
            this.start.setText("Pause / Continue");
            this.reset.setText("Stop");
            
            //disabled the combo box
            this.box.setEnabled(false);
            
            //set the text field for solved variables
            if(this.model.getMode() == ProjectileModel.INIT_ANGLE) this.angleI.setText(""+Calculator.round(this.model.getInitialAngle(), 2));
            else if(this.model.getMode() == ProjectileModel.INIT_SPEED) this.speedI.setText(""+Calculator.round(this.model.getInitialSpeed(), 2));
            else if(this.model.getMode() == ProjectileModel.INIT_VELOCITY){
                this.angleI.setText(""+Calculator.round(this.model.getInitialAngle(), 2));
                this.speedI.setText(""+Calculator.round(this.model.getInitialSpeed(), 2));
                this.targetX.setText(""+Calculator.round(this.model.getTargetX(), 2));
            }
            else if(this.model.getMode() == ProjectileModel.HEIGHT) this.heightI.setText(""+Calculator.round(this.model.getCannonHeight(), 2));
            
            //set the cannon and target at the assigned locations
            this.simulator.setTargetLocation(this.model.getTargetX(), this.model.getTargetY());
            this.simulator.setCannonHeight(this.model.getCannonHeight());
            this.simulator.setCannonAngle(this.model.getInitialAngle());
            
            //perform the animation and update output
            this.simulator.setScale(this.model.getScale());
            if(this.model.getTime() == 0) this.simulator.reset();
            this.simulator.moveProjectile(this.model.getProjectileX(), this.model.getProjectileY());
            this.updateOutput();
        }
        
        //if not animating, set the JComponents enabled or disabled depending on mode
        else{
            //change button text back to normal
            this.start.setText("Start");
            this.reset.setText("Reset");
            
            //enable the box
            this.box.setEnabled(true);
            
            //set which text fields are enabled depending on mode
            if(this.model.getMode() == ProjectileModel.INIT_ANGLE){
                this.setFieldsEditable(true, true, false, true, true, false, false);
            }
            else if(this.model.getMode() == ProjectileModel.INIT_SPEED){
                this.setFieldsEditable(true, false, true, true, true, false, false);
            }
            else if(this.model.getMode() == ProjectileModel.INIT_VELOCITY){
                this.setFieldsEditable(true, false, false, false, true, true, true);
            }
            else if(this.model.getMode() == ProjectileModel.HEIGHT){
                this.setFieldsEditable(false, true, true, true, true, false, false);
            }
            else{
                this.setFieldsEditable(true, true, true, true, true, false, false);
            }
            
            //remove text from final fields if not needed
            if(this.model.getMode() != ProjectileModel.INIT_VELOCITY){
                this.speedF.setText("");
                this.angleF.setText("");
            }
        }
        
        //fully resetting to default values, or just stopping the animation
        if(this.model.isResetted()){
            //reset all text fields if resetting fully instead of stopping animation
            if(!this.model.isAnimating()){
                this.heightI.setText("");
                this.speedI.setText("");
                this.angleI.setText("");
                this.targetX.setText("");
                this.targetY.setText("");
                this.speedF.setText("");
                this.angleF.setText("");
            }
            
            //set all animation objects back to original location
            this.simulator.reset();
            this.simulator.setScale(this.model.getScale());
            this.simulator.setTargetLocation(this.model.getTargetX(), this.model.getTargetY());
            this.simulator.setCannonHeight(this.model.getCannonHeight());
            this.simulator.setCannonAngle(this.model.getInitialAngle());
            this.updateOutput();
        }
    }
    
    /**Updates the output text. By Daniel Liang.*/
    private void updateOutput()
    {
        String output; //the output text
        final String format = "Time: %6.2f s%nX-Location: %6.2f m%nY-Location %6.2f m%nSpeed: %6.2f m/s%nX-Speed: %6.2f m/s%nY-Speed: %6.2f m/s%nAngle: %6.2f degrees";
        
        if(!this.model.isResetted()){
            output = String.format(format, this.model.getTime(), this.model.getProjectileX(), this.model.getProjectileY(), this.model.getCurrentSpeed(), 
                                   this.model.getXSpeed(), this.model.getCurrentYSpeed(), this.model.getCurrentAngle());
        }
        else{
            output = String.format(format, 0.0, 0.0, this.model.getCannonHeight(), 0.0, 0.0, 0.0, this.model.getInitialAngle());
        }
        
        this.area.setText(output);
    }
    
    /**Sets the individual text fields enabled or disabled. By Daniel Liang.
      * @param heightI The initial height of the cannon text field editable
      * @param speedI The initial speed of projectile text field editable
      * @param angleI The initial angle of the cannon text field editable
      * @param targetX The x location of the target text field editable
      * @param targetY The y location of the target text field editable
      * @param speedF The final speed of projectile text field editable
      * @param angleF The final angle of projectile text field editable
      */
    private void setFieldsEditable(boolean heightI, boolean speedI, boolean angleI, boolean targetX, boolean targetY, boolean speedF, boolean angleF)
    {
        this.heightI.setEditable(heightI);
        this.speedI.setEditable(speedI);
        this.angleI.setEditable(angleI);
        this.targetX.setEditable(targetX);
        this.targetY.setEditable(targetY);
        this.speedF.setEditable(speedF);
        this.angleF.setEditable(angleF);
    }
}//end class