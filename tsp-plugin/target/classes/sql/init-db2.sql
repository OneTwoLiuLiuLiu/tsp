create table qrtz_job_details(
  sched_name varchar(120) not null,
  job_name varchar(80) not null,
  job_group varchar(80) not null,
  description varchar(120) null,
  job_class_name varchar(128) not null,
  is_durable varchar(1) not null,
  is_nonconcurrent varchar(1) not null,
  is_update_data varchar(1) not null,
  requests_recovery varchar(1) not null,
  job_data blob(2000),
  primary key (sched_name,job_name,job_group)
);

create table qrtz_triggers(
  sched_name varchar(120) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  job_name varchar(80) not null,
  job_group varchar(80) not null,
  description varchar(120) null,
  next_fire_time bigint,
  prev_fire_time bigint,
  priority integer,
  trigger_state varchar(16) not null,
  trigger_type varchar(8) not null,
  start_time bigint not null,
  end_time bigint,
  calendar_name varchar(80),
  misfire_instr smallint,
  job_data blob(2000),
  primary key (sched_name,trigger_name,trigger_group),
  foreign key (sched_name,job_name,job_group) references qrtz_job_details(sched_name,job_name,job_group)
);

create table qrtz_simple_triggers(
  sched_name varchar(120) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  repeat_count bigint not null,
  repeat_interval bigint not null,
  times_triggered bigint not null,
  primary key (sched_name,trigger_name,trigger_group),
  foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_cron_triggers(
  sched_name varchar(120) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  cron_expression varchar(120) not null,
  time_zone_id varchar(80),
  primary key (sched_name,trigger_name,trigger_group),
  foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

CREATE TABLE qrtz_simprop_triggers(
    sched_name varchar(120) not null,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (sched_name,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (sched_name,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(sched_name,TRIGGER_NAME,TRIGGER_GROUP)
);

create table qrtz_blob_triggers(
  sched_name varchar(120) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  blob_data blob(2000) null,
  primary key (sched_name,trigger_name,trigger_group),
  foreign key (sched_name,trigger_name,trigger_group) references qrtz_triggers(sched_name,trigger_name,trigger_group)
);

create table qrtz_calendars(
  sched_name varchar(120) not null,
  calendar_name varchar(80) not null,
  calendar blob(2000) not null,
  primary key (sched_name,calendar_name)
);

create table qrtz_fired_triggers(
  sched_name varchar(120) not null,
  entry_id varchar(95) not null,
  trigger_name varchar(80) not null,
  trigger_group varchar(80) not null,
  instance_name varchar(80) not null,
  fired_time bigint not null,
  sched_time bigint not null,
  priority integer not null,
  state varchar(16) not null,
  job_name varchar(80) null,
  job_group varchar(80) null,
  is_nonconcurrent varchar(1) null,
  requests_recovery varchar(1) null,
  primary key (sched_name,entry_id)
);


create table qrtz_paused_trigger_grps(
  sched_name varchar(120) not null,
  trigger_group  varchar(80) not null,
  primary key (sched_name,trigger_group)
);

create table qrtz_scheduler_state (
  sched_name varchar(120) not null,
  instance_name varchar(80) not null,
  last_checkin_time bigint not null,
  checkin_interval bigint not null,
    primary key (sched_name,instance_name)
);

create table qrtz_locks(
  sched_name varchar(120) not null,
  lock_name  varchar(40) not null,
  primary key (sched_name,lock_name)
);

CREATE TABLE "TSP_HISTORY_JOB"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "CALL_PLAN_INST_ID" VARCHAR(255 OCTETS) ,
		  "END_TIME" VARCHAR(20 OCTETS) ,
		  "JOB_ID" VARCHAR(40 OCTETS) ,
		  "LAST_MODIFY_USER" VARCHAR(255 OCTETS) ,
		  "LAST_MODIFY_TIME" VARCHAR(255 OCTETS) ,
		  "PLAN_INST_ID" VARCHAR(40 OCTETS) ,
		  "RUN_HOSTNAME" VARCHAR(100 OCTETS) ,
		  "RUN_LOG" VARCHAR(100 OCTETS) ,
		  "START_TIME" VARCHAR(20 OCTETS) ,
		  "STATUS" VARCHAR(1 OCTETS)
);
ALTER TABLE "TSP_HISTORY_JOB" ADD PRIMARY KEY ("ID") ;

CREATE TABLE "TSP_JOB"(
		  "TYPE" VARCHAR(31 OCTETS) NOT NULL ,
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "CREATE_USER_CODE" VARCHAR(20 OCTETS) ,
		  "CREATE_TIME" VARCHAR(20 OCTETS) ,
		  "CREATE_USER" VARCHAR(20 OCTETS) ,
		  "EXPECT_RUN_TIME" INTEGER ,
		  "IGNORE_ERR" VARCHAR(1 OCTETS) ,
		  "NAME" VARCHAR(50 OCTETS) ,
		  "NEXT_JOB_ID" VARCHAR(255 OCTETS) ,
		  "PARENT_JOB_ID" VARCHAR(255 OCTETS) ,
		  "PREV_JOB_ID" VARCHAR(255 OCTETS) ,
		  "RETRY_CNT" INTEGER ,
		  "RETRY_SEC" INTEGER ,
		  "RUN_PARAMS" VARCHAR(200 OCTETS) ,
		  "TIME_FORMAT" VARCHAR(20 OCTETS) ,
		  "FILEPATH" VARCHAR(255 OCTETS) ,
		  "DSJOBNAME" VARCHAR(255 OCTETS) ,
		  "DSPROJECTNAME" VARCHAR(255 OCTETS) ,
		  "RETURNVALUES" VARCHAR(20 OCTETS) ,
		  "HTTPURL" VARCHAR(120 OCTETS) ,
		  "CLIENTNAME" VARCHAR(10 OCTETS) ,
		  "INITIALMEMORYVALUE" VARCHAR(10 OCTETS) ,
		  "MAXMEMORYVALUE" VARCHAR(10 OCTETS) ,
		  "DIR" VARCHAR(100 OCTETS) ,
		  "FILE" VARCHAR(255 OCTETS) ,
		  "JOBS" VARCHAR(50 OCTETS) ,
		  "PARAMS" VARCHAR(255 OCTETS) ,
		  "PASS" VARCHAR(20 OCTETS) ,
		  "REP" VARCHAR(50 OCTETS) ,
		  "KIND" VARCHAR(255 OCTETS) ,
		  "USER_NAME" VARCHAR(50 OCTETS) ,
		  "TRANS" VARCHAR(50 OCTETS) ,
		  "CALLPLANCONFIGID" VARCHAR(50 OCTETS) ,
		  "CALLPLANNAME" VARCHAR(50 OCTETS) ,
		  "DATABASEIP" VARCHAR(50 OCTETS) ,
		  "DATABASENAME" VARCHAR(50 OCTETS) ,
		  "DATABASEPORT" VARCHAR(50 OCTETS) ,
		  "DATABASEPWD" VARCHAR(50 OCTETS) ,
		  "DATABASETYPE" VARCHAR(50 OCTETS) ,
		  "DATABASEUSER" VARCHAR(50 OCTETS) ,
		  "PROCEDURENAME" VARCHAR(50 OCTETS) ,
		  "PLAN_ID" VARCHAR(255 OCTETS)
);
ALTER TABLE "TSP_JOB" ADD PRIMARY KEY	("ID") ;


CREATE TABLE "TSP_JOBSERVER_CONFIG"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "HOSTNAME" VARCHAR(100 OCTETS) ,
		  "MAX_HISTORY_DAY" INTEGER ,
		  "MAX_RUN_NUM" INTEGER ,
		  "MIN_FREE_CPU" INTEGER ,
		  "MIN_FREE_MEMORY" INTEGER ,
		  "RUN_JOB_TYPE" VARCHAR(255 OCTETS) ,
		  "RUN_JOB_TYPE_CN" VARCHAR(255 OCTETS) ,
		  "STATUS" VARCHAR(1 OCTETS)
);
ALTER TABLE "TSP_JOBSERVER_CONFIG"  ADD PRIMARY KEY	("ID") ;

CREATE TABLE "TSP_JOB_CONFIG"(
		  "TYPE" VARCHAR(31 OCTETS) NOT NULL ,
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "CREATE_TIME" VARCHAR(20 OCTETS) ,
		  "CREATE_USER" VARCHAR(20 OCTETS) ,
		  "CREATE_USER_CODE" VARCHAR(20 OCTETS) ,
		  "EXPECT_RUN_TIME" INTEGER ,
		  "IGNORE_ERR" VARCHAR(1 OCTETS) ,
		  "MODIFIED_TIME" VARCHAR(20 OCTETS) ,
		  "MODIFIED_USER" VARCHAR(20 OCTETS) ,
		  "MODIFIED_USER_CODE" VARCHAR(20 OCTETS) ,
		  "NAME" VARCHAR(50 OCTETS) ,
		  "NEXT_JOB_ID" VARCHAR(255 OCTETS) ,
		  "PARENT_JOB_ID" VARCHAR(255 OCTETS) ,
		  "PREV_JOB_ID" VARCHAR(255 OCTETS) ,
		  "RETRY_CNT" INTEGER ,
		  "RETRY_SEC" INTEGER ,
		  "RUN_PARAMS" VARCHAR(200 OCTETS) ,
		  "TIME_FORMAT" VARCHAR(20 OCTETS) ,
		  "FILEPATH" VARCHAR(255 OCTETS) ,
		  "DSJOBNAME" VARCHAR(255 OCTETS) ,
		  "DSPROJECTNAME" VARCHAR(255 OCTETS) ,
		  "RETURNVALUES" VARCHAR(20 OCTETS) ,
		  "HTTPURL" VARCHAR(120 OCTETS) ,
		  "INITIALMEMORYVALUE" VARCHAR(10 OCTETS) ,
		  "MAXMEMORYVALUE" VARCHAR(10 OCTETS) ,
		  "DIR" VARCHAR(100 OCTETS) ,
		  "FILE" VARCHAR(255 OCTETS) ,
		  "JOBS" VARCHAR(50 OCTETS) ,
		  "PASS" VARCHAR(20 OCTETS) ,
		  "REP" VARCHAR(50 OCTETS) ,
		  "KIND" VARCHAR(100 OCTETS) ,
		  "USER_NAME" VARCHAR(50 OCTETS) ,
		  "TRANS" VARCHAR(50 OCTETS) ,
		  "CALLPLANCONFIGID" VARCHAR(50 OCTETS) ,
		  "CALLPLANNAME" VARCHAR(50 OCTETS) ,
		  "DATABASEIP" VARCHAR(50 OCTETS) ,
		  "DATABASENAME" VARCHAR(50 OCTETS) ,
		  "DATABASEPORT" VARCHAR(50 OCTETS) ,
		  "DATABASEPWD" VARCHAR(50 OCTETS) ,
		  "DATABASETYPE" VARCHAR(50 OCTETS) ,
		  "DATABASEUSER" VARCHAR(50 OCTETS) ,
		  "PROCEDURENAME" VARCHAR(50 OCTETS) ,
		  "PLAN_CONFIG_ID" VARCHAR(255 OCTETS)
);
ALTER TABLE "TSP_JOB_CONFIG" ADD PRIMARY KEY ("ID") ;


CREATE TABLE "TSP_JOB_POOL"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "CREATE_TIME" VARCHAR(20 OCTETS) ,
		  "JOB_ID" VARCHAR(40 OCTETS) ,
		  "JOB_INSTANCE_ID" VARCHAR(40 OCTETS) ,
		  "PLAN_INST_ID" VARCHAR(40 OCTETS) ,
		  "STATUS" VARCHAR(1 OCTETS)
);
ALTER TABLE "TSP_JOB_POOL" ADD PRIMARY KEY ("ID") ;


CREATE TABLE "TSP_PLAN"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "DEPLOY_TIME" VARCHAR(20 OCTETS) ,
		  "DEPLOY_USER" VARCHAR(20 OCTETS) ,
		  "DEPLOY_USER_CODE" VARCHAR(20 OCTETS) ,
		  "NAME" VARCHAR(50 OCTETS) ,
		  "NOTICE_WAY" VARCHAR(1 OCTETS) ,
		  "PERIOD" VARCHAR(100 OCTETS) ,
		  "RECIPIENTS" VARCHAR(100 OCTETS) ,
		  "RECIPIENTSCN" VARCHAR(100 OCTETS) ,
		  "VERSION" INTEGER ,
		  "PLAN_CONFIG_ID" VARCHAR(255 OCTETS)
);
ALTER TABLE "TSP_PLAN" ADD PRIMARY KEY ("ID") ;

