package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import java.util.HashSet;
import java.util.Set;

public class BalanceCommand implements Command 
{

  private double error;
  private double currentAngle;
  private double drivePower;

  public BalanceCommand() {}

  @Override
  public Set<Subsystem> getRequirements() 
  {
    Set<Subsystem> list = new HashSet<Subsystem>();
    list.add(RobotContainer.m_driveTrain);
    return list;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    this.currentAngle = RobotContainer.m_driveTrain.getRoll();

    //error is balanced subtracted from the current angle
    error = 0.9 - currentAngle;

    drivePower = -Math.min(Constants.kPGyro * error, 1);

    //just in case im stupid and this will stop us from being bing banged to the hospital
    if (Math.abs(drivePower) > 0.6) {
        drivePower = Math.copySign(0.5, drivePower);
    }

    RobotContainer.m_driveTrain.driveStraight(drivePower);


    System.out.println("Current Angle" + currentAngle);
    System.out.println("Error " + error);
    System.out.println("Drive Power " + drivePower);

  }
    
  @Override
  public boolean isFinished() {

    //Actual Thing
    return Math.abs(error) > 0 && Math.abs(error) < 1.5; 
    
    //Fake test because of stupid gyro.
    //return Math.abs(error) == 1; 
  }

  @Override
  public void end(boolean interrupted) {
      RobotContainer.m_driveTrain.stop();
  }
}