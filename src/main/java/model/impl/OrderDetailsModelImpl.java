package model.impl;

import db.DBConnection;
import dto.OrderDetailsDto;
import model.ItemModel;
import model.OrderDetailsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModelImpl implements OrderDetailsModel {
    ItemModel itemModel = new ItemModelImpl();
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException {
        Connection connection = null;
        boolean isDetailsSaved = true;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            for (OrderDetailsDto dto:list){
                String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
                PreparedStatement pstm = connection.prepareStatement(sql);
                pstm.setString(1,dto.getOrderId());
                pstm.setString(2,dto.getItemCode());
                pstm.setInt(3,dto.getQty());
                pstm.setDouble(4,dto.getUnitPrice());

                if (pstm.executeUpdate()>0){
                    int qty = 0;
                    qty = itemModel.getItem(dto.getItemCode()).getQty() - dto.getQty();
                    boolean isQtyUpdated = itemModel.updateItemQty(dto.getItemCode(), qty);
                    if (isQtyUpdated){
                        connection.commit();
                    }else {
                        isDetailsSaved = false;
                    }
                }else{
                    isDetailsSaved = false;
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            connection.rollback();
        }
        return isDetailsSaved;
    }
}
