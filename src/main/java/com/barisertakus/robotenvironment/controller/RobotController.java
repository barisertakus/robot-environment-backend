package com.barisertakus.robotenvironment.controller;

import com.barisertakus.robotenvironment.dto.RobotDTO;
import com.barisertakus.robotenvironment.dto.ScriptDTO;
import com.barisertakus.robotenvironment.service.RobotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/robot")
@CrossOrigin
public class RobotController {
    private final RobotService robotService;

    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @GetMapping
    public ResponseEntity<RobotDTO> getRobot() {
        return ResponseEntity.ok(robotService.getTop1());
    }

    @PostMapping
    public ResponseEntity<RobotDTO> executeScript(@RequestBody ScriptDTO scriptDTO){
        return ResponseEntity.ok(robotService.executeScript(scriptDTO));
    }

}
