package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotConstants {

    public static PinpointConstants getPinpointConstants() {
        return new PinpointConstants()
                .hardwareMapName("pinpoint")
                .forwardPodY(1.0)
                .strafePodX(-2.5)
                .distanceUnit(DistanceUnit.INCH)
                .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
                .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
                .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    }

    public static MecanumConstants getMecanumConstants() {
        return new MecanumConstants()
                .leftFrontMotorName("leftFront")
                .leftRearMotorName("leftRear")
                .rightFrontMotorName("rightFront")
                .rightRearMotorName("rightRear")
                .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
                .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
                .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
                .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);
    }

    public static FollowerConstants getFollowerConstants() {
        FollowerConstants constants = new FollowerConstants();
        constants.mass = 12.0;
        return constants;
    }

    public static final String INTAKE_MOTOR_NAME = "intakeMotor";
    public static final double INTAKE_POWER_IN = 0.90;
    public static final double INTAKE_POWER_OUT = -0.80;
}
