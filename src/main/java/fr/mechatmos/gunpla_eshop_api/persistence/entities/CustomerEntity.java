package fr.mechatmos.gunpla_eshop_api.persistence.entities;

import fr.mechatmos.gunpla_eshop_api.exposition.dtos.CustomerRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerEntity {

    //contenu de mes tables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String first_name;

    @Column(nullable = false, length = 50)
    private String last_name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    //mes relations
    @OneToOne(
            cascade = {CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true
    )
    @JoinColumn(name="address_id",unique = true)
    private AddressEntity address;


    @OneToMany(
            mappedBy = "customer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<OrdersEntity> orders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public CustomerEntity(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    //methods
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void updateFrom(CustomerRequestDTO dto){
        this.first_name = dto.first_name();
        this.last_name = dto.last_name();

    }
}
