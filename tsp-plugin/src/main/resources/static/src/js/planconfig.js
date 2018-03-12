var path = $ ( '#path' ).val ();
var baseUri = $ ( '#baseUri' ).val ();
$ ( function () {

	$ ( 'tr' ).dblclick ( function ( e ) {
		window.open ( path + '/tsp/jobconfig/getjobpage' + '?id=' + $ ( this ).data ( "id" ) ,'_self');
	} );

	$('.dtpicker').datetimepicker({
		language:'zh-CN',
		format:'yyyy-mm-dd',
		todayHighlight:true,
		autoclose:true,
		startView:2,
		minView:2,
		todayBtn:'linked'

	});

	/* 新增弹出框 */
	$ ( '#plan-add' ).on ( 'click', function ( e ) {
		layer.open ( {
			type : 1, title : '新增计划配置', area : '566px', content : $ ( '#modal-edit' ),
			success : function ( dom, index ) {
				$ ( '#form-edit' ).find ( 'input' ).each ( function ( index, input ) {
					$ ( input ).val ( '' );
				} );
			}, btn : [ '确认', '取消' ], yes : function ( index ) {
				//$ ( '#form-edit' ).submit();

				if ( planConfigValidate () ) {
					$.post ( path + '/tsp/planconfig/add.json', $ ( '#form-edit' ).serialize (),
							 function ( result ) {
								 if ( result[ 'success' ] ) {
									 layer.close ( index );
									 errmsg ( "新增计划配置成功", 4000 );
									 $('#data-query').click();
								 } else {
									 layer.alert ( "新增计划配置失败：原因是" + result.message );
								 }
							 } );
				}
			}, btn2 : function ( index ) {
				layer.close ( index );
			}
		} )
	} );




	/* 删除弹出框 */
	$ ( '#plan-delete' ).on ( 'click', function ( e ) {
		var ids=[];
		$(".menu_table-checkbox").each(function(){
			var me=this;
			if($(me).prop("checked")){
				var id=$(me).parents('tr').attr("data-id");
				ids.push(id);
			}
		})
		if(null==ids||ids.length==0){
			layer.alert("请至少选择一条进行审核");
			return;
		}
			layer.alert('确定删除吗?', {
				icon: 2, btn: ['确认', '取消'], yes: function (index) {
					$.post(path + '/tsp/planconfig/delete.json', {
						planConfigId: ids.join(",")
					}, function (result) {
						if (result['success']) {
							layer.close(index);
							$('#data-query').click();
						}
					});
				}
			});
	} );

	$ ( '.config_delete' ).on ( 'click', function ( e ) {
		var id=$(this).parents('tr').attr("data-id");
		var ids=[id];
		layer.alert('确定删除吗?', {
			icon: 2, btn: ['确认', '取消'], yes: function (index) {
				$.post(path + '/tsp/planconfig/delete.json', {
					planConfigId: ids.join(",")
				}, function (result) {
					if (result['success']) {
						layer.close(index);
						$('#data-query').click();
					}
				});
			}
		});
	} );

	/*表格全选功能*/
	$ ( '.choose_all' ).click ( function () {
		var checkel = $ ( '.menu_table-checkbox' );
		if ( this.checked ) {
			checkel.prop ( 'checked', true )
		} else {
			checkel.prop ( 'checked', false )
		}
	} );

	//生成作业配置
	$ ( '#plan-toconfig' ).click ( function () {
		layer.open ( {
			zIndex : '10', type : 1, title : '生成作业树窗口', area : '566px', content : $ ( '#to-plan' ),
			success : function ( dom, index ) {
			}, btn : [ '确认', '取消' ], yes : function ( index ) {
				$ ( '#form-toplan' ).submit ();
			}, btn2 : function ( index ) {
				layer.close ( index );
			}
		} )
	} );



	var orgOption = {

		hideId : "recipients", showId : "recipientsCn", treeSettings : {
			check : {
				chkboxType : { "Y" : "s", "N" : "s" }, chkStyle : "checkbox", enable : true
			}, async : {
				url : path + "/tsp/planconfig/getrecipients.json", enable : true, autoParam : [ "id", "count" ],
				dataFilter : function ( treeId, parentNode, responseData ) {
					var names=$("#"+orgtree.showId).val();
					var ids=$("#"+orgtree.hideId).val();
					if(parentNode&&parentNode.checked){
						for(var i=0;i<responseData.length;i++){
							responseData[i].checked=true;
							console.log(responseData[i].id);
							if( !responseData[i].isParent){
								names+=(responseData[i].name+",");
								ids+=(responseData[i].id+",");
							}
						}
					}
					$("#"+orgtree.showId).val(names);
					var str=$("#"+orgtree.hideId).val(ids);
					return responseData;
				}
			}, callback : {
				onClick : function ( e, treeId, treeNode ) {
					onClicks ( e, treeId, treeNode, function () {
						/*console.log(treeNode)*/
					} )
				}, onCheck : function ( e, treeId, treeNode ) {
					onChecks ( e, treeId, treeNode, function () {
						console.log ( orgtree.getval () )
					} )
				}
			}
		}
	};
	$ ( '.form-edit' ).on ( "submit", function () {
		return false;
	} );

	var orgtree = new Stree ( orgOption );


	//重置按钮情况
	$('.btn-reset').on("click",function(){
		$('#name_qo').val('');
		$('#startTimeBelow_qo').val('');
		$('#startTimeTop_qo').val('');
		$('#status_qo').val('');
		$('#endTimeBelow_qo').val('');
		$('#endTimeTop_qo').val('');
	});

	function GetQueryString ( name ) {
		var reg = new RegExp ( "(^|&)" + name + "=([^&]*)(&|$)" );
		var r = window.location.search.substr ( 1 ).match ( reg );
		if ( r != null ) {
			return decodeURIComponent ( r[ 2 ] );
		}
		return null;
	}

	var message = (GetQueryString ( "message" ));
	if ( message ) {
		layer.alert ( message );
	}
} );

