package com.sunyard.frameworkset.plugin.tsp.execute.datastage;

import com.sun.jna.*;

import java.util.Arrays;
import java.util.List;

public interface vmdsapi extends Library {

	public static final int DSJ_JOBSTATUS = 1;
	public static final int DSJE_NOERROR = 0;
	public static final int DSJ_RUNNORMAL = 1;
	public static final int DSJ_RUNRESET = 2;
	public static final int DSJ_RUNVALIDATE = 3;
	public static final int DSJ_LIMITWARN = 1;
	public static final int DSJ_LIMITROWS = 2;
	public static final int DSJS_RUNNING = 0;
	public static final int DSJS_RUNOK = 1;
	public static final int DSJS_RUNWARN = 2;
	public static final int DSJS_RUNFAILED = 3;
	public static final int DSJ_PARAMTYPE_STRING = 0;
	public static final int DSJ_PARAMTYPE_ENCRYPTED = 1;
	public static final int DSJ_PARAMTYPE_INTEGER = 2;
	public static final int DSJ_PARAMTYPE_FLOAT = 3;
	public static final int DSJ_PARAMTYPE_PATHNAME = 4;
	public static final int DSJ_PARAMTYPE_LIST = 5;
	public static final int DSJ_PARAMTYPE_DATE = 6;
	public static final int DSJ_PARAMTYPE_TIME = 7;
	public static final int DSJ_JOBSTARTTIMESTAMP = 4;
	public static final int DSJ_JOBLASTTIMESTAMP = 11;
	public static final int DSJE_NOMORE = -1001;
	public static final int DSJ_PROJECTNAME = 2;



	

	
	
	
	public static class DSPROJECT extends Structure {
		public int dsapiVersionNo;
		public int sessionId;
		public byte valueMark;
		public byte fieldMark;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "dsapiVersionNo", "sessionId",
					"valueMark", "fieldMark" });
		}
		
		public static class ByReference extends DSPROJECTINFO implements Structure.ByReference {

        };
        public static class ByValue extends DSPROJECTINFO implements Structure.ByValue {

        };
		
	}

	public static class DSJOB extends Structure {
		public DSPROJECT hProject; /* Reference to project handle for job */
		public String serverJobHandle; /* Text of handle to job on server */
		public Pointer logData; /* Cached log summary data */
		public int logDataLen; /* Size of log summary data */
		public int logDataPsn; /* Current position in logData */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "hProject", "serverJobHandle",
					"logData", "logDataLen", "logDataPsn" });
		}
		
		public static class ByReference extends DSJOB implements Structure.ByReference {

        };
        public static class ByValue extends DSJOB implements Structure.ByValue {

        };
		
	}

	public static class DSPARAM extends Structure {
		public int paramType;
		/* public DSPARAMVALUE paramValue; */
		public String pString; /* String type */
		public String pEncrypt; /* Encrypted string */
		public int pInt; /* Integer */
		public float pFloat; /* Float */
		public String pPath; /* Pathname */
		public String pListValue; /* String from a list */
		public String pDate; /* Date string */
		public String pTime; /* Time string */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "paramType", "pString",
					"pEncrypt", "pInt", "pFloat", "pPath", "pListValue",
					"pDate", "pTime" });
		}
	}

	public static class DSPARAMVALUE extends Union {
		public String pString; /* String type */
		public String pEncrypt; /* Encrypted string */
		public int pInt; /* Integer */
		public float pFloat; /* Float */
		public String pPath; /* Pathname */
		public String pListValue; /* String from a list */
		public String pDate; /* Date string */
		public String pTime; /* Time string */
	}

	public static class DSPARAMINFO extends Structure {
		public DSPARAM defaultValue; /* Default value of parameter */
		public String helpText; /* Description of parameter */
		public String paramPrompt; /* Prompt for the parameter */
		public int paramType; /* Type of parameter */
		public DSPARAM desDefaultValue; /*
										 * Designer set default value of
										 * parameter
										 */
		public String listValues; /* Set of valid strings for List type parameter */
		public String desListValues; /*
									 * Set of designer set valid strings for
									 * list type parameter
									 */
		public int promptAtRun; /* Flag indicating prompt required at run time. */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "defaultValue", "helpText",
					"paramPrompt", "paramType", "desDefaultValue",
					"listValues", "desListValues", "promptAtRun" });
		}
	}

	public static class DSPROJECTINFO extends Structure {
		public int infoType; /* Key indicating type of information */
		public String jobList; /* List of names of all jobs in the project */
		public String projectName; /* Name of the current project */
		public String hostName; /* Host name of the server */
		public String installTag; /* Install tag of current server engine */
		public String tcpPort; /* TCP port of current server engine */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "jobList",
					"projectName", "hostName", "installTag", "tcpPort" });
		}
		
		
		public static class ByReference extends DSPROJECTINFO implements Structure.ByReference {

        };
        public static class ByValue extends DSPROJECTINFO implements Structure.ByValue {

        };

	}

	public static class DSREPOSINFO extends Structure {
		public int infoType;

		public DSREPOSJOBINFO jobs; /* linked list of found jobs */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "jobs" });
		}
	}

	public static class DSREPOSUSAGEJOB extends Structure {
		public String jobname; /* Job and cat name */
		public int jobtype; /* type of job */
		public DSREPOSUSAGEJOB nextjob; /*
										 * next job in linked list of jobs at
										 * same level
										 */
		public DSREPOSUSAGEJOB childjob; /* first child job in linked list */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "jobname", "jobtype",
					"nextjob", "childjob" });
		}
	}

	public static class DSREPOSUSAGE extends Structure {
		public int infoType;

		public DSREPOSUSAGEJOB jobs; /* linked list of jobs */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "jobs" });
		}
	}

	public static class DSSTAGEINFO extends Structure {
		public int infoType; /* Key indicating type of information */

		public DSLOGDETAIL lastError; /* Last error message (log) from a link */
		public String typeName; /* Name of stage type */
		public int inRowNum; /* Primary links input row number */
		public String linkList; /* List of stage link names */
		public String stageName; /* Name of current stage */
		public String varList; /* List of stage variable names */
		public NativeLong stageStartTime; /* Date and time when stage started */
		public NativeLong stageEndTime; /* Date and time when stage finished */
		public String linkTypes;
		public String stageDesc;
		public String instList;
		public String cpuList;
		public String stageElapsed;
		public String pidList;
		public int stageStatus;
		public String custInfoList; /* List of custom info names */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "lastError",
					"typeName", "inRowNum", "linkList", "stageName", "varList",
					"stageStartTime", "stageEndTime", "linkTypes", "stageDesc",
					"instList", "cpuList", "stageElapsed", "pidList",
					"stageStatus", "custInfoList" });
		}
	}

	public static class DSVARINFO extends Structure {
		public int infoType; /* Key indicating type of information */
		public String varValue;
		public String varDesc;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "varValue",
					"varDesc" });
		}
	}

	public static class DSCUSTINFO extends Structure {
		public int infoType; /* Key indicating type of information */

		public String custInfoValue;
		public String custInfoDesc;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "custInfoValue",
					"custInfoDesc" });
		}

	}

	public static class DSREPORTINFO extends Structure {
		public int reportType;

		public String reportText;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "reportType", "reportText" });
		}
	}

	public static class DSREPOSJOBINFO extends Structure {
		public String jobname; /* Includes category */
		public int jobtype; /* InfoType constant */
		public DSREPOSJOBINFO nextjob; /* link to next job */

		@Override
		protected List getFieldOrder() {
			return Arrays
					.asList(new String[] { "jobname", "jobtype", "nextjob" });
		}
	}

	public static class DSLOGDETAIL extends Structure {
		public int eventId; /* Id of this event */
		public NativeLong timestamp; /* Data and time event occurred */
		public int type; /* Type of event */
		public String username; /* User name associated with event */
		public String fullMessage; /* Full description of event */

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "eventId", "timestamp", "type",
					"username", "fullMessage" });
		}
	}

	public static class DSLOGEVENT extends Structure {
		public int eventId; /* Id of this event */
		public NativeLong timestamp; /* Data and time event occurred */
		public int type; /* Type of event */
		public Pointer message; /* Short event message */
		
		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "eventId", "timestamp", "type",
					"message" });
		}
		
		public static class ByReference extends DSLOGEVENT implements Structure.ByReference {

        };
        public static class ByValue extends DSLOGEVENT implements Structure.ByValue {

        };

		
		
	}

	public static class DSLINKINFO extends Structure {
		public int infoType; /* Key indicating type of information */

		public DSLOGDETAIL lastError; /* Last error message from link */
		public int rowCount; /* number of rows that have passed down link */
		public String linkName; /* Name of this link */
		public String linkSQLState; /* SQL state for last error */
		public String linkDBMSCode; /* DBMC code for last error */
		public String linkDesc;
		public String linkedStage;
		public String rowCountList;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "lastError",
					"rowCount", "linkName", "linkSQLState", "linkDBMSCode",
					"linkDesc", "linkedStage", "rowCountList" });
		}
	}

	public static class DSJOBINFO extends Structure {
		public int infoType; /* Key indicating type of information */
		public DSJOBINFOVALUE info;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList(new String[] { "infoType", "info" });
		}
	}

	public static class DSJOBINFOVALUE extends Union {
		public int jobStatus; /* Current status of job */
		public String jobController; /* Name of controlling job */
		public NativeLong jobStartTime; /* Date and time when job started */
		public int jobWaveNumber; /* Wave number of current or last run */
		public String userStatus; /* Last set user status */
		public String stageList; /* List of stage names in job */
		public String paramList; /* List of job parameter names */
		public String jobName; /* Name of this job */
		public int jobControl; /* Current job control status */
		public int jobPid; /* Job process id */
		public NativeLong jobLastTime; /* Date and time when job finished */
		public String jobInvocations; /* List of job invocation ids */
		public int jobInterimStatus; /* Interim status of job */
		public String jobInvocationId; /* Job invocation id */
		public String jobDesc;
		public String stageList2;
		public String jobElapsed;
		public int jobDMIService; /* Job is a DMI (aka WEB) service */
		public int jobMultiInvokable; /* Job can be multiply invoked */
		public String jobFullDesc; /* Full job description */
		public int jobRestartable; /* Job is restartable */
	}

	vmdsapi INSTANCE = (vmdsapi) Native.loadLibrary("vmdsapi", vmdsapi.class);

	public DSPROJECT DSOpenProjectEx(int serial, String pro);

	public DSJOB DSOpenJob(DSPROJECT pro, String job);

	public int DSLockJob(DSJOB job);

	public int DSServerMessage(String MsgIdStr, String DefMsg, String Prms,
							   String pMessage, int SizeMessage);

	public int DSGetCustInfo(DSJOB JobHandle, String StageName,
							 String CustinfoName, int InfoType, DSSTAGEINFO ReturnInfo);

	public int DSGetReposInfo(DSPROJECT hProject, int ObjectType, int InfoType,
							  String SearchCriteria, String StartingCategory, int Subcategories,
							  DSREPOSINFO ReturnInfo);

	public String DSGetProjectList();

	public DSPROJECT DSOpenProject(String ProjectName);

	public void DSSetServerParams(String ServerName, String UserName,
								  String Password);

	public int DSCloseProject(DSPROJECT ProjectHandle);

	public int DSCloseJob(DSJOB JobHandle);

	public int DSGetJobInfo(DSJOB JobHandle, int InfoType, DSJOBINFO ReturnInfo);

	public int DSAddEnvVar(DSPROJECT hProject, String EnvVarName, String Type,
						   String PromptText, String Value);

	public int DSAddProject(String ProjectName, String ProjectLocation);

	public int DSDeleteEnvVar(DSPROJECT hProject, String EnvVar);

	public int DSDeleteProject(String ProjectName);

	public int DSFindFirstLogEntry(DSJOB JobHandle, int EventType, NativeLong StartTime, NativeLong EndTime, int MaxNumber, DSLOGEVENT Event);

	public int DSFindNextLogEntry(DSJOB JobHandle, DSLOGEVENT Event);

	public int DSGetLastError();

	public String DSGetLastErrorMsg(DSPROJECT ProjectHandle);

	public int DSGetLinkInfo(DSJOB JobHandle, String StageName,
							 String LinkName, int InfoType, DSLINKINFO ReturnInfo);

	public int DSGetLogEntry(DSJOB JobHandle, int EventId, DSLOGDETAIL Event);

	public int DSGetNewestLogId(DSJOB JobHandle, int EventType);

	public int DSGetParamInfo(DSJOB JobHandle, String ParamName,
							  DSPARAMINFO ReturnInfo);

	public int DSGetProjectInfo(DSPROJECT ProjectHandle, int InfoType,
								DSPROJECTINFO ReturnInfo);

	public int DSGetReposUsage(DSPROJECT hProject, int RelationshipType,
							   String ObjectName, int Recursive, DSREPOSUSAGE ReturnInfo);

	public int DSGetStageInfo(DSJOB JobHandle, String StageName, int InfoType,
							  DSSTAGEINFO ReturnInfo);

	public int DSGetVarInfo(DSJOB JobHandle, String StageName, String VarName,
							int InfoType, DSSTAGEINFO ReturnInfo);

	public String DSListEnvVars(DSPROJECT hProject);

	public String DSListProjectProperties(DSPROJECT hProject);

	public int DSLogEvent(DSJOB JobHandle, int EventType, String Reserved,
						  String Message);

	public int DSMakeJobReport(DSJOB JobHandle, int ReportType,
							   String LineSeparator, DSREPORTINFO ReturnInfo);

	public int DSRunJob(DSJOB JobHandle, int RunMode);

	public int DSSetEnvVar(DSPROJECT hProject, String EnvVarName, String Value);

	public int DSSetGenerateOpMetaData(DSJOB JobHandle, int value);

	public int DSSetJobLimit(DSJOB JobHandle, int LimitType, int LimitValue);

	public int DSSetParam(DSJOB JobHandle, String ParamName, DSPARAM Param);

	public int DSSetProjectProperty(DSPROJECT hProject, String Property,
									String Value);

	public int DSStopJob(DSJOB JobHandle);

	public int DSUnlockJob(DSJOB JobHandle);

	public int DSWaitForJob(DSJOB JobHandle);
}
