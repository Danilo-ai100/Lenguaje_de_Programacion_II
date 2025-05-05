package SysColegio.Service.Implements;


import SysColegio.Service.IGradoService;
import SysColegio.model.Grado;
import SysColegio.repository.ICrudenericoRepository;
import SysColegio.repository.IGradoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class GradoServiceImpl extends CrudGenericService<Grado,Long> implements IGradoService {
    private final IGradoRepository gradoRepository;
    @Override
    protected ICrudenericoRepository getRepo() {
        return gradoRepository;
    }
}
