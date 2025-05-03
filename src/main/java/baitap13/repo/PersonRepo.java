package baitap13.repo;

import java.util.List;

import baitap13.model.DirectorModel;
import baitap13.model.ManagerModel;
import baitap13.model.PersonModel;
import baitap13.model.StaffModel;

@SuppressWarnings("hiding")
public interface PersonRepo<PersonModel> {
    void add(PersonModel person);
    void add(List<PersonModel> list);
    void remove(int personId);
    List<PersonModel> getAll();
    PersonModel findById(int personId);
    List<StaffModel> getAllStaff();
    List<ManagerModel> getAllManager();
    List<DirectorModel> getAllDirector();
}
