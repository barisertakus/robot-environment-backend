package com.barisertakus.robotenvironment.service.Impl;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.dto.ScriptDTO;
import com.barisertakus.robotenvironment.entity.Robot;
import com.barisertakus.robotenvironment.enums.Direction;
import com.barisertakus.robotenvironment.repository.RobotRepository;
import com.barisertakus.robotenvironment.service.RobotService;
import com.barisertakus.robotenvironment.utils.NumberUtils;
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

    private final Integer singleCommandLength = 1;

    private final Integer numericCommandLength = 2;

    private final String positionCommandNum = "1";

    public RobotServiceImpl(RobotRepository robotRepository, ModelMapper modelMapper) {
        this.robotRepository = robotRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RobotDTO getTop1() {
        Robot robot = robotRepository.findTop1By();
        return convertToRobotDTO(robot);
    }

    private Robot getLastRecord() {
        return robotRepository.findTop1By();
    }


    private IllegalArgumentException incorrectScriptError() {
        log.error("The script was entered incorrectly.");
        return new IllegalArgumentException("The script was entered incorrectly.");
    }

    @Override
    public RobotDTO executeScript(ScriptDTO scriptDTO) {
        String[] scriptArray = getScriptArrayFromScript(scriptDTO);
        String command = scriptArray[0];

        if (!isValidScriptText(scriptArray)) {
            throw incorrectScriptError();
        }

        switch (command) {
            case "POSITION":
                return setInitialPosition(scriptArray);
            case "WAIT":
                return waitRobot(scriptArray);
            case "TURNAROUND":
                return turnAround(scriptArray);
            case "FORWARD":
                return forward(scriptArray);
            case "RIGHT":
            case "LEFT":
            case "UP":
            case "DOWN":
                return changeDirection(scriptArray);
            default:
                throw incorrectScriptError();
        }

    }

    private String[] getScriptArrayFromScript(ScriptDTO scriptDTO){
        if(scriptDTO == null){
            throw incorrectScriptError();
        }
        return generateScriptArray(scriptDTO);
    }

    private String[] generateScriptArray(ScriptDTO scriptDTO){
        String scriptText = scriptDTO.getScriptText();
        String script = scriptText.toUpperCase();
        return script.split(" ");
    }

    private Boolean isValidScriptText(String[] scriptArray) {
        return scriptArray.length <= numericCommandLength;
    }

    private Boolean isValidPositionCommand(String[] scriptArray) {
        return isValidCommandLength(scriptArray) && scriptArray[1].equals(positionCommandNum);
    }

    private Boolean isValidNumericCommand(String[] scriptArray) {
        return isValidCommandLength(scriptArray) && NumberUtils.isNumeric(scriptArray[1]);
    }

    private Boolean isValidCommandLength(String[] scriptArray) {
        return scriptArray.length == numericCommandLength;
    }

    private Boolean isValidSingleCommand(String[] scriptArray) {
        return scriptArray.length == singleCommandLength;
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
        robotRepository.save(robot);
        return convertToRobotDTO(robot);
    }

    private Robot setInitialRobotFields(Robot robot) {
        robot.setXCoordinate(0);
        robot.setYCoordinate(0);
        robot.setDirection(Direction.RIGHT);
        robot.setTurnAround(false);
        return robot;
    }

    private RobotDTO waitRobot(String[] scriptArray) {
        if (!isValidSingleCommand(scriptArray)) {
            throw incorrectScriptError();
        }
        return closeTurnAround();
    }

    private RobotDTO closeTurnAround() {
        Robot robot = getLastRecord();
        robot.setTurnAround(false);
        robotRepository.save(robot);
        return convertToRobotDTO(robot);
    }

    private RobotDTO turnAround(String[] scriptArray) {
        if (!isValidSingleCommand(scriptArray)) {
            throw incorrectScriptError();
        }
        return turnedRobot();
    }

    private RobotDTO turnedRobot() {
        Robot robot = getLastRecord();
        Boolean previousTurn = robot.getTurnAround();
        robot.setTurnAround(!previousTurn);
        robotRepository.save(robot);
        return convertToRobotDTO(robot);
    }

    private RobotDTO changeDirection(String[] scriptArray) {
        if (!isValidSingleCommand(scriptArray)) {
            throw incorrectScriptError();
        }
        return setRobotDirection(scriptArray[0]);
    }

    private RobotDTO setRobotDirection(String directionText) {
        Robot robot = getLastRecord();
        Direction direction = Direction.findByValue(directionText);
        robot.setDirection(direction);
        robotRepository.save(robot);
        return convertToRobotDTO(robot);
    }

    private RobotDTO forward(String[] scriptArray) {
        if (!isValidNumericCommand(scriptArray)) {
            throw incorrectScriptError();
        }
        return forwardRobot(scriptArray[1]);
    }

    private RobotDTO forwardRobot(String stepText) {
        Robot robot = getLastRecord();
        Integer stepCount = Integer.parseInt(stepText);
        setCoordinates(robot, stepCount);
        return convertToRobotDTO(robot);
    }

    private Robot setCoordinates(Robot robot, Integer stepCount) {
        switch (robot.getDirection()) {
            case RIGHT:
                robot.setXCoordinate(checkBorders(robot.getXCoordinate() + stepCount, ARENA_WIDTH));
                break;
            case LEFT:
                robot.setXCoordinate(checkBorders(robot.getXCoordinate() - stepCount, ARENA_WIDTH));
                break;
            case UP:
                robot.setYCoordinate(checkBorders(robot.getYCoordinate() - stepCount, ARENA_HEIGHT));
                break;
            case DOWN:
                robot.setYCoordinate(checkBorders(robot.getYCoordinate() + stepCount, ARENA_HEIGHT));
                break;
        }
        return robotRepository.save(robot);
    }

    private int checkBorders(int previousPosition, int limit) {
        int position = previousPosition % limit;
        if (position >= 0) {
            return position;
        }

        int absPosition = Math.abs(position);
        return limit - absPosition;
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

}
