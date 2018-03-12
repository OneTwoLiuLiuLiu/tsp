/**
 * Created by Administrator on 2016/12/30.
 */

var path = $("#path").val();
var contextPath = $("#contextPath").val();

$(function () {
    //日历选择
    $('.dtpicker').datetimepicker({
        language:'zh-CN',
        format:'yyyy-mm-dd',
        todayHighlight:true,
        autoclose:true,
        startView:2,
        minView:2,
        todayBtn:'linked'

    });

    //状态属性选择
    $('.sel-state li').on('click', function () {
        var val = $(this).text();
        console.log(val);
        $('.sel-val').text(val);
    });


    //点击全选
    $('.menu_table-checkbox-all').on('click', function () {
        var t = $(this).is(':checked');
        $('.menu_table-checkbox').each(function () {
            $(this).prop('checked', t);
        })
    });

    //table tr选中
    //var selected;
    //var name;
    //$('.data-tb tbody tr').on('click', function () {
    //    $(this).addClass('data-avtive').siblings().removeClass('data-avtive');
    //    selected=$(this)[0].children[1].innerText;
    //    name=$(this)[0].children[3].innerText;
    //    $("#"+selected+"-subBox").attr("checked",true);
    //});

    /*日志详情*/
    $(".log-dtl").on("click", function () {

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


    });

    //下拉菜单选择
    $('.dropdown-menu li').on('click',function () {
        var val = $(this).text();
        $(this).parent().siblings('.dpn-btn').children('.dpn-tip').text(val);
    });


    ///* 编辑 弹窗框的作业类型下拉框 数据初始化及事件绑定 */
    //function initSelValue() {
    //    /*初始化菜单图标*/
    //    $.ajax({
    //        url: contextPath + "/dictionary/items/tsp.type.json",
    //        data: {},
    //        type: "post",
    //        async: true,
    //        success: function (data) {
    //            if (data.success) {
    //                var tplC = [
    //                    "{@each result as it}",
    //                    "<div class='text-box0 type-select'>",
    //                    "<p style='display: none' class='flag-${it.text}'>${it.value}</p>",
    //                    "<p class='select-text'>${it.text}</p>",
    //                    "</div>",
    //                    "{@/each}"].join("");
    //                var contentC = juicer(tplC, data);
    //                $(".select-option").html(contentC);
    //            } else {
    //                showMsg("获取菜单图标列表失败！");
    //            }
    //        }, error: function (data) {
    //            showMsg("获取菜单图标列表失败，通讯异常！");
    //        }
    //
    //    });
    //}
    //initSelValue();
    ///* 编辑 弹窗框的作业类型下拉框 数据初始化及事件绑定 */
    //function initSelValue() {
    //    /*初始化菜单图标*/
    //    $.ajax({
    //        url: contextPath + "/dictionary/items/tsp.type.json",
    //        data: {},
    //        type: "post",
    //        async: true,
    //        success: function (data) {
    //            if (data.success) {
    //                var tplC = [
    //                    "{@each result as it}",
    //                    "<option value='${it.value}'>${it.text}</option>",
    //                    "{@/each}"].join("");
    //                var contentC = juicer(tplC, data);
    //                $("#runJobType").html(contentC);
    //            } else {
    //                showMsg("获取菜单图标列表失败！");
    //            }
    //        }, error: function (data) {
    //            showMsg("获取菜单图标列表失败，通讯异常！");
    //        }
    //
    //    });
    //}


    //重置按钮情况
    $('.btn-reset').on("click",function(){
        $('#hostname').val('');
    });
});
// 编辑
function edit(selected,name) {
    //var len = $('.data-tb tbody').children('.data-avtive').length;
    //if (len == 0) {
    //    errmsg("请先选择要编辑的作业服务器", 4000);
    //} else {
    $.ajax({
        url: path + "/tsp/jobserverconfig/jobserverisalive.json",
        dataType: "json",
        type: "post",
        async: false,
        data: {
            hostname: name
        },
        success: function (result) {
            if (result.result) {
                layer.open({
                    type: 1,
                    title: "作业服务器配置信息",
                    area: "566px",
                    content: $(".modal-edit"),
                    btn: ["刷新资源", "关闭"],
                    yes: function (index) {
                        //layer.close(index)
                        $("#modifyJobServerConfigForm").submit();
                        alert("刷新资源成功");
                    }
                });
            } else {
                errmsg("该作业服务器已经断开,不可编辑", 4000);
            }
        },
        error: function (result) {
            alert("错误信息", !result.message ? "与后台通信异常!" : result.message, "error");
        }
    });
    $('.hostname').val($('#' + selected + '-hostname').text());
    $('.maxRunNum').val($('#' + selected + '-maxRunNum').text());
    $('.minFreeMemory').val($('#' + selected + '-minFreeMemory').text());
    $('.minFreeCpu').val($('#' + selected + '-minFreeCpu').text());
    $('.maxHistoryDay').val($('#' + selected + '-maxHistoryDay').text());
    $('.id').val($('#' + selected + '-id').text());
    var runJobType = $('#' + selected + '-runJobType').text();
    //$('.selectpicker').selectpicker('val', ['Mustard','Relish']);
    $('#jobTypes_modify').selectpicker('val', runJobType.split(","));
}

