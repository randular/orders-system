package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderDto {
    private String code;
    private String desc;
    private int qty;
    private double amt;
}
