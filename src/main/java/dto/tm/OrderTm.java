package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class OrderTm extends RecursiveTreeObject<OrderTm> {
    private String code;
    private String desc;
    private int qty;
    private double amt;
    private JFXButton deleteBtn;

    public OrderTm(String code, String desc, int qty, double amt){
        this.code=code;
        this.desc=desc;
        this.qty=qty;
        this.amt=amt;
    }
}
