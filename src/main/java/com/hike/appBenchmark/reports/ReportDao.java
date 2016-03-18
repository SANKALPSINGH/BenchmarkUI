package com.hike.appBenchmark.reports;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hike.appBenchmark.models.Actions;
import com.hike.appBenchmark.models.Run;
import com.hike.appBenchmark.models.RunData;

/**
 * Dao for report controller
 * @author kumarpratyush
 *
 */
public interface ReportDao {

    /**
     * Get the list of all actions that are performed for benchmark purpose
     * @return list of all Action objects
     */
    List<Actions> getAllActions();

    /**
     * get all available Apks on which benchmarking has been performed
     * @return
     */
    List<Run> getAllApksAvailable();

    /**
     * Gets the run id for the given apk passed as parameter
     * @param apk version of apk for which run Id needs to be fetched
     * @return list of run objects with only one entry
     */
    List<Run> getRunIdFromApk(@Param("apk") String apk);

    /**
     * gets total and average readings for all percentile for a given apk and a particular action performed
     * @param apk version of apk
     * @param action action pertaining to which data is retrieved
     * @return list of run data objects
     */
    List<RunData> getDataForApkAndAction(@Param("runId") String runId, @Param("action") String action);

}
