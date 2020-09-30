package job.test.regsystem.Repositories;

import job.test.regsystem.Entity.Bid;
import job.test.regsystem.Entity.BidState;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BidRepositoryCriteria {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Bid> findAllByLogin(String login) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bid> query = builder.createQuery(Bid.class);

        Root<Bid> root = query.from(Bid.class);

        query.where(builder.equal(root.get("author").get("login"), login));

        return entityManager.createQuery(query).getResultList();
    }

    public List<Bid> findAllByState(BidState state) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bid> query = builder.createQuery(Bid.class);

        Root<Bid> root = query.from(Bid.class);

        query.where(builder.equal(root.get("state"), state));
        query.orderBy(builder.asc(root.get("createdTime")));

        return entityManager.createQuery(query).getResultList();
    }
}
