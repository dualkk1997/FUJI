package tw.timhsu.message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface messageRepository extends JpaRepository<messageModel, Integer> {

}
