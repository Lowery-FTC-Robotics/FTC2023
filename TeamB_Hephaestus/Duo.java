package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//import com.qualcomm.robotcore.hardware.DcMotorSimple$Direction;
import java.util.logging.Logger;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

// public class CreateFile {
// public static void main(String[] args) {
// try {
// File my
// }
// }
// }
@TeleOp(name = "Duo", group = "")
public class Duo extends LinearOpMode {
private Servo servo;
private DcMotor front_left;
private DcMotor back_left;
private DcMotor front_right;
private DcMotor back_right;
private DcMotor arm_motor1;
private DcMotor arm_motor2;
private DcMotor arm_motor3;
private DcMotor arm;
private Servo servo2;
/**
* This function is executed when this Op Mode is selected from the Driver Station.
*/
@Override
public void runOpMode() {
servo = hardwareMap.servo.get("servo");
front_left = hardwareMap.dcMotor.get("front_left");
back_left = hardwareMap.dcMotor.get("back_left");
front_right = hardwareMap.dcMotor.get("front_right");
back_right = hardwareMap.dcMotor.get("back_right");
arm_motor1 = hardwareMap.dcMotor.get("arm_motor1");

arm_motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

arm_motor2 = hardwareMap.dcMotor.get("arm_motor2");
arm_motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
arm_motor2.setDirection(DcMotor.Direction.REVERSE);
servo2 = hardwareMap.servo.get("servo2");
servo.setDirection(Servo.Direction.REVERSE);
int degree;
// servo.setDirection(Servo.Direction BACKWARD);
// Put initialization blocks here.
waitForStart();
if (opModeIsActive()) {
    
//     String dir = "/storage/emulated/0/FIRST/";//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
// File file = new File(dir+"model.tflite");
// // try  {
// //     file.createNewFile();
// //     System.out.println("hi");
// // }
// // catch (IOException e){
// //     telemetry.addData("no body cares: ", e);
// //     telemetry.update();
// //     sleep(100000);
// // }

// //Write to file
// try (FileWriter fileWriter = new FileWriter(file)) {
// fileWriter.append("Writing to file!");
// telemetry.addData("idk ","works");
// } catch (IOException e) {
//     //Handle exception
// telemetry.addData("exception ", e);
// }
//     telemetry.update();
    
    
    
// Put run blocks here.
while (opModeIsActive()) {
// Put loop blocks here.
// sleep(10000000);
telemetry.update();
telemetry.addData("key", servo.getDirection());
// Servo
if (gamepad2.right_bumper){
servo.setPosition((double) 0.45);
servo2.setPosition((double) 1);
}
if (gamepad2.left_bumper){
servo.setPosition((double) 0);
servo2.setPosition((double) 0.55);
}
// Left and Right
front_left.setPower(-1 * gamepad1.right_stick_x);
back_left.setPower(-1 * gamepad1.right_stick_x);
front_right.setPower(-1 * gamepad1.right_stick_x);
back_right.setPower(-1 * gamepad1.right_stick_x);
// Forward/Backwards turn
front_left.setPower(-1 * gamepad1.left_trigger);
back_left.setPower(-1 * gamepad1.left_trigger);
front_right.setPower(1 * gamepad1.left_trigger);
back_right.setPower(1 * gamepad1.left_trigger);
// Forward/Backwards turn
front_left.setPower(1 * gamepad1.right_trigger);
back_left.setPower(1 * gamepad1.right_trigger);
front_right.setPower(-1 * gamepad1.right_trigger);

back_right.setPower(-1 * gamepad1.right_trigger);

//Precision Control Forward
if (gamepad1.a){
front_left.setPower(-0.5);
back_left.setPower(-0.5);
front_right.setPower(0.5);
back_right.setPower(0.5);
}
else{
front_left.setPower(0);
back_left.setPower(0);
front_right.setPower(0);
back_right.setPower(0);
}
//Precision Control Backward
if (gamepad1.y){
front_left.setPower(0.5);
back_left.setPower(0.5);
front_right.setPower(-0.5);
back_right.setPower(-0.5);
}
else{
front_left.setPower(0);
back_left.setPower(0);
front_right.setPower(0);
back_right.setPower(0);
}
if (gamepad1.x){
front_left.setPower(0.5);
back_left.setPower(0.5);
front_right.setPower(0.5);
back_right.setPower(0.5);
}
else{
front_left.setPower(0);
back_left.setPower(0);
front_right.setPower(0);
back_right.setPower(0);
}
if (gamepad1.b){
front_left.setPower(-0.5);
back_left.setPower(-0.5);
front_right.setPower(-0.5);
back_right.setPower(-0.5);

}

else{
front_left.setPower(0);
back_left.setPower(0);
front_right.setPower(0);
back_right.setPower(0);
}
// Right Strafe
arm_motor1.setPower(0.75 * gamepad2.right_stick_y);
arm_motor2.setPower(0.75 * gamepad2.right_stick_y);
//arm_motor3.setPower(1 * gamepad1.left_stick_y);
//arm.setPower(1* gamepad1.left_stick_y);
// Arm
if(gamepad1.dpad_right){
front_left.setPower(-1);
back_left.setPower(1);
front_right.setPower(-1);
back_right.setPower(1);
}
else{
front_left.setPower(0);
back_left.setPower(0);
front_right.setPower(0);
back_right.setPower(0);
}
if(gamepad1.dpad_left){
front_left.setPower(1);
back_left.setPower(-1);
front_right.setPower(1);
back_right.setPower(-1);
}
else{
front_left.setPower(0);
back_left.setPower(0);
front_right.setPower(0);
back_right.setPower(0);
}
}
}
}
}

