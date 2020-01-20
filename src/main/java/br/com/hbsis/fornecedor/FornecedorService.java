package br.com.hbsis.fornecedor;

import br.com.hbsis.categoriaProdutos.Categoria;
import br.com.hbsis.categoriaProdutos.CategoriaDTO;
import br.com.hbsis.categoriaProdutos.CategoriaService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);

    private final IFornecedorRepository iFornecedorRepository;
    private final CategoriaService categoriaService;

    public FornecedorService(IFornecedorRepository iFornecedorRepository, CategoriaService categoriaService) {
        this.iFornecedorRepository = iFornecedorRepository;

        this.categoriaService = categoriaService;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        LOGGER.info("Salvando fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();

        fornecedorDTO.setCnpj(fornecedorDTO.getCnpj().replaceAll("[^0-9]", ""));
        fornecedorDTO.setTelefone(fornecedorDTO.getTelefone().replaceAll("[^0-9]", ""));

        this.validate(fornecedorDTO);

        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        fornecedor = this.iFornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorDTO.setCnpj(fornecedorDTO.getCnpj().replaceAll("[^0-9]", ""));
            fornecedorDTO.setTelefone(fornecedorDTO.getTelefone().replaceAll("[^0-9]", ""));

            this.validate(fornecedorDTO);

            fornecedorExistente.setRazaoSocial(fornecedorDTO.getRazaoSocial());
            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());

            fornecedorExistente = this.iFornecedorRepository.save(fornecedorExistente);

            this.atualizaCategoria(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public List<Fornecedor> findAll() {

        return iFornecedorRepository.findAll();

    }

    private void validate(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Validando Fornecedor");

        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getRazaoSocial())) {
            throw new IllegalArgumentException("Razao Social não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ não deve ser nula/vazia");
        }

        if (fornecedorDTO.getCnpj().length() != 14) {
            throw new IllegalArgumentException("CNPJ deve conter 14 digitos");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getNomeFantasia())) {
            throw new IllegalArgumentException("Nome Fantasia não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getEndereco())) {
            throw new IllegalArgumentException("Endereco Social não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getTelefone())) {
            throw new IllegalArgumentException("Telefone Social não deve ser nula/vazia");
        }

        if (!((fornecedorDTO.getTelefone().length() >= 13) && (fornecedorDTO.getTelefone().length() <= 14))) {
            throw new IllegalArgumentException("telefone deve conter entre 13 e 14 números");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail Social não deve ser nula/vazia");
        }
    }

    public FornecedorDTO findById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return FornecedorDTO.of(fornecedorOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Optional<Fornecedor> findByIdOptional(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional;
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Optional<Fornecedor> findByCnpjOptional(String cnpj) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findByCnpj(cnpj);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional;
        }

        throw new IllegalArgumentException(String.format("CNPJ %s não existe", cnpj));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para FORNECEDOR de ID: [{}]", id);

        this.iFornecedorRepository.deleteById(id);
    }

    public Fornecedor findFornecedorById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void atualizaCategoria(Fornecedor fornecedor) {
        List<Categoria> categorias = categoriaService.findByFornecedor(fornecedor);

        for (Categoria categoria : categorias) {
            CategoriaDTO categoriaDTO = CategoriaDTO.of(categoria);
            categoriaDTO.setFornecedor(fornecedor);
            categoriaService.update(categoriaDTO, categoria.getId());
        }
    }


}