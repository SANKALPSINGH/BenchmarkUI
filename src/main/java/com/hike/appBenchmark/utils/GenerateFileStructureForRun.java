package com.hike.appBenchmark.utils;

import java.io.File;

import org.springframework.context.MessageSource;

public class GenerateFileStructureForRun {

    private MessageSource messageSource;

    public GenerateFileStructureForRun(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void createRunFolder(String runID) {

        String userName = System.getProperty(messageSource.getMessage("user.name.command", null, null));
        String path = messageSource.getMessage("home.path", null, null) + userName + messageSource.getMessage("apk.path", null, null) + "/";
        String folderName = runID;
        boolean folderStatus = createFolder(path, folderName);
        if (folderStatus) {
            System.out.println(runID+":Run Directory is created");
        } else{
            System.out.println(runID+":Directory is not created");
        }
    }

    public boolean createFolder(String path, String folderName) {
        boolean folderStatus = false;
        if (!new File(path + folderName).exists()) {
            File file = new File(path + folderName);
            folderStatus = file.mkdirs();
        }
        return folderStatus;
    }


}
