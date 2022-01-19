package ru.gb.web.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;

    @NotBlank
    @Length(min = 1, max = 255)
    private String title;
}
