var path = $ ( '#path' ).val ();
var baseUri = $ ( '#baseUri' ).val ()
//刷新页面
function refresh(){
	search();  //重新查询
}
function search(){
	$("#query_jobmonitor_form").submit();
}
/**
 * 消息框
 /*设置错误提示信息自动消失方法*/
function errmsg(content, delay) {
	$(".error-msg.alert").addClass("show");
	$(".error-msg strong").text(content);
	setTimeout(function () {
		$(".error-msg.alert").removeClass("show");
		$(".error-msg.alert").alert();
	}, delay)

};
$(function () {
	//日历选择
	$('.dtpicker').datetimepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd',
		todayHighlight: true,
		autoclose: true,
		startView: 2,
		minView: 2,
		todayBtn: 'linked'

	});

	//状态属性选择
	$('.sel-state li').on('click', function () {
		var val = $(this).text();
		console.log(val);
		$('.sel-val').text(val);
	});

	//table tr选中
	var selected = "";
	$('.data-tb tbody tr').on('click', function () {
		$(this).addClass('data-avtive').siblings().removeClass('data-avtive');
		selected = $(this)[0].children[0].innerText;
	});

	/*日志详情*/
	$(".log-dtl").on("click", function () {
		var hostname = $(this)[0].children[0].innerText;
		var planInstId = $(this)[0].children[1].innerText;
		var jobId = $(this)[0].children[2].innerText;
		$.ajax({
			url: path + "/tsp/jobmonitor/getjoblog.json",
			data: {
				hostname: hostname, planInstId: planInstId, jobId: jobId
			},
			type: "post",
			async: true,
			success: function (result) {
				if (result['success']) {
					$('#joblog').text(result['result'] || "无日志记录")
				} else {
					$('#joblog').text(result['message']);
				}
				layer.open({
					type: 1,
					title: "日志详情",
					area: "566px",
					content: $(".modal-log").html(),
					btn: ["关闭"],
					yes: function (index) {
						layer.close(index)
					}
				})
			}, error: function (data) {
				errmsg("通信失败", 4000);
			}
		});


	});
	/*表格全选功能*/
	$('.choose_all').click(function () {
		var checkel = $('.menu_table-checkbox');
		if (this.checked) {
			checkel.prop('checked', true)
		} else {
			checkel.prop('checked', false)
		}
	});


	// $('.re_start').bind('click', reStart);
	// $('.manual_pass').bind('click', manualPass);
	// $('.stop_job').bind('click', stopJob);
	// $('.pause_job').bind('click', pauseJob);
	// $('.continue_job').bind('click', continueJob);

	//重置按钮情况
	$('.btn-reset').on("click", function () {
		$('#p_name').val('');
		$('#startBefore_Time').val('');
		$('#startEnd_Time').val('');
		$('#endBefore_Time').val('');
		$('#endEnd_Time').val('');
		$('#status').val('');

	});

	$ ( document ).on ( 'click', '#fold-btn', foldAside );


	// 收缩侧边栏方法
	var toggle = true;

	function foldAside () {
		var root = $ ( this ).parents ( '.wlk-grail-list' );

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
				'padding-left' : '280px'
			} )

		}

	}




	//zTree使用
	var zTreeObj;


	var jobTreeOption = {
		view : {
			showLine : true, dblClickExpand : true, selectedMulti : false, expandSpeed : "fast"
		}, data : {
			key : {
				name : "name", //设置树节点的name，节点参数name必须和它匹配
				children : "children"
			}, simpleData : {
				enable : true //开启树的层级结构
			}
		},callback : {
			onClick : zTreeOnClick
		}
	};

	$.ajax({
		url : path + "tsp/jobmonitor/getplantree.json",
		dataType : "json",
		type : "post",
		success : function(result){
			if(result.success){
				zTreeObj = $.fn.zTree.init ( $ ( '.ztree' ), jobTreeOption,result.result );
			}
		}
	});
	//树点击
	function zTreeOnClick ( event, treeId, treeNode ) {
		$.post ( path + "tsp/jobmonitor/list.json", { pName : treeNode.name,id:treeNode.id }, function ( result ) {
			$ ( "table tbody" ).html("");
		    var temp = [];
		    if ( result.result && $.isArray ( result.result ) ) {
		        $ ( result.result ).each ( function ( ind, obj ) {
					ind=ind+1;
		            temp.push ( "<tr>\
		                          <td>"+ind+"</td>\
		                          <td id=\"" + obj.id + "-id\" class=\"hide\">" + obj.id + "</td>\
							      <td id=\"" + obj.id + "-planinstid\" class=\"hide\">" + obj.planInstId + "</td>\
							      <td id=\"" + obj.id + "-runhostname\" class=\"hide\">" + obj.runHostname + "</td>\
							      <td id=\"" + obj.id + "-jobid\" class=\"hide\">" + obj.jobId + "</td>\
							      <td id=\"" + obj.id + "-pName\">" + obj.pName + "</td>\
							      <td id=\"" + obj.id + "-jName\">" + obj.jName + "</td>\
                                  <td id=\"" + obj.id + "-runHostname\">" + obj.runHostname + "</td>\
                                  <td id=\"" + obj.id + "-status\">" + obj.status + "</td>\
                                  <td id=\"" + obj.id + "-startTime\">" + obj.startTime + "</td>\
                                  <td id=\"" + obj.id + "-endTime\">" + obj.endTime + "</td>\
                                  <td><a href=\"javascript:reStart('"+obj.id+"')	;\">重新运行</a>\
							          <a href=\"javascript:manualPass('"+obj.id+"');\">强制通过</a>\
							          <a href=\"javascript:pauseJob('"+obj.id+"');\">暂停</a>\
							          <a href=\"javascript:stopJob('"+obj.id+"');\">停止</a>\
							          <a href=\"javascript:continueJob('"+obj.id+"');\">继续运行</a>\
							          <a id=\""+obj.id+"-log-dtl\" href=\"#\" class=\"log-dtl\">\
							               <li class=\"hide hostname\">"+obj.runHostname+"</li>\
					                       <li class=\"hide planInstId\">"+obj.planInstId+"</li>\
				                           <li class=\"hide jobId\">"+obj.jobId+"</li>查看日志</a>\
				                  </td>\
                                 </tr>");
		        } );
		        $ ( "table tbody" ).html ( temp.join ( "" ) );
		    }
		} );
		//$ ( "#p_name" ).val ( treeNode.name );
		//$("#query_jobmonitor_form").submit();
	}


})
//重新运行
function reStart( id ) {
	var status = $('#'+id+'-status').text();
	if(status!='运行失败'){
		errmsg("运行失败的才能重新运行",4000);
		return;
	}
	var planinstid = $('#'+id+'-planinstid').text();
	var jobid = $('#'+id+'-jobid').text();
	$.ajax({
		url :path+ "/tsp/jobmonitor/restart.json",
		data: {
			planInstId:planinstid,
			jobId:jobid
		},
		type: "post",
		async: true,
		success: function (result) {
			if(result.success){
				errmsg("重新运行成功",4000);
				refresh();
			}else{
				errmsg("重新运行失败：原因是"+result.message,4000);
			}
		}, error: function (data) {
			errmsg("通信失败",4000);
		}
	});


}
//强制通过
function manualPass( id ) {
	var status = $('#'+id+'-status').text();
	if(status!='运行失败'){
		errmsg("请选择可以强制通过作业",4000);
		return
	}
	$.ajax({
		url :path+ "/tsp/jobmonitor/manualpass.json",
		data: {
			id:id,
		},
		type: "post",
		async: true,
		success: function (result) {
			if(result.success){
				errmsg("强制通过成功",4000);
				refresh();
			}else{
				errmsg("强制通过失败:原因"+result.message,4000);
			}
		}, error: function (data) {
			errmsg("通信失败",4000);
		}
	});


}
//停止作业
function  stopJob( id ) {
	var status = $('#'+id+'-status').text();
	if(status!='正在运行'){
		errmsg("请选择可以停止的作业",4000);
		return
	}
	var runhostname = $('#'+id+'-runhostname').text();
	var jobid = $('#'+id+'-jobid').text();
	$.ajax({
		url :path+ "/tsp/jobmonitor/stop.json",
		data: {
			runHostname:runhostname,
			jobId:jobid,
			id:id,
		},
		type: "post",
		async: true,
		success: function (result) {
			if(result.success){
				errmsg("停止成功",4000);
				refresh();
			}else{
				errmsg("停止失败:原因"+result.message,4000);
			}
		}, error: function (data) {
			errmsg("通信失败",4000);
		}
	});


}
//暂停作业
function pauseJob( id ) {
	var status = $('#'+id+'-status').text();
	if(status!='正在运行'){
		errmsg("请选择可以暂停的作业",4000);
		return
	}
	var runhostname = $('#'+id+'-runhostname').text();
	var jobid = $('#'+id+'-jobid').text();
	$.ajax({
		url :path+ "/tsp/jobmonitor/pause.json",
		data: {
			runHostname:runhostname,
			jobId:jobid,
			id:id,
		},
		type: "post",
		async: true,
		success: function (result) {
			if(result.success){
				errmsg("暂停成功",4000);
				refresh();
			}else{
				errmsg("暂停失败:原因"+result.message,4000);
			}
		}, error: function (data) {
			errmsg("通信失败",4000);
		}
	});


}
//继续运行作业
function continueJob( id ) {
	var status = $('#' + id + '-status').text();
	if (status != '暂停' || status != '停止') {
		errmsg("请选择可以继续运行的作业", 4000);
		return
	}
	var runhostname = $('#' + id + '-runhostname').text();
	var jobid = $('#' + id + '-jobid').text();
	$.ajax({
		url: path + "/tsp/jobmonitor/continue.json",
		data: {
			runHostname: runhostname,
			jobId: jobid,
			id: id,
		},
		type: "post",
		async: true,
		success: function (result) {
			if (result.result == true) {
				errmsg("继续运行成功", 4000);
				refresh();
			} else {
				errmsg("继续运行失败", 4000);
			}
		}, error: function (data) {
			errmsg("通信失败", 4000);
		}
	});
}
