/*----------------------------------------------------------------------------
Copyright (c) 2018 FIRST. All Rights Reserved.                             
Open Source Software - may be modified and shared by FRC teams. The code   
must be accompanied by the FIRST BSD license file in the root directory of 
the project.                                                               
----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.RaiseLift;

public class AutoSequence extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutoSequence() {
    // Add Commands here:
    // e.g. addSequential(new Command1());
    addSequential(new AutoDrive(1, .5, 0)); //forward 1 seconds at 0.5 power
    addSequential(new AutoDrive(3, 0, 0.5)); //turn right at 0.5 power for 3 seconds

    addParallel(new RaiseLift(5, 20)); // raise lift while heading alongside the switch
    addSequential(new AutoDrive(3, 0.5, 0));  //forward for 3 seconds at half power
    addSequential(new AutoDrive(2, 0, -0.5));  //turn left to face down the field

    addSequential(new AutoDrive(3, 0.5, 0));  //forward next to the switch
    addSequential(new AutoDrive(2, 0, -0.5));  //turn left and face the Scale
    addSequential(new AutoDrive(1, 0.5, 0));  //go to the scale
    //NEED:
      //Eject cube
      //addParallel(new RaiseLift(0, 0, 0));  //lower lift
    addSequential(new AutoDrive(1, -0.5, 0));  //back away from the scale
    addSequential(new AutoDrive(1, 0, -1));  //turn towards pyramid
    addSequential(new AutoDrive(1, 1, 0));  //go towards the pyramid

  /*public AutoSequence2() {
    addSequential(new AutoDrive(1, 0.5, 0));
    addSequential(new AutoDrive(2, 0, -0.5));
    addParallel(new RaiseLift(5, 15));
    addParallel(new AutoDrive());
  }
  */


    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}