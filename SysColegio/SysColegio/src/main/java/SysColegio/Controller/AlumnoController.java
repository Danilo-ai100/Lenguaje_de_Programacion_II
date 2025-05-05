package SysColegio.Controller;

import SysColegio.Service.IAlumnoService;
import SysColegio.model.Alumno;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    private final IAlumnoService alumnoService;

    public AlumnoController(IAlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public ResponseEntity<List<Alumno>> findAll() {
        List<Alumno> list = alumnoService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> findById(@PathVariable("id") Long
                                                      id) {
        Alumno obj = alumnoService.findById(id);
        return ResponseEntity.ok(obj);
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody Alumno dto) {
        Alumno obj = alumnoService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
                        obj.getIdAlumno()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> update(@PathVariable("id") Long
                                                    id, @RequestBody
                                            Alumno dto) {
        dto.setIdAlumno(id);
        Alumno obj = alumnoService.update(id, dto);
        return ResponseEntity.ok(obj);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        alumnoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
