package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingServiceImpl training;

    @GetMapping
    public List<Training> getAllTrainings() {
        return training.getAllTrainings();
    }

    @GetMapping("/{userId}")
    public List<Training> getAllTrainingsForDedicatedUser(@PathVariable Long userId) {
        return training.getAllTrainingsForDedicatedUser(userId);
    }

    @GetMapping("/finished/{afterTime}")
    public List<Training> getAllFinishedTrainingsAfterTime(@PathVariable String afterTime) {
        LocalDateTime afterDateTime = LocalDate.parse(afterTime).atStartOfDay();
        return training.getAllFinishedTrainingsAfter(afterDateTime);
    }

    @GetMapping("/activityType")
    public List<Training> getAllTrainingByActivityType(@RequestParam ActivityType activityType) {
        return training.getAllTrainingByActivityType(activityType);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Training createTraining(@RequestBody TrainingDTO trainingDTO) {
        return training.createTraining(trainingDTO);
    }

    @PutMapping("/{trainingId}")
    public Training updateUser(@PathVariable Long trainingId, @RequestBody TrainingDTO trainingDTO) {
        return training.updateTraining(trainingDTO, trainingId);
    }
}
