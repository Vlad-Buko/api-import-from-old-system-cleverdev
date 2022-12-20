package com.cleverdev.clientService.model;

import com.cleverdev.clientService.service.enums.PatientStatusEnum;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Created by Vladislav Domaniewski
 */

@Component
@Data
public class PatientModel {
    private String firstName;
    private String lastName;
    private String oldClientGuid;
    @Enumerated(EnumType.ORDINAL)
    private PatientStatusEnum statusId;
}
