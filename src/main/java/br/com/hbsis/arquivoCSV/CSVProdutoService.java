package br.com.hbsis.arquivoCSV;

import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoria;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaService;
import br.com.hbsis.produto.IProdutoRepository;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoDTO;
import br.com.hbsis.produto.ProdutoService;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CSVProdutoService {

    private final ProdutoService produtoService;
    private final IProdutoRepository iProdutoRepository;
    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;
    private final LinhaDeCategoriaService linhaDeCategoriaService;

    public CSVProdutoService(ProdutoService produtoService, IProdutoRepository iProdutoRepository, FornecedorService fornecedorService, CategoriaService categoriaService, LinhaDeCategoriaService linhaDeCategoriaService) {
        this.produtoService = produtoService;
        this.iProdutoRepository = iProdutoRepository;
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
        this.linhaDeCategoriaService = linhaDeCategoriaService;
    }

    public void oneToCSV(HttpServletResponse response, Long id) throws IOException {

        String[][] dados = produtoService.stringfyToCsvById(id);

        String fileName = "produto.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        ICSVWriter writer = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
                .withEscapeChar(ICSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                .build();

        for (String[] a : dados) {
            writer.writeNext(a);

        }
    }

    public void manyToCSV(HttpServletResponse response) throws IOException {
        String[][] dados = this.produtoService.stringfyAllToCsv();

        String fileName = "produtos.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        ICSVWriter writer = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
                .withEscapeChar(ICSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                .build();

        for (String[] a : dados) {
            writer.writeNext(a);

        }

    }

    public void readCSV(MultipartFile file) {
        String linhaArquivo;
        String quebraLinha = ";";

        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            linhaArquivo = csvReader.readLine();
            while ((linhaArquivo = csvReader.readLine()) != null) {

                String[] valores = linhaArquivo.split(quebraLinha);

                if (this.iProdutoRepository.existsByCodigoProduto(valores[0])) {
                    Optional<Produto> produtoOptional = this.iProdutoRepository.findByCodigoProduto(valores[0]);

                    ProdutoDTO produtoDTO = this.produtoService.findById(produtoOptional.get().getId());

                    produtoDTO = this.updateProdutoDTO(valores, produtoDTO);

                    produtoService.update(produtoDTO, produtoOptional.get().getId());
                } else {
                    ProdutoDTO produtoDTO = new ProdutoDTO();
                    produtoDTO = this.updateProdutoDTO(valores, produtoDTO);

                    produtoService.save(produtoDTO);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public ProdutoDTO updateProdutoDTO(String[] valores, ProdutoDTO produtoDTO) throws ParseException {

        String valor = valores[2].replaceAll("[^.|,|0-9]", "");
        valor = valor.replaceAll("[,]", ".");
        BigDecimal preco = new BigDecimal(valor);
        preco = preco.setScale(2, BigDecimal.ROUND_HALF_UP);
        produtoDTO.setPrecoProduto(preco);

        String pesoString = valores[4].replaceAll("[^.|,|0-9]", "");
        pesoString = pesoString.replaceAll("[,]", ".");
        BigDecimal peso = new BigDecimal(pesoString);
        peso = peso.setScale(3, BigDecimal.ROUND_HALF_UP);
        produtoDTO.setPesoUnidade(peso);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date data = format.parse(valores[5]);
        produtoDTO.setValidade(data);

        String codigoLinhaCategoria = valores[6];
        Optional<LinhaDeCategoria> linhaDeCategoriaOptional = linhaDeCategoriaService.findByCodigoLinhaCategoria(codigoLinhaCategoria);
        if (linhaDeCategoriaOptional.isPresent()) {
            LinhaDeCategoria linhaDeCategoria = linhaDeCategoriaOptional.get();
            produtoDTO.setLinhaDeCategoria(linhaDeCategoria);
        }

        produtoDTO.setCodigoProduto(produtoService.codeGenerator(valores[0]));
        produtoDTO.setNomeProduto(valores[1]);
        produtoDTO.setUnidadePorCaixa(Integer.parseInt(valores[3]));

        String unidadeMedida = valores[4];
        String replacedUnidadeMedida = unidadeMedida.replaceAll("[^A-z]", "");
        if (replacedUnidadeMedida.equalsIgnoreCase("g") || replacedUnidadeMedida.equalsIgnoreCase("mg") || replacedUnidadeMedida.equalsIgnoreCase("kg")) {
            if (replacedUnidadeMedida.equalsIgnoreCase("kg")){
                unidadeMedida = "Kg";
            } else {
                unidadeMedida = replacedUnidadeMedida;
            }

            produtoDTO.setUnidadeMedidaPeso(unidadeMedida);
        } else {
            throw new IllegalArgumentException("unidade de medida inválida");
        }

        return produtoDTO;
    }
}
