package com.barisertakus.robotenvironment.service;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.dto.ScriptDTO;
import com.barisertakus.robotenvironment.entity.Robot;

public interface RobotService {
    RobotDTO getTop1();
    RobotDTO executeScript(ScriptDTO scriptDTO);
    Boolean saveFirstRobot();
}
