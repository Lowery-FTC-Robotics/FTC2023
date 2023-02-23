package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.robot.RobotState;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "Bettercode")
public class Bettercode extends LinearOpMode {
    private DcMotor lf;
    private DcMotor lb;
    private DcMotor rf;
    private DcMotor rb;
    private DcMotor llift;
    private DcMotor rlift;
    private Servo arm1;
    private Servo arm2;
    private Servo wrist;
    private Servo clawRotate;
    private Servo claw;
    private Gamepad currentGamepad1;
    
    public enum RobotState {
        MANUAL,
        INTAKE,
        MOVE,
        LOW,
        MEDIUM,
        HIGH
    };
    
    @Override
    public void runOpMode() {
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        RobotState robotState = RobotState.MOVE;
        double x;
        double y;
        double rx;
        float RightTrigger;
        float LeftTrigger;
        
        int liftHigh = 2170;
        int liftMedium = 1050;
        int liftLow = 0;
        
        double wristIntake = 0.5;
        double wristMove = 0.5;
        double wristHigh = 0.5;
        double wristMedium = 0.5;
        double wristLow = 0.5;
        
        boolean isClawClose = false;
        double clawOpen = 0.18;
        double clawClose = 0.6;
        
        double rotateBack = 0.18;
        double rotateFront = 0.85;
        
        double chassisMult = 0.95;
        
        lf = hardwareMap.get(DcMotor.class, "lf");
        rb = hardwareMap.get(DcMotor.class, "rb");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rf = hardwareMap.get(DcMotor.class, "rf");
        llift = hardwareMap.get(DcMotor.class, "llift");
        rlift = hardwareMap.get(DcMotor.class, "rlift");
        arm1 = hardwareMap.get(Servo.class, "arm1");
        arm2 = hardwareMap.get(Servo.class, "arm2");
        wrist = hardwareMap.get(Servo.class, "wrist");
        clawRotate = hardwareMap.get(Servo.class, "clawRotate");
        claw = hardwareMap.get(Servo.class, "claw");
        
        llift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        llift.setPower(0.95);
        rlift.setPower(0.95);
        llift.setTargetPosition(0);
        rlift.setTargetPosition(0);
        llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
        llift.setDirection(DcMotorSimple.Direction.REVERSE);
        
        waitForStart();
        claw.setPosition(clawOpen);
        while(opModeIsActive()) {
            try {
                previousGamepad1.copy(currentGamepad1);
                currentGamepad1.copy(gamepad1);
            } catch (Exception e) {
                System.out.println(e);
            }
            if(gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_right || gamepad1.dpad_left || gamepad1.right_trigger !=0 || gamepad1.right_trigger != 0) {
                robotState = RobotState.MANUAL;
            }
            else if(gamepad1.left_bumper) robotState = RobotState.INTAKE;
            else if(gamepad1.a) robotState = RobotState.MOVE;
            else if(gamepad1.b) robotState = RobotState.LOW;
            else if(gamepad1.y) robotState = RobotState.MEDIUM;
            else if(gamepad1.x) robotState = RobotState.HIGH;
            telemetry.addData("previous bumper", !previousGamepad1.right_bumper);
            telemetry.addData("current bumper", currentGamepad1.right_bumper);
            if(currentGamepad1.right_bumper && !previousGamepad1.right_bumper) {
                if(isClawClose) {
                    if(robotState == RobotState.HIGH || robotState == RobotState.MEDIUM || robotState == RobotState.LOW) {
                        wrist.setPosition(wrist.getPosition()-0.1);
                        sleep(100);
                        claw.setPosition(clawOpen);
                        robotState = RobotState.MOVE;
                    }
                    else {
                        claw.setPosition(clawOpen);
                    }
                    isClawClose = false;
                }
                else {
                    if(robotState == RobotState.INTAKE) {
                        claw.setPosition(clawClose);
                        sleep(300);
                        robotState = RobotState.MOVE;
                    }
                        else {
                        claw.setPosition(clawClose);
                    }
                    isClawClose = true;
                }
            }
            if (gamepad1.dpad_up) {
                wrist.setPosition(0.3);
            }
            if (gamepad1.dpad_down) {
                wrist.setPosition(0.5);
            }
            switch (robotState) {
                case MANUAL:
                    llift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    rlift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    llift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    rlift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    if(gamepad1.right_trigger > 0) {
                        llift.setPower(gamepad1.right_trigger);
                        rlift.setPower(gamepad1.right_trigger);
                    }
                    else if(gamepad1.left_trigger > 0) {
                        llift.setPower(-gamepad1.left_trigger);
                        rlift.setPower(-gamepad1.left_trigger);
                    }
                    else {
                        llift.setPower(0);
                        rlift.setPower(0);
                    }
                    break;
                case HIGH:
                    llift.setPower(0.95);
                    rlift.setPower(0.95);
                    llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    chassisMult = 0.5;
                    arm1.setPosition(0.25);
                    arm2.setPosition(0.75);
                    llift.setTargetPosition(liftHigh);
                    rlift.setTargetPosition(liftHigh);
                    wrist.setPosition(wristHigh);
                    clawRotate.setPosition(rotateFront);
                    telemetry.addData("llift: ", llift.getCurrentPosition());
                    telemetry.addData("rlift: ", rlift.getCurrentPosition());
                    break;
                case MEDIUM:
                    llift.setPower(0.95);
                    rlift.setPower(0.95);
                    llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    chassisMult = 0.7;
                    arm1.setPosition(0.25);
                    arm2.setPosition(0.75);
                    llift.setTargetPosition(liftMedium);
                    rlift.setTargetPosition(liftMedium);
                    wrist.setPosition(wristMedium);
                    clawRotate.setPosition(rotateFront);
                    break;
                case LOW:
                    llift.setPower(0.85);
                    rlift.setPower(0.85);
                    llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    chassisMult = 0.9;
                    arm1.setPosition(0.22);
                    arm2.setPosition(0.78);
                    llift.setTargetPosition(liftLow);
                    rlift.setTargetPosition(liftLow);
                    wrist.setPosition(wristLow);
                    clawRotate.setPosition(rotateFront);
                    break;
                case MOVE:
                    llift.setPower(0.95);
                    rlift.setPower(0.95);
                    llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    chassisMult = 0.95;
                    arm1.setPosition(0.35);
                    arm2.setPosition(0.65);
                    llift.setTargetPosition(0);
                    rlift.setTargetPosition(0);
                    wrist.setPosition(wristMove);
                    clawRotate.setPosition(rotateFront);
                    break;
                case INTAKE:
                    llift.setPower(0.9);
                    rlift.setPower(0.9);
                    llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    chassisMult = 0.9;
                    claw.setPosition(clawOpen);
                    isClawClose = false;
                    sleep(100);
                    arm1.setPosition(0.96);
                    arm2.setPosition(0.04);
                    llift.setTargetPosition(0);
                    rlift.setTargetPosition(0);
                    wrist.setPosition(wristIntake);
                    clawRotate.setPosition(rotateBack);
                    break;
            }
            x = gamepad1.right_stick_x*0.85;
            y = gamepad1.left_stick_y;
            rx = gamepad1.left_stick_x * 1.1;
            lf.setPower(((y - x) - rx)*chassisMult);
            rb.setPower(((y + x) - rx)*chassisMult);
            rf.setPower((y + x + rx)*chassisMult);
            lb.setPower(((y - x) + rx)*chassisMult);
            telemetry.update();
        }
    }
}

















