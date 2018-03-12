var path = $('#path').val();
var contextPath = $("#contextPath").val();

function errmsg(content, delay) {
    $(".error-msg.alert").addClass("show");
    $(".error-msg strong").text(content);
    setTimeout(function () {
        $(".error-msg.alert").removeClass("show");
        $(".error-msg.alert").alert();
    }, delay)

};

function search() {
    $("#query_planmonitor_form").submit();
}

//刷新页面
function refresh() {
    search();  //重新查询
}

$(function () {
    /*设置错误提示信息自动消失方法*/
    function errmsg(content, delay) {
        $(".error-msg.alert").addClass("show");
        $(".error-msg strong").text(content);
        setTimeout(function () {
            $(".error-msg.alert").removeClass("show");
            $(".error-msg.alert").alert();
        }, delay)

    };

    /*表格全选功能*/
    $('.check-all').click(function () {
        var checkel = $('.qurey-result table .check-ls');
        if (this.checked) {
            checkel.prop('checked', true)
        } else {
            checkel.prop('checked', false)
        }
    })

    $('.dtpicker').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        autoclose: true,
        startView: 2,
        minView: 2,
        todayBtn: 'linked'

    });
    //重置按钮情况
    $('.btn-reset').on("click", function () {
        $('#p_name').val('');
        $('#startTime_Top').val('');
        $('#startTime_Below').val('');
        $('#start_BatchNo').val('');
        $('#endTime_Top').val('');
        $('#endTime_Below').val('');
        $('#end_BatchNo').val('');
        $('#status').val('');
    });

    $(document).on('click', '#fold-btn', foldAside);


    // 收缩侧边栏方法
    var toggle = true;

    function foldAside() {
        var root = $(this).parents('.wlk-grail-list');

        if (toggle) {
            toggle = false;
            $(this).html('<i class="iconfont icon-rtarrow"></i>');
            root.animate({
                'padding-left': '30px'
            })
        } else {
            toggle = true;
            $(this).html('<i class="iconfont icon-lfarrow"></i>');
            root.animate({
                'padding-left': '280px'
            })

        }

    }

    //zTree使用
    var zTreeObj;
    var jobTreeOption = {
        async: {
            enable: true, url: path + "tsp/planmonitor/getplantree.json", autoParam: ['id'],
            dataFilter: function (treeId, parentNode, responseData) {
                return responseData.result;
            }
        }, view: {
            showLine: true, dblClickExpand: true, selectedMulti: false, expandSpeed: "fast"
        }, data: {
            key: {
                name: "name", //设置树节点的name，节点参数name必须和它匹配
                children: "childrenVo"
            }, simpleData: {
                enable: true //开启树的层级结构
            }
        }, edit: {
            enable: true, drag: {
                inner: true, pre: true, next: true
            }, showRemoveBtn: false, showRenameBtn: false
        }, callback: {
            onClick: zTreeOnClick
        }
    };

    zTreeObj = $.fn.zTree.init($('.ztree'), jobTreeOption);

    //树点击
    function zTreeOnClick(event, treeId, treeNode) {
        //$.post ( path + "tsp/planmonitor/list.json", { pname : treeNode.name }, function ( result ) {
        //    $ ( ".menu-manage-btn" ).text ( treeNode.name );
        //    var temp = [];
        //    if ( result.result && $.isArray ( result.result ) ) {
        //        $ ( result.result ).each ( function ( ind, obj ) {
        //            temp.push ( "<tr>\
        //					      <td class=\"menu_table_th function_name\">", obj.text ? obj.text : "", "</td>\
        //					      <td class=\"menu_table_th function_describe\">", obj.value ? obj.value : "", "</td>\
        //					      </tr>" );
        //        } );
        //        $ ( "table tbody" ).html ( temp.join ( "" ) );
        //    }
        //} );
        $("#p_name").val(treeNode.name);
        $("#query_planmonitor_form").submit();
    }

    $('[data-height="auto"]').each(function () {
        var parent = $(this).parent();
        var bro = $(this).siblings();
        var broHeight = 0;
        $(bro).each(function () {
            var everyHeight = $(this).outerHeight();
            broHeight += everyHeight;
        });
        $(this).css({
            'box-sizing': 'border-box', 'width': '100%', 'height': parent.height() - broHeight,
            'overflow-y': 'auto', 'overflow-x': 'hidden'
        })
    });

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
                    top: '0',
                    left: '78px'
                });
            }
        },
        mouseleave: function () {
            $(this).children('.menu-box').hide();
        }

    })


});
//结束
function endPlan(id) {
    var status = $('#' + id + '-status').text();
    if (status.trim() != '暂停' && status.trim() != '正在运行') {
        errmsg("已经结束", 4000);
        return
    }
    var planinstid = $('#' + id + '-planinstid').text();
    $.ajax({
        url: path + "/tsp/planmonitor/endplan.json",
        dataType: "json",
        type: "post",
        async: false,
        data: {
            planInstId: planinstid
        },
        success: function (result) {
            if (result.success) {
                if (result.success) {
                    errmsg("结束成功", 4000);
                    refresh();
                } else {
                    errmsg("结束失败:原因" + result.message, 4000);
                }
            }
        },
        error: function (result) {
            console.log("通信失败");
        }
    });


}
//运行
function runPlan(id) {
    var status = $('#' + id + '-status').text();
    if (status.trim() != '暂停') {
        errmsg("只有暂停的作业才能运行", 4000);
        return
    }
    var planinstid = $('#' + id + '-planinstid').text();
    $.ajax({
        url: path + "/tsp/planmonitor/runplan.json",
        data: {
            planInstId: planinstid
        },
        type: "post",
        async: true,
        success: function (result) {
            if (result.success) {
                errmsg("运行成功", 4000);
                search();
            } else {
                errmsg("运行失败:原因" + result.message, 4000);
            }
        }, error: function (data) {
            errmsg("通信失败", 4000);
        }
    });


}
//暂停
function pausePlan(id) {
    var planinstid = $('#' + id + '-planinstid').text();
    var status = $('#' + id + '-status').text();
    if (status.trim() != '正在运行') {
        errmsg("只有正在运行的作业才能暂停", 4000);
        return
    }
    $.ajax({
        url: path + "/tsp/planmonitor/pauseplan.json",
        data: {
            planInstId: planinstid
        },
        type: "post",
        async: true,
        success: function (result) {
            if (result.success) {
                errmsg("暂停成功", 4000);
                refresh();
            } else {
                errmsg("暂停失败:原因" + result.message, 4000);
            }
        }, error: function (data) {
            errmsg("通信失败", 4000);
        }
    });


}

