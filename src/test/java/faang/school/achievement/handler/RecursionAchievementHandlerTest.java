package faang.school.achievement.handler;

import faang.school.achievement.dto.AchievementEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RecursionAchievementHandlerTest {

    @InjectMocks
    private RecursionAchievementHandler recursionAchievementHandler;

    @Test
    void shouldReturnCorrectAchievementName() {
        String achievementName = recursionAchievementHandler.getAchievementName();

        assertEquals("RECURSION-RECURSION", achievementName);
    }

    @Test
    void shouldExtractUserIdFromEvent() {
        long correctResult = 123L;
        AchievementEvent event = new AchievementEvent(LocalDateTime.now(), correctResult, 1L);

        long result = recursionAchievementHandler.getUserIdFromEvent(event);

        assertEquals(correctResult, result);
    }

    @Test
    void shouldReturnCorrectEventClass() {
        assertEquals(AchievementEvent.class, recursionAchievementHandler.getEventClass());
    }

    @Test
    void shouldReturnCorrectHandlerClass() {
        assertEquals(RecursionAchievementHandler.class, recursionAchievementHandler.getHandlerClass());
    }
}
