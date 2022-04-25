package Models;

public class ResCasa {
    private String id;


    public ResCasa(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "ResCasa{" +
                "id='" + id + '\'' +
                '}';
    }
}
