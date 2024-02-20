package org.agent.init;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class ConfigRead {

    String datasourceJndiName;

    public ConfigRead() {
        loadProperties();
    }

    public void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("agent-config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("설정 파일을 찾을 수 없음");
            }
            Properties prop = new Properties();
            prop.load(input);

            // 프로퍼티 값 읽기
            datasourceJndiName = prop.getProperty("datasource.jndi.name");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}