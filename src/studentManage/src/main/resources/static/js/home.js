$(function () {

    $("#admin").click(function () {
        let name = $("#name>input").val()
        let isLike = $("#isLike>select option:selected").val()
        let sex = $("#sex>select option:selected").val()
        let ageMin = $("#ageMin").val()
        let ageMax = $("#ageMax").val()
        let birthYear = $("#year>input").val()
        let birthMonth = $("#month>input").val()
        let result = "getStudentByMap";
        if (name != "" | isLike != "" | sex != "" |
            ageMin != "" | ageMax != "" | birthYear != "" |
            birthMonth != "" | result != "") {
            result = result + "?"
        }
        if (ageMin != "" & ageMin < 0){
            alert("最小年龄输入不合法")
            return
        }
        if (ageMax != "" & ageMax > 100){
            alert("最大年龄输入不合法")
            return
        }
        if (birthYear != ""){
            if (birthYear < 1900 | birthYear > 2030){
                alert("年份输入不合法")
                return
            }
        }
        if (birthMonth != ""){
            if (birthMonth < 1 | birthMonth > 12){
                alert("月份输入不合法")
                return
            }
        }
        if (name != "") result = result + "name=" + name + "&"
        if (isLike != "") result = result + "isLike=" + isLike + "&"
        if (sex != "") result = result + "sex=" + sex + "&"
        if (ageMin != "") result = result + "ageMin=" + ageMin + "&"
        if (ageMax != "") result = result + "ageMax=" + ageMax + "&"
        if (birthYear != "") result = result + "birthYear=" + birthYear + "&"
        if (birthMonth != "") result = result + "birthMonth=" + birthMonth
        if (result.substring(result.length - 1) == "&") result = result.substring(0, result.length - 1)
        $(location).attr('href', 'http://localhost:8080/studentManage/' + result);
    })

    $(".student_button1").click(function () {
        let number = $(this).parent().parent().children(".mineID").html();
        if (confirm("是否修改此同学信息？")) {
            $(location).attr('href', 'http://localhost:8080/studentManage/toUpdate/' + number);
        }
    })

    $(".student_button2").click(function () {
        let number = $(this).parent().parent().children(".mineID").html();
        if (confirm("是否删除此同学信息？")) {
            $.ajax({
                type: "post",
                url: "deleteStudentById/" + number,
                success: function (obj) {
                    if (obj == "删除成功") {
                        alert("删除成功")
                        location.reload();
                    } else {
                        alert("删除失败")
                    }
                }
            })
        }
    })

    $(".mineID").dblclick(function () {
        if ($(this).css("color") == "rgb(0, 0, 0)") {
            $(this).css("color", "red").css("font-weight", "800")
        } else {
            $(this).css("color", "black").css("font-weight", "400")
        }
    })

    $("#clickRed").click(function () {
        let result = "";
        for (let i = 0; i < $(".mineID").length; i++) {
            if ($(".mineID").eq(i).css("color") != "rgb(0, 0, 0)") {
                result = result + $(".mineID").eq(i).html() + ","
            }
        }
        result = result.substring(0, result.length - 1)
        if (result == "") {
            alert("请先选择需要删除（双击序号选择）");
            return;
        }
        if (confirm("是否删除此同学信息？")) {
            $.ajax({
                type: "post",
                url: "deleteStudentByIdBatch/" + result,
                success: function (obj) {
                    if (obj == "批删除成功") {
                        alert("删除成功")
                        location.reload();
                    } else {
                        alert("批删除失败")
                    }
                }
            })
        }
    })
})
