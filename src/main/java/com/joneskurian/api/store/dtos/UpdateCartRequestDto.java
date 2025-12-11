package com.joneskurian.api.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartRequestDto {
    @Min(value=1,message = "quantity must be between 1 and 100")
    @Max(value=100,message = "quantity must be between 1 and 100")
    private Integer quantity;

}
