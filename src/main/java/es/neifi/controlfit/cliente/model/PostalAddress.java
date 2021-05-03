package es.neifi.controlfit.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PostalAddress implements Serializable {
    private String calle;
    private String codigo_postal;
    private String ciudad;
    private String provincia;

    public PostalAddress() {
    }
}