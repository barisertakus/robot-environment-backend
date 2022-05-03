package com.barisertakus.robotenvironment.service;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.entity.Robot;

public interface RobotService {
    RobotDTO getTop1();
    RobotDTO updateRobot(RobotDTO robotDTO);
    Boolean saveRobot(Robot robot);
    Boolean saveFirstRobot();
}
