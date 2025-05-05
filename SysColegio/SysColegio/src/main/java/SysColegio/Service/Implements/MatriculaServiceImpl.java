package SysColegio.Service.Implements;

import SysColegio.Service.IMatriculaService;
import SysColegio.model.Matricula;
import SysColegio.repository.ICrudenericoRepository;
import SysColegio.repository.IMatriculaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl extends CrudGenericService<Matricula,Long> implements IMatriculaService {
    private final IMatriculaRepository matriculaRepository;
    @Override
    protected ICrudenericoRepository<Matricula, Long> getRepo() {
        return matriculaRepository;
    }
}
