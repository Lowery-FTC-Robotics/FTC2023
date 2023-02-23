package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;


@Autonomous(name = "Encoders", group = "")
public class Encoders extends LinearOpMode {

  private ColorSensor color;
  private Servo servo;
  private Servo servo2;
  private DcMotor front_left;
  private DcMotor back_left;
  private DcMotor front_right;
  private DcMotor back_right;
  private DcMotor arm_motor1;
  private DcMotor arm_motor2;
  private DcMotor arm_motor3; 
  private DcMotor arm;
  
  /**
  * This function is executed when this Op Mode is selected from the Driver Station.
  */
  @Override
  public void runOpMode() {
    double blue;
    double green;
    double red;
    double thresh = 1.2;
    double ratio;
    
    color = hardwareMap.colorSensor.get("color");
    servo = hardwareMap.servo.get("servo");
    servo2 = hardwareMap.servo.get("servo2");
    //servo.setDirection(Servo.Direction.REVERSE);
    front_left = hardwareMap.dcMotor.get("front_left");
    front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    back_left = hardwareMap.dcMotor.get("back_left");
    back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    front_right = hardwareMap.dcMotor.get("front_right");
    front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    back_right = hardwareMap.dcMotor.get("back_right");
    back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    arm_motor1 = hardwareMap.dcMotor.get("arm_motor1");
    arm_motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    arm_motor2 = hardwareMap.dcMotor.get("arm_motor2");
    arm_motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    arm_motor2.setDirection(DcMotor.Direction.REVERSE);

    front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      if (opModeIsActive()) {
        servo.setPosition((double) 0.45);
        servo2.setPosition((double) 1);
        sleep(2000);
        
        arm_motor1.setPower(-0.75);
        arm_motor2.setPower(-0.75);
        sleep(900);
        arm_motor1.setPower(0);
        arm_motor2.setPower(0);
        
        forward(500);
        strafeleft(3500);
        fin();
        red = color.red();
        green = color.green();
        blue = color.blue();
        ratio = red/green;
        
        telemetry.addData("Blue", color.blue());
        telemetry.addData("Green", color.green());
        telemetry.addData("Red", color.red());
        telemetry.update();
        
        arm_motor1.setPower(-0.75);
        arm_motor2.setPower(-0.75);
        sleep(900);
        arm_motor1.setPower(0);
        arm_motor2.setPower(0);
        forward(500);
        strafeleft(3900);
        arm_motor1.setPower(-0.75);
        arm_motor2.setPower(-0.75);
        sleep(800);
        arm_motor1.setPower(0);
        arm_motor2.setPower(0);
        slowforward(850);
        arm_motor1.setPower(0.75);
        arm_motor2.setPower(0.75);
        sleep(500);
        arm_motor1.setPower(0);
        arm_motor2.setPower(0);
        servo.setPosition((double) 1);
        servo2.setPosition((double) 0.55);
        sleep(2000);
        backward(700);
        straferight(1200);
        if (blue > green &&  blue > red) {
          telemetry.addData("color", 3);
          telemetry.addData("Action: ", "Blue Parking");
          forward(2650);
        }
        // else if (ratio > thresh) {
        //   telemetry.addData("Action: ", "Red Parking");
        //   telemetry.update();
        //   telemetry.addData("color", 1);
        // }
        else if (ratio < thresh) {
          telemetry.addData("color", 2);
          telemetry.addData("Action: ", "Green Parking");
          telemetry.update();
          backward(300);
          
        }
      }
    }
  }
  void straferight(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(-pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    front_right.setTargetPosition(-pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(1);
    front_left.setPower(-1);
    back_left.setPower(1);
    front_right.setPower(-1);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){

  }
  fin();
  }
  void strafeleft(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    
    back_left.setTargetPosition(-pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    front_right.setTargetPosition(pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(-pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    back_right.setPower(-1);
    front_left.setPower(1);
    front_right.setPower(-1);
    back_left.setPower(1);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){

  }
  fin();
  }
  void slowstrafeleft(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(-pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    front_right.setTargetPosition(pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(-pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(-0.5);
    front_left.setPower(0.5);
    back_left.setPower(-0.5);
    front_right.setPower(0.5);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){

  }
  fin();
  }
  void backward(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(-pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(-pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    

    front_right.setTargetPosition(pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(1);
    front_left.setPower(1);
    back_left.setPower(-1);
    front_right.setPower(1);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){
  }
  fin();
  }
  void slowbackward(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(-pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(-pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    front_right.setTargetPosition(pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(0.5);
    front_left.setPower(-0.5);
    back_left.setPower(-0.5);
    front_right.setPower(0.5);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){
  
  }
  fin();
  }
  void forward(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    

    front_right.setTargetPosition(-pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(-pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(-1);
    front_right.setPower(-1);
    back_left.setPower(1);
    front_left.setPower(1);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){
  
  }
  fin();
  }
  void slowforward(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    front_right.setTargetPosition(-pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(-pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(-0.5);
    front_left.setPower(0.5);
    back_left.setPower(0.5);
    front_right.setPower(-0.5);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){
  
  }
  fin();
  }
  void right(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(-pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(-pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    front_right.setTargetPosition(-pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(-pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(-1);
    front_left.setPower(-1);
    back_left.setPower(-1); 
    front_right.setPower(-1);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){
  
  }
  fin();
  }
  void left(int pos){
    front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    front_left.setTargetPosition(pos);
    front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    
    back_left.setTargetPosition(pos);
    back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    front_right.setTargetPosition(pos);
    front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    back_right.setTargetPosition(pos);
    back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    back_right.setPower(1);
    front_left.setPower(1);
    back_left.setPower(1);
    front_right.setPower(1);
  while(front_left.isBusy() && back_left.isBusy() && front_right.isBusy() && back_right.isBusy()){
  
  }
  fin();
  }
  void fin(){
    back_right.setPower(0);
    back_left.setPower(0);
    front_left.setPower(0);
    front_right.setPower(0);
  }
  
}




