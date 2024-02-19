package com.pedro.crud;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudRepository extends JpaRepository<CrudModel, Long> {

}

