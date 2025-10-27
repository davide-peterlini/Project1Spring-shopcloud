package it.smartcommunitylabdhub.shoprating.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id","user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(name = "product_id", nullable = false)
private Long productId;


@Column(name = "user_id", nullable = false)
private Long userId;


@Column(nullable = false)
private int voto; // 1-5


private String commento;