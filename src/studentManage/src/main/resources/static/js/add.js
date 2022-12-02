$(function () {

    //验证码更新
    $("#yzm").click(function () {
        this.src = "/studentManage/getCode?d=" + new Date();
    })

})
