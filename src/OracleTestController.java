package com.gestionventes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OracleTestController {

    private final JdbcTemplate jdbcTemplate;

    public OracleTestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/oracle-test")
    public Map<String, Object> testOracleConnection() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Test 1: Récupérer la date Oracle (CORRIGÉ)
            String oracleDate = jdbcTemplate.queryForObject(
                "SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') FROM DUAL", 
                String.class
            );
            
            // Test 2: Simple requête sur DUAL
            String testQuery = jdbcTemplate.queryForObject(
                "SELECT 'Connexion Oracle OK' FROM DUAL", 
                String.class
            );
            
            // Test 3: Compter les tables (avec gestion des erreurs)
            int clientCount = 0;
            int produitCount = 0;
            int commandeCount = 0;
            
            try {
                clientCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM CLIENT", Integer.class
                );
            } catch (Exception e) {
                clientCount = -1; // -1 signifie table non trouvée
            }
            
            try {
                produitCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM PRODUIT", Integer.class
                );
            } catch (Exception e) {
                produitCount = -1;
            }
            
            try {
                commandeCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM COMMANDE", Integer.class
                );
            } catch (Exception e) {
                commandeCount = -1;
            }
            
            response.put("status", "success");
            response.put("message", "Connexion Oracle établie");
            response.put("test_query", testQuery);
            response.put("oracle_date", oracleDate);
            response.put("tables", Map.of(
                "CLIENT", clientCount,
                "PRODUIT", produitCount,
                "COMMANDE", commandeCount
            ));
            response.put("database_info", Map.of(
                "driver", "Oracle JDBC",
                "connection", "OK"
            ));
            
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Erreur Oracle: " + e.getMessage());
            response.put("solution", "Vérifiez la configuration dans application.properties");
            response.put("error_details", e.getClass().getName());
        }
        
        return response;
    }
    
    @GetMapping("/oracle-test/simple")
    public Map<String, Object> simpleTest() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Test très simple
            String result = jdbcTemplate.queryForObject(
                "SELECT 'Connexion Oracle OK' FROM DUAL", 
                String.class
            );
            
            response.put("status", "success");
            response.put("message", result);
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
            // Récupérer la liste des tables (version simplifiée)
            List<Map<String, Object>> tables = jdbcTemplate.queryForList(
                "SELECT table_name FROM user_tables ORDER BY table_name"
            );
            
            // Liste des tables avec leur état
            List<Map<String, Object>> tableInfo = new ArrayList<>();
            
            for (Map<String, Object> table : tables) {
                String tableName = (String) table.get("TABLE_NAME");
                Map<String, Object> tableData = new HashMap<>();
                tableData.put("name", tableName);
                
                try {
                    Integer count = jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM " + tableName, 
                        Integer.class
                    );
                    tableData.put("count", count);
                    tableData.put("status", "OK");
                } catch (Exception e) {
                    tableData.put("count", 0);
                    tableData.put("status", "ERROR");
                    tableData.put("error", "Table vide ou inaccessible");
                }
                
                tableInfo.add(tableData);
            }
            
            response.put("status", "success");
            response.put("total_tables", tables.size());
            response.put("tables", tableInfo);
            
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
                "SELECT COUNT(*) FROM CLIENT", Integer.class
            );
            
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
                "ORDER BY column_id"
            );
            
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
    
    @GetMapping("/test-dual")
    public String testDual() {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT 'Connexion Oracle fonctionne!' FROM DUAL", 
                String.class
            );
        } catch (Exception e) {
            return "Erreur: " + e.getMessage();
        }
    }
}