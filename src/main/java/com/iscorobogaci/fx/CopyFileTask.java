package com.iscorobogaci.fx;

import com.iscorobogaci.FileSorter;
import javafx.concurrent.Task;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CopyFileTask extends Task<List<Path>> {

    private List<Path> files;
    private FileSorter fileSorter;

    public CopyFileTask(List<Path> files, FileSorter fileSorter) {
        this.files = files;
        this.fileSorter = fileSorter;
    }

    @Override
    protected List<Path> call() throws Exception {
        List<Path> copied = new ArrayList<>();
        int progress = 0;
        for (Path file : files) {
            copied.add(file);
            fileSorter.performCopy(file);
            progress++;
            this.updateProgress(progress, files.size());
        }
        return copied;
    }

}
