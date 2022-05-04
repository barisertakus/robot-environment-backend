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
    public void testPositionScript(){

        Robot robot = new Robot();
        robot.setXCoordinate(4);
        robot.setYCoordinate(4);
        robot.setDirection(Direction.LEFT);
        robot.setTurnAround(true);

        ScriptDTO scriptDTO = new ScriptDTO();
        scriptDTO.setScriptText("POSITION 1");

        when(robotRepositoryMock.findTop1By())
                .thenReturn(robot);

        assertThat(robotService.executeScript(scriptDTO).getXCoordinate())
                .as("Test position X = 0")
                .isEqualTo(0);

    }
}
