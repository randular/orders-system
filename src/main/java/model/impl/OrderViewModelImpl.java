package model.impl;

import db.DBConnection;
import dto.OrderViewDto;
import dto.OrderViewItemsDto;
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

    @Override
    public List<OrderViewItemsDto> allItems(String id) throws SQLException, ClassNotFoundException {
        List<OrderViewItemsDto> list = new ArrayList<>();
        String sql = "SELECT orderdetail.itemCode, item.description, orderdetail.qty, orderdetail.unitPrice, (orderdetail.qty * orderdetail.unitPrice) " +
                "FROM orderdetail " +
                "INNER JOIN item " +
                "ON orderdetail.itemCode = item.code " +
                "WHERE orderdetail.orderId =?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()){
            System.out.println("item sql");
            list.add(new OrderViewItemsDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)
            ));
        }
        return list;
    }
}
