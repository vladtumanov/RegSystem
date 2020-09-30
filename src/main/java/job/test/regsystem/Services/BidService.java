package job.test.regsystem.Services;

import job.test.regsystem.Entity.Bid;
import job.test.regsystem.Entity.BidState;
import job.test.regsystem.Repositories.BidRepository;
import job.test.regsystem.Repositories.BidRepositoryCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidService  {

    private final BidRepository bidRepository;
    private final BidRepositoryCriteria bidRepositoryCriteria;

    public BidService(BidRepository bidRepository, BidRepositoryCriteria bidRepositoryCriteria) {
        this.bidRepository = bidRepository;
        this.bidRepositoryCriteria = bidRepositoryCriteria;
    }

    public Bid save(Bid bid) {
        return bidRepository.save(bid);
    }

    public List<Bid> findAll() { return bidRepository.findAll(); }

    public List<Bid> findAllByLogin(String login) { return bidRepository.findAllByAuthor_Login(login); }

    public Optional<Bid> findById(Long id) {
        return bidRepository.findById(id);
    }

    public List<Bid> findAllSent() {
        List<Bid> temp = bidRepositoryCriteria.findAllByState(BidState.SENT);
        for (Bid b : temp) {
            b.setMessage(String.join("-", b.getMessage().split("")));
        }
        return temp;
    }
}
