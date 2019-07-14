package java12.cryptowin.entity;

import java12.cryptowin.entity.enumeration.CryptCoinType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table (name="subscription")
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cryptCoin")
    private CryptCoinType cryptCoinType;

    @Column (name = "minResult")
    private double minResult;

    @Column (name = "maxResult")
    private double maxResult;

    public Subscription(User user, CryptCoinType cryptCoinType, double minResult, double maxResult) {
        this.user = user;
        this.cryptCoinType = cryptCoinType;
        this.minResult = minResult;
        this.maxResult = maxResult;
    }
}
