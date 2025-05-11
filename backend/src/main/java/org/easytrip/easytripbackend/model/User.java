package org.easytrip.easytripbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@NamedQuery(
        name = "User.findByEmail",
        query = "SELECT u FROM User u WHERE u.email = :email"
)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_role", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="role")
    private Set<Role> role =new HashSet<>();

    @OneToMany(mappedBy="owner", cascade= CascadeType.ALL, orphanRemoval=true)
    private Set<Guesthouse> guesthouses =new HashSet<>();

    //one-to-many relationship with bookings(for travellers/client)
    @OneToMany(mappedBy="traveler", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Booking> bookings;

    @Column(nullable=false)
    private boolean isActive=true;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BusOperator busOperator;

}
