package br.com.hbsis.arquivoCSV.CSVLinhaCategoria;

import br.com.hbsis.arquivoCSV.CSVUtils;
import br.com.hbsis.categoriaProdutos.Categoria;
import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.linhaDeCategoria.ILinhaDeCategoriaRepository;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoria;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaService;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class CSVLinhaCategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVLinhaCategoriaService.class);

    private final ILinhaDeCategoriaRepository iLinhaDeCategoriaRepository;
    private final LinhaDeCategoriaService linhaDeCategoriaService;
    private final CategoriaService categoriaService;
    private final CSVUtils csvUtils;

    public CSVLinhaCategoriaService(ILinhaDeCategoriaRepository iLinhaDeCategoriaRepository, LinhaDeCategoriaService linhaDeCategoriaService, CategoriaService categoriaService, CSVUtils csvUtils) {
        this.iLinhaDeCategoriaRepository = iLinhaDeCategoriaRepository;
        this.linhaDeCategoriaService = linhaDeCategoriaService;
        this.categoriaService = categoriaService;
        this.csvUtils = csvUtils;
    }

    public void manyToCSV(HttpServletResponse response) throws IOException {
        String[][] dados = this.linhaDeCategoriaService.stringFyToCSVAll();

        ICSVWriter writer = csvUtils.writerBuilder("linhasdecategoria.csv", response);

        for (String[] a : dados) {
            writer.writeNext(a);

        }

    }

    public void oneToCSV(HttpServletResponse response, Long id) throws IOException {

        String[][] dados = linhaDeCategoriaService.stringFyToCSVById(id);

        ICSVWriter writer = csvUtils.writerBuilder("linhadecategoria.csv", response);

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

                String codigoLinhaCategoria = valores[0];

                if (!(this.iLinhaDeCategoriaRepository.existsByCodigoLinhaCategoria(codigoLinhaCategoria))) {

                    String codigoCategoria = valores[2];
                    Optional<Categoria> categoriaOptional = categoriaService.findByCodigoCategoriaOptional(codigoCategoria);

                    if (categoriaOptional.isPresent()) {
                        LinhaDeCategoria linhaDeCategoria = new LinhaDeCategoria();
                        linhaDeCategoria.setNomeLinhaCategoria(valores[1]);
                        linhaDeCategoria.setCodigoLinhaCategoria(linhaDeCategoriaService.codeGenerator(codigoLinhaCategoria));
                        linhaDeCategoria.setCategoriaDaLinhaCategoria(categoriaOptional.get());

                        this.iLinhaDeCategoriaRepository.save(linhaDeCategoria);

                    } else {
                        throw new IllegalArgumentException(String.format("Id %s não existe", categoriaOptional));
                    }

                }

            }

        } catch (IOException e) {
            LOGGER.info("Erro ao ler o arquivo .CSV", e);
        }

    }
}
