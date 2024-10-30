package faang.school.achievement.handler;

import faang.school.achievement.dto.FollowerEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BloggerAchievementHandlerTest {

    @InjectMocks
    private BloggerAchievementHandler bloggerAchievementHandler;

    @Test
    void shouldReturnCorrectAchievementName() {
        String achievementName = bloggerAchievementHandler.getAchievementName();
        assertEquals("BLOGGER", achievementName);
    }

    @Test
    void shouldExtractUserIdFromEvent() {
        FollowerEvent event = new FollowerEvent(LocalDateTime.now(), 123L);
        long userId = bloggerAchievementHandler.getUserIdFromEvent(event);
        assertEquals(123L, userId);
    }

    @Test
    void shouldReturnCorrectEventClass() {
        assertEquals(FollowerEvent.class, bloggerAchievementHandler.getEventClass());
    }
}
