package model;

import dto.ItemDto;

import java.util.List;

public interface ItemModel {
    boolean saveItem(ItemDto dto);
    boolean updateItem(ItemDto dto);
    boolean deleteItem(String code);
    List<ItemDto> allItems();

}