CREATE TABLE "TSP_PLANCONFIG_PARAMS"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "PLAN_CONFIG_ID" VARCHAR(40 OCTETS) ,
		  "PARAM_NAME" VARCHAR(100 OCTETS) ,
		  "PARAM_VALUE" VARCHAR(255 OCTETS)
);
ALTER TABLE "TSP_PLANCONFIG_PARAMS" ADD PRIMARY KEY ("ID") ;


CREATE TABLE "TSP_PLANINST_PARAM"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "PARAM_NAME" VARCHAR(100 OCTETS) ,
		  "PARAM_VALUE" VARCHAR(255 OCTETS) ,
		  "PLAN_INST_ID" VARCHAR(255 OCTETS)
);
ALTER TABLE "TSP_PLANINST_PARAM" ADD PRIMARY KEY ("ID") ;

CREATE TABLE "TSP_PLAN_CONFIG"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "CREATE_TIME" VARCHAR(20 OCTETS) ,
		  "CREATE_USER" VARCHAR(20 OCTETS) ,
		  "CREATE_USER_CODE" VARCHAR(20 OCTETS) ,
		  "REMAKE" VARCHAR(1024 OCTETS) ,
		  "IS_SHARE" VARCHAR(1 OCTETS) ,
		  "NAME" VARCHAR(50 OCTETS) ,
		  "NOTICE_WAY" VARCHAR(1 OCTETS) ,
		  "PERIOD" VARCHAR(100 OCTETS) ,
		  "RECIPIENTS" VARCHAR(100 OCTETS) ,
		  "RECIPIENTSCN" VARCHAR(100 OCTETS) ,
		  "START_TIME" VARCHAR(20 OCTETS) ,
		  "START_USER" VARCHAR(20 OCTETS) ,
		  "START_USER_CODE" VARCHAR(20 OCTETS) ,
		  "STATUS" VARCHAR(1 OCTETS) ,
		  "STOP_TIME" VARCHAR(20 OCTETS) ,
		  "STOP_USER" VARCHAR(20 OCTETS) ,
		  "STOP_USER_CODE" VARCHAR(20 OCTETS)
);
ALTER TABLE "TSP_PLAN_CONFIG" ADD PRIMARY KEY ("ID") ;


