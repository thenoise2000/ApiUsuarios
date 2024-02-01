package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.entities.PhoneEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private UUID id;

    @NotEmpty(message = "Especifique el nombre")
    private String name;

    @NotEmpty(message = "Especifique el email")
    @Pattern(regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$",
            message = "Email no es correcto.")
    private String email;

    @NotBlank(message = "Se requiere contraseña.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{10,20}$",
            message = "La contraseña es incorrecta. " +
                    "Ingrese entre 10 y 20 caracteres, mas de un numero, mas de una minúscula," +
                    "mas de una mayúscula y mas de un caracter no alfanumérico.")
    private String password;

    private List<PhoneDto> phones = new ArrayList<>();

}
