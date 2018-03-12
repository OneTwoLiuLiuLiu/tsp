/**
 * Created by chenqian on 2016/11/19.
 */
/*保留九宫格内没有常用菜单的格子的默认html内容，以备有菜单的格子删除菜单时添加*/
var noneTpl = ["<a href='#' class='empty'></a>","</a>"].join("");

/*保存故有的两个常用菜单名称*/
var commmenuNames={};
var orginalcommmenus = $(".nav-icon");
if(orginalcommmenus && orginalcommmenus.length>0) {
    $.each(orginalcommmenus, function () {
        commmenuNames[$(this).parent().attr("title")]=true;
    })
};

function loadQuickMenu(){
    loadCommonMenu(renderCommonMenu);
}

var selectedQuickMenuMap={};

/*从后台获取用户常用菜单数据*/
function loadCommonMenu(callback) {
    $.ajax({
        url: contextPath+"/common-menu/list.json",
        data: { },
        type: "post",
        async: true,
        success: function (data) {
            if (! data.success && data.result) {
                var msg = data.message | "未知原因";
                alert("获取常用菜单失败！"+msg);
            }
            selectedQuickMenuMap=transMap(data.result);
            callback&&callback(data.result);
        }, error: function (data) {
            alert("获取常用菜单失败，" + "通讯异常！"+data.statusText);
        }
    });
};

function transMap(data) {
    var map = {};
    for(var idx in data) {
        map[data[idx].menuItemId]=data[idx];
    }
    return map;
}

/*初始化常用菜单的html*/
function renderCommonMenu(data) {
    //加载常用菜单data
    for(var i in data) {
        initHtml(data[i]);
    }
};
/*生成常用菜单的html片段*/
function initHtml(node){
    var span_label=$("#ord-"+node.ord);
    if(span_label.children("a").hasClass("empty")) {
        var html= ["<a href='",contextPath,node.menuUrl,
            "' data-toggle='tooltip'",
            "data-placement='top'",
            "title='",node.menuName,"'",
            "menuId='",node.menuItemId,"'>",
            "<i class='nav-icon ",node.menuIcon,"' ></i>",
            "<span class='deleteCommonMenu'></span>",
            "</a>"
        ].join("");
        span_label.html(html);
        $('[data-toggle="tooltip"]').tooltip();
    }

}

function openSelectQuickMenuWin(){
    loadSelectQucikMenu(showSelectQuickMenu);
}

var globalQuickMenuData = null;
var globalMenuMap = null;
function loadSelectQucikMenu(callback) {
    if(globalQuickMenuData){
        callback && callback();
    }
    $.ajax({
        url: contextPath+"/menu/getchilds.json",
        type: "post",
        data: {},
        async: true,
        success: function (data) {
            if (! data.success && data.result) {
                var msg = data.message | "未知原因";
                alert("获取可选常用菜单列表失败！"+msg);
            }
            var treedata = commonMenuTreeFilter(data.result)
            callback&&callback(treedata);
        }, error: function (data) {
            alert("获取可选常用菜单列表失败，" + "通讯异常！"+data.statusText);
        }
    });
}


function panelDataFilter(treeData){
    var panelMap = {};
    for(var i in treeData) {
        processLeaf(treeData[i],"",panelMap);
    }
    return panelMap;
}

function processLeaf(node,key,map){
    var localKey = key;
    if(node){
        if(node.isParent && node.childMenuVos){
            localKey += node.menuName;
            for(var i in node.childMenuVos){
                processLeaf(node.childMenuVos[i],localKey,map);
            }
        }else{
            if(localKey==""){
                localKey = "其它";
            }
            if(!map.hasOwnProperty(localKey) || !map[localKey]){
                map[localKey] = [];
            }
            map[localKey].push(node);
        }
    }
}

/* 过滤常用菜单可选树的数据 */
function commonMenuTreeFilter(resultData) {
    globalMenuMap = {};
    var _select = [];
    for( var i in resultData) {
        var item = resultData[i];
        if(item.isEnable) {
            if(item.childMenuVos) {
                var children = [];
                for(var j in item.childMenuVos) {
                    var it = item.childMenuVos[j];
                    if(it.menuAction && it.isEnable){
                        globalMenuMap[it.menuId]=it;
                        if(!commmenuNames[it.menuName]){
                            children.push(it);
                        }
                    }
                }
                item.childMenuVos = children;
                if(children.length>0) {
                    _select.push(item);
                }
            }else{
                if(item.menuAction) {
                    if(!commmenuNames[item.menuName]){
                        globalMenuMap[item.menuId]=item;
                        _select.push(item);
                    }
                }
            }
        }
    }
    return _select;
};

