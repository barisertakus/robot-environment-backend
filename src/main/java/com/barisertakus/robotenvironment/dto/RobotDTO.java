package com.barisertakus.robotenvironment.dto;

import com.barisertakus.robotenvironment.enums.Direction;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;


@Getter
@Setter
public class RobotDTO {
    private Long id;
    private Integer xCoordinate;
    private Integer yCoordinate;
    private Direction direction;
    private Boolean turnAround = false;
    private Date createdDate;
    protected Date updatedDate;
}
