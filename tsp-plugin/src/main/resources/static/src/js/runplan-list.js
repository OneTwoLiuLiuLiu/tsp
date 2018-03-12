$(function () {
    var path = $('#path').val();

    /** 新增弹出框 */
    $('#data-add').on('click', function (e) {
        layer.open({
            type: 1,
            title: '增加',
            area: '566px',
            content: $('#modal-edit'),
            success: function (dom, index) {
                $('#form-edit').find('input').each(function (index, input) {
                    $(input).val('');
                });
            },
            btn: ['确认', '取消'],
            yes: function (index) {
                $.post(path + '/runPlan/add.json', $('#form-edit').serialize(), function (result) {
                    if (result['success']) {
                        search();
                    }
                    layer.close(index);
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        })
    });

    /** 批量删除 */
    $('#data-delete-all').on('click', function (e) {
        var checkedInputs = $('.qurey-result table .check-ls:checked');
        if (checkedInputs.length > 0) {
            layer.alert('确定删除全部选中的数据吗?', {
                icon: 2, btn: ['确认', '取消'], yes: function (index) {
                    var ids = [];
                    checkedInputs.each(function (i, input) {
                        ids.push($(input).parents('tr').data('id'));
                    });
                    var form = ['<form>'];
                    $.each(ids, function (i, id) {
                        form.push('<input type="text" name="ids" value="' + id + '">');
                    });
                    form.push('</form>');
                    $.post(path + '/runPlan/delete.json', $(form.join('')).serialize(), function (result) {
                        if (result['success']) {
                            search();
                        }
                    });
                    layer.close(index);
                }
            });
        } else {
            errmsg('未选中任何数据', 2000);
        }
    });

    /*修改弹出框*/
    $('.data-modify').on('click', function (e) {
        layer.open({
            type: 1,
            title: '修改',
            area: '566px',
            content: $('#modal-edit'),
            success: function (dom, index) {
                $.post(path + '/runPlan/get.json', {
                    id: $(e.target).parents('tr').data('id')
                }, function (result) {
                    if (result['success']) {
                        var data = result['result'];
                        $('#form-edit').find('input').each(function (index, input) {
                            $(input).val(data[$(input).attr('name')]);
                        });
                    }
                })
            },
            btn: ['确认', '取消'],
            yes: function (index) {
                $.post(path + '/runPlan/update.json', $('#form-edit').serialize(), function (result) {
                    if (result['success']) {
                        layer.close(index);
                        search();
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        })
    });

    /*删除单个弹出框*/
    $('.data-del').on('click', function (e) {
        layer.alert('确定删除吗?', {
            icon: 2, btn: ['确认', '取消'], yes: function (index) {
                $.post(path + '/runPlan/delete.json', {
                    ids: $(e.target).parents('tr').data('id')
                }, function (result) {
                    if (result['success']) {
                        layer.close(index);
                        search();
                    }
                });
            }
        });

    });

    /*设置错误提示信息自动消失方法*/
    function errmsg(content, delay) {
        $('.error-msg.alert').addClass('show');
        $('.error-msg strong').text(content);
        setTimeout(function () {
            $('.error-msg.alert').removeClass('show');
            $('.error-msg.alert').alert();
        }, delay)

    }

    /*表格全选功能*/
    $('.choose_all').click(function () {
        var checkel = $('.menu_table-checkbox');
        if (this.checked) {
            checkel.prop('checked', true)
        } else {
            checkel.prop('checked', false)
        }
    });

    $('.dtpicker').datetimepicker({
        language:'zh-CN',
        format:'yyyy-mm-dd',
        todayHighlight:true,
        autoclose:true,
        startView:2,
        minView:2,
        todayBtn:'linked'

    });

    //table tr选中
    var selectedId="";
    $('.data-tb tbody tr').on('click', function () {
        $(this).addClass('data-avtive').siblings().removeClass('data-avtive');
        selectedId=$(this)[0].children[0].innerText;
    });

    $('#plan-run').click(function(){
        var len = $('.data-tb tbody').children('.data-avtive').length;
        console.log(len);
        if (len == 0) {
            errmsg("请先选择要运行的计划", 4000);
        } else {
            $.post ( path + '/tsp/runplan/rerunplan.json', {
                runPlanId : selectedId
            }, function ( result ) {
                if ( result[ 'success' ] ) {
                    layer.msg ( "运行成功" );
                    search();
                } else {
                    errmsg(result['message'])
                }
            } )

        }
    });

    $('#plan-end').click(function(){
        var len = $('.data-tb tbody').children('.data-avtive').length;
        console.log(len);
        if (len == 0) {
            errmsg("请先选择要停止的计划", 4000);
        }else{
            $.post ( path + '/tsp/runplan/stopplan.json', {
                runPlanId : selectedId
            }, function ( result ) {
                if ( result[ 'success' ] ) {
                    layer.msg ( "停止成功" );
                    search();
                } else {
                    errmsg(result['message'])
                }
            } )
        }
    });

    /**
     * 查询按钮
     */

    $('.btn-submit').click(function(){
        search();
    });


    function search(){
        $('#query_runplan_form').submit();
    }

    /**
     * 查看运行计划按钮
     */
    $('#plan-findrunplan').click(function(){
        var len = $('.data-tb tbody').children('.data-avtive').length;
        console.log(len);
        if (len == 0) {
            errmsg("请先选择要查看的运行计划", 4000);
        }else{
            layer.open ( {
                type : 1, title : '查看版本', area : '1000px', content : $ ( '#runplan-detail' ), success : function ( dom, index ) {
                        $("#runplan-body").children("tr").remove();
                        $.post(path + '/tsp/runplan/getplaninstance.json', {
                            runPlanId: selectedId
                        }, function (result) {
                            if (result['success']) {
                                var planInstanceVos = result['result'];
                                for (var i = 0; i < planInstanceVos.length; i++) {
                                    var planInstanceVo = planInstanceVos[i];
                                    $("#runplan-body").append(
                                        "<tr><td class='hide'>" + planInstanceVo.id + "</td>" +
                                        "<td>" + (i + 1) + "</td>" +
                                        "<td>" + planInstanceVo.planVo.name + "</td>" +
                                        "<td>" + planInstanceVo.startTime + "</td>" +
                                        "<td>" + (planInstanceVo.endTime||"") + "</td>" +
                                        "<td>" + planInstanceVo.status + "</td>" +
                                        "<td>" + planInstanceVo.batchNo + "</td></tr>"
                                    );
                                }
                            }
                        })
                    }
                })
        }
    })

});


