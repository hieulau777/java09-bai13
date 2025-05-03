package baitap13.repo.impl;

import java.util.ArrayList;
import java.util.List;

import baitap13.model.DirectorModel;
import baitap13.model.ManagerModel;
import baitap13.model.PersonModel;
import baitap13.model.StaffModel;
import baitap13.repo.PersonRepo;

public class PersonRepoImpl implements PersonRepo<PersonModel> {
	
	private List<PersonModel> listPerson = new ArrayList<PersonModel>();
	
	public void add(PersonModel person) {
		listPerson.add(person);
	}
	
	public void add(List<PersonModel> list) {
		listPerson.addAll(list);
	}
	
	public void remove(int personId) {
		
		listPerson.removeIf(person -> person.getId() == personId);
	}
	
	public List<PersonModel> getAll() {
		return listPerson;
	}
	
	public List<StaffModel> getAllStaff() {
		return filterByClass(StaffModel.class);
	}
	
	public List<ManagerModel> getAllManager() {
		return filterByClass(ManagerModel.class);
	}
	
	public List<DirectorModel> getAllDirector() {
		return filterByClass(DirectorModel.class);
	}
	
	public PersonModel findById(int personId) {
		for (PersonModel person : listPerson) {
			if(person.getId() == personId) return person;
		}
		return null;
	}
	
    private <T extends PersonModel> List<T> filterByClass(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (PersonModel person : listPerson) {
            if (clazz.isInstance(person)) {
                result.add(clazz.cast(person));
            }
        }
        return result;
    }
}
