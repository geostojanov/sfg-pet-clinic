package com.example.sfgpetclinic.services.springdatajpa;

import com.example.sfgpetclinic.model.Owner;
import com.example.sfgpetclinic.repositories.OwnerRepository;
import com.example.sfgpetclinic.repositories.PetRepository;
import com.example.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final String LAST_NAME = "Smith";
    private static final Long OWNER_ID = 1L;

    @InjectMocks
    private OwnerSDJpaService service;

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private PetTypeRepository petTypeRepository;

    private Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build();
    }

    @Test
    void testFindByLastName() {
        // given
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        // when
        Owner smith = service.findByLastName(LAST_NAME);

        // then
        assertEquals(LAST_NAME, smith.getLastName());
        verify(ownerRepository, times(1)).findByLastName(any());
    }

    @Test
    void testFindAll() {
        // given
        Set<Owner> returnSet = new HashSet<>();
        returnSet.add(Owner.builder().id(1L).build());
        returnSet.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnSet);

        // when
        Set<Owner> result = service.findAll();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        // given
        Optional<Owner> optionalOwner = Optional.of(returnOwner);
        when(ownerRepository.findById(any())).thenReturn(optionalOwner);

        //when
        Owner owner = service.findById(OWNER_ID);

        //then
        assertNotNull(owner);
    }

    @Test
    void testFindByIdNull() {
        // given
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());

        //when
        Owner owner = service.findById(OWNER_ID);

        //then
        assertNull(owner);
    }

    @Test
    void save() {
        // given
        Owner ownerToSave = Owner.builder().build();
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        // when
        Owner savedOwner = service.save(ownerToSave);

        //then
        assertNotNull(savedOwner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        //given
        //when
        service.delete(returnOwner);

        //then
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        //given
        //when
        service.deleteById(1L);

        //then
        verify(ownerRepository).deleteById(anyLong());
    }
}