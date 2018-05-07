package com.workia;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 23/11/17.
 */
@Component
public class TikaParser implements CommandLineRunner {

    final static Logger logger = LoggerFactory.getLogger(TikaParser.class);


    @Override
    public void run(String... args) throws Exception {
        logger.info("Iniciando TIKA - PARSER...");
        String root = args.length == 0 ?  "./cvs" : args[0];
        List<File> files = getFiles(root);
        for (File filename : files){
            logger.info("Parsing: " + filename.getName());
            InputStream fis = new FileInputStream(filename);
            String txtFileName = filename.getName().substring(0, (filename.getName().lastIndexOf('.') >= 0)?filename.getName().lastIndexOf('.') : filename.getName().length()-1) + ".txt";
            txtFileName = "./results/" + txtFileName;
            OutputStream fos = new FileOutputStream(txtFileName);
            Tika tika = new Tika();
            try {
                logger.info("Tipo de archivo detectado: " + tika.detect(fis));
            }catch (Exception e){
                logger.error("No pudo detectar archivo");
            }
            try {
                fos.write(tika.parseToString(filename).getBytes());
            }catch(Exception e ){
                logger.error("Failed!");
            }
            fis.close();
            fos.flush();
            fos.close();
            logger.info("Parsed: " + filename);
        }
        logger.info("Finished parsing");
    }
    private List<File> getFiles(String origin){
        List<File> files = new ArrayList<>();
        List<String> directories = new ArrayList<>();
        File currentDir = new File(origin);
        try{
            for (File file : currentDir.listFiles()) {
                if (file.isFile()){
                    files.add(file);
                }else if (file.isDirectory()){
                    directories.add(file.getCanonicalPath());
                }
            }
            for (String dir : directories){
                files.addAll(getFiles(dir));
            }
        }catch(Exception e){
            System.out.println("The following error occurred: " + e.getMessage());
        }

        return files;
    }
}
