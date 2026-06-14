package com.drone.management.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * 数据库初始化器
 *
 * 启动时根据数据库类型自动创建表结构:
 *   SQLite  → AUTOINCREMENT
 *   MySQL   → AUTO_INCREMENT
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            String driverName = conn.getMetaData().getDriverName();
            String url = conn.getMetaData().getURL();
            boolean isSQLite = driverName.contains("SQLite") ||
                    (url != null && url.contains("sqlite"));

            log.info("检测到数据库类型: {} (JDBC URL: {})", driverName, url);

            String createTableSql;
            if (isSQLite) {
                createTableSql = buildSQLiteDDL();
            } else {
                createTableSql = buildMySQLDDL();
            }

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSql);
                log.info("数据库表 drone 初始化完成");
            }
        }
    }

    private String buildSQLiteDDL() {
        return "CREATE TABLE IF NOT EXISTS drone (" +
                "  id              INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  drone_name      VARCHAR(100)  NOT NULL," +
                "  drone_model     VARCHAR(50)   NOT NULL," +
                "  manufacturer    VARCHAR(100)  NOT NULL," +
                "  serial_number   VARCHAR(50)   NOT NULL," +
                "  drone_type      VARCHAR(20)   NOT NULL," +
                "  weight          REAL          NOT NULL DEFAULT 0," +
                "  max_altitude    REAL          NOT NULL DEFAULT 0," +
                "  max_speed       REAL          NOT NULL DEFAULT 0," +
                "  endurance       INTEGER       NOT NULL DEFAULT 0," +
                "  range_          REAL          DEFAULT 0," +
                "  camera_type     VARCHAR(50)   DEFAULT ''," +
                "  payload_capacity REAL         DEFAULT 0," +
                "  battery_capacity INTEGER      DEFAULT 0," +
                "  status          VARCHAR(10)   NOT NULL DEFAULT '正常'," +
                "  purchase_date   DATE          DEFAULT NULL," +
                "  description     VARCHAR(500)  DEFAULT ''," +
                "  create_time     DATETIME      DEFAULT CURRENT_TIMESTAMP," +
                "  update_time     DATETIME      DEFAULT CURRENT_TIMESTAMP" +
                ")";
    }

    private String buildMySQLDDL() {
        return "CREATE TABLE IF NOT EXISTS drone (" +
                "  id              BIGINT        NOT NULL AUTO_INCREMENT," +
                "  drone_name      VARCHAR(100)  NOT NULL," +
                "  drone_model     VARCHAR(50)   NOT NULL," +
                "  manufacturer    VARCHAR(100)  NOT NULL," +
                "  serial_number   VARCHAR(50)   NOT NULL," +
                "  drone_type      VARCHAR(20)   NOT NULL," +
                "  weight          DOUBLE        NOT NULL DEFAULT 0," +
                "  max_altitude    DOUBLE        NOT NULL DEFAULT 0," +
                "  max_speed       DOUBLE        NOT NULL DEFAULT 0," +
                "  endurance       INT           NOT NULL DEFAULT 0," +
                "  range_          DOUBLE        DEFAULT 0," +
                "  camera_type     VARCHAR(50)   DEFAULT ''," +
                "  payload_capacity DOUBLE       DEFAULT 0," +
                "  battery_capacity INT          DEFAULT 0," +
                "  status          VARCHAR(10)   NOT NULL DEFAULT '正常'," +
                "  purchase_date   DATE          DEFAULT NULL," +
                "  description     VARCHAR(500)  DEFAULT ''," +
                "  create_time     DATETIME      DEFAULT CURRENT_TIMESTAMP," +
                "  update_time     DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "  PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
    }
}
