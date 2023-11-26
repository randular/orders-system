package model.impl;

import dto.ItemDto;
import model.ItemModel;

import java.util.List;

public class ItemModelImpl implements ItemModel {
    @Override
    public boolean saveItem(ItemDto dto) {
        return false;
    }

    @Override
    public boolean updateItem(ItemDto dto) {
        return false;
    }

    @Override
    public boolean deleteItem(String code) {
        return false;
    }

    @Override
    public List<ItemDto> allItems() {
        return null;
    }
}
