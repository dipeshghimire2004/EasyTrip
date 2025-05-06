package org.easytrip.easytripbackend.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BusOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String contactInfo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
