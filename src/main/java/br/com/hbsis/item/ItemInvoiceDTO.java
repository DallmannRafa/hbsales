package br.com.hbsis.item;

import java.util.ArrayList;
import java.util.List;

public class ItemInvoiceDTO {

    private int amount;
    private String itemName;

    public ItemInvoiceDTO(int amount, String itemName) {
        this.amount = amount;
        this.itemName = itemName;
    }

    public static List<ItemInvoiceDTO> parser(List<Item> itens) {

        List<ItemInvoiceDTO> itemInvoiceDTOS = new ArrayList<>();

        for (Item item : itens) {
            itemInvoiceDTOS.add(
                    new ItemInvoiceDTO(item.getQuantidade(), item.getProduto().getNomeProduto())
            );
        }

        return itemInvoiceDTOS;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "ItemInvoiceDTO{" +
                "amount=" + amount +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
