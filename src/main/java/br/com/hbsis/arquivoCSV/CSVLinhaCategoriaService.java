package br.com.hbsis.arquivoCSV;

import br.com.hbsis.categoriaProdutos.Categoria;
import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.linhaDeCategoria.ILinhaDeCategoriaRepository;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoria;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaService;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class CSVLinhaCategoriaService {

    public final ILinhaDeCategoriaRepository iLinhaDeCategoriaRepository;
    public final LinhaDeCategoriaService linhaDeCategoriaService;
    public final CategoriaService categoriaService;

    public CSVLinhaCategoriaService(ILinhaDeCategoriaRepository iLinhaDeCategoriaRepository, LinhaDeCategoriaService linhaDeCategoriaService, CategoriaService categoriaService) {
        this.iLinhaDeCategoriaRepository = iLinhaDeCategoriaRepository;
        this.linhaDeCategoriaService = linhaDeCategoriaService;
        this.categoriaService = categoriaService;
    }

    public void manyToCSV (HttpServletResponse response) throws IOException {
        String[][] dados = this.linhaDeCategoriaService.stringFyToCSVAll();

        String fileName = "linhasdecategoria.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        ICSVWriter writer = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
                .withEscapeChar(ICSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                .build();

        for (String[] a: dados) {
            writer.writeNext(a);

        }

    }

    public void oneToCSV (HttpServletResponse response, Long id) throws IOException {

        String[][] dados = linhaDeCategoriaService.stringFyToCSVById(id);

        String fileName = "linhadecategoria.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        ICSVWriter writer = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
                .withEscapeChar(ICSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                .build();

        for (String[] a: dados) {
            writer.writeNext(a);

        }

    }

    public void readCSV(MultipartFile file) {
        String linhaArquivo;
        String quebraLinha = ";";

        try(BufferedReader csvReader = new BufferedReader (new InputStreamReader(file.getInputStream()))){

            while ((linhaArquivo = csvReader.readLine()) != null) {
                String[] valores = linhaArquivo.split(quebraLinha);
                Optional<Categoria> categoriaOptional = categoriaService.findByIdOptional(Long.parseLong(valores[3]));

                if (categoriaOptional.isPresent()) {
                    LinhaDeCategoria linhaDeCategoria = new LinhaDeCategoria();
                    linhaDeCategoria.setNomeLinhaCategoria(valores[1]);
                    linhaDeCategoria.setCodigoLinhaCategoria(valores[2]);
                    linhaDeCategoria.setCategoriaDaLinhaCategoria(categoriaOptional.get());

                    this.iLinhaDeCategoriaRepository.save(linhaDeCategoria);

                } else {
                    throw new IllegalArgumentException(String.format("Id %s não existe", categoriaOptional));
                }

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
