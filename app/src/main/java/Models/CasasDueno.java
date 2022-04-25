package Models;

public class CasasDueno {
    private String nombre_casa;
    public int id;

    public CasasDueno(String nombre_casa, int id){
        this.nombre_casa = nombre_casa;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNombre_casa() {
        return nombre_casa;
    }

    public void setNombre_casa(String nombre_casa) {
        this.nombre_casa = nombre_casa;
    }

    @Override
    public String toString() {
        return "CasasDueno{" +
                "nombre_casa='" + nombre_casa + '\'' +
                ", id=" + id +
                '}';
    }
}
