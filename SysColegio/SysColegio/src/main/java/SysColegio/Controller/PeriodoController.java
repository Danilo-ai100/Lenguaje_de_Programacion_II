package SysColegio.Controller;

import SysColegio.Service.IGradoService;
import SysColegio.Service.IPeriodoService;
import SysColegio.model.Grado;
import SysColegio.model.Periodo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/periodos")
public class PeriodoController {
    private final IPeriodoService periodoService;



    @GetMapping
    public ResponseEntity<List<Periodo>> findAll() {
        List<Periodo> list = periodoService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Periodo> findById(@PathVariable("id") Long
                                                  id) {
        Periodo obj = periodoService.findById(id);
        return ResponseEntity.ok(obj);
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody Periodo dto) {
        Periodo obj = periodoService.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(
                obj.getIdPeriodo()).toUri();
        return ResponseEntity.created(location).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Periodo> update(@PathVariable("id") Long
                                                id, @RequestBody
                                        Periodo dto) {
        dto.setIdPeriodo(id);
        Periodo obj = periodoService.update(id, dto);
        return ResponseEntity.ok(obj);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        periodoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}