//  /*查看作业功能*/
function findJob(id) {
    layer.open({
        type: 1, title: '计划状态表', area: '1000px', content: $('#jobs-detail'), success: function (dom, index) {
            $("#jobs-body").children("tr").remove();
            $.post(path + '/tsp/planmonitor/scanplan.json', {
                planInstId: id
            }, function (result) {
                if (result['success']) {
                    var jobMonitorVos = result['result'];
                    for (var i = 0; i < jobMonitorVos.length; i++) {
                        var jobMonitorVo = jobMonitorVos[i];
                        $("#jobs-body").append(
                            "<tr><td id='" + jobMonitorVo.id + "-id' class='hide'>" + jobMonitorVo.id + "</td>" +
                            "<td id='planinstid' class='hide'>" + jobMonitorVo.planInstId + "</td>" +
                            "<td id='jobid' class='hide'>" + jobMonitorVo.jobId + "</td>" +
                            "<td>" + (i + 1) + "</td>" +
                            "<td id='pname'>" + (jobMonitorVo.pName || "") + "</td>" +
                            "<td id='jname'>" + (jobMonitorVo.jName || "") + "</td>" +
                            "<td id='runhostname'>" + (jobMonitorVo.runHostname || "") + "</td>" +
                            "<td id='status'>" + (jobMonitorVo.status || "") + "</td>" +
                            "<td id='startTime'>" + (jobMonitorVo.startTime || "") + "</td>" +
                            "<td id='endTime'>" + (jobMonitorVo.endTime || "") + "</td>" +
                            "<td id='runTime'>" + (jobMonitorVo.runTime || "") + "</td>" +
                            "<td>" +
                            "<a id='" + (jobMonitorVo.id + "-log") + "' href='javascript:void(0)' class='log-dtl'>查看日志</a>" +
                            "</td></tr>"
                        );
                    }
                    $(".log-dtl").on("click", function () {
                        var hostname = $('#pname').text();
                        var planInstId = $('#planinstid').text();
                        var jobId = $('#jobid').text();
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


                    })
                }
            })
        }
    })
}
