package br.com.hbsis.arquivoCSV.CSVProdutoPorFuncionario;

import br.com.hbsis.arquivoCSV.CSVUtils;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.item.Item;
import br.com.hbsis.pedido.IPedidoRepository;
import br.com.hbsis.pedido.Pedido;
import com.opencsv.ICSVWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class CSVProdutoPorFuncionarioService {

    private final FornecedorService fornecedorService;
    private final IPedidoRepository iPedidoRepository;
    private final CSVUtils csvUtils;


    public CSVProdutoPorFuncionarioService(FornecedorService fornecedorService, IPedidoRepository iPedidoRepository, CSVUtils csvUtils) {
        this.fornecedorService = fornecedorService;
        this.iPedidoRepository = iPedidoRepository;
        this.csvUtils = csvUtils;
    }

    public void exportToCSV(HttpServletResponse response, Long idFornecedor) throws IOException {
        String[][] dados = this.stringfyToCSVByFuncionario(idFornecedor);

        ICSVWriter writer = csvUtils.writerBuilder("produtoPorFuncionarioPorFornecedor.csv", response);

        for (String[] linhaDeDado : dados) {
            writer.writeNext(linhaDeDado);

        }
    }

    private String[][] stringfyToCSVByFuncionario(Long idFornecedor) {
        String[] header = new String[] {"nome_funcionario", "nome_produto", "quantidade", "fornecedor"};

        Fornecedor fornecedor = this.fornecedorService.findFornecedorById(idFornecedor);
        List<Pedido> pedidos = this.findByFornecedor(fornecedor);

        List<Funcionario> funcionarios = this.populateFuncionarios(pedidos);

        List<CSVProdutoPorFuncionarioModel> itensForCSV = new ArrayList<>();

        for (Funcionario funcionario : funcionarios) {
            for (Pedido pedido : pedidos) {
                if ((pedido.getFuncionario().getId() == funcionario.getId()) && (!pedido.getStatusPedido().equals("CANCELADO"))) {
                    this.populateListForCSV(itensForCSV, pedido.getItens(), funcionario);
                }
            }
        }

        String[][] dados = new String[itensForCSV.size() + 1][4];
        dados[0] = header;

        String cnpj = fornecedor.getCnpj();

        int contador = 1;

        for (CSVProdutoPorFuncionarioModel item :itensForCSV) {
            dados[contador][0] = item.getNomeFuncionario();
            dados[contador][1] = item.getProduto().getLinhaDeCategoria().getNomeLinhaCategoria() + " " + item.getProduto().getNomeProduto();
            dados[contador][2] = String.valueOf(item.getQuantidade());
            dados[contador][3] = fornecedor.getRazaoSocial() + " - "
                    + cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "."
                    + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-"
                    + cnpj.substring(12, 14);
            contador++;
        }

        return dados;
    }

    private void populateListForCSV(List<CSVProdutoPorFuncionarioModel> itensForCSV, List<Item> itens, Funcionario funcionario) {

        for (Item item : itens) {
            if (itensForCSV.isEmpty()) {
                itensForCSV.add(new CSVProdutoPorFuncionarioModel(item.getQuantidade(), item.getProduto(), funcionario.getNome()));

            } else {
                for (CSVProdutoPorFuncionarioModel item1 : itensForCSV) {
                    if (item.getProduto().getId().equals(item1.getProduto().getId()) && (funcionario.getNome().equals(item1.getNomeFuncionario()))) {
                        item1.setQuantidade(item1.getQuantidade() + item.getQuantidade());
                        break;

                    } else if ((itensForCSV.indexOf(item1) + 1) == itensForCSV.size()){
                        itensForCSV.add(new CSVProdutoPorFuncionarioModel(item.getQuantidade(), item.getProduto(), funcionario.getNome()));
                        break;
                    }
                }
            }
        }
    }

    private List<Funcionario> populateFuncionarios(List<Pedido> pedidos) {
        List<Funcionario> funcionarios = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            boolean duplicado = false;
            for(Funcionario funcionario : funcionarios) {
                if (pedido.getFuncionario().getId() == funcionario.getId()) {
                    duplicado = true;
                    break;
                }
            }

            if (!duplicado) {
                funcionarios.add(pedido.getFuncionario());
            }

        }

        return funcionarios;
    }


    public List<Pedido> findByFornecedor(Fornecedor fornecedor) {
        List<Pedido> pedidos = this.iPedidoRepository.findByFornecedor(fornecedor);

        if (!pedidos.isEmpty()) {
            return pedidos;
        }

        throw new IllegalArgumentException("Não existem pedidos para esse fornecedor");
    }
}
