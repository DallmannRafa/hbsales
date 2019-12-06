package br.com.hbsis.categoriaProdutos;

import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.linhaDeCategoria.LinhaDeCategoriaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);

    private final ICategoriaRepository iCategoriaRepository;
    private final FornecedorService fornecedorService;

    public CategoriaService(ICategoriaRepository iCategoriaRepository, FornecedorService fornecedorService) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.fornecedorService = fornecedorService;
    }



    public CategoriaDTO save(CategoriaDTO categoriaDTO) {

        categoriaDTO.setFornecedor(fornecedorService.findFornecedorById((categoriaDTO.getFornecedor().getId())));

        LOGGER.info("Salvando categoria");
        LOGGER.debug("Categoria: {}", categoriaDTO);
        LOGGER.debug("Fornecedor: {}", categoriaDTO.getFornecedor().getNomeFantasia());

        Categoria categoria = new Categoria(
                categoriaDTO.getCodCategoria(),
                categoriaDTO.getNomeCategoria(),
                categoriaDTO.getFornecedor()
        );

        categoria = this.iCategoriaRepository.save(categoria);

        return CategoriaDTO.of(categoria);

    }

    public List<Categoria> findAll() {

        return iCategoriaRepository.findAll();

    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Optional<Categoria> findByIdOptional(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional;

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Categoria findCategoriaById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
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

            categoriaExistente.setFornecedor(categoriaDTO.getFornecedor());
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

    public String[][] stringFyToCSVbyId (Long id) {
        String[] header = new String[] {"id", "codCategoria", "nomeCategoria", "fornecedor"};
        String[][] atributos = new String[2][4];

        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            atributos[1][0] = CategoriaDTO.of(categoriaOptional.get()).getId().toString();
            atributos[1][1] = CategoriaDTO.of(categoriaOptional.get()).getCodCategoria();
            atributos[1][2] = CategoriaDTO.of(categoriaOptional.get()).getNomeCategoria();
            atributos[1][3] = CategoriaDTO.of(categoriaOptional.get()).getFornecedor().getId().toString();

            atributos[0] = header;

            return atributos;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public String[][] stringFyToCSVAll () {
        List<Categoria> categorias= this.iCategoriaRepository.findAll();
        String[] header = new String[] {"id", "codCategoria", "nomeCategoria", "fornecedor"};
        String[][] atributos = new String[categorias.size() + 1][4];
        int contador = 1;

        atributos[0] = header;

        for (Categoria cat: categorias) {
            Optional<Categoria> categoriaOptional = Optional.ofNullable(cat);

            if (categoriaOptional.isPresent()) {
                atributos[contador] [0] = CategoriaDTO.of(categoriaOptional.get()).getId().toString();
                atributos[contador] [1] = CategoriaDTO.of(categoriaOptional.get()).getCodCategoria();
                atributos[contador] [2] = CategoriaDTO.of(categoriaOptional.get()).getNomeCategoria();
                atributos[contador] [3] = CategoriaDTO.of(categoriaOptional.get()).getFornecedor().getId().toString();
            }

            contador++;
        }

        return atributos;
    }

}
