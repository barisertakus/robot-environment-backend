package com.barisertakus.robotenvironment.service.Impl;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.entity.Robot;
import com.barisertakus.robotenvironment.enums.Direction;
import com.barisertakus.robotenvironment.repository.RobotRepository;
import com.barisertakus.robotenvironment.service.RobotService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RobotServiceImpl implements RobotService {

    private final RobotRepository robotRepository;
    private final ModelMapper modelMapper;

    public RobotServiceImpl(RobotRepository robotRepository, ModelMapper modelMapper) {
        this.robotRepository = robotRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RobotDTO getTop1() {
        Robot robot = robotRepository.findTop1By();
    @Override
    public RobotDTO updateRobot(RobotDTO robotDTO) {
        Robot robot = robotRepository.findTop1By();
        Robot updatedRobot = updateRobotFields(robot, robotDTO);
        Robot savedRobot = robotRepository.save(updatedRobot);
        return convertToRobotDTO(savedRobot);
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

        return modelMapper.map(robot, RobotDTO.class);
    }
}
