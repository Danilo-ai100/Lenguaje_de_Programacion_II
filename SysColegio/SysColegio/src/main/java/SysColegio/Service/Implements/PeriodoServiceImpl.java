package SysColegio.Service.Implements;

import SysColegio.Service.IMatriculaService;
import SysColegio.Service.IPeriodoService;
import SysColegio.model.Periodo;
import SysColegio.repository.ICrudenericoRepository;
import SysColegio.repository.IPeriodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class PeriodoServiceImpl extends CrudGenericService<Periodo,Long> implements IPeriodoService {

    private final IPeriodoRepository periodoRepository;
    @Override
    protected ICrudenericoRepository<Periodo, Long> getRepo() {
        return periodoRepository;
    }
}