/*详情弹出框*/
function showDetail ( id ) {
	layer.open ( {
		type : 1, title : '查看详情', area : '566px', content : $ ( '#modal-detail' ), btn : [ '确认' ],
		yes : function ( index ) {
			layer.close ( index );
		}
	} );
	$ ( "#form-detail input[name='name']" ).val ( $ ( '#' + id + '-name' ).text () );
	$ ( "#form-detail input[name='recipientsCn']" ).val ( $ ( '#' + id + '-recipientsCn' ).text () );
	$ ( "#form-detail input[name='noticeWay']" ).val ( $.trim ( $ ( '#' + id + '-noticeWay' ).text () ) );
	$ ( "#form-detail input[name='period']" ).val ( $ ( '#' + id + '-period' ).text () );
	$ ( "#form-detail input[name='isShare']" ).val ( $.trim ( $ ( '#' + id + '-isShare' ).text () ) );
	$ ( "#form-detail input[name='createUserName']" ).val ( $ ( '#' + id + '-createUser' ).text () );
	$ ( "#form-detail input[name='createTime']" ).val ( $ ( '#' + id + '-createTime' ).text () );
	$ ( "#form-detail input[name='startUserName']" ).val ( $ ( '#' + id + '-startUser' ).text () );
	$ ( "#form-detail input[name='startTime']" ).val ( $ ( '#' + id + '-startTime' ).text () );
	$ ( "#form-detail input[name='stopUserName']" ).val ( $ ( '#' + id + '-stopUser' ).text () );
	$ ( "#form-detail input[name='stopTime']" ).val ( $ ( '#' + id + '-stopTime' ).text () );
	$ ( "#form-detail input[name='desc']" ).val ( $ ( '#' + id + '-desc' ).text () );
}

/*修改弹出框*/
function editPlan ( id ) {
	layer.open ( {
		type : 1, title : '修改', area : '566px', content : $ ( '#modal-edit' ), btn : [ '确认', '取消' ],
		yes : function ( index ) {
			if ( planConfigValidate () ) {
				$.post ( path + '/tsp/planconfig/update.json', $ ( '#form-edit' ).serialize (), function ( result ) {
					if ( result[ 'success' ] ) {
						layer.close ( index );
						errmsg ( "修改成功", 4000 );
						search ();
					} else {
						layer.alert ( "修改失败：原因是" + result.message, 4000 );
					}
				} );
			}
		}, btn2 : function ( index ) {
			layer.close ( index );
		}
	} );
	$ ( "input[name='id']" ).val ( id );
	$ ( '.name' ).val ( $ ( '#' + id + '-name' ).text () );
	$ ( '.recipientsCn' ).val ( $ ( '#' + id + '-recipientsCn' ).text () );
	var noticeWay = $.trim ( $ ( '#' + id + '-noticeWay' ).text () );
	$ ( ".noticeWay" ).find ( "option" ).each ( function () {
		if ( this.label == noticeWay ) {
			$ ( this ).attr ( "selected", true );
		} else {
			$ ( this ).attr ( "selected", false );
		}
	} );
	$ ( '.period' ).val ( $ ( '#' + id + '-period' ).text () );
	var isShare = $.trim ( $ ( '#' + id + '-isShare' ).text () );
	$ ( ".isShare" ).find ( "option" ).each ( function () {
		if ( this.label == isShare ) {
			$ ( this ).attr ( "selected", true );
		} else {
			$ ( this ).attr ( "selected", false );
		}
	} );
	$ ( '.desc' ).val ( $ ( '#' + id + '-desc' ).text () );
}

