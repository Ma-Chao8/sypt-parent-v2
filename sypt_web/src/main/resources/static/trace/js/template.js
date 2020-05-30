/**
 * 获取模板数据
 * @param ele
 * @returns {[]}
 */
function getTemplate(ele) {
    var arr = [];
    $(ele).find(".area").each(function () {
        var eleName = $(this).attr("name");
        if (eleName) {
            var nodeIndex = findNodeIndex(arr, eleName);
            var node = new Object();
            node["name"] = eleName;
            node["index"] = nodeIndex;
            arr.push(node);
        }
    });
    var tplObj = crateTplObj(arr);
    return tplObj;
}

/**
 * 查找环节所处位置下表（在出现环节名称重复的情况下）
 * @param arr
 * @param nodeName
 * @returns {number}
 */
function findNodeIndex(arr, nodeName) {
    var nodeIndex = 0;
    for (var i in arr) {
        if (arr[i].name === nodeName) {
            nodeIndex = parseInt(parseInt(nodeIndex) + 1)
        }
    }
    return nodeIndex;
}

/**
 *
 * @param arr
 * @returns {[]}
 */
function crateTplObj(arr) {
    var str = [];
    $.each(arr, function (index, item) {
        var $sub = $("#demoTpl").find('.' + item.name).eq(item.index);
        var $name = $("." + item.name).eq(item.index).find("dt>span").text();
        var obj = {
            name: $name,
            style: item.name,
            sub: $sub.prop("outerHTML")
        };
        for (var i = 1; i < 10; i++) {
            if ($sub.find(".fc-sub" + i).length > 0) {
                var $fc_html = $sub.find(".fc-sub" + i).first().prop("outerHTML");
                var $fc_key = "sub" + i;
                obj[$fc_key] = $fc_html
            } else {
                break;
            }
        }
        obj['level'] = i - 1;
        str.push(obj);
    });
    return str
}

/**
 *
 * @param arr
 */
function creatList(arr) {
    var nums = ["一", "二", "三", "四", "五", "六", "七", "八"];
    $("#modelist").empty();
    $.each(arr, function (index, item) {
        var html = '<li class="item" name="' + item.style + '">' +
            '<h4>' + item.name + '样式</h4>' +
            '<div class="info">' +
            '<ul>';
        var breakindex = 10;

        if (item.sub2 && item.sub2.indexOf('fc-sub3') > -1) {
            if (item.sub3 && item.sub3.indexOf("subkey") <= (-1) && item.sub3.indexOf("subvalue") <= (-1)) {
                breakindex = 3
            }
        } else {
            if (item.sub2 && item.sub2.indexOf("subkey") <= (-1) && item.sub2.indexOf("subvalue") <= (-1)) {
                breakindex = 2
            }
        }
        var level = Number(item.level);
        for (var i = 0; i < item.level; i++) {
            if (i + 1 >= breakindex) {
                break;
            }
            html += '<li>' + nums[i] + '级标题<i class="Hui-iconfont Hui-iconfont-arrow3-right f-r dragIcon" data-toggle="tooltip" data-placement="top" title="添加" draggable="true" name="' + (i + 1) + '" parentId="' + item.style + '"></i>'
            if (i == 0) {
                html += '<i class="Hui-iconfont Hui-iconfont-add3 f-r addIcon" data-toggle="tooltip" data-placement="top" title="添加"></i>'
            }
            html += "</li>"

        }
        html += "</ul></li>";
        $("#modelist").append(html)
    });
    zhedie("#modelist");
    /*
     * 新增
     */
    $("#modelist .item h4").first().click()
}

/**
 *
 * @param ele
 */
function zhedie(ele) {
    $(ele).Huifold({
        titCell: '.item h4',
        mainCell: '.item .info',
        type: 3,
        trigger: 'click',
        className: "selected",
        speed: "first",
    });
}