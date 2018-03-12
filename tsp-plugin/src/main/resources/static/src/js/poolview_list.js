$(function () {
    var path = $('#path').val();





    /*设置错误提示信息自动消失方法*/
    function errmsg(content, delay) {
        $('.error-msg.alert').addClass('show');
        $('.error-msg strong').text(content);
        setTimeout(function () {
            $('.error-msg.alert').removeClass('show');
            $('.error-msg.alert').alert();
        }, delay)

    }

    //重置按钮情况
    $('.btn-reset').on("click",function(){
        $('#p_name').val('');
    });



});