var tileTpl=["{@each result as it,index}",
    "<div class='panel select-menupanel'>",
    "<div class='panel-heading'>","${it.menuName}","</div>",
    "<div class='panel-body select-menu'>",
    "{@each it.childMenuVos as item}",
    "<label for='' class='radio-inline'>",
    "<input type='radio' name='commenu' value='","${item.menuId}","'/>",
    "<span class='nav-icon ","${item.menuIcon}","'></span>","${item.menuName}",
    "</label>",
    "{@/each}",
    "</div>",
    "</div>",
    "{@/each}"].join("");
var tileComMenu=juicer(tileTpl);

/*新增界面，可选项平铺时使用*/
function initTileCommon(panelData){
    var panelData = ignoreExistsMenus(panelData);
    var data = [];
    for(var key in panelData ){
        var obj= {};
        obj["menuName"]= key;
        obj["childMenuVos"]=panelData[key];
        data.push(obj);
    }

    var datas = {"result": data};
    var menushtml = tileComMenu.render(datas);
    $(".modal-body").html(menushtml);
};

function showSelectQuickMenu(treeData){
    if(!globalQuickMenuData){
        globalQuickMenuData = panelDataFilter(treeData);
    }
    initTileCommon(globalQuickMenuData);
    $("#fastmenu").modal("show");
}

function ignoreExistsMenus(panelData){
    var selectMenuData = {};
    if(panelData){
        for(var p in panelData) {
            selectMenuData[p]=[];
            var subMenus = panelData[p];
            for( var idx in subMenus) {
                if(!selectedQuickMenuMap[subMenus[idx].menuId]){
                    selectMenuData[p].push(subMenus[idx]);
                }
            }
            if(selectMenuData[p].length==0){
                delete selectMenuData[p];
            }
        }
    }
    return selectMenuData;
}

/*向后台请求删除新常用菜单*/
function deleteCommonMenu(menuId,ord) {
    var commonMenuId=selectedQuickMenuMap[menuId].commonMenuId;
    $.ajax({
        url: contextPath+"/common-menu/delete.json",
        data: {
            "commonMenuId": commonMenuId,
        },
        type: "post",
        async: true,
        success: function (data) {
            if (! data.success) {
                var msg = data.message | "未知原因";
                alert("删除常用菜单失败！"+msg);
                return;
            }
            var span_label=$("#ord-"+ord);
            span_label.html(noneTpl);
            delete selectedQuickMenuMap[menuId];
        }, error: function (data) {
            alert("删除常用菜单失败，" + "通讯异常！"+data.statusText);
        }
    });
}

/*向后台请求添加新常用菜单*/
function addCommonMenu(menuItemId) {
    var selcetNode = getMenu(menuItemId);
    var ord=$("#commonMenuOrd").val();
    if(!ord) {
        alert("未能获取位置号，无法添加常用菜单！");
        return;
    }
    $.ajax({
        url: contextPath+"/common-menu/add.json",
        data: {
            "menuItemId": selcetNode.menuId,
            "menuName": selcetNode.menuName,
            "menuUrl": selcetNode.menuAction,
            "ord": ord
        },
        type: "post",
        async: true,
        success: function (data) {
            if (! data.success) {
                var msg = data.message | "未知原因";
                alert("新增常用菜单失败！"+msg);
                return;
            }
            var newQuickMenu = data.commonMenuVo;
            newQuickMenu.menuIcon=selcetNode.menuIcon;
            initHtml(newQuickMenu);
            selectedQuickMenuMap[selcetNode.menuId]=newQuickMenu;
            $("#fastmenu").modal("hide");
        }, error: function (data) {
            alert("新增常用菜单失败，" + "通讯异常！"+data.statusText);
        }
    });
};

function getMenu(menuItemId){
    return globalMenuMap[menuItemId];
}
$(function () {
    /**
     * 常用菜单部分，开始
     * */
    $("#menucac").click(function(){
        $("#commonMenuOrd").val("");
        $("#fastmenu").modal("hide");
    })

//加载九宫图数据；
    loadQuickMenu();

    /**
     * 添加_“+”,绑定打开可选树界面事件
     * */
    $('.main_fore').on('click','.empty',function(){
        /*将位置信息传递给可选树保存*/
        var ord=$(this).parents("td").attr("id").split("-")[1];
        $("#commonMenuOrd").val(ord);

        /*打开窗口*/
        openSelectQuickMenuWin();

    });

    /*可选树界面“确定”，绑定确定添加菜单事件*/
    $("#menusure").click(function () {
        var menuItemId = $("input:radio[name='commenu']:checked").val();
        addCommonMenu(menuItemId);
    });

    /**
     * 删除_“x”,绑定删除常用菜单的事件
     * */
    $('.main_fore').on('click','.deleteCommonMenu',function(e){
        e.preventDefault();
        if(confirm("确定从常用菜单中删除“"+$(this).parents("a").attr("data-original-title")+"”吗？")){
            var menuId = $(this).parents("a").attr("menuId");
            var ord=$(this).parents("td").attr("id").split("-")[1];
            deleteCommonMenu(menuId,ord);
        }
    });
});
