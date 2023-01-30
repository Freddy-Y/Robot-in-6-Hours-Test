// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;


public class DriveTrain extends SubsystemBase {
  public final WPI_TalonSRX LEFT_MOTOR_ONE;
  public final WPI_TalonSRX LEFT_MOTOR_TWO;
  public final WPI_TalonSRX RIGHT_MOTOR_ONE;
  public final WPI_TalonSRX RIGHT_MOTOR_TWO;
  private final MotorControllerGroup leftControllerGroup;
  private final MotorControllerGroup rightControllerGroup;
  public final DifferentialDrive DIFF_DRIVE;

  public Pigeon2 gyro = new Pigeon2(0);
  private final double rollDeadband = 1; // for comp 2.5
 

  

  /** Creates a new DriveTrain. */
  public DriveTrain() {
    this.LEFT_MOTOR_ONE = new WPI_TalonSRX(1);
    this.LEFT_MOTOR_TWO = new WPI_TalonSRX(2);
    this.RIGHT_MOTOR_ONE = new WPI_TalonSRX(3);
    this.RIGHT_MOTOR_TWO = new WPI_TalonSRX(4);

    this.leftControllerGroup = new MotorControllerGroup(LEFT_MOTOR_ONE, LEFT_MOTOR_TWO);
    this.rightControllerGroup = new MotorControllerGroup(RIGHT_MOTOR_ONE, RIGHT_MOTOR_TWO);

    this.DIFF_DRIVE = new DifferentialDrive(leftControllerGroup, rightControllerGroup);

    this.leftControllerGroup.setInverted(false);
    this.rightControllerGroup.setInverted(true);

    configureMotors();

    gyro.configFactoryDefault();
    gyro.setYaw(0);
  }

  public void configureMotors() {
    this.LEFT_MOTOR_ONE.configFactoryDefault();
    this.LEFT_MOTOR_TWO.configFactoryDefault();
    this.RIGHT_MOTOR_ONE.configFactoryDefault();
    this.RIGHT_MOTOR_TWO.configFactoryDefault();
    this.LEFT_MOTOR_ONE.setNeutralMode(NeutralMode.Brake);
    this.LEFT_MOTOR_TWO.setNeutralMode(NeutralMode.Brake);
    this.RIGHT_MOTOR_ONE.setNeutralMode(NeutralMode.Brake);
    this.RIGHT_MOTOR_TWO.setNeutralMode(NeutralMode.Brake);
  }

  public void arcadeDrive(Joystick stick) 
  {
    this.DIFF_DRIVE.arcadeDrive(stick.getY(), -stick.getTwist(), false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Roll: " , gyro.getRoll());
    SmartDashboard.putNumber("Yaw: " , gyro.getYaw() % 360);
    SmartDashboard.putNumber("Pitch: " , gyro.getPitch());
  }

  public void stop(){
    DIFF_DRIVE.arcadeDrive(0, 0);
  }

  public double getRoll() {
    return gyro.getRoll();
  }
  public void driveStraight (double speed){
    DIFF_DRIVE.arcadeDrive(speed, 0);
  }

  public void autoBalance() {
    double roll = gyro.getRoll();
    double yaw = Math.abs(gyro.getYaw() % 360);
    double pitch = gyro.getPitch();

    while (Math.abs(roll) <= rollDeadband) { 
      roll = gyro.getRoll();                                       // While the robot is level
      DIFF_DRIVE.arcadeDrive(-0.4, 0);                  // Drive forward
    }
    while (Math.abs(roll) >= rollDeadband) {    
      roll = gyro.getRoll();                                    // At this point, the robot will be at an angle. While it remains at an angle:
      DIFF_DRIVE.arcadeDrive(-0.4, 0);                                    // Drive forward
    }
    return;                   // At this point, the robot should be level agaiN


    // while roll == 0
        // drive forward
    // while roll < 0
        // drive forward
    // return 






    // double roll = gyro.getRoll();                                       // Creates and gets the roll from the gyro
    // double yaw = Math.abs(gyro.getYaw() % 360);                         // Creates and gets the yaw from the gyro
    // int multiplier = 1;                                                 // Direction that the robot is driving
    // double deadband = 0.5;                                              // Sets the deadband in degrees (NOTE: For competition, set to 2.5)
    
    // // If the yaw's percent error is below 0.5 degrees, the robot will consider itself balanced.

    // if (yaw > 180) {                                                    // if the robot is facing to the side
    //     multiplier = -1;                                                // changes direction
    //     System.out.println("greater");
    // }

    // Translation2d translation = new Translation2d(0, 0);           // "Translation" moves the robot anywhere on a 2d plane 
    // double rotation = multiplier * 0.15;     // Gets and creates rotation of the wheels.
    
    // // 0.15 means that the robot drives at only 15% of its max power
    
    // while (yaw > deadband || 360 - yaw < deadband) {                    // While the yaw is above the deadband, i.e. while the robot is not straight
        
    //     yaw = Math.abs(gyro.getYaw() % 360);                            // Repeatedly updates the yaw of the robot
    //     arcadeDrive(RobotContainer.m_joyStick);
    //     //drive(translation, rotation, true, true);       // Drive
    // }
    
    // if (Math.abs(roll) <= rollDeadband) return;                         // If facing forward, then finish the function
  }
}

