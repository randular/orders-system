package model;

import dto.OrderViewDto;
import dto.OrderViewItemsDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderViewModel {
    List<OrderViewDto> allOrders() throws SQLException, ClassNotFoundException;
    List<OrderViewItemsDto> allItems(String id) throws SQLException, ClassNotFoundException;
}
