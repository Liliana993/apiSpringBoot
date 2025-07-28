package com.api.apiSpring.domain.medico;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class DatosListaMedicoModelAssembler implements RepresentationModelAssembler<DatosListaMedico, EntityModel<DatosListaMedico>> {
    public EntityModel<DatosListaMedico> toModel(@NonNull DatosListaMedico datosListaMedico) {
        return EntityModel.of(datosListaMedico);
    }
}
