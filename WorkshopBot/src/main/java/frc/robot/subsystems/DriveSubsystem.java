/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

//import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {
  WPI_TalonSRX frontLeftMotor = new WPI_TalonSRX(1);
  WPI_TalonSRX frontRightMotor = new WPI_TalonSRX(0);
  WPI_TalonSRX backLeftMotor = new WPI_TalonSRX(3);
  WPI_TalonSRX backRightMotor = new WPI_TalonSRX(2);
  WPI_TalonSRX liftMotor = new WPI_TalonSRX(5);

  SpeedControllerGroup leftmotors = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);
  SpeedControllerGroup rightmotors = new SpeedControllerGroup(frontRightMotor, backRightMotor);
  
  private DifferentialDrive drive = new DifferentialDrive(leftmotors, rightmotors);

  public void elevator(double speed) {
    liftMotor.set(speed);
  }

  @Override
  public void initDefaultCommand() {
  }

  public void drive(double forwards, double turn){
    drive.arcadeDrive((-1 * forwards), turn);
  }
}
