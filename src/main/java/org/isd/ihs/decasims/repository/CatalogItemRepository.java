package org.isd.ihs.decasims.repository;

import org.isd.ihs.decasims.entity.CatalogItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface CatalogItemRepository: java interface which represents an access to
 * database table of CatalogItem (springboot jpa feature)
 * 
 */
public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long>{

}



