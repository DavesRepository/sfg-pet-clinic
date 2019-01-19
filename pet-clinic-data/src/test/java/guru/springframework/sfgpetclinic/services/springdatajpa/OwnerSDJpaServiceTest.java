package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

  @Mock
  private OwnerRepository ownerRepository;

  @Mock
  private PetRepository petRepository;

  @Mock
  private PetTypeRepository petTypeRepository;

  @InjectMocks
  private OwnerSDJpaService ownerService;

  private static final Long OWNER1_ID = 1L;
  private static final Long OWNER2_ID = 2L;
  private static final String OWNER1_LASTNAME = "lastName";
  private Owner returnOwner;

  @BeforeEach
  void setUp() {
    returnOwner = Owner.builder().id(OWNER1_ID).lastName(OWNER1_LASTNAME).build();
  }

  @Test
  void findByLastName() {
    when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

    Owner owner = ownerService.findByLastName(OWNER1_LASTNAME);

    Assertions.assertNotNull(owner);
    Assertions.assertEquals(OWNER1_ID, owner.getId());
    Assertions.assertEquals(OWNER1_LASTNAME, owner.getLastName());

    verify(ownerRepository, times(1)).findByLastName(OWNER1_LASTNAME);
  }

  @Test
  void findAll() {
    Owner owner1 = Owner.builder().id(OWNER1_ID).build();
    Owner owner2 = Owner.builder().id(OWNER2_ID).build();

    when(ownerRepository.findAll()).thenReturn(Sets.newSet(owner1, owner2));

    Set<Owner> ownersFound = ownerService.findAll();

    Assertions.assertNotNull(ownersFound);
    Assertions.assertEquals(2, ownersFound.size());

    verify(ownerRepository, times(1)).findAll();
  }

  @Test
  void findById() {
    when(ownerRepository.findById(OWNER1_ID)).thenReturn(Optional.of(returnOwner));

    Owner ownerFound = ownerService.findById(OWNER1_ID);

    Assertions.assertNotNull(ownerFound);
    Assertions.assertEquals(OWNER1_ID, ownerFound.getId());
    Assertions.assertEquals(OWNER1_LASTNAME, ownerFound.getLastName());

    verify(ownerRepository, times(1)).findById(OWNER1_ID);
  }

  @Test
  void findByIdNotFound() {
    when(ownerRepository.findById(OWNER1_ID)).thenReturn(Optional.empty());

    Owner ownerFound = ownerService.findById(OWNER1_ID);

    Assertions.assertNull(ownerFound);

    verify(ownerRepository, times(1)).findById(OWNER1_ID);
  }

  @Test
  void save() {
    when(ownerRepository.save(any())).thenReturn(returnOwner);

    Owner savedOwner = ownerService.save(returnOwner);

    Assertions.assertNotNull(savedOwner);
    verify(ownerRepository, times(1)).save(any());
  }

  @Test
  void delete() {
    ownerService.delete(returnOwner);

    verify(ownerRepository, times(1)).delete(returnOwner);
  }

  @Test
  void deleteById() {
    ownerService.deleteById(OWNER1_ID);

    verify(ownerRepository, times(1)).deleteById(OWNER1_ID);
  }
}
