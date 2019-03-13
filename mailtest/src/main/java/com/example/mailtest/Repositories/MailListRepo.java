package com.example.mailtest.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.mailtest.entity.MailList;

public interface MailListRepo extends CrudRepository<MailList,String> {

	@Query("Select obj from MailList obj where obj.flag=false")
	public List<MailList> findFalse();


}
