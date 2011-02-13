package net.slhs.team2159;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author SLHS Robotics
 */
public class DualCylinder {
   /*************************  Constructor(s) ********************************/

    public DualCylinder(int extendSolenoidPort,
                        int retractSolenoidPort,
                        int extendSwitchPort,
                        int retractSwitchPort) {
        System.out.println("DualCylinder() start.\n");

        m_extendSolenoid = new Solenoid(extendSolenoidPort);
        m_retractSolenoid = new Solenoid(retractSolenoidPort);

        m_extendSwitch = new DigitalInput(extendSwitchPort);
        m_retractSwitch = new DigitalInput(retractSwitchPort);

        System.out.println("DualCylinder) complete.\n");
    }

    /**************************  Init methods  ********************************/

    public void init() {

        enterPassive();

        System.out.println("DualCylinder.init() complete.\n");
    }

    /************************  Periodic methods  ******************************/

    private void passive(){
        // No periodic function

        // Handle state transitions
        switch (m_commandedState){
            case EXTENDED:
                enterExtending();
                break;
            case RETRACTED:
                enterRetracting();
                break;
            default:
                System.out.println("DualCylinder.passive(): invalid commanded state!");
        }
    }

    private void extending(){
        // Handle state transitions
        switch (m_commandedState){
            case EXTENDED:
                if (m_extendSwitch.get() == true)
                    enterExtended();
                break;
            case RETRACTED:
                System.out.println("DualCylinder.extending(): illegal commanded state!");
                break;
            default:
                System.out.println("DualCylinder.extending(): invalid commanded state!");
        }
    }

    private void extended(){
        // Handle state transitions
        switch (m_commandedState){
            case EXTENDED:
                // Check for leaks/bouncing
                if (m_extendSwitch.get() == false)
                    enterExtending();
                break;
            case RETRACTED:
                enterRetracting();
                break;
            default:
                System.out.println("DualCylinder.extended(): invalid commanded state!");
        }
    }

    private void retracting(){
        // Handle state transitions
        switch (m_commandedState){
            case EXTENDED:
                System.out.println("DualCylinder.retracting(): illegal commanded state!");
                break;
            case RETRACTED:
                if (m_retractSwitch.get() == true)
                    enterRetracted();
                break;
            default:
                System.out.println("DualCylinder.retracting(): invalid commanded state!");
        }
    }

    private void retracted(){
        // Handle state transitions
        switch (m_commandedState){
            case EXTENDING:
                enterExtending();
                break;
            case RETRACTING:
                if (m_retractSwitch.get() == false)
                    enterRetracting();
                break;
            default:
                System.out.println("DualCylinder.retracted(): invalid commanded state!");
        }
    }

    public void periodic(){

        // No dependent active components

        // Process current state
        switch(m_state){
            case PASSIVE:
                passive();
                break;
            case EXTENDING:
                extending();
                break;
            case EXTENDED:
                extended();
                break;
            case RETRACTING:
                retracting();
                break;
            case RETRACTED:
                retracted();
                break;
            default:
                System.out.println("DualCylinder.periodic(): unknown state!");
        }
    }

    /************************* Continuous methods  ****************************/

    public void continuous() {
        // TBD - anything to do here?
    }

    /*************************  Accessor methods  *****************************/

    public void extend(){
        m_commandedState = EXTENDED;
    }

    public void retract(){
        m_commandedState = RETRACTED;
    }

    public void passivate(){
       m_commandedState = PASSIVE;
    }

    public boolean isPassive(){
        return m_state == PASSIVE;
    }

    public boolean isExtending(){
        return m_state == EXTENDING;
    }

    public boolean isExtended(){
        return (m_state == EXTENDED);
    }

    public boolean isRetracting(){
        return m_state == RETRACTING;
    }

    public boolean isRetracted(){
        return (m_state == RETRACTED);
    }

    /*************************  Utility methods  ******************************/

    private void enterPassive(){
        m_extendSolenoid.set(false);
        m_retractSolenoid.set(false);
        m_state = PASSIVE;
    }

    private void enterExtending(){
        m_extendSolenoid.set(true);
        m_retractSolenoid.set(false);
        m_state = EXTENDING;
    }

    private void enterExtended(){
        m_extendSolenoid.set(true);
        m_retractSolenoid.set(false);
        m_state = EXTENDED;
    }

    private void enterRetracting(){
        m_extendSolenoid.set(false);
        m_retractSolenoid.set(true);
        m_state = RETRACTING;
    }

    private void enterRetracted(){
        m_extendSolenoid.set(false);
        m_retractSolenoid.set(true);
        m_state = RETRACTED;
    }

    /*****************************  Fields  ***********************************/

    // Robot's FSM states
    static final int PASSIVE = 0;
    static final int EXTENDING = 1;
    static final int EXTENDED = 2;
    static final int RETRACTING = 3;
    static final int RETRACTED = 4;
    private int m_state;
    private int m_commandedState;

    private Solenoid m_extendSolenoid;
    private Solenoid m_retractSolenoid;

    private DigitalInput m_extendSwitch;
    private DigitalInput m_retractSwitch;

}
