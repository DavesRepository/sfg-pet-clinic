package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

  private final OwnerService ownerService;
  private final VetService vetService;
  private final PetTypeService petTypeService;
  private final SpecialityService specialityService;
  private final VisitService visitService;

  public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
    this.specialityService = specialityService;
    this.visitService = visitService;
  }

  @Override
  public void run(String... args) {
    final int count = petTypeService.findAll().size();

    if (count == 0){
      loadData();
    }
  }

  private void loadData() {
    final PetType savedDogType = createPetType("Dog");
    final PetType savedCatType = createPetType("Cat");

    final Pet mikesPet = createPet(savedDogType, "Rosco", "2017-01-01");
    createOwner("Michael", "Weston", "123 Brickerel", "Miami", "1234567890", Arrays.asList(mikesPet));

    final Pet fionaPet = createPet(savedCatType, "Just a cat", "2017-01-01");
    createOwner("Fiona", "Glenanne", "123 Brickerel","Miami","1234567890", Arrays.asList(fionaPet));

    createVisit(fionaPet, "dental work", "2019-01-04");

    System.out.println("loaded owners....");

    final Speciality radiology = createSpeciality("radiology");
    final Speciality surgery = createSpeciality("surgery");
    final Speciality dentistry = createSpeciality("dentistry");

    createVet("Sam", "Axe", Arrays.asList(radiology, surgery));
    createVet("John", "Smith", Arrays.asList(dentistry));

    System.out.println("loaded vets....");
  }

  private void createVisit(final Pet fionaPet, String description, String visitDate) {
    final Visit visit = new Visit();
    visit.setPet(fionaPet);
    visit.setDescription(description);
    visit.setDate(LocalDate.parse(visitDate));
    visitService.save(visit);
  }

  private Speciality createSpeciality(String description) {
    final Speciality speciality = new Speciality();
    speciality.setDescription(description);
    return specialityService.save(speciality);
  }

  private Pet createPet(PetType type, String name, String birthDate) {
    final Pet pet = new Pet();
    pet.setPetType(type);
    pet.setName(name);
    pet.setBirthDate(LocalDate.parse(birthDate));
    return pet;
  }

  private PetType createPetType(String dog) {
    final PetType petType = new PetType();
    petType.setName(dog);
    return petTypeService.save(petType);
  }

  private Owner createOwner(String firstName, String lastName, String address, String city, String telephone, List<Pet> pets) {
    final Owner owner = new Owner();
    owner.setFirstName(firstName);
    owner.setLastName(lastName);
    owner.setAddress(address);
    owner.setCity(city);
    owner.setTelephone(telephone);

    pets.forEach(pet -> {
      pet.setOwner(owner);
      owner.getPets().add(pet);
    });

    return ownerService.save(owner);
  }

  private void createVet(String firstName, String lastName, List<Speciality> specialities) {
    final Vet vet = new Vet();
    vet.setFirstName(firstName);
    vet.setLastName(lastName);
    vet.getSpecialities().addAll(specialities);

    vetService.save(vet);
  }
}
