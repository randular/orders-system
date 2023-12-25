package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderViewTm extends RecursiveTreeObject<OrderViewTm> {
    private String orderID;
    private String orderDate;
    private String orderCustID;
    private String orderCustName;
}
