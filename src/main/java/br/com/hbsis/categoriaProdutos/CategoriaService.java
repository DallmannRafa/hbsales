package br.com.hbsis.categoriaProdutos;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);

    private final ICategoriaRepository iCategoriaRepository;

    public CategoriaService(ICategoriaRepository iCategoriaRepository) {
        this.iCategoriaRepository = iCategoriaRepository;
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {

        this.validate(categoriaDTO);

        LOGGER.info("Salvando categoria");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Categoria categoria = new Categoria();
        categoria.setCodigoCategoria(categoriaDTO.getCodCategoria());
        categoria.setFornecedorCategoria(categoriaDTO.getFornecedorCategoria());
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());


        categoria = this.iCategoriaRepository.save(categoria);

        return CategoriaDTO.of(categoria);
    }

    public List<Categoria> findAll() {
        return iCategoriaRepository.findAll();
    }

    private void validate(CategoriaDTO categoriaDTO) {
        LOGGER.info("Validando Categoria");

        if (categoriaDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(categoriaDTO.getCodCategoria())) {
            throw new IllegalArgumentException("Codigo da categoria não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(categoriaDTO.getFornecedorCategoria())) {
            throw new IllegalArgumentException("Fornecedor da categoria não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(categoriaDTO.getNomeCategoria())) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nula/vazia");
        }

    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando categoria... id: [{}]", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaDTO);
            LOGGER.debug("Categoria Existente: {}", categoriaExistente);

            categoriaExistente.setFornecedorCategoria(categoriaDTO.getFornecedorCategoria());
            categoriaExistente.setCodigoCategoria(categoriaDTO.getCodCategoria());
            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());

            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);

            return CategoriaDTO.of(categoriaExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para FORNECEDOR de ID: [{}]", id);

        this.iCategoriaRepository.deleteById(id);
    }
}
