package cn.x5456.orgsvr.controllers;

import cn.x5456.orgsvr.model.Organization;
import cn.x5456.orgsvr.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="v1/organizations")
public class OrganizationServiceController {
    @Autowired
    private OrganizationService orgService;


    @RequestMapping(value="/{organizationId}",method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        return orgService.getOrg(organizationId);
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.PUT)
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        orgService.updateOrg( org );
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.POST)
    public void saveOrganization(@RequestBody Organization org) {
       orgService.saveOrg( org );
    }

    @RequestMapping(value="/{organizationId}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("orgId") String orgId, @RequestBody Organization org) {
        orgService.deleteOrg( org );
    }
}
