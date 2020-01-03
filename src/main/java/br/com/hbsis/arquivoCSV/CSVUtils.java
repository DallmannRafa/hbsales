package br.com.hbsis.arquivoCSV;

import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CSVUtils {

    public ICSVWriter writerBuilder(String nomeArquivo, HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"");

        ICSVWriter writer = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withQuoteChar(ICSVWriter.NO_QUOTE_CHARACTER)
                .withEscapeChar(ICSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(ICSVWriter.DEFAULT_LINE_END)
                .build();

        return writer;
    }
}
