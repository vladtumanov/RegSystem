package job.test.regsystem.Controllers;

import job.test.regsystem.Entity.Bid;
import job.test.regsystem.Entity.Role;
import job.test.regsystem.Services.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bid")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    public ResponseEntity<Bid> create(@RequestBody Bid bid) {
        return ResponseEntity.ok(bidService.save(bid));
    }

    @GetMapping
    public ResponseEntity<List<Bid>> findAll(Authentication authentication, Principal principal) {
        if (authentication.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals(Role.OPERATOR))) {
            return ResponseEntity.ok(bidService.findAllSent());
        }
        return ResponseEntity.ok(bidService.findAllByLogin(principal.getName()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Bid> update(@RequestBody Bid bid, @PathVariable Long id, Authentication authentication) {
        Optional<Bid> bidTemp = bidService.findById(id);

        if (!bidTemp.isPresent())
            return ResponseEntity.notFound().build();

        Bid bidPatched = bidTemp.get();

        if (authentication.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals(Role.OPERATOR))) {
            if (bidPatched.getState() == Bid.State.SENT &&
                    (bid.getState() == Bid.State.ACCEPTED || bid.getState() == Bid.State.REJECTED)) {
                bidPatched.setState(bid.getState());
                return ResponseEntity.ok(bidService.save(bidPatched));
            }
            return ResponseEntity.badRequest().build();
        }

        if (bid.getMessage() != null)
            if (bidPatched.getState() == Bid.State.ACCEPTED)
                bidPatched.setMessage(bid.getMessage());
            else
                return ResponseEntity.badRequest().build();

        if (bid.getState() == Bid.State.SENT)
            bidPatched.setState(Bid.State.SENT);

        return ResponseEntity.ok(bidService.save(bidPatched));
    }

}
