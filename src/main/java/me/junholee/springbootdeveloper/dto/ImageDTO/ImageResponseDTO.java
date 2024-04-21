package me.junholee.springbootdeveloper.dto.ImageDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageResponseDTO {

    private String url;

    @Builder
    public ImageResponseDTO(String url) {
        this.url = url;
    }
}
