# Robot Environment


### 30.04.2022
- SpringBoot was used to create a new application. 
- The analysis of documents and projects was completed.
- PostgreSQL was used as the database.

### 01.05.2022
- AOP logging was added for all services.
- Modelmapper library was added for DTO conversions.

### 02.05.2022
- An auditable BaseEntity has been added to keep track of when record was saved and when it was last updated.
- Robot entity was created. A robot dto has also been created to be sent to frontend.
- Find last record was added.
- First robot service and API were added.

### 03.05.2022
- Details about arena was added to application properties.
- The TurnAround function, which triggers the robot to turn around itself, was implemented.
- Create a new robot and save it if there is not one when the application is launched.
- Checking and running the script was added.

### 04.05.2022
- JUnit and Mockito libraries were used for unit testing.
- Different cases of the script, such as null or empty, were tested.
- Various test cases were prepared about many of the commands sent with the script.


## API Reference

### API Address

```http
  http://robot-demo.herokuapp.com/
```

#### Get the robot's last position.

```http
  GET /api/robot
```

| Parameter | Type   | Description |
|:----------|:-------|:------------|
| `none`    | `none` | `none`      |


#### Response example

```
{
  "createdDate": "2022-05-05T01:06:53.924Z",
  "direction": "DOWN",
  "id": 1,
  "turnAround": true,
  "updatedDate": "2022-05-05T01:06:53.924Z",
  "xcoordinate": 0,
  "ycoordinate": 0
}

```

#### Give commands to the robot via script.

```http
  POST /api/robot
```

##### Request Body


| Parameter    | Type     | Description                         |
|:-------------|:---------|:------------------------------------|
| `scriptText` | `string` | The command to be sent to the robot |


#### Response example

```
{
  "createdDate": "2022-05-05T01:06:53.924Z",
  "direction": "RIGHT",
  "id": 1,
  "turnAround": true,
  "updatedDate": "2022-05-05T01:06:53.924Z",
  "xcoordinate": 3,
  "ycoordinate": 2
}

```