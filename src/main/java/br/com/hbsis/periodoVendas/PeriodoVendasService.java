package br.com.hbsis.periodoVendas;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodoVendasService {

    private final IPeriodoVendasRepository iPeriodoVendasRepository;
    private final FornecedorService fornecedorService;

    public PeriodoVendasService(IPeriodoVendasRepository iPeriodoVendasRepository, FornecedorService fornecedorService) {
        this.iPeriodoVendasRepository = iPeriodoVendasRepository;
        this.fornecedorService = fornecedorService;
    }

    public PeriodoVendasDTO save(PeriodoVendasDTO periodoVendasDTO) {
        periodoVendasDTO.setFornecedor(fornecedorService.findFornecedorById(periodoVendasDTO.getFornecedor().getId()));

        LocalDate dataInicioVendas = periodoVendasDTO.getDataInicioVendas();
        LocalDate dataFimVendas = periodoVendasDTO.getDataFimVendas();

        this.validateOrderOfDates(dataInicioVendas,dataFimVendas);
        this.validatePeriodBeforeToday(dataInicioVendas);
        this.validateConflictingPeriods(dataInicioVendas, dataFimVendas, periodoVendasDTO.getFornecedor());

        PeriodoVendas periodoVendas = new PeriodoVendas();

        periodoVendas.setDataInicioVendas(periodoVendasDTO.getDataInicioVendas());
        periodoVendas.setDataFimVendas(periodoVendasDTO.getDataFimVendas());
        periodoVendas.setDataRetiradaPedido(periodoVendasDTO.getDataRetiradaPedido());
        periodoVendas.setDescricao(periodoVendasDTO.getDescricao());
        periodoVendas.setFornecedor(periodoVendasDTO.getFornecedor());

        periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

        return PeriodoVendasDTO.of(periodoVendas);

    }

    public PeriodoVendasDTO update(PeriodoVendasDTO periodoVendasDTO, Long id) {
        Optional<PeriodoVendas> periodoVendasOptional = this.iPeriodoVendasRepository.findById(id);

        if (periodoVendasOptional.isPresent()) {

            LocalDate dataInicioVendas = periodoVendasDTO.getDataInicioVendas();
            LocalDate dataFimVendas = periodoVendasDTO.getDataFimVendas();
            LocalDate dateOfToday = LocalDate.now();

            this.validateOrderOfDates(dataInicioVendas,dataFimVendas);
            this.validateEndValidity(dataFimVendas);
            this.validateConflictingPeriods(dataInicioVendas, dataFimVendas, periodoVendasDTO.getFornecedor());

            PeriodoVendas periodoVendas = periodoVendasOptional.get();

            if (periodoVendasDTO.getDataInicioVendas().isAfter(dateOfToday)) {
                periodoVendas.setDataInicioVendas(periodoVendasDTO.getDataInicioVendas());
            }

            periodoVendas.setFornecedor(fornecedorService.findFornecedorById(periodoVendasDTO.getFornecedor().getId()));
            periodoVendas.setDataFimVendas(periodoVendasDTO.getDataFimVendas());
            periodoVendas.setDataRetiradaPedido(periodoVendasDTO.getDataRetiradaPedido());
            periodoVendas.setDescricao(periodoVendasDTO.getDescricao());

            periodoVendas = this.iPeriodoVendasRepository.save(periodoVendas);

            return PeriodoVendasDTO.of(periodoVendas);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));

    }

    public List<PeriodoVendas> findAll() {
        return this.iPeriodoVendasRepository.findAll();
    }

    public PeriodoVendasDTO findById(Long id) {
        Optional<PeriodoVendas> periodoVendasOptional = this.iPeriodoVendasRepository.findById(id);

        if (periodoVendasOptional.isPresent()) {
            return PeriodoVendasDTO.of(periodoVendasOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        this.iPeriodoVendasRepository.deleteById(id);
    }

    private void validateOrderOfDates(LocalDate dataInicial, LocalDate dataFinal){

        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException("Ordem das datas incorreto!");
        }
    }

    private void validatePeriodBeforeToday(LocalDate dataInicial) {

        LocalDate dateOfToday = LocalDate.now();

        if (dataInicial.isBefore(dateOfToday)) {
            throw new IllegalArgumentException("Periodo não deve ser criado anterior à hoje!");
        }
    }

    private void validateEndValidity(LocalDate dataFinal) {

        LocalDate dateOfToday = LocalDate.now();

        if (dataFinal.isBefore(dateOfToday)){
            throw new IllegalArgumentException("Periodo já possui sua data de vigência expirado!");
        }
    }

    private void validateConflictingPeriods(LocalDate dataInicial, LocalDate dataFinal, Fornecedor fornecedor) {
        int page = 0;
        int itemsByPage = 50;

        Pageable paging = PageRequest.of(page, itemsByPage);
        int pagesOfRequest = iPeriodoVendasRepository.findByFornecedor(fornecedor, paging).getTotalPages();

        for (int i = pagesOfRequest; i != 0; i--) {
            Page<PeriodoVendas> periodosVendas = iPeriodoVendasRepository.findByFornecedor(fornecedor, paging);

            for (PeriodoVendas periodo: periodosVendas) {
                LocalDate initialExistent = periodo.getDataInicioVendas();
                LocalDate finalExistent = periodo.getDataFimVendas();

                if (!(dataInicial.isBefore(initialExistent) && dataFinal.isBefore(initialExistent) || dataInicial.isAfter(finalExistent) && dataFinal.isAfter(finalExistent))) {
                    throw new IllegalArgumentException("Periodo está em conflito com outro já cadastrado!");
                }
            }
            page++;
            paging =PageRequest.of(page, itemsByPage);
        }
    }

}
