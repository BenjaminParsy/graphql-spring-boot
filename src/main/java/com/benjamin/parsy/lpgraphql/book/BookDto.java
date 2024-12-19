package com.benjamin.parsy.lpgraphql.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    private String category;

    @NotNull
    @Min(0)
    private Long authorId;

}
