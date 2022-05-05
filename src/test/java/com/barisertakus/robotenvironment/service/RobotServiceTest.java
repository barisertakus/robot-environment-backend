package com.barisertakus.robotenvironment.service;
import com.barisertakus.robotenvironment.dto.ScriptDTO;
import com.barisertakus.robotenvironment.entity.Robot;
import com.barisertakus.robotenvironment.enums.Direction;
import com.barisertakus.robotenvironment.repository.RobotRepository;
import com.barisertakus.robotenvironment.service.Impl.RobotServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotServiceTest {

    RobotRepository robotRepositoryMock;
    ModelMapper modelMapper;
    RobotServiceImpl robotService;

    @Before
    public void init(){
        robotRepositoryMock = mock(RobotRepository.class);
        modelMapper = new ModelMapper();
        robotService = new RobotServiceImpl(robotRepositoryMock, modelMapper);
    }

    @Test
    public void givenScript_whenLengthGreaterThanLimit_thenThrowScriptError(){

        ScriptDTO script = createScript("SCRIPT LENGTH 3");
        Throwable throwable = catchThrowable(()->robotService.executeScript(script));
        assertThat(throwable)
                .as("Incorrect Script Error")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The script was entered incorrectly.");
    }

    @Test
    public void givenScript_whenEmpty_thenThrowScriptError(){

        ScriptDTO script = createScript("");
        Throwable throwable = catchThrowable(()->robotService.executeScript(script));
        assertThat(throwable)
                .as("Incorrect Script Error")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The script was entered incorrectly.");
    }

    @Test
    public void givenScript_whenNull_thenThrowScriptError(){

        Throwable throwable = catchThrowable(()->robotService.executeScript(null));
        assertThat(throwable)
                .as("Incorrect Script Error")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The script was entered incorrectly.");
    }

    private Robot createRobot(){
        Robot robot = new Robot();
        robot.setXCoordinate(2);
        robot.setYCoordinate(2);
        robot.setDirection(Direction.LEFT);
        robot.setTurnAround(true);
        return robot;
    }

    private ScriptDTO createScript(String script){
        ScriptDTO scriptDTO = new ScriptDTO();
        scriptDTO.setScriptText(script);
        return scriptDTO;
    }

}
