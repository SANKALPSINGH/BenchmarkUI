package com.hike.appBenchmark.jenkins;

import org.springframework.stereotype.Service;

/**
 *
 * @author kumarpratyush
 * interface for jenkins trigger jobs functionalities
 *
 */

@Service("jenkinsService")
public interface JenkinsService {

    /**
     *
     * @param jobName Name of the job in jenkins
     * @param branchName  Branch from which to take a pull
     * @return flag depending upon success or failure
     */
    boolean triggerJob(String jobName, String branchName);

    /**
     *
     * @param run_id apk build job to trigger for particular run ID
     * @param template_name template for which src.jar is to be built
     * @return flag depending upon success or failure
     */
    boolean triggerJob(int runId, int runInfoID, String templateName, String apkVersion, String androidGitBranch, String emailBody, String recipientList);

    /**
     *
     * @param branchName branch on which to build apk
     * @param runID run for which apk needs to be built
     * @return flag depending upon success or failure
     */
    boolean getLatestapk(String branchName,int runID);

    /**
     *
     * @param runID runID run for which apk needs to be moved
     */
    void moveApk(int runID);

}
