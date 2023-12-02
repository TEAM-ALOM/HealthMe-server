package HealthMe.HealthMe.domain.food.domain;

import HealthMe.HealthMe.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "INGESTION_LIST")
public class IngestionList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGETSTION_LIST_ID")
    private Long id;

    @Column(nullable = false)
    private Date date;
    private Double mass;
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "FOOD_LIST_ID")
    @JsonBackReference
    private FoodList foodList;
}
