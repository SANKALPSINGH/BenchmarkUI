package com.hike.appBenchmark.benchmarkProcess;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hike.appBenchmark.models.Actions;
import com.hike.appBenchmark.models.Percentile;
import com.hike.appBenchmark.models.Run;
import com.hike.appBenchmark.models.RunData;
import com.hike.appBenchmark.models.RunPercentile;


/**
 * dao class for benchmark process
 * @author kumarpratyush
 *
 */
public interface BenchmarkDao {

    /**
     * insert into run object
     * @param run object of run model
     * @return run id of the run generated
     */
    int insertRun(@Param("run") Run run);

    /**
     * updates teh apk version generated after jenkins build is completed
     * @param run run object with apt run id
     */
    void updateBuildVersionForRun(@Param("run") Run run);

    /**
     * get list of all percentile objects
     * @return list of percentile objects
     */
    List<Percentile>getAllPercentiles();

    /**
     * insert into run_percentile table for each percentile run given
     * @param runPercentile run percentile object containing run id and percentile
     * @return run percentile id
     */
    int insertIntoRunPercentile(@Param("runPercentile") RunPercentile runPercentile);

    /**
     * get all action objects
     * @return
     */
    List<Actions> getAllActions();

    int insertIntoRunData(@Param("runData") RunData runData);

    List<RunData> getAllWantedValues();

    void updateData(@Param("newTotal") Double newTotal, @Param("newAverage") Double newAverage, @Param("id") int id, @Param("action") String action);

}
