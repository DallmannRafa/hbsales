package br.com.hbsis.arquivoCSV;

import br.com.hbsis.categoriaProdutos.Categoria;
import br.com.hbsis.categoriaProdutos.CategoriaDTO;
import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoria;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaDTO;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaService;
import br.com.hbsis.produto.IProdutoRepository;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoDTO;
import br.com.hbsis.produto.ProdutoService;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Optional;

@Service
public class CSVProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVProdutoService.class);

    private final ProdutoService produtoService;
    private final IProdutoRepository iProdutoRepository;
    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;
    private final LinhaDeCategoriaService linhaDeCategoriaService;
    private final CSVUtils csvUtils;

    public CSVProdutoService(ProdutoService produtoService, IProdutoRepository iProdutoRepository, FornecedorService fornecedorService, CategoriaService categoriaService, LinhaDeCategoriaService linhaDeCategoriaService, CSVUtils csvUtils) {
        this.produtoService = produtoService;
        this.iProdutoRepository = iProdutoRepository;
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
        this.linhaDeCategoriaService = linhaDeCategoriaService;
        this.csvUtils = csvUtils;
    }

    public void oneToCSV(HttpServletResponse response, Long id) throws IOException {

        String[][] dados = produtoService.stringfyToCsvById(id);

        ICSVWriter writer = csvUtils.writerBuilder("produto.csv", response);

        for (String[] linhaDeDado : dados) {
            writer.writeNext(linhaDeDado);

        }
    }

    public void manyToCSV(HttpServletResponse response) throws IOException {
        String[][] dados = this.produtoService.stringfyAllToCsv();

        ICSVWriter writer = csvUtils.writerBuilder("produtos.csv", response);

        for (String[] linhaDeDado : dados) {
            writer.writeNext(linhaDeDado);

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
                    if (produtoOptional.isPresent()) {
                        ProdutoDTO produtoDTO = this.produtoService.findById(produtoOptional.get().getId());
                        produtoDTO = this.updateProdutoDTO(valores, produtoDTO);

                        produtoService.update(produtoDTO, produtoOptional.get().getId());
                    }

                } else {
                    ProdutoDTO produtoDTO = new ProdutoDTO();
                    produtoDTO = this.updateProdutoDTO(valores, produtoDTO);

                    produtoService.save(produtoDTO);
                }

            }

        } catch (IOException | ParseException e) {
            LOGGER.info("Erro ao ler o arquivo .CSV", e);
        }

    }

    public ProdutoDTO updateProdutoDTO(String[] valores, ProdutoDTO produtoDTO) throws ParseException {

        produtoDTO.setCodigoProduto(produtoService.codeGenerator(valores[0]));
        produtoDTO.setNomeProduto(valores[1]);
        produtoDTO.setPrecoProduto(produtoService.valorGenerator(valores[2]));
        produtoDTO.setUnidadePorCaixa(Integer.parseInt(valores[3]));
        produtoDTO.setPesoUnidade(produtoService.pesoGenerator(valores[4]));
        produtoDTO.setUnidadeMedidaPeso(produtoService.unidadeMedidaGenerator(valores[4]));
        produtoDTO.setValidade(produtoService.dateGenerator(valores[5]));
        String codigoLinhaCategoria = valores[6];
        Optional<LinhaDeCategoria> linhaDeCategoriaOptional = linhaDeCategoriaService.findByCodigoLinhaCategoriaOptional(codigoLinhaCategoria);
        if (linhaDeCategoriaOptional.isPresent()) {
            produtoDTO.setLinhaDeCategoria(linhaDeCategoriaOptional.get());

        }

        return produtoDTO;
    }

    public ProdutoDTO updateProdutoDTOPorFornecedor(String[] valores, ProdutoDTO produtoDTO) throws ParseException {

        produtoDTO.setCodigoProduto(produtoService.codeGenerator(valores[0]));
        produtoDTO.setNomeProduto(valores[1]);
        produtoDTO.setPrecoProduto(produtoService.valorGenerator(valores[2]));
        produtoDTO.setUnidadePorCaixa(Integer.parseInt(valores[3]));
        produtoDTO.setPesoUnidade(produtoService.pesoGenerator(valores[4]));
        produtoDTO.setUnidadeMedidaPeso(produtoService.unidadeMedidaGenerator(valores[5]));
        produtoDTO.setValidade(produtoService.dateGenerator(valores[6]));

        return produtoDTO;
    }

    public void readProdutoPorFornecedor(MultipartFile file, Long id) {
        String linhaArquivo;
        String quebraLinha = ";";

        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            Optional<Fornecedor> fornecedorOptional = fornecedorService.findByIdOptional(id);
            if (fornecedorOptional.isPresent()) {

                linhaArquivo = csvReader.readLine();
                while ((linhaArquivo = csvReader.readLine()) != null) {
                    String[] valores = linhaArquivo.split(quebraLinha);

                    ProdutoDTO produtoDTO = new ProdutoDTO();
                    produtoDTO = this.updateProdutoDTOPorFornecedor(valores, produtoDTO);

                    LinhaDeCategoria linhaDeCategoria;
                    String codLinhaCategoria = linhaDeCategoriaService.codeGenerator(valores[7]);
                    Optional<LinhaDeCategoria> linhaDeCategoriaOptional = linhaDeCategoriaService.findByCodigoLinhaCategoriaOptional(codLinhaCategoria);
                    if (linhaDeCategoriaOptional.isPresent()) {

                        linhaDeCategoria = linhaDeCategoriaOptional.get();

                    } else {

                        LinhaDeCategoriaDTO linhaDeCategoriaDTO = new LinhaDeCategoriaDTO();
                        linhaDeCategoriaDTO.setCodigoLinhaCategoria(codLinhaCategoria);
                        linhaDeCategoriaDTO.setNomeLinhaCategoria(valores[8]);

                        String codigoCategoria = categoriaService.codeGenerator(fornecedorOptional.get().getCnpj(), valores[9]);
                        Optional<Categoria> categoriaOptional = categoriaService.findByCodigoCategoriaOptional(codigoCategoria);
                        Categoria categoria ;

                        Long idFornecedorCategoriaInformada = 0L;

                        if (categoriaOptional.isPresent()) {

                            idFornecedorCategoriaInformada = categoriaOptional.get().getFornecedor().getId();

                        }

                        if (categoriaOptional.isPresent() && (idFornecedorCategoriaInformada.equals(id))) {

                            categoria = categoriaOptional.get();


                        } else {

                            CategoriaDTO categoriaDTO = new CategoriaDTO();
                            categoriaDTO.setNomeCategoria(valores[10]);
                            categoriaDTO.setCodCategoria(codigoCategoria);
                            categoriaDTO.setFornecedor(fornecedorOptional.get());

                            categoriaService.save(categoriaDTO);


                            categoria = categoriaService.findByCodigoCategoria(codigoCategoria);
                        }

                        linhaDeCategoriaDTO.setCategoriaDaLinhaCategoria(categoria);

                        linhaDeCategoriaService.save(linhaDeCategoriaDTO);
                        linhaDeCategoria = linhaDeCategoriaService.findByCodigoLinhaCategoria(codLinhaCategoria);


                    }

                    produtoDTO.setLinhaDeCategoria(linhaDeCategoria);

                    String codigoProduto = produtoService.codeGenerator(valores[0]);
                    Optional<Produto> produtoOptional = produtoService.findByCodigoProduto(codigoProduto);

                    if (produtoOptional.isPresent()) {

                        produtoService.update(produtoDTO, produtoOptional.get().getId());

                    } else {
                        produtoService.save(produtoDTO);
                    }

                }

            }

        } catch (IOException | ParseException e) {
            LOGGER.info("Erro ao ler o arquivo .CSV", e);
        }

    }


}
