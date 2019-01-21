/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import java.util.concurrent.TimeUnit;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private static final int kMotorPort = 3;
  private static final int kJoystickPort = 0;
  private static final int elevMotorLeft = 0;
  private static final int elevMotorRight = 2;
  private static final int frontLeftMotorPort = 12;
  private static final int frontRightMotorPort = 14;
  private static final int backLeftMotorPort = 13;
  private static final int backRightMotorPort = 15;

  private TalonSRX elevator_motor = new TalonSRX(kMotorPort);
  private TalonSRX elevatorMotorLeft = new TalonSRX(elevMotorLeft);
  private TalonSRX elevatorMotorRight = new TalonSRX(elevMotorRight);
  private TalonSRX frontLeftMotor = new TalonSRX(frontLeftMotorPort);
  private TalonSRX frontRightMotor = new TalonSRX(frontRightMotorPort);
  private TalonSRX backLeftMotor = new TalonSRX(backLeftMotorPort);
  private TalonSRX backRightMotor = new TalonSRX(backRightMotorPort);

  private Joystick m_joystick = new Joystick(kJoystickPort);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    backLeftMotor.follow(frontLeftMotor);
    backRightMotor.follow(frontRightMotor);

    frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,Constants.kTimeoutMs);
    frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,Constants.kTimeoutMs);

    frontRightMotor.setSensorPhase(true);
    frontLeftMotor.setSensorPhase(true);
    frontLeftMotor.setInverted(false);
    frontRightMotor.setInverted(false);

    frontLeftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    frontRightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.kTimeoutMs);
    
    frontLeftMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
    frontRightMotor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);

    frontLeftMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
    frontLeftMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
    frontLeftMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
    frontLeftMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    frontRightMotor.configNominalOutputForward(0, Constants.kTimeoutMs);
    frontRightMotor.configNominalOutputReverse(0, Constants.kTimeoutMs);
    frontRightMotor.configPeakOutputForward(1, Constants.kTimeoutMs);
    frontRightMotor.configPeakOutputReverse(-1, Constants.kTimeoutMs);

    frontLeftMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    frontLeftMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    frontLeftMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    frontLeftMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    frontLeftMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);
    frontRightMotor.selectProfileSlot(Constants.kSlotIdx, Constants.kPIDLoopIdx);
    frontRightMotor.config_kF(Constants.kSlotIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    frontRightMotor.config_kP(Constants.kSlotIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    frontRightMotor.config_kI(Constants.kSlotIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    frontRightMotor.config_kD(Constants.kSlotIdx, Constants.kGains.kD, Constants.kTimeoutMs);

    frontLeftMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
    frontLeftMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);
    frontRightMotor.configMotionCruiseVelocity(15000, Constants.kTimeoutMs);
    frontRightMotor.configMotionAcceleration(6000, Constants.kTimeoutMs);

    frontLeftMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    frontRightMotor.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    
    double rightJoy = m_joystick.getRawAxis(5);
    double leftJoy = -1*m_joystick.getRawAxis(1);
    boolean button = m_joystick.getRawButton(1);
    double motorOutput = frontRightMotor.getMotorOutputPercent();
    //tank drive
    //right side
    if(rightJoy > 0.03 || rightJoy < -0.03) {
      frontRightMotor.set(ControlMode.PercentOutput, rightJoy*0.6);
    }
    else {
      frontRightMotor.set(ControlMode.PercentOutput, 0);
    }
    //left side
    if(leftJoy > 0.03 || leftJoy < -0.03) {
      frontLeftMotor.set(ControlMode.PercentOutput, leftJoy*0.6);    }
    else {
      frontLeftMotor.set(ControlMode.PercentOutput, 0);
    }
    /*else {
      elevator_motor.set(ControlMode.PercentOutput, 0.2);
    }*/

    //intake
    if(button) {
      elevatorMotorLeft.set(ControlMode.PercentOutput, 0.4);
      elevatorMotorRight.set(ControlMode.PercentOutput, -0.4);
    }
    //outtake
    else if(m_joystick.getRawButton(2)) {
      elevatorMotorLeft.set(ControlMode.PercentOutput, -0.5);
      elevatorMotorRight.set(ControlMode.PercentOutput, 0.5);
    }
    else {
      elevatorMotorLeft.set(ControlMode.PercentOutput, 0);
      elevatorMotorRight.set(ControlMode.PercentOutput, 0);
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
