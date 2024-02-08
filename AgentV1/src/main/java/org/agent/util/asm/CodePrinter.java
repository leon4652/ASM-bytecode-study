package org.agent.util.asm;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class CodePrinter {

    /**
     *   바이트코드 조작 후 Class 파일 출력 로직
     */
    public static void printClass(byte[] bytecodes) throws IOException {
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String fileName = "printer " + LocalDateTime.now().format(formattedDate) + ".class";
        String filePath = System.getProperty("user.dir") + File.separator + "byteLog" + File.separator + fileName;    //path
        FileOutputStream out = new FileOutputStream(filePath);      //stream

        out.write(bytecodes);
        out.close();
//        log.info("done print");
    }

}
