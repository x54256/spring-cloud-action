package cn.x5456.orgsvr.services;


import cn.x5456.orgsvr.model.Organization;
import cn.x5456.orgsvr.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository orgRepository;

    public Organization getOrg(String organizationId) {
        return orgRepository.findById(organizationId).get();
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());

        orgRepository.save(org);

    }

    public void updateOrg(Organization org) {
        orgRepository.save(org);
    }

    public void deleteOrg(Organization org) {
        orgRepository.deleteById(org.getId());
    }
}
