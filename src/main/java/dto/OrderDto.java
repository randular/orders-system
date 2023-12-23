package dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderDto {
    private String orderID;
    private String date;
    private String customerID;
    private List<OrderDetailsDto> list;
}
