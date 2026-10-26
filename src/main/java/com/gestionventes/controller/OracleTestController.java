package com.gestionventes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oracle-test")
public class OracleTestController {

    private final JdbcTemplate jdbcTemplate;

    public OracleTestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/connection")
    public Map<String, Object> testConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String result = jdbcTemplate.queryForObject(
                "SELECT 'Connexion Oracle OK' as message FROM DUAL", 
                String.class);
            
            response.put("status", "success");
            response.put("message", result);
            response.put("database", "Oracle XE");
            response.put("timestamp", java.time.LocalDateTime.now().toString());
            response.put("server", "localhost:1521/XE");
            response.put("user", "admin_app");
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            response.put("timestamp", java.time.LocalDateTime.now().toString());
        }
        
        return response;
    }
    
    @GetMapping("/tables")
    public Map<String, Object> listTables() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Map<String, Object>> tables = jdbcTemplate.queryForList(
                "SELECT table_name, num_rows FROM user_tables ORDER BY table_name");
            
            response.put("status", "success");
            response.put("tables", tables);
            response.put("count", tables.size());
            response.put("user", "admin_app");
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("/client-count")
    public Map<String, Object> countClients() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CLIENT", Integer.class);
            
            response.put("status", "success");
            response.put("table", "CLIENT");
            response.put("count", count != null ? count : 0);
            response.put("message", count + " client(s) trouvé(s)");
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Table CLIENT non trouvée ou erreur: " + e.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("/schema")
    public Map<String, Object> getClientSchema() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Map<String, Object>> columns = jdbcTemplate.queryForList(
                "SELECT column_name, data_type, nullable, data_length " +
                "FROM user_tab_columns " +
                "WHERE table_name = 'CLIENT' " +
                "ORDER BY column_id");
            
            response.put("status", "success");
            response.put("table", "CLIENT");
            response.put("columns", columns);
            response.put("count", columns.size());
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        
        return response;
    }
}