/*删除单个弹出框*/
function deleteOnePlan ( id ) {
	layer.alert ( '确定删除吗?', {
		icon : 2, btn : [ '确认', '取消' ], yes : function ( index ) {
			$.post ( path + '/tsp/planconfig/delete.json', {
				planConfigId : id
			}, function ( result ) {
				if ( result[ 'success' ] ) {
					layer.close ( index );
					errmsg ( "删除成功", 4000 );
					search ();
				} else {
					layer.alert ( "删除失败：原因是" + result.message );
				}
			} );
		}
	} );
}

/*发布弹出框*/
function releasePlan ( id ) {
	var a = true;
	layer.alert ( '确定发布吗?', {
		icon : 1, btn : [ '确认', '取消' ], yes : function ( index, layero ) {
			if ( a ) {
				//防止重复点击
				a = false;
				$.post ( path + '/tsp/planconfig/getjobs.json', {
					id : id
				}, function ( result ) {
					if ( result.success ) {
						$.post ( path + '/tsp/planconfig/release.json', {
							id : id
						}, function ( result ) {
							if ( result[ 'success' ] ) {
								layer.close ( index );
								errmsg ( "发布成功", 4000 );
							} else {
								layer.alert ( "发布失败：原因是" + result.message );
							}
						} );
					} else {
						layer.alert ( "发布失败：原因是" + result.message, 4000 );
					}
				} );
			}
		}
	} );
}

/*查看版本*/
function showVersion ( id ) {
	layer.open ( {
		type : 1, title : '查看版本', area : '1000px', content : $ ( '#version-detail' ),
		success : function ( dom, index ) {
			$ ( "#version-body" ).children ( "tr" ).remove ();
			$.post ( path + '/tsp/plan/list.json', {
				'planConfig.id' : id
			}, function ( result ) {
				if ( result[ 'success' ] ) {
					var planVos = result[ 'result' ];
					for ( var i = 0; i < planVos.length; i ++ ) {
						var planVo = planVos[ i ];
						$ ( "#version-body" ).append ( "<tr><td class='hide'>" + planVo.id + "</td>" + "<td>" +
													   (i + 1) + "</td>" + "<td>" + planVo.name + "</td>" + "<td>" +
													   planVo.recipientsCn + "</td>" + "<td>" + planVo.noticeWay +
													   "</td>" + "<td>" + planVo.period + "</td>" + "<td>" +
													   planVo.deployTime + "</td>" + "<td>" + planVo.deployUser +
													   "</td>" + "<td>" + planVo.version + "</td></tr>" );
					}
				}
			} )
		}
	} )
}

/*运行按钮*/
function runPlan ( id ) {
	layer.open ( {
		zIndex : '10', type : 1, title : '运行', area : '566px', content : $ ( '#runtime-edit' ),
		success : function ( dom, index ) {
			$ ( '#form-runtime' ).children ( "input" ).val ( id );
		}, btn : [ '确认', '取消' ], yes : function ( index ) {
			$.post ( path + '/tsp/planconfig/restartplan.json', $ ( '#form-runtime' ).serialize (),
					 function ( result ) {
						 if ( result[ 'success' ] ) {
							 layer.close ( index );
							 layer.msg ( "运行成功" );
							 search ();
						 } else {
							 layer.close ( index );
							 errmsg ( result[ 'message' ] );
						 }
					 } );
		}, btn2 : function ( index ) {
			layer.close ( index );
		}
	} )
}

function jobConfig(id){
	window.open ( path + '/tsp/jobconfig/getjobpage' + '?id=' + id ,'_self');
}

/*启动按钮*/
function startPlan ( id ) {
	$.post ( path + '/tsp/planconfig/startplan.json', {
		id : id
	}, function ( result ) {
		if ( result[ 'success' ] ) {
			errmsg ( "启动成功", 4000 );
			search ();
		} else {
			layer.alert ( "启动失败，原因是：" + result[ 'message' ] );
		}
	} )
}

/*停止按钮*/
function stopPlan ( id ) {
	$.post ( path + '/tsp/planconfig/stopplan.json', {
		planConfigId : id
	}, function ( result ) {
		if ( result[ 'success' ] ) {
			layer.msg ( "计划停止成功" );
			search ();
		} else {
			errmsg ( result[ 'message' ] );
		}
	} );
}

/*设置错误提示信息自动消失方法*/
function errmsg ( content, delay ) {
	$ ( '.error-msg.alert' ).addClass ( 'show' );
	$ ( '.error-msg strong' ).text ( content );
	setTimeout ( function () {
		$ ( '.error-msg.alert' ).removeClass ( 'show' );
		$ ( '.error-msg.alert' ).alert ();
	}, delay )

}

function search () {
	$('#data-query').click();
}

/*新增修改验证*/
function planConfigValidate () {
	if ( $ ( "#form-edit input[name='name']" ).val () == "" ) {
		errmsg ( "计划名称不能为空", 4000 );
		return false;
	}
	return true;
}