package model;

import dto.OrderViewDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderViewModel {
    List<OrderViewDto> allOrders() throws SQLException, ClassNotFoundException;
}
