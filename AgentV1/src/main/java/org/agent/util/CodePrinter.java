package org.agent.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class CodePrinter {

    /**
     * 바이트코드 조작 후 Class 파일 출력 로직
     */
    public static void printClass(byte[] bytecodes, String testCode, boolean deleteLastFile) {
        try {
            DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String fileName = LocalDateTime.now().format(formattedDate) + "[" + testCode + "]" + ".class";
            String directoryPath = System.getProperty("user.dir") + File.separator + "byteLog"; // 디렉토리 경로
            String filePath = directoryPath + File.separator + fileName; // 최종 파일 경로

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // 디렉토리가 없으면 생성
            } else if(deleteLastFile) { // deleteLastFile이 true일 경우, 디렉토리 내 모든 파일 삭제
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        file.delete(); // 파일 삭제
                    }
                }
            }



            FileOutputStream out = new FileOutputStream(filePath); // 파일 출력 스트림 생성

            out.write(bytecodes);
            out.close();
            log.warn("[CODE_PRINTER_DONE] byteLog print done. : " + testCode);
        } catch (IOException e) {
            log.warn("[CODE_PRINTER ERR] : {}", e.getMessage());
        }
    }
}
