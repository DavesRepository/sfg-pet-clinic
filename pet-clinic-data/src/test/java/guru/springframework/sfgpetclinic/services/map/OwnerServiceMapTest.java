package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class OwnerServiceMapTest {

  private OwnerServiceMap ownerServiceMap;

  private static final Long OWNER1_ID = 1L;
  private static final String OWNER1_LASTNAME = "lastName";

  private static final Long OWNER2_ID = 2L;

  @BeforeEach
  void setUp() {
    ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

    ownerServiceMap.save(Owner.builder().id(OWNER1_ID).lastName(OWNER1_LASTNAME).build());
  }

  @Test
  void findAll() {
    final Set<Owner> ownerSet = ownerServiceMap.findAll();

    assertEquals(1, ownerSet.size());
  }

  @Test
  void findById() {

    final Owner owner = ownerServiceMap.findById(OWNER1_ID);

    assertEquals(OWNER1_ID, owner.getId());
  }

  @Test
  void delete() {
    ownerServiceMap.delete(ownerServiceMap.findById(OWNER1_ID));

    assertEquals(0, ownerServiceMap.findAll().size());
  }

  @Test
  void saveExistingId() {
    final Owner owner2 = Owner.builder().id(OWNER2_ID).build();

    Owner savedOwner = ownerServiceMap.save(owner2);

    assertEquals(OWNER2_ID, savedOwner.getId());
  }

  @Test
  void saveNoId() {
    final Owner ownerNoId = Owner.builder().build();

    Owner savedOwner = ownerServiceMap.save(ownerNoId);

    assertNotNull(savedOwner);
    assertNotNull(savedOwner.getId());
  }

  @Test
  void deleteById() {
    ownerServiceMap.deleteById(OWNER1_ID);
    assertEquals(0, ownerServiceMap.findAll().size());
  }

  @Test
  void findByLastName() {
    final Owner owner1 = ownerServiceMap.findByLastName(OWNER1_LASTNAME);

    assertNotNull(owner1);
    assertEquals(OWNER1_ID, owner1.getId());
    assertEquals(OWNER1_LASTNAME, owner1.getLastName());
  }

  @Test
  void findByLastNameNotFound() {
    final Owner owner1 = ownerServiceMap.findByLastName("foo");

    assertNull(owner1);
  }
}
