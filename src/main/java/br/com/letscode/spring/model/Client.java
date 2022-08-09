package br.com.letscode.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(
            min = 5,
            max = 100,
            message = "O nome deve ter no minimo {min} e no maximo {max} caracteres"
    )
    private String name;

    @NotNull
    @Min(18)
    private int age;

    @NotNull
    @Pattern(regexp = "^[A-Z]{2}\\d{9}$")
    private String vat;

    @NotNull
    @Email
    private String email;


}
