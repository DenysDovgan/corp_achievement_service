package faang.school.achievement.repository;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    @EntityGraph(attributePaths = {"userAchievements"})
    Optional<Achievement> findByTitle(String title);

    @EntityGraph(attributePaths = {"userAchievements"})
    List<Achievement> findAll();

    List<Achievement> findByType(AchievementType type);
}
