package sg.edu.ntu.m3project.m3project.repository;

import org.springframework.stereotype.Repository;

import sg.edu.ntu.m3project.m3project.entity.UserEntity;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    
}