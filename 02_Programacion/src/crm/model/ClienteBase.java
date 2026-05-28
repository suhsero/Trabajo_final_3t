package crm.model;

import java.time.LocalDateTime;

public abstract class ClienteBase extends Persona {

    // Atributo común extraído de ClienteFormal y ClientePotencial
    private String estado;

    public ClienteBase(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro, String estado) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.estado = estado;
    }

    public ClienteBase() {
        super();
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}