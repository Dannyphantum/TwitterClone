package miniTwitter.demo.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import miniTwitter.demo.models.Photo;


public interface PhotoRepository extends CrudRepository<Photo, Long>{

	Photo findByPhotoId(String name);
	
	List<Photo> findByUser_Id(Long id);

}