CREATE TABLE "TSP_PLAN_INSTANCE"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "BATCH_NO" VARCHAR(20 OCTETS) ,
		  "END_TIME" VARCHAR(20 OCTETS) ,
		  "END_USER" VARCHAR(20 OCTETS) ,
		  "END_USER_CODE" VARCHAR(20 OCTETS) ,
		  "FAIL_JOB_NUM" INTEGER ,
		  "LEFT_JOB_NUM" INTEGER ,
		  "PARENT_PLAN_INST_ID" VARCHAR(255 OCTETS) ,
		  "PASS_JON_NUM" INTEGER ,
		  "RUN_PLAN_ID" VARCHAR(255 OCTETS) ,
		  "RUNNING_JOB_NUM" INTEGER ,
		  "START_TIME" VARCHAR(20 OCTETS) ,
		  "STATUS" VARCHAR(20 OCTETS) ,
		  "SUCCESS_JOB_NUM" INTEGER ,
		  "PLAN_ID" VARCHAR(255 OCTETS)
);
ALTER TABLE "TSP_PLAN_INSTANCE" ADD PRIMARY KEY ("ID") ;


CREATE TABLE "TSP_RERUNNING_JOB"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "CALL_PLAN_INST_ID" VARCHAR(40 OCTETS) ,
		  "CREATE_TIME" VARCHAR(20 OCTETS) ,
		  "CREATE_USER" VARCHAR(20 OCTETS) ,
		  "END_TIME" VARCHAR(20 OCTETS) ,
		  "JOB_ID" VARCHAR(40 OCTETS) ,
		  "JOB_INSTANCE_ID" VARCHAR(40 OCTETS) ,
		  "LAST_MODIFY_USER" VARCHAR(20 OCTETS) ,
		  "LAST_MODIFY_TIME" VARCHAR(20 OCTETS) ,
		  "PLAN_INST_ID" VARCHAR(40 OCTETS) ,
		  "RUN_HOSTNAME" VARCHAR(100 OCTETS) ,
		  "RUN_LOG" VARCHAR(100 OCTETS) ,
		  "START_TIME" VARCHAR(20 OCTETS) ,
		  "STATUS" VARCHAR(20 OCTETS) );
