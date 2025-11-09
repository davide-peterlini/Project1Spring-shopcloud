package it.smartcommunitylabdhub.shoprating.models.dto;

public class RatingRequestDTO {
    private int voto;
    private String commento;

    // Getters e setters
    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }
}
