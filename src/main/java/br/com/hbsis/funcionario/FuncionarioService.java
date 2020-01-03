package br.com.hbsis.funcionario;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FuncionarioService {

    private final IFuncionarioRepository iFuncionarioRepository;

    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository) {
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        Funcionario funcionario = new Funcionario();

        this.validate(funcionarioDTO);
        this.ValidateEmployee(funcionarioDTO);

        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setEmail(funcionarioDTO.getEmail());
        funcionario.setUuid(funcionarioDTO.getUuid());

        funcionario = this.iFuncionarioRepository.save(funcionario);

        return FuncionarioDTO.of(funcionario);
    }

    public FuncionarioDTO update(Long id, FuncionarioDTO funcionarioDTO) {
        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();

            funcionario.setNome(funcionarioDTO.getNome());
            funcionario.setEmail(funcionarioDTO.getEmail());

            funcionario = this.iFuncionarioRepository.save(funcionario);

            return FuncionarioDTO.of(funcionario);
        }

        throw new IllegalArgumentException("Id não existe");

    }

    public FuncionarioDTO findById(Long id) {

        Optional<Funcionario> funcionarioOptional = this.iFuncionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()) {
            return FuncionarioDTO.of(funcionarioOptional.get());
        }

        throw new IllegalArgumentException("Id não existe");
    }

    public List<Funcionario> findAll() {
        return this.iFuncionarioRepository.findAll();
    }

    public void delete(Long id) {
        this.iFuncionarioRepository.deleteById(id);
    }

    private void validate(FuncionarioDTO funcionarioDTO) {

        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("Funcionario não pode ser nulo");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getNome())) {
            throw new IllegalArgumentException("Nome do funcionário não pode ser nulo");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail não pode ser nulo");
        }

    }

    private void ValidateEmployee(FuncionarioDTO funcionarioDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "f59ff402-1b67-11ea-978f-2e728ce88125");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<FuncionarioDTO> httpEntity = new HttpEntity<>(funcionarioDTO,headers);
        ResponseEntity<EmployeeDTO> resultadoEmployee = restTemplate.exchange("http://10.2.54.25:9999/api/employees",  HttpMethod.POST, httpEntity, EmployeeDTO.class);
        funcionarioDTO.setUuid(Objects.requireNonNull(resultadoEmployee.getBody()).getEmployeeUuid());
    }


}
