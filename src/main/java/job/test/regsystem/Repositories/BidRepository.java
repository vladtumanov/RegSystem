package job.test.regsystem.Repositories;

import job.test.regsystem.Entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    List<Bid> findAllByUser_Login(String login);

    List<Bid> findAllByStateOrderByCreatedTime(Bid.State state);

}
