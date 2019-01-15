package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import guru.springframework.sfgpetclinic.services.VetService;
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
  private final SpecialtyService specialtyService;

  public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
    this.specialtyService = specialtyService;
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

    System.out.println("loaded owners....");

    final Speciality radiology = createSpecialty("radiology");
    final Speciality surgery = createSpecialty("surgery");
    final Speciality dentistry = createSpecialty("dentistry");

    createVet("Sam", "Axe", Arrays.asList(radiology, surgery));
    createVet("John", "Smith", Arrays.asList(dentistry));

    System.out.println("loaded vets....");
  }

  private Speciality createSpecialty(String description) {
    final Speciality speciality = new Speciality();
    speciality.setDescription(description);
    return specialtyService.save(speciality);
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
