package SysColegio.Controller;


import SysColegio.Service.IAlumnoService;
import SysColegio.Service.IGradoService;
import SysColegio.model.Alumno;
import SysColegio.model.Grado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/grados")
public class GradoController {
    private final IGradoService gradoService;



    @GetMapping
    public ResponseEntity<List<Grado>> findAll() {
        List<Grado> list = gradoService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Grado> findById(@PathVariable("id") Long
                                                   id) {
        Grado obj = gradoService.findById(id);
        return ResponseEntity.ok(obj);
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody Grado dto) {
        Grado obj = gradoService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
                obj.getIdGrado()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Grado> update(@PathVariable("id") Long
                                                 id, @RequestBody
                                         Grado dto) {
        dto.setIdGrado(id);
        Grado obj = gradoService.update(id, dto);
        return ResponseEntity.ok(obj);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        gradoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}