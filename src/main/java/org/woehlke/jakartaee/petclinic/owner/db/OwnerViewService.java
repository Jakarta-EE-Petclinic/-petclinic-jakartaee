package org.woehlke.jakartaee.petclinic.owner.db;

import org.woehlke.jakartaee.petclinic.owner.Owner;
import org.woehlke.jakartaee.petclinic.pet.Pet;
import org.woehlke.jakartaee.petclinic.visit.Visit;

import java.io.Serializable;
import java.util.List;

public interface OwnerViewService extends Serializable {

    List<Pet> getPetsAsList(Owner owner);

    String getPetsAsString(Owner owner);

    List<Visit> getVisits(Pet ownersPet);

    Visit addNewVisit(Visit visit);

    Pet addNewPet(Pet pet);

    Pet findPetById(long id);

    Pet updatePet(Pet pet);

    Owner findOwnerById(long id);

    Owner updateOwner(Owner entity);

    Owner addNewOwner(Owner entity);

    void deleteOwnerPrepare(long ownerId);

    void deleteOwnerPerform(long ownerId);

    List<Owner> getAllOwner();

    List<Owner> searchOwner(String searchterm);
}
