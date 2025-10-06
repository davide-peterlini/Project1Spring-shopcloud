package com.shopcloud.rating.model;

import jakarta.persistence.*;

@Entity
@Table(name="ratings")
public class Rating {

    @EmbeddedId
    private RatingKey id;

    private int voto;
    private String commento;

    public Rating() {}

    // Getters e setters
    public RatingKey getId() { return id; }
    public void setId(RatingKey id) { this.id = id; }

    public int getVoto() { return voto; }
    public void setVoto(int voto) { this.voto = voto; }

    public String getCommento() { return commento; }
    public void setCommento(String commento) { this.commento = commento; }
}
