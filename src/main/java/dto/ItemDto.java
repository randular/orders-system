package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ItemDto {
    private String code;
    private String desc;
    private Double unitPrice;
    private int qty;
}
