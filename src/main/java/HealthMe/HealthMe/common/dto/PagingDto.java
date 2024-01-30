package HealthMe.HealthMe.common.dto;


import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PagingDto {
    private Integer idx;

    @Builder
    public PagingDto(Integer idx){
        this.idx = idx;
    }
}
