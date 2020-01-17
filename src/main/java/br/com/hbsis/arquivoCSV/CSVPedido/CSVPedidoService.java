package br.com.hbsis.arquivoCSV.CSVPedido;

import br.com.hbsis.arquivoCSV.CSVUtils;
import br.com.hbsis.item.IItemRepository;
import br.com.hbsis.item.Item;
import br.com.hbsis.pedido.IPedidoRepository;
import br.com.hbsis.pedido.Pedido;
import br.com.hbsis.periodoVendas.PeriodoVendas;
import br.com.hbsis.periodoVendas.PeriodoVendasService;
import com.opencsv.ICSVWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVPedidoService {

    private final CSVUtils csvUtils;
    private final PeriodoVendasService periodoVendasService;
    private final IItemRepository iItemRepository;
    private final IPedidoRepository iPedidoRepository;

    public CSVPedidoService(CSVUtils csvUtils, PeriodoVendasService periodoVendasService, IItemRepository iItemRepository, IPedidoRepository iPedidoRepository) {
        this.csvUtils = csvUtils;
        this.periodoVendasService = periodoVendasService;
        this.iItemRepository = iItemRepository;
        this.iPedidoRepository = iPedidoRepository;
    }

    public void exportToCSVByPeriodo(HttpServletResponse response, Long idPeriodo) throws IOException {
        String[][] dados = this.stringfyToCSVByPeriodo(idPeriodo);

        ICSVWriter writer = csvUtils.writerBuilder("produtoPorPeriodo.csv", response);

        for (String[] linhaDeDado : dados) {
            writer.writeNext(linhaDeDado);

        }
    }

    private String[][] stringfyToCSVByPeriodo(Long idPeriodo) {
        String[] header = new String[] {"nome_produto", "quantidade", "fornecedor"};

        PeriodoVendas periodoVendas = periodoVendasService.findPeriodoVendasById(idPeriodo);
        List<Pedido> pedidos = this.findByPeriodoVendas(periodoVendas);
        List<Item> itens  = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            if (!pedido.getStatusPedido().equals("CANCELADO")) {
                itens.addAll(this.iItemRepository.findByPedido(pedido));
            }
        }

        List<CSVItemModel> itensForCSV = this.populateListForCSV(itens);

        String[][] dados = new String [itensForCSV.size() + 1][3];
        dados[0] = header;
        int contador = 1;

        String cnpj = periodoVendas.getFornecedor().getCnpj();

        for (CSVItemModel item :itensForCSV) {
            dados[contador][0] = item.getProduto().getLinhaDeCategoria().getNomeLinhaCategoria() + " " + item.getProduto().getNomeProduto();
            dados[contador][1] = String.valueOf(item.getQuantidade());
            dados[contador][2] = periodoVendas.getFornecedor().getRazaoSocial() + " - "
                    + cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "."
                    + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-"
                    + cnpj.substring(12, 14);
            contador++;
        }

        return dados;

    }

    private List<Pedido> findByPeriodoVendas(PeriodoVendas periodoVendas) {
        List<Pedido> pedidos = this.iPedidoRepository.findByPeriodoVendas(periodoVendas);

        if (!pedidos.isEmpty()) {
            return pedidos;
        }

        throw new IllegalArgumentException("Não existem pedidos por este período de vendas");
    }

    private List<CSVItemModel> populateListForCSV(List<Item> itens) {
        List<CSVItemModel> itensForCSV = new ArrayList<>();

        for (Item item : itens) {
            if (itensForCSV.isEmpty()) {
                itensForCSV.add(new CSVItemModel(item.getQuantidade(), item.getProduto()));

            } else {
                for (CSVItemModel item1 : itensForCSV) {
                    if (item.getProduto().getId().equals(item1.getProduto().getId())) {
                        item1.setQuantidade(item1.getQuantidade() + item.getQuantidade());
                        break;

                    } else if ((itensForCSV.indexOf(item1) + 1) == itensForCSV.size()){
                        itensForCSV.add(new CSVItemModel(item.getQuantidade(), item.getProduto()));
                        break;
                    }
                }
            }
        }

        return itensForCSV;
    }
}
