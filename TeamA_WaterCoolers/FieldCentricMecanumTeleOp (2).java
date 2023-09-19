package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp

public class FieldCentricMecanumTeleOp extends LinearOpMode {
    
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private DcMotor arm;
    private DcMotor arm2;
    private Servo claw;
    private BNO055IMU imu;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure you ID's match your configuration
        frontLeftMotor = hardwareMap.dcMotor.get("front left");
        backLeftMotor = hardwareMap.dcMotor.get("back left");
        frontRightMotor = hardwareMap.dcMotor.get("front right");
        backRightMotor = hardwareMap.dcMotor.get("back right");
        arm = hardwareMap.dcMotor.get("crane motor");
        arm2 = hardwareMap.dcMotor.get("crane motor2");
        claw = hardwareMap.servo.get("servo");
        
        // Reverse the right side motors
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        
        // Retrieve the IMU from the hardware map
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technially this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws and exception 
        imu.initialize(parameters);
        
        waitForStart();
        
        if (isStopRequested()) return;
        
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing 
            double rx = gamepad1.right_stick_x;
            
            // Read inverse IMU heading, as the IMU heading is CW positive
            double botHeading = -imu.getAngularOrientation().firstAngle;
            
            double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
            
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = -(rotY + rotX + rx) / denominator;
            double backLeftPower = -(rotY - rotX + rx) / denominator;
            double frontRightPower = -(rotY - rotX - rx) / denominator;
            double backRightPower = -(rotY + rotX - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            
            if (gamepad1.right_trigger > 0) {
                // arm.setZeroPowerBehavior(ZeroPowerBehavior.FLOAT);
                // arm2.setZeroPowerBehavior(ZeroPowerBehavior.FLOAT);
                arm.setPower(1);
                arm2.setPower(-1);
            }else if (gamepad1.left_trigger > 0) {
                // arm.setZeroPowerBehavior(ZeroPowerBehavior.FLOAT);
                // arm2.setZeroPowerBehavior(ZeroPowerBehavior.FLOAT);
                arm.setPower(-1);
                arm2.setPower(1);
            }else{
                arm.setPower(0);
                arm2.setPower(0);
            }
            if (gamepad1.right_bumper) {
                claw.setPosition(0.35);
            }
            if (gamepad1.left_bumper) {
                claw.setPosition(0.1);
            }
            
            if (gamepad1.dpad_up) {
                frontLeftMotor.setPower(-0.4);
                backLeftMotor.setPower(-0.4);
                frontRightMotor.setPower(-0.4);
                backRightMotor.setPower(-0.4);
            }
            if (gamepad1.dpad_down) {
                frontLeftMotor.setPower(0.4);
                backLeftMotor.setPower(0.4);
                frontRightMotor.setPower(0.4);
                backRightMotor.setPower(0.4);
            }
            if (gamepad1.dpad_right) {
                frontLeftMotor.setPower(-0.4);
                backLeftMotor.setPower(0.4);
                frontRightMotor.setPower(0.4);
                backRightMotor.setPower(-0.4);
            }
            if (gamepad1.dpad_left) {
                frontLeftMotor.setPower(0.4);
                backLeftMotor.setPower(-0.4);
                frontRightMotor.setPower(-0.4);
                backRightMotor.setPower(0.4);
            }
        }
    }
}
