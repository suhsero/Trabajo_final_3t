package crm.model;

import java.time.LocalDateTime;

public abstract class ClienteBase extends Persona {
    private String estado;

    public ClienteBase(int idPersona, String nombre, String email, String telefono, LocalDateTime fechaRegistro, String estado) {
        super(idPersona, nombre, email, telefono, fechaRegistro);
        this.estado = estado;
    }

    public ClienteBase() {
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
