package com.barisertakus.robotenvironment.repository;

import com.barisertakus.robotenvironment.entity.Robot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RobotRepository extends JpaRepository<Robot,Long> {
    Robot findTop1By();
}
