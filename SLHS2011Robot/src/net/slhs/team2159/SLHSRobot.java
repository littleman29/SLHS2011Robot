/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.slhs.team2159;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick.ButtonType;


/**
 *
 * @author SLHS Robotics
 */
public class SLHSRobot extends SimpleRobot{
    

    public SLHSRobot(){
        System.out.println("Starting SLHSRobot()!\n");

        //
        // Acquire/construct nested components
        //

        // Acquire the Driver Station
        m_ds = DriverStation.getInstance();
        // Create the joysticks
        m_leftStick = new Joystick(USB_STICK_LEFT);
        m_rightStick = new Joystick(USB_STICK_RIGHT);

       // Iterate over all the buttons on each joystick, setting state to false for each
		int buttonNum = 1;						// start counting buttons at button 1
		for (buttonNum = 1; buttonNum <= NUM_JOYSTICK_BUTTONS; buttonNum++) {
			m_rightStickButtonState[buttonNum] = false;
			m_leftStickButtonState[buttonNum] = false;
                        // make button 3 active (on the right stick)
                        m_rightStickButtonState[3] = true;
		}

        // Create the drive train
        m_robotDrive = new RobotDrive(PWM_DRV_UPPER_LEFT,
                                      PWM_DRV_LOWER_LEFT,
                                      PWM_DRV_UPPER_RIGHT,
                                      PWM_DRV_LOWER_RIGHT);

        // Create the compressor
        m_compressor = new Compressor(DIO_PRESSURE_SWITCH,
                                      RLY_COMPRESSOR_MOTOR);

        System.out.println("Team2159Robot() complete.\n");


    }

    public void robotMain(){

        


    }

 //
    // GLOBAL RESOURCES: Allocate _here_ to prevent conflicts
    //

    // USB Ports (4?)
    static final int USB_STICK_LEFT  = 1;
    static final int USB_STICK_RIGHT = 2;
    static final int USB_3_UNUSED    = 3;
    static final int USB_4_UNUSED    = 4;

    // All buttons on both joysticks
    static final int NUM_JOYSTICK_BUTTONS = 16;
	boolean[] m_rightStickButtonState = new boolean[(NUM_JOYSTICK_BUTTONS+1)];
	boolean[] m_leftStickButtonState = new boolean[(NUM_JOYSTICK_BUTTONS+1)];

    // PWM Ports (10)
    // Current drive train/Jaguar wiring (update if changed!):
    //     Front left = 4, front right = 3
    //     Rear left = 2, rear right = 1
    //    (NOTE: "Front" is end with pneumatics/short kicker,
    //           "rear" is end with electronics/RSL/long kicker)
    static final int PWM_DRV_UPPER_LEFT  = 1;
    static final int PWM_DRV_LOWER_LEFT  = 2;
    static final int PWM_DRV_UPPER_RIGHT = 3;
    static final int PWM_DRV_LOWER_RIGHT = 4;
    static final int PWM_5_UNUSED        = 5;
    static final int PWM_6_UNUSED        = 6;
    static final int PWM_7_UNUSED        = 7;
    static final int PWM_8_UNUSED        = 8;
    static final int PWM_9_UNUSED        = 9;
    static final int PWM_10_UNUSED       = 10;

    // Relay Ports (8)
    static final int RLY_SHORT_BALL_ROLLER = 1;
    static final int RLY_LONG_BALL_ROLLER  = 2;
    static final int RLY_3_UNUSED          = 3;
    static final int RLY_4_UNUSED          = 4;
    static final int RLY_5_UNUSED          = 5;
    static final int RLY_6_UNUSED          = 6;
    static final int RLY_7_UNUSED          = 7;
    static final int RLY_COMPRESSOR_MOTOR  = 8;

    // Digital I/O Ports (14)
    static final int DIO_SHORT_BALL_SENSOR    = 1;
    static final int DIO_SHORT_LATCH_OPENED   = 2;
    static final int DIO_SHORT_LATCH_CLOSED   = 3;
    static final int DIO_SHORT_FOOT_EXTENDED  = 4;
    static final int DIO_SHORT_FOOT_RETRACTED = 5;
    static final int DIO_6_UNUSED             = 6;
    static final int DIO_LONG_BALL_SENSOR     = 7;
    static final int DIO_LONG_LATCH_OPENED    = 8;
    static final int DIO_LONG_LATCH_CLOSED    = 9;
    static final int DIO_LONG_FOOT_EXTENDED   = 10;
    static final int DIO_LONG_FOOT_RETRACTED  = 11;
    static final int DIO_12_UNUSED            = 12;
    static final int DIO_13_UNUSED            = 13;
    static final int DIO_PRESSURE_SWITCH      = 14;

    // I2C Output Ports (4)
    // (All currently unused)
    static final int I2C_1_UNUSED = 1;
    static final int I2C_2_UNUSED = 2;
    static final int I2C_3_UNUSED = 3;
    static final int I2C_4_UNUSED = 4;

    // Solenoid/cylinder Ports (8)
    static final int SOL_SHORT_LATCH_OPEN   = 1;
    static final int SOL_SHORT_LATCH_CLOSE  = 2;
    static final int SOL_SHORT_FOOT_EXTEND  = 3;
    static final int SOL_SHORT_FOOT_RETRACT = 4;
    static final int SOL_LONG_LATCH_OPEN    = 5;
    static final int SOL_LONG_LATCH_CLOSE   = 6;
    static final int SOL_LONG_FOOT_EXTEND   = 7;
    static final int SOL_LONG_FOOT_RETRACT  = 8;

    // Analog Input Ports (8)
    // (All currently unused)
    static final int AIN_1_UNUSED = 1;
    static final int AIN_2_UNUSED = 2;
    static final int AIN_3_UNUSED = 3;
    static final int AIN_4_UNUSED = 4;
    static final int AIN_5_UNUSED = 5;
    static final int AIN_6_UNUSED = 6;
    static final int AIN_7_UNUSED = 7;
    static final int AIN_8_UNUSED = 8;

    //
    // FINITE STATE MACHINE (FSM)
    //

    // States
    static final int START_UP = 0;
    static final int NO_OP    = 1;
    static final int PRE_OP   = 2;
    static final int STOP_OP  = 3;
    static final int AUTO_OP  = 4;
    static final int TELE_OP  = 5;

    // Current FSM state
    int m_state;

    //
    // NESTED/OWNED COMPONENTS
    //

    // Driver station and controls
    DriverStation m_ds;
    Joystick m_leftStick;
    Joystick m_rightStick;
    Joystick.ButtonType m_button3;


    // Drive train
    RobotDrive m_robotDrive;

    // Pneumatics compressor
    Compressor m_compressor;

    // Strategies
    // TBD

    //
    // UTILITY VALUES/VARIABLES
    //

}
