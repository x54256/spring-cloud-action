package cn.x5456.lincenses.services;

import cn.x5456.lincenses.clients.OrganizationDiscoveryClient;
import cn.x5456.lincenses.clients.OrganizationFeignClient;
import cn.x5456.lincenses.clients.OrganizationRestTemplateClient;
import cn.x5456.lincenses.config.ServiceConfig;
import cn.x5456.lincenses.model.License;
import cn.x5456.lincenses.model.Organization;
import cn.x5456.lincenses.repository.LicenseRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    ServiceConfig config;


    @Autowired
    OrganizationFeignClient organizationFeignClient;

    @Autowired
    OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    OrganizationDiscoveryClient organizationDiscoveryClient;


    private Organization retrieveOrgInfo(String organizationId, String clientType) {
        Organization organization = null;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization org = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

//    // 断路器：Hystrix会使用断路器包装这个调用，当调用超过1s（默认）时中断这个调用
//    @HystrixCommand
//    public List<License> getLicensesByOrg(String organizationId) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return licenseRepository.findByOrganizationId(organizationId);
//    }

//    // 后备处理
//    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList")
//    public List<License> getLicensesByOrg(String organizationId) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ignored) {
//        }
//
//        return licenseRepository.findByOrganizationId(organizationId);
//    }
//
//    private List<License> buildFallbackLicenseList(String organizationId) {
//        List<License> fallbackList = new ArrayList<>();
//        License license = new License()
//                .withId("0000000-00-00000")
//                .withOrganizationId(organizationId)
//                .withProductName("Sorry no licensing information currently available");
//
//        fallbackList.add(license);
//        return fallbackList;
//    }

//    // 舱壁模式
//    @HystrixCommand(threadPoolKey = "licenseByOrgThreadPool",   // 线程池名
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize", value = "30"),  // 线程池中的最大数量
//                    @HystrixProperty(name = "maxQueueSize", value = "10")}) // 阻塞队列大小，如果超出指定大小，所有的调用都将失败，如果这个设置为-1，java将使用SynchronousQueue处理请求，大于1将使用LinkedBlockingQueue；
//    public List<License> getLicensesByOrg(String organizationId) {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ignored) {
//        }
//
//        return licenseRepository.findByOrganizationId(organizationId);
//    }

    // 配置断路器
    @HystrixCommand(//fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")},
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 出现问题之后，指定时间连续发生调用的次数
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"), // 出现问题的百分比，超过这个百分比才会熔断
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),    // 下一次允许测试的空闲时间
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),    // 监视服务调用的时间
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")}    // 不懂
    )
    public List<License> getLicensesByOrg(String organizationId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());

        licenseRepository.save(license);

    }

    public void updateLicense(License license) {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {
        licenseRepository.deleteById(license.getLicenseId());
    }

}
