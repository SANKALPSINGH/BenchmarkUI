package com.hike.appBenchmark.diffStats;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hike.appBenchmark.models.RunData;

public interface DiffDao {

    /**
     * get data of all percentile for passed apk version
     * @param apkVersion apk version
     * @return list of run data objects
     */
    List<RunData> getDataForAllPercentileForApk(@Param("runId") String runId);

}
