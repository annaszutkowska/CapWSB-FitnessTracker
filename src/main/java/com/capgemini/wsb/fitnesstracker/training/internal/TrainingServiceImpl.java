package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDTO;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {

    @Autowired
    private TrainingRepository trainingRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public Optional<User> getTraining(final Long trainingId) {
        throw new UnsupportedOperationException("Not finished yet");
    }

    @Override
    public void deleteByUserId(Long userId) {
        Optional<Training> optionalTraining = trainingRepo.findAll().stream()
                .filter(x -> Objects.equals(x.getUser().getId(), userId))
                .findFirst();
        optionalTraining.ifPresent(trainingRepo::delete);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepo.findAll();
    }

    @Override
    public List<Training> getAllTrainingsForDedicatedUser(Long userId) {
        return trainingRepo.findAllByUserId(userId);
    }

    @Override
    public List<Training> getAllFinishedTrainingsAfter(LocalDateTime afterDateTime) {
        return trainingRepo.findAllByEndTimeAfter(afterDateTime);
    }

    @Override
    public List<Training> getAllTrainingByActivityType(ActivityType activityType) {
        return trainingRepo.findAllByActivityType(activityType);
    }

    @Override
    public Training createTraining(TrainingDTO trainingDTO) {
        try {
            Optional<User> user = userRepo.findById(trainingDTO.userId());
            Training training = new Training(
                    user.get(),
                    trainingDTO.startTime(),
                    trainingDTO.endTime(),
                    trainingDTO.activityType(),
                    trainingDTO.distance(),
                    trainingDTO.averageSpeed()
            );

            return trainingRepo.save(training);
        }
        catch (UserNotFoundException e) {
            throw new UserNotFoundException(trainingDTO.userId());
        }
    }

    @Override
    public Training updateTraining(TrainingDTO training, Long trainingId) {
        Optional<Training> trainingById = trainingRepo.findById(trainingId);
        if (trainingById.isPresent()) {
            Training training1 = trainingById.get();
            training1.setActivityType(training.activityType());
            training1.setDistance(training.distance());
            training1.setAverageSpeed(training.averageSpeed());
            training1.setStartTime(training.startTime());
            training1.setEndTime(training.endTime());
            return trainingRepo.save(training1);
        }
        else {
            throw new TrainingNotFoundException(trainingId);
        }
    }

}
