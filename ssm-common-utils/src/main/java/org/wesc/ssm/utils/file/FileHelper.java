package org.wesc.ssm.utils.file;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 文件的读写操作，支持并发
 *
 * @Auther: Wesley Cheung
 * @Date: 2018/8/15 17:20
 */
public class FileHelper {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    /**
     * Read File.
     *
     * @param file
     * @return
     */
    public String read(File file) {
        StringBuffer results = new StringBuffer();
        try {
            readLock.lock();
            launchPeriod();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (null != line) {
                results.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return new String(results);
    }


    /**
     * Write File.
     * @param file
     */
    public void write(File file) {
        try {
            writeLock.lock();
            launchPeriod();

            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write("Hello Kuka:");
            out.newLine();
            out.write("My name is coolszy!\n");
            out.write("I like you and miss you。");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 保证每个线程运行时间在5秒以上
      */
    private void launchPeriod() {
        long currentTime = System.currentTimeMillis();
        for (;;) {
            if (System.currentTimeMillis() - currentTime > 5000) {
                break;
            }
        }
    }
}
