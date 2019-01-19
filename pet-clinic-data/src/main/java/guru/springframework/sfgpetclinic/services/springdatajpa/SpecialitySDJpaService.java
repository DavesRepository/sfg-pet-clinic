package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialityRepository;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialitySDJpaService implements SpecialityService {

  private final SpecialityRepository specialityRepository;

  public SpecialitySDJpaService(SpecialityRepository specialityRepository) {
    this.specialityRepository = specialityRepository;
  }

  @Override
  public Set<Speciality> findAll() {
    final HashSet<Speciality> specialitys = new HashSet<>();
    specialityRepository.findAll().forEach(specialitys::add);
    return specialitys;
  }

  @Override
  public Speciality findById(Long id) {
    return specialityRepository.findById(id).orElse(null);
  }

  @Override
  public Speciality save(Speciality speciality) {
    return specialityRepository.save(speciality);
  }

  @Override
  public void delete(Speciality speciality) {
    specialityRepository.delete(speciality);
  }

  @Override
  public void deleteById(Long id) {
    specialityRepository.deleteById(id);
  }
}