ALTER TABLE "TSP_RERUNNING_JOB" ADD PRIMARY KEY ("ID") ;


CREATE TABLE "TSP_RUNNING_JOB"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "CALL_PLAN_INST_ID" VARCHAR(40 OCTETS) ,
		  "END_TIME" VARCHAR(20 OCTETS) ,
		  "JOB_ID" VARCHAR(40 OCTETS) ,
		  "JOB_INSTANCE_ID" VARCHAR(40 OCTETS) ,
		  "LAST_MODIFY_USER" VARCHAR(255 OCTETS) ,
		  "LAST_MODIFY_TIME" VARCHAR(255 OCTETS) ,
		  "PLAN_INST_ID" VARCHAR(40 OCTETS) ,
		  "RUN_HOSTNAME" VARCHAR(100 OCTETS) ,
		  "RUN_LOG" VARCHAR(100 OCTETS) ,
		  "START_TIME" VARCHAR(20 OCTETS) ,
		  "STATUS" VARCHAR(20 OCTETS)
);
ALTER TABLE "TSP_RUNNING_JOB" ADD PRIMARY KEY ("ID") ;


CREATE TABLE "TSP_RUN_PLAN"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "END_TIME" VARCHAR(20 OCTETS) ,
		  "PLAN_CONFIG_ID" VARCHAR(40 OCTETS) ,
		  "START_TIME" VARCHAR(20 OCTETS) ,
		  "STATUS" VARCHAR(1 OCTETS)
);
ALTER TABLE "TSP_RUN_PLAN" ADD PRIMARY KEY ("ID") ;

