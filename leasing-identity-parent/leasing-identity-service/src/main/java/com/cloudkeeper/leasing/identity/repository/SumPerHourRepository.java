package com.cloudkeeper.leasing.identity.repository;

import com.cloudkeeper.leasing.identity.domain.SumPerHour;
import com.cloudkeeper.leasing.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 每小时人流量 repository
 * @author asher
 */
@Repository
public interface SumPerHourRepository extends BaseRepository<SumPerHour> {

    @Query(value = "SELECT sum(amount) as total, pi.id as positionId,  DATEADD(HH, -1, GETDATE()) AS startTime, GETDATE() as endTime, ps.locationCode  FROM dbo.People_Stream ps" +
            "  LEFT JOIN Position_Information pi on pi.hardwareId = ps.locationCode " +
            "  WHERE ps.createdAt >= DATEADD(HH, -1, GETDATE()) " +
            "  GROUP BY ps.locationCode,pi.id",nativeQuery = true)
    List<Object[]> calPerHour();

}