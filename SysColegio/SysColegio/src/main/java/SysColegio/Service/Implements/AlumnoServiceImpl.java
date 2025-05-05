package SysColegio.Service.Implements;

import SysColegio.Service.IAlumnoService;
import SysColegio.model.Alumno;
import SysColegio.repository.IAlumnoRepository;
import SysColegio.repository.ICrudenericoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Transactional
@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl extends CrudGenericService<Alumno,Long> implements IAlumnoService {
    private final IAlumnoRepository alumnoRepository;
    @Override
    protected ICrudenericoRepository getRepo() {
        return alumnoRepository;
    }
}
