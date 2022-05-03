package com.barisertakus.robotenvironment.entity;

import com.barisertakus.robotenvironment.enums.Direction;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Robot extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer xCoordinate;
    private Integer yCoordinate;
    @Enumerated(EnumType.STRING)
    private Direction direction;
    private Boolean turnAround = false;
}
