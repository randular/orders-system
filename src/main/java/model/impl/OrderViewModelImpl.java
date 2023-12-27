package model.impl;

import db.DBConnection;
import dto.OrderViewDto;
import model.OrderViewModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderViewModelImpl implements OrderViewModel {
    @Override
    public List<OrderViewDto> allOrders() throws SQLException, ClassNotFoundException {
        List<OrderViewDto> list = new ArrayList<>();
        String sql = "SELECT orders.id, orders.date, orders.customerId, customer.name " +
                "FROM orders " +
                "INNER JOIN customer " +
                "ON orders.customerId = customer.id";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            list.add(new OrderViewDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    null
            ));
        }
        return list;
    }
}
