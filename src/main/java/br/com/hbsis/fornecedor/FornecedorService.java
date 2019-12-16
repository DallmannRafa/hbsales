package br.com.hbsis.fornecedor;

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

    public FornecedorService(IFornecedorRepository iFornecedorRepository) {
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        this.validate(fornecedorDTO);

        LOGGER.info("Salvando fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();

        String CNPJ = fornecedorDTO.getCnpj();
        CNPJ = CNPJ.replaceAll("[^0-9]", "");
        String Telefone = fornecedorDTO.getTelefone();
        Telefone = Telefone.replaceAll("[^0-9]", "");

        if((CNPJ.length() == 14) && ((Telefone.length() >= 13) && (Telefone.length() <= 14))) {
            fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
            fornecedor.setCnpj(CNPJ);
            fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedor.setEndereco(fornecedorDTO.getEndereco());
            fornecedor.setTelefone(Telefone);
            fornecedor.setEmail(fornecedorDTO.getEmail());
        } else {
            LOGGER.info("CNPJ ou Telefone inválidos, certifique-se de que contenham todos os digitos válidos!");
        }

        fornecedor = this.iFornecedorRepository.save(fornecedor);

        return FornecedorDTO.of(fornecedor);
    }

    public List<Fornecedor> findAll() {

        return iFornecedorRepository.findAll();

    }

    private void validate(FornecedorDTO fornecedorDTO) {
        LOGGER.info("Validando Fornecedor");

        if (fornecedorDTO == null) {
            throw new IllegalArgumentException("FornecedorDTO não deve ser nulo");
        }

        System.out.println(fornecedorDTO.getRazaoSocial());

        if (StringUtils.isEmpty(fornecedorDTO.getRazaoSocial())) {
            throw new IllegalArgumentException("Razao Social não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(fornecedorDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ não deve ser nula/vazia");
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


    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando fornecedor... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor Existente: {}", fornecedorExistente);

            fornecedorExistente.setRazaoSocial(fornecedorDTO.getRazaoSocial());
            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());

            fornecedorExistente = this.iFornecedorRepository.save(fornecedorExistente);

            return FornecedorDTO.of(fornecedorExistente);
        }


        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para FORNECEDOR de ID: [{}]", id);

        this.iFornecedorRepository.deleteById(id);
    }

    public String stringFy(Long id) {
        return this.iFornecedorRepository.findById(id).toString();
    }

    public Fornecedor findFornecedorById(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.iFornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            return fornecedorOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


}