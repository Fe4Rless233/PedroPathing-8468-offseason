package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Basic Auto", group = "Autonomous")
public class AutoBasic extends LinearOpMode {

    private Follower follower;
    private IntakeSubsystem intake;
    private final ElapsedTime timer = new ElapsedTime();

    private final Pose startPose = new Pose(7.5, 60.0, 0);
    private final Pose scorePose = new Pose(38.0, 60.0, 0);
    private final Pose intakePose = new Pose(24.0, 36.0, Math.toRadians(180));
    private final Pose parkPose = new Pose(12.0, 12.0, Math.toRadians(90));

    private PathChain scorePath;
    private PathChain intakePath;
    private PathChain parkPath;

    private int state = 0;

    @Override
    public void runOpMode() {
        follower = new FollowerBuilder(RobotConstants.getFollowerConstants(), hardwareMap)
                .pinpointLocalizer(RobotConstants.getPinpointConstants())
                .mecanumDrivetrain(RobotConstants.getMecanumConstants())
                .build();

        intake = new IntakeSubsystem(hardwareMap);

        follower.setStartingPose(startPose);
        buildPaths();

        telemetry.addData("Status", "Ready");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        setNextState(0);

        while (opModeIsActive()) {
            follower.update();

            switch (state) {
                case 0:
                    follower.followPath(scorePath, true);
                    setNextState(1);
                    break;

                case 1:
                    if (!follower.isBusy()) {
                        intake.intake();
                        timer.reset();
                        setNextState(2);
                    }
                    break;

                case 2:
                    if (timer.seconds() > 1.2) {
                        intake.stop();
                        follower.followPath(intakePath, true);
                        setNextState(3);
                    }
                    break;

                case 3:
                    if (!follower.isBusy()) {
                        intake.outtake();
                        timer.reset();
                        setNextState(4);
                    }
                    break;

                case 4:
                    if (timer.seconds() > 1.0) {
                        intake.stop();
                        follower.followPath(parkPath, true);
                        setNextState(5);
                    }
                    break;

                case 5:
                    if (!follower.isBusy()) {
                        setNextState(-1);
                    }
                    break;
            }

            telemetry.addData("State", state);
            telemetry.addData("X", follower.getPose().getX());
            telemetry.addData("Y", follower.getPose().getY());
            telemetry.update();
        }
    }

    private void buildPaths() {
        scorePath = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading())
                .build();

        intakePath = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, intakePose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), intakePose.getHeading())
                .build();

        parkPath = follower.pathBuilder()
                .addPath(new BezierLine(intakePose, parkPose))
                .setLinearHeadingInterpolation(intakePose.getHeading(), parkPose.getHeading())
                .build();
    }

    private void setNextState(int nextState) {
        state = nextState;
    }
}
