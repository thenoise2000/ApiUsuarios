package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class ApiRequestDto {

	@NotEmpty(message = "El email es requerido.")
    @Pattern(regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$",
            message = "El email no es valido.")
    private String email;

    @NotBlank(message = "La contraseña es requerida.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}$",
            message = "La contraseña no es valida. " +
                    "La contraseña debe tener al entre 8 y 16 caracteres, uno o mas dígitos, una o mas minúsculas," +
                    " una o mas mayúsculas y uno o mas caracteres no alfanuméricos.")
    private String password;
}
