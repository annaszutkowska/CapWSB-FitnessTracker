package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByUserId(Long userId);

    List<Training> findAllByEndTimeAfter(LocalDateTime afterTime);

    List<Training> findAllByActivityType(ActivityType activityType);
}
