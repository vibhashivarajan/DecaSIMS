package org.isd.ihs.decasims.service;

import java.util.List;

import org.isd.ihs.decasims.entity.CatalogItem;
import org.isd.ihs.decasims.repository.CatalogItemRepository;
import org.springframework.stereotype.Service;

/**
 * The Class InventoryService: A inventory service class simply forward the requests received to
 * specific repository (in this case CatalogItemRepository). Not much going on here. @Service 
 * indicates spring managed bean.
 *
 */
@Service
public class InventoryService {

	/** The catalog item repository. */
	private CatalogItemRepository catalogItemRepository;

	/**
	 * Instantiates a new inventory service.
	 *
	 * @param catalogItemRepository the catalog item repository
	 */
	public InventoryService(CatalogItemRepository catalogItemRepository) {
		super();
		this.catalogItemRepository = catalogItemRepository;
	}

	/**
	 * Gets the all catalog items.
	 *
	 * @return the all catalog items
	 */
	public List<CatalogItem> getAllCatalogItems() {
		return catalogItemRepository.findAll();
	}

	/**
	 * Save catalog item.
	 *
	 * @param catalogItem the catalog item
	 * @return the catalog item
	 */
	public CatalogItem saveCatalogItem(CatalogItem catalogItem) {
		return catalogItemRepository.save(catalogItem);
	}

	/**
	 * Gets the catalog item by id.
	 *
	 * @param id the id
	 * @return the catalog item by id
	 */
	public CatalogItem getCatalogItemById(Long id) {
		return catalogItemRepository.findById(id).get();
	}

	/**
	 * Update catalog item.
	 *
	 * @param catalogItem the catalog item
	 * @return the catalog item
	 */
	public CatalogItem updateCatalogItem(CatalogItem catalogItem) {
		return catalogItemRepository.save(catalogItem);
	}

	/**
	 * Delete catalog item.
	 *
	 * @param id the id
	 */
	public void deleteCatalogItem(Long id) {
		catalogItemRepository.deleteById(id);	
	}
}



