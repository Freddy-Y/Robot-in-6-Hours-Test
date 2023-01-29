// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;



import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class DriveTrain extends SubsystemBase {
  public final WPI_TalonSRX LEFT_MOTOR_ONE;
  public final WPI_TalonSRX LEFT_MOTOR_TWO;
  public final WPI_TalonSRX RIGHT_MOTOR_ONE;
  public final WPI_TalonSRX RIGHT_MOTOR_TWO;
  private final MotorControllerGroup leftControllerGroup;
  private final MotorControllerGroup rightControllerGroup;
  public final DifferentialDrive DIFF_DRIVE;

  

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
  }

}

