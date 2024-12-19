package com.benjamin.parsy.lpgraphql.review;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    @NotEmpty
    private String text;

    @NotEmpty
    private String createdBy;

    @NotNull
    @Min(0)
    private Long bookId;

}
