package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp Mecanum", group = "TeleOp")
public class TeleOpMecanum extends LinearOpMode {

    private Follower follower;
    private IntakeSubsystem intake;
    private boolean isRobotCentric = false;

    @Override
    public void runOpMode() {
        follower = new FollowerBuilder(RobotConstants.getFollowerConstants(), hardwareMap)
                .pinpointLocalizer(RobotConstants.getPinpointConstants())
                .mecanumDrivetrain(RobotConstants.getMecanumConstants())
                .build();

        intake = new IntakeSubsystem(hardwareMap);

        follower.startTeleOpDrive();
        follower.setStartingPose(new Pose(0, 0, 0));

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double drive = -gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;

            if (gamepad1.right_bumper) {
                drive *= 0.4;
                strafe *= 0.4;
                turn *= 0.4;
            }

            if (gamepad1.options) {
                follower.setStartingPose(new Pose(0, 0, 0));
            }

            follower.setTeleOpDrive(drive, strafe, turn, isRobotCentric);
            follower.update();

            double inTrigger = gamepad1.right_trigger;
            double outTrigger = gamepad1.left_trigger;

            if (inTrigger > 0.1) {
                intake.setPower(inTrigger);
            } else if (outTrigger > 0.1) {
                intake.setPower(-outTrigger);
            } else {
                intake.stop();
            }

            Pose pose = follower.getPose();
            telemetry.addData("X", pose.getX());
            telemetry.addData("Y", pose.getY());
            telemetry.addData("Heading (deg)", Math.toDegrees(pose.getHeading()));
            telemetry.update();
        }
    }
}
