package org.sch.util;

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
    public static void printClass(byte[] bytecodes, String rawName, boolean deleteLastFile) {
        try {
            DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("MM-dd__HH-mm-ss"); //시간 타입
            String name = convert(rawName); //className
            String fileName = LocalDateTime.now().format(formattedDate) + "__" + name + ".class";
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
            log.warn("[CODE_PRINTER_DONE] byteLog print done. : " + name);
        } catch (IOException e) {
            log.warn("[CODE_PRINTER ERR] : {}", e.getMessage());
        }
    }
    
    //폴더 초기화
    public static void deleteFiles(boolean use) {
        if(!use) return;
        String directoryPath = System.getProperty("user.dir") + File.separator + "byteLog"; // 디렉토리 경로
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 없으면 생성
        } 
        // deleteLastFile이 true일 경우, 디렉토리 내 모든 파일 삭제
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete(); // 파일 삭제
            }
        }
    }

    //Class명만 추출 : ex : full/path/package/className ..
    public static String convert(String fullName) {
        int lastIndex = fullName.lastIndexOf('/'); // 마지막 '/'의 인덱스
        return fullName.substring(lastIndex + 1); // 마지막 '/' 다음 위치부터 문자열의 끝까지가 클래스 이름
    }
}
