package faang.school.achievement.handler;

import faang.school.achievement.cache.AchievementCache;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventHandler<T> implements EventHandler<T> {
    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Async("taskExecutor")
    @Retryable(retryFor = ObjectOptimisticLockingFailureException.class, maxAttempts = 5, backoff = @Backoff(delay = 5000))
    protected void handleAchievement(Long userId, String achievementTitle) {
        Achievement achievement = achievementCache.getByTitle(achievementTitle)
                .orElseThrow(() -> new EntityNotFoundException("Achievement with title %s did not found".formatted(achievementTitle)));
        Long achievementId = achievement.getId();
        if (achievementService.hasAchievement(userId, achievementId)) {
            log.info("User %d already has achievement %d".formatted(userId, achievement.getId()));
            return;
        }
        achievementService.createProgressIfNeccessary(userId, achievementId);
        AchievementProgress progress = achievementService.getProgress(userId, achievementId);
        progress.increment();
        if (progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievementId);
            log.info("User %d had got an achievement %d".formatted(userId, achievementId));
        }
        achievementService.updateProgress(progress);
    }

    protected abstract String getAchievementTitle();
}
