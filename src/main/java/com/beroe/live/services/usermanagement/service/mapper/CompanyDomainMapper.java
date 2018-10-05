package com.beroe.live.services.usermanagement.service.mapper;

import com.beroe.live.services.usermanagement.domain.*;
import com.beroe.live.services.usermanagement.service.dto.CompanyDomainDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompanyDomain and its DTO CompanyDomainDTO.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface CompanyDomainMapper extends EntityMapper<CompanyDomainDTO, CompanyDomain> {

    @Mapping(source = "company.id", target = "companyId")
    CompanyDomainDTO toDto(CompanyDomain companyDomain);

    @Mapping(source = "companyId", target = "company")
    CompanyDomain toEntity(CompanyDomainDTO companyDomainDTO);

    default CompanyDomain fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyDomain companyDomain = new CompanyDomain();
        companyDomain.setId(id);
        return companyDomain;
    }
}
