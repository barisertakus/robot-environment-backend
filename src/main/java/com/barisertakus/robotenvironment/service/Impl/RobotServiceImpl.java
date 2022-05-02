package com.barisertakus.robotenvironment.service.Impl;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.entity.Robot;
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
        return modelMapper.map(robot, RobotDTO.class);
    }
}
