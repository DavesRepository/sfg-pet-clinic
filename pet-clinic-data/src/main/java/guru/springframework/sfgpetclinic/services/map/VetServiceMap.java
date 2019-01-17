package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {

  private SpecialityService specialityService;

  public VetServiceMap(SpecialityService specialityService) {
    this.specialityService = specialityService;
  }

  @Override
  public Set<Vet> findAll() {
    return super.findAll();
  }

  @Override
  public void delete(Vet vet) {
    super.delete(vet);
  }

  @Override
  public Vet save(Vet vet) {
    if (!vet.getSpecialities().isEmpty()){
      vet.getSpecialities().forEach(speciality -> {
        if (speciality.getId() == null){
          Speciality savedSpeciality = specialityService.save(speciality);
          speciality.setId(savedSpeciality.getId());
        }
      });
    }

    return super.save(vet);
  }

  @Override
  public void DeleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public Vet findById(Long id) {
    return super.findById(id);
  }
}
