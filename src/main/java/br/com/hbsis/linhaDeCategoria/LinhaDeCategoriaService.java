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

        return LinhaDeCategoriaDTO.of(linhaDeCategoria);
    }

    public List<LinhaDeCategoria> findAll() {

        return this.iLinhaDeCategoriaRepository.findAll();
    }

    public LinhaDeCategoriaDTO findById(Long id) {
        Optional<LinhaDeCategoria> linhaDeCategoriaOptional= this.iLinhaDeCategoriaRepository.findById(id);

        if (linhaDeCategoriaOptional.isPresent()) {
            return LinhaDeCategoriaDTO.of(linhaDeCategoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public LinhaDeCategoriaDTO update(LinhaDeCategoriaDTO linhaDeCategoriaDTO, Long id) {
        Optional<LinhaDeCategoria> linhaDeCategoriaOptional = this.iLinhaDeCategoriaRepository.findById(id);

        if (linhaDeCategoriaOptional.isPresent()) {
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
        this.iLinhaDeCategoriaRepository.deleteById(id);
    }
}
