package com.bankingsystem.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bankingsystem.entities.Customer;

/**
 * No debe contener reglas de negocio (02_architecture.md, capa Repository).
 * Todas las consultas excluyen explícitamente los registros con soft delete,
 * ya que no se asume que Customer tenga un filtro global (@SQLRestriction)
 * a nivel de entidad. Si SoftDeletableEntity ya lo aplica automáticamente,
 * estos "AndDeletedFalse" son redundantes pero inofensivos.
 */
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByIdAndDeletedFalse(UUID id);

    boolean existsByEmailAndDeletedFalse(String email);

    boolean existsByDocumentNumberAndDeletedFalse(String documentNumber);

    boolean existsByEmailAndDeletedFalseAndIdNot(String email, UUID id);

    boolean existsByDocumentNumberAndDeletedFalseAndIdNot(String documentNumber, UUID id);

    /**
     * Búsqueda simple (?search=) sobre nombre, apellido, documento y email,
     * según la convención de búsquedas de 04_api_design.md / 11_endpoints.md.
     */
    @Query("""
            SELECT c FROM Customer c
            WHERE c.deleted = false
              AND (:search IS NULL OR :search = ''
                   OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(c.documentNumber) LIKE LOWER(CONCAT('%', :search, '%'))
                   OR LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Customer> search(@Param("search") String search, Pageable pageable);

    Optional<Customer> findByUser_Id(UUID userId);

    Optional<Customer> findByUserId(UUID userId);
}