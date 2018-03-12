/**
 * Created by Misaki on 2017/1/3.
 */
$ ( function () {
	//修改弹出框
	var path = $ ( "#path" ).val ();
	var planConfigId = $ ( "#planConfigId" ).val ();
	var jobId;
	var jobConfigtype;
	var windows;
	var windowsTitle;
	var form;
	var validate;
	var clicked;
	$ ( '.tree-modify' ).on ( 'click', function () {
		$ ( '.fist-menu' ).hide ();
		if(jobConfigtype=="jar"){
			windows = $ ( ".modal-jaradd" );
			form=$("#form_jaradd");
			validate = jarValidate;
			windowsTitle = "修改jar作业配置";
		}else if(jobConfigtype=="kjb"){
			windows = $ ( ".modal-kjbadd" );
			form=$("#form_kjbadd");
			validate = kjbValidate;
			windowsTitle = "修改kjb作业配置";
		}else if(jobConfigtype=="ktr"){
			windows = $ ( ".modal-ktradd" );
			form=$("#form_ktradd");
			validate = ktrValidate;
			windowsTitle = "修改ktr作业配置";
		}else if(jobConfigtype=="datastage"){
			windows = $ ( ".modal-datastageadd" );
			form=$("#form_datastageadd");
			validate = datastageValidate;
			windowsTitle = "修改DataStage作业配置";
		}else if(jobConfigtype=="shell"){
			windows = $ ( ".modal-shelladd" );
			form=$("#form_shelladd");
			validate = shellValidate;
			windowsTitle = "修改shell作业配置";
		}else if(jobConfigtype=="bat"){
			windows = $ ( ".modal-batadd" );
			form=$("#form_batadd");
			validate = batValidate;
			windowsTitle = "修改bat作业配置";
		}else if(jobConfigtype=="http"){
			windows = $ ( ".modal-httpadd" );
			form=$("#form_httpadd");
			validate = httpValidate;
			windowsTitle = "修改http作业配置";
		}else if(jobConfigtype=="exe"){
			windows = $ ( ".modal-exeadd" );
			form=$("#form_exeadd");
			validate = exeValidate;
			windowsTitle = "修改exe作业配置";
		}else if(jobConfigtype=="storepro"){
			windows = $ ( ".modal-storeproadd" );
			form=$("#form_storeproadd");
			validate = storeproValidate;
			windowsTitle = "修改存储过程作业配置";
		}else if(jobConfigtype=="parallel"){
			windows = $ ( ".modal-paralleladd" );
			form=$("#form_paralleladd");
			validate = parallelValidate;
		}else if(jobConfigtype=="serial"){
			windows = $ ( ".modal-serialadd" );
			form=$("#form_serialadd");
			validate = serialValidate;
		}else if(jobConfigtype=="turnover"){
			windows = $ ( ".modal-turnover" );
			form=$("#form_turnover");
			validate=turnoverValidate;
			windowsTitle = "修改翻牌周期";
		}else{
			windows = $ ( ".modal-planadd" );
			form=$("#form_planadd");
			validate = planValidate;
			windowsTitle = "修改计划作业配置";
		}
		$.ajax({
			url :path+ "/tsp/jobconfig/getonejobconfig.json",
			dataType : "json",
			type : "post",
			data: {
				id:jobId,
				type:jobConfigtype
			},
			async : false,
			success : function(result){
				if (result.success) {
					var data = result['result'];
					windows.find('input').each(function (index, input) {
						$(input).val(data[$(input).attr('name')]);
					});
					windows.find('select').each(function (index, select) {
						$(select).val(data[$(select).attr('name')]);
					});
					if (data.jobType == "kjb") {
						if (data.select == 1) {
							$(".kjb_file").attr("disabled", true);
							$(".kjb_rep").attr("disabled", false);
							$(".kjb_user").attr("disabled", false);
							$(".kjb_pass").attr("disabled", false);
							$(".kjb_dir").attr("disabled", false);
							$(".kjb_jobs").attr("disabled", false);
						} else if (data.select == 2) {
							$(".kjb_user").attr("disabled", true);
							$(".kjb_pass").attr("disabled", true);
							$(".kjb_file").attr("disabled", true);
							$(".kjb_rep").attr("disabled", false);
							$(".kjb_dir").attr("disabled", false);
							$(".kjb_jobs").attr("disabled", false);
						} else {
							$(".kjb_rep").attr("disabled", true);
							$(".kjb_user").attr("disabled", true);
							$(".kjb_pass").attr("disabled", true);
							$(".kjb_dir").attr("disabled", true);
							$(".kjb_jobs").attr("disabled", true);
							$(".kjb_file").attr("disabled", false);
						}
					}
					if (data.jobType == "ktr") {
						if (data.select == 1) {
							$(".ktr_file").attr("disabled", true);
							$(".ktr_rep").attr("disabled", false);
							$(".ktr_user").attr("disabled", false);
							$(".ktr_pass").attr("disabled", false);
							$(".ktr_dir").attr("disabled", false);
							$(".ktr_trans").attr("disabled", false);
						} else if (data.select == 2) {
							$(".ktr_user").attr("disabled", true);
							$(".ktr_pass").attr("disabled", true);
							$(".ktr_file").attr("disabled", true);
							$(".ktr_rep").attr("disabled", false);
							$(".ktr_dir").attr("disabled", false);
							$(".ktr_trans").attr("disabled", false);
						} else {
							$(".ktr_rep").attr("disabled", true);
							$(".ktr_user").attr("disabled", true);
							$(".ktr_pass").attr("disabled", true);
							$(".ktr_dir").attr("disabled", true);
							$(".ktr_trans").attr("disabled", true);
							$(".ktr_file").attr("disabled", false);
						}
					}
				} else {
						errmsg ( "打开失败" );
				}
			},
			error : function(result){
				layer.alert ( ! result.message ? "与后台通信异常!" : result.message );
			}
		});
		layer.open ( {
			type : 1, title : "计划配置", area : "566px", content : windows,
			btn : [ "确定", "关闭" ],
			yes : function ( index ) {
				if ( validate () ) {
					$.post ( path + '/tsp/jobconfig/updatejobconfig.json', form.serialize (), function ( result ) {
						if ( result.success ) {
							layer.close ( index );
							layer.alert ( result[ 'message' ] );
							$ ( "table tbody" ).html ( "" );
							zTreeObj.reAsyncChildNodes ( rightClickSelectedNode.getParentNode (), "refresh" );
						}else {
							errmsg ( result['message'] );
						}
					} );
				}
			}, btn2 : function ( index ) {
				layer.close ( index );
			}
		} );
		//修改时把值赋给form
		$ ( '#root-'+jobConfigtype ).val ( jobId );
		$('#planId-'+jobConfigtype).val(planConfigId);
		$('#type-'+jobConfigtype).val(jobConfigtype);
	} );

	//根节点新增jar
	$ ( '.new-jar' ).on ( 'click', function () {
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_jar_reset' ).click ();
		clicked = false;
		layer.open ( {
			type : 1, title : "jar作业配置", area : "566px", content : $ ( ".modal-jaradd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_jaradd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ('#root-jar' ).val ( jobId );
		$('#planId-jar').val(planConfigId);
		$('#type-jar').val("jar");
	} );

	//根节点新增kjb
	$ ( '.new-kjb' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_kjb_reset' ).click ();
		addkjb();
		layer.open ( {
			type : 1, title : "kjb作业配置", area : "566px", content : $ ( ".modal-kjbadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_kjbadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-kjb' ).val ( jobId );
		$ ( '#planId-kjb' ).val ( planConfigId );
		$ ( '#type-kjb' ).val ( "kjb" );
	} );

	//根节点新增ktr
	$ ( '.new-ktr' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_ktr_reset' ).click ();
		addktr();
		layer.open ( {
			type : 1, title : "ktr作业配置", area : "566px", content : $ ( ".modal-ktradd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_ktradd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-ktr' ).val ( jobId );
		$ ( '#planId-ktr' ).val ( planConfigId );
		$ ( '#type-ktr' ).val ( "ktr" );
	} );

	//根节点新增datastage
	$ ( '.new-datastage' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_datastage_reset' ).click ();
		layer.open ( {
			type : 1, title : "datastage作业配置", area : "566px", content : $ ( ".modal-datastageadd" ), btn : [ "确定", "关闭" ],
				yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_datastageadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-datastage' ).val ( jobId );
		$ ( '#planId-datastage' ).val ( planConfigId );
		$ ( '#type-datastage' ).val ( "datastage" );
	} );

	//根节点新增shell
	$ ( '.new-shell' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_shell_reset' ).click ();
		layer.open ( {
			type : 1, title : "shell作业配置", area : "566px", content : $ ( ".modal-shelladd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_shelladd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-shell' ).val ( jobId );
		$ ( '#planId-shell' ).val ( planConfigId );
		$ ( '#type-shell' ).val ( "shell" );
	} );

	//根节点新增bat
	$ ( '.new-bat' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_batadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "bat作业配置", area : "566px", content : $ ( ".modal-batadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_batadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-bat' ).val ( jobId );
		$ ( '#planId-bat' ).val ( planConfigId );
		$ ( '#type-bat' ).val ( "bat" );
	} );

	//根节点新增http
	$ ( '.new-http' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_httpadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "http作业配置", area : "566px", content : $ ( ".modal-httpadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_httpadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-http' ).val ( jobId );
		$ ( '#planId-http' ).val ( planConfigId );
		$ ( '#type-http' ).val ( "http" );
	} );

	//根节点新增exe
	$ ( '.new-exe' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_exeadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "exe作业配置", area : "566px", content : $ ( ".modal-exeadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_exeadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-exe' ).val ( jobId );
		$ ( '#planId-exe' ).val ( planConfigId );
		$ ( '#type-exe' ).val ( "exe" );
	} );

	//根节点新增存储过程
	$ ( '.new-storepro' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_storeproadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "存储过程作业配置", area : "566px", content : $ ( ".modal-storeproadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_storeproadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-storepro' ).val ( jobId );
		$ ( '#planId-storepro' ).val ( planConfigId );
		$ ( '#type-storepro' ).val ( "storepro" );
	} );

	//根节点周期翻牌
	$ ( '.new-turnover' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_turnover_reset' ).click ();
		layer.open ( {
			type : 1, title : "周期翻牌作业配置", area : "566px", content : $ ( ".modal-turnover" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_turnover" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-turnover' ).val ( jobId );
		$ ( '#planId-turnover' ).val ( planConfigId );
		$ ( '#type-turnover' ).val ( "turnover" );
	} );

	//根节点新增任务
	$ ( '.new-task' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_taskadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "任务配置", area : "566px", content : $ ( ".modal-taskadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_taskadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-task' ).val ( jobId );
		$ ( '#planId-task' ).val ( planConfigId );
		$ ( '#type-task' ).val ( "task" );
	} );

	//新增子任务
	$ ( '.taskjob' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_taskadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "任务配置", area : "566px", content : $ ( ".modal-taskadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_taskadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-task' ).val ( jobId );
		$ ( '#planId-task' ).val ( planConfigId );
		$ ( '#type-task' ).val ( "task" );
	} );



	//根节点新增并行作业
	$ ( '.new-parallel' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_paralleladd_reset' ).click ();
		layer.open ( {
			type : 1, title : "并行作业配置", area : "566px", content : $ ( ".modal-paralleladd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_paralleladd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-parallel' ).val ( jobId );
		$ ( '#planId-parallel' ).val ( planConfigId );
		$ ( '#type-parallel' ).val ( "parallel" );
	} );

	//根节点新增串行作业
	$ ( '.new-serial' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_serialadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "串行行作业配置", area : "566px", content : $ ( ".modal-serialadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_serialadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-serial' ).val ( jobId );
		$ ( '#planId-serial' ).val ( planConfigId );
		$ ( '#type-serial' ).val ( "serial" );
	} );

	//根节点新增计划
	$ ( '.new-plan' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_planadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "plan任务配置", area : "566px", content : $ ( ".modal-planadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_planadd" ).attr ( "action", "addnewjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-plan' ).val ( jobId );
		$ ( '#planId-plan' ).val ( planConfigId );
		$ ( '#type-plan' ).val ( "plan" );
	} );

	//子节点新增_前jar
	$ ( '.before-jar' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_jar_reset' ).click ();
		layer.open ( {
			type : 1, title : "jar作业配置", area : "566px", content : $ ( ".modal-jaradd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_jaradd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-jar' ).val ( jobId );
		$ ( '#planId-jar' ).val ( planConfigId );
		$ ( '#type-jar' ).val ( "jar" );
	} );

	//子节点新增_前kjb
	$ ( '.before-kjb' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_kjb_reset' ).click ();
		addkjb();
		layer.open ( {
			type : 1, title : "kjb作业配置", area : "566px", content : $ ( ".modal-kjbadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_kjbadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-kjb' ).val ( jobId );
		$ ( '#planId-kjb' ).val ( planConfigId );
		$ ( '#type-kjb' ).val ( "kjb" );
	} );

	//子节点新增_前ktr
	$ ( '.before-ktr' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_ktr_reset' ).click ();
		addktr();
		layer.open ( {
			type : 1, title : "ktr作业配置", area : "566px", content : $ ( ".modal-ktradd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_ktradd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-ktr' ).val ( jobId );
		$ ( '#planId-ktr' ).val ( planConfigId );
		$ ( '#type-ktr' ).val ( "ktr" );
	} );

	//子节点新增_前datastage
	$ ( '.before-datastage' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_datastage_reset' ).click ();
		layer.open ( {
			type : 1, title : "datastage作业配置", area : "566px", content : $ ( ".modal-datastageadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_datastageadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-datastage' ).val ( jobId );
		$ ( '#planId-datastage' ).val ( planConfigId );
		$ ( '#type-datastage' ).val ( "datastage" );
	} );

	//子节点新增_前shell
	$ ( '.before-shell' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_shell_reset' ).click ();
		layer.open ( {
			type : 1, title : "shell作业配置", area : "566px", content : $ ( ".modal-shelladd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_shelladd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-shell' ).val ( jobId );
		$ ( '#planId-shell' ).val ( planConfigId );
		$ ( '#type-shell' ).val ( "shell" );
	} );

	//子节点新增_前bat
	$ ( '.before-bat' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_batadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "bat作业配置", area : "566px", content : $ ( ".modal-batadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_batadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-bat' ).val ( jobId );
		$ ( '#planId-bat' ).val ( planConfigId );
		$ ( '#type-bat' ).val ( "bat" );
	} );

	//子节点新增_前http
	$ ( '.before-http' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_httpadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "http作业配置", area : "566px", content : $ ( ".modal-httpadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_httpadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-http' ).val ( jobId );
		$ ( '#planId-http' ).val ( planConfigId );
		$ ( '#type-http' ).val ( "http" );
	} );

	//子节点新增_前exe
	$ ( '.before-exe' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_exeadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "exe作业配置", area : "566px", content : $ ( ".modal-exeadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_exeadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-exe' ).val ( jobId );
		$ ( '#planId-exe' ).val ( planConfigId );
		$ ( '#type-exe' ).val ( "exe" );
	} );

	//子节点新增_前存储过程
	$ ( '.before-storepro' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_storeproadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "存储过程作业配置", area : "566px", content : $ ( ".modal-storeproadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_storeproadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-storepro' ).val ( jobId );
		$ ( '#planId-storepro' ).val ( planConfigId );
		$ ( '#type-storepro' ).val ( "storepro" );
	} );

	//子节点新增_前并行作业
	$ ( '.before-parallel' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_paralleladd_reset' ).click ();
		layer.open ( {
			type : 1, title : "并行作业配置", area : "566px", content : $ ( ".modal-paralleladd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_paralleladd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-parallel' ).val ( jobId );
		$ ( '#planId-parallel' ).val ( planConfigId );
		$ ( '#type-parallel' ).val ( "parallel" );
	} );

	//子节点新增_前串行作业
	$ ( '.before-serial' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_serialadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "串行行作业配置", area : "566px", content : $ ( ".modal-serialadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_serialadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-serial' ).val ( jobId );
		$ ( '#planId-serial' ).val ( planConfigId );
		$ ( '#type-serial' ).val ( "serial" );
	} );

	//子节点新增_前计划
	$ ( '.before-plan' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_planadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "plan任务配置", area : "566px", content : $ ( ".modal-planadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_planadd" ).attr ( "action", "addbeforejobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-plan' ).val ( jobId );
		$ ( '#planId-plan' ).val ( planConfigId );
		$ ( '#type-plan' ).val ( "plan" );
	} );

	//子节点新增_后jar
	$ ( '.after-jar' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_jar_reset' ).click ();
		layer.open ( {
			type : 1, title : "jar作业配置", area : "566px", content : $ ( ".modal-jaradd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_jaradd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-jar' ).val ( jobId );
		$ ( '#planId-jar' ).val ( planConfigId );
		$ ( '#type-jar' ).val ( "jar" );
	} );

	//子节点新增_后kjb
	$ ( '.after-kjb' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_kjb_reset' ).click ();
		addkjb();
		layer.open ( {
			type : 1, title : "kjb作业配置", area : "566px", content : $ ( ".modal-kjbadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_kjbadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-kjb' ).val ( jobId );
		$ ( '#planId-kjb' ).val ( planConfigId );
		$ ( '#type-kjb' ).val ( "kjb" );
	} );

	//子节点新增_后ktr
	$ ( '.after-ktr' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_ktr_reset' ).click ();
		addktr();
		layer.open ( {
			type : 1, title : "ktr作业配置", area : "566px", content : $ ( ".modal-ktradd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_ktradd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-ktr' ).val ( jobId );
		$ ( '#planId-ktr' ).val ( planConfigId );
		$ ( '#type-ktr' ).val ( "ktr" );
	} );

	//子节点新增_后datastage
	$ ( '.after-datastage' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_datastage_reset' ).click ();
		layer.open ( {
			type : 1, title : "datastage作业配置", area : "566px", content : $ ( ".modal-datastageadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_datastageadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-datastage' ).val ( jobId );
		$ ( '#planId-datastage' ).val ( planConfigId );
		$ ( '#type-datastage' ).val ( "datastage" );
	} );

	//子节点新增_后shell
	$ ( '.after-shell' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_shell_reset' ).click ();
		layer.open ( {
			type : 1, title : "shell作业配置", area : "566px", content : $ ( ".modal-shelladd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_shelladd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-shell' ).val ( jobId );
		$ ( '#planId-shell' ).val ( planConfigId );
		$ ( '#type-shell' ).val ( "shell" );
	} );

	//子节点新增_后bat
	$ ( '.after-bat' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_batadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "bat作业配置", area : "566px", content : $ ( ".modal-batadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_batadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-bat' ).val ( jobId );
		$ ( '#planId-bat' ).val ( planConfigId );
		$ ( '#type-bat' ).val ( "bat" );
	} );

	//子节点新增_后http
	$ ( '.after-http' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_httpadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "http作业配置", area : "566px", content : $ ( ".modal-httpadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_httpadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-http' ).val ( jobId );
		$ ( '#planId-http' ).val ( planConfigId );
		$ ( '#type-http' ).val ( "http" );
	} );

	//子节点新增_后exe
	$ ( '.after-exe' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_exeadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "exe作业配置", area : "566px", content : $ ( ".modal-exeadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_exeadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-exe' ).val ( jobId );
		$ ( '#planId-exe' ).val ( planConfigId );
		$ ( '#type-exe' ).val ( "exe" );
	} );

	//子节点新增_后存储过程
	$ ( '.after-storepro' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_storeproadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "存储过程作业配置", area : "566px", content : $ ( ".modal-storeproadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_storeproadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-storepro' ).val ( jobId );
		$ ( '#planId-storepro' ).val ( planConfigId );
		$ ( '#type-storepro' ).val ( "storepro" );
	} );

	//子节点新增_后并行作业
	$ ( '.after-parallel' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_paralleladd_reset' ).click ();
		layer.open ( {
			type : 1, title : "并行作业配置", area : "566px", content : $ ( ".modal-paralleladd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_paralleladd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-parallel' ).val ( jobId );
		$ ( '#planId-parallel' ).val ( planConfigId );
		$ ( '#type-parallel' ).val ( "parallel" );
	} );

	//子节点新增_后串行作业
	$ ( '.after-serial' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_serialadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "串行行作业配置", area : "566px", content : $ ( ".modal-serialadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_serialadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-serial' ).val ( jobId );
		$ ( '#planId-serial' ).val ( planConfigId );
		$ ( '#type-serial' ).val ( "serial" );
	} );

	//子节点新增_后计划
	$ ( '.after-plan' ).on ( 'click', function () {
		clicked = false;
		$ ( '.fist-menu' ).hide ();
		$ ( '#form_planadd_reset' ).click ();
		layer.open ( {
			type : 1, title : "plan任务配置", area : "566px", content : $ ( ".modal-planadd" ), btn : [ "确定", "关闭" ],
			yes : function () {
				if ( ! clicked ) {
					clicked = true;
					$ ( "#form_planadd" ).attr ( "action", "addafterjobconfig" ).submit ();
				}
			}
		} );
		$ ( '#root-plan' ).val ( jobId );
		$ ( '#planId-plan' ).val ( planConfigId );
		$ ( '#type-plan' ).val ( "plan" );
	} );

	//jar验证
	$ ( "#form_jaradd" ).on ( "submit", jarValidate);
	function jarValidate() {
		if ( $ ( "#form_jaradd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_jaradd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_jaradd input[name='initialMemoryValue']" ).val () == "" ) {
			layer.alert ( '内存初始值不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_jaradd input[name='maxMemoryValue']" ).val () == "" ) {
			layer.alert ( '内存最大值f不能为空' );
			clicked = false;
			return false;
		}
		if ( !  $ ( "#form_jaradd input[name='filePath']" ).val ().endWith ( ".jar" ) ) {
			layer.alert ( '文件路径必须以.jar结尾' );
			clicked = false;
			return false;
		}
		return true;
	}

	//kjb验证
	$ ( "#form_kjbadd" ).on ( "submit", kjbValidate);
	function kjbValidate() {
		var selectkjb = $(".selectkjb").val();
		if (selectkjb == 1) {
			if ($("#form_kjbadd input[name='rep']").val() == "") {
				layer.alert('资源库不能为空');
				clicked = false;
				return false;
			}
			if ($("#form_kjbadd input[name='user']").val() == "") {
				layer.alert('用户名不能为空');
				clicked = false;
				return false;
			}
			if ($("#form_kjbadd input[name='pass']").val() == "") {
				layer.alert('密码不能为空');
				clicked = false;
				return false;
			}
			if ($("#form_kjbadd input[name='dir']").val() == "") {
				layer.alert('文件路径不能为空');
				clicked = false;
				return false;
			}
			if ($("#form_kjbadd input[name='jobs']").val() == "") {
				layer.alert('作业文件名不能为空');
				clicked = false;
				return false;
			}
		} else if (selectkjb == 2) {
			if ($("#form_kjbadd input[name='rep']").val() == "") {
				layer.alert('资源库不能为空');
				clicked = false;
				return false;
			}
			if ($("#form_kjbadd input[name='dir']").val() == "") {
				layer.alert('文件路径不能为空');
				clicked = false;
				return false;
			}
			if ($("#form_kjbadd input[name='jobs']").val() == "") {
				layer.alert('作业文件名不能为空');
				clicked = false;
				return false;
			}
		} else {
			if (!$("#form_kjbadd input[name='file']").val().endWith(".kjb")) {
				layer.alert('本地文件路径必须以.kjb结尾');
				clicked = false;
				return false;
			}
		}
		if ( $ ( "#form_kjbadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_kjbadd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}
   //turnover验证
	$("#form_turnover").on("submit",turnoverValidate);
	function turnoverValidate() {
		return true;
	}
	//ktr验证
	$ ( "#form_ktradd" ).on ( "submit", ktrValidate);
		function ktrValidate(){
			var selectktr = $(".selectktr").val();
			if(selectktr==1){
				if ( $ ( "#form_ktradd input[name='rep']" ).val () == "" ) {
					layer.alert ( '资源库不能为空' );
					clicked = false;
					return false;
				}
				if ( $ ( "#form_ktradd input[name='user']" ).val () == "" ) {
					layer.alert ( '用户名不能为空' );
					clicked = false;
					return false;
				}
				if ( $ ( "#form_ktradd input[name='pass']" ).val () == "" ) {
					layer.alert ( '密码不能为空' );
					clicked = false;
					return false;
				}
				if ( $ ( "#form_ktradd input[name='dir']" ).val () == "" ) {
					layer.alert ( '文件路径不能为空' );
					clicked = false;
					return false;
				}
				if ( $ ( "#form_ktradd input[name='trans']" ).val () == "" ) {
					layer.alert ( '转换文件名不能为空' );
					clicked = false;
					return false;
				}
			}else if(selectktr==2){
				if ( $ ( "#form_ktradd input[name='rep']" ).val () == "" ) {
					layer.alert ( '资源库不能为空' );
					clicked = false;
					return false;
				}
				if ( $ ( "#form_ktradd input[name='dir']" ).val () == "" ) {
					layer.alert ( '文件路径不能为空' );
					clicked = false;
					return false;
				}
				if ( $ ( "#form_ktradd input[name='trans']" ).val () == "" ) {
					layer.alert ( '转换文件名不能为空' );
					clicked = false;
					return false;
				}
			}else{
				if (  ! $ ( "#form_ktradd input[name='file']" ).val ().endWith ( ".ktr" ) ) {
					layer.alert ( '本地文件路径必须以.ktr结尾' );
					clicked = false;
					return false;
				}
			}
		if ( $ ( "#form_ktradd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_ktradd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}

	//datastage验证
	$ ( "#form_datastageadd" ).on ( "submit", datastageValidate );
	function datastageValidate () {
		if ( $ ( "#form_datastageadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_datastageadd input[name='dsProjectName']" ).val () == "" ) {
			layer.alert ( 'DS工程名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_datastageadd input[name='dsJobName']" ).val () == "" ) {
			layer.alert ( 'DS作业名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_datastageadd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}

	//shell验证
	$ ( "#form_shelladd" ).on ( "submit", shellValidate );
	function shellValidate () {
		if ( $ ( "#form_shelladd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_shelladd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		if (  ! $ ( "#form_shelladd input[name='filePath']" ).val ().endWith ( ".sh" ) ) {
			layer.alert ( '文件路径必须以.sh结尾' );
			clicked = false;
			return false;
		}
		return true;
	}

	//bat验证
	$ ( "#form_batadd" ).on ( "submit", batValidate );
	function batValidate () {
		if ( $ ( "#form_batadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_batadd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		if ( ! $ ( "#form_batadd input[name='filePath']" ).val ().endWith ( ".bat" ) ) {
			layer.alert ( '文件路径必须以.bat结尾' );
			clicked = false;
			return false;
		}
		return true;
	}
	//http验证
	$ ( "#form_httpadd" ).on ( "submit", httpValidate );
	function httpValidate () {
		if ( $ ( "#form_httpadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_httpadd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_httpadd input[name='httpUrl']" ).val () == "" ) {
			layer.alert ( 'Http的Url不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}
	//exe验证
	$ ( "#form_exeadd" ).on ( "submit", exeValidate );
	function exeValidate () {
		if ( $ ( "#form_exeadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_exeadd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		if ( ! $ ( "#form_exeadd input[name='filePath']" ).val ().endWith ( ".exe" ) ) {
			layer.alert ( '文件路径必须以.exe结尾' );
			clicked = false;
			return false;
		}
		return true;
	}
	//存储过程验证
	$ ( "#form_storeproadd" ).on ( "submit", storeproValidate );
	function storeproValidate () {
		if ( $ ( "#form_storeproadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='timeFormat']" ).val () == "" ) {
			layer.alert ( '日期格式不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='procedureName']" ).val () == "" ) {
			layer.alert ( '存储过程名字不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='dataBaseType']" ).val () == "" ) {
			layer.alert ( '数据库类型不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='dataBaseUser']" ).val () == "" ) {
			layer.alert ( '数据库用户名不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='dataBasePwd']" ).val () == "" ) {
			layer.alert ( '数据库密码不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='dataBaseIp']" ).val () == "" ) {
			layer.alert ( '数据库连接Ip不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='dataBasePort']" ).val () == "" ) {
			layer.alert ( '数据库端口号不能为空' );
			clicked = false;
			return false;
		}
		if ( $ ( "#form_storeproadd input[name='dataBaseName']" ).val () == "" ) {
			layer.alert ( '数据库名字不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}
	//并行作业验证
	$ ( "#form_paralleladd" ).on ( "submit", parallelValidate );
	function parallelValidate () {
		if ( $ ( "#form_paralleladd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}
	//串行作业验证
	$ ( "#form_serialadd" ).on ( "submit", serialValidate );
	function serialValidate () {
		if ( $ ( "#form_serialadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}

	//计划验证
	$ ( "#form_planadd" ).on ( "submit", planValidate );
	function planValidate () {
		//设置选中的计划名称
		$ ( "#call-plan-config-name" ).val ( $ ( "#call-plan-config-id" ).find ( "option:selected" ).text () );
		if ( $ ( "#form_planadd input[name='name']" ).val () == "" ) {
			layer.alert ( '名称不能为空' );
			clicked = false;
			return false;
		}
		return true;
	}
	//table tr 选中
	$ ( '.data-tb tbody tr' ).on ( 'click', function () {
		$ ( this ).addClass ( 'data-avtive' ).siblings ().removeClass ( 'data-avtive' );
	} );

	//获取鼠标位置
	function mousePosition ( ev ) {
		ev = ev || window.event;
		if ( ev.pageX || ev.pageY ) {
			return {
				x : ev.pageX, y : ev.pageY
			};
		}
		return {
			x : ev.clientX + document.body.scrollLeft - document.body.clientLeft,
			y : ev.clientY + document.body.scrollTop - document.body.clientTop
		};
	}

	//zTree使用
	var zTreeObj;
	var jobTreeOption = {
		async : {
			enable : true, url : path + "tsp/jobconfig/getjobtree.json", autoParam : [ 'id' ],
			otherParam : [ 'planConfigId', planConfigId ], dataFilter : function ( treeId, parentNode, responseData ) {
				return responseData.result;
			}
		}, view : {
			showLine : true, dblClickExpand : true, selectedMulti : false, expandSpeed : "fast"
		}, data : {
			key : {
				name : "name", //设置树节点的name，节点参数name必须和它匹配
				children : "childrenVo"
			}, simpleData : {
				enable : true //开启树的层级结构
			}
		}, edit : {
			enable : true, drag : {
				inner : true, pre : true, next : true
			}, showRemoveBtn : false, showRenameBtn : false
		}, callback : {
			onClick : zTreeClick, onRightClick : zTreeRightClick, beforeDrag : zTreeBeforeDrag,
			beforeDrop : zTreeBeforeDrop, onDrop : zTreeOnDrop
		}
	};

	//树点击
	function zTreeClick ( event, treeId, treeNode ) {
		$.post ( path + "tsp/jobconfig/getjobconfig.json", { id : treeNode.id }, function ( result ) {
			$ ( ".menu-manage-btn" ).text ( treeNode.name );
			var temp = [];
			if ( result.result && $.isArray ( result.result ) ) {
				$ ( result.result ).each ( function ( ind, obj ) {
					temp.push ( "<tr>\
								      <td class=\"menu_table_th function_name\">", obj.text ? obj.text : "", "</td>\
								      <td class=\"menu_table_th function_describe\">", obj.value ? obj.value : "", "</td>\
								      </tr>" );
				} );
				$ ( "table tbody" ).html ( temp.join ( "" ) );
			}
		} );
	}

	var rightClickSelectedNode;
	//树节点右击
	function zTreeRightClick ( e, treeId, treeNode ) {
		var mousePos = mousePosition ( e );
		var windowH = $(window).height();
		console.log ( 'x:' + mousePos.x + ',y:' + mousePos.y );
		var xOffset = 25;
		var yOffset = 40;
		jobId =treeNode.id;
		rightClickSelectedNode = treeNode;
		jobConfigtype=treeNode.jobConfigType;
		if ( treeNode.isParent ) {
			if(treeNode.id=="root"){
				$ ( '.root-menu .fist-menu' ).show ().css ( {
					position : 'absolute', top : mousePos.y - yOffset + 'px', left : mousePos.x + 'px'
				} );
				$ ( '.menu-mask' ).show ();}
			 else{
				if (mousePos.y + 32 > windowH) {
					$('.task-menu .fist-menu').show().css({
						position: 'absolute',
						bottom: '0px',
						left: mousePos.x + 'px'
					});
				} else {
					$('.task-menu .fist-menu').show().css({
						position: 'absolute',
						top: mousePos.y - 40 + 'px',
						left: mousePos.x + 'px'
					});
				}
				//$ ( '.child-parent-menu .fist-menu' ).show ().css ( {
				//	position : 'absolute', top : mousePos.y - yOffset + 'px', left : mousePos.x + 'px'
				//} );
				$ ( '.menu-mask' ).show ();
			}
		} else {
			if (mousePos.y + 130 > windowH) {
					$('.job-menu .fist-menu').show().css({
						position: 'absolute',
						bottom: '0px',
						left: mousePos.x + 15 + 'px'
					});
			} else {
				if(treeNode.jobType=="turnover"){
					$ ( '.turnover-menu .fist-menu' ).show ().css ( {
						position: 'absolute',
						top: mousePos.y - 20 + 'px',
						left: mousePos.x + 15 + 'px'
					} );
				}else {
					$('.job-menu .fist-menu').show().css({
						position: 'absolute',
						top: mousePos.y - 20 + 'px',
						left: mousePos.x + 15 + 'px'
					});
				}
			}
			//$ ( '.child-menu .fist-menu' ).show ().css ( {
			//	position : 'absolute', top : mousePos.y + 'px', left : mousePos.x + 'px'
			//} );
			$ ( '.menu-mask' ).show ();
		}
	}

	//树拖拽后的操作
	function zTreeOnDrop ( event, treeId, treeNodes, targetNode, moveType ) {
		//需要移动的那个节点
		var treeNode = treeNodes[ 0 ];
		//需要移动那个节点的id
		var treeNodeId = treeNodes[ 0 ].id;
		//需要移动节点的父节点
		var treeNodeParent = treeNode.getParentNode ();
		//目标节点的id
		var targetNodeId = targetNode.id;

		//往内部移动
		if ( moveType == 'inner' ) {
			//移动的节点为父节点
			$.ajax ( {
				url : path + 'tsp/jobconfig/addmovenewjob.json', type : 'POST', cache : false,
				data : { treeNodeId : treeNodeId, targetNodeId : targetNodeId, planConfigId : planConfigId },
				success : function ( result ) {
					if ( result.success ) {
						$ ( "table tbody" ).html ( "" );
						zTreeObj.reAsyncChildNodes ( targetNode, "refresh" );
						errmsg ( "向内移动成功", 4000 );
					} else {
						zTreeObj.reAsyncChildNodes ( targetNode, "refresh" );
						errmsg ( "向内移动失败" + result.message, 4000 );
					}
				}, error : function () {
					zTreeObj.reAsyncChildNodes ( targetNode, "refresh" );
					errmsg ( "向内移动错误", 4000 );
				}
			} );
		} else if ( moveType == 'prev' ) {
			$.ajax ( {
				url : path + 'tsp/jobconfig/addmovebeforejob.json', type : 'POST', cache : false,
				data : { treeNodeId : treeNodeId, targetNodeId : targetNodeId, planConfigId : planConfigId },
				success : function ( result ) {
					if ( result.success ) {
						$ ( "table tbody" ).html ( "" );
						zTreeObj.reAsyncChildNodes ( treeNodeParent, "refresh" );
						errmsg ( "向前移动成功", 4000 );
					} else {
						zTreeObj.reAsyncChildNodes ( treeNodeParent, "refresh" );
						errmsg ( "错误信息", "向前移动失败" + result.message, 4000 );
					}
				}, failure : function () {
					zTreeObj.reAsyncChildNodes ( treeNodeParent, "refresh" );
					errmsg ( "向前移动失败", 4000 );
				}
			} );
		} else if ( moveType == 'next' ) {
			$.ajax ( {
				url : path + 'tsp/jobconfig/addmoveafterjob.json', type : "POST", cache : false,
				data : { treeNodeId : treeNodeId, targetNodeId : targetNodeId, planConfigId : planConfigId },
				success : function ( result ) {
					if ( result.success ) {
						$ ( "table tbody" ).html ( "" );
						zTreeObj.reAsyncChildNodes ( treeNodeParent, "refresh" );
						errmsg ( "向后移动成功", 4000 );
					} else {
						zTreeObj.reAsyncChildNodes ( treeNodeParent, "refresh" );
						errmsg ( "错误信息", "向后移动失败" + result.message, 4000 );
					}
				}, error : function () {
					zTreeObj.reAsyncChildNodes ( treeNodeParent, "refresh" );
					errmsg ( "向后移动失败", 4000 );
				}
			} );
		}
	}

	//树拖之前的判断
	function zTreeBeforeDrag ( event, treeId, treeNodes ) {
		if ( treeId[ 0 ].id == 'root' ) {
			return false;
		}
	}

	//树拖之后放下之前的判断
	function zTreeBeforeDrop ( treeId, treeNodes, targetNode, moveType ) {
		//如果目标节点的id为root，说明为最开始的根节点，禁止向其最前和最后插入
		var targetNodeId = targetNode.id;
		if ( targetNodeId == 'root' ) {
			return false;
		} else if ( moveType == 'inner' ) {
			if ( targetNode.jobConfigType == "serial" || targetNode.jobConfigType == "parallel" ||
				 targetNode.jobConfigType == "root" ) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/*//选择树右键菜单 li 颜色
	$ ( '.tree-menu .list-group-item' ).on ( 'click', function () {
		$ ( this ).addClass ( 'active-li' ).siblings ().removeClass ( 'active-li' );
	 } );*/

	//鼠标经过出现子菜单
	$('.tree-menu .list-group-item').on({
		mouseenter: function () {
			var eleTop = $(this).offset().top;
			var cldH = $(this).children('.menu-box').height();
			var eleNUm = $(this).siblings('.list-group-item').length;
			var eleIndex = $(this).index();
			var windowH = $(window).height();
			if (eleTop + cldH > windowH) {
				if (eleNUm == 0) {
					$(this).children('.menu-box').show().css({
						position: 'absolue',
						bottom: '-20px',
						top: '',
						left: '80px'
					});
				} else {
					$(this).children('.menu-box').show().css({
						position: 'absolue',
						bottom: (eleIndex * 32 - 115) + 'px',
						top: '',
						left: '80px'
					});
				}
			} else {   //正常显示
				$(this).children('.menu-box').show().css({
					position: 'absolute',
					top:'0',
					left: '78px'
				});
			}
		},
		mouseleave: function () {
			$(this).children('.menu-box').hide();
		}

	})

	$('.menu-mask').on('click', function () {
		$('.menu-mask').hide();
		$('.fist-menu').hide();
		$('.fist-menu,.secod-menu,.thid-menu').css({
			position: 'absolute',
			top: '',
			bottom: ''
		});
	});

	$ ( window ).on ( 'scroll', function () {
		$ ( '.fist-menu' ).hide ();
	} );

	zTreeObj = $.fn.zTree.init ( $ ( '.ztree' ), jobTreeOption );


	$ ( '[data-height="auto"]' ).each ( function () {
		var parent = $ ( this ).parent ();
		var bro = $ ( this ).siblings ();
		var broHeight = 0;
		$ ( bro ).each ( function () {
			var everyHeight = $ ( this ).outerHeight ();
			broHeight += everyHeight;
		} );
		$ ( this ).css ( {
			'box-sizing' : 'border-box', 'width' : '100%', 'height' : parent.height () - broHeight,
			'overflow-y' : 'auto', 'overflow-x' : 'hidden'
		} )
	} );

	$ ( document ).on ( 'click', '#fold-btn', foldAside );


	// 收缩侧边栏方法
	var toggle = true;

	function foldAside () {
		var root = $ ( this ).parents ( '.wlk-grail' );

		if ( toggle ) {
			toggle = false;
			$ ( this ).html ( '<i class="iconfont icon-rtarrow"></i>' );
			root.animate ( {
				'padding-left' : '30px'
			} )
		} else {
			toggle = true;
			$ ( this ).html ( '<i class="iconfont icon-lfarrow"></i>' );
			root.animate ( {
				'padding-left' : '230px'
			} )

		}

	}

	/*删除单个job*/
	$ ( '.deletejob' ).on ( 'click', function () {
		$ ( '.fist-menu' ).hide ();
		layer.alert ( '确定删除吗?', {
			icon : 2, btn : [ '确认', '取消' ], yes : function ( index ) {
				$.post ( path + '/tsp/jobconfig/deletejobconfig.json', {
					id : jobId,
					planConfigId:planConfigId
				}, function ( result ) {
					if ( result[ 'success' ] ) {
						layer.close ( index );
						layer.alert ( "删除成功" );
						$ ( "table tbody" ).html ( "" );
						zTreeObj.reAsyncChildNodes ( rightClickSelectedNode.getParentNode (), "refresh" );
					}else{
						layer.close ( index );
						errmsg (result['message']);
					}
				} );
			}
		} );

	} );

	/*获取分享的计划*/
	$.ajax ( {
		url : path + "tsp/jobconfig/getplanconfigs.json", data : {id:planConfigId}, type : "post", async : true,
		success : function ( data ) {
			if ( data.success ) {
				var tplC = [ "{@each result as it}", "<option value='${it.value}'>${it.text}</option>",
					"{@/each}" ].join ( "" );
				var contentC = juicer ( tplC, data );
				$ ( "#call-plan-config-id" ).html ( contentC );
			} else {
				errmsg ( "获取通知方式列表失败！",3 );
			}
		}, error : function ( data ) {
			errmsg ( "获取通知方式列表失败，通讯异常！" ,3);
		}
	} );

	/*设置错误提示信息自动消失方法*/
	function errmsg ( content, delay ) {
		$ ( '.error-msg.alert' ).addClass ( 'show' );
		$ ( '.error-msg strong' ).text ( content );
		setTimeout ( function () {
			$ ( '.error-msg.alert' ).removeClass ( 'show' );
			$ ( '.error-msg.alert' ).alert ();
		}, delay )

	}

	//扩展字符串endWith方法
	String.prototype.endWith = function ( str ) {
		var reg = new RegExp ( str + "$" );
		return reg.test ( this );
	}

	//数据库资源库(1);文件资源库(2);本地文件(3)
	$(".selectkjb").bind("change", function () {
		if ($(this).val() == 1) {
			$(".kjb_file").attr("disabled", true);
			$(".kjb_rep").attr("disabled", false);
			$(".kjb_user").attr("disabled", false);
			$(".kjb_pass").attr("disabled", false);
			$(".kjb_dir").attr("disabled", false);
			$(".kjb_jobs").attr("disabled", false);
			$(".kjb_file").val("");
		} else if ($(this).val() == 2) {
			$(".kjb_user").attr("disabled", true);
			$(".kjb_pass").attr("disabled", true);
			$(".kjb_file").attr("disabled", true);
			$(".kjb_rep").attr("disabled", false);
			$(".kjb_dir").attr("disabled", false);
			$(".kjb_jobs").attr("disabled", false);
			$(".kjb_user").val("");
			$(".kjb_pass").val("");
			$(".kjb_file").val("");
		} else {
			$(".kjb_rep").attr("disabled", true);
			$(".kjb_user").attr("disabled", true);
			$(".kjb_pass").attr("disabled", true);
			$(".kjb_dir").attr("disabled", true);
			$(".kjb_jobs").attr("disabled", true);
			$(".kjb_file").attr("disabled", false);
			$(".kjb_rep").val("");
			$(".kjb_user").val("");
			$(".kjb_pass").val("");
			$(".kjb_dir").val("");
			$(".kjb_jobs").val("");
		}
	});

	$(".selectktr").bind("change", function () {
		if ($(this).val() == 1) {
			$(".ktr_file").attr("disabled", true);
			$(".ktr_rep").attr("disabled", false);
			$(".ktr_user").attr("disabled", false);
			$(".ktr_pass").attr("disabled", false);
			$(".ktr_dir").attr("disabled", false);
			$(".ktr_trans").attr("disabled", false);
			$(".ktr_file").val("");
		} else if ($(this).val() == 2) {
			$(".ktr_user").attr("disabled", true);
			$(".ktr_pass").attr("disabled", true);
			$(".ktr_file").attr("disabled", true);
			$(".ktr_rep").attr("disabled", false);
			$(".ktr_dir").attr("disabled", false);
			$(".ktr_trans").attr("disabled", false);
			$(".ktr_user").val("");
			$(".ktr_pass").val("");
			$(".ktr_file").val("");
		} else {
			$(".ktr_rep").attr("disabled", true);
			$(".ktr_user").attr("disabled", true);
			$(".ktr_pass").attr("disabled", true);
			$(".ktr_dir").attr("disabled", true);
			$(".ktr_trans").attr("disabled", true);
			$(".ktr_file").attr("disabled", false);
			$(".ktr_rep").val("");
			$(".ktr_user").val("");
			$(".ktr_pass").val("");
			$(".ktr_dir").val("");
			$(".ktr_trans").val("");
		}
	});

	//新增kjb重置disabled属性
	function addkjb() {
		$(".kjb_file").attr("disabled", true);
		$(".kjb_rep").attr("disabled", false);
		$(".kjb_user").attr("disabled", false);
		$(".kjb_pass").attr("disabled", false);
		$(".kjb_dir").attr("disabled", false);
		$(".kjb_jobs").attr("disabled", false);
	}

    //新增ktr重置disabled属性
	function addktr() {
		$(".ktr_file").attr("disabled", true);
		$(".ktr_rep").attr("disabled", false);
		$(".ktr_user").attr("disabled", false);
		$(".ktr_pass").attr("disabled", false);
		$(".ktr_dir").attr("disabled", false);
		$(".ktr_trans").attr("disabled", false);
	}
} );