package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "FinalRight4", group = "")
public class FinalRight4 extends LinearOpMode {
  private ColorSensor color;
  private Blinker control_Hub;
  private Blinker expansion_Hub_2;
  private Servo claw;
  private Servo arm1;
  private Servo arm2;
  private Servo wrist;
  private Servo clawRotate;
  private Gyroscope imu_1;
  private Gyroscope imu;
  private DcMotor lb;
  private DcMotor lf;
  private DcMotor llift;
  private DcMotor rb;
  private DcMotor rf;
  private DcMotor rlift;
  
  @Override
  public void runOpMode() {
    double blue;
    double green;
    double red;
    
    color = hardwareMap.colorSensor.get("color");
    lf = hardwareMap.get(DcMotor.class, "lf");
    rb = hardwareMap.get(DcMotor.class, "rb");
    lb = hardwareMap.get(DcMotor.class, "lb");
    rf = hardwareMap.get(DcMotor.class, "rf");
    llift = hardwareMap.get(DcMotor.class, "llift");
    rlift = hardwareMap.get(DcMotor.class, "rlift");
    arm1 = hardwareMap.get(Servo.class, "arm1");
    arm2 = hardwareMap.get(Servo.class, "arm2");
    wrist = hardwareMap.get(Servo.class, "wrist");
    clawRotate = hardwareMap.get(Servo.class, "clawRotate");
    claw = hardwareMap.get(Servo.class, "claw");
    
    llift.setDirection(DcMotorSimple.Direction.REVERSE);
    rf.setDirection(DcMotorSimple.Direction.REVERSE);
    rb.setDirection(DcMotorSimple.Direction.REVERSE);
    /*
    rlift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    llift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    lb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rb.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    */
    llift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rlift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    llift.setPower(0.8);
    rlift.setPower(0.8);
    lf.setPower(0.4);
    rf.setPower(0.42);
    lb.setPower(0.42);
    rb.setPower(0.42);
    
    llift.setTargetPosition(0);
    rlift.setTargetPosition(0);
    lf.setTargetPosition(0);
    lb.setTargetPosition(0);
    rf.setTargetPosition(0);
    rb.setTargetPosition(0);
    
    llift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rlift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    double clawOpen = 0.18;
    double clawClose = 0.59;
    
    // Put initialization blocks here.
    claw(clawOpen);
    arm1.setPosition(0.4);
    arm2.setPosition(0.6);
    wrist.setPosition(0.25);
    clawRotate.setPosition(0.85);
    sleep(3000);
    claw(clawClose);
    sleep(1000);
    wrist.setPosition(0.1);
    arm1.setPosition(0.35);
    arm2.setPosition(0.65);
    waitForStart();
    if (opModeIsActive()) {
      //strafe
      lf.setTargetPosition(150);
      rf.setTargetPosition(-150);
      lb.setTargetPosition(-150);
      rb.setTargetPosition(150);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(200);
      //forward
      lf.setTargetPosition(950);
      rf.setTargetPosition(550);
      lb.setTargetPosition(550);
      rb.setTargetPosition(950);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(600);
      
      red = color.red();
      green = color.green();
      blue = color.blue();
      sleep(200);
      //forward
      lf.setPower(0.25);
      rf.setPower(0.25);
      lb.setPower(0.25);
      rb.setPower(0.25);
      lf.setTargetPosition(2200);
      rf.setTargetPosition(1800);
      lb.setTargetPosition(2200);
      rb.setTargetPosition(1800);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(200);
      //turn
      lf.setPower(0.4);
      rf.setPower(0.4);
      lb.setPower(0.4);
      rb.setPower(0.4);
      lf.setTargetPosition(2400);
      rf.setTargetPosition(1800);
      lb.setTargetPosition(2400);
      rb.setTargetPosition(1800);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(200);
      //back & turn right
      lf.setTargetPosition(2200);
      rf.setTargetPosition(1500);
      lb.setTargetPosition(2200);
      rb.setTargetPosition(1500);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(200);
      
      wrist.setPosition(0.55);
      arm1.setPosition(0.25);
      arm2.setPosition(0.75);
      llift.setTargetPosition(2250);
      rlift.setTargetPosition(2250);
      while(llift.isBusy() || rlift.isBusy()) {}
      sleep(1000);
      
      wrist.setPosition(0.4);
      sleep(700);
      
      claw.setPosition(clawOpen);
      sleep(200);
      
      wrist.setPosition(0.5);
      arm1.setPosition(0.25);
      arm2.setPosition(0.75);
      llift.setTargetPosition(0);
      rlift.setTargetPosition(0);
      while(llift.isBusy() || rlift.isBusy()) {}
      sleep(200);
      //turn right
      lf.setTargetPosition(2100);
      rf.setTargetPosition(2100);
      lb.setTargetPosition(2100);
      rb.setTargetPosition(2100);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(200);
      
      lf.setTargetPosition(2200);
      rf.setTargetPosition(2000);
      lb.setTargetPosition(2000);
      rb.setTargetPosition(2200);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(200);
      
      lf.setTargetPosition(1050);
      rf.setTargetPosition(950);
      lb.setTargetPosition(950);
      rb.setTargetPosition(1100);
      while(lf.isBusy() || lb.isBusy() || rf.isBusy() || rb.isBusy()) {}
      sleep(200);
      
      claw(clawClose);
      sleep(500);
      wrist.setPosition(0.1);
      arm1.setPosition(0.35);
      arm2.setPosition(0.65);
      sleep(600);
      
      if (blue > green &&  blue > red) {
        telemetry.addData("color", 3);
        lf.setTargetPosition(2300);
        rf.setTargetPosition(-300);
        lb.setTargetPosition(-200);
        rb.setTargetPosition(2300);
      }
      else if (red > green &&  red > blue) {
        telemetry.addData("color", 1);
        lf.setTargetPosition(-300);
        rf.setTargetPosition(2250);
        lb.setTargetPosition(2200);
        rb.setTargetPosition(0);
      }
      else if (green > blue &&  green > red) {
        telemetry.addData("color", 2);
        sleep(200);
      }
      while(opModeIsActive()) {}
    }
  }
  void claw(double pos) {
    claw.setPosition(pos);
    sleep(400);
  }
  void forward(int pos, double pw) {
    
  }
  void back(int pos, double pw) {
    
  }
  void left(int pos, double pw) {
    
  }
  void right(int pos, double pw) {
    
  }
  void rturn(int pos, double pw) {
    
  }
  void lturn(int pos, double pw) {
    
  }
}