CREATE TABLE "TSP_SYSTEM_PARAMS_CONFIG"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL ,
		  "DEC" VARCHAR(255 OCTETS) ,
		  "NAME" VARCHAR(255 OCTETS) ,
		  "VALUE" VARCHAR(255 OCTETS)
);
ALTER TABLE "TSP_SYSTEM_PARAMS_CONFIG"  ADD PRIMARY KEY ("ID") ;

CREATE TABLE "TSP_DATA_SOURCE_CONFIG"  (
       "TSP_DATA_SOURCE_CONFIG_ID" VARCHAR(255) NOT NULL,
		  "DATA_SOURCE_ID" VARCHAR(255) NOT NULL ,
		  "DATA_SOURCE_NAME" VARCHAR(255) NOT NULL ,
		  "INIT_COUNT" INTEGER ,
		  "MAX_COUNT" INTEGER ,
		  "CURRENT_COUNT" INTEGER ,
		  "DB_NAME" VARCHAR(32) ,
		  "DB_TYPE" VARCHAR(16) ,
		  "DB_IP" VARCHAR(16) ,
		  "DB_PORT" INTEGER ,
		  "DB_USER" VARCHAR(32) ,
		  "DB_PWD" VARCHAR(32) ,
		  "DB_URL" VARCHAR(128) ,
		  "DB_DRIVER" VARCHAR(64) ,
		  "ENABLE_FLAG" INTEGER );



COMMENT ON TABLE "TSP_DATA_SOURCE_CONFIG" IS '数据库连接池配置';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."TSP_DATA_SOURCE_CONFIG_ID" IS '主键id';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."CURRENT_COUNT" IS '当前使用连接数';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DATA_SOURCE_ID" IS '数据源编号';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DATA_SOURCE_NAME" IS '数据源名称';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_DRIVER" IS '数据库驱动类';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_IP" IS '数据库IP';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_NAME" IS '数据库名称';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_PORT" IS '数据库端口';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_PWD" IS '数据库密码';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_TYPE" IS '数据库类型';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_URL" IS '数据库连接URL';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."DB_USER" IS '数据库用户';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."ENABLE_FLAG" IS '状态：  1--有效，0--无效';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."INIT_COUNT" IS '初始化连接数目';

COMMENT ON COLUMN "TSP_DATA_SOURCE_CONFIG"."MAX_COUNT" IS '最大连接数';

