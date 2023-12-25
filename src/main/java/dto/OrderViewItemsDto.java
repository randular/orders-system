package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderViewItemsDto {
    private String orderItemCode;
    private String orderItemName;
    private int orderItemQty;
    private double orderItemUnitPrice;
    private double orderItemAmount;
}
