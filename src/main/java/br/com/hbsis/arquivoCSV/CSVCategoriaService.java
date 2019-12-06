package br.com.hbsis.arquivoCSV;

import br.com.hbsis.categoriaProdutos.Categoria;
import br.com.hbsis.categoriaProdutos.CategoriaService;
import br.com.hbsis.categoriaProdutos.ICategoriaRepository;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

@Service
public class CSVCategoriaService {

    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;
    private final ICategoriaRepository iCategoriaRepository;

    public CSVCategoriaService(FornecedorService fornecedorService, CategoriaService categoriaService, ICategoriaRepository iCategoriaRepository) {
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
        this.iCategoriaRepository = iCategoriaRepository;
    }

    public void manyToCSV (HttpServletResponse response) throws IOException {
        String[][] dados = this.categoriaService.stringFyToCSVAll();

        String fileName = "categoria.csv";

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

        ////////

        String teste = "222.333.444/22-0000";
        System.out.println("antigo: " + teste);
        teste = teste.replaceAll("[^0-9]", "");
        System.out.println("novo: " + teste);

    }

    public void oneToCSV (HttpServletResponse response, Long id) throws IOException {

        String[][] dados = categoriaService.stringFyToCSVbyId(id);

        String fileName = "categoria.csv";

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
                Optional<Fornecedor> fornecedorOptional = fornecedorService.findByIdOptional(Long.parseLong(valores[3]));

                if (fornecedorOptional.isPresent()) {
                    Categoria categoria = new Categoria();
                    categoria.setCodigoCategoria(valores[1]);
                    categoria.setNomeCategoria(valores[2]);
                    categoria.setFornecedor(fornecedorOptional.get());

                    this.iCategoriaRepository.save(categoria);

                } else {
                    throw new IllegalArgumentException(String.format("Id %s não existe", fornecedorOptional));
                }

            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
