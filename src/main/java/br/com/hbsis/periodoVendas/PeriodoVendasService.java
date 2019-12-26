package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoVendasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoVendasService.class);

    private final IPeriodoVendasRepository iPeriodoVendasRepository;
    private final FornecedorService fornecedorService;

    public PeriodoVendasService(IPeriodoVendasRepository iPeriodoVendasRepository, FornecedorService fornecedorService) {
        this.iPeriodoVendasRepository = iPeriodoVendasRepository;
        this.fornecedorService = fornecedorService;
    }

    public PeriodoVendasDTO save(PeriodoVendasDTO periodoVendasDTO) {
        periodoVendasDTO.setFornecedor(fornecedorService.findFornecedorById(periodoVendasDTO.getFornecedor().getId()));

        if (validateCriacaoPeriodoAnteriorAHoje(periodoVendasDTO.getDataInicioVendas())) {

            PeriodoVendas periodoVendas = new PeriodoVendas();

            Long id = 0L;

            if (this.validateDatas(periodoVendasDTO.getDataInicioVendas(), periodoVendasDTO.getDataFimVendas(), periodoVendasDTO.getFornecedor(), id)) {

                periodoVendas.setDataInicioVendas(periodoVendasDTO.getDataInicioVendas());
                periodoVendas.setDataFimVendas(periodoVendasDTO.getDataFimVendas());

            } else {
                throw new IllegalArgumentException("Data informada não válida!");
            }

            periodoVendas.setDataRetiradaPedido(periodoVendasDTO.getDataRetiradaPedido());
            periodoVendas.setDescricao(periodoVendasDTO.getDescricao());
            periodoVendas.setFornecedor(periodoVendasDTO.getFornecedor());

            periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

            return PeriodoVendasDTO.of(periodoVendas);

        }

        throw new IllegalArgumentException("Periodo não pode ser cadastrado anterior a data de hoje");

    }

    public PeriodoVendasDTO update(PeriodoVendasDTO periodoVendasDTO, Long id) {
        Optional<PeriodoVendas> periodoVendasOptional = this.iPeriodoVendasRepository.findById(id);

        if (periodoVendasOptional.isPresent()) {
            if (this.validateAlteracaoAposVigencia(periodoVendasDTO.getDataInicioVendas(), periodoVendasDTO.getDataFimVendas(), this.iPeriodoVendasRepository.findById(id).get())) {
                PeriodoVendas periodoVendas = periodoVendasOptional.get();

                periodoVendas.setFornecedor(fornecedorService.findFornecedorById(periodoVendasDTO.getFornecedor().getId()));

                if (this.validateDatas(periodoVendasDTO.getDataInicioVendas(), periodoVendasDTO.getDataFimVendas(), periodoVendas.getFornecedor(), id)) {

                    periodoVendas.setDataInicioVendas(periodoVendasDTO.getDataInicioVendas());
                    periodoVendas.setDataFimVendas(periodoVendasDTO.getDataFimVendas());

                }

                periodoVendas.setDataRetiradaPedido(periodoVendasDTO.getDataRetiradaPedido());
                periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

                periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

                return PeriodoVendasDTO.of(periodoVendas);
            }

            throw new IllegalArgumentException("Periodo de vigência já expirou");

        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public List<PeriodoVendas> findAll() {
        return this.iPeriodoVendasRepository.findAll();
    }

    public PeriodoVendasDTO findById (Long id) {
        Optional<PeriodoVendas> periodoVendasOptional = this.iPeriodoVendasRepository.findById(id);

        if (periodoVendasOptional.isPresent()) {
            return PeriodoVendasDTO.of(periodoVendasOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete (Long id) {
        this.iPeriodoVendasRepository.deleteById(id);
    }

    public List<PeriodoVendas> FindByFornecedor (Fornecedor fornecedor) {
        return this.iPeriodoVendasRepository.findByFornecedor(fornecedor);
    }

    public Boolean validateDatas(LocalDate dataInicio, LocalDate dataFim, Fornecedor fornecedor, Long id) {

      List<PeriodoVendas> periodosVendas = this.FindByFornecedor(fornecedor);
      Boolean validador = true;

      for (PeriodoVendas periodoVendas: periodosVendas) {

        LocalDate dataInicioExistente = periodoVendas.getDataInicioVendas();
        LocalDate dataFimExistente = periodoVendas.getDataFimVendas();
        Long idPeriodo = periodoVendas.getId();

        if (idPeriodo != id) {

            if (dataInicio.isAfter(dataFim)) {
                validador = false;
            } else if (!(dataFim.isBefore(dataInicioExistente) || dataInicio.isAfter(dataFimExistente))) {
                validador = false;
            }
        }

      }

      return validador;

    }

    public Boolean validateCriacaoPeriodoAnteriorAHoje (LocalDate dataInicial) {
        Boolean validador = true;
        LocalDate dateNow = LocalDate.now();

        if (dataInicial.isBefore(dateNow)) {
            validador = false;
        }

        return validador;
    }

    public Boolean validateAlteracaoAposVigencia (LocalDate dataInicioInformado, LocalDate dataFimInformado, PeriodoVendas periodoVendasExistente) {

        LocalDate dateNow =  LocalDate.now();
        Boolean validador = true;

        if (!dataInicioInformado.isEqual(periodoVendasExistente.getDataInicioVendas()) && !dataFimInformado.isEqual(periodoVendasExistente.getDataFimVendas())) {
            if (dateNow.isAfter(periodoVendasExistente.getDataFimVendas())) {
                validador = false;
            }
        }

        return validador;

    }

}
