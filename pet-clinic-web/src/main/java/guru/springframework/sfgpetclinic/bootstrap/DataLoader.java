package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.OwnerServiceMap;
import guru.springframework.sfgpetclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

  private final OwnerService ownerService;
  private final VetService vetService;

  public DataLoader() {
    ownerService = new OwnerServiceMap();
    vetService = new VetServiceMap();
  }

  @Override
  public void run(String... args) throws Exception {
    createOwner(1L, "Michael", "Weston");
    createOwner(2L, "Fiona", "Glenanne");

    System.out.println("loaded owners....");

    createVet(1L, "Sam", "Axe");
    createVet(2L, "John", "Smith");

    System.out.println("loaded vets....");
  }

  private void createOwner(Long id, String firstName, String lastName) {
    final Owner owner1 = new Owner();
    owner1.setId(id);
    owner1.setFirstName(firstName);
    owner1.setLastName(lastName);

    ownerService.save(owner1);
  }

  private void createVet(Long id, String firstName, String lastName) {
    final Vet vet = new Vet();
    vet.setId(id);
    vet.setFirstName(firstName);
    vet.setLastName(lastName);

    vetService.save(vet);
  }
}
