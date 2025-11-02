package main.java.it.smartcommunitylabdhub.shoprating.dto;

public class RatingRequest {
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
