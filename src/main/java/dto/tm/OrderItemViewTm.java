package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderItemViewTm extends RecursiveTreeObject<OrderItemViewTm> {
    private String orderItemCode;
    private String orderItemName;
    private int orderItemQty;
    private double orderItemUnitPrice;
    private double orderItemAmount;
}
