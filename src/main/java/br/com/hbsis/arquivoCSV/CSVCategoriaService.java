package br.com.hbsis.arquivoCSV;

import br.com.hbsis.categoriaProdutos.Categoria;
import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.categoriaProdutos.ICategoriaRepository;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class CSVCategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVCategoriaService.class);

    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;
    private final ICategoriaRepository iCategoriaRepository;
    private final CSVUtils csvUtils;

    public CSVCategoriaService(FornecedorService fornecedorService, CategoriaService categoriaService, ICategoriaRepository iCategoriaRepository, CSVUtils csvUtils) {
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
        this.iCategoriaRepository = iCategoriaRepository;
        this.csvUtils = csvUtils;
    }

    public void manyToCSV(HttpServletResponse response) throws IOException {
        String[][] dados = this.categoriaService.stringFyToCSVAll();

        ICSVWriter writer = csvUtils.writerBuilder("categoria.csv", response);

        for (String[] a : dados) {
            writer.writeNext(a);

        }

    }

    public void oneToCSV(HttpServletResponse response, Long id) throws IOException {

        String[][] dados = categoriaService.stringFyToCSVbyId(id);

        ICSVWriter writer = csvUtils.writerBuilder("categoria.csv", response);

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

                String CNPJ = valores[3].replaceAll("[^0-9]", "");

                if (!this.iCategoriaRepository.existsByCodigoCategoria(valores[0])) {

                    Optional<Fornecedor> fornecedorOptional = fornecedorService.findByCnpjOptional(CNPJ);

                    if (fornecedorOptional.isPresent()) {

                        String codigoCategoriaOfCSV = valores[0];

                        Categoria categoria = new Categoria();
                        categoria.setCodigoCategoria(categoriaService.codeGenerator(CNPJ, codigoCategoriaOfCSV));
                        categoria.setNomeCategoria(valores[1]);
                        categoria.setFornecedor(fornecedorOptional.get());

                        this.iCategoriaRepository.save(categoria);

                    } else {
                        throw new IllegalArgumentException(String.format("Id %s não existe", fornecedorOptional));
                    }
                }
            }

        } catch (IOException e) {
            LOGGER.info("Erro ao ler o arquivo .CSV", e);
        }

    }

}
