package com.cloudkeeper.leasing.identity.dto.villagecadres;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExportDTO {
    List<VillageCadresStatisticsSearchable> searchFields;
    List<VillageCadresStatisticsSearchable> exportFields;
}
