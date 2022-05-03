package com.barisertakus.robotenvironment.service.Impl;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.dto.ScriptDTO;
import com.barisertakus.robotenvironment.entity.Robot;
import com.barisertakus.robotenvironment.enums.Direction;
import com.barisertakus.robotenvironment.repository.RobotRepository;
import com.barisertakus.robotenvironment.service.RobotService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RobotServiceImpl implements RobotService {

    private final RobotRepository robotRepository;
    private final ModelMapper modelMapper;

    @Value("${ARENA_HEIGHT}")
    private Integer ARENA_HEIGHT;

    @Value("${ARENA_WIDTH}")
    private Integer ARENA_WIDTH;

    public RobotServiceImpl(RobotRepository robotRepository, ModelMapper modelMapper) {
        this.robotRepository = robotRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RobotDTO getTop1() {
        Robot robot = robotRepository.findTop1By();
        return convertToRobotDTO(robot);
    }

    @Override
    public RobotDTO updateRobot(RobotDTO robotDTO) {
        Robot robot = robotRepository.findTop1By();
        Robot updatedRobot = updateRobotFields(robot, robotDTO);
        Robot savedRobot = robotRepository.save(updatedRobot);
        return convertToRobotDTO(savedRobot);
    }

    private Robot updateRobotFields(Robot robot, RobotDTO robotDTO) {
        robot.setDirection(robotDTO.getDirection());
        robot.setXCoordinate(robotDTO.getXCoordinate());
        robot.setYCoordinate(robotDTO.getYCoordinate());
        return robot;
    }

    private IllegalArgumentException incorrectScriptError() {
        log.error("The script was entered incorrectly.");
        return new IllegalArgumentException("The script was entered incorrectly.");
    }

    @Override
    public RobotDTO executeScript(ScriptDTO scriptDTO) {
        String scriptText = scriptDTO.getScriptText();
        String script = scriptText.toUpperCase();
        String[] scriptArray = script.split(" ");
        String command = scriptArray[0];

        if (!isValidScriptText(scriptArray)) {
            throw incorrectScriptError();
        }

        switch (command) {
            case "POSITION":
                return setInitialPosition(scriptArray);
            default:
                return null;
        }
    }


    private Boolean isValidScriptText(String[] scriptArray) {
        return scriptArray.length < 3;
    }

    private Boolean isValidPositionCommand(String[] scriptArray) {
        return isValidCommandLength(scriptArray) && scriptArray[1].equals("1");
    }

    private Boolean isValidNumericCommand(String[] scriptArray) {
        return isValidCommandLength(scriptArray) && isNumeric(scriptArray[1]);
    }

    private Boolean isValidCommandLength(String[] scriptArray) {
        return scriptArray.length == 2;
    }

    private Boolean isValidSingleCommand(String[] scriptArray) {
        return scriptArray.length == 1;
    }

    private RobotDTO setInitialPosition(String[] scriptArray) {
        if (!isValidPositionCommand(scriptArray)) { // POSITION 1
            throw incorrectScriptError();
        }
        return setInitialAndSaveRobot();
    }

    private RobotDTO setInitialAndSaveRobot() {
        Robot lastRecord = getLastRecord();
        Robot robot = setInitialRobotFields(lastRecord);
        Robot savedRobot = robotRepository.save(robot);
        return convertToRobotDTO(savedRobot);
    }

    private Robot setInitialRobotFields(Robot robot) {
        robot.setXCoordinate(0);
        robot.setYCoordinate(0);
        robot.setDirection(Direction.RIGHT);
        robot.setTurnAround(false);
        return robot;
    }

    @Override
    public Boolean saveRobot(Robot robot) {
        robotRepository.save(robot);
        return true;
    }

    @Override
    public Boolean saveFirstRobot() {
        if (!robotRepository.existsById(1L)) {
            Robot robot = new Robot();
            robot.setXCoordinate(0);
            robot.setYCoordinate(0);
            robot.setDirection(Direction.RIGHT);
            robotRepository.save(robot);
            return true;
        }
        return false;
    }

    private RobotDTO convertToRobotDTO(Robot robot) {
        return modelMapper.map(robot, RobotDTO.class);
    }

    public static boolean isNumeric(String string) {
        int intValue;

        if (string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
