package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SpecialtyServiceMap extends AbstractMapService<Speciality, Long> implements SpecialtyService {

  @Override
  public Set<Speciality> findAll() {
    return super.findAll();
  }

  @Override
  public Speciality save(Speciality speciality ) {
    return super.save(speciality);
  }

  @Override
  public void delete(Speciality speciality) {
    super.delete(speciality);
  }

  @Override
  public void DeleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public Speciality findById(Long id) {
    return super.findById(id);
  }
}
