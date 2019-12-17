package br.com.hbsis.categoriaProdutos;

import br.com.hbsis.fornecedor.FornecedorService;
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

        String codigoInformadoPeloUsuario = categoriaDTO.getCodCategoria();
        String CNPJFornecedor = categoriaDTO.getFornecedor().getCnpj();

        Categoria categoria = new Categoria();

        categoria.setCodigoCategoria(this.codeGenerator(CNPJFornecedor, codigoInformadoPeloUsuario));
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(categoriaDTO.getFornecedor());


        categoria = this.iCategoriaRepository.save(categoria);
        return CategoriaDTO.of(categoria);

    }

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();
            categoriaDTO.setFornecedor(fornecedorService.findFornecedorById((categoriaDTO.getFornecedor().getId())));

            LOGGER.info("Atualizando categoria... id: [{}]", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaDTO);
            LOGGER.debug("Categoria Existente: {}", categoriaExistente);

            String codigoInformadoPeloUsuario = categoriaDTO.getCodCategoria();
            String CNPJFornecedor = categoriaDTO.getFornecedor().getCnpj();

            categoriaExistente.setFornecedor(categoriaDTO.getFornecedor());
            categoriaExistente.setCodigoCategoria(this.codeGenerator(CNPJFornecedor, codigoInformadoPeloUsuario));
            categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());

            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);

            return CategoriaDTO.of(categoriaExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
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

    public Optional<Categoria> findByCodigoCategoriaOptional(String codigoCategoria){
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findByCodigoCategoria(codigoCategoria);

        if(categoriaOptional.isPresent()){
            return categoriaOptional;
        }

        throw new IllegalArgumentException(String.format("Código %s não existe", codigoCategoria));
    }

    public Categoria findCategoriaById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoriaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para FORNECEDOR de ID: [{}]", id);

        this.iCategoriaRepository.deleteById(id);
    }

    public String[][] stringFyToCSVbyId(Long id) {
        String[] header = new String[]{"cod_categoria", "nome_categoria", "razao_social_fornecedor", "cnpj_fornecedor"};
        String[][] atributos = new String[2][4];

        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            CategoriaDTO categoria = CategoriaDTO.of(categoriaOptional.get());
            String CNPJ = categoria.getFornecedor().getCnpj();

            atributos[1][0] = categoria.getCodCategoria();
            atributos[1][1] = categoria.getNomeCategoria();
            atributos[1][2] = categoria.getFornecedor().getRazaoSocial();
            atributos[1][3] = CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" + CNPJ.substring(12, 14);

            atributos[0] = header;

            return atributos;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public String[][] stringFyToCSVAll() {
        List<Categoria> categorias = this.iCategoriaRepository.findAll();
        String[] header = new String[]{"cod_categoria", "nome_categoria", "razao_social_fornecedor", "cnpj_fornecedor"};
        String[][] atributos = new String[categorias.size() + 1][4];
        int contador = 1;

        atributos[0] = header;

        for (Categoria cat : categorias) {
            Optional<Categoria> categoriaOptional = Optional.ofNullable(cat);

            if (categoriaOptional.isPresent()) {
                CategoriaDTO categoria = CategoriaDTO.of(categoriaOptional.get());
                String CNPJ = categoria.getFornecedor().getCnpj();

                atributos[contador][0] = categoria.getCodCategoria();
                atributos[contador][1] = categoria.getNomeCategoria();
                atributos[contador][2] = categoria.getFornecedor().getRazaoSocial();
                atributos[contador][3] = CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" + CNPJ.substring(12, 14);
            }

            contador++;
        }

        return atributos;
    }

    public String codeGenerator (String cnpjFornecedor, String codigoInformado) {

        codigoInformado = codigoInformado.replaceAll("[^0-9]", "");
        cnpjFornecedor = cnpjFornecedor.replaceAll("[^0-9]", "");

        if (codigoInformado.length() >= 3) {
            codigoInformado = codigoInformado.substring(codigoInformado.length() - 3);
        } else {
            while (codigoInformado.length() < 3) {
                codigoInformado = "0" + codigoInformado;
            }
        }

        return "CAT" + cnpjFornecedor.substring(10) + codigoInformado;
    }

    public Boolean existsByCodigoCategoria(String codigoCategoria) {
        return this.iCategoriaRepository.existsByCodigoCategoria(codigoCategoria);
    }


}
