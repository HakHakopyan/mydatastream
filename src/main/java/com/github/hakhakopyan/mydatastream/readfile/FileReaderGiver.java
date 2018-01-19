package com.github.hakhakopyan.mydatastream.readfile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileReaderGiver implements FileReaderGivable {
    List<FileReadable> myFileReaders;
    //int threadCount;
    boolean getPossible = false;

    public FileReaderGiver(String... filePaths) {
        /*
        for (int i = 0; i < filePaths.length; i++) {
            ThreadReader newThreadReader = new ThreadReader(filePaths[i], this);
            new Thread(newThreadReader, "ThreadReader_" + i).start(); ;
        }
        */
        myFileReaders = Arrays.stream(filePaths)
                .map(x->FileReaderFarm.getFileReader(x))
                .collect(Collectors.toList());

    }
    /*
    public synchronized void addFileReader(FileReadable fileReader) {
        myFileReaders.add(fileReader);
        if (!getPossible) {
            getPossible = true;
            notifyAll();
        }
    }

    public boolean GetPossible() {
        return getPossible;
    }
    */

    Integer index = -1;
    private  int addIndex() {
        if (index >= (myFileReaders.size() - 1))
            index = -1;
        return  ++index;
    }

    public FileReadable getFileReader() {
        if (myFileReaders.size() == 0)
            return new EmptyReader();
        System.out.println(Thread.currentThread().getName());
        synchronized (index) {
            FileReadable retFileReader = myFileReaders.get(addIndex());

            while (retFileReader.isEmpty()) {
                myFileReaders.remove(retFileReader);

                if (myFileReaders.size() == 0)
                    return this.getFileReader();

                if (index >= myFileReaders.size())
                    addIndex();

                retFileReader = myFileReaders.get(index);
            }
            return retFileReader;
        }
        /*
        while (!getPossible)
            try {
                wait();
            } catch (InterruptedException | IllegalMonitorStateException ex) {
                System.out.println(ex.getMessage());
            }
            */
    }
}