//编辑提交时事件
$("#modifyJobServerConfigForm").on("submit",function(){
    if ($('#hostname_modify').val()==""){
        alert("请输入主机名！");
        return false;
    }
    if ($('#maxRunNum_modify').val()==""){
        alert("请输入可运行最大作业数！");
        return false;
    }
    if ($('#minFreeMemory_modify').val()==""){
        alert("请输入最小空闲内存！");
        return false;
    }
    if ($('#minFreeCpu_modify').val()==""){
        alert("请输入最小空闲cpu！");
        return false;
    }
    if ($('#maxHistoryDay_modify').val()==""){
        alert("请输入日志保留天数！");
        return false;
    }
    if ($('#jobTypes_modify').val()==null){
        alert("请选择可运行作业类型！");
        return false;
    }
});

/*设置错误提示信息自动消失方法*/
function errmsg(content, delay) {
    $(".error-msg.alert").addClass("show");
    $(".error-msg strong").text(content);
    setTimeout(function () {
        $(".error-msg.alert").removeClass("show");
        $(".error-msg.alert").alert();
    }, delay)

};

// 删除
function deleteOne(selected,name) {
        $.ajax({
            url :path+ "/tsp/jobserverconfig/jobserverisalive.json",
            dataType : "json",
            type : "post",
            async : false,
            data:{
                hostname:name
            },
            success : function(result){
                if(result.success){
                    if(result.result){
                        errmsg("该作业服务器已经重新连接,不能被删除", 4000);
                    }else{
                        deteleJobServerConfig(selected,name)
                    }
                }
            },
            error : function(result){
                Messager.alert("错误信息", !result.message ? "与后台通信异常!" : result.message, "error");
            }
        });
}

//删除多条
$('.delete').on('click', function () {
    var flag=false;
    var checked = !$('.menu_table-checkbox').get().every(function (el) {
        return !el.checked;
    });
    if (!checked) {
        alert('您没有选择任何一条!');
    } else if(confirm("确定删除选中的作业服务器?")){
        $('.menu_table-checkbox').each(function (i) {
            var isonline=$('.data-tb tbody tr')[i].children[10].innerText;
            var isChecked = $(this).is(':checked');
            if (!isChecked) {
                $(this).siblings("input[type='text']").remove();
            }else{
                if(isonline=="在线"){
                    flag=true;
                }
            }
        });
        if(!flag){
            $('#deleteBatchForm').submit();
        }else{
            errmsg("选择删除的作业服务器有在线的,不能删除，请重新选择", 4000);
        }
    }
});

function deteleJobServerConfig(selected, name) {
    $.ajax({
        url: path + "/tsp/jobserverconfig/delete.json",
        data: {
            "id": selected
        },
        type: "post",
        async: true,
        success: function (data) {
            if (data.success) {
                alert("作业服务器" + name + "删除成功！");
                refresh();
            } else {
                var msg = data.message | "未知原因";
                alert("作业服务器" + name + "删除未成功！" + msg);
            }
            refreshNodes();
        }, error: function (data) {
            alert("删除" + name + "失败，通讯异常！" + data.statusText);
        }, complete: function (data) {
            cancleDelete();
        }

    });
}



// 启用
function enable(selected) {
    //var len = $('.data-tb tbody').children('.data-avtive').length;
    //if (len == 0) {
    //    errmsg("请先选择要启用的作业服务器", 4000);
    //}
    //else if( $('#' + selected + '-status').text().trim()=="启用") {
    //    errmsg("该作业服务器已经启用", 4000);
    //}
    //else {
        $.ajax({
            url :path+ "/tsp/jobserverconfig/changestatus.json",
            dataType : "json",
            type : "post",
            async : false,
            data:{
                status:1,
                id:selected
            } ,
            success : function(result){
                if(result.success){
                    errmsg("启用成功!", 4000);
                    refresh();
                }else{
                    errmsg(result.message, 4000);
                }
            },
            error : function(result){
                //Messager.alert("错误信息", !result.message ? "与后台通信异常!" : result.message, "error");
                errmsg("通信异常",4000);
            }
        });
}

// 不启用
function disabled(selected) {
    //var len = $('.data-tb tbody').children('.data-avtive').length;
    //if (len == 0) {
    //    errmsg("请先选择要启用的作业服务器", 4000);
    //}
    //else if( $('#' + selected + '-status').text().trim()=="不启用") {
    //    errmsg("该作业服务器已经不启用", 4000);
    //}
    //else {
        $.ajax({
            url :path+ "/tsp/jobserverconfig/changestatus.json",
            dataType : "json",
            type : "post",
            async : false,
            data:{
                status:0,
                id:selected
            } ,
            success : function(result){
                if(result.success){
                    errmsg("不启用成功!", 4000);
                    refresh();
                }else{
                    errmsg(result.message, 4000);
                }
            },
            error : function(result){
                //Messager.alert("错误信息", !result.message ? "与后台通信异常!" : result.message, "error");
                errmsg("通信异常",4000);
            }
        });
}

//刷新页面
function refresh(){
    search();  //重新查询
}

function search(){
    $("#query_jobserverconfig_form").submit();
}

$('.btn-submit').on('click',function () {
    search();
});