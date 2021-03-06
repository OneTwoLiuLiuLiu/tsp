package com.sunyard.frameworkset.plugin.tsp.manager.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by pc on 2018/3/1.
 */
@Entity
@Table(name = "tsp_data_source_config")
public class JobConnectPoolConfig {
    @Id
    @Column(name ="tsp_data_source_config_id")
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid2")
    private String tsp_data_source_config_id;

    @Column
    private String data_source_id;

    @Column
    private String data_source_name;

    @Column
    private Integer init_count;

    @Column
    private Integer max_count;

    @Column
    private Integer current_count;

    @Column
    private String db_name;

    @Column
    private String db_type;

    @Column
    private String db_ip;

    @Column
    private Integer db_port;

    @Column
    private String db_user;

    @Column
    private String db_pwd;

    @Column
    private String db_url;

    @Column
    private String db_driver;

    @Column
    private Integer enable_flag;

    public String getTsp_data_source_config_id() {
        return tsp_data_source_config_id;
    }

    public void setTsp_data_source_config_id(String tsp_data_source_config_id) {
        this.tsp_data_source_config_id = tsp_data_source_config_id;
    }

    public String getData_source_name() {
        return data_source_name;
    }

    public void setData_source_name(String data_source_name) {
        this.data_source_name = data_source_name;
    }

    public Integer getInit_count() {
        return init_count;
    }

    public void setInit_count(Integer init_count) {
        this.init_count = init_count;
    }

    public Integer getMax_count() {
        return max_count;
    }

    public void setMax_count(Integer max_count) {
        this.max_count = max_count;
    }

    public Integer getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(Integer current_count) {
        this.current_count = current_count;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getDb_type() {
        return db_type;
    }

    public void setDb_type(String db_type) {
        this.db_type = db_type;
    }

    public String getDb_ip() {
        return db_ip;
    }

    public void setDb_ip(String db_ip) {
        this.db_ip = db_ip;
    }

    public Integer getDb_port() {
        return db_port;
    }

    public void setDb_port(Integer db_port) {
        this.db_port = db_port;
    }

    public String getDb_user() {
        return db_user;
    }

    public void setDb_user(String db_user) {
        this.db_user = db_user;
    }

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public String getDb_driver() {
        return db_driver;
    }

    public void setDb_driver(String db_driver) {
        this.db_driver = db_driver;
    }

    public Integer getEnable_flag() {
        return enable_flag;
    }

    public void setEnable_flag(Integer enable_flag) {
        this.enable_flag = enable_flag;
    }

    public String getData_source_id() {
        return data_source_id;
    }

    public void setData_source_id(String data_source_id) {
        this.data_source_id = data_source_id;
    }

    public String getDb_pwd() {
        return db_pwd;
    }

    public void setDb_pwd(String db_pwd) {
        this.db_pwd = db_pwd;
    }
}
