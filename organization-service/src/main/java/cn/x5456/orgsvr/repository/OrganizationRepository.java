package cn.x5456.orgsvr.repository;

import cn.x5456.orgsvr.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String> {
}
