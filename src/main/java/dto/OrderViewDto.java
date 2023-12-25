package dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderViewDto {
    private String orderID;
    private String Date;
    private String customerID;
    private String customerName;
    private List<OrderViewItemsDto> list;
}
