package domain;

class Proveedor {
    private int id;
    private String nombre;
    public Proveedor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        }

// Métodos getter y setter para los atributos

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

