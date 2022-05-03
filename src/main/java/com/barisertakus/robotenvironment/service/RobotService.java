package com.barisertakus.robotenvironment.service;

import com.barisertakus.robotenvironment.dto.RobotDTO;

public interface RobotService {
    RobotDTO getTop1();
    Boolean saveFirstRobot();
}
