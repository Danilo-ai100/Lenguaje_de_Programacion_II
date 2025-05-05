package SysColegio.Controller;

import SysColegio.Service.IGradoService;
import SysColegio.Service.IMatriculaService;
import SysColegio.model.Grado;
import SysColegio.model.Matricula;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/matriculas")
public class MatriculaController {
    private final IMatriculaService matriculaService;



    @GetMapping
    public ResponseEntity<List<Matricula>> findAll() {
        List<Matricula> list = matriculaService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable("id") Long
                                                  id) {
        Matricula obj = matriculaService.findById(id);
        return ResponseEntity.ok(obj);
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody Matricula dto) {
        Matricula obj = matriculaService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
                obj.getIdMatricula()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@PathVariable("id") Long
                                                id, @RequestBody
                                        Matricula dto) {
        dto.setIdMatricula(id);
        Matricula obj = matriculaService.update(id, dto);
        return ResponseEntity.ok(obj);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        matriculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}