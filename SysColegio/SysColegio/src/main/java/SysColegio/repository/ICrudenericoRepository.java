package SysColegio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICrudenericoRepository <T,ID> extends JpaRepository<T,ID> {
}
