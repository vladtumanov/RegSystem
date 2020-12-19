package job.test.regsystem.Entity;

import javax.persistence.*;

@Entity
@Table(name = "t_roles")
public class Role {

    public static String ADMIN = "ROLE_ADMIN";
    public static String OPERATOR = "ROLE_OPERATOR";
    public static String CLIENT = "ROLE_CLIENT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
