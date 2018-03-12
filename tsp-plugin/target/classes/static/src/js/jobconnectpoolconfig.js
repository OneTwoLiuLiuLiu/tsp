/**
 * Created by pc on 2018/3/1.
 */
var path = $ ( '#path' ).val ();
var baseUri = $ ( '#baseUri' ).val ();
$(function () {

    /* 新增弹出框 */
    $ ( '#dataSource-add' ).on ( 'click', function ( e ) {
        layer.open ( {
            type : 1, title : '新增作业连接池配置', area : '566px', content : $ ( '#modal-edit' ),
            success : function ( dom, index ) {
                $ ( '#form-edit' ).find ( 'input' ).each ( function ( index, input ) {
                    $ ( input ).val ( '' );
                } );
            }, btn : [ '确认', '取消' ], yes : function ( index ) {
                //$ ( '#form-edit' ).submit();

                if ( jobconnectpoolconfigValidate ()) {
                    $.post ( path+'/tsp/jobconnectpoolconfig/add.json', $ ( '#form-edit' ).serialize (),
                        function ( result ) {
                            if ( result['success'] ) {
                                layer.close ( index );
                                errmsg ( "新增作业连接池配置", 4000 );
                                $('#data-query').click();
                            } else {
                                layer.alert ( "新增作业连接池配置：原因是" + result.message );
                            }
                        } );
                    alert(result['success'] +"现在测试6"+path +jobconnectpoolconfigValidate ());
                }
            }, btn2 : function ( index ) {
                layer.close ( index );
            }
        } )
    } );
    /* 删除弹出框 */
    $ ( '#dataSource-delete' ).on ( 'click', function ( e ) {
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
                $.post(path +'/tsp/jobconnectpoolconfig/delete.json', {
                    jobConnectPoolConfigId: ids.join(",")
                },
                    function (result) {
                    if (result['success']) {
                        layer.close(index);
                        $('#data-query').click();
                    }
                });
                alert("现在测试1"+result['success']);
            }
        });
    } );
    /*修改弹出*/
    $('#dataSource-edit').on('click', function (e) {
        var ids=[];
        $(".menu_table-checkbox").each(function(){
            var me=this;
            if($(me).prop("checked")){
                var id=$(me).parents('tr').attr("data-id");
                ids.push(id);
            }
        })
        if(null==ids||ids.length==0){
            layer.alert("请选择你要修改的记录");
            return;
        }
        layer.open({
            type: 1,
            title: '修改',
            area: '566px',
            content: $('#modal-edit'),
            success: function (dom, index) {
                $.post(path + '/tsp/jobconnectpoolconfig/getById.json', {
                    jobConnectPoolConfigId:ids.join(",")
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
                $.post(path + '/tsp/jobconnectpoolconfig/update.json', $('#form-edit').serialize(), function (result) {
                    if (result['success']) {
                        layer.close(index);
                        $('#data-query').click();
                    }
                });
            },
            btn2: function (index) {
                layer.close(index);
            }
        })
    });

})
/*新增修改验证*/
function jobconnectpoolconfigValidate () {
    if ( $ ( "#form-edit input[name='data_source_name']" ).val () == "" ) {
        errmsg ( "计划名称不能为空", 4000 );
        return false;
    }
    return true;
}
