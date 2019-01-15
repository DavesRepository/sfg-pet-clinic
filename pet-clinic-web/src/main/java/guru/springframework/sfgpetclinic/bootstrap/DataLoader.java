package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final OwnerService ownerService;
  private final VetService vetService;
  private final PetTypeService petTypeService;

  public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
  }

  @Override
  public void run(String... args) {
    final PetType dog = createPetType("Dog");
    final PetType cat = createPetType("Cat");

    createOwner("Michael", "Weston");
    createOwner("Fiona", "Glenanne");

    System.out.println("loaded owners....");

    createVet("Sam", "Axe");
    createVet("John", "Smith");

    System.out.println("loaded vets....");
  }

  private PetType createPetType(String dog) {
    final PetType petType = new PetType();
    petType.setName(dog);
    return petTypeService.save(petType);
  }

  private void createOwner(String firstName, String lastName) {
    final Owner owner1 = new Owner();
    owner1.setFirstName(firstName);
    owner1.setLastName(lastName);

    ownerService.save(owner1);
  }

  private void createVet(String firstName, String lastName) {
    final Vet vet = new Vet();
    vet.setFirstName(firstName);
    vet.setLastName(lastName);

    vetService.save(vet);
  }
}