ALTER TABLE "TSP_DATA_SOURCE_CONFIG"  ADD PRIMARY KEY ("TSP_DATA_SOURCE_CONFIG_ID") ;

CREATE TABLE "TSP_WAITTING_JOB"(
		  "ID" VARCHAR(255 OCTETS) NOT NULL , 
		  "CALL_PLAN_INST_ID" VARCHAR(40 OCTETS) , 
		  "CREATE_TIME" VARCHAR(20 OCTETS) , 
		  "JOB_ID" VARCHAR(40 OCTETS) , 
		  "PLAN_INST_ID" VARCHAR(40 OCTETS)
);
ALTER TABLE "TSP_WAITTING_JOB"  ADD PRIMARY KEY  ("ID") ;


ALTER TABLE "TSP_JOB" ADD CONSTRAINT "FKC2E73C4FD39F7BF3" FOREIGN KEY
		("PLAN_ID")
	REFERENCES "TSP_PLAN"
		("ID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
  ENFORCED
	ENABLE QUERY OPTIMIZATION ;

ALTER TABLE "TSP_PLAN"
	ADD CONSTRAINT "FK9A02FCD7350CAD86" FOREIGN KEY
		("PLAN_CONFIG_ID")
	REFERENCES "TSP_PLAN_CONFIG"
		("ID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION ;

ALTER TABLE "TSP_PLANINST_PARAM" ADD CONSTRAINT "FK54D18D8B5744BF55" FOREIGN KEY
		 ("PLAN_INST_ID")
	 REFERENCES "TSP_PLAN_INSTANCE"
		 ("ID")
	 ON DELETE NO ACTION
	 ON UPDATE NO ACTION
	 ENFORCED
	 ENABLE QUERY OPTIMIZATION ;

ALTER TABLE "TSP_PLAN_INSTANCE"
	ADD CONSTRAINT "FK23C24AFDD39F7BF3" FOREIGN KEY
		("PLAN_ID")
	REFERENCES "TSP_PLAN"
		("ID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION ;

ALTER TABLE "TSP_JOB_CONFIG"
	ADD CONSTRAINT "FKA0E99D32350CAD86" FOREIGN KEY
		("PLAN_CONFIG_ID")
	REFERENCES "TSP_PLAN_CONFIG"
		("ID")
	ON DELETE NO ACTION
	ON UPDATE NO ACTION
	ENFORCED
	ENABLE QUERY OPTIMIZATION;

CREATE VIEW TSP_JOB_MONITOR AS SELECT
        p.NAME AS PNAME ,
        j.NAME AS JNAME ,
        j.ID AS JOBID ,
        u.ID ,
        u.PLAN_INST_ID ,
        j.PLAN_ID ,
        u.END_TIME ,
        u.RUN_HOSTNAME ,
        u.RUN_LOG ,
        u.START_TIME ,
        u.STATUS
    FROM
        (
            SELECT
                ID ,
                PLAN_INST_ID ,
                JOB_ID ,
                END_TIME ,
                RUN_HOSTNAME ,
                RUN_LOG ,
                START_TIME ,
                STATUS
            FROM
                TSP_RUNNING_JOB
        UNION SELECT
                ID ,
                PLAN_INST_ID ,
                JOB_ID ,
                END_TIME ,
                RUN_HOSTNAME ,
                RUN_LOG ,
                START_TIME ,
                STATUS
            FROM
                TSP_HISTORY_JOB
        ) u LEFT JOIN TSP_JOB j
            ON u.JOB_ID = j.ID LEFT JOIN TSP_PLAN_INSTANCE q
            ON u.PLAN_INST_ID = q.ID LEFT JOIN TSP_PLAN p
            ON q.PLAN_ID = p.ID;

CREATE VIEW tsp_plan_monitor AS SELECT
        pi.ID AS PLAN_INST_ID ,
        tp.NAME AS PNAME ,
        pi.BATCH_NO ,
        pi.ID ,
        pi.END_USER ,
        (
            CASE
                WHEN p.JobAccount IS NULL
                THEN 0
                ELSE p.JobAccount
            END
        ) AS JobAccount ,
        (
            (
                CASE
                    WHEN p.JobAccount IS NULL
                    THEN 0
                    ELSE p.JobAccount
                END
            ) -(
                CASE
                    WHEN r.RunningAccount IS NULL
                    THEN 0
                    ELSE r.RunningAccount
                END
            ) -(
                CASE
                    WHEN h.DoneAccount IS NULL
                    THEN 0
                    ELSE h.DoneAccount
                END
            ) -(
                CASE
                    WHEN f.FailureAccount IS NULL
                    THEN 0
                    ELSE f.FailureAccount
                END
            )
        ) AS surplus ,
        (
            CASE
                WHEN r.RunningAccount IS NULL
                THEN 0
                ELSE r.RunningAccount
            END
        ) AS RunningAccount ,
        (
            CASE
                WHEN h.DoneAccount IS NULL
                THEN 0
                ELSE h.DoneAccount
            END
        ) AS DoneAccount ,
        (
            CASE
                WHEN f.FailureAccount IS NULL
                THEN 0
                ELSE f.FailureAccount
            END
        ) AS FailureAccount ,
        pi.STATUS ,
        pi.END_TIME ,
        pi.START_TIME
    FROM
        TSP_PLAN_INSTANCE pi LEFT JOIN TSP_PLAN tp
            ON tp.ID = pi.PLAN_ID LEFT JOIN(
            SELECT
                j.PLAN_ID AS PID ,
                COUNT(j.ID) AS JobAccount
            FROM
                tsp.TSP_JOB j
            GROUP BY
                j.PLAN_ID
        ) p
            ON p.PID = pi.PLAN_ID LEFT JOIN(
            SELECT
                r.PLAN_INST_ID ,
                COUNT(r.STATUS) AS RunningAccount
            FROM
                tsp.TSP_RUNNING_JOB r
            WHERE
                r.STATUS = '0'
            GROUP BY
                r.PLAN_INST_ID
        ) r
            ON r.PLAN_INST_ID = pi.Id LEFT JOIN(
            SELECT
                h.PLAN_INST_ID ,
                COUNT(h.STATUS) AS DoneAccount
            FROM
                tsp.TSP_HISTORY_JOB h
            WHERE
                h.STATUS = '1'
            GROUP BY
                h.PLAN_INST_ID
        ) h
            ON h.PLAN_INST_ID = pi.Id LEFT JOIN(
            SELECT
                f.PLAN_INST_ID ,
                COUNT(f.STATUS) AS FailureAccount
            FROM
                tsp.TSP_Running_JOB f
            WHERE
                f.STATUS = '3'
            GROUP BY
                f.PLAN_INST_ID
        ) f
            ON f.PLAN_INST_ID = pi.Id;


CREATE VIEW TSP_POOL_VIEW AS SELECT
        jp.status ,
        jp.ID ,
        p.NAME AS PNAME ,
        j.NAME AS JNAME ,
        pc.CREATE_USER ,
        pc.CREATE_TIME ,
        pc.START_USER ,
        pc.START_TIME
    FROM
        TSP_JOB_POOL jp LEFT JOIN TSP_JOB j
            ON j.ID = jp.JOB_ID LEFT JOIN TSP_PLAN p
            ON j.PLAN_ID = p.ID LEFT JOIN TSP_PLAN_INSTANCE pi
            ON jp.JOB_INSTANCE_ID = pi.ID LEFT JOIN TSP_PLAN_CONFIG pc
            ON p.PLAN_CONFIG_ID = pc.ID;


CREATE VIEW TSP_WAITTING_VIEW AS SELECT
        w.ID ,
        p.NAME AS PNAME ,
        j.NAME AS JNAME ,
        pc.START_USER ,
        pc.START_TIME ,
        pc.CREATE_USER ,
        pc.CREATE_TIME
    FROM
        TSP_WAITTING_JOB w LEFT JOIN TSP_PLAN_INSTANCE pi
            ON w.PLAN_INST_ID = pi.ID LEFT JOIN TSP_PLAN p
            ON pi.PLAN_ID = p.ID LEFT JOIN TSP_JOB j
            ON w.JOB_ID = j.ID LEFT JOIN TSP_PLAN_CONFIG pc
            ON pc.ID = p.PLAN_CONFIG_ID;

alter table tsp_plan add column start_kind  CHAR(1) default null;
alter table tsp_plan_config add column start_kind  CHAR(1) default null;

