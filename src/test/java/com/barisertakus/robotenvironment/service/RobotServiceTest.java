package com.barisertakus.robotenvironment.service;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.dto.ScriptDTO;
import com.barisertakus.robotenvironment.entity.Robot;
import com.barisertakus.robotenvironment.enums.Direction;
import com.barisertakus.robotenvironment.repository.RobotRepository;
import com.barisertakus.robotenvironment.service.Impl.RobotServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotServiceTest {

    RobotRepository robotRepositoryMock;
    ModelMapper modelMapper;
    RobotServiceImpl robotService;

    @Before
    public void init() {
        robotRepositoryMock = mock(RobotRepository.class);
        modelMapper = new ModelMapper();
        robotService = new RobotServiceImpl(robotRepositoryMock, modelMapper);
        ReflectionTestUtils.setField(robotService, "ARENA_HEIGHT", 5);
        ReflectionTestUtils.setField(robotService, "ARENA_WIDTH", 5);

    }

    @Test
    public void givenScript_whenLengthGreaterThanLimit_thenThrowScriptError() {
        ScriptDTO script = createScript("SCRIPT LENGTH 3");
        Throwable throwable = catchThrowable(() -> robotService.executeScript(script));
        checkIncorrectScript(throwable);
    }

    @Test
    public void givenScript_whenEmpty_thenThrowScriptError() {
        ScriptDTO script = createScript("");
        Throwable throwable = catchThrowable(() -> robotService.executeScript(script));
        checkIncorrectScript(throwable);
    }

    @Test
    public void givenScript_whenNull_thenThrowScriptError() {
        Throwable throwable = catchThrowable(() -> robotService.executeScript(null));
        checkIncorrectScript(throwable);
    }

    @Test
    public void givenScript_whenPositionWithoutNumber_thenThrowScriptError() {
        ScriptDTO script = createScript("POSITION");
        Throwable throwable = catchThrowable(() -> robotService.executeScript(script));
        checkIncorrectScript(throwable);
    }

    @Test
    public void givenScript_whenPositionCorrect_thenReturnInitialStatus() {
        ScriptDTO script = createScript("POSITION 1");
        Robot robot = createRobot();
        when(robotRepositoryMock.findTop1By()).thenReturn(robot);
        RobotDTO robotDTO = robotService.executeScript(script);

        assertThat(robotDTO.getXCoordinate())
                .as("Position 1 command expect X is 0")
                .isEqualTo(0);

        assertThat(robotDTO.getYCoordinate())
                .as("Position 1 command expect Y is 0")
                .isEqualTo(0);

        assertThat(robotDTO.getDirection())
                .as("Position 1 command expect direction is right")
                .isEqualTo(Direction.RIGHT);

        assertThat(robotDTO.getTurnAround())
                .as("Position 1 command expect turn around is false")
                .isEqualTo(false);

    }

    @Test
    public void givenScript_whenWaitWithMoreCommands_thenThrowScriptError() {
        ScriptDTO script = createScript("WAIT MORE");
        Throwable throwable = catchThrowable(() -> robotService.executeScript(script));
        checkIncorrectScript(throwable);
    }

    @Test
    public void givenScript_whenWaitWithoutCommand_thenReturnTurnAroundFalse() {
        ScriptDTO script = createScript("WAIT");
        Robot robot = createRobot(); // TurnAround is true by default for this case
        when(robotRepositoryMock.findTop1By()).thenReturn(robot);
        RobotDTO robotDTO = robotService.executeScript(script);

        assertThat(robotDTO.getTurnAround())
                .as("TurnAround command expect turnAround is false")
                .isEqualTo(false);
    }

    @Test
    public void givenScript_whenTurnAroundWithMoreCommands_thenThrowScriptError() {
        ScriptDTO script = createScript("TURNAROUND MORE");
        Throwable throwable = catchThrowable(() -> robotService.executeScript(script));
        checkIncorrectScript(throwable);
    }

    @Test
    public void givenScript_whenTurnAroundWithoutCommand_thenReturnOppositeTurnAround() {
        ScriptDTO script = createScript("TURNAROUND");
        Robot robot = createRobot();
        when(robotRepositoryMock.findTop1By()).thenReturn(robot);
        RobotDTO robotDTO = robotService.executeScript(script);

        assertThat(robotDTO.getTurnAround())
                .as("WAIT command expect turnAround is false")
                .isEqualTo(false);
    }

    @Test
    public void givenScript_whenDirectionWithMoreCommands_thenThrowScriptError() {
        ScriptDTO script = createScript("DOWN MORE");
        Throwable throwable = catchThrowable(() -> robotService.executeScript(script));
        checkIncorrectScript(throwable);
    }

    @Test
    public void givenScript_whenDirectionDownWithoutCommand_thenReturnChangeDirectionAsDown() {
        ScriptDTO script = createScript("DOWN");
        Robot robot = createRobot(); // Direction is left by default for this case
        when(robotRepositoryMock.findTop1By()).thenReturn(robot);
        RobotDTO robotDTO = robotService.executeScript(script);

        assertThat(robotDTO.getDirection())
                .as("DOWN command expect direction is down")
                .isEqualTo(Direction.DOWN);
    }

    @Test
    public void givenScript_whenDirectionRightWithoutCommand_thenReturnChangeDirectionAsRight() {
        ScriptDTO script = createScript("RIGHT");
        Robot robot = createRobot(); // TurnAround is true by default for this case
        when(robotRepositoryMock.findTop1By()).thenReturn(robot);
        RobotDTO robotDTO = robotService.executeScript(script);

        assertThat(robotDTO.getDirection())
                .as("RIGHT command expect direction is right")
                .isEqualTo(Direction.RIGHT);
    }

    @Test
    public void givenScript_whenForwardOneStep_thenDecreaseXPosition() {
        Robot robot = createRobot(); // X is 2 by default for this case
        ScriptDTO script = createScript("FORWARD 1");
        when(robotRepositoryMock.findTop1By()).thenReturn(robot);
        RobotDTO robotDTO = robotService.executeScript(script);

        assertThat(robotDTO.getXCoordinate())
                .as("FORWARD 1 command expect X is 1")
                .isEqualTo(1);
    }

    @Test
    public void givenScript_whenForwardTwoStep_thenDecreaseXPosition() {
        Robot robot = createRobot(); // X is 2 by default for this case
        ScriptDTO script = createScript("FORWARD 2");
        when(robotRepositoryMock.findTop1By()).thenReturn(robot);
        RobotDTO robotDTO = robotService.executeScript(script);

        assertThat(robotDTO.getXCoordinate())
                .as("FORWARD 2 command expect X is 0")
                .isEqualTo(0);
    }

    private void checkIncorrectScript(Throwable throwable) {
        assertThat(throwable)
                .as("Incorrect Script Error")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The script was entered incorrectly.");
    }

    private Robot createRobot() {
        Robot robot = new Robot();
        robot.setXCoordinate(2);
        robot.setYCoordinate(2);
        robot.setDirection(Direction.LEFT);
        robot.setTurnAround(true);
        return robot;
    }

    private ScriptDTO createScript(String script) {
        ScriptDTO scriptDTO = new ScriptDTO();
        scriptDTO.setScriptText(script);
        return scriptDTO;
    }

}
