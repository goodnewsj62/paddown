package com.paddown.paddown.security.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RefreshModel {
    @NotBlank
    private String refresh;
}
