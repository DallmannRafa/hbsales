package br.com.hbsis.linhaDeCategoria;

import br.com.hbsis.categoriaProdutos.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinhaDeCategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaDeCategoriaService.class);

    private final ILinhaDeCategoriaRepository iLinhaDeCategoriaRepository;
    private final CategoriaService categoriaService;


    public LinhaDeCategoriaService(ILinhaDeCategoriaRepository iLinhaDeCategoriaRepository, CategoriaService categoriaService) {
        this.iLinhaDeCategoriaRepository = iLinhaDeCategoriaRepository;
        this.categoriaService = categoriaService;
    }

    public LinhaDeCategoriaDTO save(LinhaDeCategoriaDTO linhaDeCategoriaDTO) {

        linhaDeCategoriaDTO.setCategoriaDaLinhaCategoria(categoriaService.findCategoriaById(linhaDeCategoriaDTO.getCategoriaDaLinhaCategoria().getId()));

        LOGGER.info("Salvando Linha de Categoria");
        LOGGER.debug("Usuario: {}", linhaDeCategoriaDTO);

        LinhaDeCategoria linhaDeCategoria = new LinhaDeCategoria();
        linhaDeCategoria.setNomeLinhaCategoria(linhaDeCategoriaDTO.getNomeLinhaCategoria());
        linhaDeCategoria.setCodigoLinhaCategoria(linhaDeCategoriaDTO.getCodigoLinhaCategoria());
        linhaDeCategoria.setCategoriaDaLinhaCategoria(linhaDeCategoriaDTO.getCategoriaDaLinhaCategoria());

        linhaDeCategoria = this.iLinhaDeCategoriaRepository.save(linhaDeCategoria);

        return LinhaDeCategoriaDTO.of(linhaDeCategoria);
    }

    public List<LinhaDeCategoria> findAll() {

        LOGGER.info("Retornando resultados da pesquisa");

        return this.iLinhaDeCategoriaRepository.findAll();
    }

    public LinhaDeCategoriaDTO findById(Long id) {

        LOGGER.info("Retornando resultado da pesquisa pelo ID:" + id);

        Optional<LinhaDeCategoria> linhaDeCategoriaOptional= this.iLinhaDeCategoriaRepository.findById(id);

        if (linhaDeCategoriaOptional.isPresent()) {
            return LinhaDeCategoriaDTO.of(linhaDeCategoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public LinhaDeCategoriaDTO update(LinhaDeCategoriaDTO linhaDeCategoriaDTO, Long id) {
        Optional<LinhaDeCategoria> linhaDeCategoriaOptional = this.iLinhaDeCategoriaRepository.findById(id);

        if (linhaDeCategoriaOptional.isPresent()) {

            LOGGER.info("Atualizando Linha de Categoria com ID:" + id);

            LinhaDeCategoria linhaDeCategoriaExistente = linhaDeCategoriaOptional.get();

            linhaDeCategoriaExistente.setCodigoLinhaCategoria(linhaDeCategoriaDTO.getCodigoLinhaCategoria());
            linhaDeCategoriaExistente.setNomeLinhaCategoria(linhaDeCategoriaDTO.getNomeLinhaCategoria());
            linhaDeCategoriaExistente.setCategoriaDaLinhaCategoria(linhaDeCategoriaDTO.getCategoriaDaLinhaCategoria());

            linhaDeCategoriaExistente = this.iLinhaDeCategoriaRepository.save(linhaDeCategoriaExistente);

            return LinhaDeCategoriaDTO.of(linhaDeCategoriaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {

        LOGGER.info("Deletando Linha de Categoria com ID:" + id);

        this.iLinhaDeCategoriaRepository.deleteById(id);
    }

    public String[][] stringFyToCSVAll() {
        List<LinhaDeCategoria> linhasDeCategoria = this.iLinhaDeCategoriaRepository.findAll();
        String[] header = {"id", "nomeLinhaCategoria", "codigoLinhaCategoria", "categoriaDaLinhaCategoria"};
        String[][] atributos = new String [linhasDeCategoria.size() + 1] [4];
        int contador = 1;

        atributos[0] = header;

        for (LinhaDeCategoria linhaDeCategoria : linhasDeCategoria) {
            Optional<LinhaDeCategoria> linhaDeCategoriaOptional = Optional.ofNullable(linhaDeCategoria);

            if (linhaDeCategoriaOptional.isPresent()) {
                atributos[contador][0] = linhaDeCategoria.getId().toString();
                atributos[contador][1] = linhaDeCategoria.getNomeLinhaCategoria();
                atributos[contador][2] = linhaDeCategoria.getCodigoLinhaCategoria();
                atributos[contador][3] = linhaDeCategoria.getCategoriaDaLinhaCategoria().getId().toString();
            }

            contador++;
        }

        return atributos;
    }

    public String[][] stringFyToCSVById (Long id) {
        String[] header = {"id", "nomeLinhaCategoria", "codigoLinhaCategoria", "categoriaDaLinhaCategoria"};
        String[][] dados = new String[2][4];

        dados [0] = header;

        Optional<LinhaDeCategoria> linhaDeCategoriaOptional = this.iLinhaDeCategoriaRepository.findById(id);

        if (linhaDeCategoriaOptional.isPresent()) {
            LinhaDeCategoriaDTO linha = LinhaDeCategoriaDTO.of(linhaDeCategoriaOptional.get());
            dados[1][0] = linha.getId().toString();
            dados[1][1] = linha.getNomeLinhaCategoria();
            dados[1][2] = linha.getCodigoLinhaCategoria();
            dados[1][3] = linha.getCategoriaDaLinhaCategoria().getId().toString();

            return dados;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }
}
