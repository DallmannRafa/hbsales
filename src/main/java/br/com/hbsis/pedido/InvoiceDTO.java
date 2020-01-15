package br.com.hbsis.pedido;

import br.com.hbsis.item.ItemInvoiceDTO;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceDTO {

    private String cnpjFornecedor;
    private String employeeUuid;
    private List<ItemInvoiceDTO> invoiceItemDTOSet;
    private BigDecimal totalValue;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String cnpjFornecedor, String employeeUuid, List<ItemInvoiceDTO> invoiceItemDTOSet, BigDecimal totalValue) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
        this.invoiceItemDTOSet = invoiceItemDTOSet;
        this.totalValue = totalValue;
    }

    public static InvoiceDTO parse(Pedido pedido) {
        return new InvoiceDTO(
                pedido.getFornecedor().getCnpj(),
                pedido.getFuncionario().getUuid(),
                ItemInvoiceDTO.parser(pedido.getItens()),
                pedido.getTotal()
        );
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public List<ItemInvoiceDTO> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public void setInvoiceItemDTOSet(List<ItemInvoiceDTO> invoiceItemDTOSet) {
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "cnpjFornecedor='" + cnpjFornecedor + '\'' +
                ", employeeUuid='" + employeeUuid + '\'' +
                ", invoiceItemDTOSet=" + invoiceItemDTOSet +
                ", totalValue=" + totalValue +
                '}';
    }